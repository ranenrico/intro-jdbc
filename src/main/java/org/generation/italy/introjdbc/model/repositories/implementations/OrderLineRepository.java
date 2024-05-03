package org.generation.italy.introjdbc.model.repositories.implementations;

import java.util.Optional;

import org.generation.italy.introjdbc.model.OrderLine;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.model.repositories.abstractions.CrudRepository;

public interface OrderLineRepository<OrderLine> extends CrudRepository<OrderLine>{
    @Override
    Iterable<OrderLine> getAll() throws DataException;
    @Override
    Iterable<OrderLine> getByNameLike(String part) throws DataException;
    @Override
    Optional<OrderLine> findById(int id) throws DataException;
    @Override
    boolean deleteById(int id) throws DataException;
    @Override
    Optional<OrderLine> update(OrderLine newOrderLine) throws DataException;
    @Override
    OrderLine create(OrderLine orderline) throws DataException;
    
}
