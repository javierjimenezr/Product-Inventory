package com.ProyecOne.EcoMarketSpa.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ProyecOne.EcoMarketSpa.model.Inventory;


public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // Sin métodos personalizados por ahora
}

