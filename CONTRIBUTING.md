# Contributing to FunLearn

Here are the guidelines we'd like you to follow:

- [Working with FunLearn](#working)
- [Coding Rules](#rules)
- [Commit Message Guidelines](#commit)
- [Issues and Bugs](#issue)
- [Feature Requests](#feature)

## <a id="working"></a> Working with FunLearn

- Fork and Clone the repository and add this as remote upstream.
- Create new branch for features.
- Follow our [Coding Rules](#rules).
- Run the Linter and all tests.
- Pull latest changes from upstream before pushing your code or creating a new feature branch.
- Send a PR to this repo for review and merging

---

**NOTE:**

Never push directly to main repository (upstream). Only push to your forked repo (origin) and send a pull request to
the main repository

---

## <a id="rules"></a> Coding Rules

To ensure consistency throughout the source code, keep these rules in mind as you are working:

- Also refer the [Jetpack architecture guide](https://developer.android.com/jetpack/docs/guide) for the conventions on
  using the jetpact libraries.

## <a id="commit"></a> Git Commit Guidelines

#### Commit Message Format

Each commit message consists of a **header**, a **body** and a **footer**. The header has a special
format that includes a **type**, a **scope** and a **subject**:

```bash
<type>(<scope>): <subject>
<BLANK LINE>
<body>
<BLANK LINE>
<footer>
```

Any line of the commit message cannot be longer 100 characters! This allows the message to be easier to read on github
as well as in various git tools.

#### Example Commit Message

```bash
feat(Profile): display QR code

fetch the qr code from API and display it on Profile page (ProfileFragment.kt)

fixes #1234
```

Please follow the conventions followed [here](http://karma-runner.github.io/latest/dev/git-commit-msg.html).

Also, refer [this page](https://chris.beams.io/posts/git-commit/) on how to write the body

## <a id="issue"></a> Found an Issue?

If you find a bug in the source code or a mistake in the documentation, submit an issue to The GitHub Repository.
Even better you can submit a Pull Request with a fix.

## <a id="feature"></a> Want a Feature?

You can request a new feature by submitting an issue to our GitHub Repository
