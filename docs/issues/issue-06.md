# Add a Clear Completed Tasks Action

> **Draft.** This file is a template for the organizer to publish as a GitHub issue. It is not published or assigned until the organizer does so.

**Difficulty:** Intermediate  
**Points:** 15

## Problem statement

To remove finished tasks, users must delete them one by one. A single action to clear all completed tasks is missing.

## Current behavior

- `DELETE /api/tasks/{id}` removes one task.
- There is no endpoint or button that removes several tasks at once.

## Expected behavior

- A new endpoint `DELETE /api/tasks/completed` removes all tasks whose status is `COMPLETED` and responds with `200` and JSON like `{"deleted": 3}`.
- A "Clear completed" button in the task list header asks for confirmation (including the number of tasks) before calling it.
- The button is disabled when there are no completed tasks.

## Acceptance criteria

- [ ] Endpoint deletes only `COMPLETED` tasks and leaves `PENDING` tasks untouched.
- [ ] Endpoint returns `200` with the number of deleted tasks, including `0` when none exist.
- [ ] The new endpoint does not break `DELETE /api/tasks/{id}`.
- [ ] The frontend asks for confirmation and shows the count.
- [ ] Success and error messages follow the existing message style.
- [ ] The list and summary refresh after clearing.
- [ ] At least two backend tests are added (some completed, none completed).
- [ ] README API table is updated.

## Suggested files or components to inspect

- `src/main/java/com/hacktheweb/tasktrack/controller/TaskController.java`
- `src/main/java/com/hacktheweb/tasktrack/service/TaskService.java`
- `src/main/java/com/hacktheweb/tasktrack/repository/TaskRepository.java`
- `src/main/resources/static/js/app.js`
- `README.md`

## Relevant edge cases

- No completed tasks.
- All tasks completed.
- Editing a task that is cleared in the meantime.
- Route ambiguity between `/completed` and `/{id}`.

## Hint

Spring Data can derive delete queries from method names, but derived deletes need a transaction.

## Before you submit

1. Run the app (`mvn spring-boot:run`) and try your change in the browser.
2. Run `mvn test` and make sure everything passes.
3. Keep your change focused on this issue only.
4. Open a pull request using the pull request template and reference this issue (for example `Closes #<issue number>`).

See [CONTRIBUTING.md](../../CONTRIBUTING.md) for the full workflow.
