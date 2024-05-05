package org.generation.italy.introjdbc.model.repositories.abstractions;

import java.util.List;
import java.util.Optional;

import org.generation.italy.introjdbc.model.exeptions.DataException;

public interface CrudRepository<T, ID> { //t sarà category o customer, e id sarà il tipo della classe della primary key
    T save(T entity) throws DataException;
    Optional<T> findById(ID id) throws DataException;
    List<T> findAll() throws DataException;
    void update(T entity) throws DataException;
    void deleteById(ID id) throws DataException;
}
