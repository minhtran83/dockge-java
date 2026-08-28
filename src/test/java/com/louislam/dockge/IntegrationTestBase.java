package com.louislam.dockge;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for all integration tests.
 * Can test against either Node.js backend or Spring Boot backend.
 * 
 * Set environment variable TEST_BACKEND_PORT to test against running backend.
 * Default: 5001 (Node.js backend)
 */
public abstract class IntegrationTestBase {

    protected static int port;
    protected static String backendType;
    protected String baseUrl;

    @BeforeAll
    public static void setUpOnce() {
        // Check if testing against external backend (Node.js or running Spring Boot)
        String portEnv = System.getenv("TEST_BACKEND_PORT");
        if (portEnv != null && !portEnv.isEmpty()) {
            port = Integer.parseInt(portEnv);
            backendType = "external";
            System.out.println("🔗 Testing against external backend on port: " + port);
        } else {
            // Default to Node.js backend port
            port = 5001;
            backendType = "nodejs";
            System.out.println("🔗 Testing against Node.js backend on port: " + port);
        }
    }

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
     * Get the Socket.IO URL for testing (Node.js backend)
     */
    protected String getSocketIOUrl() {
        return "http://localhost:" + port;
    }

    /**
     * Check if testing against Node.js backend
     */
    protected boolean isNodeBackend() {
        return "nodejs".equals(backendType);
    }

    /**
     * Check if testing against Spring Boot backend
     */
    protected boolean isSpringBackend() {
        return "spring".equals(backendType);
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
