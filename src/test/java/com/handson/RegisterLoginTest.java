package com.handson;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.relevantcodes.extentreports.ExtentReports;

import io.github.bonigarcia.wdm.WebDriverManager;
import page.HomePage;
import page.RegisterLoginPage;
import utils.Utility;

@RunWith(JUnitPlatform.class)
public class RegisterLoginTest extends Utility {

	RegisterLoginPage registerLoginPage;
	HomePage homePage;

	@BeforeAll
	public static void beforeMethod() {
		report = new ExtentReports(System.getProperty("user.dir") + getProperty("reportLoc"));
		WebDriverManager.chromedriver().setup();
	}
	
	@BeforeEach
	public void beforeEach() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*", "start-maximized");
		driver = new ChromeDriver(options);
		registerLoginPage = new RegisterLoginPage(driver);
		homePage = new HomePage(driver);
	}
	
	@AfterEach()
	public void afterEach() {
		driver.quit();
		report.endTest(logger);
	}
	
	@AfterAll
	public static void afterMethod() {
		report.flush();
	}

	@Test
	public void register() {
		logger = report.startTest("Selenium Assignment -- Register");
		registerLoginPage.launchOpenCartApp();
		registerLoginPage.clickOnMyAccount("Register");
		registerLoginPage.enterPersonalDetailsAndRegister();
		registerLoginPage.verifySuccessfulRegisteration();
	}
	
	@Test
	public void login() {
		logger = report.startTest("Selenium Assignment -- Login and Shopping cart");
		registerLoginPage.launchOpenCartApp();
		registerLoginPage.clickOnMyAccount("Login");
		registerLoginPage.loginUsingEmail();
		registerLoginPage.verifySuccessfulLogin();
		homePage.clickSubMenuLink("Components", "Monitors");
		homePage.selectProduct("Apple Cinema 30");
		homePage.verifyProductDetailsPage();
		homePage.provideProductDetails();
		homePage.clickOnAddToCart();
		homePage.verifyMessage("Success: You have added");
		homePage.goToCartAndVerifyProduct();
	}
	
	@Test
	public void editAccountLastName() {
		logger = report.startTest("Selenium Assignment -- Edit Account Last Name");
		registerLoginPage.launchOpenCartApp();
		registerLoginPage.clickOnMyAccount("Login");
		registerLoginPage.loginUsingEmail();
		registerLoginPage.verifySuccessfulLogin();
		homePage.updateLastName();
		homePage.verifyMessage("Success: Your account has been successfully updated.");
		registerLoginPage.clickOnMyAccount("Logout");
	}
	
	@Test
	public void addRemoveWishList() {
		logger = report.startTest("Selenium Assignment -- Add Remove Wish List");
		registerLoginPage.launchOpenCartApp();
		registerLoginPage.clickOnMyAccount("Login");
		registerLoginPage.loginUsingEmail();
		registerLoginPage.verifySuccessfulLogin();
		homePage.clickSubMenuLink("Desktops", "Mac");
		homePage.addProductToWishList();
		homePage.verifyTheProduct("iMac");
		registerLoginPage.clickOnMyAccount("Logout");
	}
}
