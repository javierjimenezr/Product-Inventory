package com.ecomarket.Product_Service.ControllerTest;

import com.ecomarket.Product_Service.controller.ProductController;
import com.ecomarket.Product_Service.model.Product;
import com.ecomarket.Product_Service.model.ProductModel;
import com.ecomarket.Product_Service.service.ProductService;
import com.ecomarket.Product_Service.assembler.ProductModelAssembler;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductModelAssembler assembler;

    @Autowired
    private ObjectMapper objectMapper;

    public static class Dummy extends ProductModel {
        public Dummy(Product product) {
            this.setId(product.getId());
            this.setName(product.getName());
            this.setDescription(product.getDescription());
            this.setPrice(product.getPrice());
            this.setStock(product.getStock());
            this.setCategory(product.getCategory());
            this.add(Link.of("http://localhost/api/products/" + product.getId()).withSelfRel());
        }
    }

 
    

    @Test
    @DisplayName("POST /api/products Crear un Producto")
    public void testCrearProducto() throws Exception {
        Product nuevo = new Product(null, "Audifonos", "Samsung Buds Pro", 75990, 50, "Electronicos");
        Product guardado = new Product(1L, "Audifonos", "Samsung Buds Pro", 75990, 50, "Electronicos");

        when(productService.create(any(Product.class))).thenReturn(guardado);
        when(assembler.toModel(any(Product.class))).thenReturn(new Dummy(guardado));

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Audifonos"))
            .andExpect(jsonPath("$.id").value(1));
    }

} 