package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests {

    @LocalServerPort
    private Integer port; // this port is the RANDOM_PORT

    // initialize fields:
    private static WebDriver driver;
    String baseURL;
    private HomePage homePage;
    private ResultPage resultPage;

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
        homePage = new HomePage(driver);

        // try to wait 2000s:
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    /**
     *  TEST 1:
     *  Write a test that creates a note, and verifies it is displayed.
     * */
    @Test
    public void addNewNote() {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        // simulate user to click on Notes tab on nav bar:
        homePage.clickNoteTab();

        // try to wait 2000s:
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // simulate user to click on Add/Edit button:
        homePage.clickAddNoteBtn();

        // try to wait 2000s:
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // fill data and submit:
        homePage.addNewNote("Test Note Title", "This is a test description");

        // try to wait 2000s:
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // after successfully added new note, navigate to Result page:
        // initialize new Result page object:
        resultPage = new ResultPage(driver);

        // try to wait 2000s:
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // navigate back to /home by click on "Here" link:
        resultPage.clickHereBtn();

        // this stage is currently back to homepage:
        assertEquals("Home", driver.getTitle());

        // click note tab again:
        homePage.clickNoteTab();

        assertEquals("Test Note Title", homePage.getNoteTitleText());
        assertEquals("Test Description Title", homePage.getNoteDescriptionText());

        // try to wait 2000s:
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
