package driver;

import java.lang.reflect.Method;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import methods.AppDependentMethods;
import methods.AppIndependentMethods;
import methods.Datatable;
import methods.TaskModuleMethods;
import methods.UserModuleMethods;
import reports.ReportUtils;

public class DriverScript {
	public static AppIndependentMethods appInd = null;
	public static AppDependentMethods appDep = null;
	public static UserModuleMethods userMethods = null;
	public static TaskModuleMethods taskMethods = null;
	public static Datatable datatable = null;
	public static ReportUtils reports = null;
	public static ExtentReports extent = null;
	public static ExtentTest test = null;
	public static String screenshotLocation = null;
	public static String strController = null;
	public static String moduleName = null;
	
	@BeforeSuite
	public void loadClasses()
	{
		try {
			appInd = new AppIndependentMethods();
			appDep = new AppDependentMethods();
			userMethods = new UserModuleMethods();
			taskMethods = new TaskModuleMethods();
			datatable = new Datatable();
			reports = new ReportUtils();
			strController = System.getProperty("user.dir") + "\\ExecutionController\\Controller.xlsx";
			extent = reports.startExtentReport("TestResult", appInd.readConfigData("BuildNumber"));
		}catch(Exception e)
		{
			System.out.println("Exception in 'loadClasses()' class. "+e.getMessage());
		}
	}
	
	
	
	@Test
	public void executeTest()
	{
		Class cls = null;
		Object obj = null;
		Method meth = null;
		String executeTest =  null;
		String scriptName = null;
		String className = null;
		try {
			int rows = datatable.getRowNumber(strController, "Runner");
			int count = 0;
			for(int i=0; i<rows; i++)
			{
				count++;
				executeTest = datatable.getCellData(strController, "Runner", "ExecuteTest", i+1);
				if(executeTest.equalsIgnoreCase("Yes")) {
					className = datatable.getCellData(strController, "Runner", "ClassName", i+1);
					scriptName = datatable.getCellData(strController, "Runner", "TestScriptName", i+1);
					moduleName = datatable.getCellData(strController, "Runner", "ModuleName", i+1);
					cls = Class.forName(className);
					obj = cls.newInstance();
					meth = obj.getClass().getMethod(scriptName);
					
					String status = String.valueOf(meth.invoke(obj));
					
					if(status.equals("true")) {
						datatable.setCellData(strController, "Runner", "Status", i+1, "Passed");
					}else {
						datatable.setCellData(strController, "Runner", "Status", i+1, "Passed");
					}
				}
			}
			
			if(count == 0) {
				System.out.println("No testscripts are selected for execution. Please select atleast one test script");
			}
		}catch(Exception e)
		{
			System.out.println("Exception in 'executeTest()' class. "+e.getMessage());
		}
		finally {
			cls = null;
			obj = null;
			meth = null;
		}
	}
}
