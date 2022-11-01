package rikkei.academy.service.product;

import rikkei.academy.model.Category;
import rikkei.academy.model.Product;
import rikkei.academy.service.IGenericService;

public interface IProductService extends IGenericService<Product> {
    Iterable<Product> findByCategory(Category category);
}