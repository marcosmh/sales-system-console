package com.mark.microsystem.sales.system.main.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record UserRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "Role is required")
        String role,

        @NotNull(message = "Active is required")
        Boolean active
) { }
