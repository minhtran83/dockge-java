-- Initial Schema for Dockge (H2)
-- Aligned with original Node.js Knex migrations

-- User Table
CREATE TABLE IF NOT EXISTS "user" (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    timezone VARCHAR(150),
    twofa_secret VARCHAR(64),
    twofa_status BOOLEAN NOT NULL DEFAULT FALSE,
    twofa_last_token VARCHAR(6)
);

-- Agent Table
CREATE TABLE IF NOT EXISTS agent (
    id BIGSERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Setting Table
CREATE TABLE IF NOT EXISTS setting (
    id BIGSERIAL PRIMARY KEY,
    "key" VARCHAR(200) NOT NULL UNIQUE,
    "value" TEXT,
    "type" VARCHAR(20)
);

-- Stack Table (Additional for Spring Boot version)
CREATE TABLE IF NOT EXISTS stack (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    compose_content TEXT,
    environment TEXT,
    agent_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (agent_id) REFERENCES agent(id)
);
