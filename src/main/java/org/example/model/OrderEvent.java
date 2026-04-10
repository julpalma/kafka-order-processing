package org.example.model;

public class OrderEvent {

    private String orderId;
    private String customerId;
    private String status;
    private double amount;

    public OrderEvent() {
    }

    public OrderEvent(String orderId, String customerId, String status, double amount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                '}';
    }
}