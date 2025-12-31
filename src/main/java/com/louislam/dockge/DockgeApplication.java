package com.louislam.dockge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Boot entry point for the Dockge application.
 * 
 * This is the main application class that bootstraps the Spring Boot container.
 * It enables component scanning, auto-configuration, and scheduling support.
 */
@SpringBootApplication
@EnableScheduling
public class DockgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockgeApplication.class, args);
    }
}
