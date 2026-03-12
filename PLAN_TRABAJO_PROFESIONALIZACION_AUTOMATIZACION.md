# Plan de trabajo (resumen) - Profesionalizacion de la automatizacion

## Objetivo
Elevar la automatizacion Appium + Cucumber a un nivel mas profesional en CI/CD, mejorando calidad de merge, estabilidad, trazabilidad, analisis de codigo y seguridad.

## Alcance
- Incluye ajustes en pipeline de GitHub Actions, reglas de repositorio y practicas de ejecucion.
- Incluye observabilidad de pruebas y controles de calidad/seguridad.
- No incluye rediseño funcional completo de todos los casos de prueba.

## Prioridades (orden de importancia)
1. Quality gates y branch protection.
2. Estrategia anti-flaky.
3. Logging y observabilidad de pruebas.
4. Analisis estatico con SonarQube/SonarCloud.
5. Seguridad de pipeline y dependencias.

## Roadmap rapido (5 semanas)

### Semana 1 - Quality gates (P1)
- Definir checks obligatorios para PR (build, tests, reportes).
- Activar branch protection en `main`.
- Bloquear merge sin checks en verde.

**Entregable:** reglas de merge activas + checklist DoD de PR.

### Semana 2 - Anti-flaky (P2)
- Identificar pruebas inestables (causa app, infraestructura o timing).
- Aplicar reintentos controlados y estandares de esperas.
- Crear etiquetado/quarantine para flaky.

**Entregable:** playbook anti-flaky + metrica base de flaky rate.

### Semana 3 - Logging y observabilidad (P3)
- Estandarizar logs por escenario (runId, device, sessionId, step).
- Adjuntar evidencias minimas en fallo (logs, screenshot, video si aplica).
- Mejorar trazabilidad en Allure/summary.

**Entregable:** estandar de logs + evidencia automatica por fallo.

### Semana 4 - Sonar (P4)
- Integrar SonarQube Community (self-hosted) o SonarCloud (segun plan/costo).
- Definir quality gate minimo para PR.
- Ajustar reglas para reducir ruido inicial.

**Entregable:** analisis estatico activo en CI con gate funcional.

### Semana 5 - Seguridad (P5)
- Revisar permisos minimos de workflows y tokens.
- Activar escaneo de dependencias/CVEs.
- Definir politica de remediacion por severidad.

**Entregable:** baseline de seguridad + backlog de remediacion.

## Riesgos principales
- Resistencia inicial por mas controles en PR.
- Aumento temporal de tiempo de pipeline.
- Ruido inicial (falsos positivos/flaky historico).

## Metricas de exito (objetivo)
- 100% PR en ramas protegidas con checks obligatorios.
- Flaky rate < 5% y tendencia a la baja.
- >95% de fallos con evidencia util para diagnostico.
- 0 vulnerabilidades criticas abiertas por mas de 7 dias.

## Nota
Este plan es intencionalmente breve para ejecutarlo por fases e ir profundizando en cada punto durante la implementacion.

## Puntos adicionales recomendados (tendencias 2026)

> Compatibles con Java + Appium + Cucumber + Allure + GitHub Actions, priorizados por adopcion real en equipos small/medium.

1. **Suites por SLA (PR vs Nightly) + Test Impact Analysis**
   - Ejecutar `@smoke` en PR y `@regression` en nightly; seleccionar pruebas por cambios (rutas, tags, modulos).
   - Impacto: **alto** | Dificultad: **media**.

2. **Matriz inteligente de dispositivos/OS (risk-based)**
   - Definir una matriz minima estable (top devices/versiones) y una extendida para nightly/release.
   - Impacto: **alto** | Dificultad: **baja-media**.

3. **Triage de fallos asistido por IA (sobre artefactos)**
   - Usar resumen asistido (LLM) con logs + screenshots + stacktrace para clasificar: flaky/app/infra.
   - Mantener validacion humana para decisiones de merge.
   - Impacto: **alto** | Dificultad: **media**.

4. **Observabilidad de calidad con metricas operativas**
   - Dashboard con flakiness trend, tiempo medio a diagnostico, duracion por suite, pass rate por dispositivo.
   - Impacto: **alto** | Dificultad: **media**.

5. **Seguridad moderna en CI: OIDC + secretos efimeros**
   - Reducir tokens de larga vida y mover autenticacion a OIDC cuando aplique.
   - Impacto: **alto** | Dificultad: **media**.

6. **Versionado y gobernanza de framework de pruebas**
   - Regla de upgrades continuos para Appium/clientes/plugins/dependencias Java con ventana mensual fija.
   - Impacto: **medio-alto** | Dificultad: **baja-media**.

7. **Golden path de evidencia y reproducibilidad**
   - Estandar minimo por fallo: video/screenshot/logcat/sessionId + comando para reproducir local.
   - Impacto: **medio-alto** | Dificultad: **baja**.

## Recomendacion de adopcion
- Meter estos puntos en paralelo al roadmap actual, sin frenar entregas:
  - **Q1:** puntos 1, 2 y 7.
  - **Q2:** puntos 4 y 6.
  - **Q3:** puntos 3 y 5.
