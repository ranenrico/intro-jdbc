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
<<<<<<< HEAD
            INSERT INTO categories
            (categoryName, description)
            VALUES(?,?)
            """;

    private JdbcTemplate<Category> template = new JdbcTemplate<>();

    @Override
    public List<Category> findAll() throws DataException {
        return template.query(ALL_CATEGORIES, this::fromResultSet);
=======
                                                        INSERT INTO categories
                                                        (categoryName, description)
                                                        VALUES(?,?)
                                                        """;
   private JdbcTemplate<Category> template ;

    public JdbcCategoryRepository(Connection c) {//iniezione
        template=new JdbcTemplate<>(c);
    }

    @Override
    public List<Category> findAll() throws DataException {
        return template.query(ALL_CATEGORIES, this::fromResultSet);         
>>>>>>> main
    }

    @Override
    public Iterable<Category> getByNameLike(String part) throws DataException {
<<<<<<< HEAD
        return template.query(ALL_CATEGORIES_NAME_LIKE, ps -> ps.setString(1, "%" + part + "%"), this::fromResultSet);
    }

=======
        return template.query(ALL_CATEGORIES_NAME_LIKE,
                              (ps)-> ps.setString(1, "%" + part + "%"),
                              rs -> new Category(rs.getInt("categoryid"),
                                                 rs.getString("categoryname"),
                                                 rs.getString("description"))
        );
    }            
>>>>>>> main
    @Override
    public void deleteById(Integer id) throws DataException {
        template.update(DELETE_BY_ID, id);
    }
    @Override
    public Optional<Category> findById(Integer id) throws DataException {
        return template.queryForObject(CATEGORY_BY_ID, this::fromResultSet, id);
    }

    @Override
    public void update(Category newCategory) throws DataException {
<<<<<<< HEAD
        Optional<Category> oldCategory = findById(newCategory.getId());
        if (oldCategory.isEmpty()) {
            throw new RuntimeException();
        }
        try (
                Connection c = ConnectionUtils.createConnection();
                PreparedStatement ps = c.prepareStatement(UPDATE_CATEGORY);) {
            ps.setString(1, newCategory.getName());
            ps.setString(2, newCategory.getDescription());
            ps.setInt(3, newCategory.getId());
            ps.executeUpdate();
            // if(n!=1){
            // return Optional.empty();
            // }

        } catch (SQLException e) {
            throw new DataException("Errore nella modifica di categorie ", e);
        }
=======
        template.update(UPDATE_CATEGORY,
                        newCategory.getName(),
                        newCategory.getDescription(),
                        newCategory.getId());  
>>>>>>> main
    }

    @Override
    public Category save(Category category) throws DataException {
        KeyHolder kh = new KeyHolder();
<<<<<<< HEAD
        template.insert(INSERT_CATEGORY, kh, category.getName(), category.getDescription());
        category.setCategoryId(kh.getKey().intValue());
        return category;
    }

    private Category fromResultSet(ResultSet rs)throws SQLException{
        return new Category(rs.getInt("categoryid"), 
                            rs.getString("categoryname"), 
                            rs.getString("description"));
    }
}
=======
        template.insert(INSERT_CATEGORY,
                        kh,
                        category.getName(),
                        category.getDescription());
        category.setCategoryId(kh.getKey().intValue());
        return category;
        }

        private Category fromResultSet(ResultSet rs) throws SQLException{
            return new Category(rs.getInt("categoryid"),
                                rs.getString("categoryname"),
                                rs.getString("description"));
        }
    }




>>>>>>> main
