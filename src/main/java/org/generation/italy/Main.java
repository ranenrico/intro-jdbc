package org.generation.italy;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.model.repositories.CategoryRepository;
import org.generation.italy.introjdbc.model.repositories.JdbcCategoryRepository;
import org.postgresql.Driver;

//perché il mio codice non usa direttamente le classi della libreria di postgres?
//qual è la tecnica che mi permette di usare delle interfacce per parlare con degli oggetti della libreria di postgres


//com'è possibile che il metodo DriverManager getConnection() mi ritorni un oggetto che io non ho nominato?

//perché il DriverManager seleziona il giusto driver in base all'URL (driver ha metodo acceptsURL)

//il metodo DriverManager getConnection() è un esempio di un idioma che si chiama..? (un pattern meno dignitoso)

//factory method

public class Main {
    private static final String ALL_CATEGORIES = "SELECT categoryid, categoryname, description FROM categories";
    public static void main(String[] args) {
    
    }
}
