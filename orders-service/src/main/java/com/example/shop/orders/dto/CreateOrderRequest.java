package com.example.shop.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Schema(description = "Запрос на создание заказа")
public class CreateOrderRequest {
    @Schema(description = "Сумма заказа", example = "120.00", required = true)
    @NotNull @Positive
    private BigDecimal amount;

    @Schema(description = "Описание заказа", example = "Летние шлёпанцы")
    private String description;

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}