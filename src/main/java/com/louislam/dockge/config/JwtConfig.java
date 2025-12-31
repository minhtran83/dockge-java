package com.louislam.dockge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * JWT configuration for the Dockge application.
 * 
 * Manages JWT token generation and validation settings.
 * Uses environment variables or application properties for secret key and expiration.
 */
@Configuration
public class JwtConfig {

    @Value("${dockge.jwt.secret:default-secret-key-change-in-production}")
    private String secret;

    @Value("${dockge.jwt.expiration:604800000}")
    private long expiration;

    public String getSecret() {
        return secret;
    }

    public long getExpiration() {
        return expiration;
    }

    // TODO: Implement JWT utility methods for token generation and validation
    // This will be implemented in Phase 2 when implementing JWT authentication
}
