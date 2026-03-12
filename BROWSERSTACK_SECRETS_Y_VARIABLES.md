# BrowserStack: que colocar en cada Secret y Variable (GitHub Actions)

Esta guia aplica al workflow `/.github/workflows/ci-cd-mobile-tests-browserstack.yml`.

## 1) Dónde se configura en GitHub

- Secrets: `GitHub Repo > Settings > Secrets and variables > Actions > Secrets`
- Variables: `GitHub Repo > Settings > Secrets and variables > Actions > Variables`

> Recomendacion: usa `Repository secrets/variables` para empezar. Luego, si quieres mas control por ambiente, migralos a `Environment`.

## 2) Secrets obligatorios

### `BROWSERSTACK_USERNAME`
- Tipo: **Secret**
- Obligatorio: **Si**
- Que valor poner:
  - Tu **username de BrowserStack** (no siempre es el correo).
- Formato esperado:
  - Texto simple, por ejemplo: `andresrdrgzps`
- Dónde obtenerlo en BrowserStack:
  - `Account Settings` -> `Access Keys` / `Automate` (veras `Username` y `Access Key`).

### `BROWSERSTACK_ACCESS_KEY`
- Tipo: **Secret**
- Obligatorio: **Si**
- Que valor poner:
  - Tu **Access Key** de BrowserStack.
- Formato esperado:
  - Cadena alfanumerica larga.
- Dónde obtenerlo:
  - Mismo lugar que el username en BrowserStack.

### `BROWSERSTACK_ANDROID_APP_ID`
- Tipo: **Secret**
- Obligatorio: **Si** (para ejecutar Android)
- Que valor poner:
  - El identificador de app subido a BrowserStack para Android.
- Formato esperado:
  - `bs://xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx`
- Dónde obtenerlo:
  - Al subir el APK a BrowserStack App Automate, te devuelve ese `bs://...`.

### `BROWSERSTACK_IOS_APP_ID`
- Tipo: **Secret**
- Obligatorio: **Si** (para ejecutar iOS)
- Que valor poner:
  - El identificador de app subido a BrowserStack para iOS.
- Formato esperado:
  - `bs://yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy`
- Dónde obtenerlo:
  - Al subir el IPA a BrowserStack App Automate, te devuelve ese `bs://...`.

---

## 3) Variables obligatorias

### `BROWSERSTACK_ANDROID_APP_PACKAGE`
- Tipo: **Variable**
- Obligatorio: **Si**
- Que valor poner:
  - El package de Android de tu app.
- Formato esperado:
  - `com.empresa.app`
- Dónde obtenerlo:
  - `AndroidManifest.xml`, build config, o inspeccionando el APK.

### `BROWSERSTACK_IOS_BUNDLE_ID`
- Tipo: **Variable**
- Obligatorio: **Si**
- Que valor poner:
  - El bundle id de iOS de tu app.
- Formato esperado:
  - `com.empresa.app.ios`
- Dónde obtenerlo:
  - Xcode project (`PRODUCT_BUNDLE_IDENTIFIER`) o metadatos del IPA.

---

## 4) Variables opcionales (con defaults en workflow)

### `BROWSERSTACK_PROJECT_NAME`
- Tipo: **Variable**
- Obligatorio: No
- Si no lo pones:
  - Usa `appium-mobile-automation-framework`.
- Ejemplo:
  - `Automation Mobile Hub`

### `BROWSERSTACK_ANDROID_DEVICE`
- Tipo: **Variable**
- Obligatorio: No
- Si no lo pones:
  - Usa `Google Pixel 8` (default del workflow).
- Formato esperado:
  - Nombre exacto del dispositivo tal como lo muestra BrowserStack (sensible a mayusculas/espacios).
- Donde ver dispositivos disponibles:
  - BrowserStack App Automate > seleccionar dispositivo en Quickstart, o usa la API:
    `curl -u "USER:KEY" https://api-cloud.browserstack.com/app-automate/devices.json`

**Dispositivos Android recomendados (copiar exactamente este valor):**

