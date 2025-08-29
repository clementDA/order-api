package com.mspr4.ordersapi.messaging;

import com.mspr4.ordersapi.model.Orders;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrdersEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public OrdersEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishOrderCreated(Orders order) {
        rabbitTemplate.convertAndSend("orders-exchange", "orders.created", order);
    }

    public void publishOrderUpdated(Orders order) {
        rabbitTemplate.convertAndSend("orders-exchange", "orders.updated", order);
    }

    public void publishOrderDeleted(Orders order) {
        rabbitTemplate.convertAndSend("orders-exchange", "orders.deleted", order);
    }
}
