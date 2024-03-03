package common.util;

import java.util.Properties;

import common.reports.EmailReporter;

/**
 * @author nikhil
 */
public class AppConstants {
	public static final String CONFIG_FILE = "/config.properties";
	public static final String URL = "url";
	public static final String USER_CURR_DIR = System.getProperty("user.dir");
	public static final String TestDataFolder = "/src/test/resources/TestData/";
	public static final String TestDataWorkbook = "TestData.xlsx";
	public static final String ScreenshotsFolder = USER_CURR_DIR + "/Screenshots/";
	public static final String ReportFolder = USER_CURR_DIR + "/TestReport/";
	public static final String DownloadFolder = USER_CURR_DIR + "/downloadFiles/";
	public static final String EmailReport = getEmailReportPath();
	public static final String DB_CONFIG_FILE =USER_CURR_DIR+ "/dbConfig.json";
	public static Properties prop;

	private static String getEmailReportPath() {
		prop = EmailReporter.prop;
		System.out.println("Execution type is "+prop.getProperty("executionType"));
		String executionType = prop.getProperty("executionType");
		return executionType.equalsIgnoreCase("testng")
				? USER_CURR_DIR + "/test-output/emailable-report2.html"
				: USER_CURR_DIR + "/target/surefire-reports/emailable-report2.html";

	}
}
