# Add Pagination to the Task List

> **Draft.** This file is a template for the organizer to publish as a GitHub issue. It is not published or assigned until the organizer does so.

**Difficulty:** Intermediate  
**Points:** 20

## Problem statement

`GET /api/tasks` returns every task, and the frontend renders all of them. With hundreds of tasks the page becomes slow and hard to use.

## Current behavior

- The API returns all tasks in a single JSON array.
- Filtering, summary counts and rendering all use the complete array in the browser.

## Expected behavior

- The task list shows a fixed number of tasks per page (default 10) with Previous and Next controls and a "Page X of Y" indicator.
- The backend supports `page` and `size` request parameters.
- Summary counts still show totals for all tasks, not just the current page.
- Status filtering still works across all pages.

## Acceptance criteria

- [ ] Backend accepts `page` (zero-based) and `size` and returns enough information for the UI to know the total number of pages.
- [ ] Invalid values (negative page, size of 0, size above a sensible maximum) are handled with a `400` or safe defaults, and the behaviour is documented.
- [ ] The default page size is 10.
- [ ] Previous/Next are disabled at the first/last page.
- [ ] Deleting the last task on a page moves the user to a valid page.
- [ ] Summary counts remain correct for the whole data set.
- [ ] README documents the new request parameters and response shape.
- [ ] Backend tests cover paging behaviour.

## Suggested files or components to inspect

- `src/main/java/com/hacktheweb/tasktrack/controller/TaskController.java`
- `src/main/java/com/hacktheweb/tasktrack/service/TaskService.java`
- `src/main/java/com/hacktheweb/tasktrack/repository/TaskRepository.java`
- `src/main/resources/static/js/app.js`
- `src/main/resources/static/index.html`
- `README.md`

## Relevant edge cases

- Exactly 10, 11 and 0 tasks.
- Requesting a page past the end.
- Changing the filter while on page 3.
- Deleting the only task on the last page.
- Summary counts versus a paged list.

## Hint

Think carefully about where the summary counts and status filter should be computed once the browser no longer holds every task. You may add endpoints if you document them.

## Before you submit

1. Run the app (`mvn spring-boot:run`) and try your change in the browser.
2. Run `mvn test` and make sure everything passes.
3. Keep your change focused on this issue only.
4. Open a pull request using the pull request template and reference this issue (for example `Closes #<issue number>`).

See [CONTRIBUTING.md](../../CONTRIBUTING.md) for the full workflow.
