-- Initial Schema for Dockge (SQLite)
-- Aligned with original Node.js Knex migrations

-- User Table
CREATE TABLE IF NOT EXISTS "user" (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    active BOOLEAN NOT NULL DEFAULT 1,
    timezone VARCHAR(150),
    twofa_secret VARCHAR(64),
    twofa_status BOOLEAN NOT NULL DEFAULT 0,
    twofa_last_token VARCHAR(6)
);

-- Agent Table
CREATE TABLE IF NOT EXISTS agent (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    url VARCHAR(255) NOT NULL UNIQUE,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT 1
);

-- Setting Table
CREATE TABLE IF NOT EXISTS setting (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    "key" VARCHAR(200) NOT NULL UNIQUE,
    "value" TEXT,
    "type" VARCHAR(20)
);

-- Stack Table (Additional for Spring Boot version)
CREATE TABLE IF NOT EXISTS stack (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    compose_content TEXT,
    environment TEXT,
    agent_id INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (agent_id) REFERENCES agent(id)
);
