package org.generation.italy.introjdbc.model.repositories;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.Order;
import org.generation.italy.introjdbc.model.OrderLine;
import org.generation.italy.introjdbc.model.Product;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.utils.ConnectionUtils;

public class JdbcCustomerRepository implements CustomerRepository{
    private static final String INSERT_CUSTOMER="""
            insert into customers (companyname,contactname,contacttitle,address, city,region,postalcode,country,phone,fax)
            values(?,?,?,?,?,?,?,?,?,?)
            """; 
    private static final String FIND_BY_COUNTRY="""
        SELECT custid, companyname, contactname, contacttitle, address, city, region, postalcode, country, phone, fax
        FROM public.customers where country=?
            """; 
    private static final String GET_ALL_BY_ID="""
        SELECT custid,orderid,orderdate ,unitprice,qty,discount 
	FROM public.customers JOIN orders
	using(custid)
	join orderdetails
	using(orderid)
    where custid=?
            """;
    private static final String FIND_BY_ID="""
        SELECT custid, companyname, contactname, contacttitle, address, city, region, postalcode, country, phone, fax
        FROM public.customers WHERE id=?;
            """;
    private static final String GET_ALL_BY_CUSTID="""
        SELECT orderid,o.orderdate,o.requireddate,o.shippeddate,o.freight,o.shipname,o.shipaddress,o.shipcity,o.shipregion,o.shippostalcode,o.shipcountry,
        od.productid, od.unitprice,od.qty,od.discount 
        FROM orders as o JOIN 
        orderdetails as od
        using(orderid)
        where custid=?
        order by orderid, orderdate desc
            """;
    
                    
                

    @Override
    public Customer create(Customer customer) throws DataException {
       try(Connection conn=ConnectionUtils.createConnection();
          PreparedStatement ps=conn.prepareStatement(INSERT_CUSTOMER, Statement.RETURN_GENERATED_KEYS); 
       ){
        ps.setString(1,customer.getCompanyName());
        ps.setString(2,customer.getContactName());
        ps.setString(3,customer.getContactTitle());
        ps.setString(4,customer.getAddress());
        ps.setString(5,customer.getCity());
        ps.setString(6,customer.getRegion());
        ps.setString(7,customer.getPostalCode());
        ps.setString(8,customer.getCountry());
        ps.setString(9,customer.getPhone());
        ps.setString(10,customer.getFax());
        ps.executeUpdate();
        try(ResultSet rs=ps.getGeneratedKeys()){
            if(rs.next()){
                int key=rs.getInt(1);
                customer.setCustomerId(key);
            }
        }
        return customer;
       }catch(SQLException e){
            throw new DataException("Errore nell'inserimento di un cliente ", e);
       }

 }

    @Override
    public Iterable<Customer> getByCountry(String country) throws DataException {
        try(Connection conn=ConnectionUtils.createConnection();
        PreparedStatement ps=conn.prepareStatement(FIND_BY_COUNTRY)){
            ps.setString(1,country);
            try(ResultSet rs=ps.executeQuery()){
        
                Collection<Customer> customers=new ArrayList<>();
                while(rs.next()){
                    customers.add(new Customer(rs.getInt("custid"),rs.getString("companyname"), rs.getString("contactname"), rs.getString("contacttitle"),
                    rs.getString("address"), rs.getString("city"), rs.getString("region"),rs.getString("postalcode") , 
                    rs.getString("country"),rs.getString("phone"), rs.getString("fax")));
                }
                return customers;
            }
        }catch(SQLException e){
            throw new DataException("Errore nella ricerca per nazione del cliente",e);
        }

    }

    @Override
    public Optional<Customer> findByIdWithOrders(int id) throws DataException {
        Optional<Customer> oc = findById(id);
        if(oc.isEmpty()){
           return oc; 
        }
        try {
            Set<Order> orders = getFullOrdersForCustomer(id);
            oc.get().addOrders(orders);
            return oc;
        } catch (SQLException e) {
            throw new DataException("Errore nella ricerca di un customer con ordini per id",e);
        }
               
    }

