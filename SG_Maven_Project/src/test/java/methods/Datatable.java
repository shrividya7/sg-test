package methods;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.relevantcodes.extentreports.ExtentTest;
import driver.DriverScript;

public class Datatable extends DriverScript{
	
	/***********************************************************
	 * Method Name			: getTestDataFromExcel()
	 * Author Name			: 
	 * Purpose				: It is to read the test data fromt he excel file based on SheetName & logicalName
	 * Arguments			: String sheetName, String logicalName, ExtentTest test
	 * Return Type			: Map<String, String>
	 * 
	 ************************************************************/
	public Map<String, String> getTestDataFromExcel(String sheetName, String logicalName, ExtentTest test)
	{
		FileInputStream fin = null;
		Map<String, String> objData = null;
		Workbook wb = null;
		Sheet sh = null;
		Row rowKey = null;
		Row rowVal = null;
		Cell cellKey = null;
		Cell cellVal = null;
		int rowNum = 0;
		int colNum = 0;
		String strKey = null;
		String strValue = null;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		try {
			objData = new HashMap<String, String>();
			fin = new FileInputStream(System.getProperty("user.dir")+"\\TestData\\"+moduleName+".xlsx");
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh==null) {
				reports.writeResult(null, "Fail", "The sheet '"+sheetName+"' doesnot exist in the test data file", test);
				return null;
			}
			
			//Find out the rownum of the logicalname mentioned.
			int rows = sh.getPhysicalNumberOfRows();
			for(int r=0; r<rows; r++)
			{
				rowKey = sh.getRow(r);
				cellKey = rowKey.getCell(0);
				if(cellKey.getStringCellValue().equalsIgnoreCase(logicalName)) {
					rowNum = r;
					break;
				}
			}
			
			
			if(rowNum > 0) {
				rowKey = sh.getRow(0);
				rowVal = sh.getRow(rowNum);
				colNum = rowKey.getPhysicalNumberOfCells();
				for(int c = 0; c < colNum; c++)
				{
					cellKey = rowKey.getCell(c);
					cellVal = rowVal.getCell(c);
					strKey = cellKey.getStringCellValue();
					
					//We need to format the cell value in order to read the data
					if(cellVal == null || cellVal.getCellType()==CellType.BLANK)
					{
						strValue = "";
					}
					else if(cellVal.getCellType()==CellType.BOOLEAN) {
						strValue = String.valueOf(cellVal.getBooleanCellValue());
					}
					else if(cellVal.getCellType()==CellType.STRING) {
						strValue = cellVal.getStringCellValue();
					}
					else if(cellVal.getCellType()==CellType.NUMERIC) {
						//Check for date OR number
						if(DateUtil.isCellDateFormatted(cellVal)) {
							double dt = cellVal.getNumericCellValue();
							Calendar cal = Calendar.getInstance();
							cal.setTime(DateUtil.getJavaDate(dt));
							
							//if date is <10 then prefix with zero
							if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
								sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
							}else {
								sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
							}
							
							
							//if month is <10 then prefix with zero
							if((cal.get(Calendar.MONTH)+1) < 10) {
								sMonth = "0" + (cal.get(Calendar.MONTH)+1);
							}else {
								sMonth = String.valueOf((cal.get(Calendar.MONTH)+1));
							}
							
							
							sYear = String.valueOf(cal.get(Calendar.YEAR));
							strValue = sDay+"/"+sMonth+"/"+sYear;
						}else {
							strValue = String.valueOf(cellVal.getNumericCellValue());
						}
					}
					objData.put(strKey, strValue);
				}
				
				return objData;
			}else {
				reports.writeResult(null, "Fail", "The logicalName '"+logicalName+"' doesnot exist in the testdata excel file", test);
				return null;
			}
		}catch(Exception e)
		{
			reports.writeResult(null, "Exception", "Exception in the method getTestDataFromExcel(). "+e.getMessage(), test);
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cellVal = null;
				cellKey = null;
				rowKey = null;
				rowVal = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				reports.writeResult(null, "Exception", "Exception in the method getTestDataFromExcel(). "+e.getMessage(), test);
			}
		}
	}
	
	
	
	/*********************************************************
	 * Method Name		: getRowNumber()
	 * Purpose			: to get the used rows from the excel file
	 * Author			: 
	 * parameters		: String filePath, String sheetName
	 * return type		: int
	 ***********************************************************/
	public int getRowNumber(String filePath, String sheetName)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh == null) {
				System.out.println("The sheet '"+sheetName+"' doesnot exist");
				return -1;
			}
			
			return sh.getPhysicalNumberOfRows()-1;
		}catch(Exception e)
		{
			System.out.println("Exception in 'getRowNumber()' method. "+e.getMessage());
			return -1;
		}
		finally {
			try {
				fin.close();
				fin = null;
				sh = null;
				wb.close();
				wb = null;
			}catch(Exception e)
			{
				System.out.println("Exception in 'getRowNumber()' method. "+e.getMessage());
			}
		}
	}
	
	
	
	
	/*********************************************************
	 * Method Name		: getCellData()
	 * Purpose			: to get the required cell value from the excel file
	 * Author			: 
	 * parameters		: String filePath, String sheetName, String columnName, int rowNum
	 * return type		: String
	 ***********************************************************/
	public String getCellData(String filePath, String sheetName, String columnName, int rowNum)
	{
		FileInputStream fin = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		int colNum = 0;
		String strData = null;
		String sDay = null;
		String sMonth = null;
		String sYear = null;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh == null) {
				System.out.println("The sheet '"+sheetName+"' doesnot exist");
				return null;
			}
			
			//Find out column number based on column name
			row = sh.getRow(0);
			int columns = row.getPhysicalNumberOfCells();
			for(int c=0; c < columns; c++) {
				cell = row.getCell(c);
				
				if(cell.getStringCellValue().equalsIgnoreCase(columnName)) {
					colNum = c;
					break;
				}
			}
			
			
			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);
			
			//format the cell value and read them
			if(cell==null || cell.getCellType()==CellType.BLANK) {
				strData = "";
			}
			else if(cell.getCellType()==CellType.BOOLEAN) {
				strData = String.valueOf(cell.getBooleanCellValue());
			}
			else if(cell.getCellType()==CellType.STRING) {
				strData = cell.getStringCellValue();
			}
			else if(cell.getCellType()==CellType.NUMERIC) {
				if(DateUtil.isCellDateFormatted(cell) == true) {
					double dt = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.getJavaDate(dt));
					
					//If day is less than 10 then prefix with zero
					if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
						sDay = "0" + cal.get(Calendar.DAY_OF_MONTH);
					}else {
						sDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
					}
					
					
					//If month is less than 10 then prefix with zero
					if((cal.get(Calendar.DAY_OF_MONTH)+1) < 10) {
						sMonth = "0" + (cal.get(Calendar.DAY_OF_MONTH)+1);
					}else {
						sMonth = String.valueOf((cal.get(Calendar.DAY_OF_MONTH)+1));
					}
					
					sYear = String.valueOf(cal.get(Calendar.YEAR));
					
					strData = sDay +"/"+ sMonth +"/"+ sYear;
				}else {
					strData = String.valueOf(cell.getNumericCellValue());
				}
			}
			
			return strData;
		}catch(Exception e)
		{
			System.out.println("Exception in 'getCellData()' method. "+e.getMessage());
			return null;
		}
		finally
		{
			try {
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
				strData = null;
				sDay = null;
				sMonth = null;
				sYear = null;
			}catch(Exception e)
			{
				System.out.println("Exception in 'getCellData()' method. "+e.getMessage());
				return null;
			}
		}
	}
	
	
	
	
	
	/*********************************************************
	 * Method Name		: setCellData()
	 * Purpose			: to write to the required cell
	 * Author			: 
	 * parameters		: String filePath, String sheetName, String columnName, int rowNum, String strData
	 * return type		: void
	 ***********************************************************/
	public void setCellData(String filePath, String sheetName, String columnName, int rowNum, String strData)
	{
		FileInputStream fin = null;
		FileOutputStream fout = null;
		Workbook wb = null;
		Sheet sh = null;
		Row row = null;
		Cell cell = null;
		int colNum = 0;
		try {
			fin = new FileInputStream(filePath);
			wb = new XSSFWorkbook(fin);
			sh = wb.getSheet(sheetName);
			
			if(sh == null) {
				System.out.println("The sheet '"+sheetName+"' doesnot exist");
				return;
			}
			
			//Find out column number based on column name
			row = sh.getRow(0);
			int columns = row.getPhysicalNumberOfCells();
			for(int c=0; c < columns; c++) {
				cell = row.getCell(c);
				
				if(cell.getStringCellValue().equalsIgnoreCase(columnName)) {
					colNum = c;
					break;
				}
			}
			
			
			row = sh.getRow(rowNum);
			cell = row.getCell(colNum);
			
			if(row.getCell(colNum) == null) {
				cell = row.createCell(colNum);
			}
			
			cell.setCellValue(strData);
			
			fout = new FileOutputStream(filePath);
			wb.write(fout);
			
		}catch(Exception e)
		{
			System.out.println("Exception in 'setCellData()' method. "+e.getMessage());
		}
		finally
		{
			try {
				fout.flush();
				fout.close();
				fout = null;
				fin.close();
				fin = null;
				cell = null;
				row = null;
				sh = null;
				wb.close();
				wb = null;
				strData = null;
			}catch(Exception e)
			{
				System.out.println("Exception in 'setCellData()' method. "+e.getMessage());
			}
		}
	}
}
