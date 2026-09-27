# Appium Mobile Automation Framework (local)

Demo framework for **local emulator or physical device** automation with Appium, Cucumber (BDD), JUnit 5, and Allure. Cloud device farms (BrowserStack, AWS Device Farm) live in the sibling repo [`appium-mobile-cloud-automation-framework`](https://github.com/Harp-Andres/appium-mobile-cloud-automation-framework).

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Appium](https://img.shields.io/badge/Appium-9.2.3-blue.svg)](https://appium.io/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-purple.svg)](https://maven.apache.org/)

## Scope

| In this repo | In the cloud sibling repo |
|--------------|---------------------------|
| Local Appium server (`127.0.0.1:4723`) | BrowserStack App Automate |
| Android emulator / USB device | Optional local fallback only |
| Self-hosted GitHub Actions runner | BrowserStack CI workflow |

## Stack

- **Java 17**, **Maven**, **Appium Java Client 9.2.x**
- **Cucumber 7** + **JUnit Platform**
- **Allure** for reports and evidence
- **SLF4J + Logback** for framework logging (no `System.out` in production code)

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
```

## Run tests

**Unit tests only** (no Appium server required):

```bash
mvn test
```

**BDD / Cucumber** (requires Appium + device/emulator):

```bash
mvn test -Pbdd -Drun.mobile.tests=true -Denv=local
```

Skip mobile scenarios but still run framework health:

```bash
mvn test -Pbdd -Drun.mobile.tests=false
```

Configure `src/test/resources/config/local.properties` for your emulator (`device.name`, `app.package`, etc.).

## CI

GitHub Actions workflow `.github/workflows/ci-cd-mobile-tests.yml`:

- **Sanity**: `mvn test` (unit tests on `ubuntu-latest`)
- **Mobile**: manual `workflow_dispatch` on a self-hosted Android/Appium runner (`mvn test -Pbdd ...`)

## Related docs

- `SETUP_SELF_HOSTED_RUNNER.md` — self-hosted runner for real devices
- `CAPTURA_EVIDENCIAS.md`, `COMO_VER_REPORTES.md` — evidence and Allure
- `GUIA_CUCUMBER_STEPS.md`, `SELECTORES_MULTIPLATAFORMA.md` — BDD and locators
