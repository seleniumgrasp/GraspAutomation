/**
 * 
 */
package Utilities;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.SkipException;

/**
 * @author KrishnamurthyK
 *
 */

public class BrowserFactory {
	
	static WebDriver driver;
	public static WebDriver StartBrowser(String browsername,String url){
		if(browsername.equalsIgnoreCase("firefox")){
			driver=new FirefoxDriver();
			
		}
		else if(browsername.equalsIgnoreCase("chrome")){
			driver=new ChromeDriver();
		}
		driver.manage().window().maximize();
		driver.get(url);
		return driver;
		
	}
	
	

}