    @Override
    public Optional<Customer> findById(int id) throws DataException {
        try(Connection c=ConnectionUtils.createConnection();
        PreparedStatement ps=c.prepareStatement(FIND_BY_ID)){
            ps.setInt(1,id);
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(new Customer(rs.getInt("custid"),rs.getString("companyname"), rs.getString("contactname"), rs.getString("contacttitle"),
                    rs.getString("address"), rs.getString("city"), rs.getString("region"),rs.getString("postalcode") , 
                    rs.getString("country"),rs.getString("phone"), rs.getString("fax")));
                } else {
                    return Optional.empty();
                }
            
            }
        }catch(SQLException e){
            throw new DataException("Errore nella ricerca del cliente per id",e);
        }
        
    }
    private Set<Order> getFullOrdersForCustomer(int custid) throws SQLException{
        try(Connection c=ConnectionUtils.createConnection();
        PreparedStatement ps=c.prepareStatement(GET_ALL_BY_CUSTID)){
            ps.setInt(1,custid);
            Map<Integer, Order> orderMap=new HashMap<>();
            try(ResultSet rs=ps.executeQuery()){
                while(rs.next()){
                   int id=rs.getInt("orderid");
                   Order o=orderMap.get(id);//cercato nella mappa
                   if(o==null){
                        o=new Order(rs.getDate("orderdate").toLocalDate(),rs.getDate("requireddate").toLocalDate(),rs.getDate("shippeddate").toLocalDate(),rs.getDouble("freight"),
                        rs.getString("shipname"),rs.getString("shipaddress"),rs.getString("shipcity"),rs.getString("shipregion"),rs.getString("shippostalcode"),rs.getString("country"));
                        orderMap.put(o.getId(),o);
                   }
                   OrderLine ol=new OrderLine(o,new Product(rs.getInt("productid")),rs.getDouble("unitprice"),rs.getInt("qty"),rs.getDouble("discount"));
                   o.addOrderLine(ol);
                }
                return new TreeSet<>(orderMap.values());
            }
        }

    }
    private Set<Order> getFullOrdersForCustomer2(int custid) throws SQLException{
        try(Connection c=ConnectionUtils.createConnection();
        PreparedStatement ps=c.prepareStatement(GET_ALL_BY_CUSTID)){
            ps.setInt(1,custid);
            try(ResultSet rs=ps.executeQuery()){//last order id!=order id-->nuovo 
                int lastId=0;
                Order current=null;
                Set<Order> orders=new TreeSet<>();
                while(rs.next()){
                    int id=rs.getInt("orderid");
                    if(id!=lastId){
                        current=new Order(rs.getDate("orderdate").toLocalDate(),rs.getDate("requireddate").toLocalDate(),rs.getDate("shippeddate").toLocalDate(),rs.getDouble("freight"),
                        rs.getString("shipname"),rs.getString("shipaddress"),rs.getString("shipcity"),rs.getString("shipregion"),rs.getString("shippostalcode"),rs.getString("country"));
                        orders.add(current);
                        lastId=id;
                    }
                    OrderLine ol=new OrderLine(current,new Product(rs.getInt("productid")),rs.getDouble("unitprice"),rs.getInt("qty"),rs.getDouble("discount"));
                    current.addOrderLine(ol);
                  
                }
                return orders;
            }
        }
    }   

    

    // @Override
    // public Customer getAllById(int custId) throws DataException {
    //     try(Connection conn=ConnectionUtils.createConnection();
    //     PreparedStatement ps=conn.prepareStatement(GET_ALL_BY_ID)){
    //         ps.setInt(1, custId);
    //         try(ResultSet rs=ps.executeQuery()){
    //             Customer c= new Customer();
    //             if(rs.next()){
    //                 c.setCustomerId(rs.getInt("custId"));
    //                 while(rs.next()){
    //                     Order o=new Order(); 
    //                     //o.setOrderDate(rs.getDate("orderdate").toLocalDate());
    //                     o.setOrderId(rs.getInt("orderid"));
    //                     while(rs.next()){//////////////////////////////////////////////
    //                         OrderLine ol=new OrderLine();
    //                         ol.setOrderId(rs.getInt("orderid"));
    //                         ol.setUnitPrice(rs.getDouble("unitprice"));
    //                         ol.setQuantity(rs.getInt("qty"));
    //                         ol.setDiscount(rs.getDouble("discount"));
    //                         o.addOrderLine(ol);////////////////////
    //                     }
    //                     c.addOrder(o);
    //                 }
                    
    //             }
    //             return c;
    //         }
    //     }catch(SQLException e){
    //         throw new DataException("Errore nella ricerca di un customer per id con le sue liste ", e);
    //     }
    // }
}


