package org.generation.italy.introjdbc.model.repositories.implementations;

import java.sql.Connection;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class JdbcCustomerRepositoryTest {
//findByIdWithOrders
    private Connection conn;
    private JdbcCustomerRepository repo;

    private static final String TEST_COMPANY_NAME1="TEST_COMPANY_NAME1";
    private static final String TEST_CONTACTNAME1="TEST_CONTACTNAME1";
    private static final String TEST_CONTACTTITLE1="TEST_CONTACTTITLE1";
    private static final String TEST_ADDRESS1="TEST_ADDRESS1";
    private static final String TEST_CITY1="TEST_CITY1";
    private static final String TEST_COUNTRY1="TEST_COUNTRY1";
    private static final String TEST_PHONE1="TEST_PHONE1";
    private static final LocalDate TEST_ORDERDATE1=LocalDate.of(2000,1,1);
     private static final LocalDate TEST_REQUIREDDATE1=LocalDate.of(2010,1,1);
    private static final double TEST_FREIGHT1=100;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
    }
}