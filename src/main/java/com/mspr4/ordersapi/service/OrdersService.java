package com.mspr4.ordersapi.service;

import com.mspr4.ordersapi.model.Orders;
import com.mspr4.ordersapi.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrdersService {

    private final OrdersRepository repository;

    public OrdersService(OrdersRepository repository) {
        this.repository = repository;
    }

    public List<Orders> getAllOrders() {
        return repository.findAll();
    }

    public Optional<Orders> getOrderById(UUID id) {
        return repository.findById(id);
    }

    public Orders saveOrder(Orders order) {
        return repository.save(order);
    }

    public void deleteOrder(UUID id) {
        repository.deleteById(id);
    }
}
