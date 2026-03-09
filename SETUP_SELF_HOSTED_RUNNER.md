# 🚀 Configurar Self-Hosted Runner en tu Máquina (Windows)

## ¿Qué es un Self-Hosted Runner?

Es un **agente GitHub que corre en tu máquina local** y ejecuta los tests móviles cuando GitHub lo solicita.

```
Flujo:
1. Haces push a GitHub
2. GitHub Actions se activa
3. GitHub envía instrucción a tu máquina local
4. Tu máquina corre los tests (con Appium + emulador)
5. Resultados se suben a GitHub
6. Ves reportes en GitHub Actions
```

---

## 📋 Requisitos Previos

Asegúrate de tener instalado en tu máquina:

```powershell
# Verificar Java 17
java -version

# Verificar Maven
mvn -version

# Verificar Appium
appium --version

# Verificar ADB (Android)
adb devices

# Verificar Node.js
node --version
npm --version
```

Si falta algo, instálalo con:
```powershell
# Java 17
winget install Oracle.JDK.17

# Maven
winget install Apache.Maven

# Node.js
winget install OpenJS.NodeJS

# Appium
npm install -g appium@2.0
appium driver install uiautomator2
```

---

## 🔧 Paso 1: Obtener Token de GitHub

### 1.1 Ve a GitHub
```
https://github.com/settings/tokens
```

### 1.2 Crea un token personal
- Click en "Generate new token" → "Generate new token (classic)"
- **Nombre:** `self-hosted-runner-token`
- **Scopes (marcar):**
  - ☑️ repo (acceso completo a repositorio privado)
  - ☑️ workflow (acceso a workflows)
  - ☑️ admin:repo_hook (para webhooks)

- **Validez:** 90 días (o personalizado)
- Click en "Generate token"
- **COPIA el token** (no lo olvides, no se muestra de nuevo)

```
ghp_xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

---

## 🏠 Paso 2: Descargar GitHub Actions Runner

### 2.1 Crear carpeta
```powershell
# En tu C: (o donde prefieras)
mkdir C:\github-actions-runner
cd C:\github-actions-runner
```

### 2.2 Descargar el runner
Opción A (Automático - Recomendado):
```powershell
# PowerShell como ADMIN
cd C:\github-actions-runner

# Descargar última versión
Invoke-WebRequest -Uri "https://github.com/actions/runner/releases/download/v2.316.1/actions-runner-win-x64-2.316.1.zip" -OutFile "runner.zip"

# Extraer
Expand-Archive -Path runner.zip -DestinationPath .

# Limpiar
Remove-Item runner.zip
```

Opción B (Manual):
1. Ve a: https://github.com/actions/runner/releases
2. Descarga: `actions-runner-win-x64-*.zip`
3. Extrae en `C:\github-actions-runner`

---

## 🔐 Paso 3: Configurar el Runner

### 3.1 Ejecutar configuración
```powershell
cd C:\github-actions-runner

# Ejecutar script de configuración (como ADMIN)
.\config.cmd --url https://github.com/Harp-Andres/appium-mobile-automation-framework --token ghp_xxxx...
```

**Te pedirá:**
```
√ Connected to GitHub

Runner Registration

Enter the name of the runner group to add this runner to: [press enter for 'Default']
  → Presiona ENTER (Default)

Enter the name of the runner: [press enter for 'WIN-XXXX']
  → Escribe: android-appium-runner

Enter any labels (ex. ubuntu,x64,gpu): [press enter to skip]
  → Escribe: self-hosted,android,appium,windows

Enter the work directory _work:
  → Presiona ENTER (Default)

Do you want to run the runner as a service? (Y/n)
  → Escribe: Y (SÍ, para que inicie automáticamente)

Enter the user account to use for the service:
  → Escribe: tu_usuario (ej: ADMIN)

Enter the user password:
  → Tu contraseña de Windows
```

### 3.2 Verificar configuración
```powershell
# Ver que se configuró correctamente
ls

# Deberías ver:
# - .runner
# - .credentials
# - bin
# - config.cmd
# - run.cmd
```

---

## 🚀 Paso 4: Iniciar el Runner

### 4.1 Como Servicio (Recomendado - Inicia automáticamente)
```powershell
# El runner ya está registrado como servicio
# Verificar que está corriendo:

Get-Service -Name "GitHub Actions Runner"

# Debería mostrar: Running

# Si no está corriendo:
Start-Service -Name "GitHub Actions Runner"
```

### 4.2 Manualmente (Para testing)
```powershell
cd C:\github-actions-runner
.\run.cmd
```

Verás algo como:
```
√ Connected to GitHub

Current runner version: 2.316.1
Listening for Jobs
```

---

## ✅ Paso 5: Verificar que Funciona

### 5.1 En GitHub
1. Ve a: https://github.com/Harp-Andres/appium-mobile-automation-framework
2. Settings → Actions → Runners
3. Deberías ver tu runner: `android-appium-runner` con estado **Idle** 🟢

### 5.2 Hacer un push para probar
```powershell
cd C:\...\appium-mobile-automation-framework

