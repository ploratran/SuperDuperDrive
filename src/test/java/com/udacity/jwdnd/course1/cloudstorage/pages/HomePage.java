package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// define Page Object of home.html:
public class HomePage {

    // define fields for LOGOUT:
    @FindBy(xpath = "//*[@id=\"logoutDiv\"]//button")
    private WebElement logoutBtn;

    // define fields for NOTES:
    @FindBy(id = "nav-notes-tab")
    private WebElement noteTab;

    @FindBy(id = "add-note-btn")
    private WebElement addNoteBtn;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "note-savechanges-btn")
    private WebElement submitBtn;

    // constructor:
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // METHOD FOR NOTES:

    // method to simulate user to click on Notes tab:
    public void clickNoteTab() {
        noteTab.click();
    }

    // method to click on Add/Edit button:
    public void clickAddNoteBtn() {
        addNoteBtn.click();
    }

    // method to add fill data of new note:
    public void addNewNote(String title, String description) {
        // fill in data:
        noteTitle.sendKeys(title);
        noteDescription.sendKeys(description);
        // click on "Save Changes" to submit:
        submitBtn.click();
    }

    // verify that new note title is created:
    public String getNoteTitleText() {
        return noteTitle.getText();
    }

    // verify that new note description is created:
    public String getNoteDescriptionText() {
        return noteDescription.getText();
    }

    // METHOD FOR LOGOUT:
    public void logout() {
        this.logoutBtn.click();
    }
}
