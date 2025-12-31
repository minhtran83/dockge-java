package com.louislam.dockge.model;


/**
 * Stack entity representing a Docker Compose stack in Dockge.
 * 
 * TODO: Complete implementation in Phase 2
 * - Add fields: name, composeContent, environment, agentId, createdAt, updatedAt
 */
public class Stack {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
