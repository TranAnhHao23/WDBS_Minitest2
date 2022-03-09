package cg.service;

import cg.model.Category;
import cg.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Iterable<Product> findAll();

    Page<Product> findAll(Pageable pageable);

    Product findById(Long id);

    void deleteById(Long id);

    Product save(Product product);

    Page<Product> findAllByNameContainingAndPriceBetweenAndCategory(String name, double firstPrice, double secondPrice, Category category, Pageable pageable);

    Page<Product> findAllByNameContainingAndPriceBetween(String name, double firstPrice, double secondPrice, Pageable pageable);

    void deleteAllByCategory(Category category);
}