git add .
git commit -m "test: Trigger self-hosted runner"
git push origin main
```

### 5.3 Ver ejecución
1. Ve a: https://github.com/Harp-Andres/appium-mobile-automation-framework/actions
2. Ves el workflow ejecutándose
3. Verifica logs para confirmar que tu máquina lo ejecutó

---

## 🔄 Paso 6: Cómo Funciona el Flujo

```
TÚ              GitHub              TU MÁQUINA
 │                │                    │
 ├─ git push ────→ │                    │
 │                │                    │
 │                ├─ Trigger workflow ─→ │
 │                │                    │
 │                │  ┌─ Corre tests ──→ │
 │                │  │ (Appium, Emulador)│
 │                │  │ Genera reportes   │
 │                │  │                   │
 │                │ ←─ Envía resultados ┤
 │                │                    │
 │ ←─ Resultados ─┤                    │
 │   en GitHub    │                    │
```

---

## 🎯 Lo que Hace tu Runner

Cuando se ejecuta, hace esto automáticamente:

```yaml
1. ✅ Descarga tu código
   git clone https://github.com/Harp-Andres/appium-mobile-automation-framework

2. ✅ Setup Java 17
   (ya instalado en tu máquina)

3. ✅ Build del proyecto
   mvn clean install -DskipTests

4. ✅ Inicia Appium
   appium --log-level warn &

5. ✅ Ejecuta tests móviles
   mvn test -Drun.mobile.tests=true -Devidence.video.enabled=true

6. ✅ Detiene Appium
   pkill -f appium

7. ✅ Sube resultados a GitHub
   Allure reports, screenshots, videos
```

---

## 🐛 Troubleshooting

### El runner no aparece en GitHub
```powershell
# Verificar servicio
Get-Service -Name "GitHub Actions Runner"

# Si está parado:
Start-Service -Name "GitHub Actions Runner"

# Ver logs:
Get-EventLog -LogName System -Source "GitHub Actions Runner" -Newest 10
```

### El token expiró
```powershell
# Volver a configurar
cd C:\github-actions-runner
.\config.cmd --url https://github.com/Harp-Andres/appium-mobile-automation-framework --token TOKEN_NUEVO
```

### Tests no corren en el runner
```
Razones comunes:
1. Appium no está instalado en la máquina
   → Solución: appium --version
2. Emulador no está corriendo
   → Solución: emulator -avd Pixel_8_API_35 &
3. Puerto 4723 está ocupado
   → Solución: netstat -ano | findstr 4723
4. Credenciales de GitHub vencidas
   → Solución: Re-configurar runner
```

---

## 📊 Monitoreo: Ver Logs del Runner

### En GitHub (Online)
```
https://github.com/Harp-Andres/appium-mobile-automation-framework/actions
→ Busca el workflow
→ Click en "self-hosted runner"
→ Ve logs en tiempo real
```

### En tu Máquina (Offline)
```powershell
# Ver logs recientes
Get-Content C:\github-actions-runner\_diag\*.log -Tail 50

# Ver si el servicio está corriendo
Get-Service -Name "GitHub Actions Runner" | Format-List
```

---

## 🔒 Seguridad

### Buenas prácticas:
1. ✅ Token: Usar expiracion corta (30-90 días)
2. ✅ Firewall: Abre puerto 443 (HTTPS)
3. ✅ Credenciales: Guarda token en lugar seguro
4. ✅ Logs: Revisa logs regularmente para actividad sospechosa
5. ✅ Network: El runner usa HTTPS (no HTTP)

---

## ✨ Ahora Puedes Hacer Esto

```bash
# Desde tu máquina local
cd E:\...\appium-mobile-automation-framework

# Desarrollo normal
mvn test -Drun.mobile.tests=true

# Push a GitHub
git push origin main

# GitHub automáticamente:
# 1. Ejecuta framework tests en cloud (Ubuntu)
# 2. Envía mobile tests a TU máquina
# 3. Tu máquina corre con Appium
# 4. Resultados vuelven a GitHub
# 5. Ves todo en: github.com/.../actions
```

---

## 🎊 Checklist Final

- [ ] Instalé GitHub Runner en `C:\github-actions-runner`
- [ ] Configuré runner con token válido
- [ ] Runner aparece en GitHub Settings → Runners → Idle 🟢
- [ ] Hice push y vi que se ejecutó en Actions
- [ ] El servicio está configurado para iniciar automáticamente
- [ ] Appium y emulador funcionan en mi máquina

---

## 🆘 ¿Necesitas Ayuda?

Si algo falla:
1. Lee los logs en GitHub Actions
2. Verifica que Appium está corriendo: `appium --version`
3. Verifica que el emulador existe: `emulator -list-avds`
4. Reinicia el runner: Restart-Service -Name "GitHub Actions Runner"

