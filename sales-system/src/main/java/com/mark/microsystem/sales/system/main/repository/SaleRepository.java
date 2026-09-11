package com.mark.microsystem.sales.system.main.repository;

import com.mark.microsystem.sales.system.main.model.entity.Sale;
import com.mark.microsystem.sales.system.main.model.entity.UserPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface SaleRepository extends JpaRepository<Sale, Integer> {

    @Query("""
            SELECT COALESCE(SUM(s.total), 0)
            FROM Sale s
            WHERE s.user = :user
              AND s.date >= :start
              AND s.date < :end
            """)
    BigDecimal sumTotalByUserAndDateBetween(
            @Param("user") UserPerson user,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );


}
