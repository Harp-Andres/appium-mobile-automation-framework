package com.automatizacion.base.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * UI elementos de la pantalla Counter Demo (módulo contador).
 */
public class CounterUI {

    @AndroidFindBy(id = "com.expandtesting.practice:id/btn_increment")
    @iOSXCUITFindBy(accessibility = "x")
    public WebElement btnIncrement;

    @AndroidFindBy(id = "com.expandtesting.practice:id/btn_reset")
    @iOSXCUITFindBy(accessibility = "x")
    public WebElement btnReset;

    @AndroidFindBy(id = "com.expandtesting.practice:id/tv_counter")
    @iOSXCUITFindBy(accessibility = "x")
    public WebElement tvCounter;

    public CounterUI(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}

