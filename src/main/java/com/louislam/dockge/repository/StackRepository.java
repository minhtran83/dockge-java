package com.louislam.dockge.repository;

import com.louislam.dockge.model.Stack;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for Stack entities.
 * 
 * Provides database access methods for stack-related operations.
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StackRepository extends JpaRepository<Stack, Long> {
    Optional<Stack> findByName(String name);
    List<Stack> findByAgentId(Long agentId);
}
