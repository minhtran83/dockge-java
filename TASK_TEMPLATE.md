# Task Execution Template

Balanced template for driving any GitHub issue.

**⚠️ IMPORTANT:**
- Do NOT commit this template file to git
- Do NOT commit task work files to git (store in `tasks/` folder - ignored by .gitignore)
- Example task file: `tasks/16-implement-user-entity.md`
- EXCEPTION: If task is to write documentation (e.g., README, guides), commit the documentation to git
- Commit actual deliverables: code, documentation, configuration files
- Do NOT commit: task planning/tracking files

---

## 1. Issue Overview

**Issue Number**: #[NUMBER]  
**Title**: [TITLE]  
**Phase**: [PHASE]  
**Status**: [TO_DO | IN_PROGRESS | DONE]

**Git Branch Name**: `[NUMBER]-[SHORT-DESCRIPTION]`  
*Example: `16-implement-user-entity-with-jwt`*  
*Convention: <ISSUE_NUMBER>-<SHORT_DESCRIPTION> (spaces replaced with hyphens)*

---

## 2. Understanding

**What Needs to Be Done**:  
[Clear description of the task]

**Why It Matters**:  
[Why this task is important]

**Success Criteria**:
- [ ] Criterion 1
- [ ] Criterion 2
- [ ] Criterion 3

---

## 3. Planning

**Key Components**:
- Component 1: [Description]
- Component 2: [Description]

**Files to Create/Modify**:
- `src/main/java/.../File1.java`
- `src/test/java/.../File1Test.java`
- `src/main/resources/config.yml`

**Dependencies**:
- Dependency 1: [Description]
- Dependency 2: [Description]

**References**:
- Reference 1: [Link/File]
- Reference 2: [Link/File]

---

## 4. Implementation Steps

1. [First step with details]
2. [Second step with details]
3. [Third step with details]
4. [Final step with verification]

---

## 5. Testing

**Unit Tests**:
- [ ] Test 1: [What to test and expected result]
- [ ] Test 2: [What to test and expected result]

**Integration Tests**:
- [ ] Test 1: [What to test and expected result]

**Edge Cases**:
- [ ] Edge case 1: [How to handle]
- [ ] Edge case 2: [How to handle]

---

## 6. Progress Tracking

**Implementation Checklist**:
- [ ] Implementation started
- [ ] Core functionality complete
- [ ] Unit tests written
- [ ] Tests passing
- [ ] Documentation added
- [ ] Code review ready

**Blockers/Issues**:
[Document any blockers encountered]

---

## 7. Git Workflow

**Before Starting New Task**:
```bash
# Switch to master branch
git checkout master

# Pull latest changes from remote
git pull origin master

# This ensures you're working with the latest code
```

**Create Feature Branch**:
```bash
git checkout -b [BRANCH_NAME]
# Format: <ISSUE_NUMBER>-<SHORT_DESCRIPTION>
# Example: git checkout -b 16-implement-user-entity-with-jwt
```

**Add and Commit Each File Individually**:
```bash
# Add FIRST file
git add file1.java
git commit -m "#[ISSUE_NUMBER] Brief description of first change"

# Add SECOND file  
git add file2.java
git commit -m "#[ISSUE_NUMBER] Brief description of second change"

# Add THIRD file
git add file3.java
git commit -m "#[ISSUE_NUMBER] Brief description of third change"
```

**Commit Message Format**:
```
#[ISSUE_NUMBER] Brief description of what changed

- What was changed
```

**Example Workflow** (each file = separate commit):
```bash
git add User.java
git commit -m "#16 Add User entity with JPA annotations"

git add UserRepository.java
git commit -m "#16 Add UserRepository interface"

git add UserService.java
git commit -m "#16 Add UserService for user management"

git add JwtUtil.java
git commit -m "#16 Add JwtUtil for token operations"
```

**Push to Remote**:
```bash
git push -u origin [BRANCH_NAME]
# Output will show: "Create a pull request for '[BRANCH_NAME]' by visiting: https://..."
```

**Update GitHub Issue Before PR**:
1. Go to GitHub Issue #[ISSUE_NUMBER]
2. Update issue description with all tasks checked off: `- [x] Task name`
3. Add note at bottom: "Branch: `[BRANCH_NAME]` - Ready for PR"
4. Verify all acceptance criteria are met

**Create Pull Request**:
```bash
# Using GitHub CLI (recommended)
gh pr create --base master --head [BRANCH_NAME] --title "#[ISSUE_NUMBER] Brief description" --body "Description and related issue"

# Or manually via GitHub UI
# 1. Go to the pull request link shown after git push
# 2. Fill in title: #[ISSUE_NUMBER] Brief description
# 3. Add description with: Closes #[ISSUE_NUMBER]
# 4. Review files and create PR
```

