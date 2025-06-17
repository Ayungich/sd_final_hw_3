package com.example.shop.orders.service;

import com.example.shop.common.event.PaymentStatusEvent;
import com.example.shop.orders.domain.InboxEvent;
import com.example.shop.orders.domain.Order;
import com.example.shop.orders.domain.OrderStatus;
import com.example.shop.orders.repository.InboxEventRepository;
import com.example.shop.orders.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaymentStatusListener {

    private final InboxEventRepository inboxRepo;
    private final OrderRepository orderRepo;
    private final ObjectMapper mapper;

    public PaymentStatusListener(InboxEventRepository inboxRepo,
                                 OrderRepository orderRepo,
                                 ObjectMapper mapper) {
        this.inboxRepo = inboxRepo;
        this.orderRepo = orderRepo;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "paymentstatus", groupId = "orders-listener")
    @Transactional
    public void onPaymentStatus(String payload) throws Exception {
        // десериализуем событие
        PaymentStatusEvent evt = mapper.readValue(payload, PaymentStatusEvent.class);
        // idempotency: проверяем, не обрабатывали ли уже такое событие
        if (inboxRepo.existsById(evt.getEventId())) {
            return;
        }
        // записываем в inbox
        InboxEvent ie = new InboxEvent();
        ie.setId(evt.getEventId());
        ie.setEventType("PaymentStatus");
        ie.setAggregateId(evt.getOrderId());
        inboxRepo.save(ie);

        // обновляем статус заказа
        Order order = orderRepo.findById(evt.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(
                "SUCCESS".equals(evt.getStatus())
                        ? OrderStatus.FINISHED
                        : OrderStatus.CANCELLED
        );
        orderRepo.save(order);
    }
}
