package common.drivermanager;
/**
 * @author nikhil
 *
 */
public class DriverManagerFactory {
	
	/**
	 * @param type
	 * @return DriverManager
	 */
	public static DriverManager getManager(DriverType type) {

        DriverManager driverManager = null;

        switch (type) {
            case CHROME:
                driverManager = new ChromeDriverManager();
                break;
            case FIREFOX:
                driverManager = new FirefoxDriverManager();
                break;
		default:
			break;
        }
        return driverManager;
	}
}
