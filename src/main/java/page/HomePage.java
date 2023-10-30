package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.concurrent.ThreadLocalRandom;

import com.relevantcodes.extentreports.LogStatus;

import utils.Utility;

public class HomePage extends Utility {
	
	@FindBy(how = How.ID, using = "button-cart")
	private WebElement addToCart;
	
	@FindBy(how = How.XPATH, using = "//div[@id='product']/div[contains(.,'Radio')]//input")
	private WebElement radioBtn;
	
	@FindBy(how = How.XPATH, using = "(//div[@id='product']/div[contains(.,'Checkbox')]//input)[1]")
	private WebElement checkBox;
	
	@FindBy(how = How.XPATH, using = "//div[@id='product']/div[contains(.,'Text')]//input")
	private WebElement textBox;
	
	@FindBy(how = How.XPATH, using = "//div[@id='product']/div[contains(.,'Select')]//select")
	private WebElement dropDown;
	
	@FindBy(how = How.XPATH, using = "//div[@id='product']/div[contains(.,'Textarea')]//textarea")
	private WebElement textArea;
	
	@FindBy(how = How.XPATH, using = "//div[@id='product']/div[contains(.,'File')]//button")
	private WebElement file;
	
	@FindBy(how = How.PARTIAL_LINK_TEXT, using = "View Cart")
	private WebElement viewCart;
	
	@FindBy(how = How.XPATH, using = "//span[contains(.,'item(s)')]")
	private WebElement cartItems;
	
	@FindBy(how = How.XPATH, using = "//h1[contains(.,'Shopping Cart')]")
	private WebElement shoppingCartHeader;
	
	@FindBy(how = How.LINK_TEXT, using = "Edit Account")
	private WebElement editAccount;
	
	@FindBy(how = How.XPATH, using = "//input[@value='Continue']")
	private WebElement continueBtn;
	
	@FindBy(how = How.NAME, using = "lastname")
	private WebElement lastName;
	
	@FindBy(how = How.LINK_TEXT, using = "wish list")
	private WebElement wishListLnk;
	
	@FindBy(how = How.XPATH, using = "//td[@class='text-left']//a[contains(@href,'product_id')]")
	private WebElement productName;

	@FindBy(how = How.XPATH, using = "//a[contains(@href,'remove')]")
	private WebElement removeWishList;
	
	@FindBy(how = How.XPATH, using = "//h4[.='iMac']/../..//button[@data-original-title='Add to Wish List']")
	private WebElement wishListBtn;

	public HomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void clickSubMenuLink(String parentMenu, String subMenu) {
		By parentLoc = By.partialLinkText(parentMenu);
		By menuLoc = By.partialLinkText(subMenu);
		hoverLink(driver.findElement(parentLoc));
		clickElement(menuLoc);
		captureScreenshot();
		logger.log(LogStatus.INFO, "Menu Clicked");
	}

	public void selectProduct(String product) {
		By productLoc = By.partialLinkText("Apple Cinema 30");
		waitUntilVisible(10, productLoc);
		clickElement(productLoc);
		logger.log(LogStatus.INFO, "Product selected");
	}

	public void provideProductDetails() {
		radioBtn.click();
		checkBox.click();
		textBox.sendKeys("Test");
		dropDownSelectionByIndex(driver, dropDown, "Green");
		textArea.sendKeys("TextArea");
		file.click();
		uploadFile();
		waitForAlertPresent(driver, 5);
		driver.switchTo().alert().accept();
		captureScreenshot();
	}

	private void uploadFile() {
		Robot robot;
		try {
			robot = new Robot();
			String filePath = System.getProperty("user.dir")+"\\src\\test\\resources\\assign1.png";
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	        StringSelection stringSelection = new StringSelection(filePath);
	        clipboard.setContents(stringSelection, null);
		    robot.keyPress(KeyEvent.VK_CONTROL);
		    robot.keyPress(KeyEvent.VK_V);
		    robot.keyRelease(KeyEvent.VK_V);
		    robot.keyRelease(KeyEvent.VK_CONTROL);
		    robot.keyPress(KeyEvent.VK_ENTER);
		    robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public void verifyProductDetailsPage() {
		waitUntilVisible(10, addToCart);
		verifyElement(addToCart);
		logger.log(LogStatus.INFO, "Verified product details page");
		captureScreenshot();
	}

	public void verifyMessage(String message) {
		By messageLoc = By.xpath("//div[contains(.,'"+message+"') and contains(@class,'alert')]");
		waitUntilVisible(10, messageLoc);
		verifyElement(driver.findElement(messageLoc));
		logger.log(LogStatus.INFO, "Verified message : - "+message);
		captureScreenshot();
	}

	public void goToCartAndVerifyProduct() {
		cartItems.click();
		viewCart.click();
		verifyElement(shoppingCartHeader);
	}

	public void clickOnAddToCart() {
		waitUntilVisible(10, addToCart);
		addToCart.click();
		logger.log(LogStatus.INFO, "Clicked on add to cart");
	}

	public void updateLastName() {
		waitUntilVisible(10, editAccount);
		editAccount.click();
		waitUntilVisible(10, lastName);
		lastName.sendKeys("TestData" + ThreadLocalRandom.current().nextInt(20));
		captureScreenshot();
		continueBtn.click();
	}

	public void addProductToWishList() {
		wishListBtn.click();
		verifyMessage("Success: You have added");
		wishListLnk.click();;
		captureScreenshot();
	}

	public void verifyTheProduct(String string) {
		String productNameText = productName.getText();
		verifyText(string, productNameText);
	}
}
