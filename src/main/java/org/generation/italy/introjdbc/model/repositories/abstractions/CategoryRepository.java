package org.generation.italy.introjdbc.model.repositories.abstractions;


import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.exceptions.DataException;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer>{
    List<Category> getByNameLike(String part) throws DataException;

}
