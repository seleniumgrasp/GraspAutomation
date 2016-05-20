package Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import exceptions.DataSheetException;
import exceptions.InvalidBrowserException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class testdata {

	public static void main(String[] args) throws BiffException, DataSheetException, IOException, InvalidBrowserException {
		LinkedHashMap<String, String> country = ReadFromModuleSheet("D:\\Grasp\\Grasp\\src\\test\\resources\\Location.xls", "USA","Location", 1);
System.out.println(country.size());
	}

	public static LinkedHashMap<String, String> ReadFromModuleSheet(String inputFile, String sheetcountryname, String testname, int columns)
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
			
			throw new DataSheetException(
					"The sheet headings are invalid:The first and second column heading should be Browser and Execution Status respectively");
		}

		input.close();
		return dataMap;


	}
	
	public static int getRowCount(String destFile, String sheetname)
			throws BiffException, IOException {
		FileInputStream input = new FileInputStream(destFile);
		Workbook wb = Workbook.getWorkbook(input);

		Sheet sheet = wb.getSheet(sheetname);
		int row = sheet.getRows();
		input.close();
		return row;

	}
	
	public static boolean validateHeading(String destFile, String sheetname)
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
	
}
