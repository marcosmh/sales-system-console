package com.mark.microsystem.sales.system.main.service;

import com.mark.microsystem.sales.system.main.exception.ResourceNotFoundException;
import com.mark.microsystem.sales.system.main.model.dto.SupplierCreateRequest;
import com.mark.microsystem.sales.system.main.model.dto.SupplierResponse;
import com.mark.microsystem.sales.system.main.model.dto.SupplierUpdateRequest;
import com.mark.microsystem.sales.system.main.model.entity.Supplier;
import com.mark.microsystem.sales.system.main.repository.SupplierRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierService implements ISupplier {

    private final SupplierRepository supplierRepository;

    @Override
    public SupplierResponse createSupplier(SupplierCreateRequest request) {

        Supplier supplier = Supplier.builder()
                .name(request.name())
                .contact(request.contact())
                .phone(request.phone())
                .email(request.email())
                .build();

        Supplier savedSupplier = supplierRepository.save(supplier);

        return toResponseSupplier(savedSupplier);
    }

    @Override
    public SupplierResponse updateSupplier(Integer id, SupplierUpdateRequest request) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow( () ->
                        new ResourceNotFoundException("Supplier not found." ));

        supplier.setName(request.name());
        supplier.setContact(request.contact());
        supplier.setPhone(request.phone());
        supplier.setEmail(request.email());

        Supplier updatedSupplier = supplierRepository.save(supplier);

        return toResponseSupplier(updatedSupplier);
    }

    @Override
    public void deleteSupplier(Integer id) {
        if( !supplierRepository.existsById(id) ) {
            throw new ResourceNotFoundException("Supplier not found.");
        }
        supplierRepository.deleteById(id);
    }

    @Override
    public List<SupplierResponse> listSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(this::toResponseSupplier)
                .toList();
    }

    @Override
    public SupplierResponse getSupplierById(Integer id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow( () ->
                        new ResourceNotFoundException("Supplier not found.") );

        return toResponseSupplier(supplier);
    }

    @Override
    public SupplierResponse findByName(String name) {
        Supplier supplier = supplierRepository.findByName(name)
                .orElseThrow( () ->
                        new ResourceNotFoundException("Supplier not found.") );

        return toResponseSupplier(supplier);
    }

    @Override
    public boolean existsByName(String name) {
        return supplierRepository.existsByName(name);
    }

    private SupplierResponse toResponseSupplier(Supplier supplier) {

        return new SupplierResponse(
                supplier.getId(),
                supplier.getName(),
                supplier.getContact(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getCreatedAt(),
                supplier.getUpdatedAt()
        );
    }


}
