package common.base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.exception.FrameworkException;

public class SeleniumActions {

	WebDriver driver;
	JavascriptExecutor js;
	WebDriverWait wait;
	int timeout = 60;

	public SeleniumActions(WebDriver driver) {
		this.driver = driver;
		js = ((JavascriptExecutor) driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
	}

	public By getByType(String locatorType, String locator) {
		locatorType = locatorType.toLowerCase();
		if (locatorType == "id")
			return By.id(locator);
		else if (locatorType == "name")
			return By.name(locator);
		else if (locatorType == "xpath")
			return By.xpath(locator);
		else if (locatorType == "css")
			return By.cssSelector(locator);
		else if (locatorType == "class")
			return By.className(locator);
		else if (locatorType == "link")
			return By.linkText(locator);
		else
			return null;
	}

	public void dropDownSelectElement(WebElement element, String selectorType, Object selectorValue) {
		try {
			Select sel = new Select(element);
			selectorType = selectorType.toLowerCase();
			if (selectorType == "value") {
				sel.selectByValue(selectorValue.toString());
				Thread.sleep(1);
			} else if (selectorType == "index") {
				sel.selectByIndex(Integer.parseInt(selectorType));
				Thread.sleep(1);
			} else if (selectorType == "text") {
				sel.selectByVisibleText(selectorValue.toString());
				Thread.sleep(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void moveToElement(WebElement ele) {
		Actions act = new Actions(driver);
		act.moveToElement(ele).build().perform();
	}
	
	public void moveAndClickElementByAction(WebElement ele) {

		Actions act = new Actions(driver);
		act.moveToElement(ele).click().build().perform();

	}

	public void selectDropdownValueBySearch(List<WebElement> dropdownOptions, String value) {
		for (WebElement option : dropdownOptions) {
			if (value.equals(option.getText())) {
				option.click();
				break;
			}
		}
	}

	public void selectDropDownByText(WebElement loc, String value) {
		Select dropdown = new Select(loc);
		dropdown.selectByVisibleText(value);
	}

	public WebElement getElement(String locator, String locatorType) throws FrameworkException {
		WebElement element = null;
		By byType = getByType(locatorType, locator);
		element = driver.findElement(byType);
		if (element != null) {
			return element;
		} else {
			throw new FrameworkException("Element is null");
		}

	}

	public List<WebElement> getElements(String locator, String locatorType) throws FrameworkException {
		List<WebElement> element = null;
		By byType = getByType(locatorType, locator);
		element = driver.findElements(byType);
		if (element != null) {
			return element;
		} else {
			throw new FrameworkException("Element is null");
		}
	}

	public void clickElementByJS(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}

	public void refreshBrowserByJS() {

		try {
			js.executeScript("history.go(0)");
		} catch (Exception e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getTitleByJS() {
		try {
			String title = js.executeScript("return document.title;").toString();
			return title;
		} catch (Exception e) {
// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void removeReadonlyFromEle(WebElement ele) {
		try {
			js.executeScript("arguments[0].removeAttribute('readonly','readonly')", ele);
		} catch (Exception e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPageInnerText() {

		try {
			String pageText = js.executeScript("return document.documentElement.innerText;").toString();
			return pageText;
		} catch (Exception e) {
// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void scrollPageRight() {
		try {
			js.executeScript("window.scrollTo(document.body.scrollWidth, 0);");
		} catch (Exception e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public String getValueByJS(WebElement element) {
		try {
			return js.executeScript("return arguments[0].value;", element).toString();
		} catch (Exception e) {
// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

	}

	public void setValueByJS(WebElement element, String newValue) {
		try {
			js.executeScript("arguments[0].value='" + newValue + "';", element);
		} catch (Exception e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void switchToFrame(String frameName) {
		try {
			driver.switchTo().frame(frameName);
		} catch (Exception e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void takeScreenshotAtEndOfTest() throws IOException {
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String currentDir = System.getProperty("user.dir");
			FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
		} catch (WebDriverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void runTimeInfo(String messageType, String message) throws InterruptedException {
		try {
// Check for jQuery on the page, add it if need be
			js.executeScript("if (!window.jQuery) {"
					+ "var jquery = document.createElement('script'); jquery.type = 'text/javascript';"
					+ "jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js';"
					+ "document.getElementsByTagName('head')[0].appendChild(jquery);" + "}");
			Thread.sleep(5000);

// Use jQuery to add jquery-growl to the page
			js.executeScript("$.getScript('https://the-internet.herokuapp.com/js/vendor/jquery.growl.js')");

// Use jQuery to add jquery-growl styles to the page
			js.executeScript("$('head').append('<link rel=\"stylesheet\" "
					+ "href=\"https://the-internet.herokuapp.com/css/jquery.growl.css\" " + "type=\"text/css\" />');");
			Thread.sleep(5000);

// jquery-growl w/ no frills
			js.executeScript("$.growl({ title: 'GET', message: '/' });");
			if (messageType.equals("error")) {
				js.executeScript("$.growl.error({ title: 'ERROR', message: '" + message + "' });");
			} else if (messageType.equals("info")) {
				js.executeScript("$.growl.notice({ title: 'Notice', message: 'your notice message goes here' });");
			} else if (messageType.equals("warning")) {
				js.executeScript("$.growl.warning({ title: 'Warning!', message: 'your warning message goes here' });");
			} else
				System.out.println("no error message");
// jquery-growl w/ colorized output
			Thread.sleep(5000);
		} catch (InterruptedException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCurrentWindowHandle() {
		return driver.getWindowHandle();
	}
	
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public void switchToWindowHandle(String handle) {
		driver.switchTo().window(handle);
		System.out.println("switched to window "+handle);
	}

	public void switchWindowByTitle(String title) {
		try {
//			String currentWindow = driver.getWindowHandle(); // will keep current window to switch back
			for (String winHandle : driver.getWindowHandles()) {
				if (driver.switchTo().window(winHandle).getTitle().contains(title)) {
					System.out.println("switched to window title " + driver.getTitle());
					break;
				}
//				} else {
//					driver.switchTo().window(currentWindow);
//				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void waitUntilVisible(long time, String locator, String locatorType) {
		try {
//			System.out.println(driver + "Driver");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
			wait.until(ExpectedConditions.visibilityOfElementLocated(getByType(locatorType, locator)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void waitUntilInVisible(long time, String locator, String locatorType) {
		try {
//			System.out.println(driver + "Driver");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(getByType(locatorType, locator)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean waitUntilElementPresent(long time, String locator, String locatorType) {
		try {
//			System.out.println(driver + "Driver");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
			wait.until(ExpectedConditions.presenceOfElementLocated(getByType(locatorType, locator)));
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean waitUntilPageTitlePresent(long time, String title) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
			wait.until(ExpectedConditions.titleIs(title));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getScreenShotPath(WebDriver driver, String testCaseName) throws IOException {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String destinationFile = System.getProperty("user.dir") + "/reports/" + testCaseName + ".png";
			FileUtils.copyFile(source, new File(destinationFile));
			return destinationFile;
		} catch (WebDriverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public void uploadFile(WebElement ele, String filePath) {
		ele.sendKeys(System.getProperty("user.dir") + filePath);
	}

	public void setZoomSize(String size) {
		try {
//			System.out.println(document.body.style.zoom='"+10+"%';);
			js.executeScript("document.body.style.zoom='" + size + "%';");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void scrollVerticallyByPixel(int pixel) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,"+pixel+")", "");
	}
	
	public void scrollHorizontallyByPixel(int pixel) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy("+pixel+",0)", "");
	}

	public void clearValueFromInput(WebElement ele) {
		clickElementByJS(ele);
		ele.sendKeys(Keys.CONTROL + "a");
		ele.sendKeys(Keys.DELETE);
	}

	public void clickOnElement(WebElement ele) {
		wait.until(ExpectedConditions.elementToBeClickable(ele)).click();
	}

	public void clickOnElement(String ele_xPath) {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ele_xPath))).click();
	}

	public void enterTextOnElement(WebElement ele, String text) {
		wait.until(ExpectedConditions.visibilityOf(ele)).sendKeys(text);
	}

	public void waitForElementToDisplay(WebElement ele) {
		wait.until(ExpectedConditions.visibilityOf(ele));
	}

	public void waitForElementPresent(final By by) {
		WebDriverWait wait = (WebDriverWait) new WebDriverWait(driver, Duration.ofSeconds(timeout))
				.ignoring(StaleElementReferenceException.class);
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				WebElement element = webDriver.findElement(by);
				return element != null && element.isDisplayed();
			}
		});
	}

	public boolean retryFindElement(WebElement ele) throws InterruptedException {
		int i = 0;
		boolean isPresent = false;
		while (i < 5) {
			try {
				ele.isDisplayed();
				isPresent = true;
				break;
			} catch (StaleElementReferenceException e) {
				Thread.sleep(1000);
				i++;
			}
		}
		return isPresent;
	}

	public boolean retryFindElement(By by) throws InterruptedException {
		int i = 0;
		boolean isPresent = false;
		while (i < 5) {
			try {
				driver.findElement(by).isDisplayed();
				isPresent = true;
				break;
			} catch (StaleElementReferenceException e) {
				Thread.sleep(1000);
				i++;
			}
		}
		return isPresent;
	}

	public void waitForPageLoaded() {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		wait.until(expectation);
	}

	int i = 0;

	public void waitTillInVisibility(List<WebElement> elements) throws FrameworkException {
		try {
			wait.until(ExpectedConditions.invisibilityOfAllElements(elements));
		} catch (TimeoutException e) {
			if (i > 1) {
				throw new FrameworkException("Element is not invisible after 5 mins");
			} else {
				i++;
			}
			waitTillInVisibility(elements);
		}
	}

	public void waitForElemDiappear(String loc, String locatorTpe) throws FrameworkException {
		try {
			getElement(loc, locatorTpe);
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			return;
		}
	}

	public boolean isElementDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException | TimeoutException e) {
			return false;
		}
	}

	public boolean isElementDisplayed(String loc, String locType) throws FrameworkException {
		try {
			return getElement(loc, locType).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	public boolean isElementsDisplayed(List<WebElement> elements) {
		if (elements.size() == 1) {
			return true;
		} else {
			return false;
		}
	}

	public void clearText(WebElement ele) {
		ele.clear();
	}
	
	public void sendKeys(WebElement ele, String text) {
		ele.clear();
		ele.sendKeys(text);
	}

	public String getText(WebElement ele) {
		return ele.getText();
	}

	public String getAttributeValue(WebElement ele, String attribute) {
		return ele.getAttribute(attribute);
	}

	public void waitTillElementDisabled(WebElement ele) {
		wait.until(ExpectedConditions.attributeContains(ele, "class", "disabled"));
	}

	public void waitFOrElementToBeClickable(WebElement ele) {
		wait.until(ExpectedConditions.elementToBeClickable(ele));
	}

	public String getDropdownSelectedOption(WebElement ele) {
		Select select = new Select(ele);
		return select.getFirstSelectedOption().getText();
	}

	public void switchToDefault() {
		driver.switchTo().defaultContent();
	}

	public void switchToWindowByIndex(int index) {
		Set<String> windowHandles = driver.getWindowHandles();
		List<String> windowStrings = new ArrayList<>(windowHandles);
		String reqWindow = windowStrings.get(index);
		driver.switchTo().window(reqWindow);
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}
	
	public List<String> getDropDownOptions(WebElement dropDownEle){
		Select select = new Select(dropDownEle);
		List<String> options = new ArrayList<String>();
		List<WebElement> optionsEle =  select.getOptions();
		for(WebElement optionEle: optionsEle) {
			options.add(optionEle.getText());
		}
		return options;
	}

	public void scrollBarHorizontally(int pixel, WebElement scrollBarEle) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollBy(" + pixel + ",0)", scrollBarEle);
	}
	
	public boolean isCheckBoxChecked(WebElement ele) {
		return ele.isSelected();
	}

}
