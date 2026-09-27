# App under test (local repo)

This repo targets **local Appium only** (emulator or USB device). Cloud farms live in [`appium-mobile-cloud-automation-framework`](https://github.com/Harp-Andres/appium-mobile-cloud-automation-framework). Serenity Screenplay demos live in [`demo-serenity-screenplay-mobile`](https://github.com/Harp-Andres/demo-serenity-screenplay-mobile).

## Default AUT: TheApp

| Item | Value |
| --- | --- |
| APK | `apps/TheApp.apk` (not in git — run `./scripts/download-test-apps.sh`) |
| Package | `com.appiumpro.the_app` |
| Activity | `.MainActivity` |
| Config | `src/test/resources/config/local.properties` |

BDD smoke: home → **Login Screen** → credentials `alice` / `mypassword` → secret text contains `You are logged in as`.

## Other apps

Use **ApiDemos** or farm-specific builds in the **cloud** sibling repo, not here.
