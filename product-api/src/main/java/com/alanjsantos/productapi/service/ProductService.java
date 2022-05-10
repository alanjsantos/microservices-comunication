package com.alanjsantos.productapi.service;

import com.alanjsantos.productapi.model.Product;
import com.alanjsantos.productapi.model.dto.ProductQuantityDTO;
import com.alanjsantos.productapi.model.dto.ProductStockDTO;
import com.alanjsantos.productapi.repository.ProductRepository;
import com.alanjsantos.productapi.sales.dto.SalesConfirmationDTO;
import com.alanjsantos.productapi.sales.enums.SalesStatus;
import com.alanjsantos.productapi.sales.rabbitmq.SalesConfirmationSender;
import com.alanjsantos.productapi.service.exception.DataIntegrityException;
import com.alanjsantos.productapi.service.exception.ObjectNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SalesConfirmationSender salesConfirmationSender;

    public Product save (Product product) {
        var sup = supplierService.getById(product.getSupplier().getId());
        var cat = categoryService.getById(product.getCategory().getId());

        if (product.getQuantityAvailable() <= 0) {
            throw new DataIntegrityException("The quantity should not be less or equal to zero");
        }
        product.setCategory(cat);
        product.setSupplier(sup);
        return productRepository.save(product);
    }

    public List<Product> getAll () {
        return productRepository.findAll();
    }

    public Product getId(Long id) {
        var obj =
                productRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("ID " + "This ID not aready exists in the Database"));
    }

    public List<Product> getName (String name) {
        var list = productRepository.findByNameIgnoreCaseContaining(name);

        return list;
    }

    public List<Product> getBySupplierId(Long id) {
        var list = productRepository.findBySupplierId(id);
        if (list.isEmpty()) {
            throw new ObjectNotFoundException("This SupplierId " + id + " not aready exists in the Database");
        }
        return list;
    }

    public List<Product> getByCategoryId(Long id) {
        return productRepository.findByCategoryId(id);
    }

    public Boolean existsBySupplierId(Long id) {
        return productRepository.existsBySupplierId(id);
    }

    public Boolean existsByCategoryId(Long id) {
        return productRepository.existsByCategoryId(id);
    }

    public Product update(Product product) {
        getId(product.getId());
        var sup = supplierService.getById(product.getSupplier().getId());
        var cat = categoryService.getById(product.getCategory().getId());
        if (product.getQuantityAvailable() <= 0) {
            throw new DataIntegrityException("The quantity should not be less or equal to zero");
        }
        product.setCategory(cat);
        product.setSupplier(sup);

        return productRepository.save(product);
    }

    public Product delete (Long id) {
         var product = getId(id);
         productRepository.deleteById(id);

        return product;
    }

    public void updateProductStock(ProductStockDTO dto) throws JsonProcessingException {
        try {
            validateStockUpdateData(dto);
            updateSotck(dto);
        } catch (Exception e) {
            log.error("Error while trying to update sotkc for mesage with error: {}", e.getMessage(), e);
            var rejectdMessage = new SalesConfirmationDTO(dto.getSalesId(), SalesStatus.REJECTED);
            salesConfirmationSender.sendSalesConfirmationMessage(rejectdMessage);
        }
    }
    //fluxo de atualizacao e devolucao da respota

    private void validateStockUpdateData(ProductStockDTO dto) {
        if (isEmpty(dto) || isEmpty(dto.getSalesId())) {
            throw new DataIntegrityException("The Product data and sales ID must be informed.");
        }
        if (isEmpty(dto.getProducts())) {
            throw new DataIntegrityException("The sales products must be informed.");
        }
        dto.getProducts()
                .forEach( salesProduct -> {
                    if (isEmpty(salesProduct.getQuantity()) || isEmpty(salesProduct.getProductId())) {
                        throw new DataIntegrityException("The productID and the quantity must be informed");
                    }
                });
    }

    private void updateSotck (ProductStockDTO dto) {
        dto.getProducts()
                .forEach(salesProduct -> {
                    var existsProduct = getId(salesProduct.getProductId());
                    validateQuantityInStock(salesProduct, existsProduct);
                    existsProduct.updateStock(salesProduct.getQuantity());
                    productRepository.save(existsProduct);
                    var approvedMessage = new SalesConfirmationDTO(dto.getSalesId(), SalesStatus.APPROVED);
                    try {
                        salesConfirmationSender.sendSalesConfirmationMessage(approvedMessage);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void validateQuantityInStock(ProductQuantityDTO salesProduct,
                                         Product existsProduct) {
        if (salesProduct.getQuantity() > existsProduct.getQuantityAvailable()) {
            throw new DataIntegrityException(String.format("The product %s is out of stock.", existsProduct.getId()));
        }
    }

}
