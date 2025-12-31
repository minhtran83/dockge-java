package com.louislam.dockge.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * User entity representing a Dockge user.
 * 
 * Matches original Knex migration schema.
 */
@Entity
@Table(name = "`user`" ) // Quoted as user is a reserved word
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private boolean active = true;

    private String timezone;

    @Column(name = "twofa_secret", length = 64)
    private String twofaSecret;

    @Column(name = "twofa_status", nullable = false)
    private boolean twofaStatus = false;

    @Column(name = "twofa_last_token", length = 6)
    private String twofaLastToken;

    // Standard getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }

    public String getTwofaSecret() { return twofaSecret; }
    public void setTwofaSecret(String twofaSecret) { this.twofaSecret = twofaSecret; }

    public boolean isTwofaStatus() { return twofaStatus; }
    public void setTwofaStatus(boolean twofaStatus) { this.twofaStatus = twofaStatus; }

    public String getTwofaLastToken() { return twofaLastToken; }
    public void setTwofaLastToken(String twofaLastToken) { this.twofaLastToken = twofaLastToken; }
}
