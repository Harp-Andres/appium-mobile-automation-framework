# Appium Mobile Automation Framework (local / self-hosted)

**Objective:** classic **Appium + Cucumber + POM** on a **local emulator or Windows self-hosted GitHub Actions runner**.

The self-hosted pipeline (labels `self-hosted, android, appium, windows`, Appium on `:4723`, `mvn test -Pbdd`, Allure artifacts) stays the execution model. What evolves is the **AUT and code quality**.

| Sibling | Specialty |
| --- | --- |
| **This repo** | Local + self-hosted Appium (mature runner) |
| [`appium-mobile-cloud-automation-framework`](https://github.com/Harp-Andres/appium-mobile-cloud-automation-framework) | BrowserStack + AWS Device Farm |
| [`demo-serenity-screenplay-mobile`](https://github.com/Harp-Andres/demo-serenity-screenplay-mobile) | Serenity BDD Screenplay |

## App under test

**[TheApp](https://github.com/appium-pro/TheApp)** — industry-standard free Appium demo (`com.appiumpro.the_app`).

```bash
# macOS/Linux
./scripts/download-test-apps.sh

# Windows (self-hosted)
scripts\download-test-apps.cmd
```

Demo login: `alice` / `mypassword`

## Layers (SOLID / OOP)

| Layer | Responsibility |
| --- | --- |
| `ui/*` | Locators only (PageFactory) |
| `pages/*` | Interactions / waits |
| `actions/*` | Business flows (injectable pages) |
| `steps/*` | Cucumber glue only |
| `driver/*` + `config/*` | Session + configuration |

## Run

```bash
# Unit tests (no device) — CI sanity on ubuntu-latest
mvn test

# BDD on Appium + device (self-hosted / laptop)
./scripts/download-test-apps.sh   # or .cmd on Windows
mvn test -Pbdd
```

## CI

`.github/workflows/ci-cd-mobile-tests.yml`:

1. **ubuntu-latest**: `mvn test` (always)
2. **self-hosted** (`workflow_dispatch`): verify ADB/Appium → download TheApp → `mvn test -Pbdd` → Allure artifacts
