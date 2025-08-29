package com.mspr4.ordersapi.model;

import java.util.UUID;

public class OrderItem {
    private UUID productId;
    private int quantity;

    public OrderItem() {}

    public OrderItem(UUID productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}