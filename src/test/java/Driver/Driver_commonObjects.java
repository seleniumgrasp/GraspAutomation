package Driver;

/* Import the packages */

//import io.selendroid.exceptions.NoSuchElementException;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.beans.Visibility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import jxl.read.biff.BiffException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.xalan.xsltc.runtime.ErrorMessages;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.SendKeysAction;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.thoughtworks.selenium.webdriven.commands.GetValue;
import com.thoughtworks.selenium.webdriven.commands.IsChecked;
import com.thoughtworks.selenium.webdriven.commands.KeyEvent;












//import UserDetails.UsernameDetails;
import WebDriver.WebDriverFactory;
import Utilities.Constant;
import Utilities.DataSheet;
import Utilities.ExcelUtils;
import Utilities.Log;
import commonFunctions.Functions;
import exceptions.DataSheetException;
import exceptions.InvalidBrowserException;



public class Driver_commonObjects extends Functions{


	public static ArrayList<String> allModulesToRun=new ArrayList<String>();
	public static String applicationTestModule;
	public static String applicationTestGroup;
	public static String applicationDirectory;
	public static String strdateall;
	public static String applicationEnableLogInReport;
	public static String applicationEnvironment;
	public static String testLinkBuildName;
	public static String username;
	public static String Module;
	public static String Email;
	public static String ToEmail;
	public static String Time;
	public static String webdriverServerHostName;
	public static String webdriverServerPortName;
	public static String webdriverUrl;
	public static int timeOut;
	public static String testDataSource;
	public static String externalSheetPath;
	//public static ResourceBundle globalProperties;
	//public static ResourceBundle elementProperties;
	
	
	Properties globalProperties = new Properties();
	Properties elementProperties = new Properties();

	public HashMap<String, String> mapDataSheet;
	public String testName;
	public static final String propId="rel";
	public static String screenshotname;
	public static String outputDir;
	public static String testcasename;
	public static ArrayList<String> screenshots = new ArrayList<String>();
	public static String TestBrowser;
	public static String TestBrowser_Version;
	public static String OS;
	public static String OS_Version;
	public static ArrayList<String> countries = new ArrayList<String>();
	public static String country;
	public static int countrycount=0;
	public String countrygroup = "UK,Germany,Netherland,Norway,Denmark,Sweden";
	public String countrygroup_phoneNo = "UK,Italy,Germany,Denmark,Norway,Sweden,Spain,France,BernardFrance,BernardBelgium,PresselAustria";
	public String countryAddressErrChkbox = "Italy,Netherland,Germany,Denmark,Norway,Sweden,Spain,France,BernardFrance,BernardBelgium,PresselAustria";
	//public String countrygroup_creditcard ="UK,Italy";
	//public String countrygroup_ccdetails ="Italy,Netherland";
	public String countrygroup_username = "Germany,France,PresselAustria,PresselGermany,PresselSwitzerland";



	public Driver_commonObjects(){	
		//this.Module=module;
		//this.Email = UsernameDetails.getEmailid();
		//this.username = UsernameDetails.getUserName();
		this.Time = currentDateAndTime();
		//countries.add(country);
		getGlobalProperties();
		getElementProperties();

	}



	private static final Logger logger = Log.getInstance(Thread.currentThread()
			.getStackTrace()[1].getClassName());
	SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");



	public static String currentDateAndTime() {

		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a z");
		Date today = Calendar.getInstance().getTime();
		TimeZone obj = TimeZone.getTimeZone("EST");
		formatter.setTimeZone(obj);
		strdateall = formatter.format(today);
		return strdateall = strdateall.replaceAll(":", " ");
	}

	/**
	 *@MethodName: initialization
	 *@Description:This Function initialized the browser
	 *@author: Krishnamurthy
	 *@CreatedDate: may 04, 2015
	 *@ModifiedBy:
	 *@ModifiedDate:
	 */
	//@BeforeMethod
	public void initialization() {

	//	startWebDriverClient();
		logger.info("Test initialization");
		logger.info("Calling "+TestBrowser+" Driver");
		String formattedTime = timeFormat.format(new Date()).toString(); 
		logger.info("\n" +
				"-----------------------------------------------------------------------------------------------------------------------------------------------------------\n" +
				"\nModule Name   :  "+Module+
				"\nStart time        :  "+formattedTime+
				"\n-------------------------------------------------------------------------------------------------------------------------------------");


	}


	protected void startWebDriverClient(String BrowserType) {
		try {
			System.out.println("Starting browser "+BrowserType);
			logger.info("Starting browser "+BrowserType);
			driver = WebDriverFactory.getWebdriver(BrowserType);
			driver.manage().timeouts()
			.implicitlyWait(3000, TimeUnit.MILLISECONDS);
			logger.info("startWebDriverClient: Started Driver for "
					+ driver);

		} 
		catch (Exception e) {
			logger.error("Unable to start "+TestBrowser,e.fillInStackTrace());
			throw new UnreachableBrowserException("Unable to start "+TestBrowser,e.fillInStackTrace());

		}

	}

	public static String getBaseURL() {
		return webdriverUrl;
	}

	/**
	 *@MethodName: getGlobalProperties
	 *@Description:This Function fetches the data from global.properties file
	 *@author: Krishnamurthy
	 *@CreatedDate: may 04, 2015
	 *@ModifiedBy:
	 *@ModifiedDate:
	 */
	protected void getGlobalProperties() {

		//globalProperties = ResourceBundle.getBundle("global");
		Properties globalProperties = new Properties();
		FileInputStream input;
		try {
			input = new FileInputStream("D:\\Grasp\\Grasp\\src\\test\\resources\\global.properties");
			globalProperties.load(input);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		testLinkBuildName = globalProperties.getProperty("testlink_BUILD_NAME");
		applicationEnvironment = globalProperties.getProperty("applicationEnvironment");
		applicationTestModule = globalProperties.getProperty("app.application.test.modules");
		applicationTestGroup = globalProperties.getProperty("app.application.test.groups");
		applicationDirectory = globalProperties.getProperty("app.test.reports.directory");
		applicationEnableLogInReport = globalProperties.getProperty("app.enable.logs.in.report");
		webdriverServerHostName = globalProperties
				.getProperty("webdriver_hostname");
		webdriverServerPortName = globalProperties.getProperty("webdriver_port");
		webdriverUrl = globalProperties.getProperty("webdriver_defaultURL");
		//TestBrowser = globalProperties.getProperty("browser_name");
		ToEmail = globalProperties.getProperty("ToEmail");


		timeOut = Integer.parseInt(globalProperties.getProperty("time_out"));
		if (globalProperties.containsKey("test_data_source")) {
			testDataSource = globalProperties.getProperty("test_data_source");
		}
		/*if(testDataSource.equalsIgnoreCase("excel")){
			externalSheetPath = globalProperties.getProperty("external_sheet_path");
			if(externalSheetPath.equals(""))
			{
				logger.error("Please provide a valid sheet path");
				Assert.fail();
			}
		}
		else{
			logger.error("Please provide a valid test data source value");
			Assert.fail();
		}*/		

	}

	public static String sheetpaths(String  module){
		try{
			return System.getProperty("user.dir")+"\\src\\test\\resources\\"+module+".xls";
		}catch(Exception e){

			throw new IllegalArgumentException("Invalid sheet path. Please provide valid sheetpath");
		}

	}

	/*public static String getGlobalBrowserFlag()
	{
		return globalProperties.getProperty("use_global_browser_forExcel");

	}*/

	/**
	 *@MethodName: getBrowserName
	 *@Description:This Function initialized the element.properties file
	 *@author: Krishnamurthy
	 *@CreatedDate: May 11, 2015
	 *@ModifiedBy:
	 *@ModifiedDate:
	 */


	protected void getElementProperties() {
		Properties elementProperties = new Properties();
		FileInputStream input;
		try {
			input = new FileInputStream("D:\\Grasp\\Grasp\\src\\test\\resources\\element.properties");
			elementProperties.load(input);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//elementProperties = ResourceBundle.getBundle("element");

	}

	/**
	 *@MethodName: locator_split
	 *@Description:This Function checks whether the datas form property file is what element.
	 *@author: Krishnamurthy
	 *@CreatedDate: May 06, 2015
	 *@ModifiedBy:
	 *@ModifiedDate:
	 *@param: destFile- This argument is for passing the location of the input data sheet
	 *@param: sheetname- This argument is for passing the sheet name in the input data sheet
	 *@return: Returns column number as int.
	 * @throws InvalidBrowserException 
	 */

	public static By locator_split(String object)

	{
		Properties elementProperties = new Properties();
		FileInputStream input;
		try {
			input = new FileInputStream(
					"C:\\Users\\karthickv\\workspace\\MavenCucumber\\src\\test\\resources\\element.properties");
			elementProperties.load(input);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String locator = elementProperties.getProperty(object);

		String[] locators = locator.split("-", 2);

		switch (locators[0]) {
		case "id":
			return By.id(locators[1]);

		case "xpath":
			return By.xpath(locators[1]);
		case "css":
			return By.cssSelector(locators[1]);

		case "name":
			return By.name(locators[1]);
		case "link":

			return By.linkText(locators[1]);

		case "class":
			return By.className(locators[1]);

		case "partial":
			return By.partialLinkText(locators[1]);

		case "tag":
			return By.tagName(locators[1]);

		}
		throw new IllegalArgumentException(
				"Invalid By Type, Please provide correct locator type");

	}

	/**
	 *@MethodName: TestExecution
	 *@Description:This Function check whether to skip or execute testcase based on Execution status.
	 *@author: Krishnamurthy
	 *@CreatedDate: June 15, 2015
	 *@ModifiedBy:
	 *@ModifiedDate:
	 *@param: destFile- This argument is for passing the location of the input data sheet
	 *@param: sheetname- This argument is for passing the sheet name in the input data sheet
	 *@return: Returns column number as int.
	 * @throws InvalidBrowserException 
	 */

	public void TestExecution(HashMap<String, String> DataSheet){
		this.mapDataSheet=DataSheet;
		this.testcasename=getValue("TestCase");
		this.Module=getValue("ModuleName");
		this.TestBrowser=getValue("BROWSER");
		//this.TestBrowser_Version=getValue("Browser_Version");
		//this.OS=getValue("OS");
		//this.OS_Version=getValue("OS_Version");
		if(mapDataSheet.get("Execution Status").equalsIgnoreCase("N")){
			logger.info("-----------------------------------------------------------------------");


			logger.info("Execution Status is marked as 'N'. So Skipping - "+testcasename);
			logger.info("-----------------------------------------------------------------------\n");
			Reporter.log("TestStepComponent"+"NA");	 
			Reporter.log("TestStepInput:-"+"NA");
			Reporter.log("TestStepOutput:-"+"NA");
			Reporter.log("TestStepExpectedResult:-"+"NA");

			Reporter.log("SKIP_MESSAGE:-"+"Skipping the TestCase");

			throw new SkipException("Skipping Test Case");
		}else{
			logger.info("Execution Status is marked as 'Y'. So Proceeding with TestCase - "+testcasename);
			System.out.println("Executing Test Case - '"+testcasename+"' from Module -'"+Module+"'");
		}
	}

	//************************Fetches data from Excel Sheet ******************************//
	/**
	 *@MethodName: Dataprovider
	 *@Description:This Function gets the data form Excel used in Iterations.
	 *@author: Krishnamurthy
	 *@CreatedDate: June 15, 2015
	 *@ModifiedBy:
	 *@ModifiedDate:
	 *@param: TestCasename- name of the TestCase currently getting executed
	 *@return: Returns data of hashmap into Object array
	 */

	public static Object[][] Dataprovider(String TestCasename, String module) throws BiffException, DataSheetException, IOException, InvalidBrowserException{
		externalSheetPath=sheetpaths(module);
		LinkedHashMap<String, String> dataMap =  null;
		ArrayList<Integer> datarepeat = null;
		DataSheet dataSheetObj = new DataSheet();

		datarepeat = dataSheetObj.GetCellDataNumber(externalSheetPath, countries.get(countrycount), TestCasename ,0);
		Object[][] dataObjectArray = new Object[datarepeat.size()][1];
		for(int i=0; i<datarepeat.size();i++){
			dataMap=dataSheetObj.ReadFromModuleSheet(externalSheetPath, countries.get(countrycount), datarepeat.get(i));
			dataObjectArray[i] = new Object[] { dataMap };
		}

		return dataObjectArray;
	}

	@AfterClass
	public void AfterClassCountryIncrement(){
		countrycount++;
	}

	/**
	 *@MethodName: getValue
	 *@Description:This Function gets the data from Hahsmap.
	 *@author: Krishnamurthy
	 *@CreatedDate: May 04, 2015
	 *@ModifiedBy:
	 *@ModifiedDate:
	 *@param: Key - Enter the data key needed
	 *@return: Returns data value for that key from hashmap
	 */
	public String getValue(String key)
	{

		if(mapDataSheet.containsKey(key))
		{			
			if(mapDataSheet.get(key)!=null )
			{
				return mapDataSheet.get(key);
			}
			else
			{
				logger.info("-----------------------------------FAIL---------------------");
				logger.info("test case '"+key+"' value is not present in TestDatabase <dbsadv>");
				logger.info(mapDataSheet.get(key));
				logger.info("----------------------------------------------------------------------------");
				return mapDataSheet.get(key);

			}
		}

		else
		{
			logger.info("Column heading '"+key+"' is not present in your data source \n Please Check the Column Headings of your TestCase's data in your data source (Excel / Database)");
			Reporter.log("Column heading '"+key+"' is not present in your data source \n Please Check the Column Headings of your TestCase's data in your data source (Excel / Database)");
			throw new NullPointerException("Column heading "+key+"is not present in your data source \n Please Check the Column Headings of your TestCase's data in your data source (Excel / Database)");
		}
	}


	/****************************************************************************************************/ 	

	/********************************Test Failure Screenshot***************************************/ 

	/**
	 *@MethodName: AfterMethod
	 *@Description:This Function takes screenshot of failed Test Cases and closed the browser.
	 *@author: Krishnamurthy
	 *@CreatedDate: May 04, 2015
	 *@ModifiedBy:
	 *@ModifiedDate:
	 */
	@AfterMethod
	public void AfterMethod(ITestResult result)
	{
		if(getValue("Execution Status").equalsIgnoreCase("N")){
			System.out.println("not taking screenshot for-"+testcasename);
		}else{
			driver = getDriver();

			if (result.getStatus() == ITestResult.FAILURE) {

				if (driver != null)
				{
					File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

					try
					{
						screenshotname = Module+"_"+testcasename+"_"+Driver_commonObjects.strdateall
								+ "_screenshot.png";
						String fileNameToCopy = "target/custom-test-reports/" + screenshotname;
						FileUtils.copyFile(scrFile, new File(fileNameToCopy));
						Reporter.log("[Console Log] Screenshot saved in " +fileNameToCopy+ "_screenshot.png");
						screenshots.add(screenshotname);
					} catch (IOException ex)
					{
						System.out.println("Not able to take screenshot");
						Reporter.log("[Console Log] Screenshot is not taken");
					}
				}

			}
			//driver.close();
		//	driver.quit();

		}



		logger.info("-----------------------------------------------------------------------");

		String formattedTime = timeFormat.format(new Date()).toString();
		logger.info("End time:       "+formattedTime);
		logger.info("-----------------------------------------------------------------------\n");

	}

	/****************************************************************************************************/ 	

	/********************************Functionality Related Methods
	 * @throws Exception ***************************************/ 

	/* Function Name 				: --- ----- --- openUrl
	 * Author 	   					: --- ----- --- TCS
	 * Description   				: --- ----- --- Open the application for the specified URL in Global Property file
	 * Date of creation				: --- ----- ---  
	 * Input Parameters  			: --- ----- --- url
	 * Name of person modifying 	: --- ----- ---
	 * Date of modification			:--- ----- ---
	 */

	public void openUrl(){

		//initialization();
		//String URL = Driver_commonObjects.getBaseURL();
		String URL = getValue("URL");
		
		System.out.println(URL);
		driver.get(URL);
		sleep(2000);
		
	
		//driver.navigate().to(document.getElementById("overridelink").click()”);
		class Local {};	
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());	 
		Reporter.log("TestStepInput:-"+URL);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:-"+URL+" should be loaded");
		if (URL.length()!=0) 
		{
			try
			{
				driver.get(URL);
				driver.manage().window().maximize();
				Reporter.log("PASS_MESSAGE:-"+"The Base URL: '"+URL +"' is loaded succesfully in '"+TestBrowser+"'");
				logger.info("The Base URL: "+URL +" is loaded succesfully");		
				System.out.println(URL+" is opened successfully");
				System.out.println("Title"+driver.getTitle());
			//	if (driver.getTitle().equals("Certificate Error: Navigation Blocked"){
					//System.out.println("inside loop");
				//driver.findElement(By.id("overridelink")).click();
			//	click(locator_split("certificationErrorLink"));
				//}
					//driver.findElement(By.id("overridelink")).click();
					
			} 
			catch(UnreachableBrowserException e)
			{
				Reporter.log("FAIL_MESSAGE:-"+"Unable to load the Base URL:"+URL);				
				logger.error("Unable to load the Base URL: ",e.fillInStackTrace());				
				throw new UnreachableBrowserException("Unable to load the Base URL: "+ URL);
			}
		} 
		else
		{
			logger.error("FAIL_MESSAGE:-"+"Unable to load the Base URL: "+URL+" Please provide a valid Base URL");
			Reporter.log("Unable to load the Base URL: "+URL+" Please provide a valid Base URL");
			throw new UnreachableBrowserException("Unable to load the Base URL: "
					+URL+" Please provide a valid Base URL.");
		}

	}

	public void ClickIncVAT()
	{
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- The Vat include button with WebElement "+elementProperties.getProperty("Vatinclusion")+" should be clicked");

		try{
			String countrygroup_pressel_germany="PresselGermany";
			String countrygroup_Bernard_belgium="BernardBelgium";

			if((countrygroup).contains(countries.get(countrycount))){
				click(locator_split("Vatinclusion")); 
				Reporter.log("PASS_MESSAGE:-The Vat include button with WebElement "+elementProperties.getProperty("Vatinclusion")+" is clicked");
			}
			else if((countrygroup_pressel_germany).contains(countries.get(countrycount)))
			{
				clickPresselGermany();
			}

			else if((countrygroup_Bernard_belgium).contains(countries.get(countrycount)))
			{
				clickNederlands();
			}

			else
				Reporter.log("PASS_MESSAGE:-The Vat include button with WebElement "+elementProperties.getProperty("Vatinclusion")+" is not present");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- The Vat include button with WebElement "+elementProperties.getProperty("Vatinclusion")+" is not clicked");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("Vatinclusion").toString().replace("By.", " ")
					+ " not found");

		}
	}

	public void mouseOver(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Mouse should be hovered on login icon");
		try{
			Actions action = new Actions(driver);
			WebElement we = driver.findElement(locator_split("Login"));
			action.moveToElement(we).build().perform();  
			Reporter.log("PASS_MESSAGE:- Mouse is hovered on Login");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Mouse is not hovered on login with Webelement "+elementProperties.getProperty("Login"));
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("Login").toString().replace("By.", " ")
					+ " not found");

		}
	}

