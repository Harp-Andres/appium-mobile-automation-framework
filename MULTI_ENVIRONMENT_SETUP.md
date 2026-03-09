# 🌐 Configuración Multi-Entorno: Local + BrowserStack + AWS Device Farm

## Estructura del Workflow Actualizado

Tu workflow ahora es **matrix-based** y corre en 4 escenarios automáticamente:

```
┌─────────────────────────────────────────┐
│  STAGE 1: Build (Cloud - Ubuntu)        │
│  STAGE 2: Framework Tests (Cloud)       │
│  STAGE 3: Mobile Tests (MULTI-ENV)      │
│  ├─ Local (Windows Self-Hosted)         │
│  ├─ BrowserStack Android                │
│  ├─ BrowserStack iOS                    │
│  └─ AWS Device Farm Android             │
│  STAGE 4: Quality Gates                 │
└─────────────────────────────────────────┘
```

---

## 🏠 Escenario 1: LOCAL (Tu máquina Windows)

### Cuándo corre
- `schedule` (Viernes 10 AM UTC)
- `workflow_dispatch` (manual)
- Commit con `[mobile-tests]`

### Qué necesitas en tu PC
```powershell
✅ Java 17
✅ Maven 3.9
✅ Appium corriendo
✅ Android Emulator listo
✅ ADB en PATH
```

### Commando que corre
```bash
mvn test -Drun.mobile.tests=true -Denv=local -Devidence.video.enabled=true -B
```

---

## 🌐 Escenario 2: BROWSERSTACK (Cloud - iOS + Android)

### Cuándo corre
**SOLO si ejecutas manual (`workflow_dispatch`)**

### Configuración Necesaria

#### 2.1 Agregar Secrets en GitHub
```
Settings → Secrets and variables → Actions → New repository secret

BROWSERSTACK_USERNAME = tu_username
BROWSERSTACK_ACCESS_KEY = tu_access_key
```

#### 2.2 Crear `src/test/resources/config/browserstack.properties`
```properties
# BrowserStack Server
appium.server.url=https://BROWSERSTACK_USERNAME:BROWSERSTACK_ACCESS_KEY@hub.browserstack.com:4444

# Device Config
device.name=Google Pixel 8
platform.name=Android
platform.version=15
automation.name=UiAutomator2

# App Config (pre-uploaded en BrowserStack dashboard)
app=bs://your_app_id

# Capabilities
new.command.timeout=120
browserstack.local=false
browserstack.video=true
browserstack.screenshots=true
```

#### 2.3 Subir APK a BrowserStack
1. Ve a: https://app.browserstack.com/app-automate
2. Upload APK o IPA
3. Copia el `app_id` (ej: `bs://abc123...`)
4. Úsalo en `browserstack.properties`

#### 2.4 Comando que corre
```bash
mvn test -Drun.mobile.tests=true -Denv=browserstack -Dplatform=android -B
mvn test -Drun.mobile.tests=true -Denv=browserstack -Dplatform=ios -B
```

---

## ☁️ Escenario 3: AWS DEVICE FARM (Cloud - Android)

### Cuándo corre
**SOLO si ejecutas manual (`workflow_dispatch`)**

### Configuración Necesaria

#### 3.1 Agregar Secrets en GitHub
```
Settings → Secrets and variables → Actions

AWS_ACCESS_KEY_ID = tu_key
AWS_SECRET_ACCESS_KEY = tu_secret
```

#### 3.2 Crear Proyecto en AWS Device Farm
```bash
aws devicefarm create-project --name "appium-mobile-tests"
```

#### 3.3 Crear `src/test/resources/config/aws-device-farm.properties`
```properties
aws.region=us-east-1
aws.project.arn=arn:aws:devicefarm:us-east-1:ACCOUNT_ID:project:PROJECT_ID
aws.device.arn=arn:aws:devicefarm:us-east-1:ACCOUNT_ID:device:...
```

#### 3.4 Comando que corre
```bash
mvn test -Drun.mobile.tests=true -Denv=aws-device-farm -Dplatform=android -B
```

---

## 📊 Cómo Ejecutar cada Escenario

### Opción 1: Ejecución Automática (Viernes 10 AM)
```
Solo local en self-hosted
Sin acción manual
```

### Opción 2: Manual desde GitHub UI
```
GitHub.com → Actions → Workflow
→ Run workflow → Selecciona:
   - test-environment: browserstack (o aws-device-farm, o local)
   - test-platforms: android (o ios, o android,ios)
   - include-mobile-tests: true
```

### Opción 3: Trigger con Commit
```bash
git commit -m "test [mobile-tests]"
git push origin main
# Ejecuta SOLO local en self-hosted
```

---

## 🔧 Ajustes en tu Proyecto

### En `pom.xml`
Agrega profiles para cada entorno:
```xml
<profiles>
  <profile>
    <id>local</id>
    <properties>
      <appium.server.url>http://127.0.0.1:4723</appium.server.url>
    </properties>
  </profile>
  <profile>
    <id>browserstack</id>
    <properties>
      <appium.server.url>${browserstack.url}</appium.server.url>
    </properties>
  </profile>
  <profile>
    <id>aws-device-farm</id>
    <properties>
      <appium.server.url>${aws.device.farm.url}</appium.server.url>
    </properties>
  </profile>
</profiles>
```

### En tus Steps de Cucumber
Usa propiedades para cambiar selectores y endpoints dinámicamente.

---

## 📈 Matriz de Compatibilidad

| Entorno | Platform | OS | Self-Hosted | Video | Costo |
|---------|----------|----|----|-------|-------|
| **Local** | Android | Windows | ✅ | ✅ | $0 |
| **BrowserStack** | Android | Ubuntu | ❌ | ✅ | $99+/mes |
| **BrowserStack** | iOS | Ubuntu | ❌ | ✅ | $99+/mes |
| **AWS Device Farm** | Android | Ubuntu | ❌ | ❌ | $0.01/min |

---

## 🎯 Próximos Pasos

1. **Para LOCAL:** Solo ejecuta en viernes o con `[mobile-tests]`
2. **Para BROWSERSTACK:** Agregar secrets + subir app
3. **Para AWS:** Agregar secrets + crear proyecto AWS

---

## 💡 Notas Importantes

- ✅ El workflow automáticamente elige qué matrix ejecutar basado en entorno
- ✅ BrowserStack y AWS solo corren si lo disparas manualmente
- ✅ Local siempre corre en tu PC (self-hosted)
- ✅ Todos uploadan resultados a Allure

---

**¿Listo?** Ahora tu pipeline es profesional y multi-entorno. 🚀

