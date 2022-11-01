package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.response.ResponseMessage;
import rikkei.academy.model.Category;
import rikkei.academy.service.category.ICategoryService;

import java.util.Optional;

@RestController
@RequestMapping("/api/category")
@CrossOrigin
public class CategoryController {
    @Autowired
    ICategoryService categoryService;
    @GetMapping
    public ResponseEntity<?> showAllCategory(Pageable pageable){
        Page<Category> categories = categoryService.findAll(pageable);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category){
        if (category.getName().trim().equals("")){
            return new ResponseEntity<>(new ResponseMessage("name_valid"),HttpStatus.NOT_FOUND);
        }
        categoryService.save(category);
        return new ResponseEntity<>(new ResponseMessage("Create_success"),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id")Optional<Category> category){
        if (!category.isPresent()) {
            return new ResponseEntity<>(new ResponseMessage("Category_not_found"),HttpStatus.NOT_FOUND);
        }
        categoryService.deleteById(category.get().getId());
        return new ResponseEntity<>(new ResponseMessage("delete_ok"),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable  Long id){
        return new ResponseEntity<>(categoryService.findById(id).get(),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?>editCategory( @PathVariable("id") Long id,
                                          @RequestBody Category category){

        Optional<Category> optionalCategory = categoryService.findById(id);
        if (!optionalCategory.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        category.setId(id);
        categoryService.save(category);
        return new ResponseEntity<>(new ResponseMessage("Update success"),HttpStatus.OK);
    }
}
