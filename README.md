# Appium Mobile Automation Framework (local)

Demo framework for **local emulator or physical device** automation with Appium, Cucumber (BDD), JUnit 5, Page Objects, and Allure. Cloud device farms (BrowserStack, AWS Device Farm) live in the sibling repo [`appium-mobile-cloud-automation-framework`](https://github.com/Harp-Andres/appium-mobile-cloud-automation-framework).

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Appium](https://img.shields.io/badge/Appium-9.2.3-blue.svg)](https://appium.io/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-purple.svg)](https://maven.apache.org/)

## Scope

| In this repo | In the cloud sibling repo |
|--------------|---------------------------|
| Local Appium server (`127.0.0.1:4723`) | BrowserStack App Automate |
| Android emulator / USB device | Optional local fallback only |
| Self-hosted GitHub Actions runner | BrowserStack CI workflow |

Default app under test: **TheApp** (`com.appiumpro.the_app`). See `docs/TEST_APP_AND_FARMS.md`.

## Stack

- **Java 17**, **Maven**, **Appium Java Client 9.2.x**
- **Cucumber 7** + **JUnit Platform**
- **Allure** for reports and evidence (screenshots via `EvidenceCapture` / hooks)
- **SLF4J + Logback** for framework logging

## Project layout

```text
src/test/java/com/automatizacion/base/
  config/     FrameworkConfig, ConfigRequiredKeysValidator
  driver/     DriverFactory, DriverManager
  hooks/      Cucumber lifecycle + evidence
  pages/      Page objects (POM)
  actions/    Business actions for steps
  steps/      Cucumber step definitions
  utils/      EvidenceCapture
src/test/resources/config/local.properties
src/test/resources/features/
  framework_health.feature   # no device
  mobile_smoke.feature       # session + foreground app
  theapp_login_smoke.feature # TheApp login POM smoke
apps/TheApp.apk              # download via scripts/download-test-apps.sh (gitignored)
```

## Setup

1. Install JDK 17, Maven, Android SDK, Appium 2.x, and start an emulator or connect a device.
2. Download the AUT: `./scripts/download-test-apps.sh`
3. Adjust `device.name` / `platform.version` in `local.properties` if needed.

## Run tests

**Unit tests only** (no Appium server required):

```bash
mvn test
```

**BDD / Cucumber** (requires Appium + device/emulator):

```bash
mvn test -Pbdd -Drun.mobile.tests=true -Denv=local
```

Skip `@mobile` scenarios but still run `framework_health.feature`:

```bash
mvn test -Pbdd -Drun.mobile.tests=false
```

## Reports and evidence

- Cucumber HTML: `target/cucumber-reports/`
- Allure results: `target/allure-results/` → `mvn allure:serve` or `allure generate`
- Screenshots on key steps via `EvidenceCapture` (see `EvidenceHooks`)

## CI

GitHub Actions: `.github/workflows/ci-cd-mobile-tests.yml`

- **Sanity** (every push/PR): `mvn test` on `ubuntu-latest`
- **Mobile** (manual `workflow_dispatch` only): self-hosted Windows runner with Android + Appium → `mvn test -Pbdd -Drun.mobile.tests=true -Denv=local`

Self-hosted runner labels: `self-hosted`, `android`, `appium`, `windows`. Ensure Appium listens on `http://127.0.0.1:4723` and `adb devices` shows your emulator.

## Related docs

- `docs/TEST_APP_AND_FARMS.md` — AUT choice and sibling repos
