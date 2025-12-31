package com.louislam.dockge.model;


/**
 * Agent entity representing a Docker agent in Dockge.
 * 
 * TODO: Complete implementation in Phase 2
 * - Add fields: name, url, scheme, authentication, createdAt, updatedAt
 */
public class Agent {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
