package com.mark.microsystem.sales.system.main.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleSummaryResponse(
        Integer id,

        UserSummaryResponse user,

        LocalDateTime date,

        BigDecimal total
) {
}
