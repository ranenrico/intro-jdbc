package org.generation.italy.introjdbc.model.repositories;

import java.util.Optional;

import org.generation.italy.introjdbc.model.exceptions.DataException;

public interface RepositoryInterf<T> {
    Iterable<T> getAll() throws DataException;
    Iterable<T> getByNameLike(String part) throws DataException;
    Optional<T> findById(int id) throws DataException;
    boolean deleteById(int id) throws DataException;
    Optional<T> update(T obj) throws DataException;
    T create(T obj) throws DataException;
}
