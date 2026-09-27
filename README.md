# Appium Mobile Automation Framework (local / self-hosted)

**Objective:** demo **classic Appium + Cucumber + POM** on a **local emulator or self-hosted GitHub Actions runner**.

This is the mature **self-hosted / local** specialty. Do not treat it as a cloud-farm or Serenity Screenplay project.

| Sibling | Specialty |
| --- | --- |
| **This repo** | Local + self-hosted Appium (ExpandTesting practice app) |
| [`appium-mobile-cloud-automation-framework`](https://github.com/Harp-Andres/appium-mobile-cloud-automation-framework) | BrowserStack + AWS Device Farm |
| [`demo-serenity-screenplay-mobile`](https://github.com/Harp-Andres/demo-serenity-screenplay-mobile) | Serenity BDD Screenplay teaching |

## Default AUT (self-hosted proven)

`local.properties` uses **ExpandTesting Practice**:

- `app.package=com.expandtesting.practice`
- `app.activity=com.expandtesting.practice.MainActivity`

Scenarios: framework health, mobile smoke, **counter demo**.

Optional portable AUT (TheApp) lives in `config/theapp.properties` + `./scripts/download-test-apps.sh` — **not** the default, so self-hosted stays green.

## Run

```bash
# Unit tests (no device) — CI sanity
mvn test

# BDD on local Appium + device/emulator (self-hosted)
mvn test -Pbdd
```

## CI

`.github/workflows/ci-cd-mobile-tests.yml`:

- **ubuntu**: `mvn test` (units)
- **self-hosted**: optional `workflow_dispatch` with Appium + Android device (`mvn test -Pbdd`)

See `docs/TEST_APP_AND_FARMS.md` for optional TheApp notes only.
