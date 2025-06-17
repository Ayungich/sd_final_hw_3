package com.example.shop.orders.controller;

import com.example.shop.orders.domain.Order;
import com.example.shop.orders.dto.CreateOrderRequest;
import com.example.shop.orders.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Управление заказами")
public class OrderController {

    private final OrderService service;
    public OrderController(OrderService service) { this.service = service; }

    @Operation(summary = "Создать заказ")
    @ApiResponse(responseCode = "200", description = "Заказ создан")
    @PostMapping
    public ResponseEntity<Order> createOrder(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") UUID userId,
            @Valid @RequestBody CreateOrderRequest dto) throws Exception {
        return ResponseEntity.ok(service.createOrder(userId, dto));
    }

    @Operation(summary = "Список заказов")
    @GetMapping
    public ResponseEntity<List<Order>> listOrders(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(service.listOrders(userId));
    }

    @Operation(summary = "Статус заказа")
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") UUID userId,
            @PathVariable UUID id) {
        return ResponseEntity.ok(service.getOrder(userId, id));
    }
}