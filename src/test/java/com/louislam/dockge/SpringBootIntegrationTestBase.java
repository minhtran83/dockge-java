package com.louislam.dockge;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.boot.test.web.server.LocalServerPort;
import java.util.List;

/**
 * Base class for integration tests that require a running Spring Boot context.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class SpringBootIntegrationTestBase extends IntegrationTestBase {

    @LocalServerPort
    protected int localPort;

    @Value("${socket-io.port:5051}")
    protected int springSocketIOPort;

    @Autowired
    protected List<JpaRepository<?, ?>> repositories;

    @BeforeEach
    @Override
    public void setUpBase() {
        // Sync the instance port with the random port from Spring Boot
        this.port = localPort;
        
        baseUrl = "http://localhost:" + port;
        io.restassured.RestAssured.port = port;
        io.restassured.RestAssured.baseURI = "http://localhost";
    }

    protected String getSpringSocketIOUrl() {
        return "http://localhost:" + springSocketIOPort;
    }

    /**
     * Clear all data from the database
     */
    protected void clearDatabase() {
        repositories.forEach(repo -> {
            try {
                repo.deleteAllInBatch();
            } catch (Exception e) {
                repo.deleteAll();
            }
        });
    }
}
