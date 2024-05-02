package org.generation.italy.introjdbc.model.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.utils.ConnectionUtils;

public class JdbcCustomerRepositery implements CustomerRepositery{

    private static final String ALL_CUSTOMER = """
                                                    SELECT custid, companyname, contactname, 
                                                    contacttitle, adress, city, region, postalcode, 
                                                    country, phone, fax 
                                                    FROM customers
                                                    """;
    private static final String ALL_CUSTOMER_NAME_LIKE = """
                                                            SELECT custid, companyname, contactname, 
                                                            contacttitle, adress, city, region, postalcode, 
                                                            country, phone, fax FROM customers 
                                                            FROM customers
                                                            WHERE companyname LIKE ?
                                                            """;
    private static final String CUSTOMER_BY_ID = """
                                                    SELECT custid, companyname, contactname, 
                                                    contacttitle, adress, city, region, postalcode, 
                                                    country, phone, fax 
                                                    FROM customers
                                                    WHERE custid = ?
                                                    """;
    private static final String DELETE_BY_ID = """
                                                    DELETE FROM customers
                                                    WHERE custid = ?
                                                    """;
    private static final String UPDATE_CUSTOMER = """
                                                    UPDATE customers
                                                    SET  custid = ?, companyname = ?, contactname = ?, 
                                                    contacttitle = ?, adress = ?, city = ?, region = ?, postalcode = ?, 
                                                    country = ?, phone = ?, fax = ?
                                                    WHERE custid = ?
                                                    """;
    private static final String INSERT_CUSTOMER = """
                                                        INSERT INTO categories
                                                        (categoryName, description)
                                                        VALUES(?, ?)
                                                        """;
    @Override
    public Iterable<Customer> getAll() throws DataException {
        try(
            Connection c = ConnectionUtils.createConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(ALL_CUSTOMER)){
                Collection<Customer> custs = new ArrayList<>();
                while(rs.next()){
                    custs.add(new Customer(rs.getString("companyname"), rs.getString("contactname"), 
                    rs.getString("contacttitle"), rs.getString("adress"), rs.getString("city"), rs.getString("region"), 
                    rs.getString("postalcode"), rs.getString("country"), rs.getString("phone"), rs.getString("fax")));               
                }
                return custs;
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
                    Collection<Customer> custs = new ArrayList<>();
                    while(rs.next()){
                        custs.add(new Customer(rs.getString("companyname"), rs.getString("contactname"), 
                        rs.getString("contacttitle"), rs.getString("adress"), rs.getString("city"), rs.getString("region"), 
                        rs.getString("postalcode"), rs.getString("country"), rs.getString("phone"), rs.getString("fax")));
                    }
                    return custs;
                }
                
            } catch(SQLException e){
                throw new DataException("Errore nella ricerca di categorie per nome LIKE", e);
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
                    return Optional.of(new Customer(rs.getString("companyname"), rs.getString("contactname"), 
                    rs.getString("contacttitle"), rs.getString("adress"), rs.getString("city"), rs.getString("region"), 
                    rs.getString("postalcode"), rs.getString("country"), rs.getString("phone"), rs.getString("fax")));
                } else {
                    return Optional.empty();
                }
            }
        }catch(SQLException e){
            throw new DataException("Errore nella ricerca di cliente per id", e);
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
            throw new DataException("Errore nell'eliminazione di cliente per id", e);
        }
    }

    @Override
    public Optional<Customer> update(Customer newCustomer) throws DataException {
        Optional<Customer> oldCust = findById(newCustomer.getId());
        if(oldCust.isEmpty()){
            return Optional.empty();
        }
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(UPDATE_CUSTOMER);
        ){
            ps.setString(1, newCustomer.getCompanyName());
            ps.setString(2, newCustomer.getContactName());
            ps.setString(3, newCustomer.getContactName());
            ps.setInt(11, newCustomer.getId());
            ps.executeUpdate();               
            return oldCust;
        }catch(SQLException e){
            throw new DataException("Errore nella modifica", e);
        }
    }

    @Override
    public Customer create(Customer customer) throws DataException{
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(INSERT_CUSTOMER, Statement.RETURN_GENERATED_KEYS); 
        ){
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getDescription());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int key = rs.getInt(1);
                customer.setCustomerId(key);
            }
            return customer;
        }catch(SQLException e){
            throw new DataException("Errore nell'aggiunta di una categoria", e);
        }
    }

}
