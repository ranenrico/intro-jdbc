package org.generation.italy.introjdbc.model.repositories.implementations;

import java.io.FileReader;
import java.io.IOException;
<<<<<<< HEAD
=======
import java.sql.SQLException;
>>>>>>> main
import java.util.List;
import java.util.Optional;

import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.model.repositories.abstractions.CategoryRepository;

public class CsvCategoryRepository implements CategoryRepository{

    

    @Override
    public Category save(Category entity) throws DataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
<<<<<<< HEAD
=======
    public Category save(Category entity) throws DataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
>>>>>>> main
    public Optional<Category> findById(Integer id) throws DataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Category> findAll() throws DataException {
<<<<<<< HEAD
=======
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void deleteById(Integer id) throws DataException {
>>>>>>> main
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
<<<<<<< HEAD
    public void update(Category entity) throws DataException {
=======
    public void update(Category newEntity) throws DataException {
>>>>>>> main
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

<<<<<<< HEAD
    @Override
    public void deleteById(Integer id) throws DataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Iterable<Category> getByNameLike(String part) throws DataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByNameLike'");
    }

=======
>>>>>>> main
}
