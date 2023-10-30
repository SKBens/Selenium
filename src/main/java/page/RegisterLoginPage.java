package page;

import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.LogStatus;

import utils.Context;
import utils.ScenarioContext;
import utils.Utility;

public class RegisterLoginPage extends Utility {
	@FindBy(how = How.PARTIAL_LINK_TEXT, using = "Login")
	private WebElement loginLnk;

	@FindBy(how = How.PARTIAL_LINK_TEXT, using = "Register")
	private WebElement registerLnk;

	@FindBy(how = How.XPATH, using = "//span[.='My Account']")
	private WebElement myAccountLnk;

	@FindBy(how = How.NAME, using = "firstname")
	private WebElement firstName;

	@FindBy(how = How.NAME, using = "lastname")
	private WebElement lastName;

	@FindBy(how = How.NAME, using = "email")
	private WebElement eMail;

	@FindBy(how = How.NAME, using = "telephone")
	private WebElement telephone;

	@FindBy(how = How.NAME, using = "password")
	private WebElement password;

	@FindBy(how = How.NAME, using = "confirm")
	private WebElement confirmPassword;

	@FindBy(how = How.NAME, using = "agree")
	private WebElement agreeCheckbox;

	@FindBy(how = How.XPATH, using = "//input[@value='Continue']")
	private WebElement register;

	@FindBy(how = How.XPATH, using = "//input[@value='Login']")
	private WebElement login;

	@FindBy(how = How.XPATH, using = "//h2[.='My Account']")
	private WebElement homePageHeader;

	@FindBy(how = How.XPATH, using = "//h1[.='Your Account Has Been Created!']")
	private WebElement accountCreationHeader;

	@FindBy(how = How.LINK_TEXT, using = "Desktops")
	private WebElement desktops;

	public RegisterLoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void launchOpenCartApp() {
		driver.get(getProperty("url"));
		logger.log(LogStatus.INFO, "Application Started " + driver.getCurrentUrl());
		waitUntilVisible(10, desktops);
	}

	public void clickOnMyAccount(String lnkType) {
		myAccountLnk.click();
		By subLnk = By.xpath("//a[.='" + lnkType + "']");
		waitUntilVisible(10, subLnk);
		clickElement(subLnk);
		logger.log(LogStatus.INFO, "Clicked on " + lnkType + " menu");
		captureScreenshot();
	}

	public void enterPersonalDetailsAndRegister() {
		firstName.sendKeys("Test");
		lastName.sendKeys("Test");
		String userName = "TestData" + ThreadLocalRandom.current().nextInt(1000) + "@gmail.com";
		eMail.sendKeys(userName);
		ScenarioContext.setContext(Context.USER_NAME, userName);
		telephone.sendKeys("9876543210");
		password.sendKeys("9876543210");
		confirmPassword.sendKeys("9876543210");
		agreeCheckbox.click();
		captureScreenshot();
		register.click();
		logger.log(LogStatus.INFO, "Entered Personal Details for registeration");
	}

	public void verifySuccessfulRegisteration() {
		waitUntilVisible(10, accountCreationHeader);
		verifyElement(accountCreationHeader);
		logger.log(LogStatus.INFO, "Verified account creation page");
		captureScreenshot();
	}

	public void loginUsingEmail() {
		eMail.sendKeys("TestData01@gmail.com");
		password.sendKeys("9876543210");
		captureScreenshot();
		login.click();
		logger.log(LogStatus.INFO, "Clicked on Login button");
	}

	public void verifySuccessfulLogin() {
		waitUntilVisible(10, homePageHeader);
		verifyElement(homePageHeader);
		logger.log(LogStatus.INFO, "Verified home page");
		captureScreenshot();
	}
}
