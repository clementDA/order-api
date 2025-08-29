package com.mspr4.ordersapi.service;

import com.mspr4.ordersapi.model.*;
import com.mspr4.ordersapi.repository.OrdersRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrdersServiceTest {

    OrdersRepository repository = mock(OrdersRepository.class);
    OrdersService service = new OrdersService(repository);

    @Test
    void saveOrder_shouldReturnSavedOrder() {
        Orders order = new Orders();
        order.setOrderId(UUID.randomUUID());
        order.setCustomerId(UUID.randomUUID());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("pending");

        OrderItem item = new OrderItem();
        item.setProductId(UUID.randomUUID());
        item.setQuantity(2);

// On met la liste dans la commande
        order.setOrderItems(List.of(item));

        when(repository.save(order)).thenReturn(order);

        Orders saved = service.saveOrder(order);
        assertEquals(order.getOrderId(), saved.getOrderId());
        verify(repository, times(1)).save(order);
    }

    @Test
    void getOrderById_shouldReturnOrder() {
        UUID id = UUID.randomUUID();
        Orders order = new Orders();
        order.setOrderId(id);

        when(repository.findById(id)).thenReturn(Optional.of(order));

        Optional<Orders> found = service.getOrderById(id);
        assertTrue(found.isPresent());
        assertEquals(id, found.get().getOrderId());
    }
}
