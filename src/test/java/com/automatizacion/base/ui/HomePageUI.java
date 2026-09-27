package com.automatizacion.base.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * UI elements for TheApp home menu.
 */
public class HomePageUI {

    @AndroidFindBy(accessibility = "Login Screen")
    @iOSXCUITFindBy(accessibility = "Login Screen")
    public WebElement btnLoginScreen;

    public HomePageUI(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
