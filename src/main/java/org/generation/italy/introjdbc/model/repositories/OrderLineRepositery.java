package org.generation.italy.introjdbc.model.repositories;

import java.util.Optional;

import org.generation.italy.introjdbc.model.OrderLine;
import org.generation.italy.introjdbc.model.exceptions.DataException;

public interface OrderLineRepositery {

    Iterable<OrderLine> getAll() throws DataException;
    Iterable<OrderLine> getByNameLike(String part) throws DataException;
    Optional<OrderLine> findById(int id) throws DataException;
    boolean deleteById(int id) throws DataException;
    Optional<OrderLine> update(OrderLine newOrderLine) throws DataException;
    OrderLine create(OrderLine orderLine) throws DataException;
    
}
