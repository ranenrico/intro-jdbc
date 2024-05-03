package org.generation.italy.introjdbc.model.repositories.abstractions;

import java.util.Optional;

import org.generation.italy.introjdbc.model.exceptions.DataException;

public interface CustomerRepository<Customer> extends CrudRepository<Customer>{
    @Override
    Iterable<Customer> getAll() throws DataException;
    @Override
    Iterable<Customer> getByNameLike(String part) throws DataException;
    @Override
    Optional<Customer> findById(int id) throws DataException;
    @Override
    boolean deleteById(int id) throws DataException;
    @Override
    Optional<Customer> update(Customer obj) throws DataException;
    @Override
    Customer create(Customer obj) throws DataException;

    Iterable<Customer> findByNation(String nation) throws DataException;

    Optional<Customer> findByIdWithOrders(int id) throws DataException;
}
