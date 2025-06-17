package com.example.shop.orders.service;

import com.example.shop.orders.domain.OutboxEvent;
import com.example.shop.orders.repository.OutboxEventRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OutboxPublisher {
    private final OutboxEventRepository repo;
    private final KafkaTemplate<String, String> kafka;

    public OutboxPublisher(OutboxEventRepository repo, KafkaTemplate<String, String> kafka) {
        this.repo = repo;
        this.kafka = kafka;
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publish() {
        List<OutboxEvent> events = repo.findBySentFalse();
        for (OutboxEvent e : events) {
            kafka.send(e.getEventType().toLowerCase(), e.getPayload());
            e.setSent(true);
            repo.save(e);
        }
    }
}