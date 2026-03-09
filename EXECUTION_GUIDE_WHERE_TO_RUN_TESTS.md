# 📱 Guía de Ejecución: Dónde Correr tus Tests Móviles

## 🎯 Decisión Rápida: ¿Dónde correr mis tests?

```
┌─────────────────────────────────────────────────────────────┐
│  ¿Qué tipo de tests necesitas?                              │
└─────────────────────────────────────────────────────────────┘
          │
          ├─→ "Desarrollo diario / Verificación rápida"
          │   └─→ OPCIÓN 1: Máquina Local (On-Premise)
          │       ⏱️ 5-10 min | 💰 Gratis | 🎯 Confiable
          │
          ├─→ "Validación en múltiples dispositivos"
          │   └─→ OPCIÓN 2: BrowserStack (Recommended 2026)
          │       ⏱️ 15-30 min | 💰 $99-500/mes | 🌐 100+ devices
          │
          ├─→ "Full enterprise con auto-scaling"
          │   └─→ OPCIÓN 3: AWS Device Farm
          │       ⏱️ 20-40 min | 💰 Pay-per-minute | 📊 Analytics built-in
          │
          └─→ "Hybrid (lo mejor de todo)"
              └─→ OPCIÓN 4: Local + Cloud (Recommended)
                  Local para smoke tests + Cloud para suite completa
```

---

## 🏠 OPCIÓN 1: Máquina Local (On-Premise)

### Mejor Para:
- ✅ Desarrollo activo
- ✅ Tests rápidos durante PR
- ✅ Sin costo adicional

### Setup Inicial (Windows)
```powershell
# 1. Instalar JDK 17
winget install Oracle.JDK.17

# 2. Instalar Maven 3.9
winget install Apache.Maven

# 3. Instalar Node.js + Appium
winget install OpenJS.NodeJS
npm install -g appium@2.0

# 4. Instalar UIAutomator2 driver
appium driver install uiautomator2

# 5. Instalar Android SDK (via Android Studio)
# O usar: winget install Google.AndroidSDK

# 6. Crear emulador
emulator -avd Pixel_8_API_35 &

# 7. Verificar adb
adb devices
```

### Ejecutar Tests Localmente
```powershell
cd E:\...\appium-mobile-automation-framework

# Abrir otra terminal y lanzar Appium
appium --log-level warn &

# En la terminal principal:
mvn test -Drun.mobile.tests=true -Devidence.video.enabled=true

# Ver reportes
mvn allure:serve
```

### Ventajas:
- 🚀 Instant feedback loop
- 💻 Control total
- 🔧 Debug interactivo

### Desventajas:
- ❌ Solo un dispositivo/emulador
- ❌ No escalable
- ❌ Requiere mantener máquina actualizada

---

## ☁️ OPCIÓN 2: BrowserStack (Industry Standard 2026)

### Mejor Para:
- ✅ Testing en múltiples devices reales
- ✅ iOS + Android simultáneamente
- ✅ Equipo distribuido
- ✅ Reportes integrados

### Setup:

#### 2.1 Crear Cuenta
1. https://www.browserstack.com
2. Sign up → Mobile App Testing
3. Obtener `USERNAME` y `ACCESS_KEY`

#### 2.2 Configurar Credenciales en GitHub
```
Settings → Secrets and variables → Actions
+ New repository secret

BROWSERSTACK_USERNAME = tu_username
BROWSERSTACK_ACCESS_KEY = tu_key
```

#### 2.3 Crear archivo de configuración
```bash
# src/test/resources/config/browserstack.properties

# BrowserStack Server
appium.server.url=https://${BROWSERSTACK_USERNAME}:${BROWSERSTACK_ACCESS_KEY}@hub.browserstack.com:4444

# Device Configuration
device.name=Google Pixel 8
platform.name=Android
platform.version=15
automation.name=UiAutomator2

# Android App (pre-uploaded)
app=bs://your_app_id_here

# Capabilities
new.command.timeout=120
browserstack.local=false
browserstack.localIdentifier=your_local_id
browserstack.video=true
browserstack.screenshots=true
```

#### 2.4 Ejecutar en GitHub Actions
El workflow automáticamente ejecutará en BrowserStack si detecta credenciales:

```yaml
# En .github/workflows/ci-cd-mobile-tests.yml
# Ya está configurado para soportar browserstack
```

#### 2.5 Ejecutar Localmente contra BrowserStack
```powershell
# Subir app primero
$APP_ID = BrowserStack App Uploader

# Ejecutar tests
mvn test -Denv=browserstack -Dapp=${APP_ID}

# Ver resultados
https://app.browserstack.com/dashboard/mobile/builds
```

### Devices Populares en BrowserStack (2026):
- **iPhone 16 Pro** | iOS 18
- **Samsung Galaxy S25** | Android 15
- **Google Pixel 9** | Android 15
- **OnePlus 13** | Android 15
- **iPad Pro 12.9** | iPadOS 18

### Pricing 2026:
- Starter: $99/mes (50 minutes)
- Professional: $299/mes (500 minutes)
- Premium: $799/mes (2000 minutes + API)

---

## 🏭 OPCIÓN 3: AWS Device Farm

### Mejor Para:
- ✅ Enterprise con presupuesto
- ✅ Análisis detallado de rendimiento
- ✅ Pruebas de carga
- ✅ Integración con AWS ecosystem

### Setup:

#### 3.1 Crear Proyecto en AWS Device Farm
```bash
aws devicefarm create-project --name "appium-mobile-tests"
```

