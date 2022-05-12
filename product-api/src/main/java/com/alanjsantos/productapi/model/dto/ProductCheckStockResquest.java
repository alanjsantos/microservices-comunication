package com.alanjsantos.productapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCheckStockResquest {
    private List<ProductQuantityDTO> products;
}
