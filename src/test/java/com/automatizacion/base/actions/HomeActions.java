package com.automatizacion.base.actions;

import com.automatizacion.base.pages.HomePage;
import org.junit.jupiter.api.Assertions;

/**
 * Application service for TheApp home screen (BDD orchestration).
 * Depends on {@link HomePage} (DIP) — single responsibility: home business checks.
 */
public class HomeActions {

    private final HomePage homePage;

    public HomeActions() {
        this(new HomePage());
    }

    public HomeActions(HomePage homePage) {
        this.homePage = homePage;
    }

    public void validateUserIsOnHome() {
        Assertions.assertTrue(
            homePage.isLoginScreenEntryVisible(),
            "TheApp home should show the Login Screen entry"
        );
        homePage.captureEvidence("PASS_home_login_entry_visible");
    }

    public void openLoginScreen() {
        homePage.openLoginScreen();
        homePage.captureEvidence("PASS_login_screen_opened");
    }
}
