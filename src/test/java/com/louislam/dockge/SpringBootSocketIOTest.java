package com.louislam.dockge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Concrete implementation of DockgeSocketIOTest for testing the new Spring Boot backend.
 * 
 * Automatically bootstraps the Spring context and starts the Socket.IO server.
 */
@Tag("springboot")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class SpringBootSocketIOTest extends DockgeSocketIOTest {

    @LocalServerPort
    protected int localPort;

    @Value("${socket-io.port:5051}")
    protected int springSocketIOPort;

    @BeforeEach
    @Override
    public void setUp() throws Exception {
        // Sync the ports from Spring Boot to the base class
        port = localPort;
        backendType = "spring";
        
        // IMPORTANT: In the Spring Boot implementation, we use a separate port for Socket.IO
        // compared to the Tomcat port.
        sharedSocketUrl = "http://localhost:" + springSocketIOPort;
        
        super.setUp();
    }

    private String sharedSocketUrl;

    @Override
    protected String getSocketIOUrl() {
        return sharedSocketUrl != null ? sharedSocketUrl : "http://localhost:" + springSocketIOPort;
    }
}
