package org.generation.italy.introjdbc.model.repositories;

import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.exceptions.DataException;

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
    public Iterable<Category> getByNameLike(String part) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByNameLike'");
    }

    @Override
    public Optional<Category> findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public boolean deleteById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Optional<Category> update(Category newCategory) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void create(Category category) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

}
