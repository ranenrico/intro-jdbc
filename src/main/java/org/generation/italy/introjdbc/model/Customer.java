package org.generation.italy.introjdbc.model;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
=======

import java.util.Collection;
>>>>>>> origin/francesca_piccitto
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
<<<<<<< HEAD
    public Customer(String companyName, String contactName, String contactTitle, String address, String city, String region,
            String postalCode, String country, String phone, String fax) {
=======

    public Customer(){
        this.orders = new TreeSet<>();
    }

    public Customer(int id, String companyName, String contactName, String contactTitle, String address, String city, String region, String postalCode, String country, String phone, String fax) {
        this(companyName, contactName, contactTitle, address, city, region, postalCode, country, phone, fax);
        this.id = id;
        this.orders = new TreeSet<>();
    }

    public Customer(String companyName, String contactName, String contactTitle, String address, String city, String region, String postalCode, String country, String phone, String fax) {
>>>>>>> origin/francesca_piccitto
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
<<<<<<< HEAD
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
        or.setCustomer(this);
        return orders.size();
    }
    public void addOrders(Collection<Order> newOrders){
        newOrders.stream().forEach(o -> o.setCustomer(this));
        orders.addAll(newOrders);       
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
    
=======
        this.orders = new TreeSet<>();
    }
    public int addOrder(Order o){
        orders.add(o);
        o.setCustomer(this);
        return orders.size();
    }

    public void addOrders(Collection<Order> newOrders){
        newOrders.stream().forEach(o -> o.setCustomer(this));
        orders.addAll(newOrders);
    }

    public void setCustomerId(int id) {
        this.id = id;
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

>>>>>>> origin/francesca_piccitto
    public Set<Order> getOrders() {
        return orders;
    }

<<<<<<< HEAD
    @Override
    public String toString(){
        return String.format("Customer [id: %d, companyname: %s, contactname: %s, contacttile: %s, address: %s, city: %s, region: %s, postalcode: %s, country: %s, phone: %s, fax: %s ]"+orders,
                            id,companyName,contactName,contactTitle,address,city,region,postalCode,country,phone,fax);
    }
=======
    public String toString(Customer c){
        return c.getCompanyName() + " " + c.getContactName() + " " + c.getContactTitle() + " " + c.getAddress() + " " + c.getCity() + " " + c.getRegion() + " " + c.getPostalCode() + " " + c.getCountry() + " " + c.getPhone() + " " + c.getFax();
    }
    
>>>>>>> origin/francesca_piccitto
}
