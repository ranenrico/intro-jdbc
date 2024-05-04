package org.generation.italy.introjdbc.model.repositories.implementations;
import org.generation.italy.introjdbc.model.Category;
import org.generation.italy.introjdbc.model.exceptions.DataException;
import org.generation.italy.introjdbc.utils.ConnectionUtils;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcCategoryRepositoryTest {
    private static final String TEST_NAME1="TEST_NAME1";
    private static final String TEST_NAME2="TEST_NAME2";
    private static final String DESCRIPTION1="DESCRIPTION1";
    private static final String DESCRIPTION2="DESCRIPTION2";
    private static Connection c;
    private static JdbcCategoryRepository repo;
    @BeforeAll
    static void setUpAll(){
        //System.out.println("setUpAll");
        Category c1=new Category(0,TEST_NAME1, DESCRIPTION1);
        Category c2=new Category(0,TEST_NAME2, DESCRIPTION2);
        try {
            c= ConnectionUtils.createConnection();
            c.setAutoCommit(false);
            repo=new JdbcCategoryRepository(c);
            repo.save(c1);
            repo.save(c2);

        } catch (SQLException | DataException e) {
            fail(e.getMessage());
        }
    }
    @AfterAll
    static void afterAll(){
        try {
            c.rollback();
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }
    @BeforeEach
    void setUp() {
        System.out.println("setUp");
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
    }


    @Test
    void findAll() {
        //System.out.println("findAll");
        try {
            List<Category> cats=repo.findAll();//già è iniziallizzato
            assertTrue(cats.stream().anyMatch(c->c.getName().equals(TEST_NAME1)));
            assertTrue(cats.stream().anyMatch(c->c.getName().equals(TEST_NAME2)));
        } catch (DataException e) {
            fail(e.getMessage());
        }
    }
    @Test
    void findAll2() {
        System.out.println("findAll");
    }
}