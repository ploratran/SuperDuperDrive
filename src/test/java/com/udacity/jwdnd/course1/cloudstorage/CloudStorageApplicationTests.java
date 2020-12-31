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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

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
		// check if page title is Home after loggin in:
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

}
