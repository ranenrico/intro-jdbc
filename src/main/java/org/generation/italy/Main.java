package org.generation.italy;

import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.exeptions.DataException;
import org.generation.italy.introjdbc.model.repositories.abstractions.CategoryRepository;
import org.generation.italy.introjdbc.model.repositories.abstractions.CustomerRepository;
import org.generation.italy.introjdbc.model.repositories.implementations.JdbcCategoryRepository;
import org.generation.italy.introjdbc.model.repositories.implementations.JdbcCustomerRepository;
 

public class Main {
    public static void main(String[] args) throws DataException {
        CustomerRepository customerRepo = new JdbcCustomerRepository();
        Customer c = new Customer("Fedex", "Gigi", "Mr", "via Torino", "Capalle", "Toscana", "50123", "Italia", "12343211", "0544321");
        Customer savedcustomer = customerRepo.save(c);
        System.out.println(savedcustomer.toString());



        // CategoryRepository cr = new JdbcCategoryRepository();
        // var cats = cr.getByNameLike("ea");
        // for (var ca : cats) {
        //     System.out.println(ca.getName());
        // }
        // var oc = cr.findById(1);
        // if (oc.isPresent()){
        //     System.out.println(oc.get().getName());
        // } else {
        //     System.out.println("Non esiste una categoria con questo id");
        // }  

        // Category created = cr.save(new Category(0,"ciao", "pippo"));
        // Category checkCat = cr.findById(created.getId()).get();
        // System.out.println(checkCat.getName() + " " + checkCat.getDescription());
        // Category newC = new Category(created.getId(), "ciao2", "pippo2");
        // cr.update(newC);
    }
}