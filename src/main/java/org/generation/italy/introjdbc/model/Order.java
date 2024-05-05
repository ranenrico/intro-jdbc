package org.generation.italy.introjdbc.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Order implements Comparable<Order> {
    private int id;
    private Customer customer;
    private LocalDate date;
    private LocalDate requiredDate;
    private LocalDate shippedDate;
    private double freight;
    private String shipName;
    private String shipAddress;
    private String shipCity;
    private String shipRegion;
    private String shipPostalCode;
    private String shipCountry;
    private Set<OrderLine> orderLines;
    
    
    public Order() {
        this.orderLines = new HashSet<>();
    }


    public Order(Customer customer, LocalDate date, LocalDate requiredDate, LocalDate shippedDate, double freight, String shipName, String shipAddress, String shipCity, String shipRegion, String shipPostalCode, String shipCountry) {
        this.customer = customer;
        this.date = date;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.freight = freight;
        this.shipName = shipName;
        this.shipAddress = shipAddress;
        this.shipCity = shipCity;
        this.shipRegion = shipRegion;
        this.shipPostalCode = shipPostalCode;
        this.shipCountry = shipCountry;
        this.orderLines = new HashSet<>();
    }


    public Order(LocalDate date, LocalDate requiredDate, LocalDate shippedDate, double freight, String shipName, String shipAddress, String shipCity, String shipRegion, String shipPostalCode, String shipCountry) {
        this(null, date, requiredDate, shippedDate, freight, shipName, shipAddress, shipCity, shipRegion, shipPostalCode, shipCountry);
        this.orderLines = new HashSet<>();
    }


    public int addOrderLine(OrderLine line){
        orderLines.add(line);
        return orderLines.size();
    }


    @Override
    public int compareTo(Order other) {
        if(this.date == null && other.date == null){
            return 0;
        }
        if(this.date == null && other.date != null){
            return 1; //se si ordina in asc i nulli verranno dopo
        }
        if(this.date != null && other.date == null){
            return -1; 
        }
        return other.date.compareTo(date); //ordine di date descrescente - date.compareTo(other.date) avrebbe restituito ordine crescente
    }

    @Override
    public boolean equals(Object o){
        if(o==null || o.getClass()!=getClass()){
            return false;
        }
        Order other = (Order)o;
        return this.id == other.id;
    }

    @Override
    public int hashCode(){
        return id;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public int getId() {
        return id;
    }


    public Customer getCustomer() {
        return customer;
    }


    public LocalDate getDate() {
        return date;
    }

    // public LocalDate getRequiredDate() {
    //     return requiredDate;
    // }


    // public LocalDate getShippedDate() {
    //     return shippedDate;
    // }


    // public double getFreight() {
    //     return freight;
    // }


    // public String getShipName() {
    //     return shipName;
    // }


    // public String getShipAddress() {
    //     return shipAddress;
    // }


    // public String getShipCity() {
    //     return shipCity;
    // }


    // public String getShipRegion() {
    //     return shipRegion;
    // }


    // public String getShipPostalCode() {
    //     return shipPostalCode;
    // }


    // public String getShipCountry() {
    //     return shipCountry;
    // }


    // public Set<OrderLine> getOrderLines() {
    //     return orderLines;
    // }

    
}
