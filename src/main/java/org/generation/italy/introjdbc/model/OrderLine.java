package org.generation.italy.introjdbc.model;

public class OrderLine {
    private Order order;
    private Product product;
    private double price;
    private int quantity;
    private double discount;
    
    public OrderLine() {
    }

    public OrderLine(Order order, Product product, double price, int quantity, double discount) {
        this.order = order;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }
    
}
