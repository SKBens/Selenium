package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.beust.jcommander.ResourceBundle;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Utility {
	
	public static ExtentReports report;
	public static WebDriver driver;
	public static ExtentTest logger;
	
	
	public static void captureScreenshot() {
		try {
			String partialPath = getProperty("screenShotLoc") + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + ".png";
			String dest = System.getProperty("user.dir") + "//target//" + partialPath;
			TakesScreenshot scrShot =((TakesScreenshot)driver);
	        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
	        FileUtils.copyFile(SrcFile, new File(dest));
			logger.log(LogStatus.INFO, logger.addScreenCapture(partialPath));
			} catch (Exception e) {
			System.out.println("Exception while taking Screenshot" + e.getMessage());
		}
	}
	
	public static boolean waitUntilVisible(Integer time, By by) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}
	
	public static boolean waitUntilVisible(Integer time, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}
	
	public static void clickElement(By by) {
		try {
			driver.findElement(by).click();
		}catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(by));
			System.out.println("Exception Occured");
		}
	}
	
	public static void scrollToElement(WebDriver driver, By by) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(by));
	}
	
	public void verifyElement(WebElement element) {
		if(element.isDisplayed()) {
			logger.log(LogStatus.INFO, "Verified "+element.getText()+" Element");
		}
		else {
			logger.log(LogStatus.FAIL, "Unable to verify Element");
		}
	}
	
	public void verifyText(String str, String str2) {
		if(str.contains(str2)) {
			logger.log(LogStatus.INFO, "Verified Element Text");
		}
		else {
			logger.log(LogStatus.FAIL, "Unable to verify Element");
		}
	}
	
	public void hoverLink(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).perform();
	}
	
	public boolean waitForAlertPresent(WebDriver driver, int waitTime) {
		boolean flag = false;
		new WebDriverWait(driver, Duration.ofSeconds(waitTime)).ignoring(StaleElementReferenceException.class)
				.until(ExpectedConditions.alertIsPresent());
		try{
			driver.switchTo().alert();
			return flag = true;
		}catch(Exception Ex){
			return flag;
		}
	}
	
	public static void dropDownSelectionByIndex(WebDriver driver, WebElement element, String dropDownValue) {
		try {
			new WebDriverWait(driver, Duration.ofSeconds(5)).ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.elementToBeClickable(element));
			Select dropDown = new Select(element);
			List<WebElement> list = dropDown.getOptions();
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getText().contains(dropDownValue)) {
					dropDown.selectByIndex(i);
				}
			}			
		}
		catch (StaleElementReferenceException ex) {
			System.out.println("Exception while selecting a value from dropdown" + ex.getMessage());
		}
	}
	
	public static String getProperty(String key) {
		Properties prop = new Properties();
		try {
		    prop.load(new FileInputStream("src\\test\\resources\\application.properties"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return prop.get(key).toString();
	}
}
