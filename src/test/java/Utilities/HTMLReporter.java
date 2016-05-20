package Utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.internal.Utils;
import org.testng.reporters.util.StackTraceTools;
import org.testng.xml.XmlSuite;

import Driver.Driver_commonObjects;

public class HTMLReporter extends TestListenerAdapter implements IReporter
{
	private static PrintWriter f_out;
	private static String      outputDir;

	private static String[]    MODULES;
	private static String[]    TEST_GROUPS;
	public static String htmlFile;
	public static String stackTraceFile;
	public static String fileName;



	public void generateReport(List<XmlSuite> arg0, List<ISuite> suites, String outdir)
	{
		try
		{		
			outputDir = Driver_commonObjects.applicationDirectory+"/"+Driver_commonObjects.strdateall;            
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		String modulesCommaSeparated = "";
		String testGroupsCommaSeparated = "";

		try
		{
			modulesCommaSeparated = Driver_commonObjects.applicationTestModule.replaceAll("\\s+", "");
			testGroupsCommaSeparated = Driver_commonObjects.applicationTestGroup.replaceAll("\\s+", "");
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		if (modulesCommaSeparated == null || modulesCommaSeparated.trim().length() == 0)
		{
			Assert.fail("ERROR - Modules are not found in properties file");
		} 
		else
		{
			MODULES = new String[modulesCommaSeparated.length()];
			MODULES = modulesCommaSeparated.split(",");
		}

		if (testGroupsCommaSeparated == null || testGroupsCommaSeparated.trim().length() == 0)
		{
			Assert.fail("ERROR - Test Groups are not found in properties file");
		} 
		else
		{
			TEST_GROUPS = new String[testGroupsCommaSeparated.length()];
			TEST_GROUPS = testGroupsCommaSeparated.split(",");
		}

		try
		{
			f_out = createWriter(outputDir);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}

		startHtmlPage(f_out);
		generateTestExecutionStatus(suites);
		endHtmlPage(f_out);

		f_out.flush();
		f_out.close();

		String imgDir = System.getProperty("user.dir")+"\\resource\\image003.png";
		String copyTmgDir = outputDir+"\\image003.png";
		FileChannel source = null;
		FileChannel destination = null;		

		try {

			source = new FileInputStream(imgDir).getChannel();
			destination = new FileOutputStream(copyTmgDir).getChannel();
			if (destination != null && source != null) 
			{				
				destination.transferFrom(source, 0, source.size());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try{
			for(int i=0; i<Driver_commonObjects.screenshots.size(); i++){
				File src = new File(Driver_commonObjects.applicationDirectory+"/"+Driver_commonObjects.screenshots.get(i));
				File dest = new File (outputDir);
				FileUtils.copyFileToDirectory(src, dest);
				//FileUtils.moveFile(src, dest);
				src.delete();
			}

		}catch(Exception e){
			System.out.println("No screen shot is present");
		}

		ZipDirectory zipFile =new ZipDirectory();
		String destZipFile = outputDir+".zip";
		try 
		{
			zipFile.zipFolder(outputDir, destZipFile);
		} 
		catch (Exception e) 
		{			
			e.printStackTrace();
		}

		//Mail.sendemail(Driver_commonObjects.Module, Driver_commonObjects.username, Driver_commonObjects.Email, Driver_commonObjects.ToEmail, Driver_commonObjects.Time);
	}

	private void generateTestExecutionStatus(List<ISuite> suites)
	{
		String testName = "";

		int totalPassedMethods = 0;
		int totalFailedMethods = 0;
		int totalSkippedMethods = 0;
		int totalSkippedConfigurationMethods = 0;
		int totalFailedConfigurationMethods = 0;
		int totalMethods = 0;

		int suite_totalPassedMethods = 0;
		int suite_totalFailedMethods = 0;
		int suite_totalSkippedMethods = 0;

		String suite_passPercentage = "";
		String suiteName = "";        
		long totalModuleExecutionTime=0;
		long totalSuiteExecutionTime =0;
		ITestContext overview = null;
		HashMap<String, String> dashboardReportMap = new HashMap<String, String>();

		for (ISuite suite : suites)
		{
			suiteName = suite.getName();
			//            stite_start_time = suite/
			Map<String, ISuiteResult> tests = suite.getResults();

			for (ISuiteResult r : tests.values())
			{
				overview = r.getTestContext();
				testName = overview.getName();

				totalPassedMethods = overview.getPassedTests().getAllMethods().size();
				totalFailedMethods = overview.getFailedTests().getAllMethods().size();
				totalSkippedMethods = overview.getSkippedTests().getAllMethods().size();

				totalMethods = overview.getAllTestMethods().length;

				NumberFormat nf = NumberFormat.getInstance();
				nf.setMaximumFractionDigits(2);
				nf.setGroupingUsed(true);

				String includedModule = "";
				String includedGroup = "";

				ITestNGMethod[] allTestMethods = overview.getAllTestMethods();
				for (ITestNGMethod testngMethod : allTestMethods)
				{
					String[] modules = testngMethod.getGroups();
					for (String module : modules)
					{
						for (String moduleName : MODULES)
						{
							if (module.equalsIgnoreCase(moduleName))
							{
								if (!(includedModule.contains(module)))
								{
									includedModule = includedModule + " " + module;
								}
							}
						}
						for (String groupName : TEST_GROUPS)
						{
							if (module.equalsIgnoreCase(groupName))
							{
								if (!(includedGroup.contains(module)))
								{
									includedGroup = includedGroup + " " + module;
								}
							}
						}
					}
				}

				String browser = overview.getCurrentXmlTest().getParameter("browser");
				String browser_version = overview.getCurrentXmlTest().getParameter("browser_version"); 
				String platform = overview.getCurrentXmlTest().getParameter("os");

				//this.OS_Version=getValue("OS_Version");
				/*String browser = Driver_commonObjects.TestBrowser;
				String browser_version = Driver_commonObjects.TestBrowser_Version; 
				String platform = Driver_commonObjects.OS;*/
				
				if (platform == null || platform.trim().length() == 0)
				{
					platform = "N/A";
				}

				if (browser_version == null || browser_version.trim().length() == 0)
				{
					browser_version = "N/A";
				}

				if (browser == null || browser.trim().length() == 0)
				{
					browser = "N/A";
				}

				if (!(dashboardReportMap.containsKey(includedModule)))
				{
					if (browser_version.equalsIgnoreCase("N/A"))
					{
						browser_version = "";
					}
					dashboardReportMap.put(includedModule, "os1~" + platform + "|browser1~" + browser + browser_version
							+ "|testcase_count_1~" + totalMethods + "|pass_count_1~" + totalPassedMethods
							+ "|fail_count_1~" + totalFailedMethods + "|skip_count_1~" + totalSkippedMethods
							+ "|skip_conf_count_1~" + totalSkippedConfigurationMethods + "|fail_conf_count_1~"
							+ totalFailedConfigurationMethods);

				} 
				else
				{
					for (String key : dashboardReportMap.keySet())
					{

						if (key.equalsIgnoreCase(includedModule))
						{
							if (browser_version.equalsIgnoreCase("N/A"))
							{
								browser_version = "";
							}
							String value = dashboardReportMap.get(key);
							int index = StringUtils.countMatches(value, "#") + 1;

							index += 1;

							value = value + "#" + "os" + index + "~" + platform + "|browser" + index + "~" + browser
									+ browser_version + "|testcase_count_" + index + "~" + totalMethods
									+ "|pass_count_" + index + "~" + totalPassedMethods + "|fail_count_" + index + "~"
									+ totalFailedMethods + "|skip_count_" + index + "~" + totalSkippedMethods
									+ "|skip_conf_count_" + index + "~" + totalSkippedConfigurationMethods
									+ "|fail_conf_count_" + index + "~" + totalFailedConfigurationMethods;
							dashboardReportMap.put(key, value);
						}
					}
				}

				suite_totalPassedMethods += totalPassedMethods;
				suite_totalFailedMethods += totalFailedMethods;
				suite_totalSkippedMethods += totalSkippedMethods;

				try
				{
					suite_passPercentage = nf
							.format(((float) suite_totalPassedMethods / (float) (suite_totalPassedMethods
									+ suite_totalFailedMethods + suite_totalSkippedMethods)) * 100);
				} catch (NumberFormatException e)
				{
					e.printStackTrace();
				}
			}
		}

		StringBuilder dashboardResults = new StringBuilder();

		dashboardResults.append("<table style=\"border-collapse: collapse; width: auto;\">");
		dashboardResults
		.append("<thead><tr> "
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: white; font-family: Arial;font-size:12px;\" >Test Set</th>"
				/*+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:5cm; color: white; font-family: Arial;font-size:12px;\" >Browser Name/Version</th>"
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: white; font-family: Arial;font-size:12px;\" >OS Name</th>"*/
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: white; font-family: Arial;font-size:12px;\" >Passed</th>"
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: white; font-family: Arial;font-size:12px;\" >Failed</th>"
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: white; font-family: Arial;font-size:12px;\" >Skipped</th>"
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: white; font-family: Arial;font-size:12px;\" >Total</th>"
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: white; font-family: Arial;font-size:12px;\" >Success Rate</th>"
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: white; font-family: Arial;font-size:12px;\" >Execution Time (Sec)</th>"
				+ "</tr> </thead> <tbody>");

		int total_browser_combinations = 0;

		for (String key : dashboardReportMap.keySet())
		{

			fileName = key.trim() + "_TestCasewise" + "_ExecutionSummaryReport.html";

			try
			{
				totalModuleExecutionTime = generateModuleOverallTestReport(testName, key, suites, fileName, suiteName);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

			String value = dashboardReportMap.get(key); 
			String[] values = value.split("#");

			int testcase_count = 0;
			int pass_count = 0;
			int fail_count = 0;
			int skip_count = 0;
			int skip_conf_count = 0;
			int fail_conf_count = 0;
			String browser ="";
			String  platform ="";
			String dashboardModule = key;

			for (String val : values)
			{

				String[] tokens = val.split("\\|");
				for (String token : tokens)
				{
					if(token.contains("browser"))
					{
						browser = token.split("~")[1];
					}
					if(token.contains("os"))
					{
						platform = token.split("~")[1];
					}
					if (token.contains("testcase_count"))
					{
						testcase_count = testcase_count + Integer.parseInt(token.split("~")[1]);
					}
					if (token.contains("pass_count"))
					{
						pass_count = pass_count + Integer.parseInt(token.split("~")[1]);
					}
					if (token.contains("fail_count"))
					{
						fail_count = fail_count + Integer.parseInt(token.split("~")[1]);
					}
					if (token.contains("skip_count"))
					{
						skip_count = skip_count + Integer.parseInt(token.split("~")[1]);
					}
					if (token.contains("skip_conf_count"))
					{
						skip_conf_count = skip_conf_count + Integer.parseInt(token.split("~")[1]);
					}
					if (token.contains("fail_conf_count"))
					{
						fail_conf_count = fail_conf_count + Integer.parseInt(token.split("~")[1]);
					}
				}
			}

			String[] sub = value.split("#");
			String temp = "";
			for (String s : sub)
			{
				s = s.substring(0, s.indexOf("fail_count")); 
				temp = temp + s;
			}

			temp = temp.substring(0, temp.lastIndexOf("|"));
			temp = temp.replace(" ", "%20");

			NumberFormat nformat = NumberFormat.getInstance();
			nformat.setMaximumFractionDigits(2);
			nformat.setGroupingUsed(true);
			String passPercent = nformat
					.format(((float) pass_count / (float) (pass_count + fail_count)) * 100);

			String finalStr = "[";
			String[] val = dashboardReportMap.get(key).split("#");

			int unique_testcase = 0;

			int limit = val.length - 1;
			for (int i = 0; i < val.length; i++)
			{
				String testCaseCount = (val[i].split("\\|")[2]).split("~")[1];
				int next = Integer.parseInt(testCaseCount);
				if (next > unique_testcase)
				{
					unique_testcase = next;
				}
				finalStr = finalStr + testCaseCount + " T * 1 B]";
				if (i != limit)
				{
					finalStr += " + [";
				}
			}

			String finalString = "";
			if ((unique_testcase * values.length) != (pass_count + fail_count + skip_count ))
			{
				finalString = "<a href=\"#\" title=\"" + finalStr + "\">" + (pass_count + fail_count+skip_count)
						+ "</a>";
			} else
			{
				finalString = String.valueOf((pass_count + fail_count+skip_count));
			}

			String passCount = "";
			String failCount = "";
			String skipCount = "";

			if (pass_count > 0)
			{
				passCount = "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #00CC00; font-family: Georgia;\"><b>"
						+ pass_count + "</b></td>";
			} else
			{
				passCount = "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">"
						+ pass_count + "</td>";
			}

			if (fail_count > 0)
			{
				failCount = "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #CC0000; font-family: Georgia;\"><b>"
						+ fail_count + "</b></td>";
			} else
			{
				failCount = "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">"
						+ fail_count + "</td>";
			}

			if (skip_count > 0)
			{
				skipCount = "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #CCA300; font-family: Georgia;\"><b>"
						+ skip_count + "</b></td>";
			}
			else
			{
				skipCount = "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">"
						+ skip_count + "</td>";
			}

			//            String browser = overview.getCurrentXmlTest().getParameter("browser"); 
			String browser_version = overview.getCurrentXmlTest().getParameter("browser_version"); 
			//            String platform = overview.getCurrentXmlTest().getParameter("os");
			//String platformVersion = overview.getCurrentXmlTest().getParameter("os_version");
			//String browser_version = Driver_commonObjects.OS_Version;

			if(passPercent.equals("100")){
				dashboardResults 
				.append("<tr><td style=\"text-align: center; border: 1px solid grey; height: 20px; color: #1C1C1C; font-family: Arial;font-size:12px;\"><b><a href='"
						+ fileName
						+ "'>"
						+ dashboardModule
						/*+"<td style=\"text-align: center; border: 1px solid grey; height: 20px; color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						//                            + browser+"/"+browser_version + "</td>"
						+ browser+ "</td>"
						+"<td style=\"text-align: center; border: 1px solid grey; height: 20px; color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						//                            + platform + "/"+platformVersion +"</td>"
*/						+ passCount
						+ failCount
						+ skipCount
						+ "<td style=\"text-align: center; border: 1px solid grey; height: 20px; color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ finalString
						+ "</td><td style=\"text-align: center; border: 1px solid grey; height: 20px; color: #1C1C1C; font-family: Arial;font-size:12px;\"><font color=\"#00CC00\"><b>"
						+ passPercent + " %" + "</b></font></td>"
						+"<td style=\"text-align: center; border: 1px solid grey; height: 20px; color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ totalModuleExecutionTime + "</font></td>"
						+ "</tr>");
			}else {
				dashboardResults 
				.append("<tr><td style=\"text-align: center; border: 1px solid grey; height: 20px; color: #1C1C1C; font-family: Arial;font-size:12px;\"><b><a href='"
						+ fileName
						+ "'>"
						+ dashboardModule
						/*+"<td style=\"text-align: center; border: 1px solid grey; height: 20px; color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						//                            + browser+"/"+browser_version + "</td>"
						+ browser+ "</td>"
						+"<td style=\"text-align: center; border: 1px solid grey; height: 20px; color: #1C1C1C; font-family: Arial;font-size:12px;\">"

						+ platform + "</td>"*/
						+ passCount
						+ failCount
						+ skipCount
						+ "<td style=\"text-align: center; border: 1px solid grey; height: 20px; color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ finalString
						+ "</td><td style=\"text-align: center; border: 1px solid grey; height: 20px; color: #1C1C1C; font-family: Arial;font-size:12px;\"><font color=\"#CC0000\"><b>"
						+ passPercent + " %" + "</b></font></td>"
						+"<td style=\"text-align: center; border: 1px solid grey; height: 20px; color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ totalModuleExecutionTime + "</font></td>"
						+ "</tr>");
			}

			if (total_browser_combinations < values.length)
			{
				total_browser_combinations = values.length;
			}    
			totalSuiteExecutionTime=totalSuiteExecutionTime+totalModuleExecutionTime;
		}

		dashboardResults.append("</tbody></table>");

		String suite_pass = "";
		String suite_fail = "";
		String suite_skip = "";

		if (suite_totalPassedMethods > 0)
		{
			suite_pass = "</td><td style=\"text-align: center; border: 1px solid grey; height: 12px; color: #00CC00; font-family: Arial;font-size:12px;\"><b>"
					+ suite_totalPassedMethods + "</b></td>";
		} else
		{
			suite_pass = "<td style=\"text-align: center; border: 1px solid grey; height: 12px; color: #1C1C1C; font-family: Arial;font-size:12px;\">"
					+ suite_totalPassedMethods + "</td>";
		}

		if (suite_totalFailedMethods > 0)
		{
			suite_fail = "<td style=\"text-align: center; border: 1px solid grey; height: 12px; color: #CC0000; font-family: Arial;font-size:12px;\"><b>"
					+ suite_totalFailedMethods + "</b></td>";
		} else
		{
			suite_fail = "<td style=\"text-align: center; border: 1px solid grey; height: 12px; color: #1C1C1C; font-family: Arial;font-size:12px;\">"
					+ suite_totalFailedMethods + "</td>";
		}

		if (suite_totalSkippedMethods > 0)
		{
			suite_skip = "<td style=\"text-align: center; border: 1px solid grey; height: 12px; color: #1C1C1C; font-family: Arial;font-size:12px;\"><b>"
					+ suite_totalSkippedMethods + "</b></td>";
		} else
		{
			suite_skip = "<td style=\"text-align: center; border: 1px solid grey; height: 12px; color: #1C1C1C; font-family: Arial;font-size:12px;\">"
					+ suite_totalSkippedMethods + "</td>";
		}

		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		nf.setGroupingUsed(true);

		try
		{
			suite_passPercentage = nf
					.format(((float) suite_totalPassedMethods / (float) (suite_totalPassedMethods + suite_totalFailedMethods) * 100));
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}

		// Summary Table
		f_out.println("<p><b><font color=\"blue\">OverAll Execution Summary Report</b></p>");
		if(suite_passPercentage.equals("100")){
			f_out.println("<table style=\"border-collapse: collapse;\"><thead><tr>"	      		
					+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: #FFFFFF; font-family: Arial;font-size:12px;\" >Test Suite Name</th>"
					+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: #FFFFFF; font-family: Arial;font-size:12px;\" ># Passed</th> "
					+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: #FFFFFF; font-family: Arial;font-size:12px;\" ># Failed</th> "
					+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: #FFFFFF; font-family: Arial;font-size:12px;\" ># Skipped</th>"
					+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: #FFFFFF; font-family: Arial;font-size:12px;\" ># Total</th> "
					+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: #FFFFFF; font-family: Arial;font-size:12px;\" >Success Rate</th> "
					+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: #FFFFFF; font-family: Arial;font-size:12px;\" >Execution Time (Sec)</th> "
					+ "</tr> </thead> <tbody> <tr>"
					+ "<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: black; font-family: Arial;font-size:12px;\">"
					+ suiteName
					+ suite_pass
					+ suite_fail
					+ suite_skip
					+ "<td style=\"text-align: center; border: 1px solid grey; height: 12px; color: black; font-family: Arial;font-size:12px;\">"
					+ (suite_totalPassedMethods + suite_totalFailedMethods + suite_totalSkippedMethods)
					+ "</td><td style=\"text-align: center; border: 1px solid grey; height: 12px; color: black; font-family: Arial;font-size:12px;\"><font color=\"#00cc00\"><b>"
					+ suite_passPercentage + " %" + "</b></font></td>"
					+ "<td style=\"text-align: center; border: 1px solid grey; height: 12px; color: black; font-family: Arial;font-size:12px;\">"
					+ totalSuiteExecutionTime
					+ "</td></tr></tbody></table>");
		}else{
			f_out.println("<table style=\"border-collapse: collapse;\"><thead><tr>"	      		
					+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: #FFFFFF; font-family: Arial;font-size:12px;\" >Test Suite Name</th>"
					+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: #FFFFFF; font-family: Arial;font-size:12px;\" ># Passed</th> "
					+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: #FFFFFF; font-family: Arial;font-size:12px;\" ># Failed</th> "
					+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: #FFFFFF; font-family: Arial;font-size:12px;\" ># Skipped</th>"
					+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: #FFFFFF; font-family: Arial;font-size:12px;\" ># Total</th> "
					+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: #FFFFFF; font-family: Arial;font-size:12px;\" >Success Rate</th> "
					+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: #FFFFFF; font-family: Arial;font-size:12px;\" >Execution Time (Sec)</th> "
					+ "</tr> </thead> <tbody> <tr>"
					+ "<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: black; font-family: Arial;font-size:12px;\">"
					+ suiteName
					+ suite_pass
					+ suite_fail
					+ suite_skip
					+ "<td style=\"text-align: center; border: 1px solid grey; height: 12px; color: black; font-family: Arial;font-size:12px;\">"
					+ (suite_totalPassedMethods + suite_totalFailedMethods + suite_totalSkippedMethods)
					+ "</td><td style=\"text-align: center; border: 1px solid grey; height: 12px; color: black; font-family: Arial;font-size:12px;\"><font color=\"#CC0000\"><b>"
					+ suite_passPercentage + " %" + "</b></font></td>"
					+ "<td style=\"text-align: center; border: 1px solid grey; height: 12px; color: black; font-family: Arial;font-size:12px;\">"
					+ totalSuiteExecutionTime
					+ "</td></tr></tbody></table>");
		}

		f_out.flush();

		// f_out.println("<br/>");
		f_out.println("<p><b>Modulewise Execution Summary</b></p>");
		f_out.println(dashboardResults);

		f_out.flush();
	}

	private long generateModuleOverallTestReport(String testName, String moduleVar, List<ISuite> suites,
			String newFileName, String suiteName) throws Exception
			{
		StringBuilder moduleResults = new StringBuilder();

		final PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDir, newFileName))));
		startHtmlPage(pw);

		pw.println("<button onClick=\"location.href='OverAll_ExecutionSummaryReport.html'\"><span class=\"prev\">Back to Overall Execution Summary</span></button>");
		pw.println("<br/></br>");//       
		pw.println("<table style=\"border-collapse: collapse; width: 40%;\">");
		pw.println("<tr>"               
				+ "<th style=\"text-align: left; background-color: #808080;border: 1px solid grey; height: 12px;width:15%; color: #FFFFFF; font-family: Arial;font-size:12px;\">TestSuit Name: </th>"
				+ "<td style=\"text-align: left; border: 1px solid grey; height: 12px;width:25%; color: #1C1C1C; font-family: Arial;font-size:12px;\">"+suiteName+"</td></tr>"
				+ "<th style=\"text-align: left; background-color: #808080;border: 1px solid grey; height: 12px;width:15%; color: #FFFFFF; font-family: Arial;font-size:12px;\"> Module Name: </th>"
				+ "<td style=\"text-align: left; border: 1px solid grey; height: 12px;width:25%; color: #1C1C1C; font-family: Arial;font-size:12px;\">"+moduleVar+" </td></tr>");
		pw.println("</table>");

		pw.println("<p><b><font color=\"blue\">TestCasewise Overall Execution Details</b></p>");
		moduleResults.append("<table style=\"border-collapse: collapse; width: auto;\">");
		moduleResults
		.append("<thead><tr>" 
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:7cm; color: white; font-family: Arial;font-size:12px;\" >TestCase Name</th> "
				/*+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: white; font-family: Arial;font-size:12px;\" >Browser</th> "*/
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: white; font-family: Arial;font-size:12px;\" >Status</th>"
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: white; font-family: Arial;font-size:12px;\" >Execution Time (Sec)</th>"
				+ "</tr> </thead> <tbody>");

		int totalPassedMethods = 0;
		int totalFailedMethods = 0;
		int totalSkippedMethods = 0;
		String passPercentage = "";
		String status = "";
		long time_start = Long.MAX_VALUE;
		long time_end = Long.MIN_VALUE;
		long totalTestCaseExecutionTime = 0;
		long totalModuleExecutionTime =0;
		ITestContext overview = null;

		for (ISuite suite : suites)
		{
			Map<String, ISuiteResult> tests = suite.getResults(); //{ATC001_SAdv_Cart_AddSKUItemTest=[SuiteResult context=ATC001_SAdv_Cart_AddSKUItemTest], ATC002_SAdv_Cart_AddManufacturerNameTest=[SuiteResult context=ATC002_SAdv_Cart_AddManufacturerNameTest], ATC001_1_SAdv_Cart_AddSKUItemTest=[SuiteResult context=ATC001_1_SAdv_Cart_AddSKUItemTest]}
			//            ArrayList<Set<ITestResult>> statusResult = new ArrayList<Set<ITestResult>>();


			for (ISuiteResult r : tests.values()) //[SuiteResult context=ATC001_SAdv_Cart_AddSKUItemTest]
			{
				ArrayList<Set<ITestResult>> statusResult = new ArrayList<Set<ITestResult>>();
				overview = r.getTestContext(); //org.testng.TestRunner@6177202f

				testName = overview.getName(); //ATC001_1_SAdv_Cart_AddSKUItemTest
				time_start = overview.getStartDate().getTime();
				time_end = overview.getEndDate().getTime();                
				totalTestCaseExecutionTime = (time_end-time_start)/1000;
				totalPassedMethods = overview.getPassedTests().getAllMethods().size();
				totalFailedMethods = overview.getFailedTests().getAllMethods().size();
				totalSkippedMethods = overview.getSkippedTests().getAllMethods().size();

				NumberFormat nf = NumberFormat.getInstance();
				nf.setMaximumFractionDigits(2);
				nf.setGroupingUsed(true);
				try
				{
					passPercentage = nf.format(((float) totalPassedMethods / (float) (totalPassedMethods
							+ totalFailedMethods + totalSkippedMethods)) * 100);
					if(passPercentage.equalsIgnoreCase("100"))
					{
						status="PASS";
					}
					else if(totalSkippedMethods!=0&&totalPassedMethods!=0&&totalFailedMethods==0)
					{
						status="PASS/SKIPPED";
					}
					else if(totalSkippedMethods!=0&&totalPassedMethods==0&&totalFailedMethods!=0)
					{
						status="FAIL/SKIPPED";
					}
					else if(totalSkippedMethods!=0&&totalPassedMethods==0&&totalFailedMethods==0)
					{
						status="SKIPPED";
					}
					else
					{
						status="FAIL";
					}
				} 
				catch (NumberFormatException e)
				{
					e.printStackTrace();
				}

				String includedModule = "";
				String includedGroup = "";

				ITestNGMethod[] allTestMethods = overview.getAllTestMethods(); //[ATC001_SAdv_Cart_AddSKUItemTest.addSKUItem()[pri:0, instance:[ATC001_SAdv_Cart_AddSKUItemTest passed=0 failed=0 skipped=0]]]

				for (ITestNGMethod testngMethod : allTestMethods) //ATC001_SAdv_Cart_AddSKUItemTest.addSKUItem()[pri:0, instance:[ATC001_SAdv_Cart_AddSKUItemTest passed=0 failed=0 skipped=0]]
				{
					String[] modules = testngMethod.getGroups();

					for (String module : modules)
					{
						for (String moduleName : MODULES)
						{
							if (module.equalsIgnoreCase(moduleName))
							{
								if (!(includedModule.contains(module)))
								{
									includedModule = includedModule + " " + module;
								}
							}
						}
						for (String groupName : TEST_GROUPS)
						{
							if (module.equalsIgnoreCase(groupName))
							{
								if (!(includedGroup.contains(module)))
								{
									includedGroup = includedGroup + " " + module;
								}
							}
						}
					}
				}


				String browser = overview.getCurrentXmlTest().getParameter("browser");
				String browser_version = overview.getCurrentXmlTest().getParameter("browser_version");
				String platform = overview.getCurrentXmlTest().getParameter("os");
				//String platformVersion = overview.getCurrentXmlTest().getParameter("os_version");
				/*String browser = Driver_commonObjects.TestBrowser;
				String browser_version = Driver_commonObjects.TestBrowser_Version; 
				String platform = Driver_commonObjects.OS;*/

				if (platform == null || platform.trim().length() == 0)
				{
					platform = "N/A";
				}

				if (browser_version == null || browser_version.trim().length() == 0)
				{
					browser_version = "N/A";
				}

				if (browser == null || browser.trim().length() == 0)
				{
					browser = "N/A";
				}

				if (browser.equalsIgnoreCase("firefox"))
				{
					browser = "Firefox";
				} else if (browser.equalsIgnoreCase("chrome"))
				{
					browser = "Chrome";
				} else if (browser.equalsIgnoreCase("internet explorer"))
				{
					browser = "IE";
				}

				//Edited by chakradhar k

				/*if (platform.equalsIgnoreCase("windows") && platformVersion.equalsIgnoreCase("xp"))
				{
					platform = "Win XP";
				} else if (platform.equalsIgnoreCase("windows") && platformVersion.equalsIgnoreCase("7"))
				{
					platform = "Win 7";
				} else if (platform.equalsIgnoreCase("windows") && platformVersion.equalsIgnoreCase("8"))
				{
					platform = "Win 8";
				} else if (platform.equalsIgnoreCase("mac"))
				{
					platform = "Mac";
				} else
				{


				}*/

				if (includedModule.equalsIgnoreCase(moduleVar))
				{
					String fileName = testName +"_"+moduleVar+ "_ExecutionSummaryReport.html";

					try
					{
						generateTestCaseMethodSummary(testName, stackTraceFile, moduleVar, suites, fileName, allTestMethods, "?", suiteName);
					} 
					catch (IOException e)
					{
						e.printStackTrace();
					}

					String passCount = "";
					String failCount = "";
					String skipCount = "";

					if (totalPassedMethods > 0)
					{
						passCount = "<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\"><b>"
								+ totalPassedMethods + "</b></td>";
					} 
					else
					{
						passCount = "<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
								+ totalPassedMethods + "</td>";
					}

					if (totalFailedMethods > 0)
					{
						failCount = "<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\"><b>"
								+ totalFailedMethods + "</b></td>";
					} else
					{
						failCount = "<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
								+ totalFailedMethods + "</td>";
					}

					if (totalSkippedMethods > 0)
					{
						skipCount = "<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\"><b>"
								+ totalSkippedMethods + "</b></td>";
					} else
					{
						skipCount = "<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
								+ totalSkippedMethods + "</td>";
					}

					if(status.equals("PASS")){
						moduleResults
						.append("<tr><td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\"><b><a href=\""
								+ fileName
								+ "\">"
								+ testName
								+ "</a></b></td>"
								/*+ "</td><td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
								+ browser+"/"//+browser_version
*/								+ "</td><td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\"><font color=\"#00CC00\"><b>"
								+ status  + "</b></font></td>"                                    		
								+ "<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
								+ totalTestCaseExecutionTime + "</font></td></tr>");
					}
					else if(status.equals("PASS/SKIPPED")){
						moduleResults
						.append("<tr><td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\"><b><a href=\""
								+ fileName
								+ "\">"
								+ testName
								+ "</a></b></td>"
								/*+ "</td><td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
								+ browser+"/"//+browser_version
*/								+ "</td><td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\"><font color=\"#00CC00\"><b>"
								+ status  + "</b></font></td>"                                    		
								+ "<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
								+ totalTestCaseExecutionTime + "</font></td></tr>");
					}
					else if(status.equals("FAIL/SKIPPED")){
						moduleResults
						.append("<tr><td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\"><b><a href=\""
								+ fileName
								+ "\">"
								+ testName
								+ "</a></b></td>"
								/*+ "</td><td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
								+ browser+"/"//+browser_version
*/								+ "</td><td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\"><font color=\"#FF6600\"><b>"
								+ status  + "</b></font></td>"                                    		
								+ "<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
								+ totalTestCaseExecutionTime + "</font></td></tr>");
					}
					else if(status.equals("FAIL")){
						moduleResults
						.append("<tr><td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\"><b><a href=\""
								+ fileName
								+ "\">"
								+ testName
								+ "</a></b></td>"
								/*+ "</td><td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
								+ browser+"/"//+browser_version
*/								+ "</td><td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\"><font color=\"#CC0000\"><b>"
								+ status  + "</b></font></td>"                                    		
								+ "<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
								+ totalTestCaseExecutionTime + "</font></td></tr>");
					}
					totalModuleExecutionTime =totalModuleExecutionTime+totalTestCaseExecutionTime;
				}
			}
		}

		moduleResults.append("</tbody></table>");
		pw.println(moduleResults);

		endHtmlPage(pw);

		pw.flush();
		pw.close();

		return totalModuleExecutionTime;
			}


	public void generateTestCaseMethodSummary(String testName, String stackTraceFile, String modulename, List<ISuite> suites,
			String fileName, ITestNGMethod[] testngMethods, String nodeIp, String suiteName) throws IOException
			{
		final PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDir, fileName))));

		startHtmlPage(pw);

		htmlFile = modulename + "_TestCasewise_ExecutionSummaryReport.html";
		//String modulewiseTestFileName = testName + "-" + modulename + "_ExecutionSummaryReport.html";

		pw.println("<button onClick=\"location.href='" + htmlFile
				+ "'\"><span class=\"prev\">Back to Modulewise Execution Summary</span></button>");
		pw.println("<br/>");
		pw.println("<p><b><font color=\"blue\">Details</b></p>");
		//pw.println("<br/>");
		//      <b><font color=\"blue\">Test Environment Details</font><br/><br/></b>
		pw.println("<table style=\"border-collapse: collapse; width: 40%;\">");
		// f_out.println("<tr><th style=\"text-align: center; background-color: #808080; border: 0px solid grey; height: 25px; color: #4c4c4c; font-family: Georgia;\" colspan=\"2\"><b>Configuration Details</b></th></tr>");

		pw.println("<tr>"               
				+ "<th style=\"text-align: left; background-color: #808080;border: 1px solid grey; height: 12px;width:15%; color: #FFFFFF; font-family: Arial;font-size:12px;\">TestSuite Name: </th>"
				+ "<td style=\"text-align: left; border: 1px solid grey; height: 12px;width:25%; color: #1C1C1C; font-family: Arial;font-size:12px;\">"+suiteName+"</td></tr>"
				+ "<th style=\"text-align: left; background-color: #808080;border: 1px solid grey; height: 12px;width:15%; color: #FFFFFF; font-family: Arial;font-size:12px;\"> Module Name: </th>"
				+ "<td style=\"text-align: left; border: 1px solid grey; height: 12px;width:25%; color: #1C1C1C; font-family: Arial;font-size:12px;\">"+modulename+" </td></tr>"
				+ "<th style=\"text-align: left; background-color: #808080;border: 1px solid grey; height: 12px;width:15%; color: #FFFFFF; font-family: Arial;font-size:12px;\"> Test Case Name: </th>"
				+ "<td style=\"text-align: left; border: 1px solid grey; height: 12px;width:25%; color: #1C1C1C; font-family: Arial;font-size:12px;\">"+testName+" </td></tr>");
		pw.println("</table>");
		pw.println("<br/>");
		//pw.println("<br/>");
		stackTraceFile = testName + "_" + modulename +"_trace.html"; 
		pw.println("<button onClick=\"location.href='" + stackTraceFile
				+ "'\"><span class=\"prev\">Click to Trace Stacks</span></button>");
		pw.println("<br/>");
		pw.println("<br/>");
		pw.println("<button onClick=\"location.href='" + testName + ".png"
				+ "'\"><span class=\"prev\">Click to view error Screenshot</span></button>");
		pw.println("<br/>");
		pw.println("<p><b><font color=\"blue\">TestCase Execution Summary</b></p>");
		/* pw.println("<tr><td style=\"text-align: left; border: 0px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">Module Name: </td><td style=\"text-align: left; border: 0px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">"
                + modulename + "</td></tr></table></br/>");
        pw.println("<table style=\"border-collapse: collapse; width: auto;\"><tr><td style=\"text-align: left; border: 0px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">Test Name: </td><td style=\"text-align: left; border: 0px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">"
                + testName + "</td></tr>");*/


		/*pw.println("<table style=\"border-collapse: collapse; width: auto;\">");
        pw.println("<thead>"
        		+ "<tr>"
        		+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:5cm; color: white; font-family: Arial;font-size:12px;\" >Business/Page Object Component Name</th>"
        		+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:5cm; color: white; font-family: Arial;font-size:12px;\" >Action/Step Name</th>"
//        		+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 25px; color: white; font-family: Georgia;\" >Total Time (ms)</th>"
        		+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:5cm; color: white; font-family: Arial;font-size:12px;\" >Status</th>"
//        		+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 25px; color: white; font-family: Georgia;\">Stack Trace | Test Parameters</th>"
        		+ "</tr>"
        		+ "</thead>");*/

		for (ISuite suite : suites)
		{
			Map<String, ISuiteResult> tests = suite.getResults(); //{ATC001_SAdv_Cart_AddSKUItemTest=[SuiteResult context=ATC001_SAdv_Cart_AddSKUItemTest], ATC002_SAdv_Cart_AddManufacturerNameTest=[SuiteResult context=ATC002_SAdv_Cart_AddManufacturerNameTest], ATC001_1_SAdv_Cart_AddSKUItemTest=[SuiteResult context=ATC001_1_SAdv_Cart_AddSKUItemTest]}
			for (ISuiteResult re : tests.values()) //[SuiteResult context=ATC001_SAdv_Cart_AddSKUItemTest]
			{
				ITestContext overview = re.getTestContext();
				if ((overview.getName()).equalsIgnoreCase(testName)) //ATC001_SAdv_Cart_AddSKUItemTest
				{

					//                    Iterator<ITestNGMethod> it = testngMethods; //[ATC001_SAdv_Cart_AddSKUItemTest.addSKUItem()[pri:0, instance:[ATC001_SAdv_Cart_AddSKUItemTest passed=0 failed=0 skipped=0]]]
					//                    while (it.hasNext())
					for (ITestNGMethod method : testngMethods) //ATC001_SAdv_Cart_AddSKUItemTest.addSKUItem()[pri:0, instance:[ATC001_SAdv_Cart_AddSKUItemTest passed=0 failed=0 skipped=0]]
					{
						//                        ITestNGMethod method = testngMethod.next(); //ATC001_SAdv_Cart_AddSKUItemTest.addSKUItem()[pri:0, instance:[ATC001_SAdv_Cart_AddSKUItemTest passed=0 failed=0 skipped=0]]
						String[] allGroups = method.getGroups(); //[Smoke, Module1]

						String methodName = "";
						String className = "";

						for (String grp : allGroups)
						{
							grp =" "+grp;
							if (grp.equalsIgnoreCase(modulename))
							{
								methodName = method.getMethodName(); 
								className = method.getTestClass().getName();
								//                                                             
								ArrayList<Set<ITestResult>> statusResult = new ArrayList<Set<ITestResult>>();
								Set<ITestResult> failedTestStatus = overview.getFailedTests().getResults(method);
								//need this line [[TestResult name=addSKUItem on [ATC001_1_SAdv_Cart_AddSKUItemTest passed=0 failed=0 skipped=0] status=FAILURE method=ATC001_1_SAdv_Cart_AddSKUItemTest.addSKUItem()[pri:0, instance:[ATC001_1_SAdv_Cart_AddSKUItemTest passed=0 failed=0 skipped=0]] output=ReportNg The Base URL:http://qa7.staplesadvantage.comis loaded]]

								if (!(failedTestStatus.isEmpty()))
								{
									statusResult.add(failedTestStatus);
								}

								Set<ITestResult> passedTestStatus = overview.getPassedTests().getResults(method);
								if (!(passedTestStatus.isEmpty()))
								{
									statusResult.add(passedTestStatus);
								}

								Set<ITestResult> skippedTestStatus = overview.getSkippedTests().getResults(method);
								if (!(skippedTestStatus.isEmpty()))
								{
									statusResult.add(skippedTestStatus);
								}

								Iterator<Set<ITestResult>> statusIterator = statusResult.iterator();

								while (statusIterator.hasNext())
								{
									Set<ITestResult> status = statusIterator.next();

									StringBuilder stackTrace;
									StringBuilder failedConf;

									Iterator<ITestResult> it2 = status.iterator();

									List<String> msgs = new ArrayList<String>();

									String executionStatus = "";

									long time_start = Long.MAX_VALUE;
									long time_end = Long.MIN_VALUE;

									Throwable exception = null;
									String screenshotFileLink = "";

									ITestResult result = null;

									while (it2.hasNext())
									{
										stackTrace = new StringBuilder();
										failedConf = new StringBuilder();

										result = it2.next();

										time_start = result.getStartMillis();
										time_end = result.getEndMillis();

										int execStatus = result.getStatus();
										if (execStatus == ITestResult.SUCCESS)
										{
											executionStatus = "PASS";
										} else if (execStatus == ITestResult.FAILURE)
										{
											executionStatus = "FAIL";
										} else if (execStatus == ITestResult.SKIP)
										{
											executionStatus = "SKIP";
										}

										if (execStatus == ITestResult.SKIP)
										{
											status = overview.getFailedConfigurations().getAllResults();
											it2 = status.iterator();
											failedConf.append("<br/>");
											while (it2.hasNext())
											{
												result = it2.next();
												failedConf.append("Failed Configuration - "
														+ result.getMethod().getMethodName());                                                
												failedConf.append("<br/>");
											}
											exception = result.getThrowable();
										} else
										{
											exception = result.getThrowable();
										}

										try
										{

											msgs = Reporter.getOutput(result);                                            
											/*
											 * if (msgs.size() == 0) { msgs =
											 * Reporter.getOutput(); }
											 */
										} catch (Exception ex)
										{
											// Log error message
										}

										/*
										 * If enable logs is false, then only
										 * take the screenshot.
										 */
										try
										{
											if ((Driver_commonObjects.applicationEnableLogInReport
													.equalsIgnoreCase("false")) && (msgs != null))
											{
												for (String line : msgs)
												{
													if (line.contains("[Console Log] Screenshot saved in"))
													{
														screenshotFileLink = line.substring(line.indexOf("in") + 3,
																line.length());
														break;
													}
												}
											}
										} catch (Exception ex)
										{
											ex.printStackTrace();
										}

										/*
										 * If enable logs is true, take the
										 * whole log along with screenshot.
										 */
										try
										{
											if ((Driver_commonObjects.applicationEnableLogInReport
													.equalsIgnoreCase("true")) && (msgs != null))
											{
												for (String line : msgs)
												{
													if (line.contains("[Console Log] Screenshot saved in"))
													{
														screenshotFileLink = line.substring(line.indexOf("in") + 3,
																line.length());
														break;
													}
												}

												if (screenshotFileLink.trim().length() != 0)
												{
													stackTrace
													.append("<br/><a target=\"_blank\" href=\""
															+ screenshotFileLink
															+ "\"><b>View Screenshot in New Window/Tab</b></a><br/><br/><img id=\"screenshot\" src='"
															+ screenshotFileLink
															+ "' height='300' width='300' border=\"1\" style=\"position: relative; left: 0px;\"/>");
												}

												for (String line : msgs)
												{
													if (!(line.contains("[Console Log] Screenshot saved in")))
													{
														stackTrace.append("<br/>" + line);
													}
												}
											}
										} catch (Exception ex)
										{
											ex.printStackTrace();
										}

										//                                        if (msgs != null)
										//                                        {
										//                                            msgs.clear();
										//                                        }

										Random randomGenerator = new Random();
										int randomInt = randomGenerator.nextInt(100000);

										//stackTraceFile = testName + "_" + modulename + "_" + methodName +"_"+ randomInt +"_trace.html";
										stackTraceFile = testName + "_" + modulename +"_trace.html";
										stackTrace.append("<br/>" + failedConf.toString());

										generateStackTraceReport(fileName, stackTraceFile, stackTrace, exception, method, nodeIp, result);

										String link = "<button onClick=\"location.href='" + stackTraceFile
												+ "'\"><span class=\"info\">" + "View StackTrace/Params"
												+ "</span></button>";

										if (executionStatus.equalsIgnoreCase("pass"))
										{
											executionStatus = "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #00CC00; font-family: Georgia;\"><b>"
													+ executionStatus + "</b></td>";
										} 
										else if (executionStatus.equalsIgnoreCase("fail"))
										{
											executionStatus = "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #CC0000; font-family: Georgia;\"><b>"
													+ executionStatus + "</b></td>";
										} 
										else if (executionStatus.equalsIgnoreCase("skip"))
										{
											executionStatus = "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #CCA300; font-family: Georgia;\"><b>"
													+ executionStatus + "</b></td>";
										} else
										{
											executionStatus = "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">"
													+ executionStatus + "</td>";
										}
										//Working Here
										genrateComponentReport(msgs, pw);
										//                                       pw.println("<tr><td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\"><b>"
										////                                                + method.getMethodName() + "</b></td>"msgs
										//                                    		   + msgs.get(0) + "</b></td>"
										//                                        		+ "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">"
										//                                                + "[Class Name] "
										//                                                + msgs.get(1)
										//                                                + "</td>"
										//                                                + "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">"
										//                                                + (time_end - time_start)
										//                                                + "</td>"
										//                                                + executionStatus
										//                                                + "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">"
										//                                                + link + "</td>");
										//                                        pw.flush();
									}
								}
							}
						}
					}
				}
			}
		}

		pw.println("</table>");
		endHtmlPage(pw);
		pw.flush();
		pw.close();
			}

	public void generateStackTraceReport(String htmlfile, String stackTraceFile, StringBuilder stackTrace,
			Throwable exception, ITestNGMethod method, String nodeIp, ITestResult result) throws IOException
			{
		final PrintWriter fw = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDir, stackTraceFile))));
		startHtmlPage(fw);

		fw.println("<button  onClick=\"location.href='" + fileName
				+ "'\"><span class=\"prev\">Back to Methodwise Execution Summary</span></button>");
		fw.println("<br/><br/><br/>");

		if (result != null)
		{
			fw.println("<fieldset><legend><font color=\"blue\"><b>Test Parameters</b></font></legend>");
			fw.println("<table style=\"border-collapse: collapse; width: auto;\">");

			Object[] params = result.getParameters();
			if (params != null)
			{
				for (int i = 0; i < params.length; i++)
				{
					fw.println("<tr><td style=\"text-align: left; border: 0px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">[Parameter "
							+ i
							+ "]</td><td style=\"text-align: left; border: 0px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">"
							+ params[i] + "</td></tr>");
				}
			}

			fw.println("</table>");
			fw.println("</fieldset>");
			fw.println("<br/>");
		}

		fw.println("<fieldset><legend><font color=\"green\"><b>Screenshot / Exception Log</b></font></legend>");
		try
		{
			if (Driver_commonObjects.applicationEnableLogInReport.equalsIgnoreCase("false"))
			{
				fw.println("<p><b>[Debug] - </b>Console logs in custom report is disabled. To view the Console logs please set the app.enable.logs.in.report as true in properties file!</p>");
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}

		// fw.println("<p><b>[Node IP] - </b>" + nodeIp + "</p>");
		fw.println(stackTrace + "<br/>");
		fw.println("</fieldset>");
		fw.println("<br/>");

		if (exception != null)
		{
			fw.println("<fieldset><legend><font color=\"red\"><b>Stack Trace</b></font></legend>");
			generateExceptionReport(exception, method, fw);
			fw.println("</fieldset>");
		}

		endHtmlPage(fw);
		fw.flush();
		fw.close();
			}

	protected void generateExceptionReport(Throwable exception, ITestNGMethod method, PrintWriter pw)
	{
		pw.flush();
		generateExceptionReport(exception, method, exception.getLocalizedMessage(), pw);
	}

	private void generateExceptionReport(Throwable exception, ITestNGMethod method, String title, PrintWriter m_out)
	{

		m_out.println("<p>" + title + "</p><p>");

		StackTraceElement[] s1 = exception.getStackTrace();
		Throwable t2 = exception.getCause();
		if (t2 == exception)
		{
			t2 = null;
		}
		int maxlines = Math.min(100, StackTraceTools.getTestRoot(s1, method));
		for (int x = 0; x <= maxlines; x++)
		{
			m_out.println((x > 0 ? "<br/>at " : "") + Utils.escapeHtml(s1[x].toString()));
		}
		if (maxlines < s1.length)
		{
			m_out.println("<br/>" + (s1.length - maxlines) + " lines not shown");
		}
		if (t2 != null)
		{
			generateExceptionReport(t2, method, "Caused by " + t2.getLocalizedMessage(), m_out);
		}
		m_out.println("</p>");
		m_out.flush();
	}

	private void generateModulesRow(PrintWriter pw, String fileName, String moduleName, int passedMethods,
			int failedMethods, int skippedMethods, int skippedConfiguration, int failedConfiguration, int totalMethods,
			String passPercentage)
	{

		String passCount = "";
		String failCount = "";
		String skipCount = "";

		if (passedMethods > 0)
		{
			passCount = "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #00CC00; font-family: Georgia;\"><b>"
					+ passedMethods + "</b></td>";
		} else
		{
			passCount = "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">"
					+ passedMethods + "</td>";
		}

		if (failedMethods > 0)
		{
			failCount = "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #CC0000; font-family: Georgia;\"><b>"
					+ failedMethods + "</b></td>";
		} else
		{
			failCount = "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">"
					+ failedMethods + "</td>";
		}

		if (skippedMethods > 0)
		{
			skipCount = "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #CCA300; font-family: Georgia;\"><b>"
					+ skippedMethods + "</b></td>";
		} else
		{
			skipCount = "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">"
					+ skippedMethods + "</td>";
		}

		pw.println("<tr><td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\"><b><a href=\""
				+ fileName
				+ "\">"
				+ moduleName
				+ "</a></b></td>"
				+ passCount
				+ failCount
				+ skipCount
				+ "<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">"
				+ totalMethods
				+ "</td><td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\"><font color=\"#A35200\"><b>"
				+ passPercentage + " %" + "</b></font></td></tr>");
		pw.flush();
	}

	private PrintWriter createWriter(String outdir) throws IOException
	{
		new File(outdir).mkdirs();
		return new PrintWriter(new BufferedWriter(
				new FileWriter(new File(outputDir, "Overall_ExecutionSummaryReport.html"))));
	}

	/** Starts HTML Stream */
	private void startHtmlPage(PrintWriter out)
	{
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		out.println("<head>");
		out.println("<title>GRASP Automation Test Results Summary</title>");
		out.println("</head>");
		out.println("<body><div style=\"margin:0 auto; padding:auto; min-height:auto; min-width: auto; height:auto;\">"
				+ "<div style=\"height:auto; background:#E5E4E2; padding:auto;box-shadow: 0 10px 6px -6px #777 \">"
				+"<table style=\"border-collapse: collapse; width: Auto;\">"
				+"<tr><td>"
				+"<img src=\"image003.png\" alt=\"\" style=\"width:auto; height:80px\">"
				+"</td><td>"
				+ "<h1 style=\"background-color: #E5E4E2; color: black; text-align: center; font-family: Georgia;\">GRASP Automation Report</h1>"
				+"</td></tr></table>"	
				//                + "<h1 style=\"background-color: #808080; color: white; text-align: center; font-family: Georgia;\">Staples Advantage - QA Automation Report</h1>"
				);
		//
		Calendar currentdate = Calendar.getInstance();
		String strdate = null;
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a z");
		strdate = formatter.format(currentdate.getTime());

		TimeZone obj = TimeZone.getTimeZone("EST");
		String strdateall = "";
		formatter.setTimeZone(obj);
		//strdateall = strdate.replaceAll(":", "  ");
		strdateall = formatter.format(currentdate.getTime());
		strdateall = strdateall.replaceAll(":", "  ");
		out.println("<b><font color=\"blue\"><div align=\"right\">Report generated on: " + strdateall + "</div><br/><b>");

		//String URL = BaseTest.getBaseURL();
		String URL = Driver_commonObjects.webdriverUrl;
		String applicationEnvironment = Driver_commonObjects.applicationEnvironment;
		String buildName = Driver_commonObjects.testLinkBuildName;
		if (URL == null)
		{
			URL = Driver_commonObjects.webdriverUrl;
		}

		/*out.println("<b><font color=\"blue\">Test Environment Details</font><br/><br/></b>");
		out.println("<table style=\"border-collapse: collapse; width: 40%;\">");

		out.println("<tr>"
				+ "<th style=\"text-align: left; background-color: #808080;border: 1px solid grey; height: 12px;width:15%; color: #FFFFFF; font-family: Arial;font-size:12px;\">Application URL: </th>"
				+ "<td style=\"text-align: left;border: 1px solid grey; height: 12px;width:25%; color: #1C1C1C; font-family: Arial;font-size:12px;\">"
				+ "<a href=\"" + URL + "\">" + URL + "</a></td></tr>"
				+ "<th style=\"text-align: left; background-color: #808080;border: 1px solid grey; height: 12px;width:15%; color: #FFFFFF; font-family: Arial;font-size:12px;\">BuildName: </th>"
				+ "<td style=\"text-align: left; border: 1px solid grey; height: 12px;width:25%; color: #1C1C1C; font-family: Arial;font-size:12px;\">"+buildName+" </td></tr>"
				+ "<th style=\"text-align: left; background-color: #808080;border: 1px solid grey; height: 12px;width:15%; color: #FFFFFF; font-family: Arial;font-size:12px;\"> Env: </th>"
				+ "<td style=\"text-align: left; border: 1px solid grey; height: 12px;width:25%; color: #1C1C1C; font-family: Arial;font-size:12px;\">"+applicationEnvironment+" </td></tr>");
		out.println("</table>");
		out.println("<br/>");
		out.flush();*/
	}

	/** Finishes HTML Stream */
	private void endHtmlPage(PrintWriter out)
	{
		//        out.println("<br/><br/><div align=\"right\"> &copy; <a href=\"http://www.mycompany.com\">2014 My Company Ltd.</a></div>");
		out.println("</body></html>");
	}

	private void genrateComponentReport(List<String> msgs, PrintWriter pw)
	{
		String ModuleName = "";
		String TestCaseNumber ="";
		String testStepComponent = "";
		String testStepStatus ="";
		String testOutPutMessage="";
		String testStepInput = "";
		String testStepOutput ="";
		String testStepExpectedResult ="";
		String businessComponentName="";
		String pageComponentStatusMessage = "";
		int count =0;
		int flag =0;
		int countFail =0;

		pw.println("<table style=\"border-collapse: collapse; \">");
		pw.println("<thead>"
				+ "<tr>"
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:7cm; color: white; font-family: Arial;font-size:12px;\" >Suite/Module Name</th>"
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:5cm; color: white; font-family: Arial;font-size:12px;\" >TetCase Name</th>"
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:5cm; color: white; font-family: Arial;font-size:12px;\" >Test Step Name</th>"                		
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:7cm; color: white; font-family: Arial;font-size:12px;\" >Input Value</th>"
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:5cm; color: white; font-family: Arial;font-size:12px;\" >Output</th>"
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:7cm; color: white; font-family: Arial;font-size:12px;\" >Expected Result</th>"
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:7cm; color: white; font-family: Arial;font-size:12px;\" >Actual Result</th>"
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:5cm; color: white; font-family: Arial;font-size:12px;\">Error Message</th>"
				+ "<th style=\"background-color: #808080; border: 1px solid grey; height: 12px; width:3cm; color: white; font-family: Arial;font-size:12px;\" >Execution Status</th>"
				+ "</tr>"
				+ "</thead>");
		for (String line : msgs)
		{   
			if(line.contains("BusinessComponent:-"))
			{    			
				businessComponentName = line.substring(19);

				pw.println("<td colspan=\"9\" style=\"background-color: #808080;\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						//    					"<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ businessComponentName + "</td></tr>");
				//    			pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">"
				//    	          		   + pageComponentName + "</td>");
				//                pw.println("<table style=\"border-collapse: collapse; width: auto;\"><tr><td style=\"text-align: left; border: 0px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\">Page Component Name: </td><td style=\"text-align: left; border: 0px solid grey; height: 25px; color: #1C1C1C; font-family: Georgia;\"><b>"
				//                        + pageComponentName + "</b></td></tr>");             
			}

			else if(line.contains("ModuleName"))
			{    			
				ModuleName = line.substring(10);            
			}    		

			else if(line.contains("TestNumber"))
			{
				TestCaseNumber =line.substring(10);

			}

			else if(line.contains("TestStepComponent"))
			{
				testStepComponent = line.substring(17);
				pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ ModuleName + "</td>");
				pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ TestCaseNumber + "</td>");
				pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ testStepComponent + "</td>");          	
			}

			else if (line.contains("TestStepExpectedResult"))
			{
				testStepExpectedResult = line.substring(24);
				pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ testStepExpectedResult + "</td>"); 
			}

			else if(line.contains("PASS_MESSAGE"))
			{
				testStepStatus = "PASS";
				testOutPutMessage = line.substring(14);
				pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ testOutPutMessage + "</td>");
				pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ "NA" + "</td>");
				pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #00CC00; font-family: Arial;font-size:12px;\">"
						+ testStepStatus + "</td></tr>");		
			}
			else if(line.contains("SKIP_MESSAGE"))
			{
				testStepStatus = "SKIPPED";
				testOutPutMessage = line.substring(14);
				pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ testOutPutMessage + "</td>");
				pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ "NA" + "</td>");
				pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #FF6600; font-family: Arial;font-size:12px;\">"
						+ testStepStatus + "</td></tr>");		
			}

			else if(line.contains("FAIL_MESSAGE"))
			{
				testStepStatus = "FAIL";
				testOutPutMessage = line.substring(14);
				pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ "NA" + "</td>");
				pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ testOutPutMessage + "</td>");
				pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #CC0000; font-family: Arial;font-size:12px;\">"
						+ testStepStatus + "</td></tr>");

			}

			else if(line.contains("TestStepInput"))
			{
				testStepInput = line.substring(15);
				pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ testStepInput + "</td>");
				//    			pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
				//    	          		   + "NA" + "</td>");
			}

			else if(line.contains("TestStepOutput"))
			{
				testStepOutput = line.substring(16);
				pw.println("<td style=\"text-align: center; border: 1px solid grey; height: 20px;color: #1C1C1C; font-family: Arial;font-size:12px;\">"
						+ testStepOutput + "</td>");    			
			}

		}    	
		pw.flush();    
	}

}
