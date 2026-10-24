# Add Task Search by Title and Description

> **Draft.** This file is a template for the organizer to publish as a GitHub issue. It is not published or assigned until the organizer does so.

**Difficulty:** Beginner  
**Points:** 10

## Problem statement

Once a user has many tasks there is no way to find one quickly. Users can only scan the list or filter by status.

## Current behavior

- There is no search control.
- The only filtering is by status (All / Pending / Completed).

## Expected behavior

- A search box above the task list filters tasks as the user types.
- Matching is case-insensitive and checks both title and description.
- Search combines with the existing status filter.
- The summary counts at the top still show totals for all tasks.

## Acceptance criteria

- [ ] A labelled search input is added (visible or visually-hidden label, not placeholder-only).
- [ ] Typing filters the list without a page reload or extra API call.
- [ ] Search is case-insensitive and ignores leading/trailing spaces.
- [ ] Tasks with no description are handled without errors.
- [ ] A message such as "No tasks match your search" is shown when nothing matches.
- [ ] A way to clear the search is provided (button or Escape key).
- [ ] Search works together with the status filter.

## Suggested files or components to inspect

- `src/main/resources/static/index.html`
- `src/main/resources/static/js/app.js` (`render()`)
- `src/main/resources/static/css/styles.css`

## Relevant edge cases

- Task with `description` equal to `null`.
- Search text containing regex characters such as `(` or `*`.
- Search while a task is being edited.
- Search term that matches only the description.

## Hint

Filtering already happens inside `render()`. Consider where a `searchTerm` variable would live and how it joins the existing status check.

## Before you submit

1. Run the app (`mvn spring-boot:run`) and try your change in the browser.
2. Run `mvn test` and make sure everything passes.
3. Keep your change focused on this issue only.
4. Open a pull request using the pull request template and reference this issue (for example `Closes #<issue number>`).

See [CONTRIBUTING.md](../../CONTRIBUTING.md) for the full workflow.
