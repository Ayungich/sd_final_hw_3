package com.example.shop.orders.controller;
import com.example.shop.orders.domain.Order;
import com.example.shop.orders.dto.CreateOrderRequest;
import com.example.shop.orders.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;
    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestHeader("X-User-Id") UUID userId,
            @Validated @RequestBody CreateOrderRequest dto) throws Exception {
        return ResponseEntity.ok(service.createOrder(userId, dto));
    }

    @GetMapping
    public ResponseEntity<List<Order>> listOrders(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(service.listOrders(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@RequestHeader("X-User-Id") UUID userId,
                                          @PathVariable UUID id) {
        return ResponseEntity.ok(service.getOrder(userId, id));
    }
}