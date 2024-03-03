package common.drivermanager;

import java.io.File;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * @author nikhil
 *
 */
public class FirefoxDriverManager extends DriverManager {


	/**
	 *Create FirefoxDriver
	 */
	@Override
	protected void createDriver() {
		//WebDriverManager.firefoxdriver().setup();
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.dir",
				System.getProperty("user.dir") + File.separator + "downloadFiles");
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"text/csv,application/java-archive, application/x-msexcel,application/excel,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/vnd.microsoft.portable-executable");
		FirefoxOptions option = new FirefoxOptions();
//		option.addArguments("--headless");
		option.setProfile(profile);
		driver = new FirefoxDriver(option);
//		log.info("Borwser launched Successfully");
		driver.manage().window().maximize();
	}

}
