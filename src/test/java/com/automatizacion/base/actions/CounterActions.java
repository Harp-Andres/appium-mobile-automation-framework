package com.automatizacion.base.actions;

import com.automatizacion.base.pages.CounterPage;
import com.automatizacion.base.pages.HomePage;
import org.junit.jupiter.api.Assertions;

/**
 * Acciones de negocio para el módulo Counter Demo enfocadas a BDD.
 */
public class CounterActions {

    private final CounterPage counterPage;
    private final HomePage homePage;

    public CounterActions() {
        this.counterPage = new CounterPage();
        this.homePage = new HomePage();
    }

    public void openCounterDemo() {
        homePage.clickCounterDemoButton();
        Assertions.assertTrue(counterPage.isCounterDisplayed(), "El contador debería estar visible después de navegar");
        counterPage.captureEvidence("PASS_counter_demo_abierto");
    }

    public void incrementCounterTimes(int times) {
        Assertions.assertTrue(times > 0, "La cantidad de incrementos debe ser mayor que 0");
        for (int i = 0; i < times; i++) {
            counterPage.clickIncrementButton();
        }
        counterPage.captureEvidence("counter_incrementado_" + times + "_veces");
    }

    public void resetCounter() {
        counterPage.clickResetButton();
        counterPage.captureEvidence("counter_reseteado");
    }

    public void assertCounterVisible() {
        Assertions.assertTrue(counterPage.isCounterDisplayed(), "El contador debería estar visible");
        counterPage.captureEvidence("PASS_counter_visible");
    }

    public void assertCounterValue(String expectedValue) {
        String actualValue = counterPage.getCounterValue();
        Assertions.assertEquals(
            expectedValue,
            actualValue,
            "El valor del contador debería ser " + expectedValue + " pero es " + actualValue
        );
        counterPage.captureEvidence("PASS_counter_valor_" + expectedValue);
    }
}
