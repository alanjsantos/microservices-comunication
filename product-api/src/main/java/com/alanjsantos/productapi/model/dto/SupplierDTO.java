package com.alanjsantos.productapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {

    private Long id;

    @NotBlank(message = "The Supplier description was not informed.")
    private String name;
}
