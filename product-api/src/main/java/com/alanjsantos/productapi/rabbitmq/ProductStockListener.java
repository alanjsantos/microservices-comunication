package com.alanjsantos.productapi.rabbitmq;

import com.alanjsantos.productapi.model.dto.ProductStockDTO;
import com.alanjsantos.productapi.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductStockListener {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    public void recieveProductStockMessage(ProductStockDTO dto) {
        productService.updateProductStock(dto);
    }

}
