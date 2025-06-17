package com.example.shop.payments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Schema(description = "Запрос на пополнение счёта")
public class TopUpRequest {
    @Schema(description = "Сумма пополнения", example = "50.00", required = true)
    @Positive
    private BigDecimal amount;

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}