package org.generation.italy.introjdbc.model.repositories.implementations;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.Order;
import org.generation.italy.introjdbc.model.OrderLine;
import org.generation.italy.introjdbc.model.Product;
import org.generation.italy.introjdbc.model.exeptions.DataException;
import org.generation.italy.introjdbc.model.repositories.abstractions.CustomerRepository;
import org.generation.italy.introjdbc.utils.ConnectionUtils;

public class JdbcCustomerRepository implements CustomerRepository{
    private JdbcTemplate<Customer> template = new JdbcTemplate<>();
    private static final String INSERT_CUSTOMER = """
        INSERT INTO customers (companyname, contactname, contacttitle, address, city, region, postalcode, country, phone, fax)
        VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) 
    """;

    private static final String CUSTOMER_BY_COUNTRY = """
        SELECT, custid, companyname, contactname, contacttitle, address, city, region, postalcode, country, phone, fax
        FROM customers
        WHERE COUNTRY = ? 
    """;

    private static final String CUSTOMER_BY_ID = """
        SELECT, custid, companyname, contactname, contacttitle, address, city, region, postalcode, country, phone, fax
        FROM customers
        WHERE id = ?
    """;

    private static final String GET_ALL_BY_CUSTID = """
        SELECT orderid, o.orderdate, o.requreddate, o.shippeddate, o.freight, o.shipname, o.shipaddress, o.shipcity, o.shipregion, o.shippostalcode, o.shipcountry, od.productid, od.unitprice, od.qty, od.discount
        FROM orders as o JOIN orderdetails as od
        USING (orderid)
        WHERE custid = ?
        ORDER BY orderid, orderdate DESC 
    """;

    private static final String GET_ALL = """
        SELECT, custid, companyname, contactname, contacttitle, address, city, region, postalcode, country, phone, fax
        FROM customers
    """;

    private static final String UPDATE_CUSTOMER = """
        UPDATE customer  
        SET companyname = ?, contactname = ?, contacttitle = ?, address = ?, city = ?, region = ?, postalcode = ?, country = ?, phone = ?, fax = ?
        WHERE custid = ?
    """;

    private static final String DELETE_BY_ID = """
        DELETE 
        FROM caustomers WHERE custid = ? 
    """;

    @Override
    public Customer save(Customer c) throws DataException {
        KeyHolder key = new KeyHolder();
        template.insert(INSERT_CUSTOMER, key, c.getCompanyName(), c.getContactName(), c.getContactTitle(), c.getAddress(), c.getCity(), c.getRegion(), c.getPostalCode(), c.getCountry(), c.getPhone(), c.getFax());
        c.setCustomerId(key.getKey().intValue());
        return c;
        // try(Connection con = ConnectionUtils.createConnection();
        //     PreparedStatement ps = con.prepareStatement(INSERT_CUSTOMER, Statement.RETURN_GENERATED_KEYS);){ //serve per farci ritornare le chiavi autogenerate
        //         ps.setString(1, customer.getCompanyName());
        //         ps.setString(2, customer.getContactName());
        //         ps.setString(3, customer.getContactTitle());
        //         ps.setString(4, customer.getAddress());
        //         ps.setString(5, customer.getCity());
        //         ps.setString(6, customer.getRegion());
        //         ps.setString(7, customer.getPostalCode());
        //         ps.setString(8, customer.getCountry());
        //         ps.setString(9, customer.getPhone());
        //         ps.setString(10, customer.getFax());
        //         try(ResultSet rs = ps.getGeneratedKeys()){
        //             if(rs.next()){
        //                 int key = rs.getInt(1);
        //                 customer.setCustomerId(key);
        //             }
        //         }
        //         ps.executeUpdate();
        //         return customer;
        //     } catch(SQLException e){
        //         throw new DataException("Errore nell'inserimento di un nuovo cliente", e);
        //     }
    }


    @Override
    public Iterable<Customer> getByCountry(String country) throws DataException {
        return template.query(CUSTOMER_BY_COUNTRY, this::fromResultSet , country); //this::fromResultSet significa prendi il metodo che è contenuto in this, questa classe dove siamo
        // try (Connection con = ConnectionUtils.createConnection();
        // PreparedStatement ps = con.prepareStatement(CUSTOMER_BY_COUNTRY)){
        //     ps.setString(1, country);
        //     try (ResultSet rs = ps.executeQuery()) {
        //         Collection<Customer> customers = new ArrayList<>();
        //         while (rs.next()){
        //             customers.add(new Customer(rs.getInt("custid"), rs.getString("companyname"), rs.getString("contactname"), rs.getString("contacttitle"), rs.getString("address"), rs.getString("city"), rs.getString("region"), rs.getString("postalcode"), rs.getString("country"), rs.getString("phone"), rs.getString("fax")));
        //         }
        //         return customers;
        //     }   
        // } catch (SQLException e) {
        //     throw new DataException("Errore nella ricerca del cliente per nazione", e);
        // }
    }

