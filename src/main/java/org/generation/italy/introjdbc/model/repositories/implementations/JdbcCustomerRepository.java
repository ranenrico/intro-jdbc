package org.generation.italy.introjdbc.model.repositories.implementations;

import java.sql.Date;
import java.sql.*;
import java.util.*;

import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.Order;
import org.generation.italy.introjdbc.model.OrderLine;
import org.generation.italy.introjdbc.model.Product;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.model.repositories.abstractions.CustomerRepository;
import org.generation.italy.introjdbc.utils.ConnectionUtils;

public class JdbcCustomerRepository implements CustomerRepository<Customer>{
    private static final String ALL_CUSTOMER = "SELECT custid, companyname, contactname, contacttitle, address, city, region, postalcode, country, phone, fax FROM customers";
    private static final String ALL_CUSTOMER_NAME_LIKE = """
                                                                SELECT custid, companyname, contactname, contacttitle, address, city, region, postalcode, country, phone, fax 
                                                                FROM customers
                                                                WHERE companyname LIKE ?
                                                                """;
    private static final String CUSTOMER_BY_ID = """
                                                    SELECT custid, companyname, contactname, contacttitle, address, city, region, postalcode, country, phone, fax 
                                                    FROM customers
                                                    WHERE custid = ?
                                                    """;
    private static final String DELETE_BY_ID = """
                                                    DELETE FROM customers
                                                    WHERE custid = ?
                                                    """;
    private static final String UPDATE_CATEGORY = """
                                                    UPDATE customers
                                                    SET  companyname = ?, contactname = ?, contacttitle = ?, address = ?, city = ?, region = ?, postalcode = ?, country = ?, phone = ?, fax = ?
                                                    WHERE custid = ?
                                                    """;
    private static final String INSERT_CATEGORY = """
                                                        INSERT INTO customers
                                                        (custid, companyname, contactname, contacttitle, address, city, region, postalcode, country, phone, fax)
                                                        VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                                                        """;
    private static final String CUSTOMER_BY_NATION = """
                                                            SELECT custid, companyname, contactname, contacttitle, address, city, region, postalcode, country, phone, fax 
                                                            FROM customers
                                                            WHERE country = ?
                                                            """;
    private static final String GET_ALL_BY_CUSTID = """
            SELECT orderid, o.orderdate, o.requireddate, o.shipperdate, o.freight, o.shipname, o.shipaddress, o.shipcity, o.shipregion, o.shippostalcode, o.shipcountry,
            od.productid, od.unitprice, od.qty, od.discount
            FROM orders AS o
            JOIN orderdetails AS od
            USING (orderid)
            WHERE custid = ?
            ORDER BY orderid, orderdate DESC
            """;

    @Override
    public Iterable<Customer> getAll() throws DataException {
        try(
            Connection c = ConnectionUtils.createConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(ALL_CUSTOMER)){
                Collection<Customer> cats = new ArrayList<>();
                while(rs.next()){
                    // int id = rs.getInt("categoryid");
                    // String name = rs.getString("categoryname");
                    // String desc = rs.getString("description");
                    cats.add(new Customer(rs.getString("companyname"), rs.getString("contactname"), rs.getString("contacttitle"), rs.getString("address"), rs.getString("city"), rs.getString("region"), rs.getString("postalcode"), rs.getString("country"), rs.getString("phone"), rs.getString("fax")));               
                }
                return cats;
            } catch(SQLException e){
            throw new DataException("Errore nella lettura dei clienti del database", e);
        }
    }

