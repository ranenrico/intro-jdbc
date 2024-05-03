package org.generation.italy.introjdbc.model.repositories.implementations;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.model.repositories.abstractions.CategoryRepository;

public class CsvCategoryRepository implements CategoryRepository{

    @Override
    public Iterable<Category> getAll() throws DataException {
        try(FileReader fr = new FileReader("categories.csv")){
            return null;
        } catch(IOException e){
            throw new DataException("Errore nella lettura delle categorie da file csv", e);
        }
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
    public void update(Category entity) throws DataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

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

}
