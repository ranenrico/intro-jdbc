package org.generation.italy.introjdbc.model;

import java.util.Set;
import java.util.TreeSet;

public class Customer {
    private int id;
    private String companyName;
    private String contactName;
    private String contactTitle;
    private String address;
    private String city;
    private String region;
    private String postalCode;
    private String country;
    private String phone;
    private String fax;
    private Set<Order> orders;

    public Customer(){
        this.orders = new TreeSet<>();
    }

    public Customer(String companyName, String contactName, String contactTitle, String address, String city,
            String region, String postalCode, String country, String phone, String fax) {
        this.companyName = companyName;
        this.contactName = contactName;
        this.contactTitle = contactTitle;
        this.address = address;
        this.city = city;
        this.region = region;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
        this.fax = fax;
        this.orders = new TreeSet<>();
    }

    public int addOrder(Order or){
        orders.add(or);
        return orders.size();
    }

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getContactName() {
        return contactName;
    }
}
