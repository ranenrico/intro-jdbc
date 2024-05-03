package org.generation.italy.introjdbc.model.repositories.implementations;

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
import org.generation.italy.introjdbc.model.repositories.abstractions.CategoryRepository;
import org.generation.italy.introjdbc.utils.ConnectionUtils;

public class JdbcCategoryRepository implements CategoryRepository<Category> {
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
                                                    SET  categoryname = ?, description = ?
                                                    WHERE categoryid = ?
                                                    """;
    private static final String INSERT_CATEGORY = """
                                                        INSERT INTO categories
                                                        (categoryName, description)
                                                        VALUES(?, ?)
                                                        """;

    private JdbcTemplate<Category> template = new JdbcTemplate<>();

    @Override
    public Iterable<Category> getAll() throws DataException {
        return template.query(ALL_CATEGORIES, this::fromResulSet);
    }

    @Override
    public Iterable<Category> getByNameLike(String part) throws DataException {
        return template.query(ALL_CATEGORIES_NAME_LIKE, ps -> ps.setString(1, "%"+part+"%"), 
                                rs -> new Category(rs.getInt("categoryid"), rs.getString("categoryname"), rs.getString("description"))
        );
    }

    @Override
    public Optional<Category> findById(int id) throws DataException {
        return template.queryForObject(CATEGORY_BY_ID, 
                                        this::fromResulSet, 
                                        id);
    }

    @Override
    public boolean deleteById(int id) throws DataException {
       return template.update(DELETE_BY_ID, id) != 0;
    }

    @Override
    public void update(Category obj) throws DataException {
        template.update(UPDATE_CATEGORY, obj.getName(), obj.getDescription(), obj.getId());
    }

    @Override
    public Category create(Category obj) throws DataException{
        KeyHolder key = new KeyHolder();
        template.insert(INSERT_CATEGORY, key, obj.getName(), obj.getDescription());
        obj.setCategoryId(key.getKey().intValue());
        return obj;
    }

    private Category fromResulSet(ResultSet rs) throws SQLException{
        return new Category(rs.getInt("categoryid"), rs.getString("categoryname"), rs.getString("description"));
    }

}
