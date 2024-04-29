package org.generation.italy.introjdbc.model.repositories;

import java.util.Optional;

import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.exceptions.DataException;

public interface CategoryRepository {
    Iterable<Category> getAll() throws DataException;
    Iterable<Category> getByNameLike(String part) throws DataException;
    Optional<Category> findById(int id) throws DataException;
    boolean deleteById(int id) throws DataException;
    Optional<Category> update(Category newCategory) throws DataException;
    void create(Category category) throws DataException;

}
