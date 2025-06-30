package com.ProyecOne.EcoMarketSpa.model;

import org.springframework.hateoas.RepresentationModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (callSuper = false)


public class InventoryModel extends RepresentationModel<InventoryModel> {

    private Long id;

    private Integer quantity;
    private String location;
}
