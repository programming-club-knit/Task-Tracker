# Add Sorting by Due Date and Priority

> **Draft.** This file is a template for the organizer to publish as a GitHub issue. It is not published or assigned until the organizer does so.

**Difficulty:** Beginner  
**Points:** 10

## Problem statement

Tasks are always shown newest first. Users cannot see which tasks are due soonest or most important.

## Current behavior

- The backend returns tasks newest first and the frontend displays them in that order.
- There is no sort control.

## Expected behavior

- A sort control with at least: Newest first (default), Due date (soonest first), Priority (high to low).
- Tasks without a due date appear after tasks that have one when sorting by due date.
- Sorting is applied after filtering.

## Acceptance criteria

- [ ] A labelled sort `<select>` is added to the list header.
- [ ] Default order matches the current behaviour (newest first).
- [ ] Due date sort is ascending and places tasks without a due date last.
- [ ] Priority sort orders HIGH, MEDIUM, LOW.
- [ ] Ties are broken consistently (for example newest first).
- [ ] Sorting does not mutate the stored `tasks` array in a way that breaks other features.
- [ ] Works together with the status filter.

## Suggested files or components to inspect

- `src/main/resources/static/index.html`
- `src/main/resources/static/js/app.js`
- `src/main/resources/static/css/styles.css`

## Relevant edge cases

- Several tasks with the same due date.
- All tasks without due dates.
- Tasks due on the same day with different priorities.
- Editing a task so that its position changes.

## Hint

`dueDate` arrives as an ISO string such as `2026-10-30`. Think about whether you need to convert it to compare two dates.

## Before you submit

1. Run the app (`mvn spring-boot:run`) and try your change in the browser.
2. Run `mvn test` and make sure everything passes.
3. Keep your change focused on this issue only.
4. Open a pull request using the pull request template and reference this issue (for example `Closes #<issue number>`).

See [CONTRIBUTING.md](../../CONTRIBUTING.md) for the full workflow.
