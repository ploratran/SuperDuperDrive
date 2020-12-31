package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// Page Object of login.html:
public class LoginPage {

    // define fields:
    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(tagName = "button")
    private WebElement submitBtn;

    @FindBy(id = "error-msg")
    private WebElement unAuthorizedMsg;

    @FindBy(id = "logout-msg")
    private WebElement logoutMsg;

    // constructor:
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // methods:
    public void login(String username, String password) {
        // fill in data:
        this.username.sendKeys("ploratran");
        this.password.sendKeys("p@ssword");
        // hit Login button:
        this.submitBtn.click();
    }

    public void unauthorizeLogin(String username, String password) {
        // fill in data with unauthorized user info:
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        // hit Login button
        this.submitBtn.click();
    }

    public boolean isInvalid() {
        return this.unAuthorizedMsg.isDisplayed();
    }

    public boolean isLoggedOut() {
        return this.logoutMsg.isDisplayed();
    }
}
