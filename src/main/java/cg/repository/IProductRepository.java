package cg.repository;

import cg.model.Category;
import cg.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface IProductRepository extends PagingAndSortingRepository<Product,Long> {

    Page<Product> findAllByNameContainingAndPriceBetweenAndCategory(String name, double firstPrice, double secondPrice, Category category, Pageable pageable);

    Page<Product> findAllByNameContainingAndPriceBetween(String name, double firstPrice, double secondPrice, Pageable pageable);

    void deleteAllByCategory(Category category);
}
