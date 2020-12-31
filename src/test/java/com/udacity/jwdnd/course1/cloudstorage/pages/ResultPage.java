package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    // fields:
    @FindBy(tagName = "a")
    private WebElement backHomeLink;

    // constructor:
    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // methods:
    public void clickHereBtn() {
        backHomeLink.click();
    }
}
