package org.generation.italy.introjdbc.model.repositories.implementations;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.model.repositories.abstractions.CategoryRepository;

public class CsvCategoryRepository implements CategoryRepository{

    

    @Override
    public List<Category> getByNameLike(String part) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByNameLike'");
    }

    @Override
    public Category save(Category entity) throws DataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Optional<Category> findById(Integer id) throws DataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Category> findAll() throws DataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void deleteById(Integer id) throws DataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public void update(Category newEntity) throws DataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

}
