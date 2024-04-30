package org.generation.italy.introjdbc.model.repositories;

import java.util.Optional;

import org.generation.italy.introjdbc.model.Order;
import org.generation.italy.introjdbc.model.exceptions.DataException;

public interface OrderRepository {
    Iterable<Order> getAll() throws DataException;
    Iterable<Order> getByNameLike(String part) throws DataException;
    Optional<Order> findById(int id) throws DataException;
    boolean deleteById(int id) throws DataException;
    Optional<Order> update(Order newOrder) throws DataException;
    Order create(Order order) throws DataException;
}
