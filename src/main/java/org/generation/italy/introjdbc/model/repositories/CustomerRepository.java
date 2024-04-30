package org.generation.italy.introjdbc.model.repositories;

import java.util.Optional;

import org.generation.italy.introjdbc.model.Customer;
import org.generation.italy.introjdbc.model.exceptions.DataException;

public interface CustomerRepository {
    Iterable<Customer> getAll() throws DataException;
    Iterable<Customer> getByNameLike(String part) throws DataException;
    Optional<Customer> findById(int id) throws DataException;
    boolean deleteById(int id) throws DataException;
    Optional<Customer> update(Customer newCustomer) throws DataException;
    Customer create(Customer customer) throws DataException;
}
