package com.ecomarket.Product_Service.model;

import org.springframework.hateoas.RepresentationModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)


public class ProductModel extends RepresentationModel<ProductModel> {

    private Long id;

    private String name;

    private String description;

    private double price;

    private int stock;

    private String category;

}
