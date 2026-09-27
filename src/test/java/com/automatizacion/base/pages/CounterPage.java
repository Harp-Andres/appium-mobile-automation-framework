package com.automatizacion.base.pages;

import com.automatizacion.base.ui.CounterUI;

/**
 * Page Object para la pantalla del módulo Counter Demo.
 */
public class CounterPage extends BasePage {

    private final CounterUI ui;

    public CounterPage() {
        super();
        this.ui = new CounterUI(driver);
    }

    public void clickIncrementButton() {
        click(ui.btnIncrement);
    }

    public void clickResetButton() {
        click(ui.btnReset);
    }

    public String getCounterValue() {
        return textOf(ui.tvCounter);
    }

    public boolean isCounterDisplayed() {
        return isVisible(ui.tvCounter);
    }
}

