/**
 * 
 */
package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import Driver.Driver_commonObjects;

/**
 * @author Krishnamurthy
 *
 */
public class LoginPage extends  Driver_commonObjects {
	WebDriver driver;
	@FindBy(name="USER") 
	WebElement username;
	
	@FindBy(how=How.NAME,using="PASSWORD")
	WebElement password;
	
	@FindBy(how=How.NAME,using="submit")
	WebElement loginbutton;

	
	public void login_credentials(String uid,String pass)
	{
		
		
		username.clear();
		username.sendKeys(uid);
		password.sendKeys(pass);
		loginbutton.click();
		
	}
	
	public LoginPage(WebDriver ldriver){
		this.driver=ldriver;
	}
}
