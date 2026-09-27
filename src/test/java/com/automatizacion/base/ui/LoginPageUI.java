package com.automatizacion.base.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * UI elements for TheApp login and post-login secret area.
 */
public class LoginPageUI {

    @AndroidFindBy(accessibility = "username")
    @iOSXCUITFindBy(accessibility = "username")
    public WebElement usernameField;

    @AndroidFindBy(accessibility = "password")
    @iOSXCUITFindBy(accessibility = "password")
    public WebElement passwordField;

    @AndroidFindBy(accessibility = "loginBtn")
    @iOSXCUITFindBy(accessibility = "loginBtn")
    public WebElement loginButton;

    @AndroidFindBy(xpath = "//*[contains(@text,'You are logged in as')]")
    @iOSXCUITFindBy(xpath = "//*[contains(@label,'You are logged in as')]")
    public WebElement loggedInMessage;

    public LoginPageUI(AppiumDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
