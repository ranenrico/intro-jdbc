package org.generation.italy;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.model.repositories.CategoryRepository;
import org.generation.italy.introjdbc.model.repositories.JdbcCategoryRepository;
import org.postgresql.Driver;



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
        CategoryRepository cr = new JdbcCategoryRepository();
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
        var result = cr.deleteById(9);
        System.out.println(result);
    }
}