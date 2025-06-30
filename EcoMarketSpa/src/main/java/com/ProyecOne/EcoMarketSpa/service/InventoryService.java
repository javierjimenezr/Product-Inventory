package com.ProyecOne.EcoMarketSpa.service;


import java.util.List;

import com.ProyecOne.EcoMarketSpa.model.Inventory;

public interface InventoryService {
    Inventory createInventory(Inventory inventory);
    Inventory updateInventory(Long id, Inventory inventory);
    void deleteInventory(Long id);
    List<Inventory> getAllInventory();
    Inventory getInventoryById(Long id);
    Inventory reduceStockById(Long id, int amount);
    Inventory increaseStockById(Long id, int amount);
}