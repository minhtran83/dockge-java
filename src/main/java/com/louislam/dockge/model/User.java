package com.louislam.dockge.model;


/**
 * User entity representing a Dockge user.
 * 
 * TODO: Complete implementation in Phase 2
 * - Add fields: username, passwordHash, email, createdAt, updatedAt
 * - Add authentication-related fields
 */
public class User {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
