package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthTests {

	@LocalServerPort
	private int port; // RANDOM_PORT, port of server

	// fields:
	private static WebDriver driver;

	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;

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
		// define data to fill in:
		String firstname = "Phuong";
		String lastname = "Tran";
		String username = "ploratran";
		String password = "p@ssword";

		// navigate to /signup:
		driver.get(baseURL + "/signup");

		// initialize object of SignupPage
		// call .signup() to simulate user's signup:
		signupPage = new SignupPage(driver);
		signupPage.signup(firstname, lastname, username, password);

		// after signup, navigate to /login:
		driver.get(baseURL + "/login");

		// initialize object of LoginPage
		loginPage = new LoginPage(driver);
		// check if this page title is Login after signing up:
		assertEquals("Login", driver.getTitle());
		// call .login() to simulate user's login:
		loginPage.login(username, password);

		// after successfully login, auto navigate to /home:
		// initialize object for HomePage
		homePage = new HomePage(driver);
		// check if page title is Home after logging in:
		assertEquals("Home", driver.getTitle());

		// try to wait 2000s, then log out:
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// simulate user to click logout to be logged out:
		homePage.logout();
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
		// check the title of the current page is Signup
		assertEquals("Sign Up", driver.getTitle());

		// click "Back to login" to go back to /login:
		signupPage.clickBackToLogin();

		// try to wait 2000s:
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// currently in Login page
		// simulate unauthorized user to click Login but fail:
		loginPage = new LoginPage(driver);
		loginPage.unauthorizeLogin();

		// check if the title of the current page is Login:
		assertEquals("Login", driver.getTitle());
	}
}
