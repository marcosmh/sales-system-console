package com.mark.microsystem.sales.system.main.model.dto;

import java.time.LocalDateTime;

public record SupplierResponse(
        Integer id,

        String name,

        String contact,

        String phone,

        String email,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) { }
