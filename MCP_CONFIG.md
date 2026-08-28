# GitHub MCP Server Configuration for rovo-dev

## Step 1: Get GitHub Token
1. Go to: https://github.com/settings/tokens/new
2. Name: "dockge-java-mcp"
3. Scopes: `repo`, `workflow`, `read:user`, `read:org`
4. Generate and copy the token

## Step 2: Configure rovo-dev MCP

Find your rovo-dev mcp configuration file:
- **macOS**: `~/Library/Application Support/rovo-dev/mcp.json`
- **Linux**: `~/.config/rovo-dev/mcp.json`
- **Windows**: `%APPDATA%/rovo-dev/mcp.json`

Add this to the `mcpServers` section:

```json
{
  "mcpServers": {
    "github": {
      "command": "docker",
      "args": [
        "run",
        "-i",
        "--rm",
        "-e",
        "GITHUB_PERSONAL_ACCESS_TOKEN",
        "ghcr.io/github/github-mcp-server"
      ],
      "env": {
        "GITHUB_PERSONAL_ACCESS_TOKEN": "ghp_your_token_here"
      }
    }
  }
}
```

Replace `ghp_your_token_here` with your actual GitHub token from Step 1.

## Step 3: Verify Docker is Running
```bash
docker --version
```

Make sure Docker is installed and running.

## Step 4: Restart rovo-dev
Restart your rovo-dev CLI to load the new configuration.

## Step 5: Use GitHub Features
```bash
rovo "Create a GitHub issue in minhtran83/dockge-java with title 'Phase 2: Implement User Entity with JWT Authentication'"
rovo "List all my repositories"
rovo "Create a pull request..."
```

Done! GitHub MCP is now configured in rovo-dev using Docker.
