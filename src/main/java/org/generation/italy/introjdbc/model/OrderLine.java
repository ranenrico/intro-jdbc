package org.generation.italy.introjdbc.model;

public class OrderLine {
    private Order order;
    private Product product;
    private double price;
    private int quantity;
    private double discount;
    public OrderLine() {
    }
    public OrderLine( Order order, Product product,double price,int quantity,  double discount) {
        this.order=order;
        this.product=product;
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
    public void setUnitPrice(double price) {
       this.price=price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
   
    @Override
    public String toString(){
        return String.format("OrderLine [order: %d,productid: %d,quantity: %d, price:%f,  discount: %f ]",
                            order.getId(),product.getId(),quantity,price,discount);
    }
}
// }return String.format("OrderLine [order: %d,productid: %d quantity: %d price:%f discount: %f",
// order.getId(),product.getId(),quantity,price,discount);
