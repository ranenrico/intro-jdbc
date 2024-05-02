package org.generation.italy;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.model.repositories.CategoryRepository;
import org.generation.italy.introjdbc.model.repositories.CustomerRepository;
import org.generation.italy.introjdbc.model.repositories.JdbcCategoryRepository;
import org.generation.italy.introjdbc.model.repositories.JdbcCustomerRepository;
import org.postgresql.Driver;

//costruire repository per i clienti
//newClient(), ricercaClientNazioni(Nazione)
//ricercaSingoloClientID(id), ritorno 1 cliente, deve ritornare anche la lista dei suoi ordini (e ogni ordine il suo set di ordinLine)
//

public class Main {
    private static final String ALL_CATEGORIES = "SELECT categoryid, categoryname, description FROM categories";
    public static void main(String[] args) throws DataException {
        // System.out.println("Hello world!");
        // final String jdbcurl = "jdbc:postgresql://localhost:5432/company";
        // final String user = "postgresMaster";
        // final String password = "goPostgresGo";
        // try (Connection conn = DriverManager.getConnection(jdbcurl, user, password);
        // Statement statement = conn.createStatement();    //factory method design pattern
        // ResultSet rs = statement.executeQuery(ALL_CATEGORIES)){   
        //     System.out.println("Connessione riuscita");
        //     System.out.println(conn.getClass().getName());
        //     while(rs.next()){
        //         int categoryid = rs.getInt("categoryid");
        //         String name = rs.getString("categoryname");
        //         String description = rs.getString("description");
        //         System.out.printf("id: %d, name: %s, description: %s%n", categoryid, name, description);
        //     }
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
        CategoryRepository<Category> cr = new JdbcCategoryRepository();
        var cats = cr.getByNameLike("ea");
        for(var c : cats){
            System.out.println(c.getName());
        }
        var oc = cr.findById(1);
        if(oc.isPresent()){
            System.out.println(oc.get().getName());
        } else {
            System.out.println("Non esiste questa categoria");
        }
        //var resultUpdate = cr.update(new Category(9, "Ciao", "pippo"));
        
        // Category created  = cr.create(new Category(0, "Ciao", "pippo"));
        // Category checkCategory = cr.findById(created.getId()).get();
        // System.out.println(checkCategory.getName() + " " + checkCategory.getDescription());
        // Category newC = new Category(created.getId(), "Ciao2", "Pippo2");
        // cr.update(newC);

        CustomerRepository<Customer> custRepo = new JdbcCustomerRepository();
        var custNation = custRepo.findByNation("Germany");
        System.out.println(custNation);
    }
}