package com.mark.microsystem.sales.system.main.repository;

import com.mark.microsystem.sales.system.main.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    Optional<Supplier> findByName(String name);

    boolean existsByName(String name);

}
