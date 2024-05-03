package org.generation.italy.introjdbc.model.repositories.abstractions;

import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.exceptions.DataException;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{
    Iterable<Customer> getByCountry(String country)throws DataException;
    Customer getAllById(int id)throws DataException;
}
