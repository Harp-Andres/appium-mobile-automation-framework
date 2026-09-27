package com.automatizacion.base.pages;

import com.automatizacion.base.ui.HomePageUI;

/**
 * Page Object for TheApp home screen.
 */
public class HomePage extends BasePage {

    private final HomePageUI ui;

    public HomePage() {
        super();
        this.ui = new HomePageUI(driver);
    }

    public void openLoginScreen() {
        click(ui.btnLoginScreen);
    }

    public boolean isLoginScreenEntryVisible() {
        return isVisible(ui.btnLoginScreen);
    }
}
