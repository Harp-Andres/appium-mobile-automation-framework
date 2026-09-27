package com.automatizacion.base.actions;

import com.automatizacion.base.pages.LoginPage;
import org.junit.jupiter.api.Assertions;

/**
 * Business actions for TheApp login smoke (BDD layer).
 */
public class LoginActions {

    private static final String LOGGED_IN_SNIPPET = "You are logged in as";

    private final LoginPage loginPage;

    public LoginActions() {
        this.loginPage = new LoginPage();
    }

    public void loginWithCredentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.submitLogin();
        loginPage.captureEvidence("PASS_login_submitted_" + username);
    }

    public void assertLoggedInSecretArea() {
        Assertions.assertTrue(
            loginPage.isLoggedInMessageVisible(),
            "Expected secret area after login"
        );
        String message = loginPage.getLoggedInMessageText();
        Assertions.assertTrue(
            message != null && message.contains(LOGGED_IN_SNIPPET),
            "Secret area text should contain \"" + LOGGED_IN_SNIPPET + "\" but was: " + message
        );
        loginPage.captureEvidence("PASS_logged_in_message");
    }
}
