package fr.imt.raimed2.integration;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractIntegrationTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:latest");

    @BeforeAll()
    static void beforeAll() {
        POSTGRE_SQL_CONTAINER.start();
    }

    @AfterAll()
    static void afterAll() {
        POSTGRE_SQL_CONTAINER.stop();
    }

}
