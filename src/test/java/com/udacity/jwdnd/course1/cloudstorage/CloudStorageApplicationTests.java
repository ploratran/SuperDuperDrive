package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port; // RANDOM_PORT, port of server

	// fields:
	private static WebDriver driver;

	private SignupPage signupPage;
	private LoginPage loginPage;

	String baseURL;

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	public void afterAll() {
		driver.quit();
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = "http://localhost:" + port;
	}

	@Test
	public void testAuthorization() {
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
		// call .login() to simulate user's login:
		loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		// after successfully login, auto navigate to /home:

	}

}
