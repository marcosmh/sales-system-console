package com.mark.microsystem.sales.system.main.model.dto;

import java.math.BigDecimal;

public record ProductSummaryResponse(
        Integer id,

        String name,

        BigDecimal price
) { }
