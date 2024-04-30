package org.generation.italy.introjdbc.model;

import java.util.Set;

public class Customer {
    private int id;
    private String companyName;
    private String contactName;
    private String contactTitle;
    private String aderss;
    private String city;
    private String region;
    private String postalCode;
    private String companyName;
    private String companyName;
    private String companyName;
    private Set<Order> orders;


    public int addOrder(Order or){
        orders.add(or);
        return orders.size();
    }
}
