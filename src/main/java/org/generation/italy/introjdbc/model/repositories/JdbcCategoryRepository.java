package org.generation.italy.introjdbc.model.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.utils.ConnectionUtils;

public class JdbcCategoryRepository implements CategoryRepository {
    private static final String ALL_CATEGORIES = "SELECT categoryid, categoryname, description FROM categories";
    private static final String ALL_CATEGORIES_NAME_LIKE = """
                                                                SELECT categoryid, categoryname, description 
                                                                FROM categories
                                                                WHERE categoryname LIKE ?
                                                                """;
    private static final String CATEGORY_BY_ID = """
                                                    SELECT categoryid, categoryname, description 
                                                    FROM categories
                                                    WHERE categoryid = ?
                                                    """;
    private static final String UPDATE_CATEGORY = """
                                                    UPDATE categoryies 
                                                    FROM categories
                                                    WHERE categoryid = ?
                                                    """;
    private static final String DELETE_BY_ID = """
                                                    DELETE FROM categories
                                                    WHERE categoryid = ?
                                                    """;
    @Override
    public Iterable<Category> getAll() throws DataException {
        try(
            Connection c = ConnectionUtils.createConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(ALL_CATEGORIES)){
                Collection<Category> cats = new ArrayList<>();
                while(rs.next()){
                    // int id = rs.getInt("categoryid");
                    // String name = rs.getString("categoryname");
                    // String desc = rs.getString("description");
                    cats.add(new Category(rs.getInt("categoryid"), rs.getString("categoryname"), rs.getString("description")));               
                }
                return cats;
            } catch(SQLException e){
            throw new DataException("Errore nella lettura delle categorie del database", e);
        }
    }

    @Override
    public Iterable<Category> getByNameLike(String part) throws DataException {
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(ALL_CATEGORIES_NAME_LIKE);
        ){
                ps.setString(1, "%"+part+"%");
                try(ResultSet rs = ps.executeQuery()){
                    Collection<Category> cats = new ArrayList<>();
                    while(rs.next()){
                        cats.add(new Category(rs.getInt("categoryid"), rs.getString("categoryname"), rs.getString("description")));
                    }
                    return cats;
                }
                
            } catch(SQLException e){
                throw new DataException("Errore nella ricerca di categorie per nome LIKE", e);
            }
    }

    @Override
    public Optional<Category> findById(int id) throws DataException {
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(CATEGORY_BY_ID);
        ){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(new Category(rs.getInt("categoryid"),
                    rs.getString("categoryname"), rs.getString("description")));
                } else {
                    return Optional.empty();
                }
            }
        }catch(SQLException e){
            throw new DataException("Errore nella ricerca di categorie per id", e);
        }
    }

    @Override
    public boolean deleteById(int id) throws DataException {
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(DELETE_BY_ID); 
        ){
            ps.setInt(1, id);
            int n = ps.executeUpdate();
            return n == 1;
        }catch(SQLException e){
            throw new DataException("Errore nell'eliminazione di categorie per id", e);
        }
    }

    @Override
    public Optional<Category> update(Category newCategory) {
        Optional<Category> oldCat = findById(newCategory.getId());
        if(oldCat.isEmpty()){
            return Optional.empty();
        }
        try (Connection c = ConnectionUtils.createConnection()
        PreparedStatement ps = c.prepareStatement(UPDATE_CATEGORY)) {
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void create(Category category) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

}
