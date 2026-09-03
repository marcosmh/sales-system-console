package com.mark.microsystem.sales.system.main.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Integer id,

        String name,

        String description,

        BigDecimal price,

        Integer stock,

        SupplierResponse supplier,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) { }
