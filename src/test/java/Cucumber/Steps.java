/**
 * 
 */
package Cucumber;

import Driver.Driver_commonObjects;
import exceptions.DataSheetException;
import exceptions.InvalidBrowserException;











import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.thoughtworks.selenium.webdriven.commands.GetValue;

import Pages.LoginPage;
import Utilities.BrowserFactory;
import Utilities.Constant;
import Utilities.Excel;
import Utilities.ExcelUtils;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import Driver.Driver_commonObjects;
/**
 * @author 
 *
 */
public class Steps extends  Driver_commonObjects {



WebDriver driver;
public String username;
public String password;

@Given("^Grasp URL is given$")
public void Grasp_URL_is_given() throws Throwable {
	
	/*ProfilesIni profile = new ProfilesIni(); 
	FirefoxProfile myprofile = profile.getProfile("default");		 
	driver = new FirefoxDriver(myprofile);
	
	//driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
 driver.get("http://www.guru99.com");
    
 WebDriver driver=BrowserFactory.StartBrowser("firefox", "https://tuchwsmw0301.r1-core.r1.aig.net:20400/prweb/GRASPExt");*/
	
	//driver = new FirefoxDriver();
    //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
   // driver.get("http://www.google.com");
	//TestExecution("C:\\Users\\karthickv\\workspace\\MavenCucumber\\src\\test\\resources\\Location.xls");
	startWebDriverClient("Firefox");
	//openUrl();
	//System.out.println("username"+getValue("EmailId"));
	  ExcelUtils.setExcelFile(Constant.Path_TestData + Constant.File_TestData,"USA");
//	WebDriver driver=BrowserFactory.StartBrowser("firefox", "https://tuchwsmw0301.r1-core.r1.aig.net:20400/prweb/GRASPExt");
	 // driver.get("https://tuchwsmw0301.r1-core.r1.aig.net:20400/prweb/GRASPExt");
	//LoginPage login=PageFactory.initElements(driver,LoginPage.class);
	
	 username=ExcelUtils.getCellData(8, 1);
	 password=ExcelUtils.getCellData(9, 1);
	
	
//login.login_credentials(username,password);
   System.out.println("executed the  given step"+username);
}

@When("^Enter User Name and Password$")
public void LoginCredentials() throws Throwable {

	LoginHomePage(username, password);	

}
@When("^Search the RFS$")
public void Search_RFS() throws Throwable {

	//ExcelUtils.setExcelFile(Constant.Path_TestData + Constant.File_TestData,"USA");
	//String x=ExcelUtils.getCellData(8, 1);
	String RFSID=ExcelRetrieve(6, 1);
	System.out.println("RFS ID"+RFSID);
	SearchRFS(RFSID);
	
	

}


@Then("^Grasp Home page is displayed$")
public void Grasp_Home_page() throws Throwable {
	
	System.out.println("then part");
	VerifyHomepage();
}

@Then("^RFS is displayed$")
public void RFS_Then() throws Throwable {
	
	System.out.println("then part");
	
}

}
