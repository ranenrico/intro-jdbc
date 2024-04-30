package org.generation.italy.introjdbc.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Order implements Comparable<Order>{
    private int id;
    private Customer customer;
    //private int empid->al momento non ho impiegato
    private LocalDate orderDate;
    private LocalDate requirDate;
    private LocalDate shipDate;
    //private shipperid
    private double freight;
    private String shipName;
    private String shipAddress;
    private String shipCity;
    private String shipRegion;
    private String postalCode;
    private String shipCountry;
    private Set<OrderLine> orderLines;
    public Order() {
        this.orderLines=new HashSet();
    }
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
        this.shipCity = shipCity;
        this.shipRegion = shipRegion;
        this.postalCode = postalCode;
        this.shipCountry = shipCountry;
        this.orderLines=new HashSet();
    }
    public int addOrderLine(OrderLine line){
        orderLines.add(line);
        return orderLines.size();
    }
    public int getId() {
        return id;
    }
    public Customer getCustomer() {
        return customer;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
    @Override
    public int compareTo(Order other) {
       return other.orderDate.compareTo(orderDate);
    }
    @Override
    public boolean equals(Object o){
        if(o==null ||o.getClass()!=this.getClass()){
            return false;
        }
        Order other=(Order) o;
        return this.id==other.id;
    }
    @Override
    public int hashCode(){
        return id;
    }

}
