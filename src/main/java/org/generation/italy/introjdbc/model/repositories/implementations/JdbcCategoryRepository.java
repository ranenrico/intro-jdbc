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

import javax.xml.transform.Result;

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
   private JdbcTemplate<Category> template = new JdbcTemplate<>();

    @Override
    public List<Category> findAll() throws DataException {
        return template.query(ALL_CATEGORIES, this::fromResultSet);         
    }

    @Override
    public Iterable<Category> getByNameLike(String part) throws DataException {
        return template.query(ALL_CATEGORIES_NAME_LIKE,
                              (ps)-> ps.setString(1, "%" + part + "%"),
                              rs -> new Category(rs.getInt("categoryid"),
                                                 rs.getString("categoryname"),
                                                 rs.getString("description"))
        );
    }            
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
        template.update(UPDATE_CATEGORY,
                        newCategory.getName(),
                        newCategory.getDescription(),
                        newCategory.getId());  
    }


    @Override
    public Category save(Category category) throws DataException {
        KeyHolder kh = new KeyHolder();
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




