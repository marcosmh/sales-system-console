package com.mark.microsystem.sales.system.main.service;

import com.mark.microsystem.sales.system.main.model.dto.SupplierCreateRequest;
import com.mark.microsystem.sales.system.main.model.dto.SupplierResponse;
import com.mark.microsystem.sales.system.main.model.dto.SupplierUpdateRequest;

import java.util.List;

public interface ISupplier {

    SupplierResponse createSupplier(SupplierCreateRequest request);

    SupplierResponse updateProveedor(Integer id, SupplierUpdateRequest request);

    void deleteProveedor(Integer id);

    List<SupplierResponse> listProveedores();

    SupplierResponse getProveedorById(Integer id);

    SupplierResponse findByName(String name);

    boolean existsByName(String name);
}
