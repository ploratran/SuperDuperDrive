package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.NotePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthTests {

	@LocalServerPort
	private int port; // RANDOM_PORT, port of server

	// fields:
	private static WebDriver driver;

	private SignupPage signupPage;
	private LoginPage loginPage;
	private NotePage homePage;

	String baseURL;

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = "http://localhost:" + port;
	}

	/** TEST 1:
	 * Write a test that signs up a new user, logs in,
	 * verifies that the home page is accessible,
	 * logs out, and verifies that the home page is no longer accessible.
	 * */
	@Test
	public void testLoginLogout() {

		// navigate to /signup:
		driver.get(baseURL + "/signup");

		// initialize object of SignupPage
		// call .signup() to simulate user's signup:
		signupPage = new SignupPage(driver);
		signupPage.signup("Phuong", "Tran", "ploratran", "p@ssword");

		// check if the current page title is Signup:
		assertEquals("Sign Up", driver.getTitle());

		// after signup, navigate to /login:
		driver.get(baseURL + "/login");

		// initialize object of LoginPage
		loginPage = new LoginPage(driver);
		// call .login() to simulate user's login:
		loginPage.login("ploratran", "p@ssword");

		// check if this page title is Home after successfully loggin in:
		assertEquals("Home", driver.getTitle());

		// after successfully login, auto navigate to /home:
		// initialize object for HomePage
		homePage = new NotePage(driver);

		// simulate user to click logout to be logged out:
		homePage.logout();

		// check if the "You have been logged out" message displayed after logout:
		assertTrue(loginPage.isLoggedOut());

		// after logout, navigate back to "/home":
		driver.get(baseURL + "/home");
		// verify that homepage is no longer accessible:
		assertEquals("Login", driver.getTitle());
	}

	/** TEST 2:
	 * Write a test that verifies that an unauthorized user
	 * can only access the login and signup pages.
	 * */
	@Test
	public void testUnauthorizeUser() {
		// navigate to /signup:
		driver.get(baseURL + "/signup");
		// initialize object for SignupPage:
		signupPage = new SignupPage(driver);
		signupPage.signup("Phuong", "Tran", "ploratran", "p@ssword");

		// check the title of the current page is Signup
		assertEquals("Sign Up", driver.getTitle());

		// click "Back to login" to go back to /login:
		driver.get(baseURL + "/login");

		// currently in Login page
		// simulate unauthorized user to click Login but fail:
		loginPage = new LoginPage(driver);
		loginPage.login("plora", "p@ssword");

		// check if the title of the current page is Login:
		assertEquals("Login", driver.getTitle());

		// check if invalid user message displayed:
		// expect to see "Invalid user" error message for unauthorized user login:
		assertTrue(loginPage.isInvalid());
	}
}
