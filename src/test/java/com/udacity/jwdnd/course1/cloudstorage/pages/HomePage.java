package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// define Page Object of home.html:
public class HomePage {

    // define fields:
    @FindBy(xpath = "//*[@id=\"logoutDiv\"]//button")
    private WebElement logoutBtn;

    // constructor:
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // methods:
    public void logout() {
        this.logoutBtn.click();
    }
}
