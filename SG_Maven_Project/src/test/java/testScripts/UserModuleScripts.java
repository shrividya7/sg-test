package testScripts;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import driver.DriverScript;

public class UserModuleScripts extends DriverScript{
	/***********************************************************
	 * Script Name			: TS_LoginLogout()
	 * Author Name			: 
	 * Purpose				: Automated the test case user_101
	 * Arguments			: NA
	 * Return Type			: boolean
	 * 
	 ************************************************************/
	public boolean TS_LoginLogout()
	{
		WebDriver oBrowser = null;
		Map<String, String> objData = null;
		String strStatus = null;
		try {
			test = extent.startTest("TS_LoginLogout");
			
			objData = datatable.getTestDataFromExcel("userModule", "user_101", test);
			
			oBrowser = appInd.launchBrowser(objData.get("Browser"), test);
			
			strStatus+= appDep.navigateURL(oBrowser, appInd.readConfigData("URL"), test);
			
			strStatus+= appDep.loginToApplication(oBrowser, objData.get("UserName"), objData.get("Password"), test);
			
			strStatus+= appDep.logoutFromApplication(oBrowser, test);
			
			if(strStatus.contains("false")) {
				reports.writeResult(oBrowser, "Fail", "The test script 'TS_LoginLogout()' failed", test);
				return false;
			}else {
				reports.writeResult(oBrowser, "Pass", "The test script 'TS_LoginLogout()' passed", test);
				return true;
			}
			
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'TS_LoginLogout()' script", test);
			return false;
		}
		finally {
			appInd.closeBrowser(oBrowser, test);
			reports.endExtentReport(test);
			objData = null;
		}
	}
	
	
	
	
	/***********************************************************
	 * Script Name			: TS_Create_Delete_User()
	 * Author Name			: 
	 * Purpose				: Automated the test case user_102
	 * Arguments			: NA
	 * Return Type			: boolean
	 * 
	 ************************************************************/
	public boolean TS_Create_Delete_User()
	{
		WebDriver oBrowser = null;
		Map<String, String> objData = null;
		String strStatus = null;
		String strUserName = null;
		try {
			test = extent.startTest("TS_Create_Delete_User");
			
			objData = datatable.getTestDataFromExcel("userModule", "user_102", test);
			
			oBrowser = appInd.launchBrowser(objData.get("Browser"), test);
			
			strStatus+= appDep.navigateURL(oBrowser, appInd.readConfigData("URL"), test);
			
			strStatus+= appDep.loginToApplication(oBrowser, objData.get("UserName"), objData.get("Password"), test);
			
			strUserName = userMethods.createUser(oBrowser, objData, test);
			
			strStatus+= userMethods.deleteUser(oBrowser, strUserName, test);
			
			strStatus+= appDep.logoutFromApplication(oBrowser, test);
			
			
			if(strStatus.contains("false")) {
				reports.writeResult(oBrowser, "Fail", "The test script 'TS_Create_Delete_User()' passed", test);
				return false;
			}else {
				reports.writeResult(oBrowser, "Pass", "The test script 'TS_Create_Delete_User()' passed", test);
				return true;
			}
			
		}catch(Exception e)
		{
			reports.writeResult(oBrowser, "Exception", "Exception in 'TS_Create_Delete_User()' script", test);
			return false;
		}
		finally {
			appInd.closeBrowser(oBrowser, test);
			reports.endExtentReport(test);
			objData = null;
		}
	}
	
	
	
	
	
	/***********************************************************
	 * Script Name			: TS_CreateUser_LoginWithNewUser_Delete_User()
	 * Author Name			: 
	 * Purpose				: Automated the test case user_103
	 * Arguments			: NA
	 * Return Type			: boolean
	 * 
	 ************************************************************/
	public boolean TS_CreateUser_LoginWithNewUser_Delete_User()
	{
		WebDriver oBrowser1 = null;
		WebDriver oBrowser2 = null;
		Map<String, String> objData = null;
		String strStatus = null;
		String strUserName = null;
		boolean blnRes = false;
		try {
			test = extent.startTest("TS_CreateUser_LoginWithNewUser_Delete_User");
			
			objData = datatable.getTestDataFromExcel("userModule", "user_103", test);
			
			oBrowser1 = appInd.launchBrowser(objData.get("Browser"), test);
			
			strStatus+= appDep.navigateURL(oBrowser1, appInd.readConfigData("URL"), test);
			
			strStatus+= appDep.loginToApplication(oBrowser1, objData.get("UserName"), objData.get("Password"), test);
			
			strUserName = userMethods.createUser(oBrowser1, objData, test);
			
			
			//Login to application with newly created user.
			oBrowser2 = appInd.launchBrowser(objData.get("Browser"), test);
			
			strStatus+= appDep.navigateURL(oBrowser2, appInd.readConfigData("URL"), test);
			
			blnRes = appDep.loginToApplication(oBrowser2, objData.get("User_UN"), objData.get("User_PWD"), test);
			if(blnRes) {
				reports.writeResult(oBrowser2, "Pass", "Login with newly created user is successfufl", test);
			}
			
			strStatus+= appInd.closeBrowser(oBrowser2, test);
			
			strStatus+= userMethods.deleteUser(oBrowser1, strUserName, test);
			
			strStatus+= appDep.logoutFromApplication(oBrowser1, test);
			
			
			if(strStatus.contains("false")) {
				reports.writeResult(oBrowser1, "Fail", "The test script 'TS_CreateUser_LoginWithNewUser_Delete_User()' passed", test);
				return false;
			}else {
				reports.writeResult(oBrowser1, "Pass", "The test script 'TS_CreateUser_LoginWithNewUser_Delete_User()' passed", test);
				return true;
			}
			
		}catch(Exception e)
		{
			reports.writeResult(oBrowser1, "Exception", "Exception in 'TS_CreateUser_LoginWithNewUser_Delete_User()' script", test);
			return false;
		}
		finally {
			strStatus+= appInd.closeBrowser(oBrowser1, test);
			reports.endExtentReport(test);
			objData = null;
		}
	}
}
