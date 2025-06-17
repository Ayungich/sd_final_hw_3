package com.example.shop.common.event;

import java.util.UUID;
import java.math.BigDecimal;

/**
 * Событие создания заказа для межсервисного взаимодействия.
 */
public class OrderCreatedEvent {
    private final UUID eventId = UUID.randomUUID();
    private UUID orderId;
    private UUID userId;
    private BigDecimal amount;
    private String description;

    public OrderCreatedEvent() { }

    public OrderCreatedEvent(UUID orderId, UUID userId, BigDecimal amount, String description) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.description = description;
    }

    public UUID getEventId() { return eventId; }
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}