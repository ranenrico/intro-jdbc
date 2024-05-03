package org.generation.italy.introjdbc.model.repositories.abstractions;

import java.util.List;
import java.util.Optional;

import org.generation.italy.introjdbc.model.exceptions.DataException;

<<<<<<< HEAD
public interface CrudRepository <T, ID>{
=======
public interface CrudRepository<T, ID> {
>>>>>>> main

    T save(T entity) throws DataException;
    Optional<T> findById(ID id) throws DataException;
    List<T> findAll() throws DataException;
<<<<<<< HEAD
    void update(T entity) throws DataException;
=======
    void update(T newEntity) throws DataException;
>>>>>>> main
    void deleteById(ID id) throws DataException;
}
