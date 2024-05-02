package org.generation.italy.introjdbc.model.repositories;

import java.util.Optional;

import org.generation.italy.introjdbc.model.OrderLine;
import org.generation.italy.introjdbc.model.exceptions.DataException;

public interface OrderLineRepository<OrderLine> extends RepositoryInterf<OrderLine>{
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
