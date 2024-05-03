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
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.model.repositories.abstractions.CategoryRepository;
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
    private static final String DELETE_BY_ID = """
                                                    DELETE FROM categories
                                                    WHERE categoryid = ?
                                                    """;
    private static final String UPDATE_CATEGORY = """
                                                    UPDATE categories 
                                                    SET categoryname=?, description=?
                                                    where categoryid=?
                                                    """;
    private static final String INSERT_CATEGORY = """
                                                        INSERT INTO categories
                                                        (categoryName, description)
                                                        VALUES(?,?)
                                                        """;
<<<<<<< HEAD

    private JdbcTemplate<Category> template = new JdbcTemplate<>();

    
=======
    private JdbcTemplate<Category> template = new JdbcTemplate<>();

>>>>>>> main
    @Override
    public List<Category> findAll() throws DataException {
        try(
            Connection c = ConnectionUtils.createConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(ALL_CATEGORIES)){
                List<Category> cats = new ArrayList<>();
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
<<<<<<< HEAD
        return template.query(ALL_CATEGORIES_NAME_LIKE, ps -> ps.setString(1, "%" + part + "%"), 
        rs -> 
=======
        return template.query(ALL_CATEGORIES_NAME_LIKE, ps -> ps.setString(1, "%" + part + "%"),
        rs -> new Category(rs.getInt("categoryid"), rs.getString("categoryname"), rs.getString("description"))
>>>>>>> main
        );
    }

    @Override
    public void deleteById(Integer id) throws DataException {
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(DELETE_BY_ID); 
        ){
            ps.setInt(1, id);
            int n = ps.executeUpdate();
        }catch(SQLException e){
            throw new DataException("Errore nell'eliminazione di categorie per id", e);
        }
    }

    @Override
    public Optional<Category> findById(Integer id) throws DataException {
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
    public void update(Category newCategory) throws DataException {
        Optional<Category> oldCategory= findById(newCategory.getId());
        if(oldCategory.isEmpty()){
            throw new RuntimeException();
        }
        try(
            Connection c = ConnectionUtils.createConnection();
            PreparedStatement ps = c.prepareStatement(UPDATE_CATEGORY);
        ){
            ps.setString (1, newCategory.getName());
            ps.setString(2, newCategory.getDescription());
            ps.setInt(3,newCategory.getId());
            ps.executeUpdate();
            // if(n!=1){
            //     return Optional.empty();
            // }

        }catch(SQLException e){
            throw new DataException("Errore nella modifica di categorie ", e);
        }
    }


    @Override
    public Category save(Category category) throws DataException {
            try(
                Connection c = ConnectionUtils.createConnection();
                PreparedStatement ps = c.prepareStatement(INSERT_CATEGORY,Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1,category.getName());
                ps.setString(2,category.getDescription());
                ps.executeUpdate();
                ResultSet rs=ps.getGeneratedKeys();
                if(rs.next()){
                    int key=rs.getInt(1);
                    category.setCategoryId(key);
                }
                return category;
            }catch(SQLException e){
                throw new DataException("Errore nell'aggiunta di categorie ", e);
            }
        }
    }


