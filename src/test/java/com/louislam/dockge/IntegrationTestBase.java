package com.louislam.dockge;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Base class for all integration tests.
 * Uses Spring Boot Test to bootstrap the application context.
 */
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class IntegrationTestBase {

    @LocalServerPort
    protected int port;

    @Value("${socket-io.port:5051}")
    protected int socketIOPort;

    @Autowired
    protected List<JpaRepository<?, ?>> repositories;

    protected String baseUrl;

    @BeforeEach
    public void setUpBase() {
        baseUrl = "http://localhost:" + port;
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    /**
     * Get the WebSocket URL for testing
     */
    protected String getWebSocketUrl() {
        return "ws://localhost:" + port + "/ws";
    }

    /**
     * Get the Socket.IO URL for testing
     */
    protected String getSocketIOUrl() {
        return "http://localhost:" + socketIOPort;
    }

    /**
     * Clear all data from the database
     */
    protected void clearDatabase() {
        // Delete in reverse order of dependencies if necessary, 
        // but deleteAllInBatch is generally faster and handles constraints if configured correctly
        repositories.forEach(repo -> {
            try {
                repo.deleteAllInBatch();
            } catch (Exception e) {
                repo.deleteAll();
            }
        });
    }

    /**
     * Wait for a condition with timeout
     */
    protected void waitForCondition(BooleanSupplier condition, long timeoutMs) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        while (!condition.getAsBoolean()) {
            if (System.currentTimeMillis() - startTime > timeoutMs) {
                throw new AssertionError("Condition timeout after " + timeoutMs + "ms");
            }
            Thread.sleep(100);
        }
    }

    /**
     * Functional interface for boolean suppliers
     */
    @FunctionalInterface
    protected interface BooleanSupplier {
        boolean getAsBoolean();
    }
}
