package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.pages.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTests {

    @LocalServerPort
    private Integer port; // this port is the RANDOM_PORT, or port of server

    // define Credential Service variable
    // in order to use method getCredentialById() to get Credential object
    // from Credential object, use getKey() to get key for encryption:
    @Autowired
    private CredentialService credentialService;

    // initialize fields:
    private static WebDriver driver;
    String baseURL;
    private CredentialPage credentialPage;
    private ResultPage resultPage;
    private EncryptionService encryptionService;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    // after all tests, Selenium quit driver:
    @AfterAll
    public static void afterAll() {
        driver.quit();
    }

    // before each test, signup and login to get to homepage:
    @BeforeEach
    public void beforeEach() {
        baseURL = "http://localhost:" + port;

        // navigate to /signup:
        driver.get(baseURL + "/signup");
        // initialize object for SignupPage:
        SignupPage signupPage = new SignupPage(driver);
        // simulate user to register new user:
        signupPage.signup("Phuong", "Tran", "ploratran", "p@ssword");

        // navigate to login:
        driver.get(baseURL + "/login");

        // initialize object for LoginPage:
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("ploratran", "p@ssword");

        // currently logged in at this stage
        // initialize Encryption service to encrypt/decrypt password
        // inside add credential and edit credential:
        encryptionService = new EncryptionService();

        // initialize homepage page:
        credentialPage = new CredentialPage(driver);

        // simulate user to click on Credentials tab on nav bar:
        credentialPage.clickCredTab();
        // simulate user to click on Add new credential button:
        credentialPage.clickAddCredBtn();

        // simulate user to add new data to create add credential:
        credentialPage.fillCredentialData("facebook.com", "ploratran", "p@ssword");

        // after successfully added new credential, navigate to Result page
        // initialize new Result page object:
        resultPage = new ResultPage(driver);
        // navigate back to /home by click on "here" button:
        resultPage.clickHereBtn();

        // at this stage, user is currently back to homepage:
        // test if page title is "Home":
        assertEquals("Home", driver.getTitle());

        // simulate user to click on Credentials tab again:
        credentialPage.clickCredTab();
    }

    /**
     *  TEST 1:
     *  Write a test that creates a set of credentials,
     *  verifies that they are displayed,
     *  and verifies that the displayed password is encrypted.
     * */
    @Test
    public void addNewCredential() {

        // initialize Credential object:
        // since this is a test, just get the very first value of data displayed on screen:
        Credential credential = this.credentialService.getCredentialById(1);

        // test if new credential url, username, and password match:
        assertEquals("facebook.com", credentialPage.getUrlText());
        assertEquals("ploratran", credentialPage.getUsernameText());
        assertEquals(this.encryptionService.encryptValue("p@ssword", credential.getKey()), credentialPage.getPasswordText());
    }

    /**
     * TEST 2:
     *  Write a test that views an existing set of credentials,
     *  verifies that the viewable password is unencrypted,
     *  edits the credentials, and verifies that the changes are displayed.
     * */
    @Test
    public void editCredential() {
        // simulate user to click on "Edit" button:
        credentialPage.clickEditBtn();

        // TODO: Write test to verify the viewable password is unencrypted:
        // assertEquals("p@ssword", credentialPage.getUnencryptedPassword());

        // simulate user to modify existing data to edit credential:
        credentialPage.fillCredentialData("instagram.com", "haphuong", "newP@ssword");

        // after successfully added new credential, navigate to Result page
        // initialize new Result page object:
        resultPage = new ResultPage(driver);
        // navigate back to /home by click on "here" button:
        resultPage.clickHereBtn();

        // test if page title is "Home":
        assertEquals("Home", driver.getTitle());

        // simulate user to click on Credentials tab again:
        credentialPage.clickCredTab();

        // use CredentialService to get credential data by id
        Credential credential = this.credentialService.getCredentialById(1);

        // test if newly edited credential url, username, and password match:
        assertEquals("instagram.com", credentialPage.getUrlText());
        assertEquals("haphuong", credentialPage.getUsernameText());
        // use EncryptionService to use decryptValue() to get unencrypted pass
        // then compare with currently encrypted password:
        assertEquals(this.encryptionService.encryptValue("newP@ssword", credential.getKey()), credentialPage.getPasswordText());
    }
}
