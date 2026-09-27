package com.automatizacion.base.actions;

import com.automatizacion.base.pages.HomePage;
import org.junit.jupiter.api.Assertions;

/**
 * Acciones de negocio para la Home enfocadas a BDD.
 */
public class HomeActions {

    private final HomePage homePage;

    public HomeActions() {
        this.homePage = new HomePage();
    }

    public void validateUserIsOnHome() {
        boolean visible = homePage.isTitleDisplayed();
        Assertions.assertTrue(visible, "El título de la app debería estar visible");
        homePage.captureEvidence("PASS_home_titulo_visible");
    }

    public void validateApplicationTitle(String expectedTitle) {
        String actualTitle = homePage.getTitleText();
        Assertions.assertEquals(
            expectedTitle.trim(),
            actualTitle == null ? "" : actualTitle.trim(),
            "El título de la app no coincide con el esperado"
        );
        homePage.captureEvidence("PASS_validar_titulo_" + expectedTitle.replace(" ", "_"));
    }

}
