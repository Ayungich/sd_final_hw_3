package com.example.shop.payments.controller;

import com.example.shop.payments.domain.Account;
import com.example.shop.payments.dto.CreateAccountRequest;
import com.example.shop.payments.dto.TopUpRequest;
import com.example.shop.payments.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Accounts", description = "Управление счетами")
public class AccountController {

    private final AccountService service;
    public AccountController(AccountService service) { this.service = service; }

    @Operation(summary = "Создать счёт")
    @PostMapping
    public ResponseEntity<Account> createAccount(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") UUID userId,
            @Valid @RequestBody CreateAccountRequest req) {
        return ResponseEntity.ok(service.createAccount(userId, req));
    }

    @Operation(summary = "Пополнить счёт")
    @PostMapping("/{id}/top-up")
    public ResponseEntity<Account> topUp(
            @Parameter(description = "ID пользователя") @PathVariable UUID id,
            @Valid @RequestBody TopUpRequest req) {
        return ResponseEntity.ok(service.topUp(id, req));
    }

    @Operation(summary = "Просмотр баланса")
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(
            @Parameter(description = "ID пользователя") @PathVariable UUID id) {
        return ResponseEntity.ok(service.getAccount(id));
    }
}