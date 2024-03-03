/**
 * 
 */
package common.reports;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

/**
 * @author nikhil
 *
 */
public class ExtentTestManager {
	static Map<Integer,ExtentTest>extentTestMap=new HashMap<Integer,ExtentTest>();
	static ExtentReports extent = ExtentManager.getInstance();
	static ExtentTest test;
	/**
	 * @return ExtentTest
	 */
	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));
	}

	/**
	 * This will flush the data 
	 */
	public static synchronized void endTest() {
		extent.flush();
	}

	/**
	 * @param testName
	 * @return ExtentTest
	 */
	public static synchronized ExtentTest startTest(String testName) {
		test = extent.createTest(testName);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
		return test;
	}
	
	public static ExtentTest getExtent() {
		return test;
	}

}
