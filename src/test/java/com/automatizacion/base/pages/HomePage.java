package com.automatizacion.base.pages;

import com.automatizacion.base.ui.HomePageUI;

/**
 * Page Object para la pantalla Home de la aplicación.
 */
public class HomePage extends BasePage {

    private final HomePageUI ui;

    public HomePage() {
        super();
        this.ui = new HomePageUI(driver);
    }

    public void clickCounterDemoButton() {
        click(ui.btnCounterDemo);
    }

    public String getTitleText() {
        return textOf(ui.txtTitle);
    }

    public boolean isTitleDisplayed() {
        return isVisible(ui.txtTitle);
    }
}
