package com.udacity.jwdnd.course1.cloudstorage.pages;

import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// define Page Object for each web element of Credential tab:
public class CredentialPage {
    /**
     * define fields
     */
    // logout:
    @FindBy(xpath = "//*[@id='logoutDiv']//button")
    private WebElement logoutBtn;

    // define fields for Credential:
    @FindBy(id = "nav-credentials-tab")
    private WebElement credTab;

    // url field:
    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(xpath = "//*[@id='credUrlText']")
    private WebElement credentialUrlText;

    // username field:
    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(xpath = "//*[@id='credUsernameText']")
    private WebElement credentialUsernameText;

    // password field:
    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(xpath = "//*[@id='credPasswordText']")
    private WebElement credentialPasswordText;

    // buttons:
    @FindBy(id = "add-cred-btn")
    private WebElement addCredBtn;

    @FindBy(id = "cred-savechanges-btn")
    private WebElement submitBtn;

    @FindBy(id = "cred-EditBtn")
    private WebElement editBtn;

    @FindBy(id = "cred-DeleteBtn")
    private WebElement deleteBtn;

    // driver (Chrome):
    private final WebDriver driver;

    // constructor:
    public CredentialPage(WebDriver driver) {
        this.driver = driver;
        // initialize page objects using initElements() from PageFactory class in Selenium:
        // all web element will get initialized:
        // Reference: https://www.seleniumeasy.com/selenium-tutorials/page-factory-pattern-in-selenium-webdriver
        PageFactory.initElements(driver, this);
    }

    /** define methods
     * use JavascriptExecutor to execute Javascript through Selenium Webdriver
     * .executeScript() to run javascript on selected window or current page
     * Reference: https://www.guru99.com/execute-javascript-selenium-webdriver.html
     * */
    // method to simulate user to click on Credentials tab:
    public void clickCredTab() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.credTab);
    }

    // method to click on Add New Credential button:
    public void clickAddCredBtn() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.addCredBtn);
    }

    // method to click on Edit button:
    public void clickEditBtn() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.editBtn);
    }

    // method to click on Delete button:
    public void clickDeleteBtn() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.deleteBtn);
    }

    // method to fill data to add new credentials:
    public void fillCredentialData(String url, String username, String password) {
        // fill in data:
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + url + "';", this.credentialUrl);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", this.credentialUsername);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", this.credentialPassword);
        // simulate click to submit:
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.submitBtn);
    }

    /** use .getAttribute("innerHTML") to get HTML source of the content of the element in Selenium
     * Reference: https://www.browserstack.com/guide/get-html-source-of-web-element-in-selenium-webdriver
     * */
    // verify that new credential's url is created:
    public String getUrlText() {
        return credentialUrlText.getAttribute("innerHTML");
    }

    // verify that new credential's username is created:
    public String getUsernameText() {
        return credentialUsernameText.getAttribute("innerHTML");
    }

    // verify that new credential's password is created:
    // this should be the value of ENCRYPTED password:
    public String getPasswordText() {
        return credentialPasswordText.getAttribute("innerHTML");
    }


    /** .getAttribute("value") will get the value of the attribute
     * Reference: https://knowledge.udacity.com/questions/434707
     * */
    // get unencrypted password:
    public String getUnencryptedPassword() {
        return this.credentialPassword.getAttribute("value");
    }

}
