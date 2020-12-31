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

    // constructor:
    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // methods:
    public void login(String username, String password) {
        // field in data:
        this.username.sendKeys("ploratran");
        this.password.sendKeys("p@ssword");
        // hit Login button:
        this.submitBtn.click();
    }
}
