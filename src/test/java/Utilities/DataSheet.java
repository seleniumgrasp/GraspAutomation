/**
 *@UtilityComponentName: DataSheet
 *@Description:  
 *@author: 
 *@CreatedDate:
 *@ModifiedBy:
 *@ModifiedDate:
 *@param: 
 *@return:
 */
package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import exceptions.DataSheetException;
import exceptions.InvalidBrowserException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class DataSheet extends Excel{


	Logger logger = Log.getInstance(Thread.currentThread().getStackTrace()[1]
			.getClassName());
	/**
	 *@MethodName: M001-ValidateHeading
	 *@Description: This function checks the Browser name and Execution status in excel
	 *@author: 
	 *@CreatedDate: Dec5, 2014
	 *@ModifiedBy:
	 *@ModifiedDate:
	 *@param: destFile- This argument is for passing the location of the input data sheet
	 *@param: sheetname- This argument is for passing the sheet name in the input data sheet
	 *@return: This method checks for the headings in the sheet and returns true sheet
	 *@throws BiffException
	 *@throws IOException
	 */

	public boolean validateHeading(String destFile, String sheetname)
			throws BiffException, IOException {
		FileInputStream input = new FileInputStream(destFile);
		Workbook wb = Workbook.getWorkbook(input);

		Sheet sheet = wb.getSheet(sheetname);

		if ((sheet.getCell(0, 1).getContents().equalsIgnoreCase("BROWSER"))
				&& (sheet.getCell(0, 2).getContents()
						.equalsIgnoreCase("EXECUTION STATUS"))) {
			return true;

		}
		input.close();
		return false;

	}
	/**
	 *@MethodName: M002-GetColumnCount
	 *@Description:This Function is to get column count   
	 *@author: 
	 *@CreatedDate: Dec5, 2014
	 *@ModifiedBy:
	 *@ModifiedDate:
	 *@param: destFile- This argument is for passing the location of the input data sheet
	 *@param: sheetname- This argument is for passing the sheet name in the input data sheet
	 *@return: This method returns column count in the data sheet
	 */

	public int getColumnCount(String destFile, String sheetname)

	{
		int column = 0;
		try {

			FileInputStream input = new FileInputStream(destFile);

			Workbook wb = null;
			try {
				wb = Workbook.getWorkbook(input);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Sheet sheet = wb.getSheet(sheetname);

			column = sheet.getColumns();


			if (column != 0) {
				return column;
			} else {
				logger.error("The input data sheet is blank");
				throw new DataSheetException("The input data sheet is blank");


			}

		} catch (FileNotFoundException fe) {
			logger.error("Please provide a valid sheet path:" + destFile + " "
					+ "can not be found");
			throw new DataSheetException("Please provide a valid sheet path:" + destFile + " "
					+ "can not be found");
		} 
		catch(NullPointerException e){
			logger.error("No sheet found with the class name "+sheetname);
			throw new DataSheetException("No sheet found with the class name "+sheetname);
		}

	}
	/**
	 *@MethodName: M003-GetRowCount
	 *@Description:This Function is to get row count   
	 *@author: 
	 *@CreatedDate: Dec5, 2014
	 *@ModifiedBy:
	 *@ModifiedDate:
	 *@param: destFile- This argument is for passing the location of the input data sheet
	 *@param: sheetname- This argument is for passing the sheet name in the input data sheet
	 *@return: This method returns the row count in the sheet
	 *@throws BiffException
	 *@throws IOException
	 */

	public int getRowCount(String destFile, String sheetname)
			throws BiffException, IOException {
		FileInputStream input = new FileInputStream(destFile);
		Workbook wb = Workbook.getWorkbook(input);

		Sheet sheet = wb.getSheet(sheetname);
		int row = sheet.getRows();
		input.close();
		return row;

	}
	/**
	 *@MethodName: M004-GetValidRows
	 *@Description:This Function is to get row count   
	 *@author: 
	 *@CreatedDate: Dec5, 2014
	 *@ModifiedBy:
	 *@ModifiedDate:
	 *@param: destFile- This argument is for passing the location of the input data sheet
	 *@param: sheetname- This argument is for passing the sheet name in the input data sheet
	 *@return: This method returns the valid rows considering execution status
	 *@throws: BiffException
	 *@throws: IOException
	 *@throws: DataSheetException
	 */


	public int getValidRows(String destFile, String sheetname)
			throws BiffException, IOException, DataSheetException {
		FileInputStream input = new FileInputStream(destFile);
		Workbook wb = Workbook.getWorkbook(input);
		Sheet sheet = wb.getSheet(sheetname);
		getColumnCount(destFile, sheetname);
		int rows = getRowCount(destFile, sheetname);
		int count = 0;
		for (int row = 1; row < rows; row++) {

			if ((sheet.getCell(0, row).getContents().contains(","))) {
				if (sheet.getCell(1, row).getContents().trim().equalsIgnoreCase("Y")) {
					String values = sheet.getCell(0, row).getContents().trim();
					String[] cellarray = values.trim().split(",");
					count = cellarray.length + count;
				}
			}

			else {
				if (sheet.getCell(1, row).getContents().trim().equalsIgnoreCase("Y")) {
					count++;
				}
			}

		}
		input.close();
		return count;
	}
	/**
	 *@MethodName: M005-ReadFromSheet
	 *@Description:This Function is to read data from sheet
	 *@author: Chakradhar K
	 *@CreatedDate: April30 , 2015
	 *@ModifiedBy: Chakradhar K
	 *@ModifiedDate: June 06 , 2015
	 *@param: destFile- This argument is for passing the location of the input data sheet
	 *@param: sheetname- This argument is for passing the sheet name in the input data sheet
	 *@return: Returns data in Linked HashMap
	 */

	public LinkedHashMap<String, String> readFromSheet(String destFile, String sheetname, String testno, int columns)
			throws BiffException, IOException,
			DataSheetException, InvalidBrowserException {

		int rows = getRowCount(destFile, sheetname);
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		boolean headingStatus = validateHeading(destFile, sheetname);
		FileInputStream input = new FileInputStream(destFile);
		Workbook wb = Workbook.getWorkbook(input);
		Sheet sheet = wb.getSheet(sheetname);

		/* Validating for the heading status */
		if (headingStatus==true) {

			/* Iterates through each row in excel */
			for (int col = columns; col <columns+1; col++) 
			{
				System.out.println("Executing status is "+sheet.getCell(col, 2).getContents());

				for (int globalCount = 1; globalCount < rows; globalCount++)
				{
					dataMap.put(sheet.getCell(0, globalCount)
							.getContents(),
							sheet.getCell(col, globalCount)
							.getContents().trim());
				}


			}
		}

		else
		{
			logger.error("The sheet headings are invalid:The first and second column heading should be Browser and Execution Status respectively");
			throw new DataSheetException(
					"The sheet headings are invalid:The first and second column heading should be Browser and Execution Status respectively");
		}

		input.close();
		return dataMap;


	}

	/**
	 *@MethodName: M006-GetCellDataNumber
	 *@Description:This Function is to get column number based on first row data.
	 *@author: Chakradhar K
	 *@CreatedDate: April30 , 2015
	 *@ModifiedBy: Chakradhar K
	 *@ModifiedDate: June 06 , 2015
	 *@param: inputfile- This argument is for passing the location of the input data sheet
	 *@param: sheetcountryname- This argument is for passing the sheet name in the input data sheet
	 *@param: testcasename- Name of the Test Case form which test data should be fetched.
	 *@param: RowNum-From which row data should be fetched
	 *@return: Returns column number as int.
	 */


	public ArrayList<Integer> GetCellDataNumber(String inputfile,String sheetcountryname,String testcasename,int RowNum){
		try{

			FileInputStream input = new FileInputStream(inputfile);

			Workbook wb = null;
			try {
				wb = Workbook.getWorkbook(input);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ArrayList<Integer> cellnum = new ArrayList<Integer>(); 

			Sheet sheet = wb.getSheet(sheetcountryname);

			int columns = sheet.getColumns();
			for(int i=0;i<columns;i++){
				if(sheet.getCell(i, RowNum).getContents().equalsIgnoreCase(testcasename.trim())){
					System.out.println("Test Case to be Executed is - '"+sheet.getCell(i, RowNum).getContents()+"'");
					cellnum.add(i);
				}
			}
			return cellnum;

		}
		catch(Exception e){

			e.printStackTrace();
			return null;
		}
	}


	/**
	 *@MethodName: GetCountryNames
	 *@Description:This Function will fetch the number of countries with flag as 'Y'
	 *@author: Chakradhar K
	 *@CreatedDate: June 19 , 2015
	 *@ModifiedBy: 
	 *@ModifiedDate: 
	 *@param: inputFile- This argument is for passing the location of the input data sheet
	 *@param: sheetname- This argument is for passing the sheet name in the input data sheet
	 *@param: ColNum- Colume number were the flags are present
	 *@return: Returns column number as ArrayList.
	 */


	public  ArrayList<String> GetCountryNames(String inputfile, String SheetName, int ColNum){;

	try{

		FileInputStream input = new FileInputStream(inputfile);
		Workbook wb = null;
		wb = Workbook.getWorkbook(input);
		Sheet sheet = wb.getSheet(SheetName);

		ArrayList<String> countryflag = new ArrayList<String>(); 

		int Rows = sheet.getRows();
		for(int i=1;i<Rows;i++){
			if(sheet.getCell(ColNum, i).getContents().equalsIgnoreCase("Y")){
				sheet.getCell(ColNum-1, i).getContents().trim();
				countryflag.add(sheet.getCell(ColNum-1, i).getContents().trim());
			}
		}

		return countryflag;

	}
	catch(Exception e){

		e.printStackTrace();
		return null;
	}

	}

	/**
	 *@MethodName: GetModuleNames
	 *@Description:This Function will fetch the number of Modules with flag as 'Y'
	 *@author: Chakradhar K
	 *@CreatedDate: June 19 , 2015
	 *@ModifiedBy: 
	 *@ModifiedDate: 
	 *@param: inputFile- This argument is for passing the location of the input data sheet
	 *@param: sheetname- This argument is for passing the sheet name in the input data sheet
	 *@param: ColNum- Colume number were the flags are present
	 *@return: Returns column number as ArrayList.
	 */


	public ArrayList<String> GetModuleNames(String inputfile, String SheetName, int ColNum){;

	try{

		FileInputStream input = new FileInputStream(inputfile);
		Workbook wb = null;
		wb = Workbook.getWorkbook(input);
		Sheet sheet = wb.getSheet(SheetName);

		ArrayList<String> moduleflag = new ArrayList<String>(); 

		int Rows = sheet.getRows();
		for(int i=1;i<Rows;i++){
			if(sheet.getCell(ColNum, i).getContents().equalsIgnoreCase("Y")){
				sheet.getCell(ColNum-1, i).getContents().trim();
				moduleflag.add(sheet.getCell(ColNum-1, i).getContents().trim());
			}
		}

		return moduleflag;

	}
	catch(Exception e){

		e.printStackTrace();
		return null;
	}

	}

	/**
	 *@MethodName: ReadFromModuleSheet
	 *@Description:This Function is to read data from sheet
	 *@author: Chakradhar K
	 *@CreatedDate: June 19 , 2015
	 *@ModifiedBy: 
	 *@ModifiedDate: 
	 *@param: inputFile- This argument is for passing the location of the input data sheet
	 *@param: inputFile- This argument is for passing the sheet name in the input data sheet
	 *@return: Returns data in Linked HashMap
	 */

	public LinkedHashMap<String, String> ReadFromModuleSheet(String inputFile, String sheetcountryname, int columns)
			throws BiffException, IOException,
			DataSheetException, InvalidBrowserException {

		int rows = getRowCount(inputFile, sheetcountryname);
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		boolean headingStatus = validateHeading(inputFile, sheetcountryname);
		FileInputStream input = new FileInputStream(inputFile);
		Workbook wb = Workbook.getWorkbook(input);
		Sheet sheet = wb.getSheet(sheetcountryname);

		/* Validating for the heading status */
		if (headingStatus==true) {

			/* Iterates through each row in excel */
			for (int col = columns; col <columns+1; col++) 
			{
				System.out.println("Executing status is "+sheet.getCell(col, 2).getContents());

				for (int globalCount = 1; globalCount < rows; globalCount++)
				{
					dataMap.put(sheet.getCell(0, globalCount)
							.getContents(),
							sheet.getCell(col, globalCount)
							.getContents().trim());
				}


			}
		}

		else
		{
			logger.error("The sheet headings are invalid:The first and second column heading should be Browser and Execution Status respectively");
			throw new DataSheetException(
					"The sheet headings are invalid:The first and second column heading should be Browser and Execution Status respectively");
		}

		input.close();
		return dataMap;

	}




}


