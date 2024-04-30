package org.generation.italy;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.model.repositories.CategoryRepository;
import org.generation.italy.introjdbc.model.repositories.JdbcCategoryRepository;
import org.postgresql.Driver;

//costruire repository per i clienti
//newClient(), ricercaClientNazioni(Nazione)
//ricercaSingoloClientID(id), ritorno 1 cliente, deve ritornare anche la lista dei suoi ordini (e ogni ordine il suo set di ordinLine)
//

public class Main {

    public static void main(String[] args) throws DataException{

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
        //var resultUpdate = cr.update(new Category(9, "Ciao", "pippo"));
        
        Category created  = cr.create(new Category(0, "Ciao", "pippo"));
        Category checkCategory = cr.findById(created.getId()).get();
        System.out.println(checkCategory.getName() + " " + checkCategory.getDescription());
        Category newC = new Category(created.getId(), "Ciao2", "Pippo2");
        cr.update(newC);
    }
}