| `BROWSERSTACK_ANDROID_DEVICE` | `BROWSERSTACK_ANDROID_PLATFORM_VERSION` | Notas |
|---|---|---|
| `Google Pixel 9 Pro` | `15.0` | Ultimo Pixel - recomendado |
| `Google Pixel 8` | `14.0` | Default del workflow |
| `Google Pixel 7` | `13.0` | Muy popular en QA |
| `Samsung Galaxy S24` | `14.0` | Samsung moderno |
| `Samsung Galaxy S23` | `13.0` | Samsung estable |
| `Samsung Galaxy S22` | `12.0` | Samsung clasico |
| `OnePlus 11R` | `13.0` | Alternativa media gama |
| `Xiaomi Redmi Note 12 Pro` | `13.0` | Alternativa economica |

> **Tip:** El nombre del dispositivo debe ser exactamente como aparece en la tabla (mayusculas, espacios). Un caracter diferente causa error de device not found.

### `BROWSERSTACK_ANDROID_PLATFORM_VERSION`
- Tipo: **Variable**
- Obligatorio: No
- Si no lo pones:
  - Usa `15.0`.
- Importante: debe coincidir con la version del dispositivo que elijas arriba.
- Valores validos: `15.0`, `14.0`, `13.0`, `12.0`, `11.0`

### `BROWSERSTACK_IOS_DEVICE`
- Tipo: **Variable**
- Obligatorio: No
- Si no lo pones:
  - Usa `iPhone 16 Pro`.
- Ejemplo:
  - `iPhone 15`

### `BROWSERSTACK_IOS_PLATFORM_VERSION`
- Tipo: **Variable**
- Obligatorio: No
- Si no lo pones:
  - Usa `18`.
- Ejemplo:
  - `17`

---

## 5) Pregunta frecuente: "Aqui va mi correo de BrowserStack?"

- En `BROWSERSTACK_USERNAME` va el **username** de BrowserStack.
- En algunas cuentas puede parecerse al correo, pero debes copiar el valor exacto que muestra BrowserStack como `Username`.
- El correo se usa para login en la web, pero no siempre coincide con el username tecnico.

## 6) Tabla rapida de referencia

| Nombre | Tipo GitHub | Obligatorio | Ejemplo |
|---|---|---|---|
| `BROWSERSTACK_USERNAME` | Secret | Si | `andresrdrgzps` |
| `BROWSERSTACK_ACCESS_KEY` | Secret | Si | `abcd1234...` |
| `BROWSERSTACK_ANDROID_APP_ID` | Secret | Si | `bs://abc123...` |
| `BROWSERSTACK_IOS_APP_ID` | Secret | Si | `bs://def456...` |
| `BROWSERSTACK_ANDROID_APP_PACKAGE` | Variable | Si | `com.empresa.app` |
| `BROWSERSTACK_IOS_BUNDLE_ID` | Variable | Si | `com.empresa.app.ios` |
| `BROWSERSTACK_PROJECT_NAME` | Variable | No | `Automation Mobile Hub` |
| `BROWSERSTACK_ANDROID_DEVICE` | Variable | No | `Google Pixel 8` |
| `BROWSERSTACK_ANDROID_PLATFORM_VERSION` | Variable | No | `15.0` |
| `BROWSERSTACK_IOS_DEVICE` | Variable | No | `iPhone 16 Pro` |
| `BROWSERSTACK_IOS_PLATFORM_VERSION` | Variable | No | `18` |

## 7) Checklist para primer run (manual)

1. Crea los 4 secrets obligatorios.
2. Crea las 2 variables obligatorias.
3. (Opcional) define device/version/project.
4. Ejecuta el workflow manual: `Actions` -> `Mobile Tests - BrowserStack` -> `Run workflow` -> `platform=both`.
5. Verifica que se creen dos jobs: Android e iOS en paralelo.
6. Revisa artifacts:
   - `mobile-test-results-browserstack-android`
   - `mobile-test-results-browserstack-ios`
   - `target/ci-logs/browserstack-context.txt`

## 8) Errores comunes y solucion rapida

- Error de credenciales:
  - Revisa `BROWSERSTACK_USERNAME` y `BROWSERSTACK_ACCESS_KEY` (sin espacios al inicio/final).
- Error "Missing BrowserStack app id":
  - Falta `BROWSERSTACK_ANDROID_APP_ID` o `BROWSERSTACK_IOS_APP_ID`.
- Error "Missing app identifier":
  - Falta `BROWSERSTACK_ANDROID_APP_PACKAGE` o `BROWSERSTACK_IOS_BUNDLE_ID`.
- La app abre pero no valida foreground:
  - Revisa que `app.package`/`bundle.id` coincidan exactamente con la app subida.

