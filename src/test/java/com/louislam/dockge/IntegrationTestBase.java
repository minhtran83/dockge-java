package com.louislam.dockge;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for all integration tests.
 * Can test against either Node.js backend or Spring Boot backend.
 */
public abstract class IntegrationTestBase {

    protected static int port;
    protected static String backendType;
    protected String baseUrl;

    @BeforeAll
    public static void setUpOnce() {
        // Use system property first (passed from Maven), then env var, then default
        String portProp = System.getProperty("TEST_BACKEND_PORT");
        if (portProp == null || portProp.isEmpty() || "${TEST_BACKEND_PORT}".equals(portProp)) {
            portProp = System.getenv("TEST_BACKEND_PORT");
        }

        if (portProp != null && !portProp.isEmpty() && !"${TEST_BACKEND_PORT}".equals(portProp)) {
            port = Integer.parseInt(portProp);
            backendType = "external";
            System.out.println("🔗 Testing against external backend on port: " + port);
        } else {
            // Check if we are running in Spring Boot context (port will be set by subclass)
            if (port == 0) {
                // Default to Node.js backend port
                port = 5001;
                backendType = "nodejs";
                System.out.println("🔗 Testing against Node.js backend on port: " + port);
            } else {
                backendType = "spring";
                System.out.println("🔗 Testing against Spring Boot backend on port: " + port);
            }
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
     * Get the Socket.IO URL for testing
     */
    protected String getSocketIOUrl() {
        // If testing against Spring Boot, use the specific socket-io port if known
        // Otherwise use the main port
        return "http://localhost:" + port;
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

    protected boolean isNodeBackend() {
        return "nodejs".equals(backendType);
    }

    protected boolean isSpringBackend() {
        return "spring".equals(backendType);
    }
}
