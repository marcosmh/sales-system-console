package com.mark.microsystem.sales.system.main.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleDetailResponse(
        Integer id,

        ProductSummaryResponse product,

        Integer quantity,

        BigDecimal subtotal,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) { }
