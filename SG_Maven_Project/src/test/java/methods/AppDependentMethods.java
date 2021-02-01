package methods;

import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import driver.DriverScript;
import locators.ObjectLocators;

public class AppDependentMethods extends DriverScript implements ObjectLocators{
	/*******************************************************
	 * Method Name	`	: navigateURL()
	 * Purpose			: to navigate the required URL
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, String URL
	 * Return Type		: boolean
	 ********************************************************/
	public boolean navigateURL(WebDriver oBrowser, String URL, ExtentTest test)
	{
		try {
			oBrowser.navigate().to(URL);
			appInd.waitFor(oBrowser, obj_UserName_Edit, "Clickable", "", 10);
			
			reports.writeResult(oBrowser, "screenshot", "Login Page", test);
			if(appInd.compareValue(oBrowser, oBrowser.getTitle(), "actiTIME - Login", test))
			{
				return true;
			}else {
				return false;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'navigateURL()' method. "+ e.getMessage(), test);
			return false;
		}
	}
	
	
	
	
	/*******************************************************
	 * Method Name	`	: loginToApplication()
	 * Purpose			: to login to the application
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, String UN, String PWD
	 * Return Type		: boolean
	 ********************************************************/
	public boolean loginToApplication(WebDriver oBrowser, String UN, String PWD, ExtentTest test)
	{
		String strStatus = null;
		try {
			strStatus+= appInd.setObject(oBrowser, obj_UserName_Edit, UN, test);
			strStatus+= appInd.setObject(oBrowser, obj_Password_Edit, PWD, test);
			strStatus+= appInd.clickObject(oBrowser, obj_Login_Link, test);
			appInd.waitFor(oBrowser, obj_HomePage_Title_Label, "Text", "Enter Time-Track", 10);
			
			//If newly created user is getting login then handle the screen
			if(appInd.verifyOptionalElement(oBrowser, obj_ExploreActiTime_Btn, test)) {
				strStatus+= appInd.clickObject(oBrowser, obj_ExploreActiTime_Btn, test);
				appInd.waitFor(oBrowser, obj_HomePage_Title_Label, "Text", "Enter Time-Track", 10);
			}
			
			strStatus+= appInd.verifyText(oBrowser, obj_HomePage_Title_Label, "Text", "Enter Time-Track", test);
			
			if(appInd.verifyOptionalElement(oBrowser, obj_GettingStartedShortcut_Window, test)) {
				strStatus+= appInd.clickObject(oBrowser, obj_GettingStartedShortcut_Close_Btn, test);
			}
			
			if(strStatus.contains("false")) {
				reports.writeResult(oBrowser, "Fail", "Failed to login to actiTime", test);
				return false;
			}else {
				reports.writeResult(oBrowser, "Pass", "Login to ActiTime was successful", test);
				reports.writeResult(oBrowser, "screenshot", "Login application successful", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'loginToApplication()' method. "+ e.getMessage(), test);
			return false;
		}
	}
	
	
	
	
	/*******************************************************
	 * Method Name	`	: logoutFromApplication()
	 * Purpose			: to logout from the application
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser
	 * Return Type		: boolean
	 ********************************************************/
	public boolean logoutFromApplication(WebDriver oBrowser, ExtentTest test)
	{
		String strStatus = null;
		try {
			strStatus+= appInd.clickObject(oBrowser, obj_Logout_Link, test);
			appInd.waitFor(oBrowser, obj_Login_Header_Text, "Text", "Please identify yourself", 10);
			
			strStatus+= appInd.verifyText(oBrowser, obj_Login_Header_Text, "Text", "Please identify yourself", test);
			reports.writeResult(oBrowser, "screenshot", "After  logout from application", test);
			
			if(strStatus.contains("false"))
			{
				reports.writeResult(oBrowser, "Fail", "Failed to logout from the actiTime application", test);
				return false;
			}else {
				reports.writeResult(oBrowser, "Pass", "The user was logout from the actiTime successful", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'logoutFromApplication()' method. "+ e.getMessage(), test);
			return false;
		}
	}
}
