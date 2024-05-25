package org.generation.italy.introjdbc.model.repositories.implementations;

<<<<<<< HEAD
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
=======

import java.io.FileReader;
import java.io.IOException;
>>>>>>> origin/francesca_piccitto
import java.util.List;
import java.util.Optional;

import org.generation.italy.introjdbc.model.Category;
<<<<<<< HEAD
import org.generation.italy.introjdbc.model.exceptions.DataException;
=======
import org.generation.italy.introjdbc.model.exeptions.DataException;
>>>>>>> origin/francesca_piccitto
import org.generation.italy.introjdbc.model.repositories.abstractions.CategoryRepository;

public class CsvCategoryRepository implements CategoryRepository{

<<<<<<< HEAD
    

    @Override
    public List<Category> getByNameLike(String part) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByNameLike'");
=======
    @Override
    public List<Category> findAll() throws DataException {
        try(FileReader fr = new FileReader("categories.csv")){
            return null;
        } catch(IOException e ){
            throw new DataException("Errore nella lettura delle categorie da file csv", e);
        }
>>>>>>> origin/francesca_piccitto
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
<<<<<<< HEAD
    public List<Category> findAll() throws DataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
=======
    public void update(Category entity) throws DataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
>>>>>>> origin/francesca_piccitto
    }

    @Override
    public void deleteById(Integer id) throws DataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
<<<<<<< HEAD
    public void update(Category newEntity) throws DataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

=======
    public Iterable<Category> getByNameLike(String part) throws DataException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByNameLike'");
    }


>>>>>>> origin/francesca_piccitto
}
