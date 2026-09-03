package com.mark.microsystem.sales.system.main.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SupplierCreateRequest(

        @NotBlank(message = "Name is required")
        @Size(max = 100, message = "Name cannot exceed 100 characters")
        String name,

        @Size(max = 100, message = "Contact cannot exceed 100 characters")
        String contact,

        @Size(max = 14, message = "Phone cannot exceed 14 characters")
        @Pattern(regexp = "^\\+52 \\d{10}$", message = "Phone must have the format +52 9999999999")
        String phone,

        @Email(message = "Email must be valid")
        @Size(max = 100, message = "Email cannot exceed 100 characters")
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                message = "Email must have the format person@domain.com"
        )
        String email
) { }
