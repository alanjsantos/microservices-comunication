package com.alanjsantos.productapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStockDTO {

    private String salesID;
    private List<ProductQuantityDTO> products;
}