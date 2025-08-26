package com.mspr4.ordersapi.controller;

import com.mspr4.ordersapi.model.Orders;
import com.mspr4.ordersapi.service.OrdersService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrdersControllerTest {

    OrdersService service = mock(OrdersService.class);
    OrdersController controller = new OrdersController(service);

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
        order.setTotalAmount(new BigDecimal("50.00"));
        order.setDeliveryAddress("123 Main St");
        order.setOrderItems("[{\"productId\":\"123\",\"quantity\":2,\"price\":10.5}]");

        when(service.saveOrder(order)).thenReturn(order);

        ResponseEntity<Orders> response = controller.create(order);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(order.getOrderId(), response.getBody().getOrderId());
    }
}
