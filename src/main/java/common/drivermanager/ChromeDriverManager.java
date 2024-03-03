package common.drivermanager;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @author nikhil
 *
 */
public class ChromeDriverManager extends DriverManager {

	private ChromeOptions options;
	private String headless = System.getProperty("headless");
	private String remote = System.getProperty("remote");
	static Logger log;

	/**
	 * Create ChromeDriver
	 */
	@Override
	public void createDriver() {
		log = Logger.getLogger(ChromeDriverManager.class);
		if (remote.equalsIgnoreCase("yes")) {
			try {
				driver = new RemoteWebDriver(new URL(System.getProperty("remoteUrl")), getChromeOption());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			driver = new ChromeDriver(getChromeOption());
		}
	}

	/**
	 * @return ChromeOptions
	 */
	private ChromeOptions getChromeOption() {
		try {
			options = new ChromeOptions();
			if (headless.equalsIgnoreCase("Yes")) {
				options.addArguments("--headless");
			}
			options.addArguments("--window-size=1440x990");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--remote-allow-origins=*");
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("download.default_directory", System.getProperty("user.dir") + File.separator + "downloadFiles");
			options.setExperimentalOption("prefs", prefs);
			//WebDriverManager.chromedriver().setup();
			//options.setBrowserVersion("116");
			log.info("Initialized the chrome driver manager");
			return options;
		} catch (Exception ex) {
			log.error("Issue with getting chrome options");
			return null;
		}

	}

}
