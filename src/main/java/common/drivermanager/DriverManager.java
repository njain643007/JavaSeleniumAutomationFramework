package common.drivermanager;
import java.time.Duration;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;


/**
 * @author nikhil
 *
 */
public abstract class DriverManager {
    protected WebDriver driver;
    protected EventFiringDecorator e_driver;
    protected abstract void createDriver();
    static Logger log = Logger.getLogger(DriverManager.class);
    /**
     * @return WebDriver
     */
    public WebDriver getDriver() {
        if (null == driver) {
            createDriver();
            driverInitialSetUp();
        }
        return driver;
    }
    
    
    /**
     * close current driver instance
     */
    public void closeDriver() {
    	driver.close();
    	
    }
    
    
    /**
     * quit driver
     */
    public void quitDriver() {
        if (null != driver) {
            driver.quit();
            driver = null;
        }
    }
    
    
    /**
     * Initial setup for driver
     */
    private void driverInitialSetUp() {
    	try {
    		//e_driver = new EventFiringWebDriver(driver);
    		//eventListener = new ConsoleLogsListener();
    		//e_driver.register(eventListener);
    		//driver = e_driver;
    		driver.manage().window().maximize();
    		log.info("Maximize window size");
    		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    		log.info("Set Implicit wait for 20 sec");
    	}
    	catch(Exception ex) {
    		log.error("Issue with driver initial setup");
    	}
    }
}
