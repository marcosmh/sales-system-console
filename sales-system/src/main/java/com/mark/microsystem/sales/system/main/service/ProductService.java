package com.mark.microsystem.sales.system.main.service;

import com.mark.microsystem.sales.system.main.exception.ResourceNotFoundException;
import com.mark.microsystem.sales.system.main.model.dto.ProductCreateRequest;
import com.mark.microsystem.sales.system.main.model.dto.ProductResponse;
import com.mark.microsystem.sales.system.main.model.dto.ProductUpdateRequest;
import com.mark.microsystem.sales.system.main.model.dto.SupplierResponse;
import com.mark.microsystem.sales.system.main.model.entity.Product;
import com.mark.microsystem.sales.system.main.model.entity.Supplier;
import com.mark.microsystem.sales.system.main.repository.ProductRepository;
import com.mark.microsystem.sales.system.main.repository.SupplierRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;


    @Override
    public ProductResponse createProducto(ProductCreateRequest request) {

        Supplier supplier = supplierRepository.findById(request.supplierId())
                .orElseThrow( () ->
                    new ResourceNotFoundException("Supplier not found." ));

        Product product = Product
                .builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .supplier(supplier)
                .build();

        Product productSave = productRepository.save(product);

        return toResponseProduct(productSave);
    }

    @Override
    public ProductResponse updateProducto(Integer id, ProductUpdateRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow( () ->
                        new ResourceNotFoundException("Product not found."));

        Supplier supplier = supplierRepository.findById(request.supplierId())
                .orElseThrow( () ->
                    new ResourceNotFoundException("Supplier not found."));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setSupplier(supplier);

        Product updatedProduct = productRepository.save(product);

        return toResponseProduct(updatedProduct);
    }

    @Override
    public void deleteProducto(Integer id) {

        if( !productRepository.existsById(id) ) {
            throw new ResourceNotFoundException("Product not found.");
        }

        productRepository.deleteById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> listProductos() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponseProduct)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductoById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow( () ->
                        new ResourceNotFoundException("Product not found.") );
        return toResponseProduct(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findByIdAndName(Integer id, String name) {
        Product product = productRepository.findByIdAndName(id, name)
                .orElseThrow( () ->
                        new ResourceNotFoundException("Product not found.") );
        return toResponseProduct(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findByNameAndDescription(String name, String description) {
        Product product = productRepository.findByNameAndDescription(name, description)
                .orElseThrow( () ->
                        new ResourceNotFoundException("Product not found.") );
        return toResponseProduct(product);
    }

    private ProductResponse toResponseProduct(Product product) {

        Supplier supplier = product.getSupplier();

        SupplierResponse supplierResponse =
                supplier == null ? null : new SupplierResponse(
                            supplier.getId(),
                            supplier.getName(),
                            supplier.getContact(),
                            supplier.getPhone(),
                            supplier.getEmail(),
                            supplier.getCreatedAt(),
                            supplier.getUpdatedAt()
                            );

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                supplierResponse,
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }



}
