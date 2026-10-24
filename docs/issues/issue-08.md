# Add Automated Tests for Task API Edge Cases

> **Draft.** This file is a template for the organizer to publish as a GitHub issue. It is not published or assigned until the organizer does so.

**Difficulty:** Intermediate  
**Points:** 20

## Problem statement

The existing tests cover the happy paths, a missing task and a blank title. Many edge cases that real clients hit are not tested, so regressions could slip in unnoticed.

## Current behavior

- `TaskApiTest` covers create, list, get, update, delete, 404 and blank title.
- Validation limits, invalid enum values, malformed JSON and timestamp behaviour are not tested.

## Expected behavior

- New tests that document and protect the API's edge-case behaviour, without changing production code (unless you find and report a real bug).

## Acceptance criteria

- [ ] Title with 100 characters is accepted; 101 characters is rejected with `400`.
- [ ] Description with 501 characters is rejected with `400`.
- [ ] An invalid `status` or `priority` value returns `400` with the JSON error body.
- [ ] A malformed JSON body returns `400`.
- [ ] A non-numeric id such as `/api/tasks/abc` returns `400`.
- [ ] `PUT` without `status` or `priority` returns `400`.
- [ ] Updating a task does not change `createdAt` or `id`.
- [ ] A client-supplied `id` or `createdAt` on create is ignored.
- [ ] `PUT` with a `null` description or `dueDate` clears those fields.
- [ ] Error responses never contain a stack trace.
- [ ] Tests are independent, deterministic and use the isolated test database.
- [ ] `mvn test` passes.

## Suggested files or components to inspect

- `src/test/java/com/hacktheweb/tasktrack/TaskApiTest.java`
- `src/test/resources/application.properties`
- `src/main/java/com/hacktheweb/tasktrack/exception/GlobalExceptionHandler.java` (to understand expected responses)

## Relevant edge cases

- Boundary values (exactly at and just over each limit).
- Tests depending on execution order.
- Comparing timestamps precisely.

## Hint

Read the existing tests first and follow their style. If a test reveals a genuine bug, describe it in your pull request.

## Before you submit

1. Run the app (`mvn spring-boot:run`) and try your change in the browser.
2. Run `mvn test` and make sure everything passes.
3. Keep your change focused on this issue only.
4. Open a pull request using the pull request template and reference this issue (for example `Closes #<issue number>`).

See [CONTRIBUTING.md](../../CONTRIBUTING.md) for the full workflow.
