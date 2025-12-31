package com.louislam.dockge.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Spring Security configuration for the Dockge application.
 * 
 * Handles authentication and authorization using JWT tokens.
 * WebSocket connections are authenticated via JWT tokens in the Socket.IO handshake.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configure HTTP security with stateless authentication (JWT).
     * Socket.IO connections will use JWT tokens for authentication.
     */
    // TODO: Implement JWT filter and security chains
    // This will be configured in Phase 2 when implementing JWT authentication
}
