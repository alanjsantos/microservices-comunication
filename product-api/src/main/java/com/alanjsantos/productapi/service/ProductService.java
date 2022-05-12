package com.alanjsantos.productapi.service;

import com.alanjsantos.productapi.controller.exception.ErrorGenericException;
import com.alanjsantos.productapi.controller.exception.SuccessResponse;
import com.alanjsantos.productapi.model.Product;
import com.alanjsantos.productapi.model.dto.ProductCheckStockResquest;
import com.alanjsantos.productapi.model.dto.ProductQuantityDTO;
import com.alanjsantos.productapi.model.dto.ProductSalesResponse;
import com.alanjsantos.productapi.model.dto.ProductStockDTO;
import com.alanjsantos.productapi.repository.ProductRepository;
import com.alanjsantos.productapi.sales.client.SalesClient;
import com.alanjsantos.productapi.sales.dto.SalesConfirmationDTO;
import com.alanjsantos.productapi.sales.dto.SalesProductResponseDTO;
import com.alanjsantos.productapi.sales.enums.SalesStatus;
import com.alanjsantos.productapi.sales.rabbitmq.SalesConfirmationSender;
import com.alanjsantos.productapi.service.exception.DataIntegrityException;
import com.alanjsantos.productapi.service.exception.ObjectNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Autowired
    private SalesClient salesClient;

    @Autowired
    private ModelMapper modelMapper;

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

    @Transactional
    private void updateSotck (ProductStockDTO dto) throws JsonProcessingException {
        var productsForUpdate = new ArrayList<Product>();
        dto.getProducts()
                .forEach(salesProduct -> {
                    var existsProduct = getId(salesProduct.getProductId());
                    validateQuantityInStock(salesProduct, existsProduct);
                    existsProduct.updateStock(salesProduct.getQuantity());
                    productsForUpdate.add(existsProduct);
                });
        if (!isEmpty(productsForUpdate)) {
            productRepository.saveAll(productsForUpdate);
            var approvedMessage = new SalesConfirmationDTO(dto.getSalesId(), SalesStatus.APPROVED);
            salesConfirmationSender.sendSalesConfirmationMessage(approvedMessage);
        }
    }

    private void validateQuantityInStock(ProductQuantityDTO salesProduct,
                                         Product existsProduct) {
        if (salesProduct.getQuantity() > existsProduct.getQuantityAvailable()) {
            throw new DataIntegrityException(String.format("The product %s is out of stock.", existsProduct.getId()));
        }
    }

    public ProductSalesResponse findProductSales (Long id) {
        var product = getId(id);
        try {
            salesClient.getSalesByProductId(product.getId()).orElseThrow(() ->
                    new ObjectNotFoundException("The sales was not found by this product."));
            return modelMapper.map(product, ProductSalesResponse.class);
        } catch (Exception e) {
            throw new DataIntegrityException("There was an error trying to get the product's sales.");
        }
    }

    public SuccessResponse checkProductStock(ProductCheckStockResquest resquest) {
        if (isEmpty(resquest) || isEmpty(resquest.getProducts())) {
            throw new DataIntegrityException("The request data must be informed.");
        }
        resquest.getProducts()
                .forEach(this::validateStock);
        return SuccessResponse.create("The stock is OK!");
    }

    private void validateStock (ProductQuantityDTO productQuantityDTO) {
        if (isEmpty(productQuantityDTO.getProductId()) || isEmpty(productQuantityDTO.getQuantity())) {
            throw new DataIntegrityException("Product ID and quantity must be informed.");
        }
        var product = getId(productQuantityDTO.getProductId());
        if (productQuantityDTO.getQuantity() > product.getQuantityAvailable()) {
            throw new DataIntegrityException(String.format("The product %s is out of stck", product.getId()));
        }
    }

}
