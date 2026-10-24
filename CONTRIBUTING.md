# Contributing to TaskTrack

Thanks for taking part in Hack the Web! Follow these steps to get your contribution reviewed quickly.

## 1. Pick an issue

- Open the repository's **Issues** tab and choose an open, unassigned issue. Comment to say you want to work on it and wait for the organizer to assign it.
- Detailed descriptions are in [docs/ISSUES.md](docs/ISSUES.md). Those files are drafts, so the GitHub issue is the source of truth.
- Work on one issue per pull request.

## 2. Set up

Requirements: Java 21, Maven. See the [README](README.md) for details.

```bash
# fork the repo on GitHub first, then:
git clone https://github.com/<your-username>/tasktrack.git
cd tasktrack
git remote add upstream <original-repo-url>
mvn test
mvn spring-boot:run
```

## 3. Create a branch

```bash
git checkout -b issue-<number>-short-description
```

Example: `issue-2-task-search`.

## 4. Code guidelines

- Keep changes small and focused on the issue.
- Frontend: plain HTML, CSS and vanilla JavaScript only. Do not add frameworks, build tools or libraries.
- Never put task data into the page with `innerHTML`. Use `textContent` or `createElement`.
- Backend: keep HTTP code in the controller, rules in the service, database access in the repository.
- Use clear names and add comments only where the reason is not obvious.
- Do not store tasks in `localStorage`; the backend is the source of truth.
- Do not commit the `data/` folder, `target/`, or editor files.
- Update the README if you change the API.

## 5. Test

```bash
mvn test
```

Add or update backend tests when you change backend behaviour. For frontend changes, test manually in the browser, including keyboard use and a narrow mobile-size window, and describe what you checked in your pull request.

## 6. Commit and push

Write short, clear commit messages, for example `Add search box to task list`.

```bash
git add .
git commit -m "Add search box to task list"
git push origin issue-2-task-search
```

## 7. Open a pull request

- Open it against the `main` branch of the original repository.
- Fill in the pull request template and reference the issue (`Closes #<number>`).
- Add screenshots for visible changes.
- Respond to review comments. Points are awarded after the pull request is merged or accepted by the organizer.

## Be respectful

Be kind and constructive in issues and reviews. Do your own work, and do not copy another participant's pull request.
