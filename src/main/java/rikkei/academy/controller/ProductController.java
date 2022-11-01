package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.ProductDTO;
import rikkei.academy.dto.response.ResponseMessage;
import rikkei.academy.model.Category;
import rikkei.academy.model.Product;
import rikkei.academy.service.category.ICategoryService;
import rikkei.academy.service.product.IProductService;

import java.util.Optional;

@RestController
    @RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> findAllProduct(@PageableDefault(size = (3)) Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(
            @RequestBody
            ProductDTO productDTO
    ) {
        Product product = new Product();

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setManufacturer(productDTO.getManufacturer());

        Long idCategory = productDTO.getIdCategory();
        Optional<Category> category = categoryService.findById(idCategory);
        if (!category.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage("Category not found!!!"), HttpStatus.NOT_FOUND);
        } else {
            product.setCategory(category.get());
        }
        productService.save(product);
        return new ResponseEntity<>(new ResponseMessage("Create product success!"), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editProduct(
            @PathVariable
            Long id,
            @RequestBody
            ProductDTO productDTO
    ) {
        Optional<Product> productOptional = productService.findById(id);
        if (!productOptional.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage("Product not found!!!"), HttpStatus.NOT_FOUND);
        }

        Product product = new Product();
        product.setId(id);
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setManufacturer(productDTO.getManufacturer());

        Long idCategory = productDTO.getIdCategory();
        Optional<Category> category = categoryService.findById(idCategory);
        if (!category.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage("Category not found!!!"), HttpStatus.NOT_FOUND);
        } else {
            product.setCategory(category.get());
        }

        productService.save(product);
        return new ResponseEntity<>(new ResponseMessage("Edit product success!"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> details(
            @PathVariable Long id
    ) {
        Optional<Product> product = productService.findById(id);
        if (!product.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage("Product not found!!!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(
            @PathVariable("id") Optional<Product> product
    ) {
        if(!product.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage("Product not found!!!"), HttpStatus.NOT_FOUND);
        }
        productService.deleteById(product.get().getId());
        return new ResponseEntity<>(new ResponseMessage("Delete success!!"), HttpStatus.OK);
    }
}