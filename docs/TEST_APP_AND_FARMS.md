# AUT (local / self-hosted)

## Specialty

This repo keeps the **mature Windows self-hosted runner** (Appium already up, ADB, `mvn test -Pbdd`). The AUT is **TheApp** for professional, portable Appium demos.

## Download

- Unix: `./scripts/download-test-apps.sh`
- Windows CI: `scripts\download-test-apps.cmd` (invoked by the workflow before BDD)

## What must not break

- Runner labels: `self-hosted, android, appium, windows`
- Appium URL: `http://127.0.0.1:4723`
- Command: `mvn test -Pbdd -Drun.mobile.tests=true -Denv=local ...`
- Artifact / Allure publish jobs

Cloud farms → sibling `appium-mobile-cloud-automation-framework`  
Serenity Screenplay → sibling `demo-serenity-screenplay-mobile`