    @Override
    public Optional<Customer> findById(Integer id) throws DataException {
        return template.queryForObject(CUSTOMER_BY_ID, this::fromResultSet, id);
        // try (Connection con = ConnectionUtils.createConnection();
        // PreparedStatement ps = con.prepareStatement(CUSTOMER_BY_ID)) {
        //     ps.setInt(1, id);
        //     try(ResultSet rs = ps.executeQuery()){
        //         if(rs.next()){
        //             return Optional.of(new Customer(rs.getInt("custid"), rs.getString("companyname"), rs.getString("contactname"), rs.getString("contacttitle"), rs.getString("address"), rs.getString("city"), rs.getString("region"), rs.getString("postalcode"), rs.getString("country"), rs.getString("phone"), rs.getString("fax")));
        //         } else {
        //             return Optional.empty();
        //         } 
        //     }
        // } catch (SQLException e) {
        //     throw new DataException("Errore nella ricerca del cliente per id", e);
        // }
    }

    private Set<Order> getFullOrdersForCustomer(int custid) throws SQLException{
        try (Connection c = ConnectionUtils.createConnection();
        PreparedStatement ps = c.prepareStatement(GET_ALL_BY_CUSTID)) {
            ps.setInt(1, custid);
            Map<Integer, Order> orderMap = new HashMap<>();
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    int id = rs.getInt("orderid");
                    Order o = orderMap.get(id);
                    if(o==null){
                        o = new Order(rs.getDate("orderdate").toLocalDate(),rs.getDate("requreddate").toLocalDate(), rs.getDate("shippeddate").toLocalDate(), rs.getDouble("freight"), rs.getString("shipname"), rs.getString("shipaddress"), rs.getString("shipcity"), rs.getString("shipregion"), rs.getString("shippostalcode"), rs.getString("shipcountry"));
                        orderMap.put(o.getId(), o);
                    }
                    OrderLine ol = new OrderLine(o, new Product(rs.getInt("productid")), rs.getDouble("unitprice"), rs.getInt("qty"), rs.getDouble("discount"));
                    o.addOrderLine(ol);
                }
                return new TreeSet<>(orderMap.values());
            }
        }
    }

        //metodo un po' meno efficiente del precedente ma piu' semplice
    private Set<Order> getFullOrdersForCustomer2(int custid) throws SQLException{
        try (Connection c = ConnectionUtils.createConnection();
        PreparedStatement ps = c.prepareStatement(GET_ALL_BY_CUSTID)) {
            ps.setInt(1, custid);
            try(ResultSet rs = ps.executeQuery()){
                int lastId = 0;
                Order current = null;
                Set<Order> orders = new TreeSet<>();
                while(rs.next()){
                    int id = rs.getInt("orderid");
                    if(id != lastId){
                        current = new Order(rs.getDate("orderdate").toLocalDate(),rs.getDate("requreddate").toLocalDate(), rs.getDate("shippeddate").toLocalDate(), rs.getDouble("freight"), rs.getString("shipname"), rs.getString("shipaddress"), rs.getString("shipcity"), rs.getString("shipregion"), rs.getString("shippostalcode"), rs.getString("shipcountry"));
                        orders.add(current);
                        lastId = id;
                    }
                    OrderLine ol = new OrderLine(current, new Product(rs.getInt("productid")), rs.getDouble("unitprice"), rs.getInt("qty"), rs.getDouble("discount"));
                    current.addOrderLine(ol);
                }
                return orders;
            }
        }
    }

    @Override
    public Optional<Customer> findByIdWithOrders(int id) throws DataException {
        Optional<Customer> oc = findById(id);
        if(oc.isEmpty()) {
            return oc;
        }
        try {
            Set<Order> orders = getFullOrdersForCustomer(id);
            oc.get().addOrders(orders);
            return oc;
        } catch (SQLException e) {
            throw new DataException("Errore nella ricerca del cliente con tutti gli ordini per id cliente", e);
        }
    }


    @Override
    public List<Customer> findAll() throws DataException {
        return template.query(GET_ALL, this::fromResultSet);
    }

    @Override
    public void update(Customer c) throws DataException {
        template.update(UPDATE_CUSTOMER, c.getCompanyName(), c.getContactName(), c.getContactTitle(), c.getAddress(), c.getCity(), c.getRegion(), c.getPostalCode(), c.getCountry(), c.getPhone(), c.getFax(), c.getId());
    }

    @Override
    public void deleteById(Integer id) throws DataException {
        template.update(DELETE_BY_ID, id);
    }


    private void setCustomerParameters(PreparedStatement ps, Customer customer) throws SQLException{
        ps.setString(1, customer.getCompanyName());
        ps.setString(2, customer.getContactName());
        ps.setString(3, customer.getContactTitle());
        ps.setString(4, customer.getAddress());
        ps.setString(5, customer.getCity());
        ps.setString(6, customer.getRegion());
        ps.setString(7, customer.getPostalCode());
        ps.setString(8, customer.getCountry());
        ps.setString(9, customer.getPhone());
        ps.setString(10, customer.getFax());
    }

    private Customer fromResultSet(ResultSet rs) throws SQLException{
        return new Customer(rs.getInt("custid"), rs.getString("companyname"), rs.getString("contactname"), rs.getString("contacttitle"), rs.getString("address"), rs.getString("city"), rs.getString("region"), rs.getString("postalcode"), rs.getString("country"), rs.getString("phone"), rs.getString("fax"));
    }
}
