# Improve Form Validation and Error Messages

> **Draft.** This file is a template for the organizer to publish as a GitHub issue. It is not published or assigned until the organizer does so.

**Difficulty:** Beginner  
**Points:** 10

## Problem statement

Validation errors from the server (or the empty-title check) appear in one general message at the top of the page. Users cannot tell which field is wrong, and nothing warns them before they exceed the length limits.

## Current behavior

- The frontend only checks that the title is not blank, and shows the error in the top message box.
- The backend returns field-level errors in the `details` object, but the frontend joins them into one sentence.
- Title is limited to 100 characters and description to 500, but users get no live feedback.

## Expected behavior

- Each invalid field shows its own error message next to the field.
- Invalid fields are marked for assistive technology and the first one receives focus.
- Errors clear when the user corrects the field.
- Server-side field errors are shown beside the matching field.

## Acceptance criteria

- [ ] A blank or whitespace-only title shows an inline error beside the title field.
- [ ] A character counter or similar hint shows remaining characters for title and description.
- [ ] Fields with errors use `aria-invalid` and are linked to their message with `aria-describedby`.
- [ ] The first invalid field receives focus after a failed submit.
- [ ] Field errors returned in the API `details` object are displayed beside the right field.
- [ ] No `alert()` dialogs are used.
- [ ] Valid submissions behave exactly as before.

## Suggested files or components to inspect

- `src/main/resources/static/index.html`
- `src/main/resources/static/js/app.js` (`buildErrorMessage`, the submit handler)
- `src/main/resources/static/css/styles.css`
- `src/main/java/com/hacktheweb/tasktrack/exception/GlobalExceptionHandler.java` (read only, to understand the response shape)

## Relevant edge cases

- Title of exactly 100 characters.
- Pasting text longer than the limit.
- Title made only of spaces.
- A server error that does not include `details`.
- Editing a task and clearing the title.

## Hint

You will need the error response to reach the submit handler as data, not only as text. Look at how `api()` throws errors today.

## Before you submit

1. Run the app (`mvn spring-boot:run`) and try your change in the browser.
2. Run `mvn test` and make sure everything passes.
3. Keep your change focused on this issue only.
4. Open a pull request using the pull request template and reference this issue (for example `Closes #<issue number>`).

See [CONTRIBUTING.md](../../CONTRIBUTING.md) for the full workflow.
