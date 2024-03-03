package common.base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import common.drivermanager.DriverManager;
import common.drivermanager.DriverManagerFactory;
import common.drivermanager.DriverType;
import common.reports.ExtentManager;
import common.reports.ExtentTestManager;
import common.util.AppConstants;
import common.util.FileUtils;
import common.util.PropertiesReader;

/**
 * @author nikhil
 *
 */
public class BaseTest {

	public static WebDriver driver;
	public static Logger log;
	DriverManager driverManager;
	public static Properties prop;
	ITestContext context;
	ExtentReports extent;
	public ExtentTest extentTest;
	protected static String url;

	/**
	 * BaseTest constructor loads property file and DriverManager reference
	 */
	public BaseTest() {

		log = Logger.getLogger(BaseTest.class);
		prop = PropertiesReader.getProperty(AppConstants.CONFIG_FILE);

		System.setProperty("headless", prop.getProperty("headless"));
//		System.setProperty("remote", prop.getProperty("remote"));
		System.setProperty("remote", "No");
		System.setProperty("remoteUrl", prop.getProperty("remoteUrl"));
		String browser = System.getProperty("browser");
		if (browser == null) {
			browser = prop.getProperty("browser");
		}
		switch (DriverType.valueOf(browser.toUpperCase())) {
		case CHROME:
			driverManager = DriverManagerFactory.getManager(DriverType.CHROME);
			break;
		case FIREFOX:
			driverManager = DriverManagerFactory.getManager(DriverType.FIREFOX);
			break;
		default:
			break;
		}
		extent = ExtentManager.getInstance();

	}

	/**
	 * Before Class SetUp It opens application url in browser
	 */
	@BeforeSuite(groups = { "Sanity, Regression"})
	public void setUp(ITestContext context) {
		try {
			log.info("------Suite Started------: " + context.getSuite().getName());
			driver = driverManager.getDriver();
			String url = System.getProperty("url");
			BaseTest.url = System.getProperty("url");
			log.info("jenkins url parameter is " + url);
			log.info("jenkins url parameter is " + System.getProperty("email"));
			if (url == null) {
				url = prop.getProperty("url");
				BaseTest.url = prop.getProperty("url");
			}
			System.setProperty("url", url);

			driver.get(url);
			log.info("Open the url - " + url);
			configureEnv();
		} catch (Exception ex) {
			log.error("------Exception:- " + ex.getLocalizedMessage());
			ex.printStackTrace();
		}
	}

	@BeforeMethod()
	public void beforeMethod(Method method, ITestContext context) {
		context.setAttribute("driver", driver);
		ExtentTestManager.startTest(method.getName());
		extentTest = ExtentTestManager.getExtent();
		extentTest.assignCategory(context.getCurrentXmlTest().getName());
		log.info("************** Test Case Started : " + method.getName() + "**************");
	}

	//@BeforeMethod(groups = {"PartnerLogin" })
	public void beforeMethod(Method method, Object[] testData) {
		String testCase = method.getName() + "_" + testData[0];
		ExtentTestManager.startTest(testCase);
		extentTest = ExtentTestManager.getTest();
		System.out.println("==================TestCase Started " + testCase + "===============");
	}

	/**
	 * After Class quit driver
	 */
	@AfterSuite(groups = { "Sanity, Regression" })
	public void tearDown() {
		try {
			driverManager.quitDriver();
			log.info("------Suite Ended------");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Capabilities getBrowserCapabilities() {
		Capabilities browserCap = ((RemoteWebDriver) driver).getCapabilities();
		return browserCap;
	}

	private static void configureEnv() throws IOException {
		System.setProperty("saveScreenshotsToS3", prop.getProperty("saveScreenshotsToS3"));
		File screenShotFolder = new File(AppConstants.ScreenshotsFolder);
		File reportFolder = new File(AppConstants.ReportFolder);
		File downloadFolder = new File(AppConstants.DownloadFolder);
		if (!screenShotFolder.exists()) {
			screenShotFolder.mkdir();
		}
		if (!reportFolder.exists()) {
			reportFolder.mkdir();
		}
		if (!downloadFolder.exists()) {
			downloadFolder.mkdir();
		}

		try {
			if (prop.getProperty("deleteOldLogs").equalsIgnoreCase("true")) {
				try {
					FileUtils.deleteOldLogFiles();
				} catch (IOException e) {
					log.info("------Exception:- " + e.getMessage());
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			log.error("------Exception:- " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
}