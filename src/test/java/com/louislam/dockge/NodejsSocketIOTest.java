package com.louislam.dockge;

import org.junit.jupiter.api.Tag;

/**
 * Concrete implementation of DockgeSocketIOTest for testing the original Node.js backend.
 * 
 * Usage: 
 * 1. Start the Node.js backend (npm run dev:backend) on port 5001.
 * 2. Run this test class.
 */
@Tag("nodejs")
public class NodejsSocketIOTest extends DockgeSocketIOTest {
    // Inherits all 18 tests from DockgeSocketIOTest
    // Defaults to port 5001 via IntegrationTestBase
}
