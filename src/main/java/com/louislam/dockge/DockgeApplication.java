package com.louislam.dockge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Boot entry point for the Dockge application.
 * 
 * This is the main application class that bootstraps the Spring Boot container.
 * It enables component scanning, auto-configuration, scheduling, and async support.
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class DockgeApplication {

    private static final Logger logger = LoggerFactory.getLogger(DockgeApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DockgeApplication.class, args);
        logger.info("Dockge Application started successfully.");
    }
}
