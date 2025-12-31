# GitHub Issues Template for dockge-java Migration

Copy and paste these issues into your GitHub repository: https://github.com/minhtran83/dockge-java

---

## Phase 2: Core Models (1-2 weeks)

### Issue 1: Implement User Entity with JWT Authentication
```
Title: [Phase 2] Implement User Entity with JWT Authentication

Description:
Create the User entity and JWT authentication infrastructure for Spring Boot backend.

## Tasks:
- [ ] Create User.java entity class with JPA annotations
- [ ] Implement UserRepository interface (Spring Data JPA)
- [ ] Create UserService for user management
- [ ] Implement JWT utility class (JwtUtil.java)
- [ ] Configure Spring Security for JWT validation
- [ ] Add password hashing using Spring Security BCrypt
- [ ] Create unit tests for User entity and JWT utilities

## Related Files:
- Node.js Reference: `backend/models/user.ts`, `backend/socket-handlers/main-socket-handler.ts`
- Target Package: `com.louislam.dockge.model.entity`, `com.louislam.dockge.service`, `com.louislam.dockge.util`

## Acceptance Criteria:
- User can be created and persisted to database
- JWT tokens can be generated and validated
- Password hashing works correctly with Spring Security
- All unit tests pass
```

### Issue 2: Implement Agent Entity
```
Title: [Phase 2] Implement Agent Entity for Multi-Host Support

Description:
Create the Agent entity to support multi-host Docker management.

## Tasks:
- [ ] Create Agent.java entity class with JPA annotations
- [ ] Implement AgentRepository interface
- [ ] Create AgentService for agent management
- [ ] Add relationship mapping between Agent and User
- [ ] Create unit tests for Agent entity

## Related Files:
- Node.js Reference: `backend/models/agent.ts`, `backend/agent-manager.ts`
- Target Package: `com.louislam.dockge.model.entity`, `com.louislam.dockge.service`

## Acceptance Criteria:
- Agent entity persists to database correctly
- Can retrieve agents by ID and user
- Relationships with User entity work properly
- All unit tests pass
```

### Issue 3: Implement Stack Entity
```
Title: [Phase 2] Implement Stack Entity for Docker Compose Management

Description:
Create the Stack entity to represent Docker Compose stacks.

## Tasks:
- [ ] Create Stack.java entity class with JPA annotations
- [ ] Implement StackRepository interface
- [ ] Create StackService for stack CRUD operations
- [ ] Add relationship mapping between Stack and Agent
- [ ] Create unit tests for Stack entity

## Related Files:
- Node.js Reference: `backend/stack.ts`
- Target Package: `com.louislam.dockge.model.entity`, `com.louislam.dockge.service`

## Acceptance Criteria:
- Stack entity persists to database correctly
- CRUD operations work as expected
- Relationships with Agent entity work properly
- All unit tests pass
```

### Issue 4: Implement Setting Entity
```
Title: [Phase 2] Implement Setting Entity for Application Configuration

Description:
Create the Setting entity to manage application-wide configuration.

## Tasks:
- [ ] Create Setting.java entity class with JPA annotations
- [ ] Implement SettingRepository interface
- [ ] Create SettingService with caching support
- [ ] Add key-value storage for settings
- [ ] Create unit tests for Setting entity and service

## Related Files:
- Node.js Reference: `backend/settings.ts`
- Target Package: `com.louislam.dockge.model.entity`, `com.louislam.dockge.service`

## Acceptance Criteria:
- Settings can be persisted and retrieved
- Settings are properly cached
- Updates propagate correctly
- All unit tests pass
```

