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
    public Customer(String companyName, String contactName, String contactTitle, String address, String city, String region,
            String postalCode, String country, String phone, String fax) {
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
        this.orders=new TreeSet<>();
    }
    public Customer(int id, String companyName, String contactName, String contactTitle, String address, String city,
            String region, String postalCode, String country, String phone, String fax) {
        this(companyName, contactName, contactTitle, address, city, region, postalCode, country, phone, fax);
        this.id=id;
        this.orders=new TreeSet<>();
    }
    public Customer() {
        this.orders=new TreeSet<>();
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
    public String getContactTitle() {
        return contactTitle;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public String getRegion() {
        return region;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public String getCountry() {
        return country;
    }
    public String getPhone() {
        return phone;
    }
    public String getFax() {
        return fax;
    }
    public void setCustomerId(int key){
        this.id=key;
    }
    
    public Set<Order> getOrders() {
        return orders;
    }

    @Override
    public String toString(){
        return String.format("Customer [id: %d, companyname: %s, contactname: %s, contacttile: %s, address: %s, city: %s, region: %s, postalcode: %s, country: %s, phone: %s, fax: %s ]"+orders,
                            id,companyName,contactName,contactTitle,address,city,region,postalCode,country,phone,fax);
    }
}
