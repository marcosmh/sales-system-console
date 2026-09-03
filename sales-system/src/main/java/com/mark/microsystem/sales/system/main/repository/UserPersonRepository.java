package com.mark.microsystem.sales.system.main.repository;

import com.mark.microsystem.sales.system.main.model.entity.UserPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPersonRepository extends JpaRepository<UserPerson, Integer> {

    Optional<UserPerson> findByUsername(String username);

    boolean existsByUsername(String username);
}
