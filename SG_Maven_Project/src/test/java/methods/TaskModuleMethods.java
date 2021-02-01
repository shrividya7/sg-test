package methods;

import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentTest;
import driver.DriverScript;
import locators.ObjectLocators;

public class TaskModuleMethods extends DriverScript implements ObjectLocators{


	
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
}
