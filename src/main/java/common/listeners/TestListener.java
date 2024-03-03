package common.listeners;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;
import common.base.BasePage;
import common.reports.ExtentManager;
import common.reports.ExtentTestManager;

/**
 * @author nikhil
 *
 */
public class TestListener implements ITestListener {

	static Logger log;

	public TestListener() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		System.setProperty("current.date.time", dateFormat.format(new Date()));
		File dir = new File(System.getProperty("user.dir") + File.separator + "Logs");

		if (!dir.exists()) {
			dir.mkdir();
		}
		log = Logger.getLogger(TestListener.class);
	}

	@Override
	public void onTestStart(ITestResult result) {
//		ExtentTestManager.startTest(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentTestManager.getTest().log(Status.PASS, "Test passed");
		ITestContext context = result.getTestContext();
		WebDriver driver = (WebDriver) context.getAttribute("driver");
		try {
			ExtentTestManager.getTest().addScreenCaptureFromBase64String(new BasePage(driver).getScreenShotBase64(driver),"Image");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.info("########## Test Case Passed : " + result.getMethod().getMethodName() + " ##########\n");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ExtentTestManager.getTest().log(Status.FAIL, "Exception : -" + result.getThrowable().getMessage());
		//ExtentTestManager.getTest().log(Status.FAIL, "Test Failed");
		ITestContext context = result.getTestContext();
		WebDriver driver = (WebDriver) context.getAttribute("driver");
		try {
			/*String screenShotPath = new BasePage(driver).getScreenShotPath(driver,
					result.getMethod().getMethodName() + System.currentTimeMillis());
			if (System.getProperty("saveScreenshotsToS3").equalsIgnoreCase("yes")) {
				String s3Key = System.getProperty("jobName") + "/" + System.getProperty("buildNumber")
						+ screenShotPath.replaceAll(AppConstants.USER_CURR_DIR, "");
				AWSHelper.getInstance().uploadScreenshotToS3(s3Key, screenShotPath);
				ExtentTestManager.getTest().addScreenCaptureFromPath(
						AWSHelper.getInstance().getS3Path(screenShotPath.replaceAll(AppConstants.USER_CURR_DIR, "")),
						result.getMethod().getMethodName());
			} else {
				ExtentTestManager.getTest().addScreenCaptureFromPath(screenShotPath,
						result.getMethod().getMethodName());
			}*/
			ExtentTestManager.getTest().addScreenCaptureFromBase64String(new BasePage(driver).getScreenShotBase64(driver),"Image");
			//ExtentTestManager.getTest().fail("failed", MediaEntityBuilder.createScreenCaptureFromBase64String(new BasePage(driver).getScreenShotBase64(driver)).build());
			//ExtentTestManager.getTest().addScreenCaptureFromBase64String(new BasePage(driver).getScreenShotBase64(driver));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			log.error("------Exception:- " + result.getThrowable().getMessage());
			log.error("########## Test Case Failed : " + result.getMethod().getMethodName() + " ##########\n");

		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentTestManager.getTest().log(Status.SKIP, "Test Skipped");
		log.info("------Test Case Skipped--: " + result.getTestContext().getAttribute("testCase") + " -------\n\n");
		log.info("------Exception:- " + result.getThrowable().getMessage());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStart(ITestContext context) {
		log.info("------Starting Test--: " + context.getCurrentXmlTest().getName() + " -------");
	}

	@Override
	public void onFinish(ITestContext context) {
		log.info("------End Test--: " + context.getCurrentXmlTest().getName() + " -------");
		ExtentTestManager.endTest();
		ExtentManager.getInstance().flush();
	}
}
