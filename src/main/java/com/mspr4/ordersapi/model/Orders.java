package com.mspr4.ordersapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue
    private UUID orderId;

    @Column(nullable = false)
    @NotNull
    private UUID customerId;

    @Column(nullable = false)
    @NotNull
    private LocalDateTime orderDate;

    @Column(nullable = false, length = 50)
    @NotBlank
    private String status;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull
    private BigDecimal totalAmount;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotBlank
    private String deliveryAddress;

    @Column(nullable = false, columnDefinition = "JSONB")
    @NotBlank
    private String orderItems; // JSON array : [{"productId": "...", "quantity": 2, "price": 10.50}, ...]

    // Getters & Setters
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }

    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) { this.customerId = customerId; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public String getOrderItems() { return orderItems; }
    public void setOrderItems(String orderItems) { this.orderItems = orderItems; }
}
