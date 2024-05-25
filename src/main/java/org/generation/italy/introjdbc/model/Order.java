package org.generation.italy.introjdbc.model;

<<<<<<< HEAD
import java.sql.Date;
=======
>>>>>>> origin/francesca_piccitto
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

<<<<<<< HEAD
public class Order implements Comparable<Order>{
    private int id;
    private Customer customer;
    //private int empid->al momento non ho impiegato
    private LocalDate orderDate;
    private LocalDate requiredDate;
    private LocalDate shipDate;
    //private shipperid
=======
public class Order implements Comparable<Order> {
    private int id;
    private Customer customer;
    private LocalDate date;
    private LocalDate requiredDate;
    private LocalDate shippedDate;
>>>>>>> origin/francesca_piccitto
    private double freight;
    private String shipName;
    private String shipAddress;
    private String shipCity;
    private String shipRegion;
<<<<<<< HEAD
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
=======
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
>>>>>>> origin/francesca_piccitto
        this.freight = freight;
        this.shipName = shipName;
        this.shipAddress = shipAddress;
        this.shipCity = shipCity;
        this.shipRegion = shipRegion;
<<<<<<< HEAD
        this.postalCode = postalCode;
        this.shipCountry = shipCountry;
        this.orderLines=new HashSet();
    }
    
    public Order(LocalDate orderDate, LocalDate requiredDate, LocalDate shipDate, double freight, String shipName, String shipAddress,
            String shipCity, String shipRegion, String postalCode, String shipCountry) {
            this(null,orderDate,requiredDate,shipDate,freight,shipName,shipAddress,shipCity,shipRegion,postalCode,shipCountry);
        
    }
    

    public Order(int id, LocalDate orderDate, LocalDate requiredDate, LocalDate shipDate, double freight,
            String shipName, String shipAddress, String shipCity, String shipRegion, String postalCode,
            String shipCountry) {
        this.id = id;
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

    public Set<OrderLine> getOrderLines() {
        return orderLines;
    }

=======
        this.shipPostalCode = shipPostalCode;
        this.shipCountry = shipCountry;
        this.orderLines = new HashSet<>();
    }


    public Order(LocalDate date, LocalDate requiredDate, LocalDate shippedDate, double freight, String shipName, String shipAddress, String shipCity, String shipRegion, String shipPostalCode, String shipCountry) {
        this(null, date, requiredDate, shippedDate, freight, shipName, shipAddress, shipCity, shipRegion, shipPostalCode, shipCountry);
        this.orderLines = new HashSet<>();
    }


>>>>>>> origin/francesca_piccitto
    public int addOrderLine(OrderLine line){
        orderLines.add(line);
        return orderLines.size();
    }
<<<<<<< HEAD
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
=======


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

>>>>>>> origin/francesca_piccitto
    @Override
    public int hashCode(){
        return id;
    }
<<<<<<< HEAD
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
        return "Order [id=" + id + "]"+orderLines.toString();
    }

=======

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

    
>>>>>>> origin/francesca_piccitto
}
