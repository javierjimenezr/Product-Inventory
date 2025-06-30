package com.ecomarket.Product_Service.service;



import com.ecomarket.Product_Service.model.Product;
import com.ecomarket.Product_Service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ecomarket.Product_Service.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró el producto con id: " + id));
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll();
            
    }

    @Override
    public Product update(Long id, Product product) {
        return repository.findById(id).map(p -> {
            p.setName(product.getName());
            p.setDescription(product.getDescription());
            p.setPrice(product.getPrice());
            p.setStock(product.getStock());
            p.setCategory(product.getCategory());
            return repository.save(p);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Product> getByCategory(String category) {
        return repository.findByCategory(category);
    }
}
