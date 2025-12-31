package com.louislam.dockge;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for all integration tests.
 * Can test against either Node.js backend or Spring Boot backend.
 */
public abstract class IntegrationTestBase {

    protected int port = 5001; // Default to Node.js backend port
    protected String baseUrl;

    @BeforeEach
    public void setUpBase() {
        // Use system property passed from Maven if available
        String portProp = System.getProperty("TEST_BACKEND_PORT");
        if (portProp == null || portProp.isEmpty() || "${TEST_BACKEND_PORT}".equals(portProp)) {
            portProp = System.getenv("TEST_BACKEND_PORT");
        }

        if (portProp != null && !portProp.isEmpty() && !"${TEST_BACKEND_PORT}".equals(portProp)) {
            this.port = Integer.parseInt(portProp);
        }
        
        baseUrl = "http://localhost:" + port;
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    protected String getSocketIOUrl() {
        return "http://localhost:" + port;
    }

    @FunctionalInterface
    protected interface BooleanSupplier {
        boolean getAsBoolean();
    }
}
