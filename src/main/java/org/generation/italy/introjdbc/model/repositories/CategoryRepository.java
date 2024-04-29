package org.generation.italy.introjdbc.model.repositories;

import java.util.Optional;

import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.exceptions.DataException;

public interface CategoryRepository {
    Iterable<Category> getAll() throws DataException;
    Iterable<Category> getByNameLike(String part);
    Optional<Category> findById(int id);
    boolean deleteById(int id);
    Optional<Category> update(Category newCategory);
    void create(Category category);

}
