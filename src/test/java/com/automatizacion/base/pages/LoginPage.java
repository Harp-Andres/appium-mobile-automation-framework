package com.automatizacion.base.pages;

import com.automatizacion.base.ui.LoginPageUI;

/**
 * Page Object for TheApp login flow.
 */
public class LoginPage extends BasePage {

    private final LoginPageUI ui;

    public LoginPage() {
        super();
        this.ui = new LoginPageUI(driver);
    }

    public void enterUsername(String username) {
        clearAndType(ui.usernameField, username);
    }

    public void enterPassword(String password) {
        clearAndType(ui.passwordField, password);
    }

    public void submitLogin() {
        click(ui.loginButton);
    }

    public String getLoggedInMessageText() {
        return textOf(ui.loggedInMessage);
    }

    public boolean isLoggedInMessageVisible() {
        return isVisible(ui.loggedInMessage);
    }

    private void clearAndType(org.openqa.selenium.WebElement element, String value) {
        var field = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(element));
        field.clear();
        field.sendKeys(value);
    }
}
