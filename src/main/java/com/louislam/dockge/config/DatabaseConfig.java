package com.louislam.dockge.config;

import org.springframework.context.annotation.Configuration;

/**
 * Database configuration for the Dockge application.
 * 
 * Manages database connection settings and JPA/Hibernate configuration.
 * Supports multiple databases:
 * - Development: SQLite (default)
 * - Testing: In-memory SQLite
 * - Production: MySQL
 */
@Configuration
public class DatabaseConfig {

    // TODO: Add database-specific configurations
    // - Connection pooling (HikariCP)
    // - Dialect selection
    // - Migration support (Flyway or Liquibase)
    // This will be implemented in Phase 2
}