    @Override
    public Iterable<Customer> getByNameLike(String part) throws DataException {
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(ALL_CUSTOMER_NAME_LIKE);
        ){
                ps.setString(1, "%"+part+"%");
                try(ResultSet rs = ps.executeQuery()){
                    Collection<Customer> cats = new ArrayList<>();
                    while(rs.next()){
                        cats.add(new Customer(rs.getString("companyname"), rs.getString("contactname"), rs.getString("contacttitle"), rs.getString("address"), rs.getString("city"), rs.getString("region"), rs.getString("postalcode"), rs.getString("country"), rs.getString("phone"), rs.getString("fax")));
                    }
                    return cats;
                }
                
            } catch(SQLException e){
                throw new DataException("Errore nella ricerca di clienti per nome LIKE", e);
            }
    }

    @Override
    public Optional<Customer> findById(int id) throws DataException {
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(CUSTOMER_BY_ID);
        ){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(new Customer(rs.getString("companyname"), rs.getString("contactname"), rs.getString("contacttitle"), rs.getString("address"), rs.getString("city"), rs.getString("region"), rs.getString("postalcode"), rs.getString("country"), rs.getString("phone"), rs.getString("fax")));
                } else {
                    return Optional.empty();
                }
            }
        }catch(SQLException e){
            throw new DataException("Errore nella ricerca di clienti per id", e);
        }
    }

    @Override
    public boolean deleteById(int id) throws DataException {
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(DELETE_BY_ID); 
        ){
            ps.setInt(1, id);
            int n = ps.executeUpdate();
            return n == 1;
        }catch(SQLException e){
            throw new DataException("Errore nell'eliminazione del cliente per id", e);
        }
    }

    @Override
    public Optional<Customer> update(Customer newCategory) throws DataException {
        Optional<Customer> oldCat = findById(newCategory.getId());
        if(oldCat.isEmpty()){
            return Optional.empty();
        }
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(UPDATE_CATEGORY);
        ){
            ps.setString(1, newCategory.getCompanyName());
            ps.setString(2, newCategory.getContactName());
            ps.setInt(11, newCategory.getId());
            ps.executeUpdate();               
            return oldCat;
        }catch(SQLException e){
            throw new DataException("Errore nella modifica di un cliente", e);
        }
    }

    @Override
    public Customer create(Customer customer) throws DataException{
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS); 
        ){
            ps.setString(2, customer.getCompanyName());
            ps.setString(3, customer.getContactName());
            ps.setString(4, customer.getContactTitle());
            ps.setString(5, customer.getAddress());
            ps.setString(6, customer.getCity());
            ps.setString(7, customer.getRegion());
            ps.setString(8, customer.getPostalCode());
            ps.setString(9, customer.getCountry());
            ps.setString(10, customer.getPhone());
            ps.setString(11, customer.getFax());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int key = rs.getInt(1);
                customer.setCategoryId(key);
            }
            return customer;
        }catch(SQLException e){
            throw new DataException("Errore nell'aggiunta di una cliente", e);
        }
    }

    @Override
    public Iterable<Customer> findByNation(String nation) throws DataException {
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(CUSTOMER_BY_NATION);
        ){
            ps.setString(1, nation);
            try(ResultSet rs = ps.executeQuery()){
                Collection<Customer> customers = new ArrayList<>();
                while(rs.next()){
                    customers.add(new Customer(rs.getString("companyname"), rs.getString("contactname"), rs.getString("contacttitle"), rs.getString("address"), rs.getString("city"), rs.getString("region"), rs.getString("postalcode"), rs.getString("country"), rs.getString("phone"), rs.getString("fax")));
                } 
                    return customers;
                
            }
        }catch(SQLException e){
            throw new DataException("Errore nella ricerca di clienti per nazionalità", e);
        }
    }

    private Set<Order> getFullOrdersForCustomer(int custid) throws SQLException{

        try(Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(GET_ALL_BY_CUSTID);){
                ps.setInt(1, custid);

                Map<Integer, Order> orderMap = new HashMap<>();
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("orderid");
                        Order o = orderMap.get(id);
                        if(o==null){
                            o = new Order(rs.getDate("orderdate").toLocalDate(), rs.getDate("requireddate").toLocalDate(), rs.getDate("shippeddate").toLocalDate(), rs.getDouble("freight"),
                            rs.getString("shipname"), rs.getString("shipaddress"), rs.getString("shipcity"), rs.getString("shipregion"), rs.getString("shippostalcode"), rs.getString("country"));
                            orderMap.put(o.getId(),o);
                        }
                        OrderLine ol = new OrderLine(o, new Product(rs.getInt("productid")), rs.getDouble("unitprice"), rs.getInt("qty"), rs.getDouble("discount"));
                        o.addOrderLine(ol);
                    }

                    return new TreeSet<>(orderMap.values());
                } 
            }    
        }

        private Set<Order> getFullOrdersForCustomer2(int custid) throws SQLException{

            try(Connection c = ConnectionUtils.createConnection();
                PreparedStatement ps = c.prepareStatement(GET_ALL_BY_CUSTID);){
                    ps.setInt(1, custid);
    
                    try (ResultSet rs = ps.executeQuery()) {
                        int lastId = 0;
                        Order current = null;
                        Set<Order> orders = new TreeSet<>();
                        while (rs.next()) {
                            int id = rs.getInt("orderid");
                            if(id != lastId){
                                current = new Order(rs.getDate("orderdate").toLocalDate(), rs.getDate("requireddate").toLocalDate(), rs.getDate("shippeddate").toLocalDate(), rs.getDouble("freight"),
                                rs.getString("shipname"), rs.getString("shipaddress"), rs.getString("shipcity"), rs.getString("shipregion"), rs.getString("shippostalcode"), rs.getString("country"));
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
            if(oc.isEmpty()){
                return oc;
            }
            try {
                Set<Order> orders = getFullOrdersForCustomer(id);
                oc.get().addOrders(orders);
                return oc;
            } catch (SQLException e) {
                throw new DataException("Errore nella ricerca di un customer con ordini per id", e);
            }
        }

            

}
