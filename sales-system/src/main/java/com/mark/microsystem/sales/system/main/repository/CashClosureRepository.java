package com.mark.microsystem.sales.system.main.repository;

import com.mark.microsystem.sales.system.main.model.entity.CashClosure;
import com.mark.microsystem.sales.system.main.model.entity.UserPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CashClosureRepository extends JpaRepository<CashClosure, Integer> {

    Optional<CashClosure> findByUsuarioAndFecha(
            UserPerson user,
            LocalDateTime fecha
    );

}
