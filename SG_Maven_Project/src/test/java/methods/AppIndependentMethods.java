package methods;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;

import driver.DriverScript;

public class AppIndependentMethods extends DriverScript{
	/***********************************************************
	 * Method Name			: readConfigData()
	 * Author Name			: 
	 * Purpose				: IT is to read the global test data from the /Configuration/config.properties file
	 * Arguments			: String strKey
	 * Return Type			: String
	 * 
	 ************************************************************/
	public String readConfigData(String strKey)
	{
		FileInputStream fin = null;
		Properties prop = null;
		try {
			fin = new FileInputStream(System.getProperty("user.dir")+"\\Configuration\\Config.properties");
			prop = new Properties();
			
			prop.load(fin);
			
			return prop.getProperty(strKey);
		}catch(Exception e)
		{
			System.out.println("Exception in 'readConfigData()' method. "+ e.getMessage());
			return null;
		}
		finally {
			try {
				fin.close();
				fin = null;
				prop = null;
			}catch(Exception e)
			{
				System.out.println("Exception in 'readConfigData()' method. "+ e.getMessage());
				return null;
			}
		}
	}
	
	
	
	/***********************************************************
	 * Method Name			: getDateTime()
	 * Author Name			: 
	 * Purpose				: It is to get the current date in required format
	 * Arguments			: String strDateFormat
	 * Return Type			: String
	 * 
	 ************************************************************/
	public String getDateTime(String strDateFormat)
	{
		Date dt = null;
		SimpleDateFormat sdf = null;
		try {
			dt = new Date();
			sdf = new SimpleDateFormat(strDateFormat);
			return sdf.format(dt);
		}catch(Exception e)
		{
			System.out.println("Exception in 'getDateTime()' method. "+ e.getMessage());
			return null;
		}
		finally
		{
			dt = null;
			sdf = null;
		}
	}
	
	
	
	/*******************************************************
	 * Method Name	`	: launchBrowser()
	 * Purpose			: to open the browser
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: String browserName, ExtentTest test
	 * Return Type		: WebDriver
	 ********************************************************/
	public WebDriver launchBrowser(String browserName, ExtentTest test)
	{
		WebDriver oBrowser = null;
		try {
			switch(browserName.toLowerCase())
			{
				case "chrome":
					System.setProperty("webdriver.chrome.driver", ".\\Library\\drivers\\chromedriver.exe");
					oBrowser = new ChromeDriver();
					break;
				case "firefox":
					System.setProperty("webdriver.gecko.driver", ".\\Library\\drivers\\geckodriver.exe");
					oBrowser = new FirefoxDriver();
					break;
				case "ie":
					System.setProperty("webdriver.ie.driver", ".\\Library\\drivers\\IEDriverServer_64.exe");
					oBrowser = new InternetExplorerDriver();
					break;
				default:
					reports.writeResult(null, "Fail", "Invalid browser type '"+browserName+"' specified.", test);
			}
			
			if(oBrowser != null) {
				reports.writeResult(oBrowser, "Pass", "The '"+browserName+"' browser has launched successful", test);
				oBrowser.manage().window().maximize();
				return oBrowser;
			}else {
				reports.writeResult(null, "Fail", "Failed to launch the '"+browserName+"' browser", test);
				return null;
			}
			
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exception in 'launchBrowser()' method. "+ e.getMessage(), test);
			return null;
		}
	}
	
	
	
	
	
	/*******************************************************
	 * Method Name	`	: closeBrowser()
	 * Purpose			: to close the browser instance
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, ExtentTest test
	 * Return Type		: boolean
	 ********************************************************/
	public boolean closeBrowser(WebDriver oBrowser, ExtentTest test)
	{
		try {
			oBrowser.close();
			return true;
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exception in 'closeBrowser()' method. "+ e.getMessage(), test);
			return false;
		}
		finally {
			oBrowser = null;
		}
	}
	
	
	
