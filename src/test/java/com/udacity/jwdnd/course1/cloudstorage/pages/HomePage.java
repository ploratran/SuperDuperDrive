package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// define Page Object of home.html:
public class HomePage {

    // define driver field:
    private final WebDriver driver;

    /** define fields for LOGOUT: */
    @FindBy(xpath = "//*[@id=\"logoutDiv\"]//button")
    private WebElement logoutBtn;

    /** define fields for NOTES: */
    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "add-note-btn")
    private WebElement addNoteBtn;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(xpath = "//*[@id='noteTitle']")
    private WebElement noteTitleText;

    @FindBy(xpath = "//*[@id='noteDescription']")
    private WebElement noteDescriptionText;

    @FindBy(id = "note-savechanges-btn")
    private WebElement submitBtn;

    /** define fields for CREDENTIALS: */

    // constructor:
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /** METHOD FOR NOTES: */

    // method to simulate user to click on Notes tab:
    public void clickNoteTab() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.noteTab);
    }

    // method to click on Add/Edit button:
    public void clickAddNoteBtn() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.addNoteBtn);
    }

    // method to add fill data of new note:
    public void addNewNote(String title, String description) {
        // fill in data:
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + title + "';", this.noteTitle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + description + "';", this.noteDescription);

        // click on "Save Changes" to submit:
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.submitBtn);
    }

    // verify that new note title is created:
    public String getNoteTitleText() {
        return noteTitleText.getAttribute("innerHTML");
    }

    // verify that new note description is created:
    public String getNoteDescriptionText() {
        return noteDescriptionText.getAttribute("innerHTML");
    }

    /** METHOD FOR LOGOUT: */
    public void logout() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.logoutBtn);
    }
}
