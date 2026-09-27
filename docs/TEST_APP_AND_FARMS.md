# AUT + execution (local / self-hosted repo)

## Objective

This repo is the **mature local + self-hosted** Appium demo. Default AUT stays **ExpandTesting Practice** so existing self-hosted runners keep working.

## Default (do not change lightly)

`src/test/resources/config/local.properties`:

- package: `com.expandtesting.practice`
- activity: `com.expandtesting.practice.MainActivity`
- features: `framework_health`, `mobile_smoke`, `counter_demo`

## Optional portable AUT

`config/theapp.properties` + `./scripts/download-test-apps.sh` exist only as an experiment path. They are **not** the self-hosted default.

Cloud farms → sibling `appium-mobile-cloud-automation-framework`  
Serenity Screenplay → sibling `demo-serenity-screenplay-mobile`
