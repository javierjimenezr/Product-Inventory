package com.ecomarket.Product_Service.service;


import com.ecomarket.Product_Service.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product create(Product product);
    Product getById(Long id);
    List<Product> getAll();
    Product update(Long id, Product product);
    void delete(Long id);
    List<Product> getByCategory(String category);
}
