package com.mspr4.ordersapi.controller;

import com.mspr4.ordersapi.messaging.OrdersEventPublisher;
import com.mspr4.ordersapi.model.*;
import com.mspr4.ordersapi.service.OrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrdersControllerTest {

    @Mock
    private OrdersService service;

    @Mock
    private OrdersEventPublisher eventPublisher;

    @InjectMocks
    private OrdersController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById_shouldReturnOrder() {
        UUID id = UUID.randomUUID();
        Orders order = new Orders();
        order.setOrderId(id);

        when(service.getOrderById(id)).thenReturn(Optional.of(order));

        ResponseEntity<Orders> response = controller.getById(id);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(id, response.getBody().getOrderId());
    }

    @Test
    void create_shouldReturnCreatedOrder() {
        Orders order = new Orders();
        order.setOrderId(UUID.randomUUID());
        order.setCustomerId(UUID.randomUUID());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("pending");
        OrderItem item = new OrderItem();
        item.setProductId(UUID.randomUUID());
        item.setQuantity(2);


        order.setOrderItems(List.of(item));
        when(service.saveOrder(order)).thenReturn(order);

        ResponseEntity<Orders> response = controller.create(order);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(order.getOrderId(), response.getBody().getOrderId());

        verify(eventPublisher, times(1)).publishOrderCreated(order);
    }
}
