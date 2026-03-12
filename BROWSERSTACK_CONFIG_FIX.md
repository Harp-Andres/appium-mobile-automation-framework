# 🔴 Error "Could not start a new session" - BrowserStack Configuration

## El Problema
El workflow falla con:
```
Error: Could not start a new session. Possible causes are invalid address of the remote server or browser start-up failure.
```

## Causa Raíz
Falta la configuración de:
1. **App ID remota** en BrowserStack (formato `bs://xxxxx`)
2. **Identificador de la app** (package name o bundle id)
3. **URL del servidor** con credenciales de BrowserStack

---

## Pasos para Configurar

### 1️⃣ Obtener el App ID de BrowserStack

Luego de que subiste el APK a BrowserStack:
- Ve a: https://app-automate.browserstack.com/
- En el menú izquierdo: **Apps**
- Busca tu app (Appium Practice)
- **Copia el App ID** que aparece como `bs://xxxxxxxxxxxxx`

### 2️⃣ Obtener Package Name de la APK

**Opción A: Desde el archivo APK local**
```bash
aapt dump badging "Appium Practice_2.0_APKPure.apk" | grep package
```
Resultado esperado: `package: name='io.appium.android.apis'` (o similar)

**Opción B: Desde BrowserStack Dashboard**
- En la sección de Apps, tu app debe mostrar el package name

### 3️⃣ Configurar Secretos en GitHub

Necesitas crear/actualizar estos **secrets** en tu repositorio (Settings → Secrets and variables → Actions):

| Nombre | Valor | Ejemplo |
|--------|-------|---------|
| `BROWSERSTACK_USERNAME` | Tu email de BrowserStack | `tu-email@example.com` |
| `BROWSERSTACK_ACCESS_KEY` | Tu Access Key de BrowserStack | `xxxxxxxxxxxxxx` (desde Account Settings) |
| `BROWSERSTACK_ANDROID_APP_ID` | App ID remota del APK | `bs://xxxxx` |
| `BROWSERSTACK_IOS_APP_ID` | App ID remota de iOS (si tienes) | `bs://yyyyy` |

### 4️⃣ Configurar Variables en GitHub

Necesitas crear/actualizar estas **variables** (Settings → Secrets and variables → Variables):

| Nombre | Valor | Ejemplo |
|--------|-------|---------|
| `BROWSERSTACK_ANDROID_APP_PACKAGE` | Package name del APK | `io.appium.android.apis` |
| `BROWSERSTACK_IOS_BUNDLE_ID` | Bundle ID del app iOS | `com.example.app` |
| `BROWSERSTACK_PROJECT_NAME` | Nombre del proyecto | `appium-mobile-automation-framework` |
| `BROWSERSTACK_ANDROID_DEVICE` | Device a usar | `Google Pixel 8` |
| `BROWSERSTACK_ANDROID_PLATFORM_VERSION` | Versión de Android | `15.0` |
| `BROWSERSTACK_IOS_DEVICE` | Device a usar | `iPhone 16 Pro` |
| `BROWSERSTACK_IOS_PLATFORM_VERSION` | Versión de iOS | `18` |

---

## Pasos Exactos en BrowserStack

### A. Obtener Credenciales
1. Ve a: https://app-automate.browserstack.com/
2. Click en tu **avatar** (arriba derecha)
3. Selecciona **Account Settings**
4. Busca **Access Key** y cópialo

### B. Subir APK
1. En el dashboard, ve a **Apps**
2. Click en **Upload App**
3. Selecciona tu APK local
4. Espera a que se suba completamente
5. **COPIA el App ID** que aparece (formato: `bs://xxxxx`)

### C. Obtener Package Name
```bash
# Si tienes el APK en local:
aapt dump badging "Appium Practice_2.0_APKPure.apk" | grep package
# Output: package: name='io.appium.android.apis'
```

---

## Configuración del Workflow

El workflow ya está configurado para pasar estas variables. Verifica en `.github/workflows/ci-cd-mobile-tests-browserstack.yml` que incluya:

```yaml
Validate BrowserStack config and prepare runtime context
  - env:
      BROWSERSTACK_USERNAME: ${{ secrets.BROWSERSTACK_USERNAME }}
      BROWSERSTACK_ACCESS_KEY: ${{ secrets.BROWSERSTACK_ACCESS_KEY }}
      BROWSERSTACK_ANDROID_APP_ID: ${{ secrets.BROWSERSTACK_ANDROID_APP_ID }}
      BROWSERSTACK_ANDROID_APP_PACKAGE: ${{ vars.BROWSERSTACK_ANDROID_APP_PACKAGE }}
      ...
```

---

## Checklist de Verificación

- [ ] Secreto `BROWSERSTACK_USERNAME` configurado
- [ ] Secreto `BROWSERSTACK_ACCESS_KEY` configurado
- [ ] Secreto `BROWSERSTACK_ANDROID_APP_ID` configurado (formato: `bs://xxxxx`)
- [ ] Variable `BROWSERSTACK_ANDROID_APP_PACKAGE` configurada
- [ ] Variable `BROWSERSTACK_ANDROID_DEVICE` configurada
- [ ] Variable `BROWSERSTACK_ANDROID_PLATFORM_VERSION` configurada
- [ ] APK subido a BrowserStack correctamente
- [ ] Package name coincide con la APK

---

## Después de Configurar

1. **Ejecuta el workflow manualmente** desde Actions
2. Selecciona **platform: android**
3. Verifica en los logs del step **"Validate BrowserStack config"** que:
   ```
   platform=android
   device_name=Google Pixel 8
   platform_version=15.0
   app_id=bs://xxxxx
   ```
4. Si todo está OK, el siguiente step **"Run Mobile Tests - BrowserStack"** ejecutará los tests

---

## Troubleshooting

### ❌ "Invalid address of the remote server"
→ Revisa `BROWSERSTACK_ANDROID_APP_ID`: debe ser `bs://xxxxx` (sin comillas)

### ❌ "app=bs://[object Object]"
→ Significa que el secret no se está expandiendo correctamente. Verifica que esté en **Secrets**, no en **Variables**

### ❌ "Could not connect to hub.browserstack.com"
→ Revisa credenciales: `BROWSERSTACK_USERNAME` y `BROWSERSTACK_ACCESS_KEY`

### ❌ "app.package is blank"
→ Verifica que `BROWSERSTACK_ANDROID_APP_PACKAGE` esté configurada en **Variables**

