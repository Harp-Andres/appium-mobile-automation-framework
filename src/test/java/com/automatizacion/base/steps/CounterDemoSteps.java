package com.automatizacion.base.steps;

import com.automatizacion.base.actions.CounterActions;
import com.automatizacion.base.actions.HomeActions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Steps BDD delgados para flujo Home → Módulo Counter.
 * Delegan la lógica de negocio a la capa Actions.
 */
public class CounterDemoSteps {

    private HomeActions homeActions;
    private CounterActions counterActions;

    /**
     * Verificar que el usuario está en la página de inicio
     */
    @Given("the user is on the application home page")
    public void userIsOnHomePage() {
        System.out.println("[STEPS] Verificando que el usuario está en la página de inicio");
        homeActions = new HomeActions();
        homeActions.validateUserIsOnHome();
        System.out.println("[STEPS] ✅ Usuario confirmado en página de inicio");
    }

    /**
     * Navegar a la pantalla de Counter Demo
     */
    @When("the user navigates to the counter demo screen")
    public void navigateToCounterDemo() {
        System.out.println("[STEPS] Navegando a módulo Counter Demo desde Home");
        ensureCounterActions();
        counterActions.openCounterDemo();
        System.out.println("[STEPS] ✅ Módulo Counter Demo abierto");
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
