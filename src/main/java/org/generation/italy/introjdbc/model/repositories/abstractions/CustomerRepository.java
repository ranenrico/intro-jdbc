package org.generation.italy.introjdbc.model.repositories.abstractions;

import java.util.Optional;
import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.exceptions.DataException;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{
    Iterable<Customer> getByCountry(String country)throws DataException;
    Optional<Customer> findByIdWithOrders(int id)throws DataException;
}