	/*******************************************************
	 * Method Name	`	: clickObject()
	 * Purpose			: to click on the webelement
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, By objBy, ExtentTest test
	 * Return Type		: boolean
	 ********************************************************/
	public boolean clickObject(WebDriver oBrowser, By objBy, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(objBy);
			
			if(oEles.size() > 0) {
				oEles.get(0).click();
				reports.writeResult(oBrowser, "Pass", "The element '"+String.valueOf(objBy)+"' was clicked successful", test);
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM.", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'clickObject()' method. "+ e.getMessage(), test);
			return false;
		}
		finally{
			oEles = null;
		}
	}
	
	
	
	
	/*******************************************************
	 * Method Name	`	: clickObject()
	 * Purpose			: to click on the webelement
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, String strObjectName, ExtentTest test
	 * Return Type		: boolean
	 ********************************************************/
	public boolean clickObject(WebDriver oBrowser, String strObjectName, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(By.xpath(strObjectName));
			
			if(oEles.size() > 0) {
				oEles.get(0).click();
				reports.writeResult(oBrowser, "Pass", "The element '"+strObjectName+"' was clicked successful", test);
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+strObjectName+"' was not found in the DOM.", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'clickObject()' method. "+ e.getMessage(), test);
			return false;
		}
		finally{
			oEles = null;
		}
	}
	
	
	/*******************************************************
	 * Method Name	`	: setObject()
	 * Purpose			: to enter the value on the webelement
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, By objBy, String strData, ExtentTest test
	 * Return Type		: boolean
	 ********************************************************/
	public boolean setObject(WebDriver oBrowser, By objBy, String strData, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				oEles.get(0).sendKeys(strData);
				reports.writeResult(oBrowser, "Pass", "The data '"+strData+"' was entered in the element '"+String.valueOf(objBy)+"' successful", test);
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM.", test);
				return false;
			}
			
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'setObject()' method. "+ e.getMessage(), test);
			return false;
		}
		finally{
			oEles = null;
		}
	}
	
	
	
	/*******************************************************
	 * Method Name	`	: setObject()
	 * Purpose			: to enter the value on the webelement
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, String strObjectName, String strData, ExtentTest test
	 * Return Type		: boolean
	 ********************************************************/
	public boolean setObject(WebDriver oBrowser, String strObjectName, String strData, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(By.xpath(strObjectName));
			if(oEles.size() > 0) {
				oEles.get(0).sendKeys(strData);
				reports.writeResult(oBrowser, "Pass", "The data '"+strData+"' was entered in the element '"+strObjectName+"' successful", test);
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+strObjectName+"' was not found in the DOM.", test);
				return false;
			}
			
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'setObject()' method. "+ e.getMessage(), test);
			return false;
		}
		finally{
			oEles = null;
		}
	}
	
	
	
	/*******************************************************
	 * Method Name	`	: clearAndSetObject()
	 * Purpose			: to enter the value on the webelement
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, By objBy, String strData, ExtentTest test
	 * Return Type		: boolean
	 ********************************************************/
	public boolean clearAndSetObject(WebDriver oBrowser, By objBy, String strData, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(objBy);
			if(oEles.size() > 0) {
				oEles.get(0).clear();
				oEles.get(0).sendKeys(strData);
				reports.writeResult(oBrowser, "Pass", "The data '"+strData+"' was entered in the element '"+String.valueOf(objBy)+"' successful", test);
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM.", test);
				return false;
			}
			
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'clearAndSetObject()' method. "+ e.getMessage(), test);
			return false;
		}
		finally{
			oEles = null;
		}
	}
	
	
	
	
	/*******************************************************
	 * Method Name	`	: clearAndSetObject()
	 * Purpose			: to enter the value on the webelement
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, String strObjectName, String strData, ExtentTest test
	 * Return Type		: boolean
	 ********************************************************/
	public boolean clearAndSetObject(WebDriver oBrowser, String strObjectName, String strData, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(By.xpath(strObjectName));
			if(oEles.size() > 0) {
				oEles.get(0).clear();
				oEles.get(0).sendKeys(strData);
				reports.writeResult(oBrowser, "Pass", "The data '"+strData+"' was entered in the element '"+strObjectName+"' successful", test);
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+strObjectName+"' was not found in the DOM.", test);
				return false;
			}
			
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'clearAndSetObject()' method. "+ e.getMessage(), test);
			return false;
		}
		finally{
			oEles = null;
		}
	}
	
	
	
	
	
	/*******************************************************
	 * Method Name	`	: compareValue()
	 * Purpose			: to compare the both actual and expected values
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, String actual, String expected, ExtentTest test
	 * Return Type		: boolean
	 ********************************************************/
	public boolean compareValue(WebDriver oBrowser, String actual, String expected, ExtentTest test)
	{
		try {
			if(actual.equalsIgnoreCase(expected)) {
				reports.writeResult(oBrowser, "Pass", "The actual '"+actual+"' & expected '"+expected+"' values are matching", test);
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "Mis-match in the both actual '"+actual+"' & expected '"+expected+"' values", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'compareValue()' method. "+ e.getMessage(), test);
			return false;
		}
	}
	
	
	
	
	/*******************************************************
	 * Method Name	`	: verifyText()
	 * Purpose			: to validate the text present on the element with expected values
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, By objBy, String objectType, String expected, ExtentTest test
	 * Return Type		: boolean
	 ********************************************************/
	public boolean verifyText(WebDriver oBrowser, By objBy, String objectType, String expected, ExtentTest test)
	{
		List<WebElement> oEles = null;
		Select oSel = null;
		String actual = null;
		try {
			oEles = oBrowser.findElements(objBy);
			
			if(oEles.size() > 0) {
				switch(objectType.toLowerCase()) {
					case "text":
						actual = oEles.get(0).getText();
						break;
					case "value":
						actual = oEles.get(0).getAttribute("value");
						break;
					case "dropdown":
						oSel = new Select(oEles.get(0));
						actual = oSel.getFirstSelectedOption().getText();
						break;
					default:
						reports.writeResult(oBrowser, "Fail", "Invalid object type '"+objectType+"' was specified", test);
				}
				
				if(appInd.compareValue(oBrowser, actual, expected, test)) {
					return true;
				}else {
					return false;
				}
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+String.valueOf(objBy)+"' was not found in the DOM.", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'verifyText()' method. "+ e.getMessage(), test);
			return false;
		}
		finally{
			oEles = null;
			oSel = null;
		}
	}
	
	
	
	
	
	/*******************************************************
	 * Method Name	`	: verifyText()
	 * Purpose			: to validate the text present on the element with expected values
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, String strObjectName, String objectType, String expected, ExtentTest test
	 * Return Type		: boolean
	 ********************************************************/
	public boolean verifyText(WebDriver oBrowser, String strObjectName, String objectType, String expected, ExtentTest test)
	{
		List<WebElement> oEles = null;
		Select oSel = null;
		String actual = null;
		try {
			oEles = oBrowser.findElements(By.xpath(strObjectName));
			
			if(oEles.size() > 0) {
				switch(objectType.toLowerCase()) {
					case "text":
						actual = oEles.get(0).getText();
						break;
					case "value":
						actual = oEles.get(0).getAttribute("value");
						break;
					case "dropdown":
						oSel = new Select(oEles.get(0));
						actual = oSel.getFirstSelectedOption().getText();
						break;
					default:
						reports.writeResult(oBrowser, "Fail", "Invalid object type '"+objectType+"' was specified", test);
				}
				
				if(appInd.compareValue(oBrowser, actual, expected, test)) {
					return true;
				}else {
					return false;
				}
			}else {
				reports.writeResult(oBrowser, "Fail", "The element '"+strObjectName+"' was not found in the DOM.", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'verifyText()' method. "+ e.getMessage(), test);
			return false;
		}
		finally{
			oEles = null;
			oSel = null;
		}
	}
	
	
	
	
	
	
	
	/*******************************************************
	 * Method Name	`	: verifyElementExist()
	 * Purpose			: to validate the presence of the webelement on the DOM
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, By objBy, ExtentTest test
	 * Return Type		: boolean
	 ********************************************************/
	public boolean verifyElementExist(WebDriver oBrowser, By objBy, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(objBy);
			
			if(oEles.size() > 0) {
				reports.writeResult(oBrowser, "Pass", "The webelement '"+String.valueOf(objBy)+"' was present on the DOM", test);
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "Failed to find the webelement '"+String.valueOf(objBy)+"' on the DOM", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'verifyElementExist()' method. "+ e.getMessage(), test);
			return false;
		}
		finally{
			oEles = null;
		}
	}
	
	
	
	
	/*******************************************************
	 * Method Name	`	: verifyElementExist()
	 * Purpose			: to validate the presence of the webelement on the DOM
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, String strObjectName, ExtentTest test
	 * Return Type		: boolean
	 ********************************************************/
	public boolean verifyElementExist(WebDriver oBrowser, String strObjectName, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(By.xpath(strObjectName));
			
			if(oEles.size() > 0) {
				reports.writeResult(oBrowser, "Pass", "The webelement '"+strObjectName+"' was present on the DOM", test);
				return true;
			}else {
				reports.writeResult(oBrowser, "Fail", "Failed to find the webelement '"+strObjectName+"' on the DOM", test);
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'verifyElementExist()' method. "+ e.getMessage(), test);
			return false;
		}
		finally{
			oEles = null;
		}
	}
	
	
	
	
	
	/*******************************************************
	 * Method Name	`	: verifyElementNotExist()
	 * Purpose			: to validate the non-presence of the webelement on the DOM
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, By objBy, ExtentTest test
	 * Return Type		: boolean
	 ********************************************************/
	public boolean verifyElementNotExist(WebDriver oBrowser, By objBy, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(objBy);
			
			if(oEles.size() > 0) {
				reports.writeResult(oBrowser, "Fail", "The webelement '"+String.valueOf(objBy)+"' was still present on the DOM", test);
				return false;
			}else {
				reports.writeResult(oBrowser, "Pass", "The webelement '"+String.valueOf(objBy)+"' was removed from the DOM", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'verifyElementNotExist()' method. "+ e.getMessage(), test);
			return false;
		}
		finally{
			oEles = null;
		}
	}
	
	
	
	
	/*******************************************************
	 * Method Name	`	: verifyElementNotExist()
	 * Purpose			: to validate the non-presence of the webelement on the DOM
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, String strObjectName, ExtentTest test
	 * Return Type		: boolean
	 ********************************************************/
	public boolean verifyElementNotExist(WebDriver oBrowser, String strObjectName, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(By.xpath(strObjectName));
			
			if(oEles.size() > 0) {
				reports.writeResult(oBrowser, "Fail", "The webelement '"+strObjectName+"' was still present on the DOM", test);
				return false;
			}else {
				reports.writeResult(oBrowser, "Pass", "The webelement '"+strObjectName+"' was removed from the DOM", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'verifyElementNotExist()' method. "+ e.getMessage(), test);
			return false;
		}
		finally{
			oEles = null;
		}
	}
	
	
	
	
	
	
	
	
	/*******************************************************
	 * Method Name	`	: verifyOptionalElement()
	 * Purpose			: to validate the presence of the optional webelement on the DOM
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, By objBy, ExtentTest test
	 * Return Type		: boolean
	 ********************************************************/
	public boolean verifyOptionalElement(WebDriver oBrowser, By objBy, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(objBy);
			
			if(oEles.size() > 0) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'verifyOptionalElement()' method. "+ e.getMessage(), test);
			return false;
		}
		finally{
			oEles = null;
		}
	}
	
	
	
	
	/*******************************************************
	 * Method Name	`	: verifyOptionalElement()
	 * Purpose			: to validate the presence of the optional webelement on the DOM
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, String strObjectName, ExtentTest test
	 * Return Type		: boolean
	 ********************************************************/
	public boolean verifyOptionalElement(WebDriver oBrowser, String strObjectName, ExtentTest test)
	{
		List<WebElement> oEles = null;
		try {
			oEles = oBrowser.findElements(By.xpath(strObjectName));
			
			if(oEles.size() > 0) {
				return true;
			}else {
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'verifyOptionalElement()' method. "+ e.getMessage(), test);
			return false;
		}
		finally{
			oEles = null;
		}
	}
	
	
	
	
	/*******************************************************
	 * Method Name	`	: waitFor()
	 * Purpose			: to wait for the DOM page elements to load appropriately
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, By objBy, String waitReason, String value, int timeOut
	 * Return Type		: boolean
	 ********************************************************/
	public boolean waitFor(WebDriver oBrowser, By objBy, String waitReason, String value, int timeOut)
	{
		WebDriverWait oWait = null;
		try {
			oWait = new WebDriverWait(oBrowser, timeOut);
			switch(waitReason.toLowerCase())
			{
				case "visible":
					oWait.until(ExpectedConditions.presenceOfElementLocated(objBy));
					break;
				case "clickable":
					oWait.until(ExpectedConditions.elementToBeClickable(objBy));
					break;
				case "text":
					oWait.until(ExpectedConditions.textToBePresentInElementLocated(objBy, value));
					break;
				case "value":
					oWait.until(ExpectedConditions.textToBePresentInElementValue(objBy, value));
					break;
				case "invisibility":
					oWait.until(ExpectedConditions.invisibilityOfElementLocated(objBy));
					break;
				default:
					System.out.println("Invalid condition '"+waitReason+"' for wait");
			}
			return true;
		}catch(Exception e)
		{
			System.out.println("Exception in 'waitFor()' method. "+ e.getMessage());
			return false;
		}
	}
	
	
	
	
	/*******************************************************
	 * Method Name	`	: waitFor()
	 * Purpose			: to wait for the DOM page elements to load appropriately
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, String strObjectName, String waitReason, String value, int timeOut
	 * Return Type		: boolean
	 ********************************************************/
	public boolean waitFor(WebDriver oBrowser, String strObjectName, String waitReason, String value, int timeOut)
	{
		WebDriverWait oWait = null;
		try {
			oWait = new WebDriverWait(oBrowser, timeOut);
			switch(waitReason.toLowerCase())
			{
				case "visible":
					oWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(strObjectName)));
					break;
				case "clickable":
					oWait.until(ExpectedConditions.elementToBeClickable(By.xpath(strObjectName)));
					break;
				case "text":
					oWait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(strObjectName), value));
					break;
				case "value":
					oWait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath(strObjectName), value));
					break;
				case "invisibility":
					oWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(strObjectName)));
					break;
				default:
					System.out.println("Invalid condition '"+waitReason+"' for wait");
			}
			return true;
		}catch(Exception e)
		{
			System.out.println("Exception in 'waitFor()' method. "+ e.getMessage());
			return false;
		}
		finally
		{
			try {
				Thread.sleep(1000);
			}catch(Exception e)
			{
				System.out.println("Exception in 'waitFor()' method. "+ e.getMessage());
				return false;
			}
		}
	}
}
