package org.generation.italy.introjdbc.model.repositories.abstractions;

<<<<<<< HEAD

import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.exceptions.DataException;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer>{
    List<Category> getByNameLike(String part) throws DataException;

=======
import java.util.Optional;
import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.exeptions.DataException;

public interface CategoryRepository extends CrudRepository<Category, Integer>{
    
    Iterable<Category> getByNameLike(String part) throws DataException;
    
>>>>>>> origin/francesca_piccitto
}
