package com.example.shop.common.event;

import java.util.UUID;

/**
 * Событие статуса оплаты заказа для уведомления сервиса заказов.
 */
public class PaymentStatusEvent {
    private final UUID eventId = UUID.randomUUID();
    private UUID orderId;
    private UUID userId;
    private String status; // SUCCESS или FAILED

    public PaymentStatusEvent() { }

    public PaymentStatusEvent(UUID orderId, UUID userId, String status) {
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
    }

    public UUID getEventId() { return eventId; }
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}