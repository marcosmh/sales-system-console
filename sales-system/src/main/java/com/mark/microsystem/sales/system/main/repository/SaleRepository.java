package com.mark.microsystem.sales.system.main.repository;

import com.mark.microsystem.sales.system.main.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Integer> {

}
