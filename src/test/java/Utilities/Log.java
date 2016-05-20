/**
	 *@UtilityComponentName: Log
	 *@Description:  
	 *@author: 
	 *@CreatedDate:
	 *@ModifiedBy:
	 *@ModifiedDate:
     *@param: 
     *@return:
     */
package Utilities;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author TCS
 *
 */
public class Log {
	/**
	 *@MethodName: M001-GetInstance
	 *@Description: This function configures the Log4j xml
	 *@author: 
	 *@CreatedDate: Dec5, 2014
	 *@ModifiedBy:
	 *@ModifiedDate:
     *@param: className
     *@return: 
     */
	
	public static Logger getInstance(String className)
	{
	    DOMConfigurator.configure("log4j.xml");
		return Logger.getLogger(className);
	}

}
