package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.postgresql.Driver;

//perché il mio codice non usa direttamente le classi della libreria di postgres?
//qual è la tecnica che mi permette di usare delle interfacce per parlare con degli oggetti della libreria di postgres

//com'è possibile che il metodo DriverManager getConnection() mi ritorni un oggetto che io non ho nominato?

//perché il DriverManager seleziona il giusto driver in base all'URL (driver ha metodo acceptsURL)

//il metodo DriverManager getConnection() è un esempio di un idioma che si chiama..? (un pattern meno dignitoso)

//factory method

public class Main {
    private static final String url = "jdbc:postgresql://localhost:5432/Orders";
    private static final String username = "postgresMaster";
    private static final String password = "goPostgresGo";

    public static void main(String[] args) {

        String query = "SELECT * FROM orders WHERE orderid = 10248;";
        String query2 = "INSERT INTO orders (empid, orderdate, requireddate, shipperid, freight, shipname, shipaddress, shipcity, shipcountry) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
                PreparedStatement preparedStatement = connection.prepareStatement(query2);) {
            System.out.println("Connessione con il database riuscita!");

            preparedStatement.setInt(1, 1);
            preparedStatement.setDate(2, java.sql.Date.valueOf("2021-01-01"));
            preparedStatement.setDate(3, java.sql.Date.valueOf("2022-01-10"));
            preparedStatement.setInt(4, 2);
            preparedStatement.setDouble(5, 12.08);
            preparedStatement.setString(6, "Napoli");
            preparedStatement.setString(7, "Via Napoli");
            preparedStatement.setString(8, "Napoli");
            preparedStatement.setString(9, "Italia");

            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    int orderId = resultSet.getInt("orderid");
                    String orderDate = resultSet.getString("orderdate");

                    System.out.println("Order ID: " + orderId);
                    System.out.println("Order Date: " + orderDate);
                }
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Connessione con il database fallita!");
            e.printStackTrace();
        }
    }
}
