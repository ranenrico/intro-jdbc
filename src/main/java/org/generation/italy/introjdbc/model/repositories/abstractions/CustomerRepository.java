package org.generation.italy.introjdbc.model.repositories.abstractions;

import java.util.Optional;
<<<<<<< HEAD
import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.exceptions.DataException;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{
    Iterable<Customer> getByCountry(String country)throws DataException;
    Optional<Customer> findByIdWithOrders(int id)throws DataException;
=======
import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.exeptions.DataException;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

    Iterable<Customer> getByCountry(String country) throws DataException;
    Optional<Customer> findByIdWithOrders(int id) throws DataException;
>>>>>>> origin/francesca_piccitto
}
