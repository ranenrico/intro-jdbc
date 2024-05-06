package org.generation.italy.introjdbc.model.repositories.implementations;
import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.utils.ConnectionUtils;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class JdbcCategoryRepositoryTest {
    private static final String TEST_NAME1="TEST_NAME1";
    private static final String TEST_NAME2="TEST_NAME2";
    private static final String TEST_NAME3="TEST_NAME3";
    private static final String TEST_NAME4="TEST_NAME4";
    private static final String TEST_NAME_LIKE="AME1";
    private static final String DESCRIPTION1="DESCRIPTION1";
    private static final String DESCRIPTION2="DESCRIPTION2";
    private static final String DESCRIPTION3="DESCRIPTION3";
    private static final String DESCRIPTION4="DESCRIPTION4";
    private Connection conn;
    private JdbcCategoryRepository repo;
    private Category c1;
    private Category c2;
    private final int NOT_EXISTS=-1;
    private Category category;

    @BeforeEach
    void setUp() {
        c1=new Category(0,TEST_NAME1,DESCRIPTION1);
        c2=new Category(0,TEST_NAME2,DESCRIPTION2);
        category =new Category(0,TEST_NAME3, DESCRIPTION3);
        try {
            conn =ConnectionUtils.createConnection();
            conn.setAutoCommit(false);
            repo =new JdbcCategoryRepository(conn);
            repo.save(c1);
            repo.save(c2);
            repo.save(category);
        } catch (DataException | SQLException e) {
            fail(e.getMessage());
        }

    }

    @AfterEach
    void tearDown() {
        try {
            conn.rollback();
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }


    @Test
    void findAll() {
        //System.out.println("findAll");
        try {
            List<Category> cats=repo.findAll();//già è inizializzato
            assertTrue(cats.stream().anyMatch(c->c.getName().equals(TEST_NAME1)));
            assertTrue(cats.stream().anyMatch(c->c.getName().equals(TEST_NAME2)));
        } catch (DataException e) {
            fail(e.getMessage());
        }
    }
    @Test
    void findAll2() {
        //System.out.println("findAll");
    }
    @Test
    void getByNameLike() {
        try {
            List<Category> cats= repo.getByNameLike(TEST_NAME_LIKE);
            assertEquals(1,cats.size());
            assertEquals(TEST_NAME1,cats.get(0).getName());
            assertEquals(DESCRIPTION1,cats.get(0).getDescription());
        } catch (DataException e) {
            fail(e.getMessage());
        }
    }
    @Test
    void deleteById(){
        try {
            assertTrue(repo.findById(category.getId()).isPresent());
           repo.deleteById(category.getId());
            assertTrue(repo.findById(category.getId()).isEmpty());
        } catch (DataException e) {
            fail(e.getMessage());
        }
    }
    @Test
    void findById_returns_optional_present_when_category_present(){
        try{
            assertTrue(repo.findById(category.getId()).isPresent());
        } catch (DataException e) {
            fail(e.getMessage());
        }
    }
    @Test
    void findById_returns_optional_empty_when_category_not_present(){
        try{
            assertTrue(repo.findById(NOT_EXISTS).isEmpty());
        } catch (DataException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void update(){
        try {
            Optional<Category> oc= repo.findById(category.getId());
            assertTrue(oc.isPresent());
            Category c=oc.get();
            assertEquals(TEST_NAME3,c.getName());
            assertEquals(DESCRIPTION3,c.getDescription());
            Category updated=new Category(c.getId(),TEST_NAME4,DESCRIPTION4);
            repo.update(updated);
            oc= repo.findById(c.getId());
            assertTrue(oc.isPresent());
            c=oc.get();
            assertEquals(TEST_NAME4,c.getName());
            assertEquals(DESCRIPTION4,c.getDescription());

        } catch (DataException e) {
            fail(e.getMessage());
        }
    }

}