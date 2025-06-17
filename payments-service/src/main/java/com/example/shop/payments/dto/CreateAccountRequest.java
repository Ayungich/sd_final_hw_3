package com.example.shop.payments.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Schema(description = "Запрос на создание счёта")
public class CreateAccountRequest {
    @Schema(description = "Начальный баланс", example = "100.00", required = true)
    @PositiveOrZero
    private BigDecimal initialBalance;

    public BigDecimal getInitialBalance() { return initialBalance; }
    public void setInitialBalance(BigDecimal initialBalance) { this.initialBalance = initialBalance; }
}