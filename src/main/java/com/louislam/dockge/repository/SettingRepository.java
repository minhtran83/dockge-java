package com.louislam.dockge.repository;

import com.louislam.dockge.model.Setting;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for Setting entities.
 * 
 * Provides database access methods for application settings.
 */
public interface SettingRepository { // extends JpaRepository<Setting, Long> {
    // TODO: Add custom query methods in Phase 2
    // - findByKey(String key)
}