**After PR Creation**:
1. PR URL will be provided (e.g., https://github.com/owner/repo/pull/XX)
2. **Update GitHub Issue #[ISSUE_NUMBER]**: Change status to "In Progress"
3. Link PR to issue
4. Once PR is reviewed and merged, close the issue with "Completed"
5. **Add Comprehensive PR Comment** (see section below)

**Key Rules**:
- One logical change per commit
- Add only ONE file per commit (for focused tracking)
- Commit after each meaningful file, not at the end
- Update issue status when creating PR
- Close issue when PR is merged

---

## 9. PR Comment Template: Comprehensive Explanation

**When to Add**: Right after creating the PR, add a detailed comment explaining all changes for code reviewers unfamiliar with the codebase.

**Purpose**: Help reviewers quickly understand:
- Project structure and file organization
- Component architecture and responsibilities
- Data flow and state management
- Design system and styling approach
- What's implemented vs. what's pending

**Template**:

```markdown
# 📝 Comprehensive PR Review & Explanation

Great work! Here's a detailed breakdown of all changes in this PR for someone new to the codebase:

---

## 📁 Project Structure Overview

This PR [DESCRIBE WHAT THIS PR DOES] with the following structure:

\`\`\`
project/
├── src/                          # [DESCRIPTION]
│   ├── [KEY_FILE].tsx
│   ├── components/
│   │   ├── [COMPONENT].tsx
│   │   └── ...
│   └── ...
├── [BACKEND_DIR]/
│   └── ...
└── [CONFIG_FILES]
\`\`\`

---

## 🔑 Key Components Explained

### 1. **[MAJOR_COMPONENT_1]**

#### `[FILE_NAME]` - [PURPOSE]

**Features**:
- Feature 1: Description
- Feature 2: Description

**Key Code**:
\`\`\`[LANGUAGE]
[RELEVANT_CODE_SNIPPET]
\`\`\`

**Why [TECHNOLOGY]?**
- Reason 1
- Reason 2

### 2. **[MAJOR_COMPONENT_2]**

[Similar structure as above]

---

## 🎨 Design System

### Color Palette
- **[COLOR_NAME]**: `[VALUE]` ([HEX/RGB])
- ...

### Typography
- Default font: [FONT]
- Sizes: [LIST]
- Weights: [LIST]

### Spacing System
- [DESCRIBE_SPACING]

---

## 🔄 Data Flow

\`\`\`
User Action
    ↓
Component Event Handler
    ↓
State Update
    ↓
Components Re-render
    ↓
UI Updates
\`\`\`

**Example: [SPECIFIC_FEATURE]**
1. User does X
2. Component calls Y
3. State updates Z
4. Result: A, B, C

---

## ✅ What's Working

✓ Feature 1
✓ Feature 2
✓ Feature 3

---

## ⚠️ What's Not Implemented Yet

- Feature A (tracked in Issue #XX)
- Feature B (tracked in Issue #YY)
- Feature C (future enhancement)

---

## 📚 Configuration Files Summary

| File | Purpose |
|------|---------|
| `[CONFIG_1]` | [Purpose] |
| `[CONFIG_2]` | [Purpose] |
| `[CONFIG_3]` | [Purpose] |

---

## 🚀 Next Steps

1. **Issue #[NUMBER]**: [Next task description]
2. **Issue #[NUMBER]**: [Next task description]
3. **Future**: [Long-term work]

This PR is **[COMPLETION_PERCENTAGE]% feature-complete** for the MVP!
\`\`\`

---

## Example: Desktop UI PR Comment

For reference, see the comprehensive comment added to [PR #43](https://github.com/minhtran83/localdns/pull/43) which includes:
- Full project structure with React + Tauri architecture
- Detailed component explanations (Sidebar, HostsTable, StatsBar)
- Zustand state management flow
- Design system with color palette
- Data flow diagrams
- What's implemented vs. pending work

---

### Best Practices for PR Comments

1. **Add Early**: Post comment right after PR creation, not after review starts
2. **Be Comprehensive**: Assume reviewer doesn't know the codebase
3. **Use Visuals**: Include ASCII diagrams and code snippets
4. **Highlight Decisions**: Explain WHY certain technologies/patterns were chosen
5. **Link Issues**: Reference related GitHub issues
6. **Update on Changes**: If PR gets updated, refresh the comment
7. **Keep Organized**: Use clear sections with headers
8. **Include Examples**: Show data flow and real-world usage
9. **Document Limitations**: Be transparent about what's not done yet
10. **Provide Context**: Explain how this PR relates to the larger project vision

---

## 10. Adding Inline Diff Comments to PR

**Purpose**: Add context-specific comments directly on code lines in the PR diff for precise feedback.

**When to Use**:
- Explaining specific implementation decisions
- Highlighting important code patterns
- Clarifying why certain technologies/approaches were chosen
- Noting performance considerations or trade-offs

### Implementation via GitHub API

**Tools Used**:
1. `pull_request_review_write` - Create and submit reviews
2. `add_comment_to_pending_review` - Add inline comments to specific lines

**Method**: Create a pending review first, then add multiple inline comments

**Step-by-Step Process**:

1. **Create pending review** (without submitting)
   ```bash
   pull_request_review_write(
     method: "create"
     owner: "minhtran83"
     repo: "localdns"
     pullNumber: 43
     commitID: "[commit_sha]"
     # No event parameter = pending review (not submitted yet)
   )
   ```

2. **Add inline comments** to specific lines
   ```bash
   add_comment_to_pending_review(
     owner: "minhtran83"
     repo: "localdns"
     pullNumber: 43
     path: "src/types/index.ts"
     line: 6
     body: "✅ Host interface definition. The `disabled: boolean` flag allows toggling hosts on/off without deletion."
     subjectType: "LINE"
   )
   ```

3. **Submit pending review**
   ```bash
   pull_request_review_write(
     method: "submit_pending"
     owner: "minhtran83"
     repo: "localdns"
     pullNumber: 43
     event: "COMMENT"
     body: "✅ Inline review complete - 40+ detailed comments added to key files"
   )
   ```

### Tool: `add_comment_to_pending_review` - Detailed Reference

**Purpose**: Add inline comments to specific lines in a pending pull request review.

**Required Parameters**:

| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `owner` | string | ✅ Yes | Repository owner (e.g., "minhtran83") |
| `repo` | string | ✅ Yes | Repository name (e.g., "localdns") |
| `pullNumber` | number | ✅ Yes | Pull request number (e.g., 43) |
| `path` | string | ✅ Yes | File path in repo (e.g., "src/types/index.ts") |
| `body` | string | ✅ Yes | Comment text - explain what/why (e.g., "✅ Host interface...") |
| `subjectType` | string | ✅ Yes | Comment scope: `"LINE"` or `"FILE"` |

**Optional Parameters** (for multi-line comments):

| Parameter | Type | Optional | Description |
|-----------|------|----------|-------------|
| `line` | number | For single-line | Line number where comment ends |
| `startLine` | number | For multi-line | First line of comment range |
| `side` | string | For side selection | `"LEFT"` (old code) or `"RIGHT"` (new code) |
| `startSide` | string | For multi-line | Starting side of range |

**Important Notes**:

1. **Must have pending review first**:
    - Create pending review with `pull_request_review_write(method: "create")`
    - Then add comments
    - Finally submit with `pull_request_review_write(method: "submit_pending")`

2. **Line numbers must exist in diff**:
    - Invalid line numbers cause error
    - Check PR diff to find correct line numbers
    - Line numbers are relative to the changed file

3. **Comment text best practices**:
    - Start with emoji (✅, ⚠️, 📝, 💡) for visual scanning
    - Keep under 500 characters ideally
    - Explain "why" not just "what"
    - Link to related code patterns

### Example Usage: `add_comment_to_pending_review`

```python
# Add comment to line 14 of src/store/useEnvironmentStore.ts
add_comment_to_pending_review(
    owner="minhtran83",
    repo="localdns",
    pullNumber=43,
    path="src/store/useEnvironmentStore.ts",
    line=14,
    body="✅ Zustand store creation. Lightweight alternative to Redux - minimal boilerplate.",
    subjectType="LINE"
)

# Add comment to lines 25-30 of src/App.tsx (multi-line)
add_comment_to_pending_review(
    owner="minhtran83",
    repo="localdns",
    pullNumber=43,
    path="src/App.tsx",
    startLine=25,
    line=30,
    body="⚠️ Consider extracting this layout into a separate component for reusability.",
    subjectType="FILE",
    side="RIGHT"
)
```

### Workflow: Complete Inline Review Process

```python
# Step 1: Create pending review
pull_request_review_write(
    method="create",
    owner="minhtran83",
    repo="localdns",
    pullNumber=43,
    commitID="d0b48636357150afeb091528193ca3139a4391dd"
    # No 'event' parameter = creates PENDING review
)

# Step 2: Add multiple inline comments
for file_comment in file_comments_list:
    add_comment_to_pending_review(
        owner="minhtran83",
        repo="localdns",
        pullNumber=43,
        path=file_comment["path"],
        line=file_comment["line"],
        body=file_comment["comment"],
        subjectType="LINE"
    )

# Step 3: Submit the review
pull_request_review_write(
    method="submit_pending",
    owner="minhtran83",
    repo="localdns",
    pullNumber=43,
    event="COMMENT",
    body="✅ Inline review complete - X comments added"
)
```

### Key Parameters

| Parameter | Purpose | Example |
|-----------|---------|---------|
| `path` | File path in repo | `src/types/index.ts` |
| `line` | Line number in diff | `6` |
| `body` | Comment text | `"Explains what this line does"` |
| `subjectType` | Comment scope | `"LINE"` (specific line only) |

### Comment Best Practices

**DO**:
- ✅ Keep comments focused (one idea per comment)
- ✅ Explain "why" not just "what"
- ✅ Link to related patterns in codebase
- ✅ Note trade-offs and design decisions
- ✅ Use emojis (✅, ⚠️, 📝) for visual scanning

**DON'T**:
- ❌ Add comments to every single line
- ❌ Repeat what the code obviously does
- ❌ Make comments longer than the code itself
- ❌ Comment on non-critical lines

### Example: PR #43 Implementation

**File**: PR #43 - Desktop UI for Environment Management

**Comments Added**: 40+ inline comments across 20+ files

**Coverage**:
- Core files (types, store, App, main)
- Components (Sidebar, HostsTable, StatsBar)
- Configuration files (Vite, TypeScript, Tailwind)
- Backend files (Tauri, Rust, Cargo)
- Root files (.gitignore, README, package.json)

**Sample Comments**:

```markdown
Path: src/types/index.ts, Line: 6
✅ Host interface definition. The `disabled: boolean` flag allows toggling hosts on/off without deletion. Good for MVP!

Path: src/store/useEnvironmentStore.ts, Line: 14
✅ Zustand store creation. Lightweight alternative to Redux - minimal boilerplate, all components auto-subscribe to changes.

Path: src/App.tsx, Line: 7
✅ Full-screen dark theme layout. neutral-950 is almost black background. Flexbox makes it responsive across devices.

Path: package.json, Line: 16
✅ zustand (~2kb): Lightweight state management. Perfect for MVP - much simpler than Redux with similar functionality.

Path: vite.config.ts, Line: 8
✅ Dev server port 1420: Chosen to avoid conflicts with common dev ports (3000, 8000, 5432, etc).
```

### Token Permissions Required

**Minimum Scopes**: `repo` (full control of repositories)

**Includes Permission To**:
- Create/update pull requests ✅
- Create pull request reviews ✅
- Add review comments ✅
- Submit reviews ✅

### Result

Reviewers see inline comments when they:
1. Navigate to PR "Files changed" tab
2. Hover over commented lines
3. Comments appear inline with the diff

This creates a **self-documenting PR** that explains:
- What each section does
- Why that approach was chosen
- How it fits into the larger architecture
- Trade-offs and design decisions

---

## 11. Three-Layer Documentation Strategy

Combine all three documentation approaches for comprehensive PR clarity:

| Layer | Tool | Purpose | Audience |
|-------|------|---------|----------|
| **Overview** | Issue comment | Big picture + architecture | All reviewers |
| **Detailed** | Issue comment (long form) | File-by-file breakdown | Technical reviewers |
| **Inline** | Inline review comments | Code-level context | Detailed reviewers |

**Example PR #43**:
- ✅ Layer 1: 3,000-word overview comment
- ✅ Layer 2: 2,500-word file breakdown comment
- ✅ Layer 3: 40+ inline comments on specific lines

Result: **Complete documentation at every level**

---

## 12. Adding "🔑 Key Components Explained" Detailed Comments

**Purpose**: After generic comments, add detailed architecture and design explanations for each major file/component.

**When to Add**: After basic inline comments are submitted, create a second review with detailed component explanations.

**Strategy**: Four-Layer Documentation Approach

| Layer | Purpose | Detail Level | Audience |
|-------|---------|--------------|----------|
| Layer 1 | Overview comment | Big picture (3000+ words) | All reviewers |
| Layer 2 | File breakdown comment | Architecture breakdown (2500+ words) | Technical reviewers |
| Layer 3 | Generic inline comments | Line-specific context (40+ comments) | Detailed reviewers |
| Layer 4 | Component detailed comments | Deep dives (9+ detailed comments) | Architecture reviewers |

### Layer 4: Detailed Component Comments Structure

**Create a second pending review** with comprehensive comments for each major file:

#### For Type Definition Files
```markdown
**[File Name] - Purpose**

**Structure:**
- Core data structures
- Key properties and types

**Design:**
- Why designed this way
- Trade-offs and decisions

**Usage:**
- Where used in app
- How data flows
```

#### For State Management
```markdown
**[Tool] State Management - Purpose**

**Why This Tool:**
- Reason vs alternatives
- Bundle size
- Boilerplate level

**Key Actions:**
- Action 1: Purpose
- Action 2: Purpose

**Data Flow:**
User Action → Store Update → Components Re-render

**State Structure:**
- Core entities and relationships
```

#### For Components
```markdown
**[Component Name] - Purpose**

**Features:**
- Feature 1: What it does
- Feature 2: What it does
- Smart UX: Edge cases handled

**State:**
- Local state (if any)
- Store subscriptions

**User Interactions:**
- Action 1 → Result
- Action 2 → Result

**Styling:**
- Layout approach
- Key CSS/Tailwind utilities
```

#### For Configuration Files
```markdown
**[Config File] - Purpose**

**Why This Tool:**
- Problem solved
- Advantages vs alternatives

**Key Settings:**
- Setting 1: Purpose
- Setting 2: Purpose

**Workflow:**
Dev: [Process] → Production: [Process]
```

### Example: PR #43 Layer 4 Implementation

**9 Detailed Comments Added**:

1. **src/types/index.ts**: Type definitions architecture
    - Host interface: All 5 properties explained with examples
    - Environment interface: All 6 properties explained
    - Design decisions for each field
    - Usage throughout app
    - Future timestamp additions

2. **src/store/useEnvironmentStore.ts**: Zustand state
    - Why Zustand (vs Redux, Context)
    - State structure diagram
    - All actions explained
    - Data flow: User click → Store → Components
    - Performance notes

3. **src/App.tsx**: Root layout
    - Visual ASCII layout diagram
    - Each component responsibility
    - Tailwind classes explained
    - State flow
    - Placeholder tabs for Phase 2

4. **src/components/Sidebar.tsx**: Environment selector
    - Component structure diagram
    - 3 main sections (logo, list, footer)
    - Smart UX features (delete disabled logic)
    - All interactions documented
    - Styling approach

5. **src/components/HostsTable.tsx**: Main interface
    - Full table diagram
    - Header, search, table, empty states
    - Real-time search implementation
    - Disabled styling explanation
    - Virtual scrolling future (Issue #45)

6. **src/components/StatsBar.tsx**: Statistics
    - Layout diagram
    - Calculation logic
    - Auto-update mechanism
    - Color coding for icons
    - Sync placeholder explanation

7. **setActiveEnvironment action**: Key pattern
    - Why both fields updated
    - Data flow from UI to store
    - Enforcement mechanism
    - Performance implications

8. **Dependencies (package.json)**: Frontend libraries
    - Each library purpose
    - Why chosen over alternatives
    - Tauri bridge explained
    - Zustand rationale
    - Lucide icons used

9. **vite.config.ts**: Build system
    - Why Vite chosen
    - Each config section
    - Dev vs production difference
    - Tauri integration
    - Platform-specific targeting

### When to Use This Approach

✅ **Use when**:
- Large PR with multiple components
- Complex architecture decisions
- Significant codebase addition
- New developers learning codebase
- Establishing patterns for future

❌ **Skip when**:
- Small bug fix or hotfix
- Simple one-file change
- Already documented elsewhere
- Time-sensitive merge needed

### Implementation Tips

1. **Create second review** after first is submitted
2. **One comment per major file/component**
3. **Use consistent structure** across comments
4. **Focus on component itself only** (no future phases)
5. **Keep comments concise** (~200-400 chars per comment)
6. **Explain "why"** design choices
7. **Show data flow** for state management
8. **Avoid unnecessary prefix** - let title speak for itself

### Token Requirements

Same as Layer 3:
- **Minimum Scopes**: `repo`
- **Tool**: `add_comment_to_pending_review`
- **Process**: Create → Add comments → Submit

### Result

Reviewers get **complete architectural context**:
- ✅ Why each design choice was made
- ✅ How components relate and interact
- ✅ Data flow from user action to UI update
- ✅ Future enhancement roadmap
- ✅ Performance and scalability notes
- ✅ Known limitations and trade-offs

---

## 8. Completion

**Final Verification**:
- [ ] All tests passing
- [ ] Documentation complete
- [ ] Code follows standards
- [ ] All changes committed

**Deliverables**:
- Deliverable 1: [What was delivered]
- Deliverable 2: [What was delivered]

**Next Steps**:
- [Follow-up task if any]
