package com.mark.microsystem.sales.system.main.service;

import com.mark.microsystem.sales.system.main.model.dto.SupplierCreateRequest;
import com.mark.microsystem.sales.system.main.model.dto.SupplierResponse;
import com.mark.microsystem.sales.system.main.model.dto.SupplierUpdateRequest;

import java.util.List;

public interface ISupplier {

    SupplierResponse createSupplier(SupplierCreateRequest request);

    SupplierResponse updateSupplier(Integer id, SupplierUpdateRequest request);

    void deleteSupplier(Integer id);

    List<SupplierResponse> listSuppliers();

    SupplierResponse getSupplierById(Integer id);

    SupplierResponse findByName(String name);

    boolean existsByName(String name);
}
