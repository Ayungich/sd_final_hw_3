package com.example.shop.orders.service;
import com.example.shop.orders.domain.Order;
import com.example.shop.orders.domain.OutboxEvent;
import com.example.shop.orders.dto.CreateOrderRequest;
import com.example.shop.orders.repository.OrderRepository;
import com.example.shop.orders.repository.OutboxEventRepository;
import com.example.shop.common.event.OrderCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final OutboxEventRepository outboxRepo;
    private final ObjectMapper objectMapper;

    public OrderService(OrderRepository orderRepo, OutboxEventRepository outboxRepo, ObjectMapper objectMapper) {
        this.orderRepo = orderRepo;
        this.outboxRepo = outboxRepo;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public Order createOrder(UUID userId, CreateOrderRequest dto) throws Exception {
        Order order = new Order();
        order.setUserId(userId);
        order.setAmount(dto.getAmount());
        order.setDescription(dto.getDescription());
        orderRepo.save(order);

        OutboxEvent oe = new OutboxEvent();
        oe.setAggregateType("Order");
        oe.setAggregateId(order.getId());
        oe.setEventType("OrderCreated");
        oe.setPayload(objectMapper.writeValueAsString(
                new OrderCreatedEvent(order.getId(), userId, order.getAmount(), order.getDescription())
        ));
        outboxRepo.save(oe);

        return order;
    }

    @Transactional(readOnly = true)
    public List<Order> listOrders(UUID userId) {
        return orderRepo.findAllByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Order getOrder(UUID userId, UUID orderId) {
        return orderRepo.findById(orderId)
                .filter(o -> o.getUserId().equals(userId))
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }
}