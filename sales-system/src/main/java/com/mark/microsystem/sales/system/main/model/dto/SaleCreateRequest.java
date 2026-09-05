package com.mark.microsystem.sales.system.main.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SaleCreateRequest(
        @NotNull(message = "User ID is required")
        @Min(value = 1, message = "User ID must be greater than 0")
        Integer userId,

        @NotEmpty(message = "Sale must contain at least one detail")
        List<@Valid SaleDetailCreateRequest> details
) {
}
