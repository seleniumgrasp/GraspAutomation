/**
	 *@BaseComponentName: WebDriverFactory
	 *@Description:  
	 *@author: 
	 *@CreatedDate:
	 *@ModifiedBy:
	 *@ModifiedDate:
     *@param: 
     *@return:
     */
package WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import Utilities.Log;


public class WebDriverFactory {
	
	/**
	 *@MethodName: getWebdriver
	 *@Description:This Function opens the browser based on type of browser
	 *@author: Chakradhar K
	 *@CreatedDate: June 15, 2015
	 *@ModifiedBy:
	 *@ModifiedDate:
	 *@param: browserType - name of the TestCase currently getting executed
	 */

	public static RemoteWebDriver getWebdriver(String browserType) throws InterruptedException, IOException {

		Logger logger = Log.getInstance(Thread.currentThread().getStackTrace()[1].getClassName());
		logger.info("");
		logger.info("Browser type: " + browserType);
		RemoteWebDriver webdriver = null;
		//ResourceBundle globalProperties = getGlobalProperties();
		Properties globalProperties = new Properties();
		FileInputStream input;
		input = new FileInputStream("D:\\Grasp\\Grasp\\src\\test\\resources\\global.properties");
		globalProperties.load(input);
		
		if(browserType.equalsIgnoreCase("InternetExplorer")){
			try{
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				
			   // WebDriver driver = new InternetExplorerDriver(capabilities);
			//	capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);
				File file = new File(globalProperties.getProperty("webdriver_ie_driver"));
				System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
				webdriver = new InternetExplorerDriver(capabilities);
		        System.setProperty("webdriver.ie.driver",globalProperties.getProperty("webdriver_ie_driver"));
			}

			catch (IllegalStateException e){
				logger.error(
						"The path to the driver executable must be set by the webdriver.ie.driver system property, Check IE driver path in Global.properties file",
						e.fillInStackTrace());
				throw new IllegalStateException(
						"The path to the driver executable must be set by the webdriver.ie.driver system property, Check IE driver path in Global.properties file");

			}
		}

		else if(browserType.equalsIgnoreCase("Firefox")){
			System.out.println("browserType" + browserType);
			webdriver = new FirefoxDriver();

		}

		else if(browserType.equalsIgnoreCase("Chrome")){
			System.out.println("browserType" + browserType);
			try{
				
				System.setProperty("webdriver.chrome.driver", globalProperties.getProperty("webdriver_chrome_driver"));
				
			}
			catch (Exception e){
				logger.error(
						"The path to the driver executable must be set by the webdriver.ie.driver system property, Check IE driver path in Global.properties file",
						e.fillInStackTrace());
			}
			webdriver = new ChromeDriver();
		}

		else{
			logger.error("getWebDriver - Unable to instantiate new webDriver. Unrecognised browser identifier. " + browserType);
		}

		//webdriver = new RemoteWebDriver(driverCapability);
		return webdriver;

	}
		
		
	
	private static ResourceBundle getGlobalProperties(){
		ResourceBundle globalProperties = null;
		globalProperties = ResourceBundle.getBundle("global");
		return globalProperties;
	}


}
