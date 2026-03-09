# 📊 Cómo Ver los Reportes de Allure

> **⚠️ Importante:** Todos los comandos usan la ruta completa de Maven (`C:\apache-maven-3.9.9\bin\mvn.cmd`) porque Maven no está en el PATH del sistema. Si sale el error `"mvn no se reconoce como comando"`, es porque falta la ruta completa.

---

## ✅ Opción 1: Servidor Allure con Maven (Recomendado)
**Cuándo usar:** Después de ejecutar tests. Abre automáticamente en el navegador.

```powershell
cd "E:\UnidadPrincipal\Documentos\ProyectosAutomatizacionModerna\appium-cucumber-base";
C:\apache-maven-3.9.9\bin\mvn.cmd allure:serve
```

**Ventajas:**
- ✅ Genera y abre automáticamente
- ✅ Reportes interactivos completos
- ✅ Gráficos y tendencias
- ✅ Screenshots adjuntos

**Puerto:** `http://localhost:random` (se abre automáticamente)

---

## 🔧 Opción 2: Generar HTML estático
**Cuándo usar:** Cuando quieres compartir el reporte o guardarlo.

```powershell
# Cambiar a la carpeta del proyecto
cd "E:\UnidadPrincipal\Documentos\ProyectosAutomatizacionModerna\appium-cucumber-base";

# Generar reporte
C:\apache-maven-3.9.9\bin\mvn.cmd allure:report

# Abrir manualmente en navegador
Start-Process "E:\UnidadPrincipal\Documentos\ProyectosAutomatizacionModerna\appium-cucumber-base\target\allure-report\index.html"
```

**Ventajas:**
- ✅ No necesita servidor corriendo
- ✅ Portable (puedes copiar la carpeta)
- ✅ Ideal para CI/CD

---

## 🥒 Opción 3: Reporte HTML de Cucumber
**Cuándo usar:** Visualización rápida sin Allure instalado.

```powershell
Start-Process "E:\UnidadPrincipal\Documentos\ProyectosAutomatizacionModerna\appium-cucumber-base\target\cucumber-reports\cucumber.html"
```

**Ventajas:**
- ✅ Generado automáticamente con cada test
- ✅ Más simple y liviano
- ✅ No requiere Allure CLI

**Desventajas:**
- ❌ Menos visual que Allure
- ❌ Sin gráficos avanzados

---

## 📋 Opción 4: Ver JSON crudo (Para debugging)
**Cuándo usar:** Análisis técnico o integración con otras herramientas.

```powershell
# Cambiar a la carpeta del proyecto
cd "E:\UnidadPrincipal\Documentos\ProyectosAutomatizacionModerna\appium-cucumber-base"

# Ver resultados Allure
ls target\allure-results\

# Ver JSON Cucumber
Get-Content target\cucumber-reports\cucumber.json | ConvertFrom-Json | ConvertTo-Json -Depth 10
```

---

## 🚀 Flujo Recomendado

```powershell
# 1. Cambiar a la carpeta del proyecto
cd "E:\UnidadPrincipal\Documentos\ProyectosAutomatizacionModerna\appium-cucumber-base"

# 2. Ejecutar tests
C:\apache-maven-3.9.9\bin\mvn.cmd clean test

# 3. Ver reporte con servidor (abre automáticamente)
C:\apache-maven-3.9.9\bin\mvn.cmd allure:serve
```

---

## 🔍 Solución de Problemas

### ❌ Error: "mvn no se reconoce como nombre de un cmdlet"
**Causa:** Maven no está en el PATH del sistema.

**Solución:** Usa siempre la ruta completa:
```powershell
C:\apache-maven-3.9.9\bin\mvn.cmd
```

**Opcional:** Agregar Maven al PATH permanentemente:
1. Buscar "Variables de entorno" en Windows
2. Editar la variable `Path` del usuario
3. Agregar: `C:\apache-maven-3.9.9\bin`
4. Reiniciar PowerShell

---

### ❓ Las capturas están en `target/screenshots/` pero NO en Allure
**Causa:** Las capturas se guardaban en disco pero no se adjuntaban correctamente a los resultados de Allure.

**Solución aplicada:**
- Usamos `Allure.getLifecycle().addAttachment()` con el array de bytes directamente
- Esto escribe las capturas en `target/allure-results/` correctamente
- Las capturas en `target/screenshots/` son solo backup

✅ **Ahora las capturas SÍ aparecen en el reporte de Allure.**

---

### Si `mvn allure:serve` falla:
```powershell
cd "E:\UnidadPrincipal\Documentos\ProyectosAutomatizacionModerna\appium-cucumber-base"

# Limpiar caché de Maven
C:\apache-maven-3.9.9\bin\mvn.cmd clean -U

# Reintentar
C:\apache-maven-3.9.9\bin\mvn.cmd allure:serve
```

### Si quieres detener el servidor Allure:
Presiona `Ctrl+C` en la terminal donde está corriendo.

---

## 📸 Dónde están las capturas de pantalla

```
target/screenshots/             ← Screenshots de fallos (backup en disco)
target/allure-results/          ← Resultados crudos de Allure (incluye screenshots)
target/allure-report/           ← Reporte HTML generado
target/cucumber-reports/        ← Reportes de Cucumber
```

---

## 📝 Comandos Rápidos (Copiar y Pegar)

```powershell
# Ejecutar tests SIN dispositivo móvil (solo framework_health)
cd "E:\UnidadPrincipal\Documentos\ProyectosAutomatizacionModerna\appium-cucumber-base"
C:\apache-maven-3.9.9\bin\mvn.cmd clean test "-Drun.mobile.tests=false"

# Ejecutar tests CON dispositivo móvil (requiere emulador/device + Appium server)
cd "E:\UnidadPrincipal\Documentos\ProyectosAutomatizacionModerna\appium-cucumber-base"
C:\apache-maven-3.9.9\bin\mvn.cmd clean test

# Ver reporte de Allure (después de ejecutar tests)
C:\apache-maven-3.9.9\bin\mvn.cmd allure:serve
```

---

**💡 Tip:** Usa `allure:serve` para desarrollo diario. Usa `allure:report` para CI/CD o compartir reportes.

