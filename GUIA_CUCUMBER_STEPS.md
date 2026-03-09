# Escenarios de Cucumber con Appium - Guía de Uso

## Archivos Creados

### 1. **LoginSteps.java**
Clase con los steps de Cucumber para el flujo de Login/Counter Demo.

**Pasos implementados:**
- `@Given("the user is on the application home page")` - Verificar que está en inicio
- `@When("the user navigates to the counter demo screen")` - Navegar a Counter Demo
- `@When("the user increments the counter")` - Incrementar contador
- `@Then("the counter should be visible")` - Verificar que counter es visible
- `@Then("the counter value should be {string}")` - Verificar valor específico

### 2. **counter_demo.feature**
Feature file con 3 escenarios de ejemplo:
- Navigación a Counter Demo
- Incrementar múltiples veces
- Verificar valor inicial

## Cómo Usar

### Ejecutar solo los escenarios mobile
```bash
mvn --% clean test -Dcucumber.filter.tags=@mobile -Drun.mobile.tests=true -Denv=local
```

### Ejecutar escenarios específicos
```bash
# Solo counter_demo.feature
mvn --% clean test -Dcucumber.filter.tags=@mobile -Drun.mobile.tests=true -Denv=local -Dtest=RunCucumberTest

# Por nombre de escenario
mvn --% clean test -Dcucumber.filter.tags=@mobile -Drun.mobile.tests=true -Denv=local
```

## Estructura de los Steps

### Patrón Implementado

```java
// 1. Crear instance de Page Object
HomePage homePage = new HomePage();

// 2. Validar disponibilidad con timeout
boolean isVisible = waitForElement(() -> homePage.isTitleDisplayed(), 10);

// 3. Usar métodos de Page Object
String title = homePage.getTitleText();

// 4. Assert para validar
Assertions.assertTrue(isVisible, "Mensaje de error");
```

### Con Logs
Todos los steps tienen logging integrado para debugging:
```
[LOGIN_STEPS] Verificando que el usuario está en la página de inicio
[LOGIN_STEPS] ✅ Usuario confirmado en página de inicio
[LOGIN_STEPS] Título: ExpandTesting Practice
```

## Adaptaciones Realizadas vs. Serenity

| Aspecto | Serenity | Nuestro Stack |
|--------|----------|---------------|
| **Actores** | `OnStage.theActorCalled("Andres")` | Instancia directa de Page Object |
| **Assertions** | `GivenWhenThen.seeThat()` | `Assertions.assertTrue()`, etc. |
| **Esperas** | `withTimeoutOf(60)` integrado | `waitForElement()` utility |
| **Page Objects** | Targets de Screenplay | Page Objects con UI classes |
| **Elementos** | `Target.the()` | `@AndroidFindBy` + `@iOSXCUITFindBy` |

## Próximas Mejoras

1. **Shared Steps** - Crear steps reutilizables para múltiples features
2. **Data-Driven** - Usar tablas Gherkin para múltiples casos
3. **Screenshots en fallo** - Capturar pantalla cuando falla un assertion
4. **Allure integration** - Ya configurado, se capturan automáticamente en `target/allure-results`

## Ejecutar con Allure

```bash
# Ejecutar con Allure
mvn --% clean test -Dcucumber.filter.tags=@mobile -Drun.mobile.tests=true -Denv=local

# Ver reporte
mvn allure:report
mvn allure:serve
```

## Debugging

### Ver logs en consola
- Busca líneas que empiezan con `[LOGIN_STEPS]`
- Busca líneas que empiezan con `[DRIVER]` para logs de creación de driver
- Busca líneas que empiezan con `[FRAMEWORK]` para logs de hooks

### Si los pasos no se ejecutan
1. Verifica que el feature file tiene la anotación `@mobile`
2. Verifica que ejecutas con `-Drun.mobile.tests=true`
3. Verifica que el nombre del método de step coincide exactamente con la anotación `@Given/@When/@Then`

