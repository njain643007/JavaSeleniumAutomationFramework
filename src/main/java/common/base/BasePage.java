package common.base;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import common.exception.FrameworkException;
import common.util.AppConstants;

public class BasePage extends SeleniumActions {

	public WebDriver driver;

	String subHeadingEle = "//*[contains(text(), '%s')]";
	@FindBy(xpath = "//div[@class='datepicker' and not(contains(@style, 'none'))]//input")
	WebElement datePickerEle;

	@FindBy(xpath = "//input[@placeholder='Early']")
	WebElement dateEarlyEle;

	@FindBy(xpath = "//input[@placeholder='Continuous']")
	WebElement dateContinuosEle;

	@FindBy(xpath = "//button[text()='Apply']")
	WebElement applyButtonEle;

	@FindBy(xpath = "(//select)[1]")
	WebElement domainSelectEle;

	@FindBy(xpath = "(//select)[2]")
	WebElement transactionSelectEle;

	@FindBy(xpath = "//input[@placeholder='Search']")
	WebElement searchInputEle;

	String allColumns = "//span[@class='ag-header-cell-text']";

	String columnNameEle = "//span[text()='%s']/../..";

	String rowColEle = "//div[@row-index='%s']//div[@aria-colindex='%s' and contains(@class, 'cell')]";

	String columnMenuIconEle = "//span[text()= '%s' and contains(@class,'cell')]/../..//span[@ref='eMenu']";

	@FindBy(xpath = "//span[@aria-label='filter']")
	WebElement colFilterEle;

	@FindBy(xpath = "//div[contains(@class,'filter-item')]//div[@ref='eLabel']")
	List<WebElement> colFilterLabelEle;

	String sideBarMenuEle = "//li[@title='Configure']";

	String particularColEle = "//div[@ref='eBodyViewport']//div[@aria-colindex='%s']";

	@FindBy(xpath = "//div[text()='(Select All)']/..//input")
	WebElement filterSelectAllEle;

	@FindBy(xpath = "//input[@aria-label='Search filter values']")
	WebElement searchFilterValueEle;

	String filterLabelCheckboxEle = "//div[contains(@class, 'checkbox-label') and text()='%s']";

	@FindBy(xpath = "//*[@class='modal-title']")
	WebElement addPopupHeaderEle;

	@FindBy(xpath = "//span[@class='orange']")
	WebElement addPopupNameEle;

	@FindBy(xpath = "//button[text()='Save']")
	WebElement addPopupSaveButtonEle;

	@FindBy(xpath = "//a[contains(@class,'cancelbtn')]")
	WebElement addPopupCancelButtonEle;

	String mandatoryFieldEle = "//label[contains(text(), '%s')]/span[@class='required-field']";

	String labelCheckbox = "//label[contains(text(), '%s')]/../..//label[contains(text(),'%s') and contains(@class, 'checkbox')]";
	String selectDropdownEle = "//label[contains(text(), '%s')]/../..//option[text()='%s']/..";
	String inputTextEle = "//label[contains(text(), '%s')]/..//input";
	String minInputTextEle = "//label[contains(text(), '%s')]/..//input[@placeholder='min']";
	String maxInputTextEle = "//label[contains(text(), '%s')]/..//input[@placeholder='max']";

	String submitBtnEle = "//label[@class='active']/ancestor::div[contains(@class,'parent')]//label[text()= '%s']/../..//button[text()='Submit']";

	@FindBy(id = "name")
	WebElement nameEle;

	@FindBy(xpath = "//*[text()='Start Date']/..//input[@name='day']")
	WebElement startDateDayEle;

	@FindBy(xpath = "//*[text()='Start Date']/..//input[@name='month']")
	WebElement startDateMonthEle;

	@FindBy(xpath = "//*[text()='Start Date']/..//input[@name='year']")
	WebElement startDateYearEle;

	@FindBy(xpath = "//*[text()='End Date']/..//input[@name='day']")
	WebElement endDateDayEle;

	@FindBy(xpath = "//*[text()='End Date']/..//input[@name='month']")
	WebElement endDateMonthEle;

