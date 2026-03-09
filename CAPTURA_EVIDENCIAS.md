# 📸 Captura de Evidencias: Screenshots & Logs

Tu stack Appium + Cucumber **SÍ puede capturar evidencias** de forma automática y manual.

## ✅ Lo que está implementado

### 1. **Captura Automática en Fallos**
- Cuando un escenario falla → automáticamente se captura screenshot
- Se adjunta a Allure Report
- Se guarda en carpeta `target/screenshots/`

### 2. **Captura Manual en Pasos**
Puedes capturar evidencia en cualquier momento desde Pages/Actions:
```java
// Dentro de una Action
this.loginPage.captureEvidence("contador_validacion");
```

### 3. **Logs Adjuntos**
Información del driver + logs contextuales se capturan automáticamente:
- Session ID
- Plataforma (Android/iOS)
- Device Name
- App Package

## 🎯 Archivos Agregados

```
src/test/java/com/automatizacion/base/
├── utils/
│   └── EvidenceCapture.java          ← Métodos para capturar screenshots + logs
├── hooks/
│   └── EvidenceHooks.java            ← Hook de Cucumber para captura automática
└── pages/
    └── BasePage.java                 ← Integración de captureEvidence()
```

## 📁 Dónde se Guardan

```
target/
├── screenshots/                       ← Screenshots locales
│   ├── FAILED_Escenario_2026-03-06_17-45-25-123.png
│   └── contador_validacion_2026-03-06_17-45-26-456.png
└── site/
    └── allure-maven-plugin/
        └── index.html                ← Screenshots también en Allure Report
```

## 🚀 Cómo Usar

### Opción 1: Automática (sin código)
La captura sucede automáticamente cuando:
- Un escenario **falla** → screenshot + logs adjuntos
- Un escenario **pasa** → solo info del driver

```bash
mvn clean test -Dcucumber.filter.tags=@mobile -Drun.mobile.tests=true -Denv=local
# Si falla → automáticamente captura
```

### Opción 2: Manual en Pasos Críticos
En `CounterActions.java` o `HomeActions.java`:
```java
public void validateCounterValue(String expectedValue) {
    // Capturar antes de validar
    this.loginPage.captureEvidence("counter_before_validation");
    
    String actualValue = loginPage.getCounterValue();
    Assertions.assertEquals(expectedValue, actualValue);
    
    // Capturar después de validación exitosa
    this.loginPage.captureEvidence("counter_after_validation");
}
```

### Opción 3: Log Manual
```java
import com.automatizacion.base.utils.EvidenceCapture;

// En cualquier Action/Page
EvidenceCapture.attachLog("Mi Log Custom", "Descripción del evento");
```

## 📊 Ver Evidencias en Allure

```bash
# 1. Generar reporte Allure
mvn allure:report

# 2. Servir en navegador
mvn allure:serve

# 3. Ver sección "Attachments" en cada escenario → screenshots + logs
```

## 🎨 Ejemplo Completo de Uso

**CounterActions.java**:
```java
public class CounterActions {
    
    private final LoginPage loginPage;

    public CounterActions() {
        this.loginPage = new LoginPage();
    }

    public void assertCounterValue(String expectedValue) {
        // Capturar antes
        loginPage.captureEvidence("ANTES_validar_counter_" + expectedValue);
        
        // Validar
        String actualValue = loginPage.getCounterValue();
        Assertions.assertEquals(expectedValue, actualValue, 
            "Counter debería ser " + expectedValue);
        
        // Capturar después
        loginPage.captureEvidence("DESPUES_validar_counter_" + expectedValue);
    }
}
```

## ⚙️ Configuración Actual

- **Screenshot format**: PNG
- **Hook trigger**: After each scenario (solo si falla, adjunta screenshot automático)
- **Allure integration**: Automática
- **Directorio local**: `target/screenshots/`

## 📈 Ventajas

✅ **Automática en fallos** → no necesitas recordar capturar  
✅ **Manual opcional** → captura cuando TÚ lo decidas  
✅ **Allure + Local** → dos copias de evidencias  
✅ **Logs contextuales** → session ID, device info automático  
✅ **Sin cambios de código** → funciona con tu estructura actual POM + Actions  

## 🔧 Si Quieres Extender

Posibles mejoras:
- Capturar **video** durante tests (Appium + FFmpeg)
- Capturar **page source** en caso de fallo
- Capturar **console logs** del device
- Capturar **network calls** (si usas proxy)

Por ahora, **screenshots + logs textuales** son suficientes para debugging.