### Issue 5: Set Up Database Schema and Migrations
```
Title: [Phase 2] Set Up Database Schema and Migrations

Description:
Configure database schema and migration strategy for Spring Boot application.

## Tasks:
- [ ] Configure Hibernate/JPA mapping properties
- [ ] Create Flyway migration framework setup
- [ ] Create initial database schema migrations
- [ ] Add database migrations for User table
- [ ] Add database migrations for Agent table
- [ ] Add database migrations for Stack table
- [ ] Add database migrations for Setting table
- [ ] Test migrations with both SQLite and PostgreSQL

## Related Files:
- Node.js Reference: `backend/database.ts`, `backend/migrations/*`
- Target Package: `db/migration/`

## Acceptance Criteria:
- Database migrations run successfully
- All tables are created with correct schema
- Migrations are reversible
- Works with both SQLite and PostgreSQL
```

---

## Phase 3: WebSocket Layer (2-3 weeks)

### Issue 6: Configure Spring WebSocket and SockJS
```
Title: [Phase 3] Configure Spring WebSocket and SockJS

Description:
Set up Spring WebSocket configuration with SockJS fallback for real-time communication.

## Tasks:
- [ ] Create WebSocketConfig.java configuration class
- [ ] Configure STOMP message broker
- [ ] Enable SockJS fallback
- [ ] Configure WebSocket endpoint
- [ ] Set up message routing
- [ ] Add CORS configuration for WebSocket
- [ ] Create integration tests for WebSocket connectivity

## Related Files:
- Node.js Reference: `backend/socket-handler.ts`, `backend/dockge-server.ts`
- Target Package: `com.louislam.dockge.config`, `com.louislam.dockge.controller`

## Acceptance Criteria:
- WebSocket connections establish successfully
- SockJS fallback works
- STOMP messaging is functional
- CORS allows proper cross-origin connections
```

### Issue 7: Implement Main Socket Event Handlers
```
Title: [Phase 3] Implement Main Socket Event Handlers

Description:
Migrate main socket event handlers from Node.js to Spring Boot.

## Tasks:
- [ ] Create MainSocketHandler.java for main socket events
- [ ] Implement login event handler
- [ ] Implement logout event handler
- [ ] Implement settings management events
- [ ] Implement error handling for socket events
- [ ] Add comprehensive integration tests

## Related Files:
- Node.js Reference: `backend/main-socket-handler.ts`
- Target Package: `com.louislam.dockge.socket.handler`, `com.louislam.dockge.controller`

## Acceptance Criteria:
- Socket events are properly routed
- Login/logout work correctly
- Settings can be retrieved and updated via WebSocket
- Error handling is robust
```

### Issue 8: Implement Agent Socket Proxy Handler
```
Title: [Phase 3] Implement Agent Socket Proxy Handler

Description:
Implement socket event proxy for communicating with remote agents.

## Tasks:
- [ ] Create AgentProxySocketHandler.java
- [ ] Implement command proxying to remote agents
- [ ] Add message routing logic
- [ ] Implement error handling for agent communication
- [ ] Add integration tests

## Related Files:
- Node.js Reference: `backend/socket-handlers/agent-proxy-socket-handler.ts`
- Target Package: `com.louislam.dockge.socket.handler`

## Acceptance Criteria:
- Commands can be proxied to agents
- Responses are properly routed back to clients
- Error handling works for agent disconnections
```

### Issue 9: Implement Agent Management Socket Handler
```
Title: [Phase 3] Implement Agent Management Socket Handler

Description:
Implement socket events for managing agents (add, remove, update).

## Tasks:
- [ ] Create AgentManagementSocketHandler.java
- [ ] Implement add agent event
- [ ] Implement remove agent event
- [ ] Implement update agent event
- [ ] Implement list agents event
- [ ] Add validation and error handling
- [ ] Add integration tests

## Related Files:
- Node.js Reference: `backend/socket-handlers/manage-agent-socket-handler.ts`
- Target Package: `com.louislam.dockge.socket.handler`

## Acceptance Criteria:
- Agents can be added, removed, and updated via WebSocket
- Agent list is properly persisted
- All operations are validated
```

---

## Phase 4: Business Logic (2-3 weeks)

### Issue 10: Implement Stack Service with Docker Compose Operations
```
Title: [Phase 4] Implement Stack Service with Docker Compose Operations

Description:
Implement stack operations (CRUD, start, stop, restart, update).

## Tasks:
- [ ] Create comprehensive StackService with all operations
- [ ] Implement createStack() method
- [ ] Implement getStack() method
- [ ] Implement updateStack() method
- [ ] Implement deleteStack() method
- [ ] Implement startStack() method
- [ ] Implement stopStack() method
- [ ] Implement restartStack() method
- [ ] Add Docker CLI integration
- [ ] Add comprehensive unit tests

## Related Files:
- Node.js Reference: `backend/stack.ts`
- Target Package: `com.louislam.dockge.service`, `com.louislam.dockge.util`

## Acceptance Criteria:
- All stack operations work correctly
- Docker Compose files are properly managed
- State transitions are handled correctly
- Error handling is robust
```

### Issue 11: Implement Terminal Service with PTY Support
```
Title: [Phase 4] Implement Terminal Service with PTY Support

Description:
Implement terminal emulation using pty4j library.

## Tasks:
- [ ] Add pty4j dependency to pom.xml
- [ ] Create TerminalService.java
- [ ] Implement terminal session management
- [ ] Implement PTY allocation
- [ ] Implement command execution
- [ ] Implement output streaming
- [ ] Add proper resource cleanup
- [ ] Add error handling for terminal operations
- [ ] Create unit tests

## Related Files:
- Node.js Reference: `backend/terminal.ts`
- Target Package: `com.louislam.dockge.service`

## Acceptance Criteria:
- Terminal sessions can be created and destroyed
- Commands execute properly in terminal
- Output is properly streamed
- Resources are cleaned up on session end
```

### Issue 12: Implement Terminal Socket Agent Handler
```
Title: [Phase 4] Implement Terminal Socket Agent Handler

Description:
Implement WebSocket events for terminal operations on agents.

## Tasks:
- [ ] Create TerminalAgentHandler.java
- [ ] Implement terminal session creation event
- [ ] Implement terminal input event handler
- [ ] Implement terminal output streaming
- [ ] Implement terminal session termination
- [ ] Add error handling and cleanup
- [ ] Create integration tests

## Related Files:
- Node.js Reference: `backend/agent-socket-handlers/terminal-socket-handler.ts`
- Target Package: `com.louislam.dockge.socket.agent`

## Acceptance Criteria:
- Terminal sessions can be created via WebSocket
- Commands can be sent and output received
- Sessions properly terminate
```

### Issue 13: Implement Docker Agent Handler
```
Title: [Phase 4] Implement Docker Agent Handler

Description:
Implement WebSocket events for Docker operations on agents.

## Tasks:
- [ ] Create DockerAgentHandler.java
- [ ] Implement Docker command execution
- [ ] Implement Docker status queries
- [ ] Implement container management events
- [ ] Add error handling
- [ ] Create integration tests

## Related Files:
- Node.js Reference: `backend/agent-socket-handlers/docker-socket-handler.ts`
- Target Package: `com.louislam.dockge.socket.agent`

## Acceptance Criteria:
- Docker commands can be executed on agents
- Container information is properly retrieved
- Status updates are streamed via WebSocket
```

### Issue 14: Implement Utility Classes and Helpers
```
Title: [Phase 4] Implement Utility Classes and Helpers

Description:
Create utility classes for common operations.

## Tasks:
- [ ] Create DockerUtil.java for Docker CLI operations
- [ ] Create YamlUtil.java for YAML parsing (SnakeYAML)
- [ ] Create ServerUtil.java for server utilities
- [ ] Create LimitedQueue.java for bounded queue operations
- [ ] Create VersionChecker.java for version validation
- [ ] Add unit tests for all utilities

## Related Files:
- Node.js Reference: `backend/util-server.ts`, `backend/utils/limit-queue.ts`, etc.
- Target Package: `com.louislam.dockge.util`

## Acceptance Criteria:
- All utilities work correctly
- YAML parsing works for compose files
- Docker operations are properly abstracted
- Queue operations handle limits correctly
```

---

## Phase 5: Testing & Validation (1-2 weeks)

### Issue 15: Port Integration Tests from Node.js to Spring Boot
```
Title: [Phase 5] Port Integration Tests from Node.js to Spring Boot

Description:
Migrate 18 existing integration tests from Node.js backend to Spring Boot.

## Tasks:
- [ ] Create WebSocket connection tests
- [ ] Create authentication tests (login, JWT, logout)
- [ ] Create settings management tests
- [ ] Create stack CRUD operation tests
- [ ] Create stack lifecycle tests (start, stop, restart)
- [ ] Create utility function tests
- [ ] Create security/error handling tests
- [ ] Ensure 100% pass rate

## Related Files:
- Node.js Reference: `src/test/java/com/louislam/dockge/DockgeSocketIOTest.java`
- Target Package: `src/test/java/com/louislam/dockge/integration`

## Acceptance Criteria:
- All 18 tests pass
- 100% pass rate achieved
- Tests are comprehensive and maintainable
- Coverage includes all critical paths
```

### Issue 16: Create Unit Tests for Service Layer
```
Title: [Phase 5] Create Unit Tests for Service Layer

Description:
Create comprehensive unit tests for all service classes.

## Tasks:
- [ ] Create UserServiceTest
- [ ] Create AgentServiceTest
- [ ] Create StackServiceTest
- [ ] Create SettingServiceTest
- [ ] Create TerminalServiceTest
- [ ] Add mock dependencies using Mockito
- [ ] Aim for 80%+ code coverage

## Related Files:
- Target Package: `src/test/java/com/louislam/dockge/unit`

## Acceptance Criteria:
- All service tests pass
- Code coverage is 80%+
- Mocking is properly implemented
- Edge cases are covered
```

### Issue 17: Performance and Load Testing
```
Title: [Phase 5] Performance and Load Testing

Description:
Conduct performance benchmarking and load testing.

## Tasks:
- [ ] Set up JMH benchmarks
- [ ] Create load tests for WebSocket connections
- [ ] Create load tests for stack operations
- [ ] Benchmark against Node.js baseline
- [ ] Document performance results
- [ ] Identify and address bottlenecks

## Acceptance Criteria:
- Performance meets or exceeds Node.js baseline
- System can handle expected load
- No memory leaks detected
- Results documented
```

### Issue 18: End-to-End Testing
```
Title: [Phase 5] End-to-End Testing

Description:
Create end-to-end tests covering complete user workflows.

## Tasks:
- [ ] Create multi-agent scenario tests
- [ ] Create terminal operation tests
- [ ] Create full stack lifecycle tests
- [ ] Create concurrent operation tests
- [ ] Document test scenarios

## Acceptance Criteria:
- All critical workflows tested
- Multi-agent scenarios work correctly
- Concurrent operations are handled properly
- Edge cases are identified and handled
```

---

## Phase 6: Build & Configuration

### Issue 19: Configure Maven Build and Dependencies
```
Title: [Phase 6] Configure Maven Build and Dependencies

Description:
Complete Maven configuration with all Spring Boot dependencies.

## Tasks:
- [ ] Add all Spring Boot starters to pom.xml
- [ ] Configure dependency versions and BOMs
- [ ] Set up Maven plugins for testing and packaging
- [ ] Configure build profiles for different environments
- [ ] Set up Maven enforcer for version consistency

## Related Files:
- Target: `pom.xml`

## Acceptance Criteria:
- Project builds successfully
- All dependencies are properly managed
- Build is reproducible
```

### Issue 20: Create Docker Configuration
```
Title: [Phase 6] Create Docker Configuration

Description:
Create Docker setup for Spring Boot application deployment.

## Tasks:
- [ ] Create Dockerfile for Spring Boot JAR
- [ ] Create docker-compose.yml for local development
- [ ] Add health check configuration
- [ ] Document Docker deployment process

## Related Files:
- Target: `docker/` directory

## Acceptance Criteria:
- Application runs in Docker
- Health checks work correctly
- Database persistence configured
- Volumes properly mounted
```

### Issue 21: Migration Documentation
```
Title: [Phase 6] Migration Documentation

Description:
Complete documentation of the migration process and architecture.

## Tasks:
- [ ] Document Spring Boot architecture
- [ ] Create deployment guide
- [ ] Document configuration options
- [ ] Create troubleshooting guide
- [ ] Document migration timeline and decisions

## Acceptance Criteria:
- Documentation is comprehensive
- Setup instructions are clear
- Future developers can understand the architecture
```

---

## Labels to Use

When creating issues in GitHub, add these labels:
- `phase2`, `phase3`, `phase4`, `phase5`, `phase6` (based on phase)
- `migration` (all migration-related issues)
- `backend` (backend work)
- `websocket` (WebSocket-related)
- `database` (database-related)
- `testing` (testing-related)
- `documentation` (docs)
- `bug` (if fixing existing issues)
- `enhancement` (if adding new features)

---

## Milestones

Suggested GitHub Milestones:
- Phase 2: Core Models
- Phase 3: WebSocket Layer
- Phase 4: Business Logic
- Phase 5: Testing & Validation
- Phase 6: Build & Configuration

