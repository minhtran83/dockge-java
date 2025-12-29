# Dockge Backend Migration Plan: Node.js/TypeScript → Java Spring Boot

---

## Executive Summary

This document outlines the plan to migrate the Dockge backend from Node.js/TypeScript to Java Spring Boot while maintaining all existing functionality and leveraging Java's enterprise ecosystem.

**Current State:**
- **Backend**: Node.js/TypeScript with Socket.IO (running in production)
- **Tests**: 18 integration tests (100% passing) validating Node.js backend
- **Frontend**: Vue.js (will remain unchanged)
- **Build System**: Maven project with integrated npm/TypeScript pipeline

---

## 1. Current Architecture

### 1.1 Node.js Backend Components

| Component | File | Purpose | Status |
|-----------|------|---------|--------|
| **DockgeServer** | `dockge-server.ts` | Main server, HTTP/WebSocket | ✅ Working |
| **Stack** | `stack.ts` | Docker Compose management | ✅ Working |
| **AgentManager** | `agent-manager.ts` | Multi-agent management | ✅ Working |
| **Terminal** | `terminal.ts` | PTY terminal emulation | ✅ Working |
| **Database** | `database.ts` | SQLite/MySQL abstraction | ✅ Working |
| **SocketHandler** | `main-socket-handler.ts` | WebSocket events | ✅ Working |
| **Settings** | `settings.ts` | Application settings | ✅ Working |
| **Models** | `models/*.ts` | User, Agent models | ✅ Working |

### 1.2 Integration Tests

**File**: `src/test/java/com/louislam/dockge/DockgeSocketIOTest.java`

**Coverage**: 18 comprehensive tests (100% passing)
- Connection & Socket.IO communication
- Authentication (login, JWT tokens)
- Settings management
- Stack operations (CRUD)
- Stack lifecycle (start, stop, restart, update)
- Utilities and error handling

**Technology**: JUnit 5 + Socket.IO Java Client + AssertJ

---

## 2. Technology Stack Mapping

### 2.1 Node.js → Spring Boot Equivalents

| Node.js | Spring Boot | Status |
|---------|------------|--------|
| Express.js | Spring Web MVC | Planned |
| Socket.IO | Spring WebSocket + SockJS | Planned |
| SQLite3 | Spring Data JPA + SQLite JDBC | Planned |
| Redbean ORM | Spring Data JPA + Hibernate | Planned |
| bcryptjs | Spring Security BCrypt | Planned |
| jsonwebtoken | Spring Security + JJWT | Planned |
| node-pty | pty4j (JetBrains) | Planned |
| child_process | ProcessBuilder + Apache Commons Exec | Planned |
| yaml | SnakeYAML | Planned |

### 2.2 Current Maven Setup

**Location**: Root `pom.xml`

**Features**:
- ✅ dependencyManagement (centralized versions)
- ✅ pluginManagement (plugin version control)
- ✅ Properties (all versions configurable)
- ✅ Environment variables as properties
- ✅ Integration test execution
- ✅ Automated backend startup/validation

---

## 3. Goals and Objectives

### 3.1 Primary Goal

**Migrate Dockge backend from Node.js/TypeScript to Java Spring Boot while maintaining 100% feature parity, improving system architecture, and enabling future scalability through GraalVM native compilation.**

### 3.2 Strategic Objectives

**Technical Objectives**:
1. Maintain Feature Parity - All 27 TypeScript files → ~50 Java classes
2. Improve Architecture Quality - Standard Spring Boot patterns
3. Enhance System Reliability - 80%+ test coverage
4. Enable GraalVM Native Compilation - Faster startup, reduced memory

**Operational Objectives**:
1. Improve Deployment Story - Single JAR deployment
2. Enable Better Observability - Metrics and health checks
3. Enhance Security Posture - Spring Security best practices
4. Facilitate Future Growth - Cloud-ready architecture

### 3.3 Quality Objectives

**Code Quality**: 80%+ unit coverage, Spring Boot standards  
**Performance**: < 5s startup (JVM), < 1s (GraalVM), < 512MB memory  
**Security**: Zero critical vulnerabilities, BCrypt + JWT  
**Documentation**: JavaDoc, architecture docs, deployment guides

---

## 4. Migration Strategy

### 4.1 Phased Approach

**Phase 0: Project Documentation & Preparation** (Foundation)
- Document project purpose and goals
- Create comprehensive README for Spring Boot version
- Document current Node.js architecture and features
- Map technology stack migration (Node.js → Spring Boot equivalents)
- Prepare GitHub Issues template for tracking
- Set up GitHub MCP Server with rovo-dev CLI
- Document all 27 TypeScript files → ~50 Java classes mapping
- Create testing strategy documentation
- Prepare deployment and rollout strategy
- Establish coding standards and best practices

**Phase 1: Project Setup**
- Maven project structure created
- pom.xml configured with dependencyManagement
- Integration tests in place (18 tests, 100% passing)
- Backend lifecycle management automated

**Phase 2: Core Models** (Next)
- User entity with JWT auth
- Agent entity
- Stack entity
- Database schema with migrations

**Phase 3: WebSocket Layer** (After Phase 2)
- Spring WebSocket + SockJS configuration
- STOMP messaging for real-time communication
- Socket event handlers

**Phase 4: Business Logic** (After Phase 3)
- Stack operations (CRUD)
- Agent management
- Terminal emulation (pty4j)
- Settings management

**Phase 5: Testing & Validation** (Final)
- Port existing Node.js tests to Spring Boot
- Add new tests for Spring-specific features
- Performance testing
- Load testing

### 3.2 Parallel Operation

During migration:
1. Keep Node.js backend running (production)
2. Develop Spring Boot backend alongside
3. Keep existing 18 integration tests passing
4. Gradually migrate tests to new backend

---

## 5. Prerequisites for Migration

### 5.1 Required Dependencies

```xml
<!-- Core Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- WebSocket -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>

<!-- JPA/Database -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- SQLite Driver -->
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
</dependency>

<!-- Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
</dependency>

<!-- Terminal -->
<dependency>
    <groupId>com.pty4j</groupId>
    <artifactId>pty4j</artifactId>
</dependency>

<!-- YAML -->
<dependency>
    <groupId>org.yaml</groupId>
    <artifactId>snakeyaml</artifactId>
</dependency>

<!-- Testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

### 4.2 Configuration

**application.yml**:
```yaml
spring:
  application:
    name: dockge
  
  datasource:
    url: jdbc:sqlite:${dockge.data.dir}/dockge.db
    driver-class-name: org.sqlite.JDBC
  
  jpa:
    database-platform: org.hibernate.dialect.SQLiteDialect
    hibernate:
      ddl-auto: validate
    show-sql: false

server:
  port: 5001

dockge:
  stacks-dir: ${dockge.stacks.dir}
  data-dir: ${dockge.data.dir}
```

---

## 6. Project Structure (Proposed)

```
dockge/ (root - Maven project)
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/louislam/dockge/
│   │   │   ├── DockgeApplication.java
│   │   │   ├── config/
│   │   │   │   ├── WebSocketConfig.java
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── JwtConfig.java
│   │   │   ├── models/
│   │   │   │   ├── User.java
│   │   │   │   ├── Agent.java
│   │   │   │   └── Stack.java
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── AgentRepository.java
│   │   │   │   └── StackRepository.java
│   │   │   ├── service/
│   │   │   │   ├── UserService.java
│   │   │   │   ├── AgentService.java
│   │   │   │   ├── StackService.java
│   │   │   │   ├── TerminalService.java
│   │   │   │   └── SettingsService.java
│   │   │   ├── controller/
│   │   │   │   ├── MainSocketController.java
│   │   │   │   ├── AgentSocketController.java
│   │   │   │   └── TerminalSocketController.java
│   │   │   ├── dto/
│   │   │   │   ├── StackDTO.java
│   │   │   │   ├── AgentDTO.java
│   │   │   │   └── TerminalDTO.java
│   │   │   └── util/
│   │   │       ├── JwtUtil.java
│   │   │       ├── DockerUtil.java
│   │   │       └── YamlUtil.java
│   │   └── resources/
│   │       └── application.yml
│   │
│   └── test/
│       ├── java/com/louislam/dockge/
│       │   ├── DockgeSocketIOTest.java (existing - port to Spring)
│       │   ├── integration/
│       │   │   ├── WebSocketIntegrationTest.java
│       │   │   ├── StackOperationsTest.java
│       │   │   └── AuthenticationTest.java
│       │   └── unit/
│       │       ├── JwtUtilTest.java
│       │       ├── StackServiceTest.java
│       │       └── TerminalServiceTest.java
│       └── resources/
│           └── application-test.yml
│
├── backend/ (Node.js - keep for reference/comparison)
├── frontend/
├── common/
└── MIGRATION_TO_SPRING_BOOT.md (this file)
```

---

## 7. TypeScript → Java Migration Mapping

### 5.1 Complete File Mapping

#### Core Server & Initialization (3 files)

| TypeScript File | Java Equivalent | Package | Purpose |
|---|---|---|---|
| `index.ts` | `DockgeApplication.java` | `com.louislam.dockge` | Spring Boot main entry point |
| `dockge-server.ts` | `DockgeServer.java` | `com.louislam.dockge.server` | Main server initialization & lifecycle |
| `check-version.ts` | `VersionChecker.java` | `com.louislam.dockge.util` | Version validation utility |

#### Models & Entities (4 files)

| TypeScript File | Java Equivalent | Package | Purpose |
|---|---|---|---|
| `models/user.ts` | `User.java` | `com.louislam.dockge.model.entity` | User entity with JWT auth |
| `models/agent.ts` | `Agent.java` | `com.louislam.dockge.model.entity` | Agent entity for multi-host support |
| (new) | `Stack.java` | `com.louislam.dockge.model.entity` | Stack entity for Docker Compose |
| (new) | `Setting.java` | `com.louislam.dockge.model.entity` | Setting entity for configuration |

#### Repositories & Data Access (6 files)

| TypeScript File | Java Equivalent | Package | Purpose |
|---|---|---|---|
| `database.ts` | `DatabaseConfig.java` | `com.louislam.dockge.config` | Database configuration & migrations |
| `migrations/*` | SQL migration files | `db/migration/` | Database migrations (Flyway) |
| (new) | `UserRepository.java` | `com.louislam.dockge.repository` | Spring Data JPA for User |
| (new) | `AgentRepository.java` | `com.louislam.dockge.repository` | Spring Data JPA for Agent |
| (new) | `StackRepository.java` | `com.louislam.dockge.repository` | Spring Data JPA for Stack |
| (new) | `SettingRepository.java` | `com.louislam.dockge.repository` | Spring Data JPA for Setting |

#### Services & Business Logic (9 files)

| TypeScript File | Java Equivalent | Package | Purpose |
|---|---|---|---|
| `settings.ts` | `SettingService.java` | `com.louislam.dockge.service` | Application settings with caching |
| `stack.ts` | `StackService.java` | `com.louislam.dockge.service` | Docker Compose stack operations |
| `agent-manager.ts` | `AgentService.java` | `com.louislam.dockge.service` | Multi-agent management |
| `terminal.ts` | `TerminalService.java` | `com.louislam.dockge.service` | PTY terminal emulation (pty4j) |
| `password-hash.ts` | `PasswordHashingUtil.java` | `com.louislam.dockge.util` | Password hashing (Spring Security) |
| `rate-limiter.ts` | `RateLimitingFilter.java` | `com.louislam.dockge.filter` | Rate limiting for API |
| `util-server.ts` | `ServerUtil.java` | `com.louislam.dockge.util` | Server utilities |
| `utils/limit-queue.ts` | `LimitedQueue.java` | `com.louislam.dockge.util` | Queue with size limit |
| (new) | `UserService.java` | `com.louislam.dockge.service` | User management & authentication |

#### WebSocket & Real-time Communication (9 files)

| TypeScript File | Java Equivalent | Package | Purpose |
|---|---|---|---|
| `socket-handler.ts` | `BaseSocketHandler.java` | `com.louislam.dockge.socket` | Abstract base for WebSocket handlers |
| `main-socket-handler.ts` | `MainSocketHandler.java` | `com.louislam.dockge.socket.handler` | Main WebSocket events (login, settings) |
| `socket-handlers/agent-proxy-socket-handler.ts` | `AgentProxySocketHandler.java` | `com.louislam.dockge.socket.handler` | Agent command proxy via WebSocket |
| `socket-handlers/manage-agent-socket-handler.ts` | `AgentManagementSocketHandler.java` | `com.louislam.dockge.socket.handler` | Agent management operations |
| `agent-socket-handler.ts` | `AgentSocketDispatcher.java` | `com.louislam.dockge.socket` | Routes agent socket events |
| `agent-socket-handlers/docker-socket-handler.ts` | `DockerAgentHandler.java` | `com.louislam.dockge.socket.agent` | Docker operations on agent |
| `agent-socket-handlers/terminal-socket-handler.ts` | `TerminalAgentHandler.java` | `com.louislam.dockge.socket.agent` | Terminal operations on agent |
| (new) | `WebSocketConfig.java` | `com.louislam.dockge.config` | Spring WebSocket configuration |
| (new) | `WebSocketController.java` | `com.louislam.dockge.controller` | WebSocket message handlers |

#### Router & REST API (3 files)

| TypeScript File | Java Equivalent | Package | Purpose |
|---|---|---|---|
| `router.ts` | `RestControllerAdvice.java` | `com.louislam.dockge.config` | Global REST configuration |
| `routers/main-router.ts` | `MainController.java` | `com.louislam.dockge.controller` | REST endpoints (if any) |
| (new) | `GlobalExceptionHandler.java` | `com.louislam.dockge.exception` | Global exception handling |

#### Logging & Configuration (5 files)

| TypeScript File | Java Equivalent | Package | Purpose |
|---|---|---|---|
| `log.ts` | `LoggingConfig.java` | `com.louislam.dockge.config` | Logging configuration (SLF4J) |
| (new) | `SecurityConfig.java` | `com.louislam.dockge.config` | Spring Security configuration |
| (new) | `JwtConfig.java` | `com.louislam.dockge.config` | JWT token configuration |
| (new) | `YamlUtil.java` | `com.louislam.dockge.util` | YAML parsing (SnakeYAML) |
| (new) | `DockerUtil.java` | `com.louislam.dockge.util` | Docker CLI utilities |

#### DTOs (5 files)

| TypeScript File | Java Equivalent | Package | Purpose |
|---|---|---|---|
| (new) | `UserDTO.java` | `com.louislam.dockge.model.dto` | User data transfer object |
| (new) | `AgentDTO.java` | `com.louislam.dockge.model.dto` | Agent data transfer object |
| (new) | `StackDTO.java` | `com.louislam.dockge.model.dto` | Stack data transfer object |
| (new) | `SettingDTO.java` | `com.louislam.dockge.model.dto` | Setting data transfer object |
| (new) | `TerminalDTO.java` | `com.louislam.dockge.model.dto` | Terminal session DTO |

### 5.2 Summary Statistics

| Category | Count | Notes |
|---|---|---|
| TypeScript Files | 27 | Current backend |
| Entity Classes | 4 | User, Agent, Stack, Setting |
| Services | 9 | Business logic layer |
| WebSocket Handlers | 7 | Real-time communication |
| Repositories | 4 | Data access layer |
| Configuration Classes | 6 | Spring configuration |
| Utilities | 6 | Helper utilities |
| DTOs | 5 | Data transfer objects |
| Controllers | 2 | REST + WebSocket |
| Filters | 1 | Rate limiting |
| **Total Java Classes** | **~50** | Including tests |

---

## 8. Migration Steps

### Step 1: Create Spring Boot Project Structure
- Create base directories
- Add Spring Boot dependencies to pom.xml
- Configure application.yml

### Step 2: Implement Core Models
- User entity with JWT
- Agent entity
- Stack entity
- Database migrations

### Step 3: Create Repositories & Services
- UserRepository/Service
- AgentRepository/Service
- StackRepository/Service
- TerminalService
- SettingsService

### Step 4: Implement WebSocket Layer
- Spring WebSocket configuration
- Socket event handlers
- Message routing

### Step 5: Migrate Business Logic
- Stack operations
- Docker Compose management
- Terminal emulation
- Settings management

### Step 6: Testing & Validation
- Unit tests for services
- Integration tests (port from Node.js)
- End-to-end tests
- Performance testing

### Step 7: Deployment
- Docker image creation
- Configuration management
- Rollout strategy

---

## 9. Testing Strategy

### 9.1 Existing Tests

**Current**: 18 passing integration tests against Node.js backend

**Action**: 
- Keep tests passing during migration
- Gradually port to Spring Boot backend
- Maintain 100% pass rate

### 9.2 New Test Categories

**Unit Tests**:
- Service layer tests
- Utility tests
- Validation tests

**Integration Tests**:
- WebSocket communication
- Database operations
- Socket.IO events

**End-to-End Tests**:
- Full workflow testing
- Multi-agent scenarios
- Terminal operations

---

## 10. Build & Deployment

### 10.1 Maven Configuration

**Current Setup** (Ready to use):
```bash
mvn clean test              # Build + run integration tests
mvn clean test-compile      # Just compile
mvn clean verify            # Full validation
```

**Properties** (Configurable):
```
dockge.stacks.dir=./stacks
dockge.data.dir=./data
maven.compiler.release=21
```

### 10.2 Docker Deployment

```dockerfile
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/dockge-1.5.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

## 11. Risk Assessment

| Risk | Impact | Probability | Mitigation |
|------|--------|-------------|-----------|
| Breaking existing tests | High | Low | Keep Node.js running, test gradually |
| WebSocket compatibility | High | Medium | Comprehensive testing during Phase 3 |
| Terminal emulation issues | Medium | Medium | Use proven pty4j library |
| Performance degradation | Medium | Low | Load testing before deployment |
| Database migration issues | High | Low | Careful schema mapping and testing |

---

## 12. Timeline

| Phase | Duration | Status |
|-------|----------|--------|
| Phase 1: Project Setup | ✅ Complete | Done |
| Phase 2: Core Models | 1-2 weeks | Pending |
| Phase 3: WebSocket | 2-3 weeks | Pending |
| Phase 4: Business Logic | 2-3 weeks | Pending |
| Phase 5: Testing | 1-2 weeks | Pending |
| **Total** | **7-11 weeks** | Estimated |

---

## 13. Conclusion

The migration from Node.js to Spring Boot is well-planned with:
- ✅ Maven project infrastructure in place
- ✅ 18 passing integration tests as validation baseline
- ✅ Clear technology stack mapping
- ✅ Phased approach minimizing risk
- ✅ Parallel operation capability

**Next Steps**:
1. Finalize core model definitions
2. Set up database schema
3. Implement repository layer
4. Begin WebSocket implementation
5. Port existing tests

---

**Questions or Contributions?**  
See `CONTRIBUTING.md` for guidelines.

# Dockge Maven Project Setup

**Date**: December 29, 2025  
**Status**: ✅ Complete - Unified Maven Project  
**Version**: 1.5.0

---

## Overview

Dockge is now a complete Maven project with an integrated build pipeline that coordinates npm, TypeScript, Vue.js frontend, and Java integration tests.

---

## Project Structure

```
dockge/ (root - Maven Project)
├── pom.xml                          ← Maven configuration
├── package.json                     ← npm configuration
├── src/
│   └── test/
│       ├── java/
│       │   └── com/louislam/dockge/
│       │       ├── DockgeSocketIOTest.java      (18 tests)
│       │       └── IntegrationTestBase.java     (base class)
│       └── resources/
│           └── application-test.yml             (test config)
├── backend/                         ← Node.js TypeScript backend
├── frontend/                        ← Vue.js frontend
├── common/                          ← Shared code
└── ... (all other files unchanged)
```

---

## Maven Build Pipeline

### Complete Lifecycle

```
validate
  └─ npm install

process-resources
  └─ npm run check-ts

compile
  └─ Maven compiler (Java 21)

process-test-resources ⭐ BACKEND START
  ├─ npm run dev:backend (async)
  │  Environment Variables:
  │  ├─ DOCKGE_STACKS_DIR (configurable)
  │  └─ DOCKGE_DATA_DIR (configurable)
  └─ Health check (wait for port 5001)

test ⭐ TESTS RUN
  └─ Maven Surefire: 18 integration tests
     Result: 18/18 passing (100%)
```

---

## Properties Configuration

All versions and configuration are centralized in properties:

```xml
<properties>
    <!-- Compiler Configuration -->
    <maven.compiler.release>21</maven.compiler.release>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    
    <!-- Application Configuration -->
    <dockge.stacks.dir>./stacks</dockge.stacks.dir>
    <dockge.data.dir>./data</dockge.data.dir>
    
    <!-- Dependency Versions -->
    <junit.jupiter.version>5.10.1</junit.jupiter.version>
    <assertj.version>3.24.2</assertj.version>
    <socketio.client.version>2.1.0</socketio.client.version>
    <rest.assured.version>5.4.0</rest.assured.version>
    <jackson.version>2.16.0</jackson.version>
    <json.version>20231013</json.version>
    
    <!-- Plugin Versions -->
    <maven.compiler.plugin.version>3.11.0</maven.compiler.plugin.version>
    <maven.exec.plugin.version>3.1.0</maven.exec.plugin.version>
    <maven.surefire.plugin.version>3.2.3</maven.surefire.plugin.version>
</properties>
```

---

## Dependency Management

Uses `<dependencyManagement>` for centralized version control:

- Versions defined in properties
- Scopes defined in `<dependencies>` section
- Easy to update versions (change one property)
- Parent POM compatible
- Multi-module project ready

---

## Commands

### Quick Start

```bash
mvn clean test
```

Executes the complete pipeline:
- Installs npm dependencies
- Checks TypeScript
- Compiles Java tests
- Starts backend with environment variables
- Waits for backend readiness
- Runs 18 integration tests
- All tests pass (100%)

### Override Configuration

Run with custom directories:

```bash
mvn clean test -Ddockge.stacks.dir=/custom/stacks -Ddockge.data.dir=/custom/data
```

### Build Only

```bash
mvn clean test-compile
```

Compiles without running tests.

### Specific Test

```bash
mvn test -Dtest=DockgeSocketIOTest
```

---

## Backend Configuration

### Environment Variables

The following environment variables are passed to the backend (configurable via properties):

| Variable | Property | Default | Purpose |
|----------|----------|---------|---------|
| `DOCKGE_STACKS_DIR` | `dockge.stacks.dir` | `./stacks` | Stack definitions directory |
| `DOCKGE_DATA_DIR` | `dockge.data.dir` | `./data` | Data storage directory |

### Backend Features

- ✅ Starts in `process-test-resources` phase
- ✅ Runs asynchronously in background
- ✅ Health check waits for port 5001
- ✅ Accepts Socket.IO connections
- ✅ Initializes with proper directories
- ✅ Auto-cleanup on Maven completion

---

## Integration Tests

### Test Suite: DockgeSocketIOTest

**Location**: `src/test/java/com/louislam/dockge/DockgeSocketIOTest.java`

**Tests**: 18 comprehensive integration tests

**Pass Rate**: 100% (18/18)

### Test Coverage

- Connection & Socket.IO (1 test)
- Authentication (3 tests)
- JWT tokens (2 tests)
- Settings management (2 tests)
- Stack operations (4 tests)
- Utilities (1 test)
- Security (1 test)
- Error handling (1 test)

### All 18 Tests Passing

```
✅ shouldConnectToServer
✅ shouldHandleSetupEvent
✅ shouldLoginWithValidCredentials
✅ shouldRejectInvalidCredentials
✅ shouldLoginByToken
✅ shouldGetSettings
✅ shouldSetSettings
✅ shouldGetStackList
✅ shouldCreateStack
✅ shouldGetStack
✅ shouldDeleteStack
✅ shouldStartStack
✅ shouldStopStack
✅ shouldRestartStack
✅ shouldUpdateStack
✅ shouldComposerize
✅ shouldChangePassword
✅ shouldHandleConnectionTimeout
```

---

## Development Workflow

### Option 1: Automated (Recommended)

```bash
mvn clean test
```

Everything runs automatically:
- Backend starts
- Tests run
- Backend stops

### Option 2: Manual (For Development)

**Terminal 1 - Start Backend**:
```bash
DOCKGE_STACKS_DIR=./stacks DOCKGE_DATA_DIR=./data npm run dev:backend
```

**Terminal 2 - Start Frontend Dev Server**:
```bash
npm run dev:frontend
```

**Terminal 3 - Run Tests** (backend must be running):
```bash
mvn test
```

---

## Troubleshooting

### Tests Fail - Backend Not Responding

**Solution**: Ensure backend has time to start. The health check retries for up to 30 seconds.

### Permission Denied on Stacks Directory

**Solution**: Use properties to specify writable directories:
```bash
mvn clean test -Ddockge.stacks.dir=/writable/path/stacks
```

### Port 5001 Already in Use

**Solution**: Kill the existing process:
```bash
lsof -i :5001 | grep -v COMMAND | awk '{print $2}' | xargs kill -9
```

### TypeScript Errors

**Solution**: Check TypeScript compilation:
```bash
npm run check-ts
```

### Maven Build Hangs

**Solution**: Add timeout to prevent indefinite waiting:
```bash
timeout 300 mvn clean test
```

---

## Performance

| Phase | Time |
|-------|------|
| validate (npm install) | ~3 seconds |
| process-resources (check-ts) | ~2 seconds |
| compile (Java) | ~1 second |
| process-test-resources (backend start) | ~4 seconds |
| test (18 tests) | ~25 seconds |
| **Total** | **~35 seconds** |

---

## Best Practices Implemented

✅ **dependencyManagement** - Centralized version control  
✅ **pluginManagement** - Plugin version management  
✅ **Properties** - All versions as properties  
✅ **Java 21** - Proper `--release` configuration  
✅ **Configurable** - Override via command line  
✅ **Environment Variables** - As properties for flexibility  
✅ **Async Backend** - Non-blocking execution  
✅ **Health Check** - Intelligent backend verification  

---

## CI/CD Integration

### GitHub Actions Example

```yaml
name: Build and Test

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          java-version: '21'
          distribution: 'temurin'
      
      - name: Set up Node.js
        uses: actions/setup-node@v3
        with:
          node-version: '18'
      
      - name: Build and Test
        run: mvn clean test
```

---

## Version Management

To update dependency or plugin versions, simply change the property:

```xml
<junit.jupiter.version>5.10.1</junit.jupiter.version>
```

All usages automatically update across the project.

---

## Summary

Dockge is now a professional Maven project with:
- ✅ Automated backend lifecycle management
- ✅ 18 integration tests (100% passing)
- ✅ Configurable properties
- ✅ Centralized dependency management
- ✅ Professional plugin management
- ✅ Production-ready configuration

**Quick Start**: `mvn clean test`

