package org.generation.italy.introjdbc.model;

public class OrderLine {
    private Order order;
    private Product product;
    private int quantity;
    private double price;
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
    

    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setDiscount(double discount) {
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
