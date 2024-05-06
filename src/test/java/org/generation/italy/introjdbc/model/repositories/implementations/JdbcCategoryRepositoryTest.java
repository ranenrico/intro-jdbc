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
    private static final String TEST_NAME_LIKE="TEST";
    private static final String DESCRIPTION1="DESCRIPTION1";
    private static final String DESCRIPTION2="DESCRIPTION2";
    private static Connection c;
    private static JdbcCategoryRepository repo;
    static Category c1;
    static Category c2;
    @BeforeAll
    static void setUpAll(){
        //System.out.println("setUpAll");
        c1=new Category(0,TEST_NAME1, DESCRIPTION1);
        c2=new Category(0,TEST_NAME2, DESCRIPTION2);
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

        assertTrue(c1.getName().contains(TEST_NAME_LIKE));
        assertTrue(c2.getName().contains(TEST_NAME_LIKE));
    }
    @Test
    void deleteById(){
        try {
           repo.deleteById(c1.getId());
            assertNotNull(c1);
        } catch (DataException e) {
            fail(e.getMessage());
        }
    }
    @Test
    void findById(){
        try{
            assertNotNull(repo.findById(c1.getId()));
        } catch (DataException e) {
            fail(e.getMessage());
        }
    }
    @Test
    void update(){
        try {
//            c1.setName(TEST_NAME2);
//            c1.setDescription(DESCRIPTION2);
//            repo.update(c1);
//            assertNotEquals(TEST_NAME1,c1.getName());
//            assertNotEquals(DESCRIPTION1,c1.getDescription());

            Optional<Category> c3= repo.findById(c1.getId());/////
            c3.get().setName(TEST_NAME2);
            c3.get().setDescription(DESCRIPTION2);
            repo.update(c3.get());
            assertNotEquals(TEST_NAME1,c3.get().getName());
            assertNotEquals(DESCRIPTION1,c3.get().getDescription());
        } catch (DataException e) {
            fail(e.getMessage());
        }


    }


}