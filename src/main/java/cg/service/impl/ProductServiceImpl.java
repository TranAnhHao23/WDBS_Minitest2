package cg.service.impl;

import cg.model.Category;
import cg.model.Product;
import cg.repository.ICategoryRepository;
import cg.repository.IProductRepository;
import cg.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements IProductService {
    @Autowired
    private IProductRepository productRepository;

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findById(Long id) {
        if (productRepository.findById(id).isPresent()) {
            return productRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Page<Product> findAllByNameContainingAndPriceBetweenAndCategory(String name, double firstPrice, double secondPrice, Category category, Pageable pageable) {
        return productRepository.findAllByNameContainingAndPriceBetweenAndCategory(name,firstPrice,secondPrice,category,pageable);
    }

    @Override
    public Page<Product> findAllByNameContainingAndPriceBetween(String name, double firstPrice, double secondPrice, Pageable pageable) {
        return productRepository.findAllByNameContainingAndPriceBetween(name,firstPrice,secondPrice,pageable);
    }

    @Override
    public void deleteAllByCategory(Category category) {
        productRepository.deleteAllByCategory(category);
    }

}
