package org.generation.italy.introjdbc.model.repositories.implementations;

import java.sql.*;
import java.util.*;
import java.sql.Date;
import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.Order;
import org.generation.italy.introjdbc.model.OrderLine;
import org.generation.italy.introjdbc.model.Product;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.model.repositories.abstractions.CustomerRepository;
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
        FROM public.customers WHERE custid=?;
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

    private static final String GET_ALL = """
        SELECT custid,orderid,orderdate,unitprice,qty,discount 
        FROM public.customers 
            """;      
    private static final String UPDATE_CUSTOMER="""
        UPDATE customers
        SET companyname=?, contactname=?, contacttitle=?, address=?, city=?, region=?, postalcode=?, country=?, phone=?, fax=?
        where custid=?
        """;
    private static final String DELETE_BY_ID="""
            DELETE FROM customers
            where custid=?
            """;
    private JdbcTemplate<Customer> template = new JdbcTemplate<>();

    @Override//
    public Iterable<Customer> getByCountry(String country) throws DataException {
            return template.query(FIND_BY_COUNTRY, this::fromResultSet, country);
    }

    @Override
    public Optional<Customer> findByIdWithOrders(int id) throws DataException {
        Optional<Customer> oc = findById(id);
        if(oc.isEmpty()){
           return oc; 
        }
        try {
            Set<Order> orders = getFullOrdersForCustomer2(id);
            oc.get().addOrders(orders);
            return oc;
        } catch (SQLException e) {
            throw new DataException("Errore nella ricerca di un customer con ordini per id",e);
        }
               
    }

    @Override
    public Optional<Customer> findById(Integer id) throws DataException {
        return template.queryForObject(FIND_BY_ID, this::fromResultSet,id);  
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
                        o=new Order(rs.getInt("orderid"),rs.getDate("orderdate").toLocalDate(),rs.getDate("requireddate").toLocalDate(),rs.getDate("shippeddate").toLocalDate(),rs.getDouble("freight"),
                        rs.getString("shipname"),rs.getString("shipaddress"),rs.getString("shipcity"),rs.getString("shipregion"),rs.getString("shippostalcode"),rs.getString("shipcountry"));
                        orderMap.put(o.getId(),o);
                   }
                   OrderLine ol=new OrderLine(o,new Product(rs.getInt("productid")),rs.getDouble("unitprice"),rs.getInt("qty"),rs.getDouble("discount"));
                   o.addOrderLine(ol);
                   System.out.println(ol);
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
                        current=new Order(rs.getInt("orderid"),rs.getDate("orderdate").toLocalDate(),rs.getDate("requireddate").toLocalDate(),rs.getDate("shippeddate").toLocalDate(),rs.getDouble("freight"),
                        rs.getString("shipname"),rs.getString("shipaddress"),rs.getString("shipcity"),rs.getString("shipregion"),rs.getString("shippostalcode"),rs.getString("shipcountry"));
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
    // public Customer save(Customer c) throws DataException {
    //     KeyHolder kh = new KeyHolder();
    //     template.insert(INSERT_CUSTOMER,
    //                     kh,
    //                    // this::setCustomerParameters,
    //                     c.getCompanyName(),
    //                     c.getContactName(),
    //                     c.getContactTitle(),
    //                     c.getAddress(),
    //                     c.getCity(),
    //                     c.getRegion(),
    //                     c.getPostalCode(),
    //                     c.getCountry(),
    //                     c.getPhone(),
    //                     c.getFax()
    //                     ); 
    //    c.setCustomerId(kh.getKey().intValue());
    //    return c; 
    // }
    @Override
     public Customer save(Customer c) throws DataException {
        KeyHolder kh=new KeyHolder();
        template.insert(INSERT_CUSTOMER, kh,this::setCustomerParameters,c);
        c.setCustomerId(kh.getKey().intValue());
        return c;
    }


    @Override
    public List<Customer> findAll() throws DataException {
        return template.query(GET_ALL, this::fromResultSet);
    }

    @Override
    public void update(Customer newEntity) throws DataException { 
        template.update(UPDATE_CUSTOMER,this::setCustomerParameters,newEntity);
    }

    @Override
    public void deleteById(Integer id) throws DataException {
        template.update(DELETE_BY_ID,id);
    }   

    private Customer fromResultSet(ResultSet rs) throws SQLException{
        return new Customer(rs.getInt("custid"),
        rs.getString("companyname"),
        rs.getString("contactname"),
        rs.getString("contacttitle"),
        rs.getString("address"),
        rs.getString("city"),
        rs.getString("region"),
        rs.getString("postalcode"), 
        rs.getString("country"),
        rs.getString("phone"),
        rs.getString("fax")); 

    }

    private void setCustomerParameters(PreparedStatement ps, Object o) throws SQLException{
        Customer c=(Customer) o;
        ps.setString(1,c.getCompanyName());
        ps.setString(2,c.getContactName());
        ps.setString(3,c.getContactTitle());
        ps.setString(4,c.getAddress());
        ps.setString(5,c.getCity());
        ps.setString(6,c.getRegion());
        ps.setString(7,c.getPostalCode());
        ps.setString(8,c.getCountry());
        ps.setString(9,c.getPhone());
        ps.setString(10,c.getFax());
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


