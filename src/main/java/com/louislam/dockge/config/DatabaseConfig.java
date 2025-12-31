package com.louislam.dockge.config;

import org.springframework.context.annotation.Configuration;

/**
 * Database configuration for the Dockge application.
 * 
 * Manages database connection settings and JPA/Hibernate configuration.
 * Supports multiple databases:
 * - Development: SQLite (default)
 * - Testing: In-memory SQLite
 * - Production: PostgreSQL
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Database configuration for the Dockge application.
 * 
 * Manages database connection settings and JPA/Hibernate configuration.
 * Transaction management is enabled for the entire application.
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
    // Spring Boot auto-configures HikariCP and JPA based on application.yml
}