	public void setEmailAddress(String emailAddress){
		String Email = getValue(emailAddress);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Email);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Email Address should be entered in Login");
		try{
			sendKeys(locator_split("txtUserName"), Email);	
			System.out.println("Email address is entered");
			Reporter.log("PASS_MESSAGE:- Email Address/Username is entered in Login field");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Email Address/Username is not entered in Login field with WebElement "+elementProperties.getProperty("txtUserName"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtUserName").toString().replace("By.", " ")
					+ " not found");

		}
	}

	public void setRegEmailAddress(String emailAddress){
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

		Date date = new Date();

		String ldDate=(String) dateFormat.format(date);

		String Email = getValue(emailAddress);
		String Email_date=Email+ldDate;

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Email);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Email Address should be entered in Login");
		try{
			sendKeys(locator_split("txtRegUserName"), Email_date);	
			System.out.println("Email address is entered");
			Reporter.log("PASS_MESSAGE:- Email Address/Username is entered in Login field");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Email Address/Username is not entered in Login field with WebElement "+elementProperties.getProperty("txtRegUserName"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtUserName").toString().replace("By.", " ")
					+ " not found");

		}
	}



	public void setPassword(String PWD){
		String Password = getValue(PWD);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Password);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Password should be enterd in Login");
		try{
			sendKeys(locator_split("txtpassword"), Password);
			System.out.println("Password is entered");
			Reporter.log("PASS_MESSAGE:- Password is entered in Login field");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Password is not enterd in Login with WebElement "+elementProperties.getProperty("txtpassword"));
			e.printStackTrace();
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtpassword").toString().replace("By.", " ")
					+ " not found");

		}
	}

	/* Method Name    : --- ----- --- setRegistrationUsername
	 * Purpose     : --- ----- --- set the Registration username
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */
	public void setRegistrationUsername(String Username){

		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

		Date date = new Date();

		String ldDate=(String) dateFormat.format(date);

		String USR = getValue(Username);

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+USR);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration Username is entered");
		try{

			waitforElementVisible(locator_split("txtRegistrationUsername"));
			sendKeys(locator_split("txtRegistrationUsername"), USR+ldDate+"@Quill.com");        
			System.out.println("Username is entered");
			Reporter.log("PASS_MESSAGE:-User name is entered");


		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Confirm password is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtRegistrationUsername").toString().replace("By.", " ")
					+ " not found");

		}

	}


	/* Method Name : --- ----- --- setcheckoutRegistrationPassword
	 * Purpose     : --- ----- --- set the check out Registration password
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void setcheckoutRegistrationPassword(String password){
		String Password = getValue(password);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- check out registration password should be entered");
		try{

			waitforElementVisible(locator_split("checkoutregistrationpassword"));
			sendKeys(locator_split("checkoutregistrationpassword"), Password);        
			System.out.println("Check out registration password is entered");
			Reporter.log("PASS_MESSAGE:- check out registration password is entered");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- check out registration password is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("checkoutregistrationpassword").toString().replace("By.", " ")
					+ " not found");

		}

		Reporter.log("Entered Confirm Password in Registration Confirm Password field");
	}



	/* Method Name : --- ----- --- setRegistrationPassword
	 * Purpose     : --- ----- --- set the Registration password
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void setRegistrationPassword(String password){
		String Password = getValue(password);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- check out registration password should be entered");
		try{

			waitforElementVisible(locator_split("txtRegistrationPassword"));
			sendKeys(locator_split("txtRegistrationPassword"), Password);        
			System.out.println("Check out registration password is entered");
			Reporter.log("PASS_MESSAGE:- check out registration password is entered");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- check out registration password is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtRegistrationPassword").toString().replace("By.", " ")
					+ " not found");

		}

		Reporter.log("Entered Confirm Password in Registration Confirm Password field");
	}
	/* Method Name : --- ----- --- setRegistration confirm password
	 * Purpose     : --- ----- --- set the Registration confirm password
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void setRegistrationConfirmPassword(String password){

		String password1 = getValue(password);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Confirm password is entered");
		try{

			waitforElementVisible(locator_split("txtRegistrationContactConfirmPassword"));
			sendKeys(locator_split("txtRegistrationContactConfirmPassword"), password1);        


			System.out.println("Confirm password is entered");
			Reporter.log("PASS_MESSAGE:-Create new user link clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Confirm password is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtRegistrationContactConfirmPassword").toString().replace("By.", " ")
					+ " not found");

		}

		Reporter.log("Entered Confirm Password in Registration Confirm Password field");
	}


	public void setRegistrationcontactConfirmPassword(String password){

		String password1 = getValue(password);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Confirm password is entered");
		try{

			waitforElementVisible(locator_split("txtRegistrationContactConfirmPassword"));
			sendKeys(locator_split("txtRegistrationContactConfirmPassword"), password1);        


			System.out.println("Confirm password is entered");
			Reporter.log("PASS_MESSAGE:-Create new user link clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Confirm password is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtRegistrationContactConfirmPassword").toString().replace("By.", " ")
					+ " not found");

		}

		Reporter.log("Entered Confirm Password in Registration Confirm Password field");
	}

	/* Method Name : --- ----- --- clickRegistration accounttyperadiobutton
	 * Purpose     : --- ----- --- click Registration accounttyperadiobutton
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- --- 
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */       


	public void clickRegistrationaccounttyperadiobutton(String option){

		String Acctype = getValue(option);
		String countrygroup_accounttype ="Spain,France,BernardFrance,BernardBelgium,PresselAustria";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Account type Radio button clicked");
		try{
			if(!(countrygroup_accounttype).contains(countries.get(countrycount))){
				waitforElementVisible(locator_split("rdbRegistrationAccounttype"));
				clickSpecificElementByProperty(locator_split("rdbRegistrationAccounttype"),"value",Acctype);
				System.out.println("Account type Radio button clicked for "+Acctype);
				Reporter.log("PASS_MESSAGE:- Account type Radio button clicked for "+Acctype);
			}
			else
			{
				Reporter.log("PASS_MESSAGE:- Account type Radio button is not applicable to " + country);
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Account type Radio button is not clicked "+elementProperties.getProperty("rdbRegistrationAccounttype"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("rdbRegistrationAccounttype").toString().replace("By.", " ")
					+ " not found");

		}

	}



	/* Method Name : --- ----- --- createnewuserbutton
	 * Purpose          : --- ----- --- createnewuserbutton
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */       

	public void createnewusersubmitbutton( ){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Create new user button clicked");
		try{

			waitforElementVisible(locator_split("createnewuserbutton"));
			click(locator_split("createnewuserbutton"));

			Reporter.log("PASS_MESSAGE:- create new user button clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- create new user button is not clicked "+elementProperties.getProperty("createnewuserbutton"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("createnewuserbutton").toString().replace("By.", " ")
					+ " not found");

		}


	}




	/* ******************************************************************************/

	/* Method Name : --- ----- --- Logoutbutton
	 * Purpose 	   : --- ----- --- Click on theLogoutbutton
	 * Parameter   : --- ----- --- None
	 * Date Created: --- ----- --- 6/20/2014
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	/*public void Logoutbutton(){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Logoutbutton should beclicked");
		try{
			waitForElement(locator_split("Pancakemenu"));
			click(locator_split("Pancakemenu"));
			waitForElement(locator_split("Logoutbutton"));
			click(locator_split("Logoutbutton"));
			click(locator_split("Logoutuser"));
			waitForPageToLoad(100);	
			System.out.println("Log out button is clicked");
			Reporter.log("PASS_MESSAGE:- Clicked on Logoutbutton");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Logoutbutton clicked is not clicked "+elementProperties.getProperty("Logoutbutton"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("Logoutbutton").toString().replace("By.", " ")
					+ " not found");

		}
	}*/

	/* Method Name : --- ----- --- Logoutbutton
	 * Purpose 	   : --- ----- --- Click on theLogoutbutton
	 * Parameter   : --- ----- --- None
	 * Date Created: --- ----- --- 6/20/2014
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void Logoutbutton(){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Logoutbutton should beclicked");
		try{

			waitForElement(locator_split("logout"));
			click(locator_split("logout"));
			waitForPageToLoad(100);	
			System.out.println("Log out button is clicked");
			waitForPageToLoad(100);	
			System.out.println("Log out button is clicked");
			Reporter.log("PASS_MESSAGE:- Clicked on Logoutbutton");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Logoutbutton clicked is not clicked "+elementProperties.getProperty("Logoutbutton"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("Logoutbutton").toString().replace("By.", " ")
					+ " not found");

		}



	}






	/* Method Name : --- ----- --- verifyRegistrationwelcomemsg
	 * Purpose     : --- ----- --- verifyRegistrationwelcomemsg
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */       


	public void verifyRegistrationwelcomemsg(String welcomemessage){
		String weolcome = getValue(welcomemessage);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- continue Registartion  button clicked");
		try{

			if(getAndVerifyPartialText(locator_split("Regwelcomemsg"),weolcome)) {                                
				waitForPageToLoad(20);
			}
			Reporter.log("PASS_MESSAGE:- Welcome msg Displayed");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Welcome msg Displayed  is displayed "+elementProperties.getProperty("Regwelcomemsg"));
			throw new NoSuchElementException("The element with " + elementProperties.getProperty("Regwelcomemsg").toString().replace("By.", " ") + " not found");

		}


	}


	/* Method Name : --- ----- --- Regcontinuebutton
	 * Purpose     : --- ----- --- Continue button in registration will be clicked
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */       


	public void Regcontinuebutton( ){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- continue Registartion  button clicked");
		try{

			waitforElementVisible(locator_split("Regcontinuebutton"));
			click(locator_split("Regcontinuebutton"));

			Reporter.log("PASS_MESSAGE:- continue Registartion  button clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- continue Registartion  button  is not clicked "+elementProperties.getProperty("Regcontinuebutton"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("Regcontinuebutton").toString().replace("By.", " ")
					+ " not found");

		}


	}





	/* Method Name : --- ----- --- ClickMyAccount
	 * Purpose     : --- ----- --- My Account Link will be clicked
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */       


	public void ClickMyAccount( ){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account Link clicked");
		try{

			waitforElementVisible(locator_split("LoginLogout"));
			click(locator_split("LoginLogout"));

			Reporter.log("PASS_MESSAGE:- My Account Link clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- My Account Link  is not clicked "+elementProperties.getProperty("LoginLogout"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("LoginLogout").toString().replace("By.", " ")
					+ " not found");

		}


	}


	/* Method Name : --- ----- --- AddItemFavList
	 * Purpose     : --- ----- --- FavLsit Link will be clicked
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */       


	public void AddItemFavList( ){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Favourite List Link should be clicked");
		try{

			sleep(1000);
			waitforElementVisible(returnByValue(getValue("FavouriteListLink")));
			click(returnByValue(getValue("FavouriteListLink")));
			waitForPageToLoad(100);

			waitforElementVisible(locator_split("btnAdditemtocart"));
			click(locator_split("btnAdditemtocart"));
			waitForPageToLoad(20);

			Reporter.log("PASS_MESSAGE:- Favourite List and Add Item button clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- FavList Link  is not clicked "+elementProperties.getProperty("FavouriteListLink"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("FavouriteListLink").toString().replace("By.", " ")
					+ elementProperties.getProperty("btnAdditemtocart").toString().replace("By.", " ")
					+ " not found");

		}


	}

	/* Method Name : --- ----- --- ClickCreateDelAddress
	 * Purpose     : --- ----- ---  CreateDelAddress Link will be clicked
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */       


	public void ClickCreateDelAddress( ){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Create Delivery Link is clicked");
		try{

			waitforElementVisible(locator_split("lkMyAddress"));
			click(locator_split("lkMyAddress"));
			waitforElementVisible(locator_split("lkCreateDelAddress"));
			click(locator_split("lkCreateDelAddress"));			

			Reporter.log("PASS_MESSAGE:- Create Delivery Link clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Create Delivery Link  is not clicked ");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("lkMyAddress").toString().replace("By.", " ")
					+ elementProperties.getProperty("lkCreateDelAddress").toString().replace("By.", " ")
					+ " not found");

		}


	}



	/* Method Name : --- ----- --- clickloginbutton
	 * Purpose     : --- ----- --- Login button should be clicked on the home page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */   

	public void clickloginbutton(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Login Button should be clicked");
		try{
			click(locator_split("btnLogin"));
			sleep(2000);
			Reporter.log("PASS_MESSAGE:- Login Button is clicked");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Login button is not clicked with WebElement "+elementProperties.getProperty("btnLogin"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("btnLogin").toString().replace("By.", " ")
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- searchItem
	 * Purpose     : --- ----- --- searchItem
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */   

	public void searchItem(String itemnum){	
		String searchitem = getValue(itemnum);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+searchitem);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Item should be searched in the search box");
		try{
			//editSubmit(locator_split("txtSearch"));
			waitforElementVisible(locator_split("txtSearch"));
			clearWebEdit(locator_split("txtSearch"));
			sendKeys(locator_split("txtSearch"), searchitem);
			Thread.sleep(2000);
			Reporter.log("PASS_MESSAGE:- Item is searched in the search box");
			System.out.println("Search item - "+searchitem+" is entered");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Item is not entered in the search box "+elementProperties.getProperty("txtSearch"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtSearch").toString().replace("By.", " ")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- clickSubmitSearchButton
	 * Purpose     : --- ----- --- clickSubmitSearchButton
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */   

	public void clickSubmitSearchButton(){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Search Button should be clicked");
		try{

			waitforElementVisible(locator_split("btnSearchSubmit"));
			click(locator_split("btnSearchSubmit"));
			waitForPageToLoad(10);
			Reporter.log("PASS_MESSAGE:- Search Button is clicked");
			System.out.println("Sarch icon is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Search Button is not clicked "+elementProperties.getProperty("btnSearchSubmit"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("btnSearchSubmit").toString().replace("By.", " ")
					+ " not found");

		}


	}

	/* Method Name : --- ----- --- clickAddtoCart
	 * Purpose     : --- ----- --- clickAddtoCart
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */   
	public void clickAddtoCart(){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Addtocart button should be clicked");
		try{
			waitForPageToLoad(25);
			waitForElement(locator_split("btnSkuAddToCart"));
			click(locator_split("btnSkuAddToCart"));
			System.out.println("Add to cart button is clicked");
			sleep(2000);
			Reporter.log("PASS_MESSAGE:- Addtocart button is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Addtocart button is not clicked "+elementProperties.getProperty("btnSkuAddToCart"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("btnSkuAddToCart").toString().replace("By.", " ")
					+ " not found");

		}      
	}


	/* Method Name : --- ----- --- RegisterFromMyAccount
	 * Purpose     : --- ----- --- clicks the register link from my account drop menu
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- --- Bala
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	/*public void RegisterFromMyAccount(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Register link should be clicked from My Account dropdown");
		try{
			mousehover(locator_split("LoginLogout"));
			click(locator_split("lkRegisternewuser"));
			Reporter.log("PASS_MESSAGE:- Register link is clicked from My Account dropdown");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Register link is not clicked from My Account dropdown");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("LoginLogout")
					+ elementProperties.getProperty("lkRegisternewuser")
					+ " not found");

		}
	}*/


	public void RegisterFromMyAccount(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Register link should be clicked from My Account dropdown");
		try{
			sleep(3000);
			//click(locator_split("lkReorderfast"));
			click(locator_split("lnkMyAccounttip"));
			sleep(1000);			 
			click(locator_split("lkRegisternewuser"));
			Reporter.log("PASS_MESSAGE:- Register link is clicked from My Account dropdown");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Register link is not clicked from My Account dropdown");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("LoginLogout")
					+ elementProperties.getProperty("lkRegisternewuser")
					+ " not found");

		}
	}



	/* Method Name : --- ----- --- clickViewCart
	 * Purpose     : --- ----- --- clickViewCart
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */   
	public void clickViewCart(){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- ViewCart button should be clicked");
		try{
			waitForPageToLoad(25);
			waitForElement(locator_split("BybtnSkuViewCart"));
			click(locator_split("BybtnSkuViewCart"));
			waitForPageToLoad(25);
			Reporter.log("PASS_MESSAGE:- ViewCart button is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- ViewCart button is not clicked "+elementProperties.getProperty("BybtnSkuViewCart"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("BybtnSkuViewCart").toString().replace("By.", " ")
					+ " not found");

		}      
	}

	/* Method Name : --- ----- --- clickViewCartButton
	 * Purpose     : --- ----- --- clickViewCartButton
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */   
	public void clickViewCartButton(){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Viewcart button should be clicked");
		try{
			//waitForElement(btnViewCart);
			waitForElement(locator_split("btnViewCartBtn"));
			//click(btnViewCart);
			click(locator_split("btnViewCartBtn"));
			waitForPageToLoad(20);


			Reporter.log("PASS_MESSAGE:- Viewcart button is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- View button is not clicked "+elementProperties.getProperty("btnViewCartBtn"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("btnViewCartBtn").toString().replace("By.", " ")
					+ " not found");

		}


	}

	/* Method Name : --- ----- --- clickCheckoutButton
	 * Purpose     : --- ----- --- clickCheckoutButton
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void clickCheckoutButton(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- CheckOut button should be clicked");
		try{

			waitForElement(locator_split("btnCheckout"));
			click(locator_split("btnCheckout"));  
			sleep(1000);
			Reporter.log("PASS_MESSAGE:- CheckOut button is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- CheckOut button is not clicked "+elementProperties.getProperty("btnCheckout"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("btnCheckout").toString().replace("By.", " ")
					+ " not found");

		}

		Reporter.log("Clicked on Checkout button");
	}


	/* Method Name : --- ----- --- Click Cashondelivery radiobutton
	 * Purpose     : --- ----- --- click Cashondelivery radiobutton
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- --- 
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */       


	public void clickCashondeliveryradiobutton(){


		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Cashondelivery Radio button clicked");
		try{

			waitforElementVisible(locator_split("Cashondelivery_Spain"));
			click(locator_split("Cashondelivery_Spain"));
			Thread.sleep(3000);
			waitforElementVisible(locator_split("Submitorder_Spain"));
			click(locator_split("Submitorder_Spain"));
			Reporter.log("PASS_MESSAGE:- Cashondelivery radio button clicked ");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Cashondelivery Radio button is not clicked "+elementProperties.getProperty("Cashondelivery_Spain"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("Cashondelivery_Spain").toString().replace("By.", " ")
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- Click Cashondelivery radiobutton
	 * Purpose     : --- ----- --- click Cashondelivery radiobutton
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- --- 
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */       


	public void clickCardradiobutton(){


		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Cashondelivery Radio button clicked");
		try{

			waitforElementVisible(locator_split("Cashondelivery_Spain"));
			click(locator_split("Cashondelivery_Spain"));
			Thread.sleep(3000);
			waitforElementVisible(locator_split("Submitorder_Spain"));
			click(locator_split("Submitorder_Spain"));
			Reporter.log("PASS_MESSAGE:- Cashondelivery radio button clicked ");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Cashondelivery Radio button is not clicked "+elementProperties.getProperty("Cashondelivery_Spain"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("Cashondelivery_Spain").toString().replace("By.", " ")
					+ " not found");

		}

	}






	/* Method Name : --- ----- --- BrowserQuit
	 * Purpose     : --- ----- --- BrowserQuit
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void BrowserQuit() {
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- The Browser should be closed");
		try{
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.quit();
			System.out.println("Browser Closed");
			Reporter.log("PASS_MESSAGE:-The Browser is closed");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- No Browser is opened currently");

		}

	}

	/* Method Name : --- ----- --- clickloginlink
	 * Purpose     : --- ----- --- Login link will be clicked on the Home page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 06/05/2015
	 * Created By  : --- ----- --- TCS
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void clickloginlink(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Loginlink should be clicked from Home Page");
		try{
			waitForElement(locator_split("lnkLogin"));
			click(locator_split("lnkLogin"));
			waitForPageToLoad(200);
			System.out.println("Login link clicked");
			Reporter.log("PASS_MESSAGE:-Login link is clicked from home page");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Login link is not clicked");

		}


	}

	/* Method Name : --- ----- --- clickcreateuser
	 * Purpose     : --- ----- --- The Create User link will be clicked
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 06/05/2015
	 * Created By  : --- ----- --- TCS
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void clickcreateuser(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Create new user link should be clicked");
		try{
			waitForElement(locator_split("lnkcreateuser"));
			click(locator_split("lnkcreateuser"));
			waitForPageToLoad(400);
			System.out.println("Create new user link clicked");
			Reporter.log("PASS_MESSAGE:-Create new user link is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Create new user link is not clicked");

		}


	}

	/* Method Name : --- ----- --- clickSupercatlink
	 * Purpose     : --- ----- --- Super Cart Link will be clicked from Pan Cake menu
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 07/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void clickSupercatlink(String CategoryName){
		String supercartlink1 = getValue(CategoryName);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+supercartlink1);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- '"+supercartlink1+"' link should be clicked");
		try{

			waitForElement(locator_split("BylnkSuperCat"));
			clickSpecificElement(locator_split("BylnkSuperCat"),supercartlink1);
			waitForPageToLoad(20);
			System.out.println(supercartlink1+" link is clicked");
			Reporter.log("PASS_MESSAGE:- '"+supercartlink1+"' link is clicked");
			sleep(3000);
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- "+supercartlink1+" link is not clicked");

		}
	}

	/* Method Name : --- ----- --- clickSupercatNextlink
	 * Purpose     : --- ----- --- Super Cart Next link from Pan Cake menu will be clicked.
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 07/05/2015
	 * Created By  : --- ----- --- TCS
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void clickSupercatNextLevel(String CategoryName){
		String supercartlink2 = getValue(CategoryName);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+supercartlink2);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- '"+supercartlink2+"' link should be clicked");
		try{

			waitForElement(locator_split("BylnkSuperCatNext"));
			clickSpecificElementContains(locator_split("BylnkSuperCatNext"),supercartlink2);
			waitForPageToLoad(20);
			System.out.println(supercartlink2+"link clicked");
			Reporter.log("PASS_MESSAGE:- '"+supercartlink2+"' link is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- '"+supercartlink2+"' link is not clicked");

		}
	}

	/* Method Name : --- ----- --- clickOnfirstSearchItem
	 * Purpose     : --- ----- --- Clicks the first item in the search item.
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 07/05/2015
	 * Created By  : --- ----- --- TCS
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void clickOnfirstSearchItem(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- first item link should be clicked");
		try{

			click(locator_split("ByeleSearchItems1"));
			waitForPageToLoad(20);
			sleep(6000);
			System.out.println("first item link clicked");
			Reporter.log("PASS_MESSAGE:- first item link is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- first item link is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("ByeleSearchItems1").toString().replace("By.", " ")
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- InkandTonersearchbox
	 * Purpose     : --- ----- --- Searches the item in Ink and Toner search box
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- --- TCS
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void InkandTonersearchbox(String searchitem){
		String inktonersearch = getValue(searchitem);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- The search item '"+inktonersearch+"' should be entered in the search box");
		try{

			sendKeys(locator_split("InkandTonersearchbox"), inktonersearch);
			click(locator_split("InkandTonersearchboxgo"));
			waitForPageToLoad(20);
			System.out.println("Search item "+inktonersearch+" is enterd");
			Reporter.log("PASS_MESSAGE:- Search item '"+inktonersearch+"' is enterd in search box");


		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Search item '"+inktonersearch+"' is not enterd in search box");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("InkandTonersearchbox").toString().replace("By.", " ")
					+" or "+elementProperties.getProperty("InkandTonersearchboxgo").toString().replace("By.", " ")+ " not found");
		}

	}

	/* Method Name : --- ----- --- CheckoutRegisterradiobutton
	 * Purpose     : --- ----- --- Radio button will be clicked in checkout page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void CheckoutRegisterradiobutton(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration radio button in checkout should be clicked");
		try{

			click(locator_split("rdcheckoutregistrationradio"));
			waitForPageToLoad(20);
			System.out.println("Registration radio button is clicked");
			Reporter.log("PASS_MESSAGE:- Registration radio button in checkout is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration radio button in checkout is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("rdcheckoutregistrationradio").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- ClickPrivacyPolicycheckbox
	 * Purpose     : --- ----- --- checks the privacy policy check box in checout registration
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void ClickPrivacyPolicycheckbox(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Privacy Policy checkbox should be checked");
		try{

			click(locator_split("rdcheckoutprivacypolicy"));
			System.out.println("Privacy Policy checkbox is checked");
			Reporter.log("PASS_MESSAGE:- Privacy Policy checkbox is checked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Privacy Policy checkbox is not checked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("rdcheckoutprivacypolicy").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- ClickChecoutSubmitbutton
	 * Purpose     : --- ----- --- Clicks submit button in checkout
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void ClickChecoutSubmitbutton(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Checkout Registration submit button should be clicked");
		try{

			click(locator_split("btncheckoutregistrationsubmit"));
			System.out.println("Checkout Registration submit button is clicked");
			Reporter.log("PASS_MESSAGE:- Checkout Registration submit button is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Checkout Registration submit button is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("btncheckoutregistrationsubmit").toString().replace("By.", " ")
					+ " not found");
		}

	}


	/* Method Name : --- ----- --- SetCheckoutRegistrationCIFNIIF
	 * Purpose     : --- ----- --- Sets the Registration CIFNIF in check out page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationCIFNIF(String CIF){
		String CIFNIF = getValue(CIF);
		String countryspecific_cifnif = "Spain";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration CIFNIF should be entered as "+CIFNIF);
		try{
			if((countryspecific_cifnif).contains(countries.get(countrycount))){
				sendKeys(locator_split("txtcheckoutregistrationCIFNIF"),CIFNIF);
				System.out.println("Registration CIFNIF is entered as "+CIFNIF);
				Reporter.log("PASS_MESSAGE:- Registration CIFNIF is entered as "+CIFNIF);
			}else
			{
				Reporter.log("PASS_MESSAGE:- Registration CIFNIF is not applicable to " + country);
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration CIFNIF is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("checkoutregistrationCIFNIF").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- ClickChecoutInsertBillingInfo
	 * Purpose     : --- ----- --- Clicks InsertBillingInfo button in checkout
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void ClickChecoutInsertBillingInfobutton(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Checkout InsertBillingInfo submit button should be clicked");
		try{

			click(locator_split("btncheckoutInsertBillingInfo"));
			System.out.println("Checkout InsertBillingInfo submit button is clicked");
			Reporter.log("PASS_MESSAGE:- Checkout InsertBillingInfo submit button is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Checkout InsertBillingInfo submit button is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("btncheckoutInsertBillingInfo").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- verifyBillingWithDeliveryAddress
	 * Purpose     : --- ----- --- Compares and verifies both Billing and Delivery Address are same
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- --- Krishnamurthy
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void verifyBillingWithDeliveryAddress(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Both Billing and Delivery address should be same");
		try{
			String BillingAddress = driver.findElement(locator_split("txtBillingAddress")).getText().toString();
			click(locator_split("btnDeliverytab"));
			String DeliveryAddress = driver.findElement(locator_split("txtDeliveryAddress")).getText().toString();
			if(BillingAddress.equals(DeliveryAddress)){
				System.out.println("Both Billing and Delivery address are same");
				Reporter.log("PASS_MESSAGE:- Both Billing and Delivery address are same");
			}
			else{
				Reporter.log("FAIL_MESSAGE:- Billing and Delivery address are not same");
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Element "+elementProperties.getProperty("txtBillingAddress")+" "+elementProperties.getProperty("txtDeliveryAddress")+" "+elementProperties.getProperty("btnDeliverytab"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtAddtoCartPage")
					+ " not found");

		}
	}


	/* Method Name : --- ----- --- setcheckoutComment
	 * Purpose     : --- ----- --- Sets the Checkout Comment in check out page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void setcheckoutComment(String text){
		String comment = getValue(text);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Checkout Comment should be entered as "+comment);
		try{

			sendKeys(locator_split("txtComment"),comment);
			System.out.println("Checkout Comment is entered as "+comment);
			Reporter.log("PASS_MESSAGE:- Checkout Comment is entered as "+comment);
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Checkout Comment is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtComment").toString().replace("By.", " ")
					+ " not found");
		}

	}


	public void verifyEditAddresslinkPresent(String text){
		String Text = getValue(text);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Text);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- link text -"+Text+" should be present");
		try{
			if(getText(locator_split("lkEditAddress")).equalsIgnoreCase(Text)){
				Reporter.log("PASS_MESSAGE:- link text -"+Text+" is present");
				System.out.println("link text -"+Text+" is present");
			}else {
				Reporter.log("FAIL_MESSAGE:- The Page dosent contain title -"+Text);
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- link text -"+Text+" is not present");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("lkEditAddress")
					+ " not found");

		}
	}


	/* Method Name : --- ----- --- clickCreditCardradio
	 * Purpose     : --- ----- --- Clicks the Credit Card radio button
	 * Parameter   : --- ----- --- NA
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- --- Bala 
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void clickCreditCardradio(){		
		String countrygroup_cc = "PresselAustria";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- The CreditCard radio button should be clicked");
		try{
			if(!(countrygroup_cc).contains(countries.get(countrycount))){
				click(locator_split("rdcreditcardradiobuton"));
				Reporter.log("FAIL_MESSAGE:- The CreditCard radio button is  clicked");
			}else{
				Reporter.log("FAIL_MESSAGE:- The CreditCard radio button is not clicked");
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- The CreditCard radio button is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("rdcreditcardradiobuton").toString().replace("By.", " ")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- SetCreditCarddetails
	 * Purpose     : --- ----- --- Enters all the details of Credit Card
	 * Parameter   : --- ----- --- ccnumber, ccname, ccmonth, ccyear, ccseccode
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- --- Krishnamurthy
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */


	public void SetCreditCarddetails(String ccnumber, String ccname, String ccmonth, String ccyear, String ccseccode){
		String countrygroup_ccdetails= "UK,PresselAustria";
		String CCnum = getValue(ccnumber);
		String CCName = getValue(ccname);
		String CCMonth = getValue(ccmonth);
		String CCYear = getValue(ccyear);
		String CCSecCode = getValue(ccseccode);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+CCnum+", "+CCName+", "+CCMonth+", "+CCYear+", "+CCSecCode);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- The CreditCard details should be entered");
		try{
			if(!(countrygroup_ccdetails).contains(countries.get(countrycount))){
				switchframe("TokenizationPage");
				sleep(2000);
				sendKeys(locator_split("txtCCnumber"), CCnum);
				clearWebEdit(locator_split("txtCCname"));
				sendKeys(locator_split("txtCCname"), CCName);
				sendKeys(locator_split("dpCCMonth"), CCMonth);
				sendKeys(locator_split("dpCCYear"), CCYear);
				clearWebEdit(locator_split("txtCCcode"));
				sendKeys(locator_split("txtCCcode"), CCSecCode);						

				Reporter.log("PASS_MESSAGE:- The CreditCard details are entered");
			}
			else
			{
				Reporter.log("PASS_MESSAGE:- The  new CreditCard details are applicable to this " +country);
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- The CreditCard details are not entered");
			throw new NoSuchElementException("One of the element with "
					+ elementProperties.getProperty("txtCCnumber").toString().replace("By.", " ")
					+ elementProperties.getProperty("txtCCname").toString().replace("By.", " ")
					+ elementProperties.getProperty("dpCCMonth").toString().replace("By.", " ")
					+ elementProperties.getProperty("dpCCYear").toString().replace("By.", " ")
					+ elementProperties.getProperty("txtCCcode").toString().replace("By.", " ")
					+ " are not found");

		}
	}


	public void UncheckRememberCreditcardData(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Credit card Remember Checkbox should be submitted");
		try{
			click(locator_split("chkPaymentRemeber"));
			Reporter.log("PASS_MESSAGE:- Credit card Remember Checkbox is Unchecked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Credit card Remember Checkbox is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("chkPaymentRemeber").toString().replace("By.", " ")
					+ " not found");
		}
	}



	public void SetCreditCarddetailsold(String ccnumber, String ccname, String ccmonth, String ccyear, String ccseccode){
		String countrygroup_oldcreditcard= "UK";
		String CCnum = getValue(ccnumber);
		String CCName = getValue(ccname);
		String CCMonth = getValue(ccmonth);
		String CCYear = getValue(ccyear);
		String CCSecCode = getValue(ccseccode);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+CCnum+", "+CCName+", "+CCMonth+", "+CCYear+", "+CCSecCode);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- The CreditCard details should be entered");
		try{
			if((countrygroup_oldcreditcard).contains(countries.get(countrycount))){

				switchframe("iframepsp");

				if(isExist(locator_split("imgMasterCard"))){

					clickMastercard();

				}

				sendKeys(locator_split("txtCCnumberold"), CCnum);
				sendKeys(locator_split("txtCCnameold"), CCName);
				sendKeys(locator_split("dpCCMonthold"), CCMonth);
				sendKeys(locator_split("dpCCYearold"), CCYear);
				sendKeys(locator_split("txtCCcodeold"), CCSecCode);
				Reporter.log("PASS_MESSAGE:- The CreditCard details are entered");
			}
			else{
				Reporter.log("PASS_MESSAGE:- The CreditCard details are entered");
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- The CreditCard details are not entered");
			throw new NoSuchElementException("One of the element with "
					+ elementProperties.getProperty("txtCCnumberold").toString().replace("By.", " ")
					+ elementProperties.getProperty("txtCCnameold").toString().replace("By.", " ")
					+ elementProperties.getProperty("dpCCMonthold").toString().replace("By.", " ")
					+ elementProperties.getProperty("dpCCYearold").toString().replace("By.", " ")
					+ elementProperties.getProperty("txtCCcodeold").toString().replace("By.", " ")
					+ " are not found");

		}
	}


	/*public void PaymentSubmit(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- The CreditCard Payment should be submitted");
		try{
			click(locator_split("btnPaymentSubmit"));
			Reporter.log("PASS_MESSAGE:- The CreditCard payment should be clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- The CreditCard payment button is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("btnPaymentSubmit").toString().replace("By.", " ")
					+ " not found");

		}
	}*/


	/* Method Name : --- ----- --- PaymentSubmit
	 * Purpose     : --- ----- --- Clicks the submit button after the details are entered in credit card fields
	 * Parameter   : --- ----- --- NA
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- --- Bala 
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void PaymentSubmit(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- The CreditCard Payment submit should be submitted");
		try{
			click(locator_split("btnPaymentSubmit"));
			//Reporter.log("PASS_MESSAGE:- The CreditCard payment clicked");
			if(isExist(locator_split("btnPaymentSubmit"))){

				click_submitorder_creditcard();

			}
			if(isExist(locator_split("txtPaymentsuccess"))){
				Reporter.log("PASS_MESSAGE:- The CreditCard payment submit is clicked");
			}


		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- The CreditCard payment button is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("btnPaymentSubmit").toString().replace("By.", " ")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- CashonDeliverySubmit
	 * Purpose     : --- ----- --- CashonDeliverySubmit submit button after the details are entered in credit card fields
	 * Parameter   : --- ----- --- NA
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- --- Bala 
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void CashonDeliverySubmit(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Cashondelivery should be submitted");
		try{

			click(locator_split("btnsubmitorder"));

			if(isExist(locator_split("btnsubmitorder"))){

				click_submitorder_creditcard();

			}


			Reporter.log("PASS_MESSAGE:- Cashondelivery should be clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Cashondelivery button is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("btnsubmitorder").toString().replace("By.", " ")
					+ " not found");

		}
	}



	/* Method Name : --- ----- --- PaymentSuccess
	 * Purpose     : --- ----- --- Validates the Text after the payment is done
	 * Parameter   : --- ----- --- String- Test to be verified
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- --- Bala
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */
	public void PaymentSuccess(String text){
		String Text = getValue(text);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Text);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- The Payment success message - '"+Text+"' should be displayed");
		try{
			if(getText(locator_split("txtPaymentsuccess")).equalsIgnoreCase(Text)){
				Reporter.log("PASS_MESSAGE:- The Payment success message - '"+Text+"' is displayed");
				System.out.println("Verified the Text -"+Text);
			}else {
				Reporter.log("FAIL_MESSAGE:- The Payment success message - '"+Text+"' is not displayed");
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- The Payment success message - '"+Text+"' is not displayed");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtPaymentsuccess").toString().replace("By.", " ")
					+ " not found");

		}
	}



	/* Method Name : --- ----- --- VerifyRecapBillinfo
	 * Purpose     : --- ----- --- Verifies the Billing information in Recap page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 11/06/2015
	 * Created By  : --- ----- --- Krishnamurthy
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void VerifyRecapBillinfo(){
		String BillingName = getValue("FirstName")+" "+getValue("LastName");
		String Companyname = getValue("CompanyName");
		String Address = getValue("Address");
		String ZipCity = getValue("Zip")+" , "+getValue("City");
		String CardType= getValue("CardType");

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:-  shopping cart image link should be clicked");


		try{
			String[] BD=getWebTableData(locator_split("txtRecapBillinfo"), locator_split("txtRecapBillinfodata"));
			if(BD[1].equalsIgnoreCase(BillingName)&&BD[2].equalsIgnoreCase(Companyname)
					&&BD[3].equalsIgnoreCase(Address)&&BD[5].equalsIgnoreCase(ZipCity)
					&&BD[6].contains(CardType)){
				System.out.println("The data from Web matches with Excel Registration data");
				Reporter.log("PASS_MESSAGE:- The data from Web matches with Excel Registration data");
			}
			else{
				Reporter.log("FAIL_MESSAGE:- The data from Web dosent match with Excel Registration data");
				throw new Exception("The data from Web dosent match with Excel Registration data");
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- shopping cart image link is not clicked  ");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("txtRecapBillinfo")
					+ elementProperties.getProperty("txtRecapBillinfodata")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- logoutmousehover
	 * Purpose     : --- ----- --- Mouse the move over to account info
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- --- Krishnamurthy
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void logout(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Mouse should be hovered on login icon");
		try{
			//mousehover(locator_split("LoginLogout"));
			click(locator_split("lnkMyAccounttip"));
			click(locator_split("btnlogout"));
			Reporter.log("PASS_MESSAGE:- Mouse is hovered on accountInfo and logout is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Mouse is not hovered/logout with Webelement "+elementProperties.getProperty("LoginLogout")+" or "+elementProperties.getProperty("btnlogout")+" is not found");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("lnkMyAccounttip")
					+ elementProperties.getProperty("btnlogout")
					+ " not found");

		}
	}


	/* Method Name : --- ----- --- SetCheckoutRegistrationTitle
	 * Purpose     : --- ----- --- Sets the Registration title in check out page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationTitle(String prefixname){
		String prefix = getValue(prefixname);
		String countrygroup_prefix ="Denmark,Norway,Sweden,Spain";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+prefix);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration Tile should be selected as '"+prefix+"' ");
		try{
			if(!(countrygroup_prefix).contains(countries.get(countrycount))){
				selectList(locator_split("drpcheckoutregistrationprefix"),prefix);
				System.out.println("Registration Tile is selected as '"+prefix+"' ");
				Reporter.log("PASS_MESSAGE:- Registration Tile is selected as '"+prefix+"' ");
			} else {
				Reporter.log("PASS_MESSAGE:- Registration Tile is not applicable to " +country);
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration Tile is not selected as '"+prefix+"' ");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("drpcheckoutregistrationprefix").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- SetCheckoutRegistrationFirstname
	 * Purpose     : --- ----- --- Sets the Registration first name in check out page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationFirstname(String firstname){
		String Firstname = getValue(firstname);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Firstname);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration First name should be entered as '"+Firstname+"' ");
		try{
			sleep(2000);
			sendKeys(locator_split("txtcheckoutregistrationfirstname"),Firstname);
			System.out.println("Registration first name is entered as '"+Firstname+"' ");
			Reporter.log("PASS_MESSAGE:- Registration first name is entered as '"+Firstname+"' ");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration first name is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtcheckoutregistrationfirstname").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- SetCheckoutRegistrationLastname
	 * Purpose     : --- ----- --- Sets the Registration last name in check out page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationLastname(String lastname){
		String Lastname = getValue(lastname);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Lastname);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration last name should be entered as '"+Lastname+"' ");
		try{

			sendKeys(locator_split("txtcheckoutregistrationlastname"),Lastname);
			System.out.println("Registration last name is entered as '"+Lastname+"' ");
			Reporter.log("PASS_MESSAGE:- Registration last name is entered as '"+Lastname+"' ");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration last name is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtcheckoutregistrationlastname").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- SetCheckoutRegistrationcompanyname
	 * Purpose     : --- ----- --- Sets the Registration company name in check out page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationcompanyname(String compname){
		String Companyname = getValue(compname);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Companyname);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration company name should be entered as '"+Companyname+"' ");
		try{

			sendKeys(locator_split("txtcheckoutregistrationcompanyname"),Companyname);
			System.out.println("Registration company name is entered as '"+Companyname+"' ");
			Reporter.log("PASS_MESSAGE:- Registration company name is entered as '"+Companyname+"' ");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration company name is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtcheckoutregistrationcompanyname").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- SetCheckoutRegistrationHousenumber
	 * Purpose     : --- ----- --- Sets the Registration house number in check out page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationHousenumber(String hosuenumber){
		String Hosuenumber = getValue(hosuenumber);
		String countrygroup_houseno = "Netherland";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Hosuenumber);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration House number should be entered as '"+Hosuenumber+"' ");
		try{
			if((countrygroup_houseno).contains(countries.get(countrycount))){
				sendKeys(locator_split("txtcheckoutregistrationhousenumber"),Hosuenumber);
				System.out.println("Registration house number is entered as '"+Hosuenumber+"' ");
				Reporter.log("PASS_MESSAGE:- Registration house number is entered as '"+Hosuenumber+"' ");
			}
			else
			{   
				sendKeys(locator_split("txtcheckoutregistrationaddress"),Hosuenumber);
				System.out.println("Registration Address is entered as "+Hosuenumber);
				Reporter.log("PASS_MESSAGE:- Registration Address is entered as "+Hosuenumber);

			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration house number is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtcheckoutregistrationhousenumber").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- SetCheckoutRegistrationZip
	 * Purpose     : --- ----- --- Sets the Registration zip in check out page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationZip(String zip){
		String Zip = getValue(zip);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:- "+Zip);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration zip should be entered as '"+Zip+"' ");
		try{

			sendKeys(locator_split("txtcheckoutregistrationZip"),Zip);
			System.out.println("Registration zip is entered as '"+Zip+"' ");
			Reporter.log("PASS_MESSAGE:- Registration zip is entered as '"+Zip+"' ");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration zip is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtcheckoutregistrationZip").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- SetCheckoutRegistrationPhonecode_number
	 * Purpose     : --- ----- --- Sets the phone code and number in check out page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationPhonecode_number(String code, String phoneno){
		String Code = getValue(code);
		String Phoneno = getValue(phoneno);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:- "+Code+" , "+Phoneno);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration Phone code and Phone number should be entered as '"+Code+"' , '"+Phoneno+"' ");
		try{
			if((countrygroup_phoneNo).contains(countries.get(countrycount))){
				sendKeys(locator_split("txtcheckoutregistrationPhonenumber"),Phoneno);
			}
			else{
				sendKeys(locator_split("txtcheckoutregistrationPhonecode"),Code);
				sendKeys(locator_split("txtcheckoutregistrationPhonenumber"),Phoneno);
			}


			System.out.println("Registration Phone code and number is entered as '"+Code+"' , '"+Phoneno+"' ");
			Reporter.log("PASS_MESSAGE:- Registration Phone code and number is entered as '"+Code+"' , '"+Phoneno+"' ");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration Phone code and number is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtcheckoutregistrationPhonecode").toString().replace("By.", " ")
					+ " / "+elementProperties.getProperty("txtcheckoutregistrationPhonenumber").toString().replace("By.", " ")+
					" not found");
		}

	}



	/* Method Name : --- ----- --- ClickChecoutsubmitcontinue
	 * Purpose     : --- ----- --- Clicks submit continue button in checkout
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void ClickChecoutsubmitcontinue(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Checkout Registration submit continue button should be clicked");
		try{

			if(isElementPresent(locator_split("btncheckoutregistrationsubmitcontinue"))){
				click(locator_split("btncheckoutregistrationsubmitcontinue"));
			}else{
				click(locator_split("chkcheckoutregistrationconfirmaddress"));
			}
			System.out.println("Checkout Registration submit continue button is clicked");
			Reporter.log("PASS_MESSAGE:- Checkout Registration submit continue button is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Checkout Registration submit continue button is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("btncheckoutregistrationsubmitcontinue").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- clicklogoutiflogin
	 * Purpose     : --- ----- --- will get logged out if user is already logged in
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */   

	public void clicklogoutiflogin(String welcome){
		String welcomemess = getValue(welcome);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Logout Button should be clicked if its logged in");
		try{
			if(getAndVerifyPartialText(locator_split("Regwelcomemsg"),welcomemess)) {                                
				waitForElement(locator_split("Pancakemenu"));
				click(locator_split("Pancakemenu"));
				waitForElement(locator_split("Logoutbutton"));
				click(locator_split("Logoutbutton"));
				click(locator_split("Logoutuser"));
				waitForPageToLoad(1000);
				waitForElement(locator_split("Pancakemenu"));
				System.out.println("User has been logged out");
				Reporter.log("PASS_MESSAGE:- User has been logged out");
			}else{	
				System.out.println("User is not logged in");
				Reporter.log("PASS_MESSAGE:- User is not logged in");
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Logout button is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("Logoutbutton").toString().replace("By.", " ")
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- Enterloginpassword
	 * Purpose     : --- ----- --- Enters login password for existing user login
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void Enterloginpassword(String PWD){
		String Password = getValue(PWD);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Password);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Password should be enterd in Login");
		try{
			sendKeys(locator_split("loginpassword"), Password);
			System.out.println("password is entered");
			Reporter.log("PASS_MESSAGE:- Password is entered in Login field");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Password is not enterd in Login with WebElement "+elementProperties.getProperty("loginpassword"));
			e.printStackTrace();
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("loginpassword").toString().replace("By.", " ")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- clickSearchButton
	 * Purpose     : --- ----- --- will click the search button in home page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void clickSearchButton(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:-  SearchButton should be clicked");
		try{
			sleep(3000);
			waitforElementVisible(locator_split("BybtnSearch"));
			click(locator_split("BybtnSearch"));
			waitForPageToLoad(100);
			System.out.println("SearchButton is clicked");
			Reporter.log("PASS_MESSAGE:- SearchButton is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- SearchButton is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("BybtnSearch").toString().replace("By.", " ")
					+ " not found");

		}

	}


	/* Method Name : --- ----- --- ClickOrderByItemLink
	 * Purpose     : --- ----- --- will click the OrderByItemLink in home page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void ClickOrderByItemLink(){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:-  OrderByItem Link should be clicked");
		try{
			sleep(1000);
			waitforElementVisible(returnByValue(getValue("OrderbyItemLinkText")));
			click(returnByValue(getValue("OrderbyItemLinkText")));
			waitForPageToLoad(100);
			System.out.println("OrderByItem Link is clicked");
			Reporter.log("PASS_MESSAGE:- OrderByItem  is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- OrderByItem Link is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty(getValue("OrderbyItemLinkText")).toString().replace("By.", " ")
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- OrderByitemNumber_add to cart
	 * Purpose     : --- ----- ---  
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void OrderByitemNumber(String item){
		String orderbyitemnumber = getValue(item);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+orderbyitemnumber);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:-  OrderByitemnumber should be Added");
		try{

			waitforElementVisible(locator_split("txtItemno"));
			sendKeys(locator_split("txtItemno"), orderbyitemnumber);
			sleep(2000);
			click(locator_split("btnAddtoList"));
			sleep(3000);
			click(locator_split("btnAdditemtocart"));
			waitForPageToLoad(20);
			System.out.println("orderByitemnumber is added.");
			Reporter.log("PASS_MESSAGE:- OrderByitemnumber is added");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- orderByitemnumber is not added");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("txtItemno").toString().replace("By.", " ")
					+ elementProperties.getProperty("btnAdditemtocart").toString().replace("By.", " ")
					+ " not found");


		}

	}



	/* Method Name : --- ----- --- AddToCartListPage
	 * Purpose     : --- ----- --- Add to cart button should be clicked.
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void AddToCartListPage(String Item){
		String Addtocartlistitem = getValue(Item);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:-  Addtocartlist should be Added");
		try{
			waitForElement(locator_split("BylnkFavListAddToCart"));
			clickObjectByMatchingPropertyValue(locator_split("BylnkFavListAddToCart"),propId,Addtocartlistitem);
			waitForPageToLoad(20);
			System.out.println("Addtocartlist is Added.");
			Reporter.log("PASS_MESSAGE:- Addtocartlist is Added");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Addtocartlist is not Added");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("BylnkFavListAddToCart").toString().replace("By.", " ")
					+ " not found");
		}
	}






	/* Method Name : --- ----- --- clickshoppingcart
	 * Purpose     : --- ----- ---  
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void clickshoppingcart(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:-  shopping cart link should be clicked");
		try{
			waitForElement(locator_split("lnkShoppingCart"));
			click(locator_split("lnkShoppingCart"));
			waitForPageToLoad(20);
			System.out.println("shopping cart link is clicked");
			Reporter.log("PASS_MESSAGE:- shopping cart link is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- shopping cart link is not clicked  ");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("lnkShoppingCart").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- cashondelivery_submit
	 * Purpose     : --- ----- ---  cash on delivery and submit order
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void cashondelivery_submit(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:-  shopping cart link should be clicked");
		try{

			waitforElementVisible(locator_split("cashondelivery"));
			click(locator_split("cashondelivery"));
			waitForPageToLoad(100);
			waitforElementVisible(locator_split("submitorder"));
			click(locator_split("submitorder"));
			waitForPageToLoad(20);
			System.out.println("shopping cart link is clicked.");
			Reporter.log("PASS_MESSAGE:- shopping cart link is clicked");
		}
		catch(Exception e){

			Reporter.log("FAIL_MESSAGE:- shopping cart link is not clicked  ");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("submitorder").toString().replace("By.", " ")
					+ " not found");
		}

	}





	/* Method Name : --- ----- --- verifyOrderRecape page
	 * Purpose     : --- ----- --- verifyOrderRecape page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */       
	public void verifyOrderRecape(String orderrecape1){

		String order = getValue(orderrecape1);

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- orderrecape page should be displayed");
		try{

			if(getAndVerifyPartialText(locator_split("orderrecape"),order)) {                                
				waitForPageToLoad(20);
				Reporter.log("PASS_MESSAGE:- orderrecape page is Displayed");
			}

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- orderrecape is not displayed "+elementProperties.getProperty("orderrecape"));
			throw new NoSuchElementException("The element with " + elementProperties.getProperty("orderrecape").toString().replace("By.", " ") + " not found");

		}


	}



	/* Method Name : --- ----- --- Clearcartitems
	 * Purpose     : --- ----- --- Clears all the items form the cart
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 11/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void Clearcartitems(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Items in the cart should be removed");
		try{
			if(!getText(locator_split("lnkShoppingCart")).equalsIgnoreCase("0")){
				//sleep(4000);
				//waitforElementVisible(locator_split("lnkShoppingCart"));
				click(locator_split("lnkShoppingCart"));
				List<WebElement> elements = listofelements(By.linkText(getValue("Trash")));
				for(int i=0; i<elements.size(); i++){
					sleep(1000);
					click(By.linkText(getValue("Trash")));;
					waitForPageToLoad(70);

				}
				System.out.println("Items are removed from cart");
				Reporter.log("PASS_MESSAGE:- Items in the cart are removed");
			}else{
				System.out.println("No items are present in the cart");
				Reporter.log("PASS_MESSAGE:- No Items are present in the cart");
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Items in the cart are not removed");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("lnkShoppingCart").toString().replace("By.", " ")+" "
					+ elementProperties.getProperty("returnByValue()").toString().replace("By.", " ")+" "
					+ " not found");

		}
	}









	/* Method Name : --- ----- --- SetCheckoutRegistrationBuildingnumber
	 * Purpose     : --- ----- --- Sets the Registration Building number in check out page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationBuildingnumber(String housenumber){
		String Hosuenumber = getValue(housenumber);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration Building number should be entered as "+Hosuenumber);
		try{

			sendKeys(locator_split("checkoutregistrationbuildingnumber"),Hosuenumber);
			System.out.println("Registration Building number is entered as "+Hosuenumber);
			Reporter.log("PASS_MESSAGE:- Registration Building number is entered as "+Hosuenumber);
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration Building number is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("checkoutregistrationbuildingnumber").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- SetCheckoutRegistrationCity
	 * Purpose     : --- ----- --- Sets the Registration City in check out page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationCity(String city){
		String City = getValue(city);
		String countrygroup_city="Netherland";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration City should be entered as "+City);
		try{
			if(!(countrygroup_city).contains(countries.get(countrycount)))
			{
				sendKeys(locator_split("txtcheckoutregistrationCity"),City);
				System.out.println("Registration City is entered as "+City);
				Reporter.log("PASS_MESSAGE:- Registration house number is entered as "+City);
			}
			else{
				Reporter.log("PASS_MESSAGE:- Registration City is not applicable to " +countrygroup_city);
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration City is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtcheckoutregistrationCity").toString().replace("By.", " ")
					+ " not found");
		}

	}


	/* Method Name : --- ----- --- SetCheckoutRegistrationPhonecode_number
	 * Purpose     : --- ----- --- Sets the phone code and number in check out page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationPhonecode_number(String phoneno){

		String Phoneno = getValue(phoneno);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration Phone code and number should be entered as , "+Phoneno);
		try{


			sendKeys(locator_split("txtcheckoutregistrationPhonenumber"),Phoneno);
			System.out.println("Registration Phone code and number is entered as , "+Phoneno);
			Reporter.log("PASS_MESSAGE:- Registration Phone code and number is entered as , "+Phoneno);
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration Phone number is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("checkoutregistrationPhonenumber").toString().replace("By.", " ")
					+ " / "+elementProperties.getProperty("checkoutregistrationPhonenumber").toString().replace("By.", " ")+
					" not found");
		}

	}



	/* Method Name : --- ----- --- SetCheckoutRegistrationAddress
	 * Purpose     : --- ----- --- Sets the Registration Address in check out page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationaddress(String address){
		String Address = getValue(address);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration Address should be entered as "+Address);
		try{

			sendKeys(locator_split("txtcheckoutregistrationaddress"),Address);
			System.out.println("Registration Address is entered as "+Address);
			Reporter.log("PASS_MESSAGE:- Registration Address is entered as "+Address);

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration Address is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtcheckoutregistrationaddress").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- SetCheckoutRegistrationCity
	 * Purpose     : --- ----- --- Sets the Registration City in check out page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationcity(String city){
		String Address = getValue(city);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration City should be entered as "+Address);
		try{

			sendKeys(locator_split("BycheckoutregistrationCity"),Address);
			System.out.println("Registration City is entered as "+Address);
			Reporter.log("PASS_MESSAGE:- Registration City is entered as "+Address);
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration City is not entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("checkoutregistrationCity").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- ---Click Checkout Registration Continue button in PopUppage
	 * Purpose     : --- ----- --- Click Checkout Registration Continue button in PopUppage
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void checkoutcontinuebutton( ){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- continue Registartion  button clicked in popu page");
		try{

			waitforElementVisible(locator_split("checkoutregistrationcontinue"));
			click(locator_split("checkoutregistrationcontinue"));

			Reporter.log("PASS_MESSAGE:- ccontinue Registartion  button clicked in popu page");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- continue Registartion  button is not clicked in popu page"+elementProperties.getProperty("_Regcontinuebutton"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("checkoutregistrationcontinue").toString().replace("By.", " ")
					+ " not found");

		}


	}

	/* Method Name : --- ----- --- ClickAddressErrorCheckBoxcheckbox
	 * Purpose     : --- ----- --- ClickAddressErrorCheckBoxcheckbox to submit reg checkout
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---  Natarajan 
	 * Modified Date:--- ----- ---  07/07/2015
	 */ 

	public void ClickAddressErrorCheckBoxcheckbox(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Address Error check box should be clicked");
		try{
			if((countryAddressErrChkbox).contains(countries.get(countrycount)))
			{
				click(locator_split("chkcheckoutAddressErrorCheckBox"));
				System.out.println("Address Error check box is clicked");
				Reporter.log("PASS_MESSAGE:- Address Error check box is clicked");
			}
			else{
				Reporter.log("PASS_MESSAGE:- Address Error check box is not applicable ");
			}

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Address Error check box is not checked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("checkoutAddressErrorCheckBox").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- Submit order
	 * Purpose     : --- ----- --- Click the submit order for germany
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void submitorder( ){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- submit order link is clicked entered");
		try{

			waitforElementVisible(locator_split("submitorder"));
			click(locator_split("submitorder"));
			waitForPageToLoad(20);
			System.out.println("submit  link clicked");
			Reporter.log("PASS_MESSAGE:-submit  link clicked  ");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- unable to click submit button" );

		}
	}











	/* Method Name : --- ----- --- SetCheckoutRegistrationemployeecount
	 * Purpose     : --- ----- --- Sets the Registration employee count
	 * Parameter   : --- ----- --- String- Empno to be entered
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- --- Bala
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationemployeecount(String empno){
		String Employeenum = getValue(empno);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration Tile should be selected as "+Employeenum);
		try{

			sendKeys(locator_split("txtRegistrationEmployees"),Employeenum);
			System.out.println("Registration Tile is selected as "+Employeenum);
			Reporter.log("PASS_MESSAGE:- Registration Tile is Entered as "+Employeenum);
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration Tile is not Entered");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtRegistrationEmployees").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- SetCheckoutRegistrationBusinessDesc
	 * Purpose     : --- ----- --- Selects the year from the dropdown
	 * Parameter   : --- ----- --- String- Year to be selected
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- --- Bala
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationestablished_year(String estyear){
		String Establishedyear = getValue(estyear);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration Tile should be selected as "+Establishedyear);
		try{

			selectList(locator_split("dpRegistrationBusinessFoundation"),Establishedyear);
			System.out.println("Registration Tile is selected as "+Establishedyear);
			Reporter.log("PASS_MESSAGE:- Registration Tile is selected as "+Establishedyear);
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration Tile is not selected as "+Establishedyear);
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("dpRegistrationBusinessFoundation").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- SetCheckoutRegistrationBusinessDesc
	 * Purpose     : --- ----- --- Sets the Business Description
	 * Parameter   : --- ----- --- String- Test to be entered
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- --- Krishnamurthy
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationBusinessDesc(String Businesstype){
		String businetype = getValue(Businesstype);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration Tile should be selected as "+businetype);
		try{

			sendKeys(locator_split("txtRegistrationBusinessDesc"),businetype);
			System.out.println("Registration Tile is selected as "+businetype);
			Reporter.log("PASS_MESSAGE:- Registration Tile is selected as "+businetype);
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration Tile is not selected as "+businetype);
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtRegistrationBusinessDesc").toString().replace("By.", " ")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- VerifyRememberme
	 * Purpose     : --- ----- --- verifies remember me is checked or not in registration
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- --- Krishnamurthy
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */
	public void VerifyRememberme(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Remember me should be unchecked");
		try{
			if(driver.findElement(locator_split("chkRememberme")).isSelected()){
				System.out.println("Rememeber me is checked as default");
				Reporter.log("PASS_MESSAGE:- Rememeber me is checked as default");
			}else{
				System.out.println("Rememeber me is not checked as default");
				Reporter.log("PASS_MESSAGE:- Rememeber me is not checked as default");
			}

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Element '"+elementProperties.getProperty("chkRememberme")+"' is not identified");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("chkRememberme").toString().replace("By.", " ")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- UncheckRememberme
	 * Purpose     : --- ----- --- Unchecks the remember me checkbox
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- --- Krishnamurthy
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */
	public void UncheckRememberme(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Remember me should be unchecked");
		try{
			click(locator_split("chkRememberme"));
			Reporter.log("PASS_MESSAGE:- Remember me is unchecked");
			System.out.println("Remember me is unchecked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Remember me is not unchecked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("chkRememberme").toString().replace("By.", " ")
					+ " not found");

		}
	}


	/* Method Name : --- ----- --- VerifyMyAccountRememberme
	 * Purpose     : --- ----- --- verifies remember me is checked or not in registration in My Account Registration
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- --- Krishnamurthy
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */
	public void VerifyMyAccountRememberme(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Remember me should be unchecked");
		try{
			if(driver.findElement(locator_split("chkMyAccountRememberme")).isSelected()){
				System.out.println("Rememeber me is checked as default");
				Reporter.log("PASS_MESSAGE:- Rememeber me is checked as default");
			}else{
				System.out.println("Rememeber me is not checked as default");
				Reporter.log("FAIL_MESSAGE:- Rememeber me is not checked as default");
			}

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Element '"+elementProperties.getProperty("chkMyAccountRememberme")+"' is not identified");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("chkMyAccountRememberme")
					+ " not found");

		}
	}


	/* Method Name : --- ----- --- SetCheckoutRegistrationBusinesstype
	 * Purpose     : --- ----- --- Sets the Registration business type in check out page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void SetCheckoutRegistrationBusinesstype(String Businesstype){
		String businetype = getValue(Businesstype);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Registration Tile should be selected as "+businetype);
		try{

			selectList(locator_split("checkoutregistrationbusinesstype"),businetype);
			System.out.println("Registration Tile is selected as "+businetype);
			Reporter.log("PASS_MESSAGE:- Registration Tile is selected as "+businetype);
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Registration Tile is not selected as "+businetype);
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("checkoutregistrationbusinesstype").toString().replace("By.", " ")
					+ " not found");
		}

	}







	public void verifyAddToCartText(String text){
		String Text = getValue(text);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Text);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- The Page should be navigated to "+Text);

		if(getText(locator_split("txtAddtocartPage")).equalsIgnoreCase(Text)){
			Reporter.log("PASS_MESSAGE:- The Page is navigated and it has Text -"+Text);
			System.out.println("Verified the text -"+Text);
		}else {
			Reporter.log("FAIL_MESSAGE:- The Page dosent contain Text -"+Text);
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtAddtocartPage").toString().replace("By.", " ")
					+ " not found");
		}
	}



	public void verifyShoppingCartText(String text){
		String Text = getValue(text);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Text);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- The Page should be navigated to "+Text);

		if(getText(locator_split("txtShoppingCartHeader")).equalsIgnoreCase(Text)){
			Reporter.log("PASS_MESSAGE:- The Page is navigated and it has Text -"+Text);
			System.out.println("Verified the Text -"+Text);
		}else {
			Reporter.log("FAIL_MESSAGE:- The Page dosent contain Text -"+Text);
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtShoppingCartHeader").toString().replace("By.", " ")
					+ " not found");

		}				
	}

	public void LoginMouseOver(){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account should be Hovered");
		try{
			mousehover(locator_split("LoginLogout"));
			Reporter.log("PASS_MESSAGE:- My Account  is Hovered");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- My Account is not Hovered");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("LoginLogout")
					+ " not found");

		}

	}

	public void clickMyAccounttip(){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account tip should be clicked");
		try{
			sleep(3000);
			click(locator_split("lnkMyAccounttip"));
			Reporter.log("PASS_MESSAGE:- My Account tip  is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- My Account tip is not clicked");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("lnkMyAccounttip")
					+ " not found");

		}

	}


	/* Method Name : --- ----- --- shoppingCartCleanUp
	 * Purpose     : --- ----- --- clean the shopping cart items
	 * Parameter   : --- ----- --- None
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- --- 
	 * Modified By : --- ----- --- 
	 * Modified Date:--- ----- --- 
	 */       
	public void shoppingCartCleanUp(){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Shopping cart should be cleaned");
		try{

			sleep(1000);
			String aQty=getTextandreturn(locator_split("lnkShoppingCart"));
			int iQty=Integer.parseInt(aQty);
			System.out.println(iQty);
			sleep(1000);
			if(iQty!=0){
				//click(locator_split("lnkShoppingCart"));
				clickSpecificElement(returnByValue(getValue("TrashLinkText")),0);
				shoppingCartCleanUp();        	
			} 

			Reporter.log("PASS_MESSAGE:- Removed the Cart Item(s)");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Shopping cart is not cleaned or Error in removing the items from shpping cart");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("lnkShoppingCart")
					+ " not found");

		}
	}

	public By returnByValue(String LinkText){
		return By.linkText(LinkText);
	}

	/* Method Name : --- ----- --- clickNederlands
	 * Purpose     : --- ----- --- Nederalnds link should be clicked
	 * Parameter   : --- ----- --- None
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- --- 
	 * Modified By : --- ----- --- 
	 * Modified Date:--- ----- --- 
	 */   
	public void clickNederlands(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Nederalnds link should be clicked");
		try{

			click(locator_split("lnkNederland"));
			Reporter.log("PASS_MESSAGE:- Nederalnds link is clicked");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Nederalnds link is not clicked");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("lnkNederland")
					+ " not found");

		}

	}




	/* Method Name : --- ----- --- clickFirstQviewbutton
	 * Purpose     : --- ----- --- Qview First Buy button should be clicked
	 * Parameter   : --- ----- --- None
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- --- 
	 * Modified By : --- ----- --- 
	 * Modified Date:--- ----- --- 
	 */  

	public void clickFirstQviewbutton(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Qview First Buy button should be clicked");
		try{

			/*List<WebElement> eles=driver.findElements((locator_split("btnQview")));
			System.out.println(eles.size());
			eles.get(0).click();*/

			click(locator_split("btnQview"));				
			System.out.println("Clicked on the First  Buy button");
			Reporter.log("PASS_MESSAGE:- First Buy Button is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Qview First Buy button is not clicked or Not Available");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("btnQview")
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- ClickQviewAddtoCartbutton
	 * Purpose     : --- ----- --- Qview AddtoCart should be clicked
	 * Parameter   : --- ----- --- None
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- --- 
	 * Modified By : --- ----- --- 
	 * Modified Date:--- ----- --- 
	 */  

	public void ClickQviewAddtoCartbutton(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Qview Add to cart button should be clicked");
		try{
			click(locator_split("btnQviewAddtoCart"));
			Reporter.log("PASS_MESSAGE:- Qview Add to cart button is clicked");
			System.out.println("Clicked the Add to Cart in QView");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Qview Add to cart button is not clicked or Not Available");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("btnQviewAddtoCart")
					+ " not found");

		}

	}
	public void ClickonSupercat(String Text){
		String Data = getValue(Text);			
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Supercat Link should be clicked");
		try{	
			clickSpecificElementContains(locator_split("lnkSupercat"), Data);		
			Reporter.log("PASS_MESSAGE:- Supercat Link is clicked");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Supercat Link is not clicked or Not Available");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("lnkSupercat")
					+ " not found");

		}

	}


	public void ClickonSupercatNext(String Text){
		String Data = getValue(Text);


		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Supercat Next Link should be clicked");
		try{	
			clickSpecificElementContains(locator_split("lnkSupercatNext"), Data);		
			Reporter.log("PASS_MESSAGE:- Supercat Next Link is clicked"); 
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Supercat Next Link is not clicked or Not Available");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("lnkSupercatNext")
					+ " not found");

		}

	}

	public void ClickSuperCatNextItems(){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:-  Supercat Items Link should be clicked");
		try{
			sleep(1000);
			waitforElementVisible(returnByValue(getValue("SupercatNextItems")));
			click(returnByValue(getValue("SupercatNextItems")));
			waitForPageToLoad(100);
			System.out.println("Supercat Items  Link is clicked");
			Reporter.log("PASS_MESSAGE:- Supercat Items link  is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Supercat Items Link is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty(getValue("SupercatNextItems")).toString().replace("By.", " ")
					+ " not found");

		}

	}








	// New User Registration Updates




	/* Method Name : --- ----- --- UncheckMyAccountRememberme
	 * Purpose     : --- ----- --- Unchecks the remember me checkbox in My Account Registration
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- ---   
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */
	public void UncheckMyAccountRememberme(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Remember me should be unchecked");
		try{
			click(locator_split("chkMyAccountRememberme"));
			Reporter.log("PASS_MESSAGE:- Remember me is unchecked");
			System.out.println("Remember me is unchecked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Remember me is not unchecked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("chkMyAccountRememberme")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- ClickMyAccountSubmitbutton
	 * Purpose     : --- ----- --- Clicks submit button in MyAccount page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 08/05/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void ClickMyAccountSubmitbutton(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account Registration submit button should be clicked");
		try{

			click(locator_split("btnMyAccountSubmit"));
			System.out.println("Checkout Registration submit button is clicked");
			Reporter.log("PASS_MESSAGE:- My Account Registration submit button is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- My Account Registration submit button is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("btnMyAccountSubmit")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- ClickMyAccountContinueShopping
	 * Purpose     : --- ----- --- Clicks continue shopping in My Account registration
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void ClickMyAccountContinueShopping(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account Registration continue shopping should be clicked");
		try{

			click(locator_split("btnMyAccountContinueShopping"));
			Thread.sleep(3000);
			System.out.println("My Account Registration continue shopping is clicked");
			Reporter.log("PASS_MESSAGE:- My Account Registration continue shopping is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- My Account Registration continue shopping is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("btnMyAccountContinueShopping")
					+ " not found");
		}

	}
	/* Method Name : --- ----- --- mouseOver_logout
	 * Purpose     : --- ----- --- mouseOver_logout
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 09/06/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void mouseOver_logout(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Mouse should be hovered on logout icon");
		try{

			click(locator_split("logoutmover"));
			Reporter.log("PASS_MESSAGE:- Mouse is hovered on logout");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Mouse is not hovered on logout with Webelement "+elementProperties.getProperty("logoutmover"));
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("logoutmover").toString().replace("By.", " ")
					+ " not found");

		}
	}










	// NotLoggedin Checkout


	public void switchframe(){	

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Item should be searched in the search box");
		try{


			//editSubmit(locator_split("txtSearch"));
			//waitforElementVisible(locator_split("creditcardiframe"));
			String locator = elementProperties.getProperty("creditcardiframe");
			swtichframedriver(locator);
			Reporter.log("PASS_MESSAGE:- switched to frame");
		}

		catch(Exception e){
			//Reporter.log("FAIL_MESSAGE:- Item is not entered in the search box "+elementProperties.getProperty("creditcardiframe"));

		}

		try
		{
			String locator1 = elementProperties.getProperty("oldcreditcardiframe");
			swtichframedriver(locator1);
		}
		catch(Exception e){
			//Reporter.log("FAIL_MESSAGE:- Item is not entered in the search box "+elementProperties.getProperty("creditcardiframe"));
		}
	}

	public void swtichframedriver(String frame)
	{
		driver.switchTo().frame(frame);

	}

	public void click_submitorder_creditcard()
	{

		/*class Local {};
	Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
	Reporter.log("TestStepInput:-"+"NA");
	Reporter.log("TestStepOutput:-"+"NA");
	Reporter.log("TestStepExpectedResult:- The CreditCard Payment should be submitted");
		 */	
		try

		{
			if(isExist(locator_split("btnPaymentSubmit"))){

				WebElement ccardsubmit=driver.findElement(By.id("payment-submit"));
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", ccardsubmit);
				//Reporter.log("PASS_MESSAGE:-submit  button clicked  ");
			}
			if(isExist(locator_split("btnsubmitorder"))){
				WebElement ccardsubmit=driver.findElement(By.cssSelector("input[type='submit']"));
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", ccardsubmit);
				//Reporter.log("PASS_MESSAGE:-submit  button clicked  ");
			}

		}catch(Exception e){
			/*Reporter.log("FAIL_MESSAGE:- The CreditCard payment button is not clicked");
		throw new NoSuchElementException("The element with "
				+ elementProperties.getProperty("btnPaymentSubmit").toString().replace("By.", " ")
				+ elementProperties.getProperty("btnsubmitorder").toString().replace("By.", " ")
				+ " not found");*/

		}

	}

	/* Method Name : --- ----- --- SetManagingDirectorName
	 * Purpose     : --- ----- --- Enter Managing Director information
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */   

	public void SetManagingDirectorName(String itemnum){	
		String searchitem = getValue(itemnum);
		String countrygroup_md="Germany";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+searchitem);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Managing Director name should be entered");
		try{
			//editSubmit(locator_split("txtSearch"));
			if((countrygroup_md).contains(countries.get(countrycount))){
				Thread.sleep(2000);
				waitforElementVisible(locator_split("txtManagingDriector"));
				clearWebEdit(locator_split("txtManagingDriector"));
				sendKeys(locator_split("txtManagingDriector"), searchitem);
				Reporter.log("PASS_MESSAGE:- Managing Director name is entered");
			}
			else
			{
				Reporter.log("PASS_MESSAGE:- Managing Director field is not applicable to " + country);
			}

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Managing Director name is not entered "+elementProperties.getProperty("FiscalCode"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtManagingDriector").toString().replace("By.", " ")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- cashondelivery_submit
	 * Purpose     : --- ----- ---  cash on delivery and submit order
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void cashondeliverysubmit(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:-  Submit order button should be clicked");
		try{
			waitforElementVisible(locator_split("btnsubmitorder"));
			click(locator_split("btnsubmitorder"));
			if(isExist(locator_split("btnsubmitorder")))
			{
				click(locator_split("btnsubmitorder"));
			}

			waitForPageToLoad(20);
			System.out.println("Submit order button is clicked.");
			Reporter.log("PASS_MESSAGE:- Submit order button is clicked");
		}
		catch(Exception e){

			Reporter.log("FAIL_MESSAGE:- Submit order button is not clicked  ");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("btnsubmitorder").toString().replace("By.", " ")
					+ " not found");
		}

	}









	/* Method Name : --- ----- --- searchItemInkandTonner
	 * Purpose     : --- ----- --- searchItem Ink and Tonner
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */   

	public void searchInkAndTonnerItem(String itemnum){	
		String searchitem = getValue(itemnum);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+searchitem);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Item should be searched in the Ink and Tonner search box");
		try{
			//editSubmit(locator_split("txtSearch"));
			waitforElementVisible(locator_split("txtInkSearch"));
			clearWebEdit(locator_split("txtInkSearch"));
			sendKeys(locator_split("txtInkSearch"), searchitem);
			Thread.sleep(2000);
			Reporter.log("PASS_MESSAGE:- Item is searched in the Ink and Tonner search box");
			System.out.println("Ink and Tonner Search item - "+searchitem+" is entered");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Item is not entered in the search box "+elementProperties.getProperty("txtSearch"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtInkSearch").toString().replace("By.", " ")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- clickSubmitSearchButton
	 * Purpose     : --- ----- --- clickSubmitSearchButton
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */   

	public void clickSubmitInkAndTonnerSearchButton(){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Ink and Tonner Search Button should be clicked");
		try{

			waitforElementVisible(locator_split("btnInkSeacrh"));
			click(locator_split("btnInkSeacrh"));
			waitForPageToLoad(10);
			Reporter.log("PASS_MESSAGE:- Ink and Tonner Search Button is clicked");
			System.out.println("Ink and Tonner Search icon is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Ink and Tonner Search Button is not clicked "+elementProperties.getProperty("btnSearchSubmit"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("btnInkSeacrh").toString().replace("By.", " ")
					+ " not found");

		}


	}

	/* Method Name : --- ----- --- searchItemInkandTonner
	 * Purpose     : --- ----- --- searchItem Ink and Tonner
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */   

	public void InkAndTonnerSearchQty(String itemnum){     
		String searchitem = getValue(itemnum);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+searchitem);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Quantity should be entered in quantity box for the first item in searched result.");
		try{
			//editSubmit(locator_split("txtSearch"));
			waitforElementVisible(locator_split("txtInkSearchQty"));
			clearWebEdit(locator_split("txtInkSearchQty"));
			sendKeys(locator_split("txtInkSearchQty"), searchitem);
			Thread.sleep(2000);
			Reporter.log("PASS_MESSAGE:- Quantity is entered in quantity box for the first item in searched result.");
			System.out.println("Quantity is entered in quantity box for the first item in searched result. - "+searchitem+"");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Quantity is not entered in quantity box for the first item in searched result. "+elementProperties.getProperty("txtInkSearchQty"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtInkSearchQty").toString().replace("By.", " ")
					+ " not found");

		}
	}   



	/* Method Name : --- ----- --- Entering taxidentification
	 * Purpose     : --- ----- --- entering the taxidentificaiton number
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---  Natarajan	
	 * Modified Date:--- ----- ---  07/07/2015
	 */   

	public void Taxidentificaiton(String itemnum){	
		String searchitem = getValue(itemnum);
		String countryspecific_tax = "Italy";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+searchitem);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Taxidentificaiton should be entered in the Tax field");
		try{
			//editSubmit(locator_split("txtSearch"));
			if((countryspecific_tax).contains(countries.get(countrycount))){
				Thread.sleep(3000);
				waitforElementVisible(locator_split("Checkouttax"));
				clearWebEdit(locator_split("Checkouttax"));
				sendKeys(locator_split("Checkouttax"), searchitem);
				Reporter.log("PASS_MESSAGE:- Item is searched in the search box");}
			else{
				Reporter.log("PASS_MESSAGE:- Taxidentificaiton is not applicable for this country");
			}

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Item is not entered in the search box "+elementProperties.getProperty("Tax"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("Tax").toString().replace("By.", " ")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- Entering FiscalCode
	 * Purpose     : --- ----- --- entering the FiscalCode number
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */   

	public void FiscalCode(String itemnum){	
		String searchitem = getValue(itemnum);
		String countryspecific_fiscalcode = "Italy,Sweden";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+searchitem);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Fiscalcode should be entered in the fiscal code field");
		try{
			//editSubmit(locator_split("txtSearch"));
			if((countryspecific_fiscalcode).contains(countries.get(countrycount))){
				Thread.sleep(2000);
				waitforElementVisible(locator_split("checkoutFiscalCode"));
				clearWebEdit(locator_split("checkoutFiscalCode"));
				sendKeys(locator_split("checkoutFiscalCode"), searchitem);
				Reporter.log("PASS_MESSAGE:- Fiscalcode is entered ");
			}
			else
			{
				Reporter.log("PASS_MESSAGE:- Item is searched in the search box");
			}

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Item is not entered in the search box "+elementProperties.getProperty("FiscalCode"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("FiscalCode").toString().replace("By.", " ")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- submit checkout tax submit
	 * Purpose     : --- ----- --- submit checkout tax submit
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */   

	public void Checkouttaxsubmit(){	
		String countryspecific_tax = "Italy,Germany,Sweden,Spain";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Item should be searched in the search box");
		try{
			if((countryspecific_tax).contains(countries.get(countrycount))){
				//editSubmit(locator_split("txtSearch"));
				waitforElementVisible(locator_split("checkouttaxsubmit"));
				click(locator_split("checkouttaxsubmit"));
				Reporter.log("PASS_MESSAGE:- Tax submit button is clicked");
			}
			else
			{
				Reporter.log("PASS_MESSAGE:- Tax submit button is not applicable to " +country);
			}

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Item is not entered in the search box "+elementProperties.getProperty("checkoutFiscalCode"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("checkoutFiscalCode").toString().replace("By.", " ")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- click credit card radio button
	 * Purpose     : --- ----- --- clicking the credit card radio button 
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */   

	public void clickcreditcardradiobutton(){	

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Item should be searched in the search box");
		try{
			//editSubmit(locator_split("txtSearch"));
			waitforElementVisible(locator_split("creditcardradiobuton"));
			click(locator_split("creditcardradiobuton"));
			Thread.sleep(2000);
			Reporter.log("PASS_MESSAGE:- Item is searched in the search box");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Item is not entered in the search box "+elementProperties.getProperty("creditcardradiobuton"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("creditcardradiobuton").toString().replace("By.", " ")
					+ " not found");

		}
	}


	/* Method Name : --- ----- --- clickMastercard 
	 * Purpose     : --- ----- --- clickMastercard 
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */   

	public void clickMastercard(){	

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Mastercard should be clicked");
		try{
			//editSubmit(locator_split("txtSearch"));
			waitforElementVisible(locator_split("imgMasterCard"));
			click(locator_split("imgMasterCard"));
			Thread.sleep(2000);
			Reporter.log("PASS_MESSAGE:- Mastercard is clicked");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- imgMasterCard not clicked "+elementProperties.getProperty("imgMasterCard"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("imgMasterCard").toString().replace("By.", " ")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- setUsername
	 * Purpose     : --- ----- --- Sets the Username in the registration page for France
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 05/20/2015
	 * Created By  : --- ----- ---  Kishore Kumar
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */
	public void setUserName(String UserName){
		String UsrName = getValue(UserName);
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

		Date date = new Date();

		String ldDate=(String) dateFormat.format(date);

		//String USR = getValue(Username);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+UsrName);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Username  should be entered in Login");
		try{
			if((countrygroup_username).contains(countries.get(countrycount))){
				sendKeys(locator_split("txtRegistrationUniqueUsername"), UsrName+ldDate);	
				System.out.println("User Name is entered");
				Reporter.log("PASS_MESSAGE:- Username is entered in username  field");
			}
			else
			{
				Reporter.log("PASS_MESSAGE:- Username is not applicable to " + country);
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Username is not entered in Login field with WebElement "+elementProperties.getProperty("txtUserName"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtRegistrationUniqueUsername").toString().replace("By.", " ")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- clickCreateAccountLink
	 * Purpose     : --- ----- --- clickCreateAccountLink
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void clickCreateAccountLink(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Create Account Link should be clicked");
		try{

			waitForElement(locator_split("lnkCreateAccount"));
			click(locator_split("lnkCreateAccount"));  
			sleep(1000);
			Reporter.log("PASS_MESSAGE:- Create Account Link is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Create Account Link is not clicked "+elementProperties.getProperty("lnkCreateAccount"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("lnkCreateAccount").toString().replace("By.", " ")
					+ " not found");
		}
	}


	/* Method Name : --- ----- --- clickQuilllogo
	 * Purpose     : --- ----- --- clickQuilllogo
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void clickQuilllogo(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Staples logo should be clicked");
		try{

			waitforElementVisible(locator_split("imgQuilllogo"));
			waitForPageToLoad(1000);
			click(locator_split("imgQuilllogo"));

			Reporter.log("PASS_MESSAGE:- Staples logo is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- staples logo  is not clicked "+elementProperties.getProperty("imgQuilllogo"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("imgQuilllogo").toString().replace("By.", " ")
					+ " not found");

		}
	}


	/* Method Name : --- ----- --- clickFavoritieslk
	 * Purpose     : --- ----- --- navigating to Favorities page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- --- siva
	 * Modified Date:--- ----- ---
	 */ 

	public void clickFavoritieslink(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:-   Favorities Link should be clicked");
		try{

			waitForElement(locator_split("lnkFavorities"));
			click(locator_split("lnkFavorities"));  
			sleep(1000);
			Reporter.log("PASS_MESSAGE:- Favorities Link is clicked");
			System.out.println("Favourites link clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Favorities is not clicked "+elementProperties.getProperty("lnkFavorities"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("lnkFavorities").toString().replace("By.", " ")
					+ " not found");
		}
	}

	/* Method Name : --- ----- --- createlistlk
	 * Purpose     : --- ----- --- create list with name,description and select remainder notificaiton
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- --- siva
	 * Modified Date:--- ----- ---
	 */ 

	public void createlist(String name,String description){
		String lstname = getValue(name);
		String lstdescription = getValue(description);


		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:-   Link should be created with name and description");
		try{

			waitForElement(locator_split("lnkcreatlist"));
			click(locator_split("lnkcreatlist")); 

			sleep(1000);
			waitForElement(locator_split("txtlistname"));
			sendKeys(locator_split("txtlistname"), lstname);

			waitForElement(locator_split("txtlistdesc"));
			sendKeys(locator_split("txtlistdesc"), lstdescription);

			waitForElement(locator_split("rdemailremainder"));
			click(locator_split("rdemailremainder")); 

			waitForElement(locator_split("clksavelist"));
			click(locator_split("clksavelist")); 

			System.out.println("List is created");
			Reporter.log("PASS_MESSAGE:- Favorities Link is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Favorities is not clicked "+elementProperties.getProperty("lnkFavorities"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("lnkFavorities").toString().replace("By.", " ")
					+ " not found");
		}	


	}


	/* Method Name : --- ----- --- ClickonFilterSearch
	 * Purpose     : --- ----- --- To click on the search filter and select the chekbox
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 7/8/2015
	 * Created By  : --- ----- ---  Sowndarya
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void ClickonFilterSearch(String filter1, String filter2)
	{
		String fil1 = getValue(filter1);  
		String fil2 = getValue(filter2); 
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+fil1+" "+fil2);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Filtered value should be clicked "+fil1+" "+fil2);

		List<WebElement> elements = listofelements(locator_split("lstFilterSearch"));
		System.out.println(elements.size());
		try{
			for(int i=0; i<elements.size(); i++){

				System.out.println("Filter options   "+ elements.get(i).getText());
				if(elements.get(i).getText().contains(fil1)){
					sleep(1000);
					elements.get(i).click();
					WebElement ele = elements.get(i);
					List<WebElement> element2 = ele.findElements(locator_split("chkFilterSearch"));
					System.out.println("Size of filter option "+element2.size());
					for(int j=0; j<element2.size(); j++){
						System.out.println(element2.get(j).getText());
						if(element2.get(j).getText().contains(fil2)){
							element2.get(j).click();
							break; 
						}
					}

					break;
				}
			}
			Reporter.log("PASS_MESSAGE:- Filtered value is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Filtered value is not displayed and it dosent contain Text -"+fil2);
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("verifyFilter").toString().replace("By.", " ")
					+ " not found");
		}

		VerifyFilterTextSelected(fil1,fil2);
	}       


	/* Method Name : --- ----- --- ClickonFilterCheckbox
	 * Purpose     : --- ----- --- To click on the search filter checkbox dropdown
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 7/3/2015
	 * Created By  : --- ----- ---  Sowndarya
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void ClickonFilterCheckbox(String Text){
		String Data = getValue(Text);                   
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Filter checkbox should be clicked");
		try{   
			clickSpecificElementContains(locator_split("chkFilterCheckbox"), Data);
			Reporter.log("PASS_MESSAGE:- Filter checkbox is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Filter checkbox is not clicked or Not Available");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("chkFilterCheckbox")
					+ " not found");

		}

	}




	/* Method Name : --- ----- --- ClickShowMoreOptions
	 * Purpose     : --- ----- --- To click on Show More Options Button
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 7/3/2015
	 * Created By  : --- ----- ---  Sowndarya
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void ClickShowMoreOptions(){                    
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Show More Option Button should be clicked");
		try{   
			click(locator_split("btnShowMoreOptions"));            
			Reporter.log("PASS_MESSAGE:- Show More Option Button is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Show More Option Button is not clicked or Not Available");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("btnShowMoreOptions")
					+ " not found");

		}

	}



	/* Method Name : --- ----- --- ClickClearAllSelection
	 * Purpose     : --- ----- --- To Click on Clear All Selection
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 7/3/2015
	 * Created By  : --- ----- ---  Sowndarya
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void ClickClearAllSelection(){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Clear All Selection Button should be clicked");
		try{   
			click(locator_split("btnClearAllSelections"));         
			Reporter.log("PASS_MESSAGE:- Clear All Selection Button is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Clear All Selection Button is not clicked or Not Available");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("btnClearAllSelections")
					+ " not found");

		}

	}


	/* Method Name : --- ----- --- VerifyShowOnlyInkAndTonerLink
	 * Purpose     : --- ----- --- Validates ShowOnlyInkAndTonerLink is present or not
	 * Parameter   : --- ----- --- String- Test to be verified
	 * Date Created: --- ----- --- 03/07/2015
	 * Created By  : --- ----- --- Arun
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */
	public void VerifyShowOnlyInkAndTonerLink(String text){
		String Text = getValue(text);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Text);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Show Only Ink And Toner Link - '"+Text+"' should be displayed");
		try{
			if(getText(locator_split("lnkShowOnlyInkAndToner")).equalsIgnoreCase(Text)){
				Reporter.log("PASS_MESSAGE:- Show Only Ink And Toner Link - '"+Text+"' is displayed");
				System.out.println("Verified the Text -"+Text);
			}else {
				Reporter.log("FAIL_MESSAGE:- Show Only Ink And Toner Link - '"+Text+"' is not displayed");
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Show Only Ink And Toner Link - '"+Text+"' is not displayed");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("lnkShowOnlyInkAndToner").toString().replace("By.", " ")
					+ " not found");

		}
	}




	/* Method Name : --- ----- --- ClickonSortByFilter
	 * Purpose     : --- ----- --- Click on Sort By Filter
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void ClickonSortByFilter(String Text){
		String Data = getValue(Text);                   
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Sort By dropdown should be clicked");
		try{   
			clickSpecificElementContains(locator_split("lnkSortBy"), Data);       
			Reporter.log("PASS_MESSAGE:- Sort By dropdown is clicked");
			//System.out.println("Search catogory"+Data);

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Sort By dropdown is not clicked or Not Available");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("lnkSortBy")
					+ " not found");

		}

	}



	/* Method Name : --- ----- --- EnterQtyInSku
	 * Purpose     : --- ----- --- Enter Quantity in SKU Page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */   

	public void EnterQtyInSku(String itemnum){      
		String searchitem = getValue(itemnum);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+searchitem);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Quantity should be entered in the quantity box");
		try{
			//editSubmit(locator_split("txtSearch"));
			waitforElementVisible(locator_split("txtSkuPageQty"));
			clearWebEdit(locator_split("txtSkuPageQty"));
			sendKeys(locator_split("txtSkuPageQty"), searchitem);
			Thread.sleep(2000);
			Reporter.log("PASS_MESSAGE:- Quantity is entered in the quantity box");
			System.out.println("Quantity is entered in the quantity box - "+searchitem+" is entered");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Quantity is not entered in the quantity box "+elementProperties.getProperty("txtSkuPageQty"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtSkuPageQty").toString().replace("By.", " ")
					+ " not found");

		}
	}


	/* Method Name : --- ----- --- verifyShoppingCartPreview
	 * Purpose     : --- ----- --- To Verify if Shopping cart Preview is displayed on adding item
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */  
	public void VerifyShoppingCartPreview(String text){
		String Text = getValue(text);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Text);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Shopping Cart Preview should be displayed "+Text);

		if(getText(locator_split("txtShoppingCartPreview")).equalsIgnoreCase(Text)){
			Reporter.log("PASS_MESSAGE:- Shopping Cart Preview is displayed and it has Text -"+Text);
			System.out.println("Verified the text -"+Text);
		}else {
			Reporter.log("FAIL_MESSAGE:- Shopping Cart Preview is not displayed and it dosent contain Text -"+Text);
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtShoppingCartPreview").toString().replace("By.", " ")
					+ " not found");
		}
	}

	/* Method Name : --- ----- --- VerifyFilterTextSelected
	 * Purpose     : --- ----- --- To Verify whether filtered option is selected.
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  sowndrya
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void VerifyFilterTextSelected(String text, String text1){
		String Text = text + ": " + text1;
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Text);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Filtered value should be displayed "+Text);
		try{  

			if(verifySpecificElementContains((locator_split("verifyFilter")),Text)){
				Reporter.log("PASS_MESSAGE:- Filtered value is displayed and it has Text -"+Text);
				System.out.println("Verified the text -"+Text);
			}else {

				throw new NoSuchElementException("The element with "
						+ elementProperties.getProperty("verifyFilter").toString().replace("By.", " ")
						+ " not found");
			}
		}
		catch(NoSuchElementException e){
			Reporter.log("FAIL_MESSAGE:- "+elementProperties.getProperty("verifyFilter").toString() +" is not Present");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("verifyFilter").toString()
					+ " not found");
		}

		catch(Error Er){
			Reporter.log("FAIL_MESSAGE:- The Payment success Expected text - '"+Text+" and Actual Tesxt : "+getText(locator_split("verifyFilter"))+" is not equal");
			throw new Error("The Payment success Expected test - '"+Text+" and Actual Tesxt : "+getText(locator_split("verifyFilter"))+" is not equal");
		}
	}


	/* Method Name : --- ----- --- select street type in chkout registration
	 * Purpose     : --- ----- --- select street type in chkout registration
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 07/07/2015
	 * Created By  : --- ----- ---  Natarajan P.N.
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void selectstreettype(String Street){

		String Street1 = getValue(Street);
		String countrygroup_streettype ="Italy";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Street1);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:-"+Street1+" Street type  should be selected");
		try{
			if((countrygroup_streettype).contains(countries.get(countrycount))){
				waitforElementVisible(locator_split("checkoutstreettype"));
				selectList(locator_split("checkoutstreettype"),Street1);
				waitForPageToLoad(20);

				System.out.println(locator_split("checkoutstreettype")+" link selected");
				Reporter.log("PASS_MESSAGE:- Street Type is  selected");
				System.out.println("Street type down is applicable to " +country+ " only");
			}
			else{
				System.out.println("Street type is not applicable for this country");
				Reporter.log("PASS_MESSAGE:- Street Type is not applicable ");
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Street Type is not selected or type not available"+elementProperties.getProperty("txtSearch"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("checkoutstreettype").toString().replace("By.", " ")
					+ " not found");

		}
	}


	/* Method Name : --- ----- --- select province from the drop down
	 * Purpose     : --- ----- --- Select the province from the drop down at reg checkout
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  07/07/2015
	 * Created By  : --- ----- ---  natarajan
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void selectProvince(String Province){

		String Province1 = getValue(Province);
		String countrygroup_province ="Italy";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Province1);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:-"+Province1+" link should be selected");
		try{
			if((countrygroup_province).contains(countries.get(countrycount))){
				waitforElementVisible(locator_split("checkoutProvince"));
				selectList(locator_split("checkoutProvince"),Province1);
				waitForPageToLoad(20);
				sleep(1000);
				Thread.sleep(2000);
				System.out.println(locator_split("checkoutProvince")+" link selected");
				Reporter.log("PASS_MESSAGE:-"+Province1+" is entered  ");
			}
			else{
				Reporter.log("PASS_MESSAGE:- Province is not applicable to this country ");
			}
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- "+Province1+" unable to enter");

		}
	}

	/* Method Name : --- ----- --- VerifySkuPageItemNumber
	 * Purpose     : --- ----- --- verifying Welcome message UserName
	 * Parameter   : --- ----- ---  SKU Number
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */       

	public void VerifySkuPageItemNumber(String ItemNumber){
		String itemNumber = getValue(ItemNumber);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- SKU Page Item Number is verified");
		try{

			if(getAndVerifyPartialText(locator_split("txtSkuItemnumber"),itemNumber)) {                                
				waitForPageToLoad(20);
			}
			Reporter.log("PASS_MESSAGE:- SKU Page Displayed");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- SKU Page is not displayed "+elementProperties.getProperty("txtSkuItemnumber"));
			throw new NoSuchElementException("The element with " + elementProperties.getProperty("txtSkuItemnumber").toString().replace("By.", " ") + " not found");
		}
	}

	/* Method Name : --- ----- --- emailMousehoverAndVerifyPopup
	 * Purpose     : --- ----- --- To Hoverover on email icon and verify Popup displayed
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 	
	public void emailMousehoverAndVerifyPopup(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Email icon Popup should display");
		try{
			mousehover(locator_split("eleEmail"));
			if(isElementPresent(locator_split("eleEmailPopup"))==true){
				waitForPageToLoad(10);
			}
			Reporter.log("PASS_MESSAGE:- Email icon Popup displayed");
			System.out.println("Popup Displayed ");
		}		
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Email icon popup is not displayed "+elementProperties.getProperty("eleEmailPopup"));
			throw new NoSuchElementException("The element with " + elementProperties.getProperty("eleEmailPopup").toString().replace("By.", " ") + " not found");
		}
	}

	/* Method Name : --- ----- --- clickAnywhereInScreenToClosePopup
	 * Purpose     : --- ----- --- click Anywhere In Screen To Close Popup
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void clickAnywhereInScreenToClosePopup(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Page should be clicked");
		try{

			waitForElement(locator_split("pageSKU"));
			click(locator_split("pageSKU"));  
			sleep(1000);
			Reporter.log("PASS_MESSAGE:- Somewhere in SKU page clicked");
			System.out.println("SKU page Clicked to close popup");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- SKU page is not clicked "+elementProperties.getProperty("pageSKU"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("pageSKU").toString().replace("By.", " ")
					+ " not found");
		}
	}

	/* Method Name : --- ----- --- ClickFaceboofIconAndVerifyPopup
	 * Purpose     : --- ----- --- To Click on Facebook icon and verify Popup displayed
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 	
	public void ClickFacebookIconAndVerifyPopup(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Facebook icon Popup should display");
		try{
			click(locator_split("eleFacebook"));
			waitForPageToLoad(10);
			if(isElementPresent(locator_split("eleFacebookPopup"))==true){
				waitForPageToLoad(10);
			}
			Reporter.log("PASS_MESSAGE:- facebook icon Popup displayed");
			System.out.println("Facebook Popup Displayed ");
		}		
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Facebook icon popup is not displayed "+elementProperties.getProperty("eleFacebookPopup"));
			throw new NoSuchElementException("The element with " + elementProperties.getProperty("eleFacebookPopup").toString().replace("By.", " ") + " not found");
		}
	}

	/* Method Name : --- ----- ---ClickContinueShoppingBtnAddedToCartPage
	 * Purpose     : --- ----- --- Click Continue Shopping button in Added to Cart Page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 07/07/2015
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void ClickContinueShoppingBtnAddedToCartPage( ){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Continue Shopping button clicked in Added to Cart Page.");
		try{

			waitforElementVisible(locator_split("btnShoppingCartContinueShopping"));
			click(locator_split("btnShoppingCartContinueShopping"));

			Reporter.log("PASS_MESSAGE:- Continue Shopping button clicked in Added to Cart Page.");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Continue Shopping button is not clicked in Added to Cart Page."+elementProperties.getProperty("btnShoppingCartContinueShopping"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("btnShoppingCartContinueShopping").toString().replace("By.", " ")
					+ " not found");

		}


	}

	/* Method Name : --- ----- --- createlistlk
	 * Purpose     : --- ----- --- create list with name,description and select remainder notificaiton
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- --- siva
	 * Modified Date:--- ----- ---
	 */ 

	public void EditandSavelist(String Editlistname){
		String editlist = getValue(Editlistname);



		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:-   Link should be edited and saved");
		try{

			waitForElement(locator_split("lnkeditlist"));
			click(locator_split("lnkeditlist")); 

			sleep(1000);
			waitForElement(locator_split("txtlistname"));
			sendKeys(locator_split("txtlistname"), editlist);


			waitForElement(locator_split("clksavelist"));
			click(locator_split("clksavelist")); 


			Reporter.log("PASS_MESSAGE:- Link is edited and saved");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Favorities is not clicked "+elementProperties.getProperty("lnkFavorities"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("lnkFavorities").toString().replace("By.", " ")
					+ " not found");
		}	


	}

	/* Method Name : --- ----- --- selectlistname
	 * Purpose     : --- ----- --- select the list based on the text
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- --- siva
	 * Modified Date:--- ----- ---
	 */ 

	public void selectlistname(String selectlist){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:-   Link should be selected in the dropdown");
		try{

			waitForElement(locator_split("dropdowncurrentlist"));
			click(locator_split("dropdowncurrentlist"));

			waitforElementVisible(returnByValue(getValue("listselectname")));
			click(returnByValue(getValue("listselectname")));

			Reporter.log("PASS_MESSAGE:- list is slected");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Favorities is not clicked "+elementProperties.getProperty("dropdowncurrentlist"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("dropdowncurrentlist").toString().replace("By.", " ")
					+ " not found");
		}	


	}

	/* Method Name : --- ----- --- deletelist
	 * Purpose     : --- ----- --- delete the current selected list
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- --- siva
	 * Modified Date:--- ----- ---
	 */ 

	public void deletelist(){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- current list  should be deleted");
		try{

			waitForElement(locator_split("lnkdeletelist"));
			click(locator_split("lnkdeletelist"));

			Thread.sleep(3000);
			waitForElement(locator_split("btnconfirmdeletelist"));
			click(locator_split("btnconfirmdeletelist"));


			Reporter.log("PASS_MESSAGE:- list is deleted");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- list is not deleted "+elementProperties.getProperty("lnkdeletelist"));
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("lnkdeletelist").toString().replace("By.", " ")
					+ " not found");
		}	


	}


	/* Method Name : --- ----- --- clickPresselGermany
	 * Purpose     : --- ----- --- PresselGermany link should be clicked
	 * Parameter   : --- ----- --- None
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- --- 
	 * Modified By : --- ----- --- 
	 * Modified Date:--- ----- --- 
	 */  

	public void clickPresselGermany(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- PresselGermany link should be clicked");
		try{

			click(locator_split("lnkPresselGermany"));
			Reporter.log("PASS_MESSAGE:- PresselGermany link is clicked");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- PresselGermany link is not clicked");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("lnkPresselGermany")
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- verifylistcreated
	 * Purpose     : --- ----- --- To check List is created 
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */       
	public void verifylistcreated(String orderrecape1){

		String order = getValue(orderrecape1);

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Created List should be dispalyed");
		try{

			if(getAndVerifyPartialText(locator_split("verifycurrentlist"),order)) {                                
				waitForPageToLoad(20);
				Reporter.log("PASS_MESSAGE:- Created List is Displayed");
			}

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- orderrecape is not displayed "+elementProperties.getProperty("verifycurrentlist"));
			throw new NoSuchElementException("The element with " + elementProperties.getProperty("verifycurrentlist").toString().replace("By.", " ") + " not found");

		}


	}

	/* Method Name : --- ----- --- LoginHomePage
	 * Purpose     : --- ----- --- To Login from home page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void LoginHomePage(String username, String password){
		//String username = getValue(Email);
	//	String password = getValue(Password);

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account should be clicked and user should get logged in");
		try{
			System.out.println("inside login function");
			sleep(3000);
			//click(locator_split("btnLoginArrow"));
		//	clearWebEdit(locator_split("txtLoginNamegrasp"));
		driver.get("https://tuchwsmw0301.r1-core.r1.aig.net:20400/prweb/GRASPExt");
		sleep(5000);
			System.out.println(locator_split("txtLoginNamegrasp"));
						clearWebEdit(locator_split("txtLoginNamegrasp"));
			sendKeys(locator_split("txtLoginNamegrasp"), username);
			sendKeys(locator_split("txtpasswordgrasp"), password);
			//driver.findElement(By.name("USER")).clear();
			//driver.findElement(By.name("USER")).sendKeys(username);
		//	driver.findElement(locator_split("txtLoginNamegrasp")).sendKeys(username);
			//driver.findElement(locator_split("txtpasswordgrasp")).sendKeys(password);
			//driver.findElement(locator_split("btnlogingrasp")).click();
			click(locator_split("btnlogingrasp"));
			//sleep(5000);
			//driver.findElement(locator_split("btn_privacyok")).click();
		//click(locator_split("btn_privacyok"));
			
			/*Reporter.log("PASS_MESSAGE:- My Account  is clicked and user is logged in");*/
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- User is not logged in");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("LoginLogout")
					+ " not found");

		}

	}
	
	/* Method Name : --- ----- --- LoginHomePage
	 * Purpose     : --- ----- --- To Login from home page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void VerifyHomepage(){
		//String username = getValue(Email);
	//	String password = getValue(Password);

	//	class Local {};
		/*Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account should be clicked and user should get logged in");*/
		try{
			sleep(3000);
			//driver.findElement(locator_split("btn_privacyok")).click();
		click(locator_split("btn_privacyok"));
			
			/*Reporter.log("PASS_MESSAGE:- My Account  is clicked and user is logged in");*/
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- User is not logged in");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("LoginLogout")
					+ " not found");

		}

	}

	
	
	/* Method Name : --- ----- --- ExcelRetrieve
	 * Purpose     : --- ----- --- To Login from home page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public String  ExcelRetrieve(int row,int col){
		//String username = getValue(Email);
	//	String password = getValue(Password);

	//	class Local {};
		/*Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account should be clicked and user should get logged in");*/
		try{
			sleep(5000);
			//driver.findElement(locator_split("btn_privacyok")).click();
			ExcelUtils.setExcelFile(Constant.Path_TestData + Constant.File_TestData,"USA");
			username=ExcelUtils.getCellData(row, col);
			/*Reporter.log("PASS_MESSAGE:- My Account  is clicked and user is logged in");*/
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- User is not logged in");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("LoginLogout")
					+ " not found");

		}
		return username;

	}
	/* Method Name : --- ----- --- VerifyFrequentlyBoughtTogetherTitle
	 * Purpose     : --- ----- --- Verifies the text from Frequently Brought together items
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void VerifyFrequentlyBoughtTogetherTitle(String Exptext){
		String[] ExpectedTitle = getValue(Exptext).split(",",2);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Frequently Bought Item title should be present");
		try{
			List<WebElement> ele = listofelements(locator_split("txtTitle"));
			if(getAndVerifyPartialText(ele.get(Integer.valueOf(ExpectedTitle[0])-1), ExpectedTitle[1])){
				System.out.println(ExpectedTitle[1]+" is present");
				Reporter.log("PASS_MESSAGE:- "+ExpectedTitle[1]+" is present in Frequenlty bought box");
			}  else{                
				throw new Error("Actual Text: "+ExpectedTitle[1]+" is not present");
			}
		}
		catch(NoSuchElementException e){
			Reporter.log("FAIL_MESSAGE:- "+elementProperties.getProperty("txtTitle").toString() +" is not Present");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtTitle").toString()
					+ " not found");
		}
		catch(Error Er){
			Reporter.log("FAIL_MESSAGE:- Expected text - '"+ExpectedTitle[1]+" and Actual Text : "+getText(locator_split("txtTitle"))+" is not equal from Frequenlty bought box");
			throw new Error("Expected text - '"+ExpectedTitle[1]+" and Actual Text : "+getText(locator_split("txtTitle"))+" is not equal");
		}
	}


	/* Method Name : --- ----- --- ClickItemChkboxinFrequentlyBrought
	 * Purpose     : --- ----- --- To ClickItemChkboxinFrequentlyBrought
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void ClickItemChkboxinFrequentlyBrought(String itemnumber){
		int item = Integer.valueOf(getValue(itemnumber));

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Third item should be unchecked from Frequenlty bought items");
		try{
			List<WebElement> ele = listofelements(locator_split("chkFrequentlybrought"));
			click(ele.get(item-1));
			System.out.println(item+" item checbox is clicked");
			Reporter.log("PASS_MESSAGE:- Third item is unchecked from Frequenlty bought items");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Third item is not unchecked");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("chkFrequentlybrought")
					+ " not found");

		}

	}
	

	/* Method Name : --- ----- --- VerifyitemGreyedout
	 * Purpose     : --- ----- --- Verify the unchecked item is greyed out by attribute
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void VerifyitemGreyedout(String exp){
		String Expected = getValue(exp);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- The unchecked item should be greyed out");
		try{
			VerifySpecificElementByProperty(locator_split("vfyItemgreyout"), "style",Expected );
			System.out.println(exp+" is present so the item is greyed out");
			Reporter.log("PASS_MESSAGE:- Third item is unchecked from Frequenlty bought items");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Third item is not unchecked");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("chkFrequentlybrought")
					+ " not found");

		}

	}


	/* Method Name : --- ----- --- VerifyNoofItemsSelectedandPrice
	 * Purpose     : --- ----- --- Verify number of items selected and price
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void VerifyNoofItemsSelectedandPrice(String price, String noitems){
		String Price = getValue(price);
		String items[]= getValue(noitems).split(",",2);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- price should be updated");
		try{
			List<WebElement> ele = listofelements(locator_split("txtTotalItems"));
			String noofitems= getText(ele.get(Integer.valueOf(items[0])-1));
			String Pr= getText(locator_split("txtpriceinFrequentlybrought"));
			if(Pr.contains(Price)&items[1].equalsIgnoreCase(noofitems)){
				System.out.println("Number of items selected are "+noofitems+" and price is "+Price);
				Reporter.log("PASS_MESSAGE:- Number of items selected are "+noofitems+" and price is "+Price);
			}else{                
				throw new Error("Price is not updated to "+Price);
			}
		}
		catch(NoSuchElementException e){
			Reporter.log("FAIL_MESSAGE:- "+elementProperties.getProperty("txtpriceinFrequentlybrought").toString() +" is not Present");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtpriceinFrequentlybrought").toString()
					+ " not found");
		}
		catch(Error Er){
			Reporter.log("FAIL_MESSAGE:- Price is not updated to "+Price);
			throw new Error("Price is not updated to "+Price);
		}

	}

	/* Method Name : --- ----- --- ClickonMoreInformationlink
	 * Purpose     : --- ----- --- click on the more information link in Frequently bought items
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- Chakri
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void ClickonMoreInformationlink(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- More information link should be clicked");
		try{
			clickSpecificElement(locator_split("lnkFrequentlybroughtmoreinformation"), 2);
			System.out.println("More information link is clicked");
			Reporter.log("PASS_MESSAGE:- More information link is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- More information link is not clicked");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("lnkFrequentlybroughtmoreinformation")
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- ClickContinueShopping
	 * Purpose     : --- ----- --- click continue shopping button
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void ClickContinueShopping(){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Continue shopping button should be clicked");
		try{
			sleep(3000);
			List<WebElement> ele = listofelements(locator_split("btnClickcontinueButton"));
			if(ele.size()>1){
				click(ele.get(1));
			}else{
				click(ele.get(0));
			}
			System.out.println("Continue Shopping button is clicked");
			Reporter.log("PASS_MESSAGE:- Continue Shopping button is clicked");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Continue Shopping button is not clicked");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("btnClickcontinueButton")
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- UpdateQuantity
	 * Purpose     : --- ----- --- Updates the quantity in Frequently Bought table for second item
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  Chakri
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void UpdateQuantity(String qty, int item){
		String Qty = getValue(qty);

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Quantity should be udapted to "+qty);
		try{
			List<WebElement> ele =listofelements(locator_split("txtUpdatequantity"));
			if(ele.size()>4){
				item=item+4;
			}
			ele.get(item).clear();
			ele.get(item).sendKeys(Qty);
			System.out.println("Quantity for second item is updated to "+Qty);
			Reporter.log("PASS_MESSAGE:- Quantity for second item is updated to "+Qty);
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Quantity is not udated to "+Qty);
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("txtUpdatequantity")
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- ClickAddtoCartinFrequentlyBought
	 * Purpose     : --- ----- --- Clicks the Add to cart button in Frequently Bought table
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  
	 * Created By  : --- ----- ---  Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- --- 
	 */

	public void ClickAddtoCartinFrequentlyBought(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Add to cart should be clicked in Frequently Bought");
		try{
			List<WebElement> ele = listofelements(locator_split("btnAddtoCartFrequentlybought"));
			if(ele.size()>1){
				click(ele.get(1));
			}else{
				click(ele.get(0));
			}
			System.out.println("Add to cart is clicked in Frequently Bought");
			Reporter.log("PASS_MESSAGE:- Add to cart is clicked in Frequently Bought");
			sleep(5000);
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Add to cart is not clicked in Frequently Bought");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("btnAddtoCartFrequentlybought")
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- VerifyShopinsightTitle
	 * Purpose     : --- ----- --- Verifies the shop insight title
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---   Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void VerifyShopinsightTitle(String Exptext){
		String countryy="Germany";
		String ExpectedTitle[] = getValue(Exptext).split(",",2);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Shopping insight title should be present");
		try{
			if(countryy.contains(countries.get(countrycount))){
				if(getAndVerifyTextfromList(locator_split("txtShoppinginSightDesc"), ExpectedTitle[1],1)){
					System.out.println(ExpectedTitle[1]+" is present");
					Reporter.log("PASS_MESSAGE:- "+ExpectedTitle[1]+" is present in Shopping insight box");
				}  else{                
					throw new Error(ExpectedTitle[1]+" is not present");
				}
			}else{
				if(getAndVerifyTextfromList(locator_split("txtTitle"), ExpectedTitle[1],Integer.valueOf(ExpectedTitle[0]))){
					System.out.println(ExpectedTitle[1]+" is present");
					Reporter.log("PASS_MESSAGE:- "+ExpectedTitle[1]+" is present in Shopping insight box");
				}  else{                
					throw new Error(ExpectedTitle[1]+" is not present");
				}
			}
		}
		catch(NoSuchElementException e){
			Reporter.log("FAIL_MESSAGE:- "+elementProperties.getProperty("txtTitle")+" / "+elementProperties.getProperty("txtShoppinginSightDesc")+" is not Present");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtTitle")
					+ elementProperties.getProperty("txtShoppinginSightDesc")
					+ " not found");
		}
		catch(Error Er){
			Reporter.log("FAIL_MESSAGE:- Expected text - '"+ExpectedTitle[1]+" is not present in Shopping insight box");
			throw new Error("Expected text - '"+ExpectedTitle[1]+" is not present");
		}
	}

	/* Method Name : --- ----- --- ClickAddtoCartinShoppinginsight
	 * Purpose     : --- ----- --- Clicks the Add to cart button in shopping insight table
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  
	 * Created By  : --- ----- ---  Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- --- 
	 */

	public void ClickAddtoCartinShoppinginsight(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Add to cart should be clicked in shopping insight");
		try{
			List<WebElement> ele = listofelements(locator_split("btnShoppinginsightaddtocart"));
			click(ele.get(1));
			sleep(3000);
			System.out.println("Add to cart is clicked in shopping insight");
			Reporter.log("PASS_MESSAGE:- Add to cart is clicked in shopping insight");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Add to cart is not clicked in shopping insight");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("btnShoppinginsightaddtocart")
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- ClickClosepopupinShoppinginsight
	 * Purpose     : --- ----- --- Clicks the 'x' image in the pop up after clickign add to cart
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  
	 * Created By  : --- ----- ---  Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- --- 
	 */

	public void ClickClosepopupinShoppinginsight(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- close 'x' image should be clicked for Add to Cart pop up");
		try{
			click(locator_split("imgShoppinginsightPopupclose"));
			System.out.println("close 'x' image is clicked for Add to Cart pop up");
			Reporter.log("PASS_MESSAGE:- close 'x' image is clicked for Add to Cart pop up");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- close 'x' image is not clicked for Add to Cart pop up");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("imgShoppinginsightPopupclose")
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- ClickDescinShoppinginsight
	 * Purpose     : --- ----- --- Clicks the Description link for second item
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  
	 * Created By  : --- ----- ---  Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- --- 
	 */

	public void ClickDescinShoppinginsight(String Linktext){
		String LinkText = getValue(Linktext);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Description link should be clicked in shopping insight");
		try{
			clickbylinktext(LinkText);
			System.out.println(LinkText+" is clicked in shopping insight");
			Reporter.log("PASS_MESSAGE:- "+LinkText+" is clicked in shopping insight");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- "+LinkText+" link is not clicked in shopping insight");
			throw new NoSuchElementException("The link text with"
					+ LinkText
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- VerifySkupageItemDesc
	 * Purpose     : --- ----- --- Verifies the SKU page item Desc
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---   Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void VerifySkupageItemDesc(String Linktext){
		String countryy="Germany,Denmark";
		String LinkText = getValue(Linktext);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Item description should match with "+LinkText+" From SKU Page");
		try{
			if(countryy.contains(countries.get(countrycount))){
				if(getAndVerifyPartialText(locator_split("txtSKUNametitle"), LinkText)){
					System.out.println(LinkText+" is present");
					Reporter.log("PASS_MESSAGE:- "+LinkText+" is present in SKU Page");
				}  else{                
					throw new Error("Actual Text: "+LinkText+" is not present in SKU Page");
				}
			}else{
				if(getAndVerifyPartialText(locator_split("txtSKUHeadertitle"), LinkText)){
					System.out.println(LinkText+" is present");
					Reporter.log("PASS_MESSAGE:- "+LinkText+" is present in SKU Page");
				}  else{                
					throw new Error("Actual Text: "+LinkText+" is not present in SKU Page");
				}
			}

		}
		catch(NoSuchElementException e){
			Reporter.log("FAIL_MESSAGE:- "+elementProperties.getProperty("txtSKUHeadertitle")+" / "
					+ elementProperties.getProperty("txtSKUNametitle")
					+" is not Present");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtSKUHeadertitle")
					+ elementProperties.getProperty("txtSKUNametitle")
					+ " not found");
		}
		catch(Error Er){
			Reporter.log("FAIL_MESSAGE:- Actual Text: "+LinkText+" is not present in SKU Page");
			throw new Error("Expected text - '"+LinkText+" is not present");
		}
	}

	/* Method Name : --- ----- --- VerifyCustomeralsoOrderedtitle
	 * Purpose     : --- ----- --- Verifies the Customer also Ordered title
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---   Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void VerifyCustomeralsoOrderedtitle(String Exptext){
		String[] ExpText = getValue(Exptext).split(",", 2);

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Title with "+ExpText[1]+" should be present in SKU Page");
		try{
			if(getAndVerifyTextfromList(locator_split("txtCustomeralsoOrdered"), ExpText[1], Integer.valueOf(ExpText[0]))){
				System.out.println(ExpText[1]+" is present");
				Reporter.log("PASS_MESSAGE:- "+ExpText[1]+" is present in SKU Page");
			}  else{                
				throw new Error("Actual Text: "+ExpText[1]+" is not present in SKU Page");
			}
		}
		catch(NoSuchElementException e){
			Reporter.log("FAIL_MESSAGE:- "+elementProperties.getProperty("txtCustomeralsoOrdered").toString() +" is not Present");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtCustomeralsoOrdered").toString()
					+ " not found");
		}
		catch(Error Er){
			Reporter.log("FAIL_MESSAGE:- Actual Text: "+ExpText[1]+" is not present in SKU Page");
			throw new Error("Expected text - '"+ExpText[1]+" and Actual Text : "+getText(locator_split("txtCustomeralsoOrdered"))+" is not equal");
		}
	}

	/* Method Name : --- ----- --- Clickarrow
	 * Purpose     : --- ----- --- clicks arrow
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---   Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void Clickarrow(String Exptext){
		String countryy="France";
		String[] ExpText = getValue(Exptext).split(",", 2);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Arrow should be clicked in Customer also Ordered table");
		try{
			if(countryy.contains(countries.get(countrycount))){
				if(Exptext.contains("Ordered")){
					click(locator_split("arrowCustomeralsoOrderedcss"));
					System.out.println("Arrow is clicked");
					Reporter.log("PASS_MESSAGE:- Arrow is clicked in Customer also Ordered table");
				}else{
					click(locator_split("arrowCustomeralsoViewedcss"));
					System.out.println("Arrow is clicked");
					Reporter.log("PASS_MESSAGE:- Arrow is clicked in Customer also Ordered table");
				}
			}else{

				clickSpecificElement(locator_split("arrowCustomeralsoOrdered"), Integer.valueOf(ExpText[0]));
				System.out.println("Arrow is clicked");
				Reporter.log("PASS_MESSAGE:- Arrow is clicked in Customer also Ordered table");

			}

		}
		catch(NoSuchElementException e){
			Reporter.log("FAIL_MESSAGE:- Arrow is not clicked");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("arrowCustomeralsoOrdered").toString()
					+ " not found");
		}
	}


	/* Method Name : --- ----- --- ClickAddtoCartiteminCustomersalsoOrdered
	 * Purpose     : --- ----- --- clicks addtocart for one item in customer also ordered 
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---   Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void ClickAddtoCartiteminCustomersalsoOrdered(){
		String[] ExpText = getValue("CustomeralsoOrderedtitle").split(",", 2);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Add to Cart button should be clicked in CustomeralsoOrdered table");
		try{
			List<WebElement> ele = listofelements(locator_split("imgitemCustomeralsoOrderedViewed"));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele.get(Integer.valueOf(ExpText[0])-1));
			System.out.println("Add to Cart button is clicked in CustomeralsoOrdered table");
			Reporter.log("PASS_MESSAGE:- Add to Cart button is clicked in CustomeralsoOrdered table");

		}
		catch(NoSuchElementException e){
			Reporter.log("FAIL_MESSAGE:- Add to Cart button is not clicked in CustomeralsoOrdered table");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("imgitemCustomeralsoOrderedViewed").toString()
					+ " not found");
		}
	}


	/* Method Name : --- ----- --- VerifyCustomeralsoViewedTitle
	 * Purpose     : --- ----- --- Verifies the Customer also Viewed title
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---   Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void VerifyCustomeralsoViewedTitle(String Exptext){
		String[] ExpText = getValue(Exptext).split(",", 2);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Title with "+ExpText+" should be present in SKU Page");
		try{
			if(getAndVerifyTextfromList(locator_split("txtCustomeralsoOrdered"), ExpText[1], Integer.valueOf(ExpText[0]))){
				System.out.println(ExpText[1]+" is present");
				Reporter.log("PASS_MESSAGE:- "+ExpText[1]+" is present in SKU Page");
			}  else{                
				throw new Error("Actual Text: "+ExpText[1]+" is not present in SKU Page");
			}
		}
		catch(NoSuchElementException e){
			Reporter.log("FAIL_MESSAGE:- "+elementProperties.getProperty("txtCustomeralsoOrdered").toString() +" is not Present");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtCustomeralsoOrdered").toString()
					+ " not found");
		}
		catch(Error Er){
			Reporter.log("FAIL_MESSAGE:- Actual Text: "+ExpText[1]+" is not present in SKU Page");
			throw new Error("Expected text - '"+ExpText[1]+" and Actual Text : "+getText(locator_split("txtCustomeralsoOrdered"))+" is not equal");
		}
	}

	/* Method Name : --- ----- --- ClickAddtoCartiteminCustomersalsoViewed
	 * Purpose     : --- ----- --- clicks addtocart for one item in customer also Viewed 
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---   Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void ClickAddtoCartiteminCustomersalsoViewed(){
		String[] ExpText = getValue("CustomeralsoViewedtitle").split(",", 2);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Add to Cart button should be clicked in CustomeralsoViewed table");
		try{
			List<WebElement> ele = listofelements(locator_split("imgitemCustomeralsoOrderedViewed"));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele.get(Integer.valueOf(ExpText[0])-1));
			System.out.println("Add to Cart button is clicked in CustomeralsoOrdered table");
			Reporter.log("PASS_MESSAGE:- Add to Cart button is clicked in CustomeralsoOrdered table");

		}
		catch(NoSuchElementException e){
			Reporter.log("FAIL_MESSAGE:- Add to Cart button is not clicked in CustomeralsoViewed table");
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("imgitemCustomeralsoOrderedViewed").toString()
					+ " not found");
		}
	}

	/* Method Name : --- ----- --- EnterQuantityandAdditem
	 * Purpose     : --- ----- --- Enters the required quantity and add the respective item
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- --- Chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void EnterQuantityandAdditem(String itemnumber, String quantity){
		String ItemNumber = getValue(itemnumber);
		String Quantity = getValue(quantity);
		int ItemNumberint = Integer.parseInt(ItemNumber);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+ "Click " + ItemNumberint + " Compare check box");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Quantity should be entered and item should be added");
		try{
			List<WebElement> ele = listofelements(locator_split("txtSearchQuantity"));
			List<WebElement> ele1 = listofelements(locator_split("txtSearchAddtoCart"));
			System.out.println("Total no of elements for Quantity "+ ele.size());
			System.out.println("Total no of elements for Add to Cart buttons "+ ele1.size());
			for(int i=0; i<ItemNumberint+1; i++){
				if(ItemNumberint==i+1 && i==0){
					ele.get(i).click();
					ele.get(i).clear();
					ele.get(i).sendKeys(Quantity);
					ele1.get(i).click();
					Reporter.log("PASS_MESSAGE:- Enterd Quantity "+Quantity+" for the item No "+ItemNumber);
					System.out.println("Enterd Quantity "+Quantity+" for the item No "+ItemNumber);
					break;
				}
				if(ItemNumberint==i+1 && i==1){
					ele.get(i+2).click();
					ele.get(i-1).click();
					ele.get(i).click();
					ele.get(i).clear();
					ele.get(i).sendKeys(Quantity);
					ele1.get(i).click();
					Reporter.log("PASS_MESSAGE:- Enterd Quantity "+Quantity+" for the item No "+ItemNumber);
					System.out.println("Enterd Quantity "+Quantity+" for the item No "+ItemNumber);
					break;
				}
				if(ItemNumberint==i+1 && i>1){
					ele.get(i+1).click();
					ele.get(i-1).click();
					ele.get(i).click();
					ele.get(i).clear();
					ele.get(i).sendKeys(Quantity);
					ele1.get(i).click();
					Reporter.log("PASS_MESSAGE:- Enterd Quantity "+Quantity+" for the item No "+ItemNumber);
					System.out.println("Enterd Quantity "+Quantity+" for the item No "+ItemNumber);
					break;
				}

			}
		}
		catch (Exception e)
		{
			Reporter.log("FAIL_MESSAGE:- Itemsnumber/Element is not present");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("txtSearchQuantity")
					+ elementProperties.getProperty("txtSearchAddtoCart")
					+ " not found");
		}
		catch (Error  e){
			Reporter.log("FAIL_MESSAGE:- Both the Quantity elements and Add to Cart items are not equal");

		}

	}

	/* Method Name : --- ----- --- clickCartPopupViewCart
	 * Purpose     : --- ----- --- To click the Viewcart button inside the shopping cart popup (Top Right side)
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 7/10/2015
	 * Created By  : --- ----- ---  Kishore
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void clickCartPopupViewCart(){

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- To Click the View cart button in the Shopping Cart popup ");
		try{
			click(locator_split("btnCartViewCart"));
			Reporter.log("PASS_MESSAGE:- Clicked on the View Cart Button in the shopping cart popup");
			System.out.println("Clicked on the View Cart Button in the shopping cart popup");
		}
		catch (Exception e)
		{
			Reporter.log("FAIL_MESSAGE:- View Cart Button is not clicked in the shopping cart popup");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("btnCartViewCart")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- VerifyShoppingCartPreviewVatText
	 * Purpose     : --- ----- --- To Verify if Shopping cart Preview is displayed on adding item and vat is verified
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  
	 * Modified By : --- ----- --- chakri
	 * Modified Date:--- ----- ---
	 */  
	public void VerifyShoppingCartPreviewVatText(String text){
		String Text = getValue(text);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+Text);
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Shopping Cart Preview should be displayed "+Text);
		mousehover(locator_split("btnCheckout"));
		if(getText(locator_split("txtShoppingCartPreviewVat")).equalsIgnoreCase(Text)){
			Reporter.log("PASS_MESSAGE:- Shopping Cart Preview is displayed and it has Text -"+Text);
			System.out.println("Verified the text -"+Text);
		}else {
			Reporter.log("FAIL_MESSAGE:- Shopping Cart Preview is not displayed and it dosent contain Text -"+Text);
			throw new NoSuchElementException("The element with "
					+ elementProperties.getProperty("txtShoppingCartPreviewVat")
					+ " not found");
		}
	}

	/* Method Name : --- ----- --- ClickExcVAT
	 * Purpose     : --- ----- --- Clicks the Exclude Vat.
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 7/20/2015
	 * Created By  : --- ----- --- chakri 
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void ClickExcVAT()
	{
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- The Vat Exclude button with WebElement "+elementProperties.getProperty("Vatinclusion")+" should be clicked");

		try{
			String countrygroup_pressel_germany="PresselGermany";
			String countrygroup_Bernard_belgium="BernardBelgium";

			if((countrygroup).contains(countries.get(countrycount))){
				List<WebElement> ele = listofelements(locator_split("Vatinclusion"));
				click(ele.get(1)); 
				Reporter.log("PASS_MESSAGE:-The Vat Exclude button with WebElement "+elementProperties.getProperty("Vatinclusion")+" is clicked");
			}
			else if((countrygroup_pressel_germany).contains(countries.get(countrycount)))
			{
				clickPresselGermany();
			}

			else if((countrygroup_Bernard_belgium).contains(countries.get(countrycount)))
			{
				clickNederlands();
			}

			else
				Reporter.log("PASS_MESSAGE:-The Vat Exclude button with WebElement "+elementProperties.getProperty("Vatinclusion")+" is not present");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- The Vat Exclude button with WebElement "+elementProperties.getProperty("Vatinclusion")+" is not clicked");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("Vatinclusion").toString().replace("By.", " ")
					+ " not found");

		}
	}


	/* Method Name : --- ----- --- VerifylogosonCheckoutPage
	 * Purpose     : --- ----- --- Verifies logos on the Checkout page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 7/20/2015
	 * Created By  : --- ----- ---  chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 
	public void VerifylogosonCheckoutPage(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- The Vat Exclude button with WebElement "+elementProperties.getProperty("Vatinclusion")+" should be clicked");
		try{
			if(isElementPresent(locator_split("imgStapleslogoonleft"))& isElementPresent(locator_split("imgCheckoutLockicon"))
					& isElementPresent(locator_split("imgCheckoutNortan")) ){
				System.out.println("All the three logos are present");
				Point stapleslogo = getcoordinates(locator_split("imgStapleslogoonleft"));
				System.out.println(stapleslogo);
				Point lockicon = getcoordinates(locator_split("imgCheckoutLockicon"));
				System.out.println(lockicon);
				Point Nortonicon = getcoordinates(locator_split("imgCheckoutNortan"));
				System.out.println(Nortonicon);
				if(stapleslogo.x<lockicon.x & lockicon.x<Nortonicon.x){
					System.out.println("Staples logo is on left side, Lock icon in middle and Norton img on  right side");
				}else{
					System.out.println("Logos are mis aligned");
					throw new Error("Logos are mis aligned");
				}


			}
			else{
				System.out.println("One or all the three logos are not present");
				throw new Error("One or all the three logos are not present");
			}

		}
		catch (Exception e)
		{
			Reporter.log("FAIL_MESSAGE:- Logo Elements are not present");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("imgStapleslogoonleft")
					+ elementProperties.getProperty("imgCheckoutLockicon")
					+ elementProperties.getProperty("imgCheckoutNortan")
					+ " not found");
		}
		catch (Error  e){
			Reporter.log("FAIL_MESSAGE:- One or all the three logos are not present/Logos are mis aligned");

		}
	}

	/* Method Name : --- ----- --- VerifyCheckoutcollapsedheaders
	 * Purpose     : --- ----- --- Verifies what are all the tabs collapsed
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  7/20/2015
	 * Created By  : --- ----- ---  chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void VerifyCheckoutcollapsedheaders(String edittext){
		String EditText = getValue(edittext);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Contact info and Delivery address should be in Collpased mode");

		try{
			List<WebElement> ele = listofelements(locator_split("txtcheckoutHeader"));
			List<WebElement> ele1 = listofelements(locator_split("txtCheckoutEdit"));

			for(int i=0; i<ele1.size(); i++){
				if(getText(ele1.get(i)).contains(EditText)){
					System.out.println(getText(ele.get(i))+" is in collapsed mode");

				}else{
					System.out.println("Contact info and Delivery address are not collapsed or given text in data sheet is incorrect");
					throw new Error("Contact info and Delivery address are not collapsed or given text in data sheet is incorrect");
				}

			}
			Reporter.log("PASS_MESSAGE:- Contact info and Delivery address are in Collpased mode");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Element is not present");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("txtcheckoutHeader")
					+ elementProperties.getProperty("txtCheckoutEdit")
					+ " not found");

		}
		catch (Error  e){
			Reporter.log("FAIL_MESSAGE:- Contact info and Delivery address are not collapsed or given text in data sheet is incorrect");

		}
	}

	/* Method Name : --- ----- --- VerifyBillingDetails
	 * Purpose     : --- ----- --- Verifies detaisl in Billing
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  7/20/2015
	 * Created By  : --- ----- ---  chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void VerifyBillingTabPaymentDetails(String payment0, String payment1, String payment2, String payment3){
		String[] Payment = {getValue(payment0),getValue(payment1),getValue(payment2),getValue(payment3)};
		String Payments="";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Payment Methods should be present in Checkout Page");

		try{
			sleep(8000);
			List<WebElement> ele = listofelements(locator_split("txtCheckoutPaymentMethods"));

			for(int i=0; i<ele.size(); i++){
				if(getText(ele.get(i)).contains(Payment[i])){
					System.out.println(getText(ele.get(i))+" payment option is present");
					Payments=Payments.concat(getText(ele.get(i))).concat(", ");
				}else{
					System.out.println("one or more Payment options are not present");
					throw new Error("one or more Payment options are not present");
				}

			}


			Reporter.log("PASS_MESSAGE:- All the payment options"+Payments+" are present");

		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Element is not present");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("txtCheckoutBillingAddress")
					+ elementProperties.getProperty("txtCheckoutPaymentMethods")
					+ " not found");

		}
		catch (Error  e){
			Reporter.log("FAIL_MESSAGE:- one or more Payment options are not present");

		}
	}

	/* Method Name : --- ----- --- VerifyBillingAddress
	 * Purpose     : --- ----- --- Verifies address and title in billing address
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  7/20/2015
	 * Created By  : --- ----- ---  chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void VerifyBillingAddress(String billingaddresstitle, String billingaddress){
		String BilingTitle=getValue(billingaddresstitle);
		String Billingaddress=getValue(billingaddress);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Billing address and title should be present");

		try{
			if(getAndVerifyPartialText(locator_split("txtCheckoutBillingAddressTitle"), BilingTitle)){
				System.out.println("Billing address title is present "+getText(locator_split("txtCheckoutBillingAddressTitle")));
			}else{
				throw new Error("Billing address title is not present");
			}
			if(getAndVerifyPartialText(locator_split("txtCheckoutBillingAddress"), Billingaddress)){
				System.out.println("Billing address is present "+getText(locator_split("txtCheckoutBillingAddress")));
			}else{
				throw new Error("Billing address is not present");
			}
			Reporter.log("PASS_MESSAGE:- Billing address and title are present");

		}catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Element is not present");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("txtCheckoutBillingAddressTitle")
					+ elementProperties.getProperty("txtCheckoutBillingAddress")
					+ " not found");

		}
		catch (Error  e){
			Reporter.log("FAIL_MESSAGE:- Billing title or address is not present");
			throw new Error("Billing address is not present");

		}
	}

	/* Method Name : --- ----- --- VerifyCheckoutTermsandCondition
	 * Purpose     : --- ----- --- Verifies terms and condition in Billing address
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  7/20/2015
	 * Created By  : --- ----- ---  chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void VerifyCheckoutTermsandCondition(String Exptitle){
		String ExpTitle=getValue(Exptitle);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Terms and Condition should be present");

		try{
			if(getAndVerifyPartialText(locator_split("txtCheckoutTermstitle"), ExpTitle)){
				System.out.println("Terms and Condition in Billing is present "+getText(locator_split("txtCheckoutTermstitle")));
			}else{
				throw new Exception("Terms and Condition in Billing is not present");
			}
			Reporter.log("PASS_MESSAGE:- Terms and Condition is present");

		}catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Terms and Condition in Billing is not present");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("txtCheckoutTermstitle")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- VerifyCheckoutTotalPrice
	 * Purpose     : --- ----- --- Verifies Total Price present in Checkout
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  7/20/2015
	 * Created By  : --- ----- ---  chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void VerifyCheckoutTotalPrice(String Exptitle){
		String ExpTitle=getValue(Exptitle);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Total Price should be present");

		try{
			if(getAndVerifyPartialText(locator_split("txtCheckoutOrdertotal"), ExpTitle)){
				System.out.println("Total Price  in Billing is present "+getText(locator_split("txtCheckoutOrdertotal")));
			}else{
				throw new Exception("Total Price  in Billing is not present");
			}
			Reporter.log("PASS_MESSAGE:- Total Price title is present");
		}catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Total Price title is not present");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("txtCheckoutOrdertotal")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- VerifyCommentsorReferencePOField
	 * Purpose     : --- ----- --- Verifies whether comments filed is present
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  7/21/2015
	 * Created By  : --- ----- ---  chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void VerifyCommentsField(){
		String countriesComments="Spain,France,Italy,Sweden,Germany,Netherland,Denmark,BernardFrance,BernardBelgium";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Comments should be present based on country");

		try{
			if(countriesComments.contains(countries.get(countrycount))){
				System.out.println("Comments field is present as  "+getPropertyValue(locator_split("txtCheckoutCommentssection"), "name"));
				Reporter.log("PASS_MESSAGE:- Comments field is present");
			}else{
				Reporter.log("PASS_MESSAGE:- Comments field will not be present for this counry");
			}

		}catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Comments field is not present");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("txtCheckoutCommentssection")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- VerifyCommentsorReferencePOField
	 * Purpose     : --- ----- --- Verifies whether Reference PO field is present
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  7/21/2015
	 * Created By  : --- ----- ---  chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void VerifyReferencePOField(){
		String countriesReferencePO="Germany,UK,Denmark";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Comments or ReferencePOField or both should be present based on country");

		try{
			if(countriesReferencePO.contains(countries.get(countrycount))){
				System.out.println("Reference Po field is present as  "+getPropertyValue(locator_split("txtCheckoutReferencePO"), "name"));
				Reporter.log("PASS_MESSAGE:- Reference Po field is present as  "+getPropertyValue(locator_split("txtCheckoutReferencePO"), "name"));
			}else{
				Reporter.log("PASS_MESSAGE:- Reference PO field will not be present for this counry");
			}

		}catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Reference PO field is not present");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("txtCheckoutCommentssection")
					+ elementProperties.getProperty("txtCheckoutReferencePO")
					+ " not found");

		}
	}

	/* Method Name : --- ----- --- VerifyViewCartandPrintinCheckout
	 * Purpose     : --- ----- --- Verifies ViewCart and Print present in checkout
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  7/21/2015
	 * Created By  : --- ----- ---  chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void VerifyViewCartandPrintinCheckout(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- View Cart and Print Cart should be present");

		try{
			List<WebElement> ele = listofelements(locator_split("lnkViewPrintCart"));
			if(ele.size()==2){
				System.out.println("View Cart  -"+ele.get(1).getText()+" and print Cart -"+ele.get(0).getText()+" is present");
				Reporter.log("PASS_MESSAGE:- View Cart  "+ele.get(1).getText()+" and print Cart "+ele.get(0).getText()+" is present");
			}else{
				throw new Exception("View cart or Print or both is not present");
			}

		}catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- View cart or Print or both is not present");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("lnkViewPrintCart")
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- VerifyCartCheckout_Coupon_OrderSumamrysections
	 * Purpose     : --- ----- --- Verifies whether cartcheckout, coupon and Order summary is present in checkout page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  7/21/2015
	 * Created By  : --- ----- ---  chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void VerifyCartCheckout_Coupon_OrderSumamrysections(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- cartcheckout, coupon and Order summary should be present in checkout page");

		try{
			if(isElementPresent(locator_split("boxCheckoutCartCheckout")) & isElementPresent(locator_split("boxCheckoutCoupon"))
					& isElementPresent(locator_split("boxCheckoutOrderSummary"))){
				System.out.println("cartcheckout, coupon and Order summary is present in checkout page");
				Reporter.log("PASS_MESSAGE:- cartcheckout, coupon and Order summary is present in checkout page");
			}else{
				throw new Exception("cartcheckout/coupon/Order summary are not present in checkout page");
			}
		}catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- cartcheckout/coupon/Order summary are not present in checkout page");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("boxCheckoutCartCheckout")
					+ elementProperties.getProperty("boxCheckoutCoupon")
					+ elementProperties.getProperty("boxCheckoutOrderSummary")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- VerifyDetailsin_Cart_inCheckout
	 * Purpose     : --- ----- --- Verifies all the details present in cart in checkoutpage
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  7/21/2015
	 * Created By  : --- ----- ---  chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void VerifyDetailsin_Cart_inCheckout(){
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Image, ItemDesc, ItemNum, Price, Quantity, Inc/Exec tax, Remove and change in Cart in Checkout");

		try{
			String image= getPropertyValue(locator_split("imgCartinCheckout"), "src");
			String ItemDesc = getText(locator_split("txtItemDescCartinCheckout"));
			String ItemNum = getText(locator_split("txtitemNumCartinCheckout"));
			List<WebElement> ele = listofelements(locator_split("lnkRemChanCartinCheckout"));
			String Remove = getText(ele.get(0));
			String change = getText(ele.get(1));
			String Price = getText(locator_split("txtpriceCartinCheckout"));
			List<WebElement> ele1 = listofelements(locator_split("txtIncExctaxinCartinCheckout"));
			String Tax= getText(ele1.get(0));
			String Quantity = getText(locator_split("txtQuantityCartinCheckout"));
			String UnitofMeasure= getText(locator_split("txtUnitofmeasureCartinCheckout"));
			System.out.println("Following details are present in CartinCheckout \n"+image+"\n"+
					ItemDesc+"\n"+ItemNum+"\n"+Remove+"\n"+change+"\n"+Price+"\n"+Tax+"\n"+Quantity+"\n"+UnitofMeasure+"\n");
			Reporter.log("PASS_MESSAGE:- Following details are present in CartinCheckout \n"+image+"\n"+
					ItemDesc+"\n"+ItemNum+"\n"+Remove+"\n"+change+"\n"+Price+"\n"+Tax+"\n"+Quantity+"\n"+UnitofMeasure+"\n");


		}catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- cartcheckout/coupon/Order summary are not present in checkout page");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("lnkRemChanCartinCheckout")
					+ elementProperties.getProperty("txtIncExctaxinCartinCheckout")
					+ elementProperties.getProperty("imgCartinCheckout")
					+ " not found");
		}

	}


	/* Method Name : --- ----- --- VerifyShiptoStoreImagein_Cart_incheckout
	 * Purpose     : --- ----- --- Verifies the Ship to Store image in Cart in Checkout
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  7/22/2015
	 * Created By  : --- ----- ---  chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void VerifyShiptoStoreImagein_Cart_incheckout(){
		String countriess = "Germany,Norway,Netherland,UK";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Ship to Cart image should be present");

		try{
			if(countriess.contains(countries.get(countrycount))){
				if(isElementPresent(locator_split("imgShiptoStoreinCartinCheckout"))){
					System.out.println("Ship to Store image is present");
					Reporter.log("PASS_MESSAGE:- Ship to Store image is present");
				}else {
					System.out.println("Ship to Store image is not present for the country -"+countries.get(countrycount));
					throw new Exception("Ship to Store image is not present for the country -"+countries.get(countrycount));
				}
			}else{
				Reporter.log("PASS_MESSAGE:- Ship to Store image will not be present for country -"+countries.get(countrycount));
			}
		}catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Ship to Store image is not present for country -"+countries.get(countrycount));
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("imgShiptoStoreinCartinCheckout")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- VerifyCurrencySymbol
	 * Purpose     : --- ----- --- Verifies the currency symbol in cart in checkout and Order summary in checkout page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  7/22/2015
	 * Created By  : --- ----- ---  chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void VerifyCurrencySymbol(){
		String countriess1 = "Germany,France,Netherland,Italy,Spain,BernardFrance,BernardBelgium,PresselAustria,PresselGermany";
		String countriess2 = "Sweden,Denmark,Norway";
		String countriess3 = "PresselSwitzerland";
		String countriess4 = "UK";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Currency symbol should be present in Cartincheckout and Order Summary as per the country");

		try{
			if(countriess1.contains(countries.get(countrycount))){
				if(getText(locator_split("txtQuantityCartinCheckout")).contains("€") & getText(locator_split("txtOrderSummaryTotal")).contains("€")){
					System.out.println("The currency symbole -€ is present for -"+countries.get(countrycount));
				}else{
					System.out.println("The currency symbole -€ is not present for -"+countries.get(countrycount));
					throw new Error();
				}
			}else if(countriess2.contains(countries.get(countrycount))){
				if(getText(locator_split("txtQuantityCartinCheckout")).contains("Kr") & getText(locator_split("txtOrderSummaryTotal")).contains("Kr")){
					System.out.println("The currency symbole -Kr is present for -"+countries.get(countrycount));
				}else{
					System.out.println("The currency symbole -Kr is not present for -"+countries.get(countrycount));
					throw new Error();
				}
			}else if(countriess3.contains(countries.get(countrycount))){
				if(getText(locator_split("txtQuantityCartinCheckout")).contains("CHF") & getText(locator_split("txtOrderSummaryTotal")).contains("CHF")){
					System.out.println("The currency symbole -CHF is present for -"+countries.get(countrycount));
				}else{
					System.out.println("The currency symbole -CHF is not present for -"+countries.get(countrycount));
					throw new Error();
				}
			}else if(countriess4.contains(countries.get(countrycount))){
				if(getText(locator_split("txtQuantityCartinCheckout")).contains("£") & getText(locator_split("txtOrderSummaryTotal")).contains("£")){
					System.out.println("The currency symbole -£ is present for -"+countries.get(countrycount));
				}else{
					System.out.println("The currency symbole -£ is not present for -"+countries.get(countrycount));
					throw new Error();
				}
			}else{
				System.out.println("incorret country is entered in method for -"+countries.get(countrycount));
				throw new Exception();
			}
		}catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Incorrect country is entered in method for -"+countries.get(countrycount)+" or"
					+ " Element not found");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("imgShiptoStoreinCartinCheckout")
					+ " not found");
		}
		catch(Error e){
			Reporter.log("FAIL_MESSAGE:- The Currency symbole is not matching for country -"+countries.get(countrycount));
			throw new Error("The Currency symbole is not matching for country -"+countries.get(countrycount));
		}

	}

	/* Method Name : --- ----- --- VerifyCouponfieldsinCheckout
	 * Purpose     : --- ----- --- Verifies the number of cupon input txt box and apply button in checkout page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  7/22/2015
	 * Created By  : --- ----- ---  chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void VerifyCouponfieldsinCheckout(){
		String countriess1 = "Denmark,France,PresselSwitzerland,Norway,Spain,BernardFrance,,BernardBelgium,PresselAustria,UK,PresselGermany";
		String countriess2 = "Germany,Spain";
		String countriess3 = "Netherland,Italy";
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Cupon Input txt box and Apply button should be present");

		try{
			if(countriess1.contains(countries.get(countrycount))){
				if(isElementPresent(locator_split("txtCouponinput1inCheckout")) &
						isElementPresent(locator_split("txtCouponinput2inCheckout")) &
						isElementPresent(locator_split("txtCouponinput3inCheckout")) &
						isElementPresent(locator_split("btnCuponApplybuttoninCheckout"))){
					System.out.println("3 cupon input box and apply button is present for -"+countries.get(countrycount));
				}else{
					System.out.println("3 cupon or apply buttons is not present for -"+countries.get(countrycount));
					throw new Error();
				}
			}else if(countriess2.contains(countries.get(countrycount))){
				if(isElementPresent(locator_split("txtCouponinput1inCheckout")) &
						isElementPresent(locator_split("btnCuponApplybuttoninCheckout"))){
					System.out.println("1 cupon input box and apply button is present for -"+countries.get(countrycount));
				}else{
					System.out.println("1 cupon or apply buttons is not present for -"+countries.get(countrycount));
					throw new Error();
				}
			}else if(countriess3.contains(countries.get(countrycount))){
				if(isElementPresent(locator_split("txtCouponinput1inCheckout")) &
						isElementPresent(locator_split("txtCouponinput2inCheckout")) &
						isElementPresent(locator_split("btnCuponApplybuttoninCheckout"))){
					System.out.println("2 cupon input box and apply button is present for -"+countries.get(countrycount));
				}else{
					System.out.println("2 cupon or apply buttons is not present for -"+countries.get(countrycount));
					throw new Error();
				}
			}else{
				System.out.println("incorret country is entered in method for -"+countries.get(countrycount));
				throw new Exception();
			}
		}catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Incorrect country is entered in method for -"+countries.get(countrycount)+" or"
					+ " Element not found");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("imgShiptoStoreinCartinCheckout")
					+ " not found");
		}
		catch(Error e){
			Reporter.log("FAIL_MESSAGE:- Number of cupons is not matching for country or Apply button not present for-"+countries.get(countrycount));
			throw new Error("Number of cupons is not matching for country or Apply button not present for-"+countries.get(countrycount));
		}

	}

	
	/* Method Name : --- ----- --- VerifyDeliveryLinkPopup
	 * Purpose     : --- ----- --- Verifies that when delivery link is clicked in Ordersummary a pop up comes
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- ---  7/23/2015
	 * Created By  : --- ----- ---  chakri
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */ 

	public void VerifyDeliveryLinkPopup(String title){
		String Title = getValue(title);
		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- Pop up should come when delivery link is clicked in order summary");

		try{
			click(locator_split("lnkDeliverylinkinOrderSummary"));
			HashMap<String, String> windowids=getWindowID();
			driver.switchTo().window(windowids.get("childID"));
			verifyPage(Title);
			closeBrowser();
			
		}catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Ship to Store image is not present for country -"+countries.get(countrycount));
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("imgShiptoStoreinCartinCheckout")
					+ " not found");
		}

	}

	/* Method Name : --- ----- --- AddLocation
	 * Purpose     : --- ----- --- To Login from home page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Krishnamurthy K
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void AddLocation(String subtab){
		//String username = getValue(Email);
	//	String password = getValue(Password);

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account should be clicked and user should get logged in");
		try{
			sleep(3000);
			click(locator_split("Maintenencetab"));
			sleep(3000);
			switchframe("PegaGadget1Ifr");
			sleep(2000);
			click(locator_split(subtab));
			sleep(3000);
			//selectList(locator_split("LstLineofBusiness"),1);
			selectListValue(locator_split("LstLineofBusiness"), "ENERGY AND ENGINEERED RISK");
			sleep(3000);
			sendKeys(locator_split("txtlocationName"), "KOPPERS INC.");
			click(locator_split("txtlocationselect"));
			sleep(2000);
			//click(locator_split("Lstlocationselect"));
			sendKeys(locator_split("LstPolicyeffectiveDate"), "01-Jan-2013 - 201310029");
			click(locator_split("txtlocationselect"));
			//selectListValue(locator_split("LstPolicyeffectiveDate"),"01-Jan-2013 - 201310029");
			//selectlistname(locator_split("LstPolicyeffectiveDate"));
			sleep(3000);
			click(locator_split("btnGetLocations"));
		//	click(locator_split("btnAddLocations"));
		/*	clearWebEdit(locator_split("txtLoginNamegrasp"));
			//sendKeys(locator_split("txtLoginNamegrasp"), username);
			//sendKeys(locator_split("txtpasswordgrasp"), password);
			
			sleep(3000);
			click(locator_split("btn_privacyok"));*/
			Reporter.log("PASS_MESSAGE:- Report tab  is clicked and user is logged in");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Report tab is not clicked in");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("LoginLogout")
					+ " not found");

		}

	}
	
	/* Method Name : --- ----- --- Search RFS
	 * Purpose     : --- ----- --- To Search and click the RFS
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Krishnamurthy K
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void SearchRFS(String RFSID){
	//	String RFSID = getValue("RFSName");
	//	String password = getValue(Password);

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account should be clicked and user should get logged in");
		try{
			sleep(3000);
			click(locator_split("RFStab"));
			sleep(3000);
			switchframe("PegaGadget1Ifr");
			sleep(3000);
			//click(locator_split(subtab));
			//sleep(3000);
			//selectList(locator_split("LstLineofBusiness"),1);
			sendKeys(locator_split("RFSName"),RFSID);
			sleep(3000);
			click(locator_split("btnRFSSearch"));
			sleep(3000);
			clickbylinktext(RFSID);
			
				//clickbylinktext("No");
			
		//switchframe("PegaGadget2Ifr");
		//	sleep(3000);
		//clickbylinktext("No");
			
			//sleep(2000);
	
			Reporter.log("PASS_MESSAGE:- Report tab  is clicked and user is logged in");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Report tab is not clicked in");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("LoginLogout")
					+ " not found");

		}

	}
	
	/* Method Name : --- ----- --- LocationAssesment_Location
	 * Purpose     : --- ----- --- LocationAssesment_Location
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Krishnamurthy K
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void LocationAssesment_Location(){
		//String RFSID = getValue("RFSName");
	//	String password = getValue(Password);

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account should be clicked and user should get logged in");
		try{
			
			
			driver.switchTo().defaultContent();
					sleep(3000);
			    System.out.println("switched frame"+ switchframe("PegaGadget2Ifr"));
			    click(locator_split("tabLocationAssesment"));
			    click(locator_split("linkclickLocation"));
			   // driver.findElement(By.xpath("//*[@tabindex='4']/a/span")).click();
			   // driver.findElement(By.xpath("//a[@title='Click here to open Location Assessment']")).click();
			    driver.switchTo().defaultContent();
			    System.out.println("switched frame"+ switchframe("PegaGadget3Ifr"));
			    click(locator_split("tabLocation"));
			    //driver.findElement(By.xpath("//li[@tabindex='0']/a/span")).click();
			    System.out.println("verification");
			    sleep(1000);
				  System.out.println(driver.findElement(By.id("GRASP_LOCATION_NM")).getAttribute("value"));
sleep(2000);
			   
			  //  verifyTextPresent(locator_split("txtLocation"), getValue("Locationname"),"Location Name");
			    if(getAndVerifyTextvalue(locator_split("txtLocation"), getValue("Locationname")) == false){
			    
			    	clearWebEdit(locator_split("txtLocation"));
			    	sleep(1000);
			    	sendKeys((locator_split("txtLocation")), getValue("Locationname"));
			    	sleep(1000);
			    }
			    
			    	
			    
			    	
			 //  getAndVerifyTextvalue(locator_split("txtCity"), getValue("CityName"),"city");
			 // System.out.println(driver.findElement(By.id("GRASP_LOCATION_NM")).getText());
			   
			   if(getAndVerifyTextvalue(locator_split("txtCity"), getValue("CityName"))==false){
				    
			    	clearWebEdit(locator_split("txtCity"));
			    	sleep(1000);
			    	sendKeys((locator_split("txtCity")), getValue("CityName"));
			    	sleep(1000);
			    }
			 //  verifyTextPresent(locator_split("ListCountry"), getValue("Country"),"Country");
			   if(getAndVerifyTextvalue(locator_split("ListCountry"), getValue("Country"))==false){
				    
			    	//clearWebEdit(locator_split("ListCountry"));
			    	sleep(1000);
			    	selectListValue((locator_split("ListCountry")), getValue("Country"));
			    	sleep(1000);
			    }
			 //  verifyTextPresent(locator_split("txtBIvalue"),getValue("BIValue"),"BI Value");
			   if(getAndVerifyTextvalue(locator_split("txtBIvalue"), getValue("BIValue"))==false){
				    
			    	clearWebEdit(locator_split("txtBIvalue"));
			    	sleep(1000);
			    	sendKeys((locator_split("txtBIvalue")), getValue("BIValue"));
			    	sleep(1000);
			    }
			  // verifyTextPresent(locator_split("txtBIindemnityperiod"), getValue("BIIndemnityperiod"),"BI Indemnity period");
			   if(getAndVerifyTextvalue(locator_split("txtBIindemnityperiod"), getValue("BIIndemnityperiod"))==false){
				    
			    	clearWebEdit(locator_split("txtBIindemnityperiod"));
			    	sleep(1000);
			    	sendKeys((locator_split("txtBIindemnityperiod")), getValue("BIIndemnityperiod"));
			    	sleep(1000);
			    }
			 //  verifyTextPresent(locator_split("txtBuildingValue"), getValue("BuildingValue"),"Building");
			   if(getAndVerifyTextvalue(locator_split("txtBuildingValue"), getValue("BuildingValue"))==false){
				    
			    	clearWebEdit(locator_split("txtBuildingValue"));
			    	sleep(1000);
			    	sendKeys((locator_split("txtBuildingValue")), getValue("BuildingValue"));
			    	sleep(1000);
			    }
			  // verifyTextPresent(locator_split("txtMEvalue"), getValue("MandEvalue"),"M+EValue");
			   if(getAndVerifyTextvalue(locator_split("txtMEvalue"), getValue("MandEvalue"))==false){
				    
			    	clearWebEdit(locator_split("txtMEvalue"));
			    	sleep(1000);
			    	sendKeys((locator_split("txtMEvalue")), getValue("MandEvalue"));
			    	sleep(1000);
			    }
			  // verifyTextPresent(locator_split("txtcontentvalue"), getValue("Contentvalue"),"Content value");
			   if(getAndVerifyTextvalue(locator_split("txtcontentvalue"), getValue("Contentvalue"))==false){
				    
			    	clearWebEdit(locator_split("txtcontentvalue"));
			    	sleep(1000);
			    	sendKeys((locator_split("txtcontentvalue")), getValue("Contentvalue"));
			    	sleep(1000);
			    }
			   //verifyTextPresent(locator_split("txtstockinventory"), getValue("Stockvalue"),"Stock inventory");
			   if(getAndVerifyTextvalue(locator_split("txtstockinventory"), getValue("Stockvalue"))==false){
				    
			    	clearWebEdit(locator_split("txtstockinventory"));
			    	sleep(1000);
			    	sendKeys((locator_split("txtstockinventory")), getValue("Stockvalue"));
			    	sleep(1000);
			    }
			  // verifyTextPresent(locator_split("txtotherPD"), getValue("OtherPD"),"Other PD");
			   if(getAndVerifyTextvalue(locator_split("txtotherPD"), getValue("OtherPD"))==false){
				    
			    	clearWebEdit(locator_split("txtotherPD"));
			    	sleep(1000);
			    	sendKeys((locator_split("txtotherPD")), getValue("OtherPD"));
			    	sleep(1000);
			    }
			   //click(locator_split("txtLocationnext"));
				//clickbylinktext("No");
			
		//switchframe("PegaGadget2Ifr");
		sleep(3000);
		//clickbylinktext("No");*/
			if(driver.findElement(By.id("SectionCompleted")).isSelected()==true)
			{
				System.out.println("Check Box alresy selected");
				
			}
			else
			{
				System.out.println("Check box is not selected");
				driver.findElement(By.id("SectionCompleted")).click();
				
			}
	
			Reporter.log("PASS_MESSAGE:- Report tab  is clicked and user is logged in");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Report tab is not clicked in");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("LoginLogout")
					+ " not found");

		}

	}
	
	/* Method Name : --- ----- --- LocationAssesment_COPE
	 * Purpose     : --- ----- --- LocationAssesment_COPE
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Krishnamurthy K
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void LocationAssesment_COPE(){
		String Tradeoccupancy = getValue("TradeOccupancy");
	String Account = getValue("Account");
	 boolean flag=false;

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account should be clicked and user should get logged in");
		try{
			
			
			/*driver.switchTo().defaultContent();
					sleep(3000);
			    System.out.println("switched frame"+ switchframe("PegaGadget2Ifr"));
			    click(locator_split("tabLocationAssesment"));
			    click(locator_split("linkclickLocation"));*/
			   // driver.findElement(By.xpath("//*[@tabindex='4']/a/span")).click();
			   // driver.findElement(By.xpath("//a[@title='Click here to open Location Assessment']")).click();
			    driver.switchTo().defaultContent();
			    System.out.println("switched frame"+ switchframe("PegaGadget3Ifr"));
			    click(locator_split("tabcope"));
			    //driver.findElement(By.xpath("//li[@tabindex='0']/a/span")).click();
			    clearWebEdit(locator_split("txtStoreyscope"));
			    sleep(2000);
			    sendKeys(locator_split("txtStoreyscope"), getValue("Numberofstories"));
			    clearWebEdit(locator_split("txtbuildingheightcope"));
			   sleep(3000);
			    sendKeys(locator_split("txtbuildingheightcope"),getValue("Buildingheight"));
			    
			    selectListValue(locator_split("listbasementcope"), getValue("Basement"));
			   sleep(3000);
			    clearWebEdit(locator_split("txtyearbuildcope"));
			   sleep(3000);
			    sendKeys(locator_split("txtyearbuildcope"), getValue("YearBuild"));
			   sleep(3000);
			    clearWebEdit(locator_split("txtyearlastupgradecope"));
			   sleep(3000);
			    sendKeys(locator_split("txtyearlastupgradecope"), getValue("LastUpgradeyear"));
			    clearWebEdit(locator_split("txtbasementfloorelevationcope"));
			   sleep(3000);
			    sendKeys(locator_split("txtbasementfloorelevationcope"), getValue("FloorElevation"));
			   sleep(3000);
			    System.out.println("Account is"+Account);
			    if(Account.equals("EEA"))
			    {
			    clearWebEdit(locator_split("txttotalareacope"));
			   sleep(3000);
			    sendKeys(locator_split("txttotalareacope"), getValue("TotalAreaSqft"));
			    clearWebEdit(locator_split("txtfireareacope"));
			   sleep(3000);
			    sendKeys(locator_split("txtfireareacope"),getValue("FireArea"));
			   sleep(3000);
			    selectListValue(locator_split("listroofcondition"),getValue("Roofcondition"));
			   sleep(3000);
			    
			    }
			    if(Account.equals("CP")){
			    	System.out.println("inside CP");
			    	sleep(2000);
			    	selectListValue(locator_split("listpredominantcope"), getValue("Predominantconstruction"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtis06AA"));
				    sleep(5000);
				    sendKeys(locator_split("txtis06AA"),getValue("ClassAA"));
					sleep(2000);
					   System.out.println("Value entered in AA");
					   click(locator_split("txtis05A"));
					   sleep(10000);
				 clearWebEdit(locator_split("txtis05A"));
				    sleep(5000);
				    sendKeys(locator_split("txtis05A"),getValue("ClassA"));
				    sleep(2000);
				    click(locator_split("txtis04"));
				    sleep(10000);
				    clearWebEdit(locator_split("txtis04"));
				    sleep(5000);
				  sendKeys(locator_split("txtis04"),getValue("ClassB"));
				    sleep(2000);
				    click(locator_split("txtcmd04"));
				  sleep(10000);
				    clearWebEdit(locator_split("txtcmd04"));
				    sleep(5000);
				    sendKeys(locator_split("txtcmd04"),getValue("ClassC"));
				    sleep(2000);
				    click(locator_split("txtis03"));
				    sleep(10000);
				    clearWebEdit(locator_split("txtis03"));
				    sleep(5000);
				   sendKeys(locator_split("txtis03"),getValue("IS03ClassB"));
				   sleep(2000);
				    click(locator_split("txtcmd03"));
				   sleep(10000);
				    clearWebEdit(locator_split("txtcmd03"));
				   sleep(5000);
				    sendKeys(locator_split("txtcmd03"),getValue("CMDISO3ClassC"));
				    sleep(2000);
				    click(locator_split("txtis02"));
				    sleep(10000);
				    clearWebEdit(locator_split("txtis02"));
				    sleep(5000);
				    sendKeys(locator_split("txtis02"),getValue("IS02ClassC"));
				    sleep(2000);
				    click(locator_split("txtis01"));
				    sleep(10000);
				    clearWebEdit(locator_split("txtis01"));
				    sleep(5000);
				    sendKeys(locator_split("txtis01"),getValue("IS01"));
			    		sleep(5000);	    
			    
			    }
			    selectListValue(locator_split("listtradeoccupancy"), getValue("TradeOccupancy"));
			    sleep(3000);
			    selectListValue(locator_split("listhazardgrade"), getValue("Hazardgrade"));
			    sleep(3000);
			    
			    if (Tradeoccupancy.equals("Oil & Chemical")&& (Account.equals("EEA")))
			    {
			    	 sleep(1000);
			    selectListValue(locator_split("listfireindex"), getValue("Fireindex"));
			    sleep(1000);
			    selectListValue(locator_split("listexplosionindex"),getValue("Explosionindex"));
			    		
			    sleep(1000);
			    }
			   
				sleep(3000);
			    clearWebEdit(locator_split("txtoperatinghours"));
			    sleep(3000);
			    sendKeys(locator_split("txtoperatinghours"), getValue("Operatinghours"));
			    sleep(1000);
			    clearWebEdit(locator_split("txtoperatingdays"));
			    sleep(3000);
			    sendKeys(locator_split("txtoperatingdays"), getValue("Operatingdays"));
			    sleep(3000);
			    clearWebEdit(locator_split("txtautosprinkler"));
			    sleep(3000);
			    sendKeys(locator_split("txtautosprinkler"), getValue("Autosprinkler"));
				sleep(3000);
			    clearWebEdit(locator_split("txtadequate"));
			    sleep(3000);
			    sendKeys(locator_split("txtadequate"), getValue("Adequate"));
				sleep(3000);
			    clearWebEdit(locator_split("txtasn"));
			    sleep(3000);
			    sendKeys(locator_split("txtasn"), getValue("ASN"));
				sleep(3000);
			    clearWebEdit(locator_split("txtdeduction"));
			    sleep(3000);
			    sendKeys(locator_split("txtdeduction"), getValue("Deduction"));
			    sleep(5000);
			    click(locator_split("txtyearbuildcope"));
			    sleep(2000);
			   
/*WebElement checkbox=driver.findElement(By.xpath("//img[contains(@name,'SaveButtonsForCOPE_pyWorkPage')]")).
sleep(2000);
highlight(checkbox);*/
System.out.println("Checkbox status");
sleep(2000);
/*System.out.println(driver.findElement(By.xpath("//img[@src='webwb/lv_unchecked_12144257304.gif!!.gif']")).isDisplayed());
sleep(2000);
if(driver.findElement(By.xpath("//img[@src='webwb/lv_unchecked_12144257304.gif!!.gif']")).isDisplayed()== true)
{
	sleep(2000);
	click(locator_split("checkboxsavecope"));
	sleep(2000);
	//driver.findElement(By.xpath("//img[@src='webwb/lv_unchecked_12144257304.gif!!.gif']")).click();
}else{
	System.out.println("Check box already checked");
}*/
//System.out.println(checkbox.getAttribute(arg0)());
			 // System.out.println(verifyCheckboxChecked(locator_split("checkboxsavecope")));
		//	flag=verifyCheckboxChecked(locator_split("xpath-//img[contains(@name,'SaveButtonsForCOPE_pyWorkPage')]"));
		/*	  sleep(2000);
			 if(!checkbox.isSelected()){
				  System.out.println("inside if loop");
				  checkbox.click();
				  sleep(2000);
			  }
			  else{
				  checkbox.click();
				  System.out.println("inside else loop");
				  sleep(2000);
				  
				  
				  checkbox.click();
				  sleep(2000);
			  }*/
			  
			 /* driver.findElement(By.xpath("//label[contains(.,'Click here if the Screen is Completed')]")).click();
			  Robot r=new Robot();
			  r.keyPress(java.awt.event.KeyEvent.VK_TAB);
			  sleep(1000);
			  r.keyPress(java.awt.event.KeyEvent.VK_ENTER);
			  sleep(2000);*/
		/*if (flag==true){
			  System.out.println("flag"+flag);
			 System.out.println("Checked Already");
			 sleep(2000);
			 click(locator_split("checkboxsavecope"));
			 //sleep(2000);
			 	//		 click(locator_split("checkboxsavecope"));
		 click(locator_split("btncopesave"));
			 sleep(2000);
			 }else{
				
				 sleep(2000);
				 click(locator_split("checkboxsavecope"));
				 sleep(3000);
			 }
			 */
			    
			    sleep(5000);
			    if(getValue("BROWSER").equalsIgnoreCase("InternetExplorer")){
			   System.out.println("inside iE loop");
			    	click(locator_split("btncopesave_ie"));	
			    }else{
			    	click(locator_split("btncopesave_ie"));
			    }
		
			    sleep(5000);
			    click(locator_split("txtLocationnext"));
			   sleep(3000);
				//clickbylinktext("No");
			
		//switchframe("PegaGadget2Ifr");
		//	sleep(3000);
		//clickbylinktext("No");
			
			
	
			Reporter.log("PASS_MESSAGE:- Report tab  is clicked and user is logged in");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Report tab is not clicked in");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("LoginLogout")
					+ " not found");

		}

	}
	public void LocationAssesment_COPE_updated(){
		String Tradeoccupancy = getValue("TradeOccupancy");
	String Account = getValue("Account");
	 boolean flag=false;

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account should be clicked and user should get logged in");
		try{
			
			
			/*driver.switchTo().defaultContent();
					sleep(3000);
			    System.out.println("switched frame"+ switchframe("PegaGadget2Ifr"));
			    click(locator_split("tabLocationAssesment"));
			    click(locator_split("linkclickLocation"));*/
			   // driver.findElement(By.xpath("//*[@tabindex='4']/a/span")).click();
			   // driver.findElement(By.xpath("//a[@title='Click here to open Location Assessment']")).click();
			    driver.switchTo().defaultContent();
			    System.out.println("switched frame"+ switchframe("PegaGadget3Ifr"));
			    click(locator_split("tabcope"));
			    sleep(2000);
			    click(locator_split("btnconstructionsearch"));
			    sleep(2000);
			    //driver.findElement(By.xpath("//li[@tabindex='0']/a/span")).click();
			 // This will return the number of windows opened by Webdriver and will return Set of St//rings
			    String parent=driver.getWindowHandle();
			    Set<String>s1=driver.getWindowHandles();
			     
			    // Now we will iterate using Iterator
			    Iterator<String> I1= s1.iterator();
			     
			    while(I1.hasNext())
			    {
			     
			       String child_window=I1.next();
			     
			    // Here we will compare if parent window is not equal to child window then we            will close
			     
			    if(!parent.equals(child_window))
			    {
			    	 System.out.println(driver.getTitle()); 
			    driver.switchTo().window(child_window);
			   
			    System.out.println(driver.switchTo().window(child_window).getTitle());
			     
			    sleep(5000);
			    sendKeys(locator_split("txtpopupconstruction"), getValue("ConstructionSearch"));
			    sleep(2000);
			    click(locator_split("btnpopupconstructionsearch"));
			    sleep(5000);
			    sleep(2000);
			   WebElement radiobutton=driver.findElement(By.xpath(".//*[@name='$PConstructionSearchResult$ppxResults$l4$ppySelected'][@type='radio']"));
			     System.out.println(radiobutton.isEnabled());
			     sleep(2000);
			        radiobutton.click();
			     try{
			            sleep(8000);
			   		 click(locator_split("btnpopupselectConstruction"));
			      }catch(Exception e){
			    	e.printStackTrace();
			    }
			        
		
			  // sleep(7000);
			    //$PConstructionSearchResult$ppxResults$l1$ppySelected
			 
			   
			// driver.close();
			  // System.out.println("child browser"+driver.getTitle());
			    }
			    
			 
			    }
			    sleep(5000);
			    System.out.println("mainbrowser");
			    sleep(3000);
			    
			    driver.switchTo().window(parent);
			    sleep(3000);
			    System.out.println("returned to main browser");
			    sleep(2000);
			    System.out.println("After Switch"+driver.getTitle());
			    sleep(2000);
			    driver.switchTo().defaultContent();
			    System.out.println("switched frame"+ switchframe("PegaGadget3Ifr"));
			    sleep(2000);
			    clearWebEdit(locator_split("txtStoreyscope"));
			    sleep(2000);
			 //   sleep(2000);
			    sendKeys(locator_split("txtStoreyscope"), getValue("Numberofstories"));
			    clearWebEdit(locator_split("txtbuildingheightcope"));
			   sleep(3000);
			    sendKeys(locator_split("txtbuildingheightcope"),getValue("Buildingheight"));
			    
			    selectListValue(locator_split("listbasementcope"), getValue("Basement"));
			   sleep(3000);
			    clearWebEdit(locator_split("txtyearbuildcope"));
			   sleep(3000);
			    sendKeys(locator_split("txtyearbuildcope"), getValue("YearBuild"));
			   sleep(3000);
			    clearWebEdit(locator_split("txtyearlastupgradecope"));
			   sleep(3000);
			    sendKeys(locator_split("txtyearlastupgradecope"), getValue("LastUpgradeyear"));
			    clearWebEdit(locator_split("txtbasementfloorelevationcope"));
			   sleep(3000);
			    sendKeys(locator_split("txtbasementfloorelevationcope"), getValue("FloorElevation"));
			   sleep(3000);
			   System.out.println("Account is"+Account);
			    if(Account.equals("EEA"))
			    {
			    clearWebEdit(locator_split("txttotalareacope"));
			   sleep(3000);
			    sendKeys(locator_split("txttotalareacope"), getValue("TotalAreaSqft"));
			    sleep(2000);
			    clearWebEdit(locator_split("txtfireareacope"));
			   sleep(3000);
			    sendKeys(locator_split("txtfireareacope"),getValue("FireArea"));
			   sleep(3000);
			    selectListValue(locator_split("listroofcondition"),getValue("Roofcondition"));
			   sleep(3000);
			    sleep(10000);
			    }
			    if(Account.equals("CP")){
			    	System.out.println("inside CP");
			    	sleep(2000);
			    	selectListValue(locator_split("listpredominantcope"), getValue("Predominantconstruction"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtis06AA"));
				    sleep(5000);
				    sendKeys(locator_split("txtis06AA"),getValue("ClassAA"));
					sleep(2000);
					   System.out.println("Value entered in AA");
					   click(locator_split("txtis05A"));
					   sleep(10000);
				 clearWebEdit(locator_split("txtis05A"));
				    sleep(5000);
				    sendKeys(locator_split("txtis05A"),getValue("ClassA"));
				    sleep(2000);
				    click(locator_split("txtis04"));
				    sleep(10000);
				    clearWebEdit(locator_split("txtis04"));
				    sleep(5000);
				  sendKeys(locator_split("txtis04"),getValue("ClassB"));
				    sleep(2000);
				    click(locator_split("txtcmd04"));
				  sleep(10000);
				    clearWebEdit(locator_split("txtcmd04"));
				    sleep(5000);
				    sendKeys(locator_split("txtcmd04"),getValue("ClassC"));
				    sleep(2000);
				    click(locator_split("txtis03"));
				    sleep(10000);
				    clearWebEdit(locator_split("txtis03"));
				    sleep(5000);
				   sendKeys(locator_split("txtis03"),getValue("IS03ClassB"));
				   sleep(2000);
				    click(locator_split("txtcmd03"));
				   sleep(10000);
				    clearWebEdit(locator_split("txtcmd03"));
				   sleep(5000);
				    sendKeys(locator_split("txtcmd03"),getValue("CMDISO3ClassC"));
				    sleep(2000);
				    click(locator_split("txtis02"));
				    sleep(10000);
				    clearWebEdit(locator_split("txtis02"));
				    sleep(5000);
				    sendKeys(locator_split("txtis02"),getValue("IS02ClassC"));
				    sleep(2000);
				    click(locator_split("txtis01"));
				    sleep(10000);
				    clearWebEdit(locator_split("txtis01"));
				    sleep(5000);
				    sendKeys(locator_split("txtis01"),getValue("IS01"));
			    		sleep(5000);	    
			    
			    }
			sleep(5000);
			    click(locator_split("btnsicsearch"));
			    System.out.println("Sic Search clicked");
			    sleep(10000);
			    String parent1=driver.getWindowHandle();
			    Set<String>s2=driver.getWindowHandles();
			     
			    // Now we will iterate using Iterator
			    Iterator<String> I2= s2.iterator();
			     
			    while(I2.hasNext()) {
			     
			       String child_window2=I2.next();
			     
			    // Here we will compare if parent window is not equal to child 


			     
			    if(!parent1.equals(child_window2))
			    {
			    driver.switchTo().window(child_window2);
			     
			    System.out.println(driver.switchTo().window(child_window2).getTitle());
			   // driver.findElement(By.xpath(".//*[@id='SICSearchFlag']"));
			    sleep(2000);
			    selectListValue(locator_split("listsicsearchpopup"),getValue("Siclist"));			    
			    sleep(2000);
			    sleep(2000);
				  //  sendKeys(locator_split("txtsicsearch3digit"),"t");
		//		   String  searchvalue="4 Digit SIC";
				    if (getValue("Siclist").equals("3 Digit SIC")){
				    	  sleep(2000);
				    	  System.out.println("inside if loop");
				    	  sleep(5000);
				     sendKeys(locator_split("txtsicsearch3digit"), getValue("SICSearch"));
				   // 	sendKeys(locator_split("txtsicsearch3digit"),"t");
				    	sleep(5000);
				    //	 sendKeys(locator_split("txtsicsearch3digit"), getValue("SICSearch"));
				    	 sleep(2000);
				    	click(locator_split("btnsicsearchpopup"));
				    	sleep(10000);
				    	click(locator_split("btnsicsearchpopup"));
				    	sleep(2000);
				    	WebElement radiobutton=driver.findElement(By.xpath(".//*[@name='$PSICSearchResult$ppxResults$l1$pSelectSIC'][@type='radio']"));
				    	System.out.println(radiobutton.isEnabled());
					     sleep(2000);
					     radiobutton.click();
					    sleep(2000);
				    }else{
				    	sendKeys(locator_split("txtsicsearch4digit"),getValue("SICSearch"));
				    	sleep(3000);
				    	WebElement radiobutton=driver.findElement(By.xpath(".//*[@name='$PSICSearchResult$ppxResults$l1$ppySelected'][@type='radio']"));
				    	System.out.println(radiobutton.isEnabled());
					     sleep(2000);
					     radiobutton.click();
					     
				    }
				    sleep(5000);
				    try{
				    	 click(locator_split("btnpopupselectsic"));
				    	 
				    }catch(Exception e){
				    	e.printStackTrace();
				    }
				 //   click(locator_split("btnpopupselect"));
				    sleep(2000);
			    
			    }
			    }
			    
			    
			    driver.switchTo().window(parent1);
			    sleep(3000);
			    System.out.println("returned to main browser");
			    sleep(3000);
			    System.out.println("After Switch"+driver.getTitle());
			    sleep(3000);
			    driver.switchTo().defaultContent();
			    System.out.println("switched frame"+ switchframe("PegaGadget3Ifr"));
			    sleep(5000);
			    selectListValue(locator_split("listtradeoccupancy"), getValue("TradeOccupancy"));
			    sleep(3000);
			    selectListValue(locator_split("listhazardgrade"), getValue("Hazardgrade"));
			    sleep(10000);
			    if (Tradeoccupancy.equals("Oil & Chemical")&& (Account.equals("EEA")))
			    {
			    	 sleep(1000);
			    	 System.out.println("inside if loop");
			    selectListValue(locator_split("listfireindex"), getValue("Fireindex"));
			    sleep(1000);
			    selectListValue(locator_split("listexplosionindex"),getValue("Explosionindex"));
			    		
			    sleep(1000);
			    }
			    
			    
		
			    
			    sleep(3000);
			    System.out.println("before clicking occupancy search ");
			    sleep(5000);
			    click(locator_split("btnoccupancysearch"));
			    sleep(3000);
			    String parent2=driver.getWindowHandle();
			    Set<String>s3=driver.getWindowHandles();
			     sleep(5000);
			    // Now we will iterate using Iterator
			    Iterator<String> I3= s3.iterator();
			     
			    while(I3.hasNext())
			    {
			     
			       String child_window3=I3.next();
			     
			    // Here we will compare if parent window is not equal to child window then we            will close
			     
			    if(!parent2.equals(child_window3))
			    {
			    	sleep(2000);
			    driver.switchTo().window(child_window3);
			    sleep(5000);
			     System.out.println("inside if loop");
			     sleep(2000);
			    System.out.println(driver.switchTo().window(child_window3).getTitle());
			    
			    //selectListValue(locator_split("selectListValue"), getValue("3 Digit SIC"));			    
			    //sleep(2000);
			  //  sendKeys(locator_split("txtsicsearch3digit"),"t");
			   sleep(3000);
			   
			    	sendKeys(locator_split("txtoccupancytypepopup"),getValue("Occupancysearch"));
			    	sleep(3000);
			    	click(locator_split("btnoccupancysearchpopup"));
			    	sleep(5000);
			    	WebElement radiobutton=driver.findElement(By.xpath(".//*[@name='$POccupancySearchResult$ppxResults$l1$ppySelected'][@type='radio']"));

			    	
			    	
			    	System.out.println(radiobutton.isEnabled());
				     sleep(2000);
				     radiobutton.click();
				    sleep(2000);
				     try{
				    	 click(locator_split("btnpopupselectoccupancy"));
				     }catch(Exception e){
				    	// e.printStackTrace();
				    	 
				     }
				 //   click(locator_split("btnpopupselect"));
				    sleep(2000);
			    	
			    	sleep(3000);
			    	
			   
			    
			    
			    }
			    
			    
			    }
			    
			    
			    driver.switchTo().window(parent2);
			    sleep(3000);
			    System.out.println("returned to main browser");
			    sleep(2000);
			    System.out.println("After Switch"+driver.getTitle());
			    sleep(2000);
			    driver.switchTo().defaultContent();
			    System.out.println("switched frame"+ switchframe("PegaGadget3Ifr"));
			    sleep(5000);
			    
				//sleep(3000);
			    clearWebEdit(locator_split("txtoperatinghours"));
			    sleep(3000);
			    sendKeys(locator_split("txtoperatinghours"), getValue("Operatinghours"));
			    sleep(1000);
			    clearWebEdit(locator_split("txtoperatingdays"));
			    sleep(3000);
			    sendKeys(locator_split("txtoperatingdays"), getValue("Operatingdays"));
			    sleep(3000);
			    if(Account.equals("CP")){
			        selectListValue(locator_split("listothertenents"), "Yes");
				    sleep(3000);
			    }
			sleep(3000);
			    clearWebEdit(locator_split("txtautosprinkler"));
			    sleep(3000);
			    sendKeys(locator_split("txtautosprinkler"), getValue("Autosprinkler"));
				sleep(3000);
			    clearWebEdit(locator_split("txtadequate"));
			    sleep(3000);
			    sendKeys(locator_split("txtadequate"), getValue("Adequate"));
				sleep(3000);
			    clearWebEdit(locator_split("txtasn"));
			    sleep(3000);
			    sendKeys(locator_split("txtasn"), getValue("ASN"));
				sleep(3000);
			    clearWebEdit(locator_split("txtdeduction"));
			    sleep(3000);
			    sendKeys(locator_split("txtdeduction"), getValue("Deduction"));
			    sleep(3000);
			    if(Account.equals("CP")){
			    	
			    	
			    selectListValue(locator_split("listprotectionclass"), "Adequately sprinklered where needed");
			    sleep(5000);
			    }
    driver.findElement(By.xpath("//a[@title='Add a row ']")).click();
			    sleep(2000);
			    System.out.println("after anchor tag");
			    // add row
			  click(locator_split("linkaddrow"));
			   //driver.
			    sleep(3000);
			  sendKeys(locator_split("txtaddrwareaprotected"), getValue("Areaproteted"));
			   sleep(2000);
			   sendKeys(locator_split("txtaddrwsystemnumber"), getValue("SystemNumber"));
			   sleep(2000);
			   sendKeys(locator_split("txtaddrwexistdesign"), getValue("Existingdesign"));
			   sleep(2000);
			   sendKeys(locator_split("txtaddrwrequireddesign"), getValue("Requireddesign"));
			   sleep(2000);
			   sendKeys(locator_split("txtaddrwexistingborflow"), getValue("ExistingBORflow"));
			   sleep(2000);
			   sendKeys(locator_split("txtaddrwexistingborpress"), getValue("ExistingBorePress"));
			   sleep(2000);
			   sendKeys(locator_split("txtaddrwsprinklertemp"), getValue("SprinklerTemp"));
			   sleep(2000);
			   sendKeys(locator_split("txtaddrwhose"), getValue("Hose"));
			   sleep(5000);
			   selectListValue(locator_split("listadequency"), "Adequate");
			   sleep(5000);
			   click(locator_split("iconrefresh"));
			   
			   sleep(5000);
			   click(locator_split("btnaddrwsave"));
			   sleep(5000);
			   sleep(3000);
			   click(locator_split("btncopesave_ie"));
			   sleep(3000);
			   click(locator_split("btncopesave_ie"));
			   sleep(3000);
			   System.out.println("After Save");
			   try{
				   
			  
			  if(driver.findElement(By.xpath(".//*[@id='RULE_KEY']/table[2]/tbody/tr[2]/td[2]/nobr/span/button")).isDisplayed()==true)
			  {
				  System.out.println("inside continue button");
				   sleep(2000);
				   click(locator_split("btncontinue"));
				   
			   }
			   }catch(Exception e)
			   {
			   }
			   
			   sleep(2000);
			   if (driver.findElement(By.xpath("//img[contains(@name,'SaveButtonsForCOPE_pyWorkPage')]")).isSelected()== true ){
				   System.out.println("inside if loop");
				   driver.findElement(By.xpath("//img[contains(@name,'SaveButtonsForCOPE_pyWorkPage')]")).click();
				   
			   }else{
				   System.out.println("checkbox not checked");
				   sleep(2000);
				   //driver.findElement(By.xpath("//img[contains(@name,'SaveButtonsForCOPE_pyWorkPage')]")).click();
			click(locator_split("checkboxsavecope"));
			   sleep(3000);
			   }
			  
			   sleep(3000);
			   
			Reporter.log("PASS_MESSAGE:- Report tab  is clicked and user is logged in");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Report tab is not clicked in");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("LoginLogout")
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- LocationAssesment_Location
	 * Purpose     : --- ----- --- LocationAssesment_Location
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Krishnamurthy K
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void WaterSupplyDetail(){
	

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account should be clicked and user should get logged in");
		try{
			
			
			/*driver.switchTo().defaultContent();
					sleep(3000);
			    System.out.println("switched frame"+ switchframe("PegaGadget2Ifr"));
			    click(locator_split("tabLocationAssesment"));
			    click(locator_split("linkclickLocation"));*/
			   // driver.findElement(By.xpath("//*[@tabindex='4']/a/span")).click();
			   // driver.findElement(By.xpath("//a[@title='Click here to open Location Assessment']")).click();
			sleep(5000);
			    driver.switchTo().defaultContent();
			    System.out.println("switched frame"+ switchframe("PegaGadget3Ifr"));
			    click(locator_split("tabwatersupply"));
			    sleep(2000);
			    click(locator_split("btnaddwatersupply"));
			    sleep(2000);
			    selectListValue(locator_split("listwatersupplytype"), getValue("WaterSupplyType"));
			    sleep(2000);
			    selectListValue(locator_split("listpumpsassociated"), getValue("PumpsAssociated"));
			    sleep(2000);
			    selectListValue(locator_split("listwateradequecy"), getValue("Adequecy"));
			    sleep(2000);
			   click(locator_split("expandwatersupplydata"));
			   sleep(5000);
			   click(locator_split("linkaddrow"));
			   sleep(5000);
			   click(locator_split("linkaddrow"));
			   sleep(5000);
  			    
			    sendKeys(locator_split("txtwatersupplytstname"), getValue("WaterSupplyTestName"));
			   
			   sleep(3000);
			    sendKeys(locator_split("txtwatersupplytestdate"),getValue("TestDate"));
			  //select[@id='WATER_SUPPLY_TYPE_CD']
			   
			   sleep(3000);
			   sendKeys(locator_split("txtwatersupplytestedby"),getValue("Testedby"));
			   sleep(2000);
			   
			   sendKeys(locator_split("txtstatipressure"), getValue("StaticPressure"));
			   sleep(2000);
			    
			    sendKeys(locator_split("txtresidualpressure"), getValue("ResidualPressure"));
			    sleep(2000);
			    sendKeys(locator_split("txtresidualflowrate"), getValue("ResidualFlowRate"));
			   sleep(2000);
			   sendKeys(locator_split("txtpressureloss"), getValue("PressureLoss"));
			   sleep(2000);
			   sendKeys(locator_split("txtlocationpressurereadings"), getValue("PressureReadings"));
			   sleep(2000);
			   sendKeys(locator_split("txtlocationflowreading"), getValue("FlowReadings"));
			   sleep(2000);
			   click(locator_split("btnwatersupplysave"));
			   sleep(5000);
			   click(locator_split("checkboxclick"));
			   sleep(2000);
			   click(locator_split("expandwatersupplygraph"));
			   sleep(2000);
			   selectListValue(locator_split("listwatersupplygraph"), getValue("GraphType"));
			   sleep(2000);
			   click(locator_split("btnwatersupplysaveall"));
			    
			// click(locator_split("btncopesave"));
			 sleep(5000);
			   
			   
				//clickbylinktext("No");
			
		//switchframe("PegaGadget2Ifr");
		//	sleep(3000);
		//clickbylinktext("No");
			
			
	
			Reporter.log("PASS_MESSAGE:- Report tab  is clicked and user is logged in");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Report tab is not clicked in");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("LoginLogout")
					+ " not found");

		}

	}

	/* Method Name : --- ----- --- LocationAssesment_Hazards
	 * Purpose     : --- ----- --- LocationAssesment_Hazards
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Krishnamurthy K
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void Hazards(){
	

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account should be clicked and user should get logged in");
		try{
			
			
			/*driver.switchTo().defaultContent();
					sleep(3000);
			    System.out.println("switched frame"+ switchframe("PegaGadget2Ifr"));
			    click(locator_split("tabLocationAssesment"));
			    click(locator_split("linkclickLocation"));*/
			   // driver.findElement(By.xpath("//*[@tabindex='4']/a/span")).click();
			   // driver.findElement(By.xpath("//a[@title='Click here to open Location Assessment']")).click();
			sleep(5000);
			    driver.switchTo().defaultContent();
			    System.out.println("switched frame"+ switchframe("PegaGadget3Ifr"));
			    click(locator_split("tabhazards"));
			    sleep(2000);
			    click(locator_split("btnaddrowhazards"));
			    sleep(5000);
			    Robot r1=new Robot();
				 // r.keyPress(java.awt.event.KeyEvent.);
				  sleep(1000);
				  // Press Enter
				  r1.keyPress(java.awt.event.KeyEvent.VK_ENTER);
				  sleep(1000);
			   sleep(5000);
			    	
				    selectListValue(locator_split("listhazardsmaintype"), getValue("HazardsMainType"));
				    sleep(2000);
			    
			    selectListValue(locator_split("listhazardtype"), getValue("HazardsType"));
			    sleep(2000);
			    if(getValue("HazardsType").equals("Storage")){
			    	
			    	clearWebEdit(locator_split("txtcustomhazardtype"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtcustomhazardtype"), getValue("CustomHazardType"));
			    	
				    sleep(2000);
			    	
			    }
			 
			    
			    
			    selectListValue(locator_split("lsthazardcategory"), getValue("HazardCategory"));
			    sleep(2000);
			    clearWebEdit(locator_split("txthazardareasize"));
			    sleep(2000);
			    sendKeys(locator_split("txthazardareasize"), getValue("HazardAreaSize"));
			    sleep(2000);
			    selectListValue(locator_split("lsthazardclass"), getValue("HazardClass"));
			    sleep(2000);
			    clearWebEdit(locator_split("txtspecificlocationname"));
			    sleep(2000);
			    sendKeys(locator_split("txtspecificlocationname"), getValue("SpecificLocationName"));
			    sleep(2000);
			    selectListValue(locator_split("lstdeficiencies"), getValue("Deficiencies"));
			    sleep(2000);
			    clearWebEdit(locator_split("txtareadeficienciesdescription"));
			    sleep(2000);
			    sendKeys(locator_split("txtareadeficienciesdescription"), getValue("DeficienciesDescription"));
			    sleep(2000);
			    if (getValue("HazardsMainType").equals("Storage")){
			    	 clearWebEdit(locator_split("txtstorageheight"));
					    sleep(2000);
					    sendKeys(locator_split("txtstorageheight"), getValue("StorageHeight"));
					    sleep(2000);
					clearWebEdit(locator_split("txtceilingheight"));
						    sleep(2000);
						    sendKeys(locator_split("txtceilingheight"), getValue("CeilingHeight"));
						    sleep(2000);
					selectListValue(locator_split("lstpredominantclass"), getValue("PredominantClass"));
						    sleep(2000);
						    selectListValue(locator_split("lststoragearrangement"), getValue("StorageArrangement"));
						    sleep(2000);
						     
			    }
			    sleep(2000);
			    			   click(locator_split("btnuploadphotos"));
			   sleep(5000);
			 
  			    
			    sendKeys(locator_split("txtphotocaption"), getValue("PhotoCaption"));
			   
			   sleep(3000);
			   
			   
			  click(locator_split("btnbrowse"));
			   sleep(5000);
			   Runtime.getRuntime().exec("D:\\Grasp\\AutoIT\\UploadFile.exe");
			  // click(locator_split("btnbrowse"));
			  /* StringSelection sel = new StringSelection("C:\\Users\\Public\\Pictures\\Sample Pictures\\Penguins.jpg");
			   Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel,null);
			   System.out.println("selection" +sel);
			   
			 sleep(2000);
			   Robot r=new Robot();
				 // r.keyPress(java.awt.event.KeyEvent.);
				  sleep(3000);
				  // Press Enter
				  r.keyPress(java.awt.event.KeyEvent.VK_ENTER);
				  sleep(1000);
				 // Release Enter
				  r.keyRelease(java.awt.event.KeyEvent.VK_ENTER);
				  sleep(1000);
				   // Press CTRL+V
				  r.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
				  sleep(1000);
				  //r.keyPress(KeyEvent.VK_CONTROL);
				  r.keyRelease(java.awt.event.KeyEvent.VK_V);
				  //r.keyPress(KeyEvent.VK_V);
				  sleep(1000);
				 // Release CTRL+V
				  r.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
				  sleep(1000);
				//  r.keyRelease(KeyEvent.VK_CONTROL);
				  r.keyRelease(java.awt.event.KeyEvent.VK_V);
				  sleep(2000);
				  r.keyPress(java.awt.event.KeyEvent.VK_TAB);
				  sleep(2000);
				  r.keyPress(java.awt.event.KeyEvent.VK_TAB);
				  sleep(2000);
				  r.keyPress(java.awt.event.KeyEvent.VK_ENTER);
				  sleep(2000);*/
			  
			    
			
			 sleep(10000);
			click(locator_split("btnsavebrowse"));
			   sleep(4000);
			   click(locator_split("btnclose"));
			   sleep(2000);
				//clickbylinktext("No");
			   click(locator_split("btnhazardclose"));
			   sleep(3000);
			   
			  
			   if (driver.findElement(By.xpath("//input[contains(@class,'checkbox chkBxCtl')]")).isSelected()== true ){
				   System.out.println("inside if loop");
				   
			   }else{
				   System.out.println("checkbox not checked");
				   sleep(2000);
			   click(locator_split("inputcheckhazard"));
			   sleep(3000);
			   }
			  
			   sleep(3000);
			
		//switchframe("PegaGadget2Ifr");
		//	sleep(3000);
		//clickbylinktext("No");
			
			
	
			Reporter.log("PASS_MESSAGE:- Report tab  is clicked and user is logged in");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Report tab is not clicked in");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("LoginLogout")
					+ " not found");

		}

	}
	

	/* Method Name : --- ----- --- LocationAssesment_LossEstimates
	 * Purpose     : --- ----- --- LocationAssesment_LossEstimates
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Krishnamurthy K
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void LossEstimates(){
	

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account should be clicked and user should get logged in");
		try{
			
			
			/*driver.switchTo().defaultContent();
					sleep(3000);
			    System.out.println("switched frame"+ switchframe("PegaGadget2Ifr"));
			    click(locator_split("tabLocationAssesment"));
			    click(locator_split("linkclickLocation"));*/
			   // driver.findElement(By.xpath("//*[@tabindex='4']/a/span")).click();
			   // driver.findElement(By.xpath("//a[@title='Click here to open Location Assessment']")).click();
			sleep(5000);
			if(getValue("Account").equals("EEA")){
			    driver.switchTo().defaultContent();
			    System.out.println("switched frame"+ switchframe("PegaGadget3Ifr"));
			    click(locator_split("tablossestimates"));
			    sleep(2000);
			    click(locator_split("tabeml"));
			    sleep(5000);
			    click(locator_split("linkexpandpdlossestimates"));
			    sleep(3000);
			    if (getValue("Radiobutton").contains("Value")){
			     	ClickRadiobutton(locator_split("radiovalue"));
			    	
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtbuildingdamage"));
			    	sleep(3000);
			    	clearWebEdit(locator_split("txtbuildingdamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtbuildingdamage"), getValue("BuildingDamage"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtmeDamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtmeDamage"), getValue("MEDamage"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtcontentdamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtcontentdamage"), getValue("ContentDamage"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtstockdamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtstockdamage"), getValue("StockDamage"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtotherpddamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtotherpddamage"), getValue("OtherPDDamage"));
			    	sleep(1000);
			    	
			    }else{
			    	ClickRadiobutton(locator_split("radiopercentage"));
			    	clearWebEdit(locator_split("txtbuildingdamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtbuildingdamagepc"), getValue("BuildingDamagePC"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtmedamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtmedamagepc"), getValue("MEDamagePC"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtcontentdamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtcontentdamagepc"), getValue("ContentDamagePC"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtstockdamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtstockdamagepc"), getValue("StockDamagePC"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtotherpddmagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtotherpddmagepc"), getValue("OtherPDDamagePC"));
			    	sleep(1000);
			    	
			    }
			    
			    click(locator_split("linkexpandbilossestimates"));
			    sleep(3000);
			 //   click(locator_split("linkaddroweml"));
			 //   sleep(3000);
			   // click(locator_split("linkaddroweml"));
			//    sleep(3000);
			    
			    selectListValue(locator_split("listbidowntime"), getValue("BIDownTime"));
			    sleep(2000);
			    clearWebEdit(locator_split("txtbiaffected"));
			    sleep(2000);
			    sendKeys(locator_split("txtbiaffected"), getValue("BIAffected"));
			    sleep(2000);
			    clearWebEdit(locator_split("txtbidowntime"));
			    sleep(2000);
			    sendKeys(locator_split("txtbidowntime"), getValue("BIDownTimeValue"));
			    sleep(2000);
			    //if (driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).isSelected()== true ){
			    	
			    //}
			   // click(locator_split("checklecompleted"));
			// driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).click();
			  driver.findElement(By.xpath("//input[@id='LECompletedFlag']")).click();
			  //sendkeys(driver.findElement(By.xpath("//input[@name='USER']")),"jkdkd");
			    sleep(2000);
			    
/////////////////////////////PML flag
			    click(locator_split("tabpml"));
			    sleep(5000);
		
			  //  click(locator_split("linkexpandpdlossestimates"));
			    sleep(3000);
			    if (getValue("Radiobutton").contains("Value")){
			    	System.out.println("inside if loop");
			    	ClickRadiobutton(locator_split("radiovalue"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtbuildingdamage"));
			    	sleep(2000);
			    	System.out.println("inside pml tab");
			    	clearWebEdit(locator_split("txtbuildingdamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtbuildingdamage"), getValue("BuildingDamage1"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtmeDamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtmeDamage"), getValue("MEDamage1"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtcontentdamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtcontentdamage"), getValue("ContentDamage1"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtstockdamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtstockdamage"), getValue("StockDamage1"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtotherpddamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtotherpddamage"), getValue("OtherPDDamage1"));
			    	sleep(1000);
			    	
			    }else{
			    	ClickRadiobutton(locator_split("radiopercentage"));
			    	
			    	clearWebEdit(locator_split("txtbuildingdamagepc"));
			    	sleep(2000);
			    	
			    	clearWebEdit(locator_split("txtbuildingdamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtbuildingdamagepc"), getValue("BuildingDamagePC1"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtmedamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtmedamagepc"), getValue("MEDamagePC1"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtcontentdamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtcontentdamagepc"), getValue("ContentDamagePC1"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtstockdamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtstockdamagepc"), getValue("StockDamagePC1"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtotherpddmagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtotherpddmagepc"), getValue("OtherPDDamagePC1"));
			    	sleep(1000);
			    	
			    }
			    
			 //   click(locator_split("linkexpandbilossestimates"));
			    sleep(3000);
			    selectListValue(locator_split("listbidowntime"), getValue("BIDownTime1"));
			    sleep(2000);
			    clearWebEdit(locator_split("txtbiaffected"));
			    sleep(2000);
			    sendKeys(locator_split("txtbiaffected"), getValue("BIAffected1"));
			    sleep(2000);
			    clearWebEdit(locator_split("txtbidowntime"));
			    sleep(2000);
			    sendKeys(locator_split("txtbidowntime"), getValue("BIDownTimeValue1"));
			    sleep(2000);
			    //if (driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).isSelected()== true ){
			    	
			    //}
			  //  driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).click();
			    driver.findElement(By.xpath("//input[@id='LECompletedFlag']")).click();
			    sleep(2000);
			    //////////////////////////////////////////////
			    
			    
		    //////////NLE///////////////////////////
			    click(locator_split("tabnle"));
			    sleep(5000);
		
			 //   click(locator_split("linkexpandpdlossestimates"));
			    sleep(3000);
			    if (getValue("Radiobutton").contains("Value")){
			    	
			    	ClickRadiobutton(locator_split("radiovalue"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtbuildingdamage"));
			    	sleep(2000);
			    	clearWebEdit(locator_split("txtbuildingdamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtbuildingdamage"), getValue("BuildingDamage2"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtmeDamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtmeDamage"), getValue("MEDamage2"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtcontentdamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtcontentdamage"), getValue("ContentDamage2"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtstockdamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtstockdamage"), getValue("StockDamage2"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtotherpddamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtotherpddamage"), getValue("OtherPDDamage2"));
			    	sleep(1000);
			    	
			    }else{
			    	ClickRadiobutton(locator_split("radiopercentage1"));
			    	
			    	clearWebEdit(locator_split("txtbuildingdamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtbuildingdamagepc"), getValue("BuildingDamagePC2"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtmedamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtmedamagepc"), getValue("MEDamagePC2"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtcontentdamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtcontentdamagepc"), getValue("ContentDamagePC2"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtstockdamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtstockdamagepc"), getValue("StockDamagePC2"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtotherpddmagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtotherpddmagepc"), getValue("OtherPDDamagePC2"));
			    	sleep(1000);
			    	
			    }
			    
			   // click(locator_split("linkexpandbilossestimates"));
			    sleep(3000);
			    selectListValue(locator_split("listbidowntime"), getValue("BIDownTime2"));
			    sleep(2000);
			    clearWebEdit(locator_split("txtbiaffected"));
			    sleep(2000);
			    sendKeys(locator_split("txtbiaffected"), getValue("BIAffected2"));
			    sleep(2000);
			    clearWebEdit(locator_split("txtbidowntime"));
			    sleep(2000);
			    sendKeys(locator_split("txtbidowntime"), getValue("BIDownTimeValue2"));
			    sleep(2000);
			    //if (driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).isSelected()== true ){
			    	
			    //}
			  if (driver.findElement(By.xpath(".//*[@id='NLEScenarioComplete']")).isSelected()== true ){
				  driver.findElement(By.xpath("//*[@id='NLEScenarioComplete']")).click();
				    sleep(2000); 
			  }
			  driver.findElement(By.xpath("//*[@id='NLEScenarioComplete']")).click();
			    sleep(2000); 
			    //////////////////////////////////////////////////////
			   
			  click(locator_split("tabnleprotected"));
			  sleep(2000);
			  if (driver.findElement(By.xpath(".//*[@id='NLEScenarioComplete']")).isSelected()== true ){
				  driver.findElement(By.xpath("//*[@id='NLEScenarioComplete']")).click();
				  sleep(2000);
			  }
			  driver.findElement(By.xpath("//*[@id='NLEScenarioComplete']")).click();
			  sleep(2000);
			  
		click(locator_split("tabnleprotectednonstorage"));
			  sleep(2000);
			//  driver.findElement(By.xpath(".//*[@id='NLEScenarioComplete']")).click();
			sleep(2000);
			click(locator_split("tabnlesummary"));
		//	driver.findElement(By.xpath("tabnlesummary")).click();
			sleep(2000);
			  if (driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).isSelected()== true ){
				  driver.findElement(By.xpath("//input[@id='LECompletedFlag']")).click();
					sleep(2000);
			  }
			driver.findElement(By.xpath("//input[@id='LECompletedFlag']")).click();
			sleep(2000);
			  if (driver.findElement(By.xpath(".//*[@id='SectionCompleted']")).isSelected()== true ){
				  driver.findElement(By.xpath("//input[@id='SectionCompleted']")).click();
				  sleep(2000);
			  }
			driver.findElement(By.xpath("//input[@id='SectionCompleted']")).click();
			sleep(2000);
		//switchframe("PegaGadget2Ifr");
		//	sleep(3000);
		//clickbylinktext("No");
			
			}else{

			    driver.switchTo().defaultContent();
			    System.out.println("switched frame"+ switchframe("PegaGadget3Ifr"));
			    click(locator_split("tablossestimates"));
			    sleep(2000);
			    click(locator_split("tabmas"));
			    sleep(5000);
			    click(locator_split("linkexpandpdlossestimates"));
			    sleep(3000);
			    if (getValue("Radiobutton").contains("Value")){
			     	ClickRadiobutton(locator_split("radiovalue"));
			    	
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtbuildingdamage"));
			    	sleep(3000);
			    	clearWebEdit(locator_split("txtbuildingdamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtbuildingdamage"), getValue("BuildingDamageMas"));
			    	sleep(1000);
			    	
			    	clearWebEdit(locator_split("txtmeDamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtmeDamage"), getValue("MEDamageMas"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtcontentdamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtcontentdamage"), getValue("ContentDamageMas"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtstockdamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtstockdamage"), getValue("StockDamageMas"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtotherpddamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtotherpddamage"), getValue("OtherPDDamageMas"));
			    	sleep(1000);
			    	
			    }else{
			    	ClickRadiobutton(locator_split("radiopercentage"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtbuildingdamagepc"));
			    	sleep(3000);
			    	clearWebEdit(locator_split("txtbuildingdamagepc"));
			    	sleep(2000);
			    	
			    	sendKeys(locator_split("txtbuildingdamagepc"), getValue("BuildingDamagePCMas"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtbuildingdamagepc"));
			    	sleep(2000);
			    	
			    	sendKeys(locator_split("txtbuildingdamagepc"), getValue("BuildingDamagePCMas"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtmedamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtmedamagepc"), getValue("MEDamagePCMas"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtcontentdamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtcontentdamagepc"), getValue("ContentDamagePCMas"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtstockdamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtstockdamagepc"), getValue("StockDamagePCMas"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtotherpddmagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtotherpddmagepc"), getValue("OtherPDDamagePCMas"));
			    	sleep(1000);
			    	
			    }
			    
			    click(locator_split("linkexpandbilossestimates"));
			    sleep(3000);
			 //   click(locator_split("linkaddroweml"));
			 //   sleep(3000);
			   // click(locator_split("linkaddroweml"));
			//    sleep(3000);
			    
			    selectListValue(locator_split("listbidowntime"), getValue("BIDownTimeMas"));
			    sleep(2000);
			    clearWebEdit(locator_split("txtbiaffected"));
			    sleep(2000);
			    sendKeys(locator_split("txtbiaffected"), getValue("BIAffectedMas"));
			    sleep(2000);
			    clearWebEdit(locator_split("txtbidowntime"));
			    sleep(2000);
			    sendKeys(locator_split("txtbidowntime"), getValue("BIDownTimeValueMas"));
			    sleep(2000);
			    click(locator_split("linkexpandothertecobverage"));
			    sleep(2000);
			    if (getValue("Radiobutton").contains("Value")){
			     	ClickRadiobutton(locator_split("radiovalue2"));
			     	sleep(2000);
			     	clearWebEdit(locator_split("txteeloss"));
				    sleep(2000);
				    sendKeys(locator_split("txteeloss"), getValue("EELoss1"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtothertecoverageloss"));
				    sleep(2000);
				    sendKeys(locator_split("txtothertecoverageloss"), getValue("OtherTE1CoverageLoss1"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtothertecoverage2loss"));
				    sleep(2000);
				    sendKeys(locator_split("txtothertecoverage2loss"), getValue("OtherTE2CoverageLoss1"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtothertecoverage3loss"));
				    sleep(2000);
				    sendKeys(locator_split("txtothertecoverage3loss"), getValue("OtherTE3CoverageLoss1"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtibiloss"));
				    sleep(2000);
				    sendKeys(locator_split("txtibiloss"), getValue("IBILoss1"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtcbiloss"));
				    sleep(2000);
				    sendKeys(locator_split("txtcbiloss"), getValue("CBILoss1"));
				    
			    //if (driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).isSelected()== true ){
			    	
			    //}
			   // click(locator_split("checklecompleted"));
			// driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).click();
			     	
			    }else{
			    	
			    	ClickRadiobutton(locator_split("radiopercentage2"));
			     	sleep(2000);
			     	clearWebEdit(locator_split("txteelosspc"));
				    sleep(2000);
				    sendKeys(locator_split("txteelosspc"), getValue("EELoss1PC"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtothertecoveragelosspc"));
				    sleep(2000);
				    sendKeys(locator_split("txtothertecoveragelosspc"), getValue("OtherTE1CoverageLoss1PC"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtothertecoverage2losspc"));
				    sleep(2000);
				    sendKeys(locator_split("txtothertecoverage2losspc"), getValue("OtherTE2CoverageLoss1PC"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtothertecoverage3losspc"));
				    sleep(2000);
				    sendKeys(locator_split("txtothertecoverage3losspc"), getValue("OtherTE3CoverageLoss1PC"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtibilosspc"));
				    sleep(2000);
				    sendKeys(locator_split("txtibilosspc"), getValue("IBILoss1PC"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtcbilosspc"));
				    sleep(2000);
				    sendKeys(locator_split("txtcbilosspc"), getValue("CBILoss1PC"));
			    }
			    if (driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).isSelected()== true ){
					  driver.findElement(By.xpath("//input[@id='LECompletedFlag']")).click();
						sleep(2000);
				  }
				driver.findElement(By.xpath("//input[@id='LECompletedFlag']")).click();
				sleep(2000);
			//  driver.findElement(By.xpath("//input[@id='LECompletedFlag']")).click();
			  //sendkeys(driver.findElement(By.xpath("//input[@name='USER']")),"jkdkd");
			 //   sleep(2000);
			    
			    
			    
			    
			    
			    
			    /////////////MFL/////////////////////
			  
			    click(locator_split("tabmfl"));
			    sleep(5000);
			   // click(locator_split("linkexpandpdlossestimates"));
			    sleep(3000);
			    if (getValue("Radiobutton").contains("Value")){
			     	ClickRadiobutton(locator_split("radiovalue"));
			     	sleep(2000);
			    	System.out.println("After clicking Radio button");
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtbuildingdamage"));
			    	sleep(3000);
			    	clearWebEdit(locator_split("txtbuildingdamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtbuildingdamage"), getValue("BuildingDamageMfl1"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtmeDamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtmeDamage"), getValue("MEDamageMfl1"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtcontentdamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtcontentdamage"), getValue("ContentDamageMfl1"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtstockdamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtstockdamage"), getValue("StockDamageMfl1"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtotherpddamage"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtotherpddamage"), getValue("OtherPDDamageMfl1"));
			    	sleep(1000);
			    	
			    }else{
			    	ClickRadiobutton(locator_split("radiopercentage"));
			    	sleep(2000);
			    	clearWebEdit(locator_split("txtbuildingdamagepc"));
			    	sleep(2000);
			    	clearWebEdit(locator_split("txtbuildingdamagepc"));
			    	sleep(2000);
			    	clearWebEdit(locator_split("txtbuildingdamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtbuildingdamagepc"), getValue("BuildingDamageMflPC1"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtmedamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtmedamagepc"), getValue("MEDamageMflPC1"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtcontentdamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtcontentdamagepc"), getValue("ContentDamageMflPC1"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtstockdamagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtstockdamagepc"), getValue("StockDamageMflPC1"));
			    	sleep(1000);
			    	clearWebEdit(locator_split("txtotherpddmagepc"));
			    	sleep(2000);
			    	sendKeys(locator_split("txtotherpddmagepc"), getValue("OtherPDDamageMflPC1"));
			    	sleep(1000);
			    	
			    }
			    
			  //  click(locator_split("linkexpandbilossestimates"));
			    sleep(3000);
			  /*  click(locator_split("linkaddroweml"));
			    sleep(3000);
			   click(locator_split("linkaddroweml"));
			    sleep(3000);*/
			    
			    selectListValue(locator_split("listbidowntime"), getValue("BIDownTimeMfl1"));
			    sleep(2000);
			    clearWebEdit(locator_split("txtbiaffected"));
			    sleep(2000);
			    sendKeys(locator_split("txtbiaffected"), getValue("BIAffectedMfl1"));
			    sleep(5000);
			    clearWebEdit(locator_split("txtbidowntime"));
			    sleep(2000);
			    sendKeys(locator_split("txtbidowntime"), getValue("BIDownTimeValueMfl1"));
			    sleep(2000);
			  //  click(locator_split("linkexpandothertecobverage"));
			    sleep(2000);
			    if (getValue("Radiobutton").contains("Value")){
			     	ClickRadiobutton(locator_split("radiovaluemfs"));
			     	sleep(2000);
			     	clearWebEdit(locator_split("txteeloss"));
				    sleep(2000);
				    sendKeys(locator_split("txteeloss"), getValue("EELossMfl"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtothertecoverageloss"));
				    sleep(2000);
				    sendKeys(locator_split("txtothertecoverageloss"), getValue("OtherTE1CoverageLossMfl"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtothertecoverage2loss"));
				    sleep(2000);
				    sendKeys(locator_split("txtothertecoverage2loss"), getValue("OtherTE2CoverageLossMfl"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtothertecoverage3loss"));
				    sleep(2000);
				    sendKeys(locator_split("txtothertecoverage3loss"), getValue("OtherTE3CoverageLossMfl"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtibiloss"));
				    sleep(2000);
				    sendKeys(locator_split("txtibiloss"), getValue("IBILossMfl"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtcbiloss"));
				    sleep(2000);
				    sendKeys(locator_split("txtcbiloss"), getValue("CBILossMfl"));
				    
			    //if (driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).isSelected()== true ){
			    	
			    //}
			   // click(locator_split("checklecompleted"));
			// driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).click();
			     	
			    }else{
			    	
			    	ClickRadiobutton(locator_split("radiopercentagemfl"));
			     	sleep(2000);
			     	clearWebEdit(locator_split("txteelosspc"));
				    sleep(2000);
				    sendKeys(locator_split("txteelosspc"), getValue("EELossMflPC"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtothertecoveragelosspc"));
				    sleep(2000);
				    sendKeys(locator_split("txtothertecoveragelosspc"), getValue("OtherTE1CoverageLossMflPC"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtothertecoverage2losspc"));
				    sleep(2000);
				    sendKeys(locator_split("txtothertecoverage2losspc"), getValue("OtherTE2CoverageLossMflPC"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtothertecoverage3losspc"));
				    sleep(2000);
				    sendKeys(locator_split("txtothertecoverage3losspc"), getValue("OtherTE3CoverageLossMflPC"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtibilosspc"));
				    sleep(2000);
				    sendKeys(locator_split("txtibilosspc"), getValue("IBILossMflPC"));
				    sleep(2000);
			     	clearWebEdit(locator_split("txtcbilosspc"));
				    sleep(2000);
				    sendKeys(locator_split("txtcbilosspc"), getValue("CBILossMfPC"));
			    }
			    if (driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).isSelected()== true ){
					  driver.findElement(By.xpath("//input[@id='LECompletedFlag']")).click();
						sleep(2000);
				  }
				driver.findElement(By.xpath("//input[@id='LECompletedFlag']")).click();
				sleep(2000);
			 // driver.findElement(By.xpath("//input[@id='LECompletedFlag']")).click();
			  //sendkeys(driver.findElement(By.xpath("//input[@name='USER']")),"jkdkd");
			 //   sleep(2000);
			    ///////////////MFL/////////////
	/////////////PML/////////////////////
				  
				    click(locator_split("tabpml"));
				    sleep(5000);
				   // click(locator_split("linkexpandpdlossestimates"));
				    sleep(3000);
				    if (getValue("Radiobutton").contains("Value")){
				     	ClickRadiobutton(locator_split("radiovalue"));
				     	sleep(2000);
				    	System.out.println("After clicking Radio button");
				    	sleep(1000);
				    	clearWebEdit(locator_split("txtbuildingdamage"));
				    	sleep(3000);
				    	clearWebEdit(locator_split("txtbuildingdamage"));
				    	sleep(2000);
				    	sendKeys(locator_split("txtbuildingdamage"), getValue("BuildingDamageMfl1"));
				    	sleep(1000);
				    	clearWebEdit(locator_split("txtmeDamage"));
				    	sleep(2000);
				    	sendKeys(locator_split("txtmeDamage"), getValue("MEDamageMfl1"));
				    	sleep(1000);
				    	clearWebEdit(locator_split("txtcontentdamage"));
				    	sleep(2000);
				    	sendKeys(locator_split("txtcontentdamage"), getValue("ContentDamageMfl1"));
				    	sleep(1000);
				    	clearWebEdit(locator_split("txtstockdamage"));
				    	sleep(2000);
				    	sendKeys(locator_split("txtstockdamage"), getValue("StockDamageMfl1"));
				    	sleep(1000);
				    	clearWebEdit(locator_split("txtotherpddamage"));
				    	sleep(2000);
				    	sendKeys(locator_split("txtotherpddamage"), getValue("OtherPDDamageMfl1"));
				    	sleep(1000);
				    	
				    }else{
				    	ClickRadiobutton(locator_split("radiopercentage"));
				    	sleep(2000);
				    	clearWebEdit(locator_split("txtbuildingdamagepc"));
				    	sleep(2000);
				    	clearWebEdit(locator_split("txtbuildingdamagepc"));
				    	sleep(2000);
				    	clearWebEdit(locator_split("txtbuildingdamagepc"));
				    	sleep(2000);
				    	sendKeys(locator_split("txtbuildingdamagepc"), getValue("BuildingDamageMflPC1"));
				    	sleep(1000);
				    	clearWebEdit(locator_split("txtmedamagepc"));
				    	sleep(2000);
				    	sendKeys(locator_split("txtmedamagepc"), getValue("MEDamageMflPC1"));
				    	sleep(1000);
				    	clearWebEdit(locator_split("txtcontentdamagepc"));
				    	sleep(2000);
				    	sendKeys(locator_split("txtcontentdamagepc"), getValue("ContentDamageMflPC1"));
				    	sleep(1000);
				    	clearWebEdit(locator_split("txtstockdamagepc"));
				    	sleep(2000);
				    	sendKeys(locator_split("txtstockdamagepc"), getValue("StockDamageMflPC1"));
				    	sleep(1000);
				    	clearWebEdit(locator_split("txtotherpddmagepc"));
				    	sleep(2000);
				    	sendKeys(locator_split("txtotherpddmagepc"), getValue("OtherPDDamageMflPC1"));
				    	sleep(1000);
				    	
				    }
				    
				  //  click(locator_split("linkexpandbilossestimates"));
				 /*   sleep(3000);
				    click(locator_split("linkaddroweml"));
				    sleep(3000);
				   click(locator_split("linkaddroweml"));
				    sleep(3000);*/
				    sleep(3000);
				    selectListValue(locator_split("listbidowntime"), getValue("BIDownTimeMfl1"));
				    sleep(2000);
				    clearWebEdit(locator_split("txtbiaffected"));
				    sleep(2000);
				    sendKeys(locator_split("txtbiaffected"), getValue("BIAffectedMfl1"));
				    sleep(5000);
				    clearWebEdit(locator_split("txtbidowntime"));
				    sleep(2000);
				    sendKeys(locator_split("txtbidowntime"), getValue("BIDownTimeValueMfl1"));
				    sleep(2000);
				  //  click(locator_split("linkexpandothertecobverage"));
				    sleep(2000);
				    if (getValue("Radiobutton").contains("Value")){
				    	System.out.println("Inside If Loop");
				     	//ClickRadiobutton(locator_split("radiovaluepml"));
				     	sleep(5000);
				     	clearWebEdit(locator_split("txteeloss"));
					    sleep(2000);
					    sendKeys(locator_split("txteeloss"), getValue("EELossMfl"));
					    sleep(2000);
				     	clearWebEdit(locator_split("txtothertecoverageloss"));
					    sleep(2000);
					    sendKeys(locator_split("txtothertecoverageloss"), getValue("OtherTE1CoverageLossMfl"));
					    sleep(2000);
				     	clearWebEdit(locator_split("txtothertecoverage2loss"));
					    sleep(3000);
					    sendKeys(locator_split("txtothertecoverage2loss"), getValue("OtherTE2CoverageLossMfl"));
					    sleep(3000);
				     	clearWebEdit(locator_split("txtothertecoverage3loss"));
					    sleep(2000);
					    sendKeys(locator_split("txtothertecoverage3loss"), getValue("OtherTE3CoverageLossMfl"));
					    sleep(2000);
				     	clearWebEdit(locator_split("txtibiloss"));
					    sleep(2000);
					    sendKeys(locator_split("txtibiloss"), getValue("IBILossPML"));
					    sleep(2000);
				     	clearWebEdit(locator_split("txtcbiloss"));
					    sleep(2000);
					    sendKeys(locator_split("txtcbiloss"), getValue("CBILossPML"));
					    
				    //if (driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).isSelected()== true ){
				    	
				    //}
				   // click(locator_split("checklecompleted"));
				// driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).click();
				     	
				    }else{
				    	
				    	ClickRadiobutton(locator_split("radiopercentage3"));
				     	sleep(2000);
				     	clearWebEdit(locator_split("txteelosspc"));
					    sleep(2000);
					    sendKeys(locator_split("txteelosspc"), getValue("EELossPML1"));
					    sleep(2000);
				     	clearWebEdit(locator_split("txtothertecoveragelosspc"));
					    sleep(2000);
					    sendKeys(locator_split("txtothertecoveragelosspc"), getValue("OtherTE1CoverageLossPMLPC"));
					    sleep(2000);
				     	clearWebEdit(locator_split("txtothertecoverage2losspc"));
					    sleep(2000);
					    sendKeys(locator_split("txtothertecoverage2losspc"), getValue("OtherTE2CoverageLossPMLPC"));
					    sleep(2000);
				     	clearWebEdit(locator_split("txtothertecoverage3losspc"));
					    sleep(2000);
					    sendKeys(locator_split("txtothertecoverage3losspc"), getValue("OtherTE3CoverageLossPMLPC"));
					    sleep(2000);
				     	clearWebEdit(locator_split("txtibilosspc"));
					    sleep(2000);
					    sendKeys(locator_split("txtibilosspc"), getValue("IBILossPMLPC"));
					    sleep(2000);
				     	clearWebEdit(locator_split("txtcbilosspc"));
					    sleep(2000);
					    sendKeys(locator_split("txtcbilosspc"), getValue("CBILossPMLPC"));
					    sleep(2000);
				    }
				    if (driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).isSelected()== true ){
						  driver.findElement(By.xpath("//input[@id='LECompletedFlag']")).click();
							sleep(2000);
					  }
					driver.findElement(By.xpath("//input[@id='LECompletedFlag']")).click();
					sleep(2000);
				 // driver.findElement(By.xpath("//input[@id='LECompletedFlag']")).click();
				  //sendkeys(driver.findElement(By.xpath("//input[@name='USER']")),"jkdkd");
				   // sleep(2000);
				    ///////////////PML/////////////
				    //////////////NLE Manual////////////////
	
					  
					    click(locator_split("tabnlemanual"));
					    sleep(5000);
					   // click(locator_split("linkexpandpdlossestimates"));
					    sleep(3000);
					    if (getValue("Radiobutton").contains("Value")){
					     	ClickRadiobutton(locator_split("radiovalue"));
					     	sleep(2000);
					    	System.out.println("After clicking Radio button");
					    	sleep(1000);
					    	clearWebEdit(locator_split("txtbuildingdamage"));
					    	sleep(3000);
					    	clearWebEdit(locator_split("txtbuildingdamage"));
					    	sleep(2000);
					    	sendKeys(locator_split("txtbuildingdamage"), getValue("BuildingDamageNLE1"));
					    	sleep(1000);
					    	clearWebEdit(locator_split("txtmeDamage"));
					    	sleep(2000);
					    	sendKeys(locator_split("txtmeDamage"), getValue("MEDamageNLE1"));
					    	sleep(1000);
					    	clearWebEdit(locator_split("txtcontentdamage"));
					    	sleep(2000);
					    	sendKeys(locator_split("txtcontentdamage"), getValue("ContentDamageNLE1"));
					    	sleep(1000);
					    	clearWebEdit(locator_split("txtstockdamage"));
					    	sleep(2000);
					    	sendKeys(locator_split("txtstockdamage"), getValue("StockDamageNLE1"));
					    	sleep(1000);
					    	clearWebEdit(locator_split("txtotherpddamage"));
					    	sleep(2000);
					    	sendKeys(locator_split("txtotherpddamage"), getValue("OtherPDDamageNLE1"));
					    	sleep(1000);
					    	
					    }else{
					    	ClickRadiobutton(locator_split("radiopercentage"));
					    	sleep(2000);
					    	clearWebEdit(locator_split("txtbuildingdamagepc"));
					    	sleep(2000);
					    	clearWebEdit(locator_split("txtbuildingdamagepc"));
					    	sleep(2000);
					    	clearWebEdit(locator_split("txtbuildingdamagepc"));
					    	sleep(2000);
					    	sendKeys(locator_split("txtbuildingdamagepc"), getValue("BuildingDamageNLEPC1"));
					    	sleep(1000);
					    	clearWebEdit(locator_split("txtmedamagepc"));
					    	sleep(2000);
					    	sendKeys(locator_split("txtmedamagepc"), getValue("MEDamageNLEPC1"));
					    	sleep(1000);
					    	clearWebEdit(locator_split("txtcontentdamagepc"));
					    	sleep(2000);
					    	sendKeys(locator_split("txtcontentdamagepc"), getValue("ContentDamageNLEPC1"));
					    	sleep(1000);
					    	clearWebEdit(locator_split("txtstockdamagepc"));
					    	sleep(2000);
					    	sendKeys(locator_split("txtstockdamagepc"), getValue("StockDamageNLEPC1"));
					    	sleep(1000);
					    	clearWebEdit(locator_split("txtotherpddmagepc"));
					    	sleep(2000);
					    	sendKeys(locator_split("txtotherpddmagepc"), getValue("OtherPDDamageNLEPC1"));
					    	sleep(1000);
					    	
					    }
					    
					  //  click(locator_split("linkexpandbilossestimates"));
					 /*   sleep(3000);
					    click(locator_split("linkaddroweml"));
					    sleep(3000);
					   click(locator_split("linkaddroweml"));
					    sleep(3000);*/
					    sleep(3000);
					    selectListValue(locator_split("listbidowntime"), getValue("BIDownTimeNLE1"));
					    sleep(2000);
					    clearWebEdit(locator_split("txtbiaffected"));
					    sleep(2000);
					    sendKeys(locator_split("txtbiaffected"), getValue("BIAffectedNLE1"));
					    sleep(5000);
					    clearWebEdit(locator_split("txtbidowntime"));
					    sleep(2000);
					    sendKeys(locator_split("txtbidowntime"), getValue("BIDownTimeValueNLE1"));
					    sleep(2000);
					  //  click(locator_split("linkexpandothertecobverage"));
					    sleep(2000);
					    if (getValue("Radiobutton").contains("Value")){
					    	System.out.println("Inside If Loop");
					     	//ClickRadiobutton(locator_split("radiovaluepml"));
					     	sleep(5000);
					     	clearWebEdit(locator_split("txteeloss"));
						    sleep(2000);
						    sendKeys(locator_split("txteeloss"), getValue("EELossNLE"));
						    sleep(2000);
					     	clearWebEdit(locator_split("txtothertecoverageloss"));
						    sleep(2000);
						    sendKeys(locator_split("txtothertecoverageloss"), getValue("OtherTE1CoverageLossNLE"));
						    sleep(2000);
					     	clearWebEdit(locator_split("txtothertecoverage2loss"));
						    sleep(3000);
						    sendKeys(locator_split("txtothertecoverage2loss"), getValue("OtherTE2CoverageLossNLE"));
						    sleep(3000);
					     	clearWebEdit(locator_split("txtothertecoverage3loss"));
						    sleep(2000);
						    sendKeys(locator_split("txtothertecoverage3loss"), getValue("OtherTE3CoverageLossNLE"));
						    sleep(2000);
					     	clearWebEdit(locator_split("txtibiloss"));
						    sleep(2000);
						    sendKeys(locator_split("txtibiloss"), getValue("IBILossNLE"));
						    sleep(2000);
					     	clearWebEdit(locator_split("txtcbiloss"));
						    sleep(2000);
						    sendKeys(locator_split("txtcbiloss"), getValue("CBILossNLE"));
						    
					    //if (driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).isSelected()== true ){
					    	
					    //}
					   // click(locator_split("checklecompleted"));
					// driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).click();
					     	
					    }else{
					    	
					    	ClickRadiobutton(locator_split("radiopercentagenlemanual"));
					     	sleep(2000);
					     	clearWebEdit(locator_split("txteelosspc"));
						    sleep(2000);
						    sendKeys(locator_split("txteelosspc"), getValue("EELossNLEPC"));
						    sleep(2000);
					     	clearWebEdit(locator_split("txtothertecoveragelosspc"));
						    sleep(2000);
						    sendKeys(locator_split("txtothertecoveragelosspc"), getValue("OtherTE1CoverageLossNLEPC"));
						    sleep(2000);
					     	clearWebEdit(locator_split("txtothertecoverage2losspc"));
						    sleep(2000);
						    sendKeys(locator_split("txtothertecoverage2losspc"), getValue("OtherTE2CoverageLossNLEPC"));
						    sleep(2000);
					     	clearWebEdit(locator_split("txtothertecoverage3losspc"));
						    sleep(2000);
						    sendKeys(locator_split("txtothertecoverage3losspc"), getValue("OtherTE3CoverageLossNLEPC"));
						    sleep(2000);
					     	clearWebEdit(locator_split("txtibilosspc"));
						    sleep(2000);
						    sendKeys(locator_split("txtibilosspc"), getValue("IBILossNLEPC"));
						    sleep(2000);
					     	clearWebEdit(locator_split("txtcbilosspc"));
						    sleep(2000);
						    sendKeys(locator_split("txtcbilosspc"), getValue("CBILossNLEPC"));
						    sleep(2000);
					    }
					 // driver.findElement(By.xpath("//input[@id='LECompletedFlag']")).click();
					  if (driver.findElement(By.xpath(".//*[@id='NLEScenarioComplete']")).isSelected()== true ){
						  driver.findElement(By.xpath("//*[@id='NLEScenarioComplete']")).click();
						    sleep(2000); 
					  }
					  driver.findElement(By.xpath("//*[@id='NLEScenarioComplete']")).click();
					    sleep(2000); 
					  //sendkeys(driver.findElement(By.xpath("//input[@name='USER']")),"jkdkd");
					    sleep(2000);
					 
				    /////////////NLE Manual/////////////
					    ////////////////////Remaining tabs/////////////
					    
						
						   
						  click(locator_split("tabnleprotected"));
						  sleep(2000);
						  if (driver.findElement(By.xpath(".//*[@id='NLEScenarioComplete']")).isSelected()== true ){
							  driver.findElement(By.xpath("//*[@id='NLEScenarioComplete']")).click();
							  sleep(2000);
						  }
						  driver.findElement(By.xpath("//*[@id='NLEScenarioComplete']")).click();
						  sleep(2000);
						  
					click(locator_split("tabnleprotectednonstorage"));
						  sleep(2000);
						  if (driver.findElement(By.xpath(".//*[@id='NLEScenarioComplete']")).isSelected()== true ){
							  driver.findElement(By.xpath("//*[@id='NLEScenarioComplete']")).click();
							  sleep(2000);
						  }
						  driver.findElement(By.xpath("//*[@id='NLEScenarioComplete']")).click();
						  sleep(2000);
						  
						//  driver.findElement(By.xpath(".//*[@id='NLEScenarioComplete']")).click();
						sleep(2000);
						click(locator_split("tabnlesummary"));
					//	driver.findElement(By.xpath("tabnlesummary")).click();
						sleep(2000);
						  if (driver.findElement(By.xpath(".//*[@id='LECompletedFlag']")).isSelected()== true ){
							  driver.findElement(By.xpath("//input[@id='LECompletedFlag']")).click();
								sleep(2000);
						  }
						driver.findElement(By.xpath("//input[@id='LECompletedFlag']")).click();
						sleep(2000);
						  if (driver.findElement(By.xpath(".//*[@id='SectionCompleted']")).isSelected()== true ){
							  driver.findElement(By.xpath("//input[@id='SectionCompleted']")).click();
							  sleep(2000);
						  }
						driver.findElement(By.xpath("//input[@id='SectionCompleted']")).click();
						sleep(2000);
					    ///////////////Remaining tabs///////////////
					    
					    
			}
////////////
	
			Reporter.log("PASS_MESSAGE:- Report tab  is clicked and user is logged in");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Report tab is not clicked in");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("LoginLogout")
					+ " not found");

		}

	}
	/* Method Name : --- ----- --- AddAccount
	 * Purpose     : --- ----- --- To Login from home page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Krishnamurthy K
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void AddAccount(String subtab){
		//String username = getValue(Email);
	//	String password = getValue(Password);

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account should be clicked and user should get logged in");
		try{
			sleep(3000);
			click(locator_split("Maintenencetab"));
			sleep(3000);
			switchframe("PegaGadget1Ifr");
			sleep(2000);
			click(locator_split(subtab));
			sleep(3000);
			sendKeys(locator_split("txtAccountName"), getValue("Accountname"));
			//selectList(locator_split("LstLineofBusiness"),1);
			selectListValue(locator_split("LstLineofBusiness"), "ENERGY AND ENGINEERED RISK");
			sleep(3000);
			sendKeys(locator_split("lstOccupancyType"), getValue("Occupancytype"));
			sendKeys(locator_split("txtlocationselect"), getValue("BusinessAs"));
			sendKeys(locator_split("LstaccountunderWriter"), getValue("Underwriter"));
			sleep(2000);
			sendKeys(locator_split("LstunderwriterRegion"), getValue("UnderwriterRegion"));
			sleep(2000);

			sendKeys(locator_split("LstUnderWriterCountry"), getValue("UnderWriterCountry"));
			sleep(2000);

			sendKeys(locator_split("LstUnderWriterBranch"), getValue("UnderWriterBranch"));
			sleep(2000);

			sendKeys(locator_split("LstAccountEngineerId"), getValue("AccountEngineer"));
			sleep(2000);

			clearWebEdit(locator_split("LstAccountCurrency"));
			sleep(2000);
			sendKeys(locator_split("LstAccountCurrency"), getValue("Accountcurrency"));
			
			sleep(2000);
			sendKeys(locator_split("txtPolicyInceptionDate"), getValue("PolicyInceptiondate"));
			sleep(2000);
			sendKeys(locator_split("txtpolicyExpirationDate"), getValue("PolicyExpirydate"));
			sendKeys(locator_split("txtAccountPD"), getValue("AccountPD"));
			sleep(3000);
			sendKeys(locator_split("txtAccountBI"), getValue("AccountBI"));
			sleep(3000);
			sendKeys(locator_split("txtAccBI"), getValue("AccountBI"));
			sleep(5000);
			sendKeys(locator_split("txtpolicyLimit"), getValue("PolicyLimit"));
			sleep(5000);
			sendKeys(locator_split("txtPolicyNumber"), getValue("PolicyNumber"));
			sleep(2000);
			sendKeys(locator_split("txtpolicyLimit"), getValue("PolicyLimit"));
			sleep(5000);
			sendKeys(locator_split("LstPolicyStatus"), getValue("Policystatus"));
			//click(locator_split("txtlocationselect"));
			sleep(2000);
			//click(locator_split("Lstlocationselect"));
			
			click(locator_split("btnAddAccount"));
			sleep(3000);
		//	click(locator_split("btnAddLocations"));
		/*	clearWebEdit(locator_split("txtLoginNamegrasp"));
			//sendKeys(locator_split("txtLoginNamegrasp"), username);
			//sendKeys(locator_split("txtpasswordgrasp"), password);
			
			sleep(3000);
			click(locator_split("btn_privacyok"));*/
			sleep(5000);
			
			
			System.out.println(getText(locator_split("txtAccountID")));
			sleep(5000);

			Reporter.log("PASS_MESSAGE:- Report tab  is clicked and user is logged in");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Report tab is not clicked in");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("LoginLogout")
					+ " not found");

		}

	}
	/* Method Name : --- ----- --- LoginHomePage
	 * Purpose     : --- ----- --- To Login from home page
	 * Parameter   : --- ----- --- 
	 * Date Created: --- ----- --- 
	 * Created By  : --- ----- ---  Krishnamurthy K
	 * Modified By : --- ----- ---
	 * Modified Date:--- ----- ---
	 */

	public void AddSingleLocation(){
		

		class Local {};
		Reporter.log("TestStepComponent"+Local.class.getEnclosingMethod().getName());
		Reporter.log("TestStepInput:-"+"NA");
		Reporter.log("TestStepOutput:-"+"NA");
		Reporter.log("TestStepExpectedResult:- My Account should be clicked and user should get logged in");
		try{
			sleep(3000);
			
			click(locator_split("btnAddSingleLocation"));
		
			sendKeys(locator_split("txtlocationname"), getValue("Locationname"));
			sendKeys(locator_split("txtlocationCurrency"), getValue("LocationCurrency"));
			sendKeys(locator_split("txtCityname"), getValue("CityName"));
			sendKeys(locator_split("txtcountryname"), getValue("Country"));
			sendKeys(locator_split("txtstate"), getValue("State"));
			sleep(3000);
			sendKeys(locator_split("txtbuildingvalue"), getValue("BuildingValueCurrency"));
			sendKeys(locator_split("txtmandevalue"), getValue("MandEvaluecurrency"));
			sendKeys(locator_split("txtcontentvalue"), getValue("ContentvalueCurrency"));
			sendKeys(locator_split("txtotherpd"), getValue("Stockvaluecurrency"));
			sendKeys(locator_split("txtstockvalue"), getValue("OtherPDcurrency"));
			sendKeys(locator_split("txtBIValue"), getValue("BIValueCurrency"));
			sendKeys(locator_split("txtindemnityperiod"), getValue("BIIndemnityperiod"));
		
			click(locator_split("btnSaveLocation"));
			sleep(5000);
			Reporter.log("PASS_MESSAGE:- Report tab  is clicked and user is logged in");
		}
		catch(Exception e){
			Reporter.log("FAIL_MESSAGE:- Report tab is not clicked in");
			throw new NoSuchElementException("The element with"
					+ elementProperties.getProperty("LoginLogout")
					+ " not found");

		}

	}
}

