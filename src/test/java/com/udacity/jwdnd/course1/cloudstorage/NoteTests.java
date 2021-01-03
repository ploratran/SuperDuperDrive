package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.NotePage;
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
    private NotePage notePage;
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
        notePage = new NotePage(driver);

        /** add a new note so that we can test for add/edit/delete at same time: */
        // simulate user to click on Notes tab:
        notePage.clickNoteTab();
        // simulate user to click "Add/Edit a Note" button to add new note:
        notePage.clickAddNoteBtn();
        // fill in data to add a new note:
        notePage.fillNoteData("Test Title", "Test Description");

        // after successfully added new note, navigate to Result page:
        // initialize new Result page object:
        resultPage = new ResultPage(driver);
        // navigate back to /home by click on "Here" link:
        resultPage.clickHereBtn();

        // simulate user to click on Notes tab:
        notePage.clickNoteTab();
    }

    /**
     * TEST 3:
     * Write a test that deletes a note
     * and verifies that the note is no longer displayed.
     * */
    @Test
    public void deleteNote() {

        // simulate user to click on "Delete button"
        notePage.clickDeleteBtn();

        // after successfully added new note, navigate to Result page:
        // initialize new Result page object:
        resultPage = new ResultPage(driver);
        // navigate back to /home by click on "Here" link:
        resultPage.clickHereBtn();

        // simulate user to click on Notes tab:
        notePage.clickNoteTab();

        // test there should be no note data on homepage:
        assertEquals(null, notePage.getNoteTitleText());
    }

    /**
     *  TEST 1:
     *  Write a test that creates a note, and verifies it is displayed.
     *  Test if newly added note is displayed with same title and description in Home
     * */
    @Test
    public void addNewNote() {

        // test if new note's title and description match:
        assertEquals("Test Title", notePage.getNoteTitleText());
        assertEquals("Test Description", notePage.getNoteDescriptionText());
    }

    /**
     * TEST 2:
     * Write a test that edits an existing note
     * and verifies that the changes are displayed.
     * */
    @Test
    public void editNote() {

        // simulate user to click on "Edit" button:
        notePage.clickEditBtn();
        // simulate user to editing note with new data:
        notePage.fillNoteData("New Title", "New Description");

        // after successfully added new note, navigate to Result page:
        // initialize new Result page object:
        resultPage = new ResultPage(driver);
        // navigate back to /home by click on "Here" link:
        resultPage.clickHereBtn();

        // simulate user to click on Notes tab:
        notePage.clickNoteTab();

        // test if new note's title and description match:
        assertEquals("New Title", notePage.getNoteTitleText());
        assertEquals("New Description", notePage.getNoteDescriptionText());
    }

}
