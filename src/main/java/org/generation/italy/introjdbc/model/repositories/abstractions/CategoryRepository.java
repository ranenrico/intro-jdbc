package org.generation.italy.introjdbc.model.repositories.abstractions;

import java.util.Optional;

import org.generation.italy.introjdbc.model.exceptions.DataException;

public interface CategoryRepository<Category> extends CrudRepository<Category>{

    @Override
    Category create(Category obj) throws DataException;

    @Override
    boolean deleteById(int id) throws DataException;

    @Override
    Optional<Category> findById(int id) throws DataException;

    @Override
    Iterable<Category> getAll() throws DataException;

    @Override
    Iterable<Category> getByNameLike(String part) throws DataException;

    @Override
    void update(Category obj) throws DataException;

}
