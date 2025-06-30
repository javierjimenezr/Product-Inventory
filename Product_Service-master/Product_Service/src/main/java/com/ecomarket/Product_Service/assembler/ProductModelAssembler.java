package com.ecomarket.Product_Service.assembler;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.ecomarket.Product_Service.controller.ProductController;
import com.ecomarket.Product_Service.model.Product;
import com.ecomarket.Product_Service.model.ProductModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Optional;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, ProductModel> {

    @Override
    public ProductModel toModel(Product prueba){
        ProductModel model = new ProductModel();

        model.setId(prueba.getId());
        model.setName(prueba.getName());
        model.setDescription(prueba.getDescription());
        model.setPrice(prueba.getPrice());
        model.setStock(prueba.getStock());
        model.setCategory(prueba.getCategory());

        model.add(linkTo(methodOn(ProductController.class).getById(prueba.getId())).withSelfRel());
        model.add(linkTo(methodOn(ProductController.class).getAll()).withRel("Listar todo"));
        model.add(linkTo(methodOn(ProductController.class).update(prueba.getId(),prueba)).withRel("Actualizar"));
        model.add(linkTo(methodOn(ProductController.class).delete(prueba.getId())).withRel("Eliminar"));
        
        if(prueba.getCategory() != null){
            model.add(linkTo(methodOn(ProductController.class).getByCategory(prueba.getCategory())).withRel("Buscar Categoria"));
        }
        return model;
    }

}
