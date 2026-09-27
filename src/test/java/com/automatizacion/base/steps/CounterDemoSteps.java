package com.automatizacion.base.steps;

import com.automatizacion.base.actions.CounterActions;
import com.automatizacion.base.actions.HomeActions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Steps BDD delgados para flujo Home → Módulo Counter.
 * Delegan la lógica de negocio a la capa Actions.
 */
public class CounterDemoSteps {

    private static final Logger log = LoggerFactory.getLogger(CounterDemoSteps.class);

    private HomeActions homeActions;
    private CounterActions counterActions;

    /**
     * Verificar que el usuario está en la página de inicio
     */
    @Given("the user is on the application home page")
    public void userIsOnHomePage() {
        log.info("Verificando que el usuario está en la página de inicio");
        homeActions = new HomeActions();
        homeActions.validateUserIsOnHome();
        log.info("Usuario confirmado en página de inicio");
    }

    /**
     * Navegar a la pantalla de Counter Demo
     */
    @When("the user navigates to the counter demo screen")
    public void navigateToCounterDemo() {
        log.info("Navegando a módulo Counter Demo desde Home");
        ensureCounterActions();
        counterActions.openCounterDemo();
        log.info("Módulo Counter Demo abierto");
    }

    /**
     * El usuario incrementa el contador
     */
    @When("the user increments the counter")
    public void incrementCounter() {
        ensureCounterActions();
        counterActions.incrementCounterTimes(1);
    }

    /**
     * Incrementar el contador N veces desde Cucumber
     */
    @When("the user increments the counter {int} times")
    public void incrementCounterTimes(int times) {
        ensureCounterActions();
        counterActions.incrementCounterTimes(times);
    }

    /**
     * El usuario resetea el contador
     */
    @When("the user reset the counter")
    public void resetCounter() {
        ensureCounterActions();
        counterActions.resetCounter();
    }

    /**
     * Verificar que el contador tiene un valor específico
     */
    @Then("the counter value should be {string}")
    public void verifyCounterValue(String expectedValue) {
        ensureCounterActions();
        counterActions.assertCounterValue(expectedValue);
    }

    /**
     * Verificar que el contador está visible
     */
    @Then("the counter should be visible")
    public void counterShouldBeVisible() {
        ensureCounterActions();
        counterActions.assertCounterVisible();
    }

    /**
     * Verificar que el título de la aplicación es el esperado
     */
    @Then("the application title should be {string}")
    public void verifyApplicationTitle(String expectedTitle) {
        if (homeActions == null) {
            homeActions = new HomeActions();
        }
        homeActions.validateApplicationTitle(expectedTitle);
    }

    private void ensureCounterActions() {
        if (counterActions == null) {
            counterActions = new CounterActions();
        }
    }
}
