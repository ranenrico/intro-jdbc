package org.generation.italy.introjdbc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {
    final static String jdbcurl = "jdbc:postgresql://localhost:5432/company";
    final static String user = "postgresMaster";
    final static String password = "goPostgresGo";

<<<<<<< HEAD
    public static Connection createConnection() throws SQLException{
       return DriverManager.getConnection(jdbcurl, user, password);
=======
    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(jdbcurl, user, password);
>>>>>>> origin/francesca_piccitto
    }
}
