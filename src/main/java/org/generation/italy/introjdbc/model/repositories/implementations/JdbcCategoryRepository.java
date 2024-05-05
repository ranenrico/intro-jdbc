package org.generation.italy.introjdbc.model.repositories.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.exeptions.DataException;
import org.generation.italy.introjdbc.model.repositories.abstractions.CategoryRepository;
import org.generation.italy.introjdbc.utils.ConnectionUtils;

public class JdbcCategoryRepository implements CategoryRepository{
    private JdbcTemplate<Category> template = new JdbcTemplate<>();
    private static final String ALL_CATEGORIES = "SELECT categoryid, categoryname, description FROM categories";
    private static final String ALL_CATEGORIES_NAME_LIKE = """
        SELECT categoryid, categoryname, description 
        FROM categories WHERE categoryname LIKE ? 
     """;
    private static final String CATEGORY_BY_ID = """
        SELECT categoryid, categoryname, description 
        FROM categories WHERE categoryid = ? 
    """;
    private static final String DELETE_BY_ID = """
        DELETE 
        FROM categories WHERE categoryid = ? 
    """;
    private static final String UPDATE_CATEGORY = """
        UPDATE categories  
        SET categoryname = ?, description = ?
        WHERE categoryid = ?
    """;
    private static final String INSERT_CATEGORY = """
        INSERT INTO categories (categoryname, description)
        VALUES(?, ?) 
    """;

    @Override
    public List<Category> findAll() throws DataException {
        return template.query(ALL_CATEGORIES, this::fromResultSet);
        // try(Connection c = ConnectionUtils.createConnection();
        //     Statement statement = c.createStatement();
        //     ResultSet rs = statement.executeQuery(ALL_CATEGORIES)){ //ResultSet non ha i dati, che sono ancora nel db
        //         List<Category> cats = new ArrayList<>();
        //         while (rs.next()){
        //             cats.add(new Category(rs.getInt("categoryid"), rs.getString("categoryname"), rs.getString("description")));
        //         }
        //         return cats;
        // } catch(SQLException e){
        //     throw new DataException("Errore nella lettura delle categorie dal database", e);
        // }
    }

    @Override
    public Iterable<Category> getByNameLike(String part) throws DataException{ 
        return template.query(ALL_CATEGORIES_NAME_LIKE, ps -> ps.setString(1, "%"+part+"%"), this::fromResultSet);
    }

    @Override
    public Optional<Category> findById(Integer id) throws DataException{
        return template.queryForObject(CATEGORY_BY_ID, this::fromResultSet, id);
        // try(Connection c = ConnectionUtils.createConnection();
        //     PreparedStatement ps = c.prepareStatement(CATEGORY_BY_ID);){
        //     ps.setInt(1, id);
        //     try (ResultSet rs = ps.executeQuery()){
        //         if (rs.next()){
        //             return Optional.of(new Category(rs.getInt("categoryid"), rs.getString("categoryname"), rs.getString("description")));
        //         } else {
        //             return Optional.empty();
        //         }
        //     }   
        // } catch (SQLException e) {
        //     throw new DataException("Errore nella ricerca di categorie per id", e);
        // }
    }

    @Override
    public void deleteById(Integer id) throws DataException{
        template.update(DELETE_BY_ID, id);
        // try(Connection c = ConnectionUtils.createConnection();
        //     PreparedStatement ps = c.prepareStatement(DELETE_BY_ID);){
        //     ps.setInt(1, id);
        //     int n = ps.executeUpdate(); //restituisce il numero di righe che vengono toccate da 
        //     throw new RuntimeException();              //questo statement, ossia quante righe ha modificato (in questo caso che ha cancellato)
        // }catch (SQLException e) {
        //     throw new DataException("Errore di eliminazione di categorie per id", e);
        // }    
    }

    @Override
    public void update(Category newCategory) throws DataException {
        template.update(UPDATE_CATEGORY, newCategory.getName(), newCategory.getDescription(), newCategory.getId());
        // Optional<Category> oldCat = findById(newCategory.getId());
        // if(oldCat.isEmpty()){
        //     throw new RuntimeException();
        // }
        // try(Connection c = ConnectionUtils.createConnection();
        //     PreparedStatement ps = c.prepareStatement(UPDATE_CATEGORY);){
        //     ps.setString(1, newCategory.getName());
        //     ps.setString(2, newCategory.getDescription());
        //     ps.setInt(3, newCategory.getId());
        //     ps.executeUpdate();
        //     throw new RuntimeException();
        // } catch (SQLException e) {
        //     throw new DataException("Errore nella modifica di categorie", e);
        // }
    }

    @Override
    public Category save(Category category) throws DataException {
        KeyHolder key = new KeyHolder();
        template.update(INSERT_CATEGORY, key, category.getName(), category.getDescription());
        category.setId(key.getKey().intValue());
        return category;
        // try(Connection c = ConnectionUtils.createConnection();
        //     PreparedStatement ps = c.prepareStatement(INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS);){
        //     ps.setString(1, category.getName());
        //     ps.setString(2, category.getDescription());
        //     ps.executeUpdate();
        //     ResultSet rs = ps.getGeneratedKeys();
        //     if(rs.next()){
        //         int key = rs.getInt(1);
        //         category.setCategoryId(key);
        //     }    
        //     return category;         
        // }catch (SQLException e) {
        //     throw new DataException("Errore nell'aggiunta di categorie", e);
        // } 
    }

    private Category fromResultSet(ResultSet rs) throws SQLException {
        return new Category(rs.getInt("categoryid"), rs.getString("categoryname"), rs.getString("description"));
    }

}
