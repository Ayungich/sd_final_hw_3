package com.example.shop.payments.repository;

import com.example.shop.payments.domain.InboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface InboxEventRepository extends JpaRepository<InboxEvent, UUID> { }