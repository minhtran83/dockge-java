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
    protected int socketIOPort;

    @Autowired
    protected List<JpaRepository<?, ?>> repositories;

    @BeforeEach
    public void setUpSpringBoot() {
        // Sync the static port in parent class with the random port from Spring Boot
        port = localPort;
        backendType = "spring";
        super.setUpBase();
    }

    @Override
    protected String getSocketIOUrl() {
        return "http://localhost:" + socketIOPort;
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
