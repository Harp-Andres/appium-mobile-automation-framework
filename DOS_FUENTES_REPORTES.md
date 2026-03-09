# Dos Fuentes de Reportes: Allure + Cucumber Reports

## 📊 Configuración Completada

Tu proyecto ahora genera **dos tipos de reportes** automáticamente:

### 1️⃣ **Allure Reports** (Avanzado)
- **Ubicación**: `target/site/allure-maven-plugin/index.html`
- **Características**:
  - Dashboard con métricas
  - Línea de tiempo de ejecución
  - Integración con logs
  - Filtros avanzados
  - Screenshots en caso de fallo

**Para ver Allure:**
```bash
# Opción 1: Generar y servir
mvn allure:report allure:serve

# Opción 2: Solo generar
mvn allure:report
# Luego abrir manualmente: target/site/allure-maven-plugin/index.html
```

---

### 2️⃣ **Cucumber HTML Reports** (Nativo de Cucumber)
- **Ubicación**: `target/cucumber-reports/cucumber.html`
- **Características**:
  - Reporte HTML limpio y simple
  - Detalles paso a paso
  - Documentación de features
  - Fácil de compartir

**Para ver Cucumber Reports:**
```bash
# Solo abrir en navegador:
# target/cucumber-reports/cucumber.html
```

---

## 🚀 Flujo Recomendado

### Ejecutar pruebas y generar ambos reportes:
```bash
# 1. Limpiar y ejecutar tests
mvn clean test -Dcucumber.filter.tags=@mobile -Drun.mobile.tests=true -Denv=local

# 2. Generar reporte Allure
mvn allure:report

# 3. Servir Allure en navegador (auto-abre)
mvn allure:serve

# 4. Acceder a Cucumber Reports manualmente
# target/cucumber-reports/cucumber.html
```

---

## 📁 Archivos Generados Después de Ejecutar

```
target/
├── allure-results/              ← Datos crudos para Allure
├── cucumber-reports/
│   ├── cucumber.html            ← Reporte HTML Cucumber
│   └── cucumber.json            ← JSON para herramientas externas
└── site/
    └── allure-maven-plugin/     ← Reporte Allure completo
        └── index.html           ← Página principal Allure
```

---

## 🔧 Configuración en RunCucumberTest.java

Ya está configurado con los plugins:
```java
@ConfigurationParameter(
    key = PLUGIN_PROPERTY_NAME,
    value = "pretty,summary,io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm,html:target/cucumber-reports/cucumber.html,json:target/cucumber-reports/cucumber.json"
)
```

Esto genera:
- ✅ **pretty**: Output formateado en consola
- ✅ **summary**: Resumen en consola
- ✅ **AllureCucumber7Jvm**: Datos para Allure
- ✅ **html**: Reporte HTML nativo de Cucumber
- ✅ **json**: JSON para integración con otras herramientas

---

## 📊 Comparación de Reportes

| Aspecto | Allure | Cucumber HTML |
|---------|--------|---------------|
| **Complexity** | Avanzado | Simple |
| **Dashboards** | Sí | No |
| **Histórico** | Sí | No |
| **Gráficos** | Sí | No |
| **Logs integrados** | Sí | No |
| **Compartible** | Sí | Sí |
| **Setup** | Requiere servidor | Solo archivo HTML |

---

## ✅ Ventajas de Tener Ambos

1. **Allure**: Para análisis profundo y métricas
2. **Cucumber HTML**: Para compartir rápidamente con el equipo
3. **Flexibilidad**: Elige según necesidad
4. **Documentación**: Ambos son estándares de la industria


