package com.louislam.dockge.repository;

import com.louislam.dockge.model.Stack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for Stack entities.
 * 
 * Provides database access methods for stack-related operations.
 */
@Repository
public interface StackRepository extends JpaRepository<Stack, Long> {
    // TODO: Add custom query methods in Phase 2
    // - findByName(String name)
    // - findByAgentId(Long agentId)
}
