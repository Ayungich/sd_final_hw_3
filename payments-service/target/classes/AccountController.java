package com.example.shop.payments.controller;
import com.example.shop.payments.domain.Account;
import com.example.shop.payments.dto.CreateAccountRequest;
import com.example.shop.payments.dto.TopUpRequest;
import com.example.shop.payments.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService service;
    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(
            @RequestHeader("X-User-Id") UUID userId,
            @Validated @RequestBody CreateAccountRequest req) {
        return ResponseEntity.ok(service.createAccount(userId, req));
    }

    @PostMapping("/{id}/top-up")
    public ResponseEntity<Account> topUp(
            @PathVariable UUID id,
            @Validated @RequestBody TopUpRequest req) {
        return ResponseEntity.ok(service.topUp(id, req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getAccount(id));
    }
}