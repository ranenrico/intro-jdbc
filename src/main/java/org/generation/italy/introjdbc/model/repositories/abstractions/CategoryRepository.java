package org.generation.italy.introjdbc.model.repositories.abstractions;

import java.util.Optional;
import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.exceptions.DataException;

public interface CategoryRepository extends CrudRepository<Category, Integer>{
    Iterable<Category> getByNameLike(String part) throws DataException;

}
