package org.generation.italy.introjdbc.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Order implements Comparable<Order>{
    private int id;
    private Customer customer;
    //private int empid->al momento non ho impiegato
    private LocalDate orderDate;
    private LocalDate requiredDate;
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
        this.orderLines = new HashSet();
        //this.orderDate=LocalDate.now();
    }

    public Order(Customer customer, LocalDate orderDate, LocalDate requiredDate, LocalDate shipDate, double freight,
            String shipName, String shipAddress, String shipCity, String shipRegion, String postalCode,
            String shipCountry) {
        this.customer = customer;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
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
    
    public Order(LocalDate orderDate, LocalDate requiredDate, LocalDate shipDate, double freight, String shipName, String shipAddress,
            String shipCity, String shipRegion, String postalCode, String shipCountry) {
            this(null,orderDate,requiredDate,shipDate,freight,shipName,shipAddress,shipCity,shipRegion,postalCode,shipCountry);
        
    }

    public Set<OrderLine> getOrderLines() {
        return orderLines;
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
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }

    //comparazione delle date in modo decrescente, 
    //se avessi voluto fare in modo crescente avrei dovurto methere other. all'interno delle parentesi
    @Override
    public int compareTo(Order other) {
        if(this.orderDate==null && other.orderDate==null){
            return 0;
        }if(this.orderDate==null && other.orderDate!=null){// voglio vedere i null alla fine->quello null viene dopo perche più grande 
            return 1;
        }if(this.orderDate!=null && other.orderDate==null){
            return -1;
        }
        return other.orderDate.compareTo(orderDate);//da più recente
    //  Integer id1=this.id;
    //  Integer id2=other.id;
    //  return id1.compareTo(id2);
    }
    @Override
    public boolean equals(Object o){
        if(o==null ||o.getClass()!=this.getClass()){
            return false;
        }
        Order other=(Order) o;
        return this.id==other.id;// && this.orderDate==other.orderDate;
    }
    @Override
    public int hashCode(){
        return id;
    }
    public void setOrderId(int id) {
        this.id=id;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    // public void setOrderLines(Set<OrderLine> orderLines) {
    //     this.orderLines = orderLines;
    // }

    @Override
    public String toString() {
        return "Order [id=" + id + "]"+orderLines;
    }

}
