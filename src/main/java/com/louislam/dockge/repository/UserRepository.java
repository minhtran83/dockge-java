package com.louislam.dockge.repository;

import com.louislam.dockge.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for User entities.
 * 
 * Provides database access methods for user-related operations.
 */
public interface UserRepository { // extends JpaRepository<User, Long> {
    // TODO: Add custom query methods in Phase 2
    // - findByUsername(String username)
    // - existsByUsername(String username)
}
