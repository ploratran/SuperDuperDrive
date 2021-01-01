package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.*;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.security.SecureRandom;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTests {

    @LocalServerPort
    private Integer port; // this port is the RANDOM_PORT

    // initialize fields:
    private static WebDriver driver;
    String baseURL;
    private NotePage homePage;
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
        // initialize homepage page:
        credentialPage = new CredentialPage(driver);

        encryptionService = new EncryptionService();
    }

    /**
     *  TEST 1:
     *  Write a test that creates a set of credentials,
     *  verifies that they are displayed,
     *  and verifies that the displayed password is encrypted.
     * */
    @Test
    public void addNewCredential() {

        // test that user can successfully login to /home page:
        assertEquals("Home", driver.getTitle());

        // simulate user to click on Credentials tab on nav bar:
        credentialPage.clickCredTab();

        // simulate user to click on Add new credential button:
        credentialPage.clickAddCredBtn();

        // simulate user to add new data to create add credential:
        credentialPage.addNewCredential("facebook.com", "ploratran", "p@ssword");

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

        // test if new credential url and username match:
        assertEquals("facebook.com", credentialPage.getUrlText());
        assertEquals("ploratran", credentialPage.getUsernameText());

        // encrypt password when user adds new credential before store to DB:
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);

        assertEquals(this.encryptionService.encryptValue("p@ssword", encodedKey), credentialPage.getPasswordText());
    }
}
