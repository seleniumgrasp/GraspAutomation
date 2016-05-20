/**
 * 
 */
package Cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;



/**
 * @author KrishnamurthyK
 *
 */
@RunWith(Cucumber.class)

@CucumberOptions(features = "src/test/resources",
format = {"pretty"},
		plugin= {"json:target/cucumber-report1.json",
		"html:target/test-report1",
		"junit:target/test-report1.xml"}
		 
)
public class RunnerTest  { 

	
}
