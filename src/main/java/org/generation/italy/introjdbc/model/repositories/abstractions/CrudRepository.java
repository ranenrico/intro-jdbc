package org.generation.italy.introjdbc.model.repositories.abstractions;

import java.util.List;
import java.util.Optional;

import org.generation.italy.introjdbc.model.exceptions.DataException;

public interface CrudRepository<T, ID> {

    T save(T entity) throws DataException;
    Optional<T> findById(ID id) throws DataException;
    List<T> findAll() throws DataException;
    void update(T newEntity) throws DataException;
    void deleteById(ID id) throws DataException;
}
