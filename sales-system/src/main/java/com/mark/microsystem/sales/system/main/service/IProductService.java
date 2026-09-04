package com.mark.microsystem.sales.system.main.service;

import com.mark.microsystem.sales.system.main.model.dto.ProductCreateRequest;
import com.mark.microsystem.sales.system.main.model.dto.ProductResponse;
import com.mark.microsystem.sales.system.main.model.dto.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    ProductResponse createProducto(ProductCreateRequest request);

    ProductResponse updateProducto(Integer id, ProductUpdateRequest request);

    void deleteProducto(Integer id);

    List<ProductResponse> listProductos();

    ProductResponse getProductoById(Integer id);

    ProductResponse findByIdAndName(Integer id, String name);

    ProductResponse findByNameAndDescription(String name, String description);


}
