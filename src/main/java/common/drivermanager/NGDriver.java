package common.drivermanager;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.paulhammant.ngwebdriver.NgWebDriver;

public class NGDriver {

	WebDriver driver;
	static NgWebDriver ngDriver;
	
	public static NgWebDriver getNgDriver(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		ngDriver = new NgWebDriver(js);
		return ngDriver;
	}
}
