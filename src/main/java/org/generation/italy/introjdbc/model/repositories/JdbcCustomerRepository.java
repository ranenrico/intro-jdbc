package org.generation.italy.introjdbc.model.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.utils.ConnectionUtils;

public class JdbcCustomerRepository implements CustomerRepository{
    private static final String ALL_CUSTOMER = "SELECT custid, companyname, contactname, contacttitile, address, city, region, postalcode, country, phone, fax FROM customers";
    private static final String ALL_CUSTOMER_NAME_LIKE = """
                                                                SELECT custid, companyname, contactname, contacttitile, address, city, region, postalcode, country, phone, fax 
                                                                FROM customers
                                                                WHERE companyname LIKE ?
                                                                """;
    private static final String CUSTOMER_BY_ID = """
                                                    SELECT custid, companyname, contactname, contacttitile, address, city, region, postalcode, country, phone, fax 
                                                    FROM customers
                                                    WHERE custid = ?
                                                    """;
    private static final String DELETE_BY_ID = """
                                                    DELETE FROM customers
                                                    WHERE custid = ?
                                                    """;
    private static final String UPDATE_CATEGORY = """
                                                    UPDATE customers
                                                    SET  companyname = ?, contactname = ?, contacttitile = ?, address = ?, city = ?, region = ?, postalcode = ?, country = ?, phone = ?, fax = ?
                                                    WHERE custid = ?
                                                    """;
    private static final String INSERT_CATEGORY = """
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
            throw new DataException("Errore nella modifica", e);
        }
    }

    @Override
    public Customer create(Customer category) throws DataException{
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS); 
        ){
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int key = rs.getInt(1);
                category.setCategoryId(key);
            }
            return category;
        }catch(SQLException e){
            throw new DataException("Errore nell'aggiunta di una categoria", e);
        }
    }
}
