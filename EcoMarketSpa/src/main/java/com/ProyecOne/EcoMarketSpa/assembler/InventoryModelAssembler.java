package com.ProyecOne.EcoMarketSpa.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.ProyecOne.EcoMarketSpa.controller.InventoryController;
import com.ProyecOne.EcoMarketSpa.model.Inventory;
import com.ProyecOne.EcoMarketSpa.model.InventoryModel;

import io.micrometer.common.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Optional;

@Component
public class InventoryModelAssembler implements RepresentationModelAssembler<Inventory, InventoryModel> {

    @Override
    @NonNull
    public InventoryModel toModel(@NonNull Inventory inventory) {
        InventoryModel model = new InventoryModel();

        model.setId(inventory.getId());
        model.setQuantity(inventory.getQuantity());
        model.setLocation(inventory.getLocation());

        model.add(linkTo(methodOn(InventoryController.class).getById(inventory.getId())).withSelfRel());
        model.add(linkTo(methodOn(InventoryController.class).getAll()).withRel("listar-todo"));
        model.add(linkTo(methodOn(InventoryController.class).update(inventory.getId(), inventory)).withRel("actualizar"));
        model.add(linkTo(methodOn(InventoryController.class).delete(inventory.getId())).withRel("eliminar"));
        model.add(linkTo(methodOn(InventoryController.class).increaseStockById(inventory.getId(), 0)).withRel("aumentar-stock"));
        model.add(linkTo(methodOn(InventoryController.class).reduceStockById(inventory.getId(), 0)).withRel("reducir-stock"));

        return model;
    }
}