package com.mark.microsystem.sales.system.main.repository;

import com.mark.microsystem.sales.system.main.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

   Optional<Product> findByIdAndName(Integer id, String name);

   Optional<Product> findByName(String name);

   Optional<Product> findByNameAndSupplier(String name, Integer idSupplier);

   Optional<Product> findByNameAndDescription(String name, String description);

}