	@FindBy(xpath = "//*[text()='End Date']/..//input[@name='year']")
	WebElement endDateYearEle;

	@FindBy(xpath = "//a[text()='Reset']")
	WebElement resetButtonEle;

	@FindBy(xpath = "//a[@class='addlink']//img")
	WebElement addButtonEle;

	String sideBarNameEle = "//div[normalize-space(@class='sidebar2')]//a[contains(text(), '%s')]";

	@FindBy(xpath = "//a[@class='iconRuleTop'][1]")
	WebElement exportButtonEle;

	@FindBy(xpath = "//div[@class='searchbar']/input")
	WebElement searchBarInSideBarEle;

	@FindBy(xpath = "//div[contains(@class, 'ruleSearch')]/input")
	WebElement searchBarEle;

	@FindBy(xpath = "//a[text()='Zones & Slabs']")
	WebElement zoneSlabEle;

	@FindBy(xpath = "//div[@role='alert' and contains(text(), 'successfully')]")
	WebElement ruleSuceessfullyAlert;

	@FindBy(xpath = "//button[contains(@class,'active')]/abbr")
	WebElement activeDayEle;

	public BasePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}

	public boolean getSubHeadingValue(String value) {
		return waitUntilElementPresent(30, String.format(subHeadingEle, value), "xpath");
	}

	public boolean waitUntilVisibleElement(long time, String locator, String locatorType) {
		try {
			System.out.println(driver + "Driver");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
			wait.until(ExpectedConditions.visibilityOfElementLocated(getByType(locatorType, locator)));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void waitUntilInVisible(long time, String locator, String locatorType) {
		try {
			System.out.println(driver + "Driver");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(getByType(locatorType, locator)));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public boolean waitUntilElementPresent(long time, String locator, String locatorType) {
		try {
			System.out.println(driver + "Driver");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
			wait.until(ExpectedConditions.presenceOfElementLocated(getByType(locatorType, locator)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String getScreenShotPath(WebDriver driver, String testCaseName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destinationFile = AppConstants.ScreenshotsFolder + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;
	}

	public String getScreenShotBase64(WebDriver driver) throws IOException {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
	}

	public void uploadFile(WebElement ele, String filePath) {
		ele.sendKeys(System.getProperty("user.dir") + filePath);
	}

	public void clickOnSidebarMenu(String menuName) throws FrameworkException {
		WebElement ele = getElement(String.format(sideBarMenuEle, menuName), "xpath");
		clickElementByJS(ele);
	}

	public String getPageTitle() {

		return driver.getTitle();
	}

	public void selectDateRange(String startDate, String endDate) throws InterruptedException {
		clickElementByJS(datePickerEle);
		Thread.sleep(1000);
		removeReadonlyFromEle(dateEarlyEle);
		removeReadonlyFromEle(dateContinuosEle);
		Thread.sleep(1000);
		clickElementByJS(dateEarlyEle);
		dateEarlyEle.sendKeys(Keys.CONTROL + "a");
		dateEarlyEle.sendKeys(startDate);
		Thread.sleep(1000);
		clickElementByJS(dateContinuosEle);
		dateContinuosEle.sendKeys(Keys.CONTROL + "a");

		dateContinuosEle.sendKeys(endDate);
		Thread.sleep(1000);
		dateContinuosEle.sendKeys(Keys.ENTER);

		clickElementByJS(applyButtonEle);
	}

	public boolean getDateSelected(String expecteDate) throws ParseException {
		String date = datePickerEle.getAttribute("value");

		if (date.equals(expecteDate)) {
			return true;
		} else {
			return false;
		}
	}

	public void selectDomain(String value) {
		dropDownSelectElement(domainSelectEle, "text", value);
	}

	public void selectTransaction(String value) {
		dropDownSelectElement(transactionSelectEle, "text", value);
	}

	public void enterValueInSearchText(String value) {
		searchInputEle.clear();
		searchInputEle.sendKeys(value);
	}

	public void clearValueInSearchText() {
//		searchInputEle.clear();
		searchInputEle.sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
	}

	public boolean getColumnNameAvailability(String columnName) {
		try {
			getElement(String.format(columnNameEle, columnName), "xpath");
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public List<String> getAllColumnName() {
		try {
			List<String> colName = new ArrayList<String>();
			List<WebElement> columnNamesEle = getElements(allColumns, "xpath");
			for (WebElement ele : columnNamesEle) {
				colName.add(ele.getAttribute("innerText"));
			}
			return colName;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	public void sortColumn(String columnName, String type) throws InterruptedException, FrameworkException {
		WebElement ele = getElement(String.format(columnNameEle, columnName), "xpath");
		String val = ele.getAttribute("class");
		if (val.contains("none")) {
			clickElementByJS(ele);
			if (type.equalsIgnoreCase("desc")) {
				clickElementByJS(ele);
			}
		} else if (val.contains("asc")) {
			if (type.equalsIgnoreCase("desc")) {
				clickElementByJS(ele);
			}
		} else if (val.contains("desc")) {
			if (type.equalsIgnoreCase("asc")) {
				clickElementByJS(ele);
				Thread.sleep(1000);
				clickElementByJS(ele);
			}
		}
		Thread.sleep(1000);
	}

	public String getSingleColumnValue(int row_num, int col_num) {

		try {
			WebElement ele = getElement(String.format(rowColEle, row_num - 1, col_num), "xpath");
			String text = ele.getText();
			return text;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	public List<String> getParticularColumnValue(String columnName) throws FrameworkException {
		int columnNum = 0;
		for (String s : getAllColumnName()) {
			columnNum++;
			if (s.equals(columnName)) {
				break;
			}
		}
		List<WebElement> ele = getElements(String.format(particularColEle, columnNum), "xpath");
		List<String> data = new ArrayList<String>();
		for (WebElement e : ele) {
			data.add(e.getAttribute("innerText"));
		}
		return data;
	}

	public void clickOnColumnMenu(String colName) throws FrameworkException {
		WebElement ele = getElement(String.format(columnMenuIconEle, colName), "xpath");
		clickElementByJS(ele);
	}

	public void clickOnColumnFilter() {
		clickElementByJS(colFilterEle);
	}

	public void clickOnSelectAllCheckboxFromFilter() {
		clickElementByJS(filterSelectAllEle);
	}

	public void clickOnParticularFilterLabel(String name) throws FrameworkException {
		WebElement ele = getElement(String.format(filterLabelCheckboxEle, name), "xpath");
		clickElementByJS(ele);
	}

	public void enterTextInFilterSearchTextbox(String value) {
		searchFilterValueEle.sendKeys(value);
	}

	public List<String> getFilterAllLabel() {
		List<String> data = new ArrayList<String>();
		for (WebElement ele : colFilterLabelEle) {
			data.add(ele.getAttribute("innerText"));
		}
		return data;
	}

	public void clickCancelButtonOfAddPopup() {
		clickElementByJS(addPopupCancelButtonEle);
	}

	public void clickOnResetButton() {
		clickElementByJS(resetButtonEle);
	}

	public String getAddPopupHeader() {
		return addPopupHeaderEle.getAttribute("innerText");
	}

	public String getAddPopupName() {
		return addPopupNameEle.getAttribute("innerText");
	}

	public void enterName(String text) {
		nameEle.sendKeys(text);
	}

	public boolean getFieldMandatory(String fieldName) {
		return waitUntilElementPresent(10, String.format(mandatoryFieldEle, fieldName), "xpath");
	}

	public void clickCheckboxFromField(String fieldName, String checkBoxName) throws FrameworkException {
		WebElement ele = getElement(String.format(labelCheckbox, fieldName, checkBoxName), "xpath");
		clickElementByJS(ele);
		clickOnSubmitButton(fieldName);
	}

	public boolean getAddPopupHeaderAvailability() {
		return waitUntilElementPresent(10, "//*[@class='modal-title']", "xpath");
	}

	public boolean getRuleSaveAlertAvailability() {
		return waitUntilElementPresent(10, "//div[@role='alert' and contains(text(), 'successfully')]", "xpath");
	}

	public void selectDropDownFromField(String fieldName, String name) throws FrameworkException {
		WebElement ele = getElement(String.format(selectDropdownEle, fieldName, name), "xpath");
		dropDownSelectElement(ele, "text", name);
	}

	public void enterValueInInputField(String fieldName, String value) throws FrameworkException {
		WebElement ele = getElement(String.format(inputTextEle, fieldName), "xpath");
		ele.sendKeys(value);
	}

	public void clickOnSubmitButton(String name) throws FrameworkException {
		WebElement ele = getElement(String.format(submitBtnEle, name), "xpath");
		clickElementByJS(ele);
	}

	public void enterMinValueInField(String fieldName, String value) throws FrameworkException {
		WebElement ele = getElement(String.format(minInputTextEle, fieldName), "xpath");
		ele.sendKeys(value);
	}

	public void enterMaxValueInField(String fieldName, String value) throws FrameworkException {
		WebElement ele = getElement(String.format(maxInputTextEle, fieldName), "xpath");
		ele.sendKeys(value);
	}

	public void selectStartDate(String date) throws InterruptedException {
		String[] dateSplit = date.split("/");
		startDateDayEle.sendKeys(dateSplit[0]);
		startDateMonthEle.sendKeys(dateSplit[1]);
		startDateYearEle.sendKeys(dateSplit[2]);
		clickElementByJS(activeDayEle);
		Thread.sleep(2000);
	}

	public void selectEndDate(String date) {
		String[] dateSplit = date.split("/");
		endDateDayEle.sendKeys(dateSplit[0]);
		endDateMonthEle.sendKeys(dateSplit[1]);
		endDateYearEle.sendKeys(dateSplit[2]);
		clickElementByJS(activeDayEle);

	}

	public String getAddPopupSaveButtonStatus() {
		return addPopupSaveButtonEle.getAttribute("class");
	}

	public boolean getAddButtonAvailability() {
		return waitUntilElementPresent(25, "//a[@class='addlink']", "xpath");
	}

	public void clickOnAddButton() throws InterruptedException {
		Thread.sleep(2000);
		clickElementByJS(addButtonEle);
		Thread.sleep(2000);
		waitUntilElementPresent(25, "//*[@class='modal-title']", "xpath");
	}

	public boolean getNameAvailability(String name) throws FrameworkException {
		return waitUntilElementPresent(10, String.format(sideBarNameEle, name), "xpath");
	}

	public boolean isChannelTypePresent(String channelType) throws FrameworkException {
		return isElementDisplayed(String.format(sideBarNameEle, channelType), "xpath");
	}



	public void clickOnExportButton() {
		clickElementByJS(exportButtonEle);
	}



	public void enterValueInMainSearchBar(String value) {
		clearValueFromInput(searchBarEle);
		searchBarEle.sendKeys(value);
	}

	public void clickOnZoneAndSlabLink() {
		clickElementByJS(zoneSlabEle);
	}

	public void verifyBrokenLinksOnPage(String pageUrl) throws InterruptedException {
		driver.get(pageUrl);
		Thread.sleep(2000);
		SoftAssert sa = new SoftAssert();
		String url = "";
		HttpURLConnection huc = null;
		int respCode = 200;
		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("No of links on the page " + links.size());
		Iterator<WebElement> it = links.iterator();
		while (it.hasNext()) {
			System.out.println(url);
			url = it.next().getAttribute("href");
			if (url.equals("javascript:void(0)")) {
				continue;
			}
			if (url == null || url.isEmpty()) {
				System.out.println("Link " + url + " is Empty");
				continue;
			}
			try {
				huc = (HttpURLConnection) (new URL(url).openConnection());
				huc.setRequestMethod("HEAD");
				huc.connect();
				respCode = huc.getResponseCode();
				if (respCode >= 400) {
					sa.assertTrue(false);
					System.err.println(url + " is a broken link, Response code is " + respCode);
				} else {
					sa.assertTrue(true);
					// System.out.println(url + " is a valid link");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		sa.assertAll();

	}
}
