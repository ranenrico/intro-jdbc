package org.generation.italy.introjdbc.model;

public class OrderLine {
<<<<<<< HEAD

    private int id;
    private int quantity;
=======
    private Order order;
    private Product product;
    //lasciamo perdere product id 
>>>>>>> main
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
<<<<<<< HEAD
=======
    public void setUnitPrice(double price) {
       this.price=price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
   
>>>>>>> main
}
