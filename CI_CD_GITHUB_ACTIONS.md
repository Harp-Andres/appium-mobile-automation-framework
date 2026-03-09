# 🚀 CI/CD Pipeline para Appium Mobile Test Automation

## 📋 Descripción General

Este proyecto cuenta con un pipeline **GitHub Actions** completamente configurado para:
- ✅ **Compilación automática** en cada push/PR
- ✅ **Tests de framework** (sin dispositivo móvil) en cloud
- ✅ **Tests móviles** en self-hosted runner (opcional)
- ✅ **Reportes Allure** y Cucumber automáticos
- ✅ **Publicación en GitHub Pages**

---

## 🏗️ Componentes del Pipeline

### 1. **Build & Test (Ejecutado en Ubuntu - Cloud)**
```bash
# Se ejecuta automáticamente en cada:
# - Push a main o develop
# - Pull Request
# - Horario programado (6 AM UTC diario)

mvn clean install -DskipTests
mvn test -Drun.mobile.tests=false
```

**Duración:** ~5-10 minutos  
**No requiere:** Appium, dispositivo, o emulador

### 2. **Mobile Tests (Ejecutado en Self-Hosted Runner - Local)**
```bash
# Solo si:
# - Commit contiene [mobile-tests]
# - Es triggered manualmente
# - Es scheduled

appium &
mvn test -Drun.mobile.tests=true
```

**Duración:** ~30-60 minutos  
**Requiere:** Appium Server + Android Emulator/Device

### 3. **Reportes**
- **Allure Report** → Publicado en GitHub Pages
- **Cucumber Reports** → Descargables como artifacts
- **Screenshots** → Almacenados por 30 días

---

## 🔧 Cómo Usar

### Opción 1: Ejecución Automática (Recomendado)
Simplemente haz push a `main` o `develop`:

```bash
git add .
git commit -m "Feature: Add new counter tests"
git push origin main
```

El pipeline se ejecutará automáticamente. Ve a:
```
https://github.com/Harp-Andres/appium-mobile-automation-framework/actions
```

### Opción 2: Incluir Tests Móviles
Si tu commit contiene `[mobile-tests]`, se ejecutarán también en self-hosted:

```bash
git commit -m "Feature: Add mobile tests [mobile-tests]"
git push origin main
```

### Opción 3: Ejecución Manual
En GitHub → Actions → Selecciona el workflow → Click en "Run workflow"

### Opción 4: Ejecución Diaria (Ya Configurada)
Automáticamente a las **6:00 AM UTC** cada día.

---

## 🏠 Configurar Self-Hosted Runner (Para Tests Móviles)

### Paso 1: Registrar Runner en GitHub
1. Ve a: `Settings → Actions → Runners → New self-hosted runner`
2. Sigue las instrucciones para tu OS (Windows/Mac/Linux)

### Paso 2: Instalar Dependencias en la Máquina
```bash
# Windows PowerShell (como Admin)
winget install Oracle.JDK.17
winget install Apache.Maven
npm install -g appium

# Iniciar Appium
appium
```

### Paso 3: Verificar en GitHub
El runner debe aparecer como "Idle" en Settings → Actions → Runners

---

## 📊 Entender los Resultados

### En GitHub Actions (Actions Tab)
- 🟢 **Verde** = Todos los tests pasaron
- 🔴 **Rojo** = Al menos un test falló
- ⚠️ **Amarillo** = Warnings (tests skipped)

### Descargar Reportes
1. Abre el workflow completado
2. Scroll hasta "Artifacts"
3. Descarga:
   - `allure-results` → Para revisar en local
   - `cucumber-reports` → Abrir HTML directamente
   - `evidence-screenshots` → Ver capturas

### Ver Reportes Online
Si GitHub Pages está habilitado:
```
https://harp-andres.github.io/appium-mobile-automation-framework/
```

---

## ⚙️ Customizar el Pipeline

### Cambiar Horario de Ejecución Diaria
En `.github/workflows/ci-cd-mobile-tests.yml`:

```yaml
schedule:
  - cron: '0 6 * * *'  # Cambiar a tu horario
  # Formato: minuto hora día-mes día-semana
  # Ejemplos:
  # 0 8 * * 1       → Lunes a las 8 AM
  # 30 14 * * *     → Todos los días a las 14:30
  # 0 0 * * 0       → Domingo a las 0:00
```

### Agregar más properties Maven
En la sección `Run Framework Health Tests`:

```yaml
- name: Run with Custom Config
  run: mvn test -Drun.mobile.tests=false -Denv=qa -Devidence.video.enabled=true -B
```

### Notificar a Slack/Email
Agregar paso al final del job:

```yaml
- name: Notify Slack
  if: failure()
  uses: 8398a7/action-slack@v3
  with:
    status: ${{ job.status }}
    text: 'Tests fallaron en ${{ github.repository }}'
    webhook_url: ${{ secrets.SLACK_WEBHOOK }}
```

---

## 🔐 Secrets Necesarios en GitHub

Si usas integraciones externas, agrega en:
`Settings → Secrets and variables → Actions → New repository secret`

Ejemplos:
- `SLACK_WEBHOOK` → Para notificaciones Slack
- `JIRA_TOKEN` → Para reportar en Jira
- `EMAIL_PASSWORD` → Para notificaciones por email

---

## 📈 Monitorear CI/CD

### Dashboard Recomendado
- GitHub Actions built-in: `Actions` tab en tu repo
- Alternativa: Usar `GitHub CLI`

```bash
gh run list --repo Harp-Andres/appium-mobile-automation-framework
gh run view <RUN_ID> --log
```

---

## 🚨 Troubleshooting

### Los tests no se ejecutan
```
❌ Solución:
1. Verifica que .github/workflows/ci-cd-mobile-tests.yml existe
2. Ve a Actions tab → Habilita Workflows
3. Haz push a main o develop nuevamente
```

### Falla en self-hosted runner
```
❌ Solución:
1. Verifica que el runner está "Idle" en Settings
2. Verifica Java, Maven, Appium están instalados
3. Reinicia el runner: ~/actions-runner/run.sh
```

### No se generan reportes
```
❌ Solución:
1. Verifica que Allure está en pom.xml
2. Ejecuta localmente: mvn allure:report
3. Revisa logs en GitHub Actions
```

---

## 📚 Próximos Pasos

1. **Push este archivo** a GitHub:
   ```bash
   git add .
   git commit -m "Add CI/CD GitHub Actions pipeline"
   git push origin main
   ```

2. **Verifica que se ejecutó** en:
   ```
   https://github.com/Harp-Andres/appium-mobile-automation-framework/actions
   ```

3. **Customiza** según tus necesidades (horarios, notificaciones, etc.)

4. **(Opcional) Configura self-hosted runner** si quieres tests móviles automáticos

---

## 💡 Tips Profesionales

✅ **Usa [mobile-tests] tag** en commits que requieran validación móvil  
✅ **Revisa logs regularmente** para detectar flakiness  
✅ **Mantén updated** pom.xml con versiones estables  
✅ **Usa GitHub Pages** para compartir reportes con el team  
✅ **Integra con Slack** para notificaciones automáticas

---

**¿Preguntas?** Revisa `.github/workflows/ci-cd-mobile-tests.yml` directamente.

