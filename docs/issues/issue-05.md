# Add a Task Status Filter Dropdown and Filter Count

> **Draft.** This file is a template for the organizer to publish as a GitHub issue. It is not published or assigned until the organizer does so.

**Difficulty:** Beginner  
**Points:** 10

## Problem statement

Filtering is limited to three status buttons. Users cannot filter by priority and cannot see how many tasks the current filter is showing.

## Current behavior

- Status filtering uses three buttons (All, Pending, Completed).
- There is no priority filter.
- The page never says how many tasks are currently displayed.

## Expected behavior

- A status dropdown that includes the number of tasks in each status (for example `Pending (3)`).
- A priority dropdown with All, Low, Medium, High.
- A line such as "Showing 2 of 7 tasks" that updates as filters change.

## Acceptance criteria

- [ ] The status buttons are replaced by (or reorganised into) a labelled `<select>` that behaves identically.
- [ ] Option labels include live counts based on all tasks.
- [ ] A labelled priority `<select>` is added and combines with the status filter.
- [ ] A visible "Showing X of Y" message updates after every filter change and after every create, update or delete.
- [ ] Everything is keyboard accessible.
- [ ] No leftover unused CSS or JavaScript for the removed buttons.

## Suggested files or components to inspect

- `src/main/resources/static/index.html` (`.filters`)
- `src/main/resources/static/js/app.js` (`currentFilter`, filter button handlers, `render()`)
- `src/main/resources/static/css/styles.css`

## Relevant edge cases

- Zero tasks.
- Changing a task's status while a status filter is active.
- Deleting the last task in a filtered view.
- Counts after editing a task's priority.

## Hint

The counts need to come from the full `tasks` array, not from the already-filtered list.

## Before you submit

1. Run the app (`mvn spring-boot:run`) and try your change in the browser.
2. Run `mvn test` and make sure everything passes.
3. Keep your change focused on this issue only.
4. Open a pull request using the pull request template and reference this issue (for example `Closes #<issue number>`).

See [CONTRIBUTING.md](../../CONTRIBUTING.md) for the full workflow.
