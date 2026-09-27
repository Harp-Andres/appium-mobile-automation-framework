# App under test + farms (local repo)

This repo’s specialty is **local Appium**. Do not break `local.properties` ExpandTesting runs.

## Free apps (shared recommendation)

| App | Use here |
| --- | --- |
| ExpandTesting practice (current default) | Keep for your proven local package/activity flow |
| **TheApp** (`apps/TheApp.apk`) | Portable alternative → `config/theapp.properties` + `./scripts/download-test-apps.sh` |
| ApiDemos | Prefer the **cloud** sibling for farm demos |

Cloud farms (BrowserStack / AWS) live in  
[`appium-mobile-cloud-automation-framework`](https://github.com/Harp-Andres/appium-mobile-cloud-automation-framework).  
Serenity Screenplay: [`demo-serenity-screenplay-mobile`](https://github.com/Harp-Andres/demo-serenity-screenplay-mobile).
