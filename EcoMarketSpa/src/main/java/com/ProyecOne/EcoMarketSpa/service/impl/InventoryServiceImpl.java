package com.ProyecOne.EcoMarketSpa.service.impl;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ProyecOne.EcoMarketSpa.dto.ProductDTO;
import com.ProyecOne.EcoMarketSpa.model.Inventory;
import com.ProyecOne.EcoMarketSpa.repository.InventoryRepository;
import com.ProyecOne.EcoMarketSpa.service.InventoryService;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository repository;
    private final RestTemplate restTemplate;

    // Inyectar URL base del Product-Service
    @Value("${product.service.url}")
    private String productServiceUrl;

    public InventoryServiceImpl(InventoryRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    // MÉTODO NUEVO: consumir datos del Product-Service por ID
    public ProductDTO getProductDetails(Long productId) {
        String url = productServiceUrl + "/api/products/" + productId;
        return restTemplate.getForObject(url, ProductDTO.class);
    }

  
    
    @Override
    public Inventory createInventory(Inventory inventory) {
        inventory.setId(null);
        return repository.save(inventory);
    }

    @Override
    public Inventory updateInventory(Long id, Inventory inventory) {
        Inventory existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory with ID " + id + " not found."));

        existing.setQuantity(inventory.getQuantity());
        existing.setLocation(inventory.getLocation());

        return repository.save(existing);
    }

    @Override
    public void deleteInventory(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Inventory with ID " + id + " does not exist.");
        }
        repository.deleteById(id);
    }

    @Override
    public List<Inventory> getAllInventory() {
        return repository.findAll();
    }

    @Override
    public Inventory getInventoryById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory with ID " + id + " not found."));
    }

    @Override
    public Inventory reduceStockById(Long id, int amount) {
        Inventory inventory = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory with ID " + id + " not found."));

        if (inventory.getQuantity() < amount) {
            throw new RuntimeException("Not enough stock. Available: " + inventory.getQuantity());
        }

        inventory.setQuantity(inventory.getQuantity() - amount);
        return repository.save(inventory);
    }

    @Override
    public Inventory increaseStockById(Long id, int amount) {
        Inventory inventory = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory with ID " + id + " not found."));

        inventory.setQuantity(inventory.getQuantity() + amount);
        return repository.save(inventory);
    }
}