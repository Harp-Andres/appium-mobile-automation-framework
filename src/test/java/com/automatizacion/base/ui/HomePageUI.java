package com.automatizacion.base.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * UI elementos de la pantalla Home.
 * Contiene el botón para acceder al módulo Counter Demo.
 */
public class HomePageUI {

    @AndroidFindBy(id = "com.expandtesting.practice:id/tv_app_name")
    @iOSXCUITFindBy(accessibility = "x")
    public WebElement txtTitle;

    @AndroidFindBy(id = "com.expandtesting.practice:id/btn_counter_demo")
    @iOSXCUITFindBy(accessibility = "x")
    public WebElement btnCounterDemo;

    public HomePageUI(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}