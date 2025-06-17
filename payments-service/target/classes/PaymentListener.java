package com.example.shop.payments.service;
import com.example.shop.common.event.OrderCreatedEvent;
import com.example.shop.common.event.PaymentStatusEvent;
import com.example.shop.payments.domain.InboxEvent;
import com.example.shop.payments.domain.OutboxEvent;
import com.example.shop.payments.repository.AccountRepository;
import com.example.shop.payments.repository.InboxEventRepository;
import com.example.shop.payments.repository.OutboxEventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Component
public class PaymentListener {
    private final InboxEventRepository inboxRepo;
    private final OutboxEventRepository outboxRepo;
    private final AccountRepository accountRepo;
    private final ObjectMapper mapper;
    private final KafkaTemplate<String, String> kafka;

    public PaymentListener(InboxEventRepository inboxRepo,
                           OutboxEventRepository outboxRepo,
                           AccountRepository accountRepo,
                           ObjectMapper mapper,
                           KafkaTemplate<String, String> kafka) {
        this.inboxRepo = inboxRepo;
        this.outboxRepo = outboxRepo;
        this.accountRepo = accountRepo;
        this.mapper = mapper;
        this.kafka = kafka;
    }

    @KafkaListener(topics = "ordercreated", groupId = "payments-group")
    @Transactional
    public void handleOrderCreated(String payload) throws Exception {
        OrderCreatedEvent event = mapper.readValue(payload, OrderCreatedEvent.class);
        UUID inboxId = UUID.randomUUID();
        if (inboxRepo.existsById(inboxId)) return;
        InboxEvent ie = new InboxEvent();
        ie.setId(inboxId);
        ie.setEventType("OrderCreated");
        ie.setAggregateId(event.getOrderId());
        inboxRepo.save(ie);

        // Process payment
        boolean success;
        var accOpt = accountRepo.findById(event.getUserId());
        if (accOpt.isEmpty() || accOpt.get().getBalance().compareTo(event.getAmount()) < 0) {
            success = false;
        } else {
            var acc = accOpt.get();
            acc.setBalance(acc.getBalance().subtract(event.getAmount()));
            accountRepo.save(acc);
            success = true;
        }

        // Emit status event
        OutboxEvent oe = new OutboxEvent();
        oe.setEventType("PaymentStatus");
        oe.setAggregateId(event.getOrderId());
        oe.setPayload(mapper.writeValueAsString(
                new PaymentStatusEvent(event.getOrderId(), event.getUserId(), success ? "SUCCESS" : "FAILED")
        ));
        outboxRepo.save(oe);
    }
}