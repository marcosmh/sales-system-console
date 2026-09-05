package com.mark.microsystem.sales.system.main.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SaleDetailCreateRequest(

        @NotNull(message = "Product ID is required")
        @Min(value = 1, message = "Product ID must be greater than 0")
        Integer productId,

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity
) { }
