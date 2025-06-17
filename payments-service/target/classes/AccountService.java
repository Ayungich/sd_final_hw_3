package com.example.shop.payments.service;
import com.example.shop.payments.domain.Account;
import com.example.shop.payments.dto.CreateAccountRequest;
import com.example.shop.payments.dto.TopUpRequest;
import com.example.shop.payments.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository repo;
    public AccountService(AccountRepository repo) { this.repo = repo; }

    @Transactional
    public Account createAccount(UUID userId, CreateAccountRequest req) {
        Account acc = new Account();
        acc.setUserId(userId);
        acc.setBalance(req.getInitialBalance());
        return repo.save(acc);
    }

    @Transactional
    public Account topUp(UUID userId, TopUpRequest req) {
        Account acc = repo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        acc.setBalance(acc.getBalance().add(req.getAmount()));
        return repo.save(acc);
    }

    @Transactional(readOnly = true)
    public Account getAccount(UUID userId) {
        return repo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }
}