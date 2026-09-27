package com.automatizacion.base.pages;

import com.automatizacion.base.ui.LoginPageUI;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for TheApp login + secret area (UI interaction only).
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

    private void clearAndType(WebElement element, String value) {
        WebElement field = wait.until(ExpectedConditions.visibilityOf(element));
        field.clear();
        field.sendKeys(value);
    }
}
