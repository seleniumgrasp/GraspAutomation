import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;


public class sample {

@Test
	public void oneMoreTest() {
	WebDriver driver=new FirefoxDriver();
	driver.get("http://www.google.com");
	        System.out.println("This is a TestNG-Maven based test");

}
}