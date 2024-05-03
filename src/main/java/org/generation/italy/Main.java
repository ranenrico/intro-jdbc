package org.generation.italy;

import org.generation.italy.introjdbc.model.exceptions.DataException;
//REPOSITORY PER CLIENTI-->
//metodo per creare nuovo cliente
//ricerca per tutti i clienti di una certa nazione
//ricerca di un singolo cliente per id(torna un cliente).
//avrà lista ordini e ogni ordine lsita di sue linee d'ordine (customer,order,orderdetails) 
//.ordine ha un customer.customer ha lista di ordini.ordine ha list di linee ordine. linee ordine ha ordine. 
//tripla join
import org.generation.italy.introjdbc.model.repositories.abstractions.CustomerRepository;
import org.generation.italy.introjdbc.model.repositories.implementations.JdbcCustomerRepository;

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
    //     CategoryRepository cr = new JdbcCategoryRepository();
    //     var cats = cr.getByNameLike("ea");
    //     for(var c : cats){
    //         System.out.println(c.getName());
    //     }
    //     var oc = cr.findById(1);
    //     if(oc.isPresent()){
    //         System.out.println(oc.get().getName());
    //     } else {
    //         System.out.println("Non esiste questa categoria");
    //     }
    //     // var result = cr.deleteById(9);
    //     // System.out.println(result);

        //Optional<Category> update(Category newCategory)
       //var oc1= cr.update(new Category(9, "ciao", "pippo"));
    //    Category created=cr.create(new Category(0, "ciao", "pippo"));
    //    Category checkCategory=cr.findById(created.getId()).get();
    //    System.out.println(checkCategory.getName()+" "+checkCategory.getDescription());
    //    Category newC=new Category(created.getId(), "ciao2", "pippo2");
    //    cr.update(newC);
    
        CustomerRepository cu=new JdbcCustomerRepository();

        //Customer c=cu.create(new Customer("Oracle","Riccardo","Mr","Via Cosenza","Cosenza","Calabria","001","Italia","344556677","098765"));
        // var c=cu.getByCountry("Italy");
        // for(var ac: c){
        //     System.out.println(ac);
        // }

        var c1=cu.getAllById(1);
        //System.out.println("custid: %s, %s,  c1.getId(),c1.getOrders(),c1.getOrders().getOrderLines() +)";c1.getOrders()
            System.out.println(c1);




    }
}