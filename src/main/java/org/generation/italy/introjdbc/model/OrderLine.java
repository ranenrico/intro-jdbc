package org.generation.italy.introjdbc.model;

public class OrderLine {
    private int id;
    private int quantity;
    private double price;
    private double discount;
    
    public OrderLine() {
    }
    public OrderLine(int id, int quantity, double price, double discount) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }
    
    public int getId() {
        return id;
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
