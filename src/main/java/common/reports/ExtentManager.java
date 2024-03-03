/**
 * 
 */
package common.reports;

import java.io.File;
import java.util.Properties;

import org.json.simple.JSONObject;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.mongodb.MongoClientURI;

import common.util.AppConstants;
import common.util.DateUtils;
import common.util.JsonUtility;
import common.util.PropertiesReader;

/**
 * @author nikhil
 *
 */
public class ExtentManager {

	private static ExtentReports extent;
//	private static DateUtils dateUtil = new DateUtils();
	private static Properties prop = PropertiesReader.getProperty(AppConstants.CONFIG_FILE);
	private static String reportFileName = prop.getProperty("reportName") + "_"
			+ DateUtils.generateCurrentDateTimeString("dmmyyhhmmss") + ".html";
	private static String fileSeperator = System.getProperty("file.separator");
	private static String reportFilepath = System.getProperty("user.dir") + fileSeperator + "TestReport";
	private static String reportFileLocation = reportFilepath + fileSeperator + reportFileName;

	/**
	 * @return ExtentReports singleton object
	 */
	public static ExtentReports getInstance() {
		if (extent == null)
			createInstance();
		return extent;
	}

	/**
	 * Create an extent report instance
	 * 
	 * @return ExtentReports
	 */
	public static ExtentReports createInstance() {
		extent = new ExtentReports();
		if (System.getProperty("jobName") != null) {
			String projectName = System.getProperty("mailSubject");
			projectName = projectName.split("_")[0] + " " + projectName.split("_")[1];
			/*
			 * if (!System.getProperty("devUser").isEmpty() &&
			 * System.getProperty("devUser").length() > 0) { projectName = projectName + "-"
			 * + System.getProperty("devUser"); }
			 */
			extent.attachReporter(getExtentKlovReporter(projectName), getExtentSparkReporter());
			extent.setSystemInfo("url", System.getProperty("url"));
		} else {
			extent.attachReporter(getExtentSparkReporter());
		}
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		return extent;
	}

	private static ExtentSparkReporter getExtentSparkReporter() {
		String fileName = getReportPath(reportFilepath);
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(fileName);
		sparkReporter.config().setTheme(Theme.STANDARD);
		sparkReporter.config().setDocumentTitle(reportFileName);
		sparkReporter.config().setEncoding("utf-8");
		sparkReporter.config().setReportName(reportFileName);
		sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		return sparkReporter;
	}

	private static ExtentKlovReporter getExtentKlovReporter(String projectName) {
		String mongoDb = null;
		JsonUtility jsonUtility = new JsonUtility("klovConfig.json");
		String mongoServer = jsonUtility.getJsonKeyValue("mongoServer");
		String klovserver = jsonUtility.getJsonKeyValue("klovserver");
		JSONObject env = jsonUtility.getJsonObject("env");
		if (System.getProperty("env").toLowerCase().contains("stage")) {
			JSONObject testEnv = (JSONObject) env.get("test");
			mongoDb = testEnv.get("mongoDb").toString();
		}
		if (System.getProperty("env").toLowerCase().contains("prod")) {
			JSONObject prodEnv = (JSONObject) env.get("prod");
			mongoDb = prodEnv.get("mongoDb").toString();
		}

		ExtentKlovReporter klov = new ExtentKlovReporter(projectName);
		if (!System.getProperty("devUser").isEmpty() && System.getProperty("devUser").length() > 0) {
			klov.setReportName("Build# " + System.getProperty("buildNumber") + " - " + System.getProperty("devUser"));
		} else {
			klov.setReportName("Build# " + System.getProperty("buildNumber"));
		}
		MongoClientURI uri = new MongoClientURI(mongoServer);
		klov.initMongoDbConnection(uri);
		klov.setDatabaseName(mongoDb);
		klov.initKlovServerConnection(klovserver);
		return klov;
	}

	/**
	 * @param path
	 * @return
	 */
	private static String getReportPath(String path) {
		File testDirectory = new File(path);
		if (!testDirectory.exists()) {
			if (testDirectory.mkdir()) {
				System.out.println("Directory: " + path + " is created!");
				return reportFileLocation;
			} else {
				System.out.println("Failed to create directory: " + path);
				return System.getProperty("user.dir");
			}
		} else {
			System.out.println("Directory already exists: " + path);
		}
		return reportFileLocation;
	}

	public static String getReportFileName() {
		return reportFileName;
	}
}
