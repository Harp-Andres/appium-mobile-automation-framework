package com.automatizacion.base.steps;

import com.automatizacion.base.actions.HomeActions;
import com.automatizacion.base.actions.LoginActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BDD steps for TheApp home → login smoke.
 */
public class TheAppLoginSteps {

    private static final Logger log = LoggerFactory.getLogger(TheAppLoginSteps.class);

    private HomeActions homeActions;
    private LoginActions loginActions;

    @Given("the user is on TheApp home screen")
    public void userIsOnTheAppHome() {
        log.info("Verifying TheApp home screen");
        homeActions = new HomeActions();
        homeActions.validateUserIsOnHome();
    }

    @When("the user opens the login screen")
    public void openLoginScreen() {
        ensureHomeActions();
        homeActions.openLoginScreen();
    }

    @When("the user logs in with username {string} and password {string}")
    public void loginWithCredentials(String username, String password) {
        ensureLoginActions();
        loginActions.loginWithCredentials(username, password);
    }

    @Then("the secret area should show logged-in message")
    public void secretAreaShowsLoggedInMessage() {
        ensureLoginActions();
        loginActions.assertLoggedInSecretArea();
    }

    @And("the user opens the login screen")
    public void andOpenLoginScreen() {
        openLoginScreen();
    }

    private void ensureHomeActions() {
        if (homeActions == null) {
            homeActions = new HomeActions();
        }
    }

    private void ensureLoginActions() {
        if (loginActions == null) {
            loginActions = new LoginActions();
        }
    }
}
