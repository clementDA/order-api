package com.mspr4.ordersapi.controller;

import com.mspr4.ordersapi.messaging.OrdersEventPublisher;
import com.mspr4.ordersapi.model.Orders;
import com.mspr4.ordersapi.service.OrdersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    private final OrdersService service;
    private final OrdersEventPublisher eventPublisher;

    public OrdersController(OrdersService service, OrdersEventPublisher eventPublisher) {
        this.service = service;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping
    public List<Orders> getAll() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getById(@PathVariable UUID id) {
        return service.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Orders> create(@Valid @RequestBody Orders order) {
        Orders saved = service.saveOrder(order);
        eventPublisher.publishOrderCreated(saved); // publication de l'événement
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orders> update(@PathVariable UUID id,
                                         @Valid @RequestBody Orders order) {
        return service.getOrderById(id)
                .map(existing -> {
                    existing.setCustomerId(order.getCustomerId());
                    existing.setOrderDate(order.getOrderDate());
                    existing.setStatus(order.getStatus());
                    existing.setOrderItems(order.getOrderItems());
                    Orders updated = service.saveOrder(existing);
                    eventPublisher.publishOrderUpdated(updated); // publication
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        var existing = service.getOrderById(id);
        if (existing.isPresent()) {
            service.deleteOrder(id);
            eventPublisher.publishOrderDeleted(existing.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
