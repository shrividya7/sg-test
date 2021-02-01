package methods;

import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import driver.DriverScript;
import locators.ObjectLocators;

public class UserModuleMethods extends DriverScript implements ObjectLocators{
	/*******************************************************
	 * Method Name	`	: createUser()
	 * Purpose			: to create the new user
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, Map<String, String> data
	 * Return Type		: String
	 ********************************************************/
	public String createUser(WebDriver oBrowser, Map<String, String> data, ExtentTest test)
	{
		String userName = null;
		String strStatus = null;
		try {
			
			strStatus+= appInd.clickObject(oBrowser, obj_Users_Menu, test);
			appInd.waitFor(oBrowser, obj_AddUser_Btn, "Clickable", "", 10);
			strStatus+= appInd.clickObject(oBrowser, obj_AddUser_Btn, test);
			appInd.waitFor(oBrowser, obj_FirstName_User_Edit, "Clickable", "", 10);
			
			reports.writeResult(oBrowser, "screenshot", "Create  User screen", test);
			if(appInd.verifyElementExist(oBrowser, obj_AddUser_Window_Header, test)) {
				strStatus+= appInd.setObject(oBrowser, obj_FirstName_User_Edit, data.get("FirstName"), test);
				strStatus+= appInd.setObject(oBrowser, obj_LastName_User_Edit, data.get("LastName"), test);
				strStatus+= appInd.setObject(oBrowser, obj_Email_User_Edit, data.get("Email"), test);
				strStatus+= appInd.setObject(oBrowser, obj_UserName_User_Edit, data.get("User_UN"), test);
				strStatus+= appInd.setObject(oBrowser, obj_Password_User_Edit, data.get("User_PWD"), test);
				strStatus+= appInd.setObject(oBrowser, obj_Retype_User_Edit, data.get("User_ReType"), test);
				
				strStatus+= appInd.clickObject(oBrowser, obj_CreateUser_Btn, test);
				userName = data.get("LastName")+", "+data.get("FirstName");
				
				appInd.waitFor(oBrowser, By.xpath("//div[@class='name']/span[text()="+"'"+userName+"'"+"]"), "Visible", "", 10);
								
				strStatus+= appInd.verifyElementExist(oBrowser, By.xpath("//div[@class='name']/span[text()="+"'"+userName+"'"+"]"), test);
				
				if(strStatus.contains("false"))
				{
					reports.writeResult(oBrowser, "Fail", "Failed to create the new user", test);
					return null;
				}else {
					reports.writeResult(oBrowser, "screenshot", "After creating the user", test);
					reports.writeResult(oBrowser, "Pass", "The user was created successful", test);
					return userName;
				}
			}else {
				reports.writeResult(oBrowser, "Fail", "Failed to open the 'Add User' page.", test);
				return null;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'createUser()' method. "+ e.getMessage(), test);
			return null;
		}
	}
	
	
	
	/*******************************************************
	 * Method Name	`	: deleteUser()
	 * Purpose			: to delete the user
	 * Author			:
	 * Reviewer			:
	 * Date creation	:
	 * Date Modified	:
	 * Modified By		:
	 * Parameters		: WebDriver oBrowser, String userName
	 * Return Type		: boolean
	 ********************************************************/
	public boolean deleteUser(WebDriver oBrowser, String userName, ExtentTest test)
	{
		String strStatus = null;
		try {
			strStatus+= appInd.clickObject(oBrowser, By.xpath("//div[@class='name']/span[text()="+"'"+userName+"'"+"]"), test);
			appInd.waitFor(oBrowser, obj_DeleteUser_Btn, "Clickable", "", 10);
			
			reports.writeResult(oBrowser, "screenshot", "Befire deleting user", test);
			
			strStatus+= appInd.clickObject(oBrowser, obj_DeleteUser_Btn, test);
			Thread.sleep(1000);
			oBrowser.switchTo().alert().accept();
			Thread.sleep(1000);
			
			strStatus+= appInd.verifyElementNotExist(oBrowser, By.xpath("//div[@class='name']/span[text()="+"'"+userName+"'"+"]"), test);
			
			if(strStatus.contains("false")) {
				reports.writeResult(oBrowser, "Fail", "Failed to delete the user", test);
				return false;
			}else {
				reports.writeResult(oBrowser, "screenshot", "After deleting the user", test);
				reports.writeResult(oBrowser, "Pass", "The user was deleted successful", test);
				return true;
			}
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'deleteUser()' method. "+ e.getMessage(), test);
			return false;
		}
	}
}