#### 3.2 Preparar App
```bash
# Subir APK
aws devicefarm create-upload \
  --project-arn "arn:aws:devicefarm:..." \
  --name "my-app.apk" \
  --type ANDROID_APP
```

#### 3.3 Ejecutar Suite
```bash
aws devicefarm schedule-run \
  --project-arn "arn:..." \
  --app-arn "arn:..." \
  --test-type APPIUM_JAVA_JUNIT \
  --test "path/to/tests.zip" \
  --devices "deviceArn1" "deviceArn2"
```

### Pricing 2026:
- Por minuto: $0.01/dispositivo
- Ejemplo: 100 min × 5 devices = $5
- Muy cost-effective para uso esporádico

---

## 🔀 OPCIÓN 4: Hybrid (Recomendado 2026)

### Arquitectura:
```
┌────────────────────────────────┐
│   Desarrollo Activo (Local)    │
│  - Smoke tests en emulador     │
│  - 5-10 minutos / 0€           │
└────────┬───────────────────────┘
         │
    Cada commit
         │
         ▼
┌────────────────────────────────┐
│   GitHub Actions (Cloud)       │
│  - Build & Framework Tests     │
│  - 10-15 minutos / Free        │
└────────┬───────────────────────┘
         │
    Si es Release
    o PR crítica
         │
         ▼
┌────────────────────────────────┐
│   BrowserStack (Multi-device)  │
│  - iOS + Android reales        │
│  - 30-45 minutos / ~$1 por run │
└────────────────────────────────┘
```

### Configuración:

#### 4.1 Local Development
```bash
# Tu máquina local
appium &
mvn test -Drun.mobile.tests=true
```

#### 4.2 GitHub Actions (automático)
```bash
# Cada push a main/develop
# Runner ejecuta framework tests (sin mobile)
# Self-hosted ejecuta local si está disponible
```

#### 4.3 BrowserStack (manual o scheduled)
```bash
# Ejecutar full suite en devices reales
# Agregar tag [full-test] en commit
git commit -m "Release prep [full-test]"
git push origin main
```

---

## 📊 Tabla Comparativa: ¿Cuál es la Mejor?

| Aspecto | Local | BrowserStack | AWS Device Farm | Hybrid |
|---------|-------|--------------|-----------------|--------|
| **Costo** | 🟢 Gratis | 🟡 $99-799/mes | 🟢 Pay-per-use | 🟡 Hybrid |
| **Devices** | 🔴 1 | 🟢 100+ | 🟢 40+ | 🟢 Múltiples |
| **Setup** | 🟡 Moderado | 🟢 Simple | 🔴 Complejo | 🟡 Moderado |
| **Speed** | 🟢 Rápido | 🟡 Medio | 🟡 Medio | 🟢 Mejor |
| **CI/CD** | 🔴 No | 🟢 Sí | 🟢 Sí | 🟢 Sí |
| **Escalabilidad** | 🔴 No | 🟢 Sí | 🟢 Sí | 🟢 Sí |
| **Reporting** | 🔴 Manual | 🟢 Automático | 🟢 Automático | 🟢 Automático |
| **Recomendado para 2026** | Dev | ⭐⭐⭐ | ⭐⭐ | ⭐⭐⭐⭐ |

---

## 🚀 Paso a Paso: Implementar Hybrid

### Paso 1: Configurar Self-Hosted Runner (Local)
```powershell
# En tu máquina local
cd C:\github-actions-runner
.\config.cmd --url https://github.com/Harp-Andres/appium-mobile-automation-framework --token <TOKEN>
.\run.cmd
```

### Paso 2: Configurar BrowserStack
```bash
# Agregar secrets en GitHub
BROWSERSTACK_USERNAME
BROWSERSTACK_ACCESS_KEY
BROWSERSTACK_APP_ID
```

### Paso 3: Modificar Workflow (Ya está hecho)
El workflow actual soporta:
- ✅ Cloud tests (automático)
- ✅ Self-hosted tests (si el runner está disponible)
- ✅ BrowserStack (si credenciales existen)

### Paso 4: Usar en tu día a día
```bash
# Desarrollo local
mvn test -Drun.mobile.tests=true

# Enviar a GitHub (corre en cloud)
git push origin main

# Para full test en devices reales
git commit -m "feat: New payment flow [full-test]"
git push origin main

# Revisar resultados
https://github.com/Harp-Andres/appium-mobile-automation-framework/actions
```

---

## 🎯 Recomendación Final

```
┌─────────────────────────────────────────────────┐
│ PARA TU PROYECTO (2026 Best Practice)          │
├─────────────────────────────────────────────────┤
│ 1. Local Development                            │
│    └─ Emulador Pixel 8 API 35 (Android 15)    │
│    └─ iPhone Simulator (iOS 18)                │
│                                                 │
│ 2. GitHub Actions (Automático)                  │
│    └─ Framework tests en Ubuntu (cloud)        │
│    └─ Self-hosted si está disponible           │
│                                                 │
│ 3. BrowserStack (Para QA/Staging)               │
│    └─ Google Pixel 9, iPhone 16                │
│    └─ Corre antes de release                   │
│                                                 │
│ Presupuesto Mensual Estimado:                  │
│ - Development: $0 (local)                       │
│ - BrowserStack Lite: $99/mes                   │
│ - TOTAL: ~$100/mes                             │
└─────────────────────────────────────────────────┘
```

---

**¿Listo para implementar?** Sigue esta guía paso a paso y tu CI/CD estará a nivel enterprise.

