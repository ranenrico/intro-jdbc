package org.generation.italy.introjdbc.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Order implements Comparable<Order>{
    private int id;
    private Customer customer;
    private LocalDate orderDate;
<<<<<<< HEAD
    private LocalDate requireDate;
    private LocalDate shipDate;
    private double freight;
    private String shipName;
    private String shipAdress;
=======
    private LocalDate requirDate;
    private LocalDate shipDate;
    private double freight;
    private String shipName;
    private String shipAddress;
>>>>>>> main
    private String shipCity;
    private String shipRegion;
    private String postalCode;
    private String shipCountry;
    private Set<OrderLine> orderLines;

<<<<<<< HEAD

=======
>>>>>>> main
    public Order() {
        this.orderLines = new HashSet<>();
    }

<<<<<<< HEAD

    public Order(Customer customer, LocalDate orderDate, LocalDate requireDate, LocalDate shipDate, double freight,
            String shipName, String shipAdress, String shipCity, String shipRegion, String postalCode,
            String shipCountry) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.requireDate = requireDate;
        this.shipDate = shipDate;
        this.freight = freight;
        this.shipName = shipName;
        this.shipAdress = shipAdress;
=======
    public Order(Customer customer, LocalDate orderDate, LocalDate requirDate, LocalDate shipDate, double freight,
            String shipName, String shipAddress, String shipCity, String shipRegion, String postalCode,
            String shipCountry) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.requirDate = requirDate;
        this.shipDate = shipDate;
        this.freight = freight;
        this.shipName = shipName;
        this.shipAddress = shipAddress;
>>>>>>> main
        this.shipCity = shipCity;
        this.shipRegion = shipRegion;
        this.postalCode = postalCode;
        this.shipCountry = shipCountry;
        this.orderLines = new HashSet<>();
    }

    public int addOrderLine(OrderLine line){
        orderLines.add(line);
        return orderLines.size();
    }

<<<<<<< HEAD

=======
>>>>>>> main
    public int getId() {
        return id;
    }

<<<<<<< HEAD

=======
>>>>>>> main
    public Customer getCustomer() {
        return customer;
    }

<<<<<<< HEAD

=======
>>>>>>> main
    public LocalDate getOrderDate() {
        return orderDate;
    }

<<<<<<< HEAD
    //comparazione delle date in modo decrescente, 
    //se avessi voluto fare in modo crescente avrei dovurto methere other. all'interno delle parentesi
=======
>>>>>>> main
    @Override
    public int compareTo(Order other) {
        return other.orderDate.compareTo(orderDate);
    }

    @Override
    public boolean equals(Object o){
<<<<<<< HEAD
        if(o == null || o.getClass() != getClass()){
            return false;
        }

=======
        if(o==null || o.getClass()!=getClass()){
            return false;
        }
>>>>>>> main
        Order other = (Order) o;
        return this.id == other.id;
    }

    @Override
    public int hashCode(){
        return id;
    }
<<<<<<< HEAD
    

    

    


=======
>>>>>>> main
}
