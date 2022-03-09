package cg.controller;

import cg.model.Category;
import cg.model.Product;
import cg.service.ICategoryService;
import cg.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<Iterable<Product>> showAll(@PageableDefault(value = 2) Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        if (products.isEmpty()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products.getContent(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Product>> showAllPage(@PageableDefault(value = 2) Pageable pageable) {
        Page<Product> products = productService.findAll(pageable);
        if (products.isEmpty()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> showDetailProduct(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        productService.save(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.deleteById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        Product productUpdate = productService.findById(id);
        if (productUpdate == null) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        product.setId(id);
        productService.save(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/searchFull")
    public ResponseEntity<Iterable<Product>> findByAll(@RequestParam("search") String search, @RequestParam("firstPrice") String firstPrice, @RequestParam("secondPrice") String secondPrice, @RequestParam("idCategory") Long idCategory, @PageableDefault(value = 2) Pageable pageable) {
        Page<Product> products;
        Iterable<Product> productsState = productService.findAll();
        double max = 0, min = 0;
        for (Product product : productsState) {
            if (product.getPrice() < min) {
                min = product.getPrice();
            }
            if (product.getPrice() > max) {
                max = product.getPrice();
            }
        }

        if (firstPrice.equals("")) {
            firstPrice = String.valueOf(min);
        }
        if (secondPrice.equals("")) {
            secondPrice = String.valueOf(max);
        }

        if (idCategory == 0) {
            products = productService.findAllByNameContainingAndPriceBetween(search, Double.parseDouble(firstPrice), Double.parseDouble(secondPrice), pageable);
        } else {
            Category category = categoryService.findById(idCategory);
            products = productService.findAllByNameContainingAndPriceBetweenAndCategory(search, Double.parseDouble(firstPrice), Double.parseDouble(secondPrice), category, pageable);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
