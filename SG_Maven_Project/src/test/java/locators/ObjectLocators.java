package locators;

public interface ObjectLocators {
	String obj_UserName_Edit="//input[@name='username']";
	String obj_Password_Edit="//input[@name='pwd']";
	String obj_Login_Link = "//a[@id='loginButton']";
	String obj_HomePage_Title_Label= "//td[@class='pagetitle']";
	String obj_GettingStartedShortcut_Window= "//div[@id='gettingStartedShortcutsMenuWrapper']";
	String obj_GettingStartedShortcut_Close_Btn= "//div[@id='gettingStartedShortcutsMenuCloseId']";
	String obj_Users_Menu= "//div[text()='USERS']";
	String obj_AddUser_Btn= "//div[text()='Add User']";
	String obj_FirstName_User_Edit= "//input[@name='firstName']";
	String obj_LastName_User_Edit= "//input[@name='lastName']";
	String obj_Email_User_Edit= "//input[@name='email']";
	String obj_UserName_User_Edit= "//input[@name='username']";
	String obj_Password_User_Edit= "//input[@name='password']";
	String obj_Retype_User_Edit= "//input[@name='passwordCopy']";
	String obj_CreateUser_Btn= "//span[text()='Create User']";
	String obj_UserTitle_Label= "//span[@id='userDataLightBox_userNamePlaceholder']";
	String obj_DeleteUser_Btn= "//button[contains(text(), 'Delete User')]";
	String obj_Logout_Link= "//a[@id='logoutLink']";
	String obj_Logo_LoginPage_Img= "//img[contains(@src, '/timer.png')]";
	String obj_Login_Header_Text = "//td[@id='headerContainer']";
	String obj_AddUser_Window_Header = "//span[text()='Add User']";
	String obj_ExploreActiTime_Btn = "//span[text()='Start exploring actiTIME']";
}
