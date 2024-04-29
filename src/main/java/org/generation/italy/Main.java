package org.generation.italy;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.Driver;



public class Main {
    private static final String ALL_CATEGORIES = "SELECT categoryid, categoryname, description FROM categories";
    public static void main(String[] args) {
        System.out.println("Hello world!");
        final String jdbcurl = "jdbc:postgresql://localhost:5432/company";
        final String user = "postgresMaster";
        final String password = "goPostgresGo";
        try (Connection conn = DriverManager.getConnection(jdbcurl, user, password);
        Statement statement = conn.createStatement();    //factory method design pattern
        ResultSet rs = statement.executeQuery(ALL_CATEGORIES)){   
            System.out.println("Connessione riuscita");
            System.out.println(conn.getClass().getName());
            while(rs.next()){
                int categoryid = rs.getInt("categoryid");
                String name = rs.getString("categoryname");
                String description = rs.getString("description");
                System.out.printf("id: %d, name: %s, description: %s%n", categoryid, name, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}