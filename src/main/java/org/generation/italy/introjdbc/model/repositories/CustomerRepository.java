package org.generation.italy.introjdbc.model.repositories;

import java.util.Optional;

import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.exceptions.DataException;

public interface CustomerRepository {
    Customer create(Customer customer)throws DataException;
    Iterable<Customer> getByCountry(String country)throws DataException;
    Customer getAllById(int id)throws DataException;
    Optional<Customer> findById(int id)throws DataException;
}
