package com.alanjsantos.productapi.sales.client;

import com.alanjsantos.productapi.sales.dto.SalesProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "salesClient",
            contextId = "salesClient",
            url = "${app-config.services.sales}")
public interface SalesClient {

    @GetMapping("products/{productId}")
    Optional<SalesProductResponseDTO> getSalesByProductId(@PathVariable Long productId);
}
