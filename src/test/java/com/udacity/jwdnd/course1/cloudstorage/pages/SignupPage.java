package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// define Page Object for signup.html:
public class SignupPage {

    // define fields:
    @FindBy(id = "inputFirstName")
    private WebElement firstname;

    @FindBy(id = "inputLastName")
    private WebElement lastname;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(tagName = "button")
    private WebElement signupBtn;

    // constructor:
    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // methods:
    public void signup(String fname, String lname, String username, String password) {
        // field data:
        this.firstname.sendKeys(fname);
        this.lastname.sendKeys(lname);
        this.username.sendKeys(username);
        this.password.sendKeys(password);
        // hit Sign Up button:
        this.signupBtn.click();
    }
}
