# Improve Empty States and Loading Feedback

> **Draft.** This file is a template for the organizer to publish as a GitHub issue. It is not published or assigned until the organizer does so.

**Difficulty:** Good First Issue  
**Points:** 5

## Problem statement

The app shows a single generic message ("No tasks to show.") whenever the list is empty, and the loading state is only a line of plain text. New users get no guidance, and the message is the same whether they have no tasks at all or a filter simply matches nothing.

## Current behavior

- While tasks load, the page shows plain text: "Loading tasks…".
- When the visible list is empty, the page always shows "No tasks to show." regardless of why.
- Screen readers are not told when loading starts or finishes.

## Expected behavior

- Two different empty messages: one for "you have no tasks yet" (with a hint to use the form) and one for "no tasks match the current filter".
- A small CSS-only loading indicator (for example a spinner) next to the loading text.
- Loading and empty messages are announced politely to assistive technology.
- The empty message never flashes before the first load has finished.

## Acceptance criteria

- [ ] Empty list with no tasks in the database shows a friendly first-use message.
- [ ] Selecting Pending or Completed when no task matches shows a message naming that filter.
- [ ] A visible loading indicator appears while tasks are being fetched.
- [ ] The loading indicator respects `prefers-reduced-motion`.
- [ ] Messages are announced via an appropriate ARIA live region or role.
- [ ] No `innerHTML` is used with task data; existing behaviour is unchanged.

## Suggested files or components to inspect

- `src/main/resources/static/index.html` (`#loading`, `#empty`)
- `src/main/resources/static/js/app.js` (`render()`)
- `src/main/resources/static/css/styles.css`

## Relevant edge cases

- First page load while the backend is slow.
- Switching filters when the list is empty.
- Backend unavailable (error message shown, empty state should not mislead).
- Deleting the last remaining task.

## Hint

Look at how `render()` decides when to show `#empty`, and think about which pieces of state (`tasks`, `currentFilter`, `isLoading`) the message depends on.

## Before you submit

1. Run the app (`mvn spring-boot:run`) and try your change in the browser.
2. Run `mvn test` and make sure everything passes.
3. Keep your change focused on this issue only.
4. Open a pull request using the pull request template and reference this issue (for example `Closes #<issue number>`).

See [CONTRIBUTING.md](../../CONTRIBUTING.md) for the full workflow.
