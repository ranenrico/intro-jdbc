package org.generation.italy.introjdbc.model.repositories.implementations;

import java.sql.Date;
import java.sql.*;
import java.util.*;

import org.generation.italy.introjdbc.model.Category;
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
    private static final String UPDATE_CUSTOMER = """
                                                    UPDATE customers
                                                    SET  companyname = ?, contactname = ?, contacttitle = ?, address = ?, city = ?, region = ?, postalcode = ?, country = ?, phone = ?, fax = ?
                                                    WHERE custid = ?
                                                    """;
    private static final String INSERT_CUSTOMER = """
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

    private JdbcTemplate<Customer> template = new JdbcTemplate<>();        

    @Override
    public Iterable<Customer> getAll() throws DataException {
        return template.query(ALL_CUSTOMER, this::fromResultSet);
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
                        cats.add(new Customer(rs.getInt("custid"), rs.getString("companyname"), rs.getString("contactname"), rs.getString("contacttitle"), rs.getString("address"), rs.getString("city"), rs.getString("region"), rs.getString("postalcode"), rs.getString("country"), rs.getString("phone"), rs.getString("fax")));
                    }
                    return cats;
                }
                
            } catch(SQLException e){
                throw new DataException("Errore nella ricerca di clienti per nome LIKE", e);
            }
    }

    @Override
    public Optional<Customer> findById(int id) throws DataException {
        return template.queryForObject(CUSTOMER_BY_ID, this::fromResultSet, id);
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
    public void update(Customer newCategory) throws DataException {
        template.update(UPDATE_CUSTOMER, newCategory.getCompanyName(), newCategory.getContactName(), newCategory.getId());
    }

    @Override
    public Customer create(Customer c) throws DataException{
        KeyHolder key = new KeyHolder();
        template.insert(INSERT_CUSTOMER, key,    
                                        c.getCompanyName(),
                                        c.getContactName(),
                                        c.getContactTitle(),
                                        c.getAddress(),
                                        c.getCity(),
                                        c.getRegion(),
                                        c.getPostalCode(),
                                        c.getCountry(),
                                        c.getPhone(),
                                        c.getFax());
        c.setCustomerId(key.getKey().intValue());
        return c;
    }

    private void setCustomerParameters(PreparedStatement ps, Customer customer) throws SQLException{
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
    }

    @Override
    public Iterable<Customer> findByNation(String nation) throws DataException {
        return template.query(CUSTOMER_BY_NATION, 
                                this::fromResultSet,
                                nation);
    }

    private Customer fromResultSet(ResultSet rs) throws SQLException{
        return new Customer(rs.getInt("custid"), rs.getString("companyname"), rs.getString("contactname"), rs.getString("contacttitle"), rs.getString("address"), rs.getString("city"), rs.getString("region"), rs.getString("postalcode"), rs.getString("country"), rs.getString("phone"), rs.getString("fax"));                      
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
