package com.ecomarket.Product_Service.controller;


import com.ecomarket.Product_Service.assembler.ProductModelAssembler;
import com.ecomarket.Product_Service.model.Product;
import com.ecomarket.Product_Service.model.ProductModel;
import com.ecomarket.Product_Service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import com.ecomarket.Product_Service.exception.ResourceNotFoundException;



import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductModelAssembler assembler;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Product producto) {
        try{
            producto.setId(null);
            Product newProduct= productService.create(producto);
            ProductModel newProductModel = assembler.toModel(newProduct);

            return ResponseEntity.created(newProductModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                 .body(newProductModel);

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el producto: " + e.getMessage());
        }
        

        
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try{
            Product prueba = productService.getById(id);
            ProductModel pruebaModel = assembler.toModel(prueba);
            return ResponseEntity.ok(pruebaModel);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prueba no encontrada:" + e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error interno: " + e.getMessage());
        }
        
        
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try{
            List<Product> productos = productService.getAll();
            List<ProductModel> productosModel = productos.stream().map(assembler::toModel).collect(Collectors.toList());
            CollectionModel<ProductModel> collectionModel = CollectionModel.of(productosModel);
            collectionModel.add(linkTo(methodOn(ProductController.class).getAll()).withSelfRel());

            return ResponseEntity.ok(collectionModel);

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener todos los productos: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductModel> update(@PathVariable Long id, @RequestBody Product product) {
        Product prueba = productService.update(id, product);
        if (prueba == null){
            return ResponseEntity.notFound().build();

        }
        return ResponseEntity.ok(assembler.toModel(prueba));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getByCategory(@PathVariable String category){
        
            
            List<Product> prueba = productService.getByCategory(category);

            List<ProductModel> pruebamodel = prueba.stream()
                    .map(assembler:: toModel)
                    .collect(Collectors.toList());
            
           if (prueba.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en la busqueda categoria no encontrada");
        }
            
            CollectionModel<ProductModel> collectionModel = CollectionModel.of(pruebamodel);
            collectionModel.add(linkTo(methodOn(ProductController.class).getByCategory(category)).withSelfRel());
            collectionModel.add(linkTo(methodOn(ProductController.class).getAll()).withRel("Todos los modelos"));

            return ResponseEntity.ok(collectionModel);
        
        

    }

        
    }

