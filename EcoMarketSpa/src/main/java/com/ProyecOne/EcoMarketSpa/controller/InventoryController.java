package com.ProyecOne.EcoMarketSpa.controller;


import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ProyecOne.EcoMarketSpa.model.InventoryModel;
import com.ProyecOne.EcoMarketSpa.Exeption.ResourceNotFoundException;
import com.ProyecOne.EcoMarketSpa.assembler.InventoryModelAssembler;
import com.ProyecOne.EcoMarketSpa.dto.ProductDTO;
import com.ProyecOne.EcoMarketSpa.model.Inventory;
import com.ProyecOne.EcoMarketSpa.service.InventoryService;
import com.ProyecOne.EcoMarketSpa.service.impl.InventoryServiceImpl;

import java.util.List;
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService service;
    private final InventoryModelAssembler assembler;  // <-- inyectar assembler

    public InventoryController(InventoryService service, InventoryModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    // Crear nuevo inventario
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Inventory inventory) {
        try {
            Inventory created = service.createInventory(inventory);
            InventoryModel model = assembler.toModel(created);  // convertir a modelo HATEOAS
            return ResponseEntity.ok(model);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error al crear inventario: " + e.getMessage());
        }
    }

    // Actualizar inventario por ID
    @PutMapping("/{id}_actualizar")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Inventory inventory) {
        try {
            Inventory updated = service.updateInventory(id, inventory);
            InventoryModel model = assembler.toModel(updated);  // convertir a modelo HATEOAS
            return ResponseEntity.ok(model);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Inventario no encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error al actualizar inventario: " + e.getMessage());
        }
    }

    // Eliminar inventario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.deleteInventory(id);
            return ResponseEntity.noContent().build(); // HTTP 204
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Inventario no encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error al eliminar inventario: " + e.getMessage());
        }
    }

    // Obtener todos los inventarios
    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<Inventory> list = service.getAllInventory();
            CollectionModel<InventoryModel> models = assembler.toCollectionModel(list);  // lista con HATEOAS
            return ResponseEntity.ok(models);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error al obtener inventarios: " + e.getMessage());
        }
    }

    // Obtener inventario por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Inventory inventory = service.getInventoryById(id);
            InventoryModel model = assembler.toModel(inventory);  // convertir a modelo HATEOAS
            return ResponseEntity.ok(model);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Inventario no encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error interno: " + e.getMessage());
        }
    }

    // Reducir stock
    @PutMapping("/{id}/reduce-stock")
    public ResponseEntity<?> reduceStockById(@PathVariable Long id, @RequestParam int amount) {
        try {
            Inventory updated = service.reduceStockById(id, amount);
            InventoryModel model = assembler.toModel(updated);  // convertir a modelo HATEOAS
            return ResponseEntity.ok(model);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Inventario no encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error al reducir stock: " + e.getMessage());
        }
    }

    // Aumentar stock
    @PutMapping("/{id}/increase-stock")
    public ResponseEntity<?> increaseStockById(@PathVariable Long id, @RequestParam int amount) {
        try {
            Inventory updated = service.increaseStockById(id, amount);
            InventoryModel model = assembler.toModel(updated);  // convertir a modelo HATEOAS
            return ResponseEntity.ok(model);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Inventario no encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error al aumentar stock: " + e.getMessage());
        }
    }

    @GetMapping("/product-details/{productId}")
    public ResponseEntity<?> getProductDetailsFromProductService(@PathVariable Long productId) {
        try {
            // ⚠️ Asegúrate de hacer cast si estás usando la interfaz como tipo
            ProductDTO product = ((InventoryServiceImpl) service).getProductDetails(productId);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al obtener producto: " + e.getMessage());
        }
    }
}