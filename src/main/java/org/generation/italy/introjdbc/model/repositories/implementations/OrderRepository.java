package org.generation.italy.introjdbc.model.repositories.implementations;

import java.util.Optional;

import org.generation.italy.introjdbc.model.Order;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.model.repositories.abstractions.CrudRepository;

public interface OrderRepository<Order> extends CrudRepository<Order> {
    @Override
    Iterable<Order> getAll() throws DataException;
    @Override
    Iterable<Order> getByNameLike(String part) throws DataException;
    @Override
    Optional<Order> findById(int id) throws DataException;
    @Override
    boolean deleteById(int id) throws DataException;
    @Override
    Optional<Order> update(Order newOrder) throws DataException;
    @Override
    Order create(Order order) throws DataException;
}
