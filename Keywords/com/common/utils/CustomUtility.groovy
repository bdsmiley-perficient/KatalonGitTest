package com.common.utils

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI



import internal.GlobalVariable
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors
import java.util.List
import java.awt.TexturePaintContext.Int
import java.text.SimpleDateFormat
import java.text.StringCharacterIterator
import java.time.LocalDateTime;
import java.time.ZoneId
import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.Clipboard
import java. awt. Robot
import java.awt.event.KeyEvent
import java.awt.AWTEvent
import java.awt.Toolkit


import org.openqa.selenium.StaleElementReferenceException
import org.junit.After
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.util.KeywordUtil
import org.openqa.selenium.NoSuchWindowException
import pageObjects.NavigationBar
import pageObjects.ManageLocations_LocationDetails
import pageObjects.ManageLocations_RelatedUsers


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.lang.String

import java.text.CharacterIterator
import java.text.DateFormat
import java.text.Normalizer
import java.text.ParseException


public class CustomUtility {

	/**
	 * Check if Manage xxxx  Page is loaded
	 * @return
	 */
	@Keyword
	public static boolean isDataManagementPageLoaded(){
		WebUI.delay(5)
		TestObject isViewLinkDisplayed = findTestObject("Common Objects/Link_View")
		WebUI.waitForElementClickable(isViewLinkDisplayed, 50)
		WebUI.scrollToElement(isViewLinkDisplayed, 5)
		if(WebUI.verifyElementVisible(isViewLinkDisplayed, FailureHandling.STOP_ON_FAILURE)==false){
			WebUI.refresh()
			WebUI.waitForElementClickable(isViewLinkDisplayed, 50)
		}
		return WebUI.verifyElementVisible(isViewLinkDisplayed, FailureHandling.STOP_ON_FAILURE)
		System.out.println("Data Management page loaded")
	}

	/**
	 * Verify default search text Displays
	 */
	@Keyword
	public static void verifyDefaultSearchText(String defaultSearchText){
		String defaultSearchTextXpath = "//div[contains(@class,'searchBoxContainer')]//input"
		TestObject searchDefaultTextObject = getTestObject("xpath", defaultSearchTextXpath)
		WebUI.verifyElementAttributeValue(searchDefaultTextObject, "placeholder", defaultSearchText, 5, FailureHandling.CONTINUE_ON_FAILURE)
	}

	/**
	 * Verify heading is displayed
	 * @return
	 */
	@Keyword
	public static boolean verifyHeadingDisplayed(String headerName){
		TestObject headingObject = getTestObject("xpath", "(//div[contains(@class,'headingContainer')]//span[contains(@class,'headerTwo')])[1]")
		WebUI.waitForElementVisible(headingObject, 15,FailureHandling.CONTINUE_ON_FAILURE)
		return WebUI.verifyEqual(WebUI.getText(headingObject), headerName, FailureHandling.CONTINUE_ON_FAILURE)
	}
	/**
	 * Verify Tab is highlighted
	 */
	@Keyword
	public static void isTabHighlighted(String tabName){
		String tabXPath = "(//span[contains(text(),'%s')]/../../..)[1]"
		TestObject tabObject = getTestObject("xpath", String.format(tabXPath, tabName))
		WebUI.waitForElementVisible(tabObject, 5)
		WebUI.getAttribute(tabObject, "class").contains("tabSelected")
	}
	/**
	 * Check if specified column is displayed
	 * @param columnName
	 * @return
	 */
	@Keyword
	public static boolean verifyColumnsDisplayed(String columnName){
		String columnPath = "//div[contains(@class, 'header')]/p/span[text()='%s']"
		TestObject columnObject = getTestObject("xpath", String.format(columnPath, columnName))
		WebUI.waitForElementVisible(columnObject, 10,FailureHandling.CONTINUE_ON_FAILURE)
		return WebUI.verifyElementVisible(columnObject, FailureHandling.OPTIONAL)
	}
	/**
	 * Click on View link
	 * @param rowNumber
	 * @return
	 */
	@Keyword
	public static void clickViewLinkByRowNumber(String rowNumber){
		String viewLinkXpath = "(//*[@id='appContainer']//button[contains(text(),'View')])[%s]"
		TestObject viewLinkObject = getTestObject("xpath", String.format(viewLinkXpath, rowNumber))
		WebUI.scrollToElement(viewLinkObject,3)
		WebUI.waitForElementClickable(viewLinkObject, 5)
		clickOnObjectUsingJS(viewLinkObject)
	}
	/**
	 * Click on View link
	 */
	@Keyword
	public static void clickViewLink (){

		String viewLinkXpath = "(//div[contains(@style,'grid-area')]//button[contains(@name,'_link')])[1]"
		TestObject viewLinkObject = getTestObject("xpath", viewLinkXpath)
		WebUI.scrollToElement(viewLinkObject,3)
		clickOnObjectUsingJS(viewLinkObject)
	}

	/**
	 * Send text to object using javascript command
	 */
	@Keyword
	public static void sendTextToObjectUsingJS(TestObject objectToSend, String textToSend){
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		WebElement element = WebUiCommonHelper.findWebElement(objectToSend,5)
		executor.executeScript(String.format("arguments[0].value='%s';",textToSend), element);
		KeywordUtil.logInfo("Clicked on the object: "+element)
		//executor.executeScript("arguments[0].value='"+textToSend+"';", element);
	}


	/**
	 * Click on the object using javascript command
	 */
	@Keyword
	public static void clickOnObjectUsingJS(TestObject objectToClick){
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		WebElement element = WebUiCommonHelper.findWebElement(objectToClick,5)
		executor.executeScript("arguments[0].click();", element);
	}

	/**
	 * Scroll to web element using JS
	 * @param objectToScroll
	 */
	@Keyword
	public static void scrollToWebElementUsingJS(WebElement objectToScroll){
		WebDriver driver = DriverFactory.getWebDriver()
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].scrollIntoView(true);", objectToScroll);
	}

	/*
	 * Scroll to web element using JS
	 * @param objectToScroll
	 @Keyword
	 public static void scrollToWebElementUsingJS(WebElement objectToScroll){
	 WebDriver driver = DriverFactory.getWebDriver()
	 JavascriptExecutor executor = (JavascriptExecutor)driver;
	 executor.executeScript("arguments[0].scrollIntoView(true);", objectToScroll);
	 }
	 */

	/**
	 * Get test object
	 * @param locatorType
	 * @param locator
	 * @return
	 */
	@Keyword
	public static TestObject getTestObject(String locatorType, String locator){
		TestObject object = new TestObject()
		object.addProperty(locatorType, ConditionType.EQUALS, locator)
		return object
	}


	/**
	 * Check if refreshing wheel is no longer present on the page
	 * @return
	 */
	@Keyword
	public static boolean isLoadWheelGone(){
		TestObject loadWheelObject = getTestObject("xpath", "//div[contains(@class,'saveIndicator')]/img")
		return WebUI.verifyElementNotPresent(loadWheelObject, 100)
	}
	/**
	 * Check if checkmark "Saved" label is no longer present on the page
	 * @return
	 */
	@Keyword
	public static boolean isCheckmarkSavedLabelGone(){
		TestObject loadWheelObject = getTestObject("xpath", "//div[contains(@class, 'saveIndicator')]//span[text()='Saved']")
		return WebUI.verifyElementNotPresent(loadWheelObject, 120)
	}
	/**
	 * Get the total number of toggles displayed in the grid list
	 * @return
	 */
	@Keyword
	public static int getTotalNumberOfToggles(){
		WebDriver driver = DriverFactory.getWebDriver()
		List<WebElement> ls = driver.findElements(By.xpath("(//input[@type='checkbox'])"))
		return ls.size()-1
	}
	/**
	 * Get the total number of ACTIVATE toggles (current status is toggled off) displayed in the grid list
	 * @return
	 */
	@Keyword
	public static int getTotalNumberOfActivateToggles(){
		WebDriver driver = DriverFactory.getWebDriver()
		List<WebElement> ls = driver.findElements(By.xpath("(//div[@title='Activate']//input[@type='checkbox'])"))
		return ls.size()
	}
	/**
	 * Get the total number of Deactivate toggles (current status is toggled on) displayed in the grid list
	 * @return
	 */
	@Keyword
	public static int getTotalNumberOfDeactivateToggles(){
		WebDriver driver = DriverFactory.getWebDriver()
		List<WebElement> ls = driver.findElements(By.xpath("(//div[@title='Deactivate']//input[@type='checkbox'])"))
		return ls.size()
	}

	/**
	 * Get current toggle status
	 * @param rowNumber
	 * @return
	 */
	@Keyword
	public static String getToggleStatusValue (int rowNumber){
		String currentToggleXpath = "//div[contains(@style,'grid-area: r0-%ds / c0s / r0-%de / c0e')]/div/div[contains(@class,'toggleInput')]"
		currentToggleXpath = String.format(currentToggleXpath, rowNumber, rowNumber)
		TestObject currentToggleButtonObject = getTestObject("xpath", currentToggleXpath)
		String currentToggleClass = WebUI.getAttribute(currentToggleButtonObject, 'class')
	}
	/**
	 * Verify if the current row is in toggled off status
	 * @param rowNumber
	 * @return
	 */
	@Keyword
	public static boolean isToggleInOffStatus (){
		String currentToggleXpath = "//div[contains(@style,'grid-area: r0-%ds / c0s / r0-%de / c0e')]/div/div[contains(@class,'toggleInput')]"
		currentToggleXpath = String.format(currentToggleXpath, 1, 1)
		TestObject currentToggleButtonObject = getTestObject("xpath", currentToggleXpath)
		String currentToggleClass = WebUI.getAttribute(currentToggleButtonObject, 'class')
		return currentToggleClass.contains("toggleInputOff")
	}
	/**
	 * Verify if the current row is in toggled on status
	 * @param rowNumber
	 * @return
	 */
	@Keyword
	public static boolean isToggleInOnStatus (){
		String currentToggleXpath = "//div[contains(@style,'grid-area: r0-%ds / c0s / r0-%de / c0e')]/div/div[contains(@class,'toggleInput')]"
		currentToggleXpath = String.format(currentToggleXpath, 1, 1)
		TestObject currentToggleButtonObject = getTestObject("xpath", currentToggleXpath)
		String currentToggleClass = WebUI.getAttribute(currentToggleButtonObject, 'class')
		return currentToggleClass.contains("toggleInputOn")
	}
	/**
	 * Get current column value
	 * @param columnNumber, rowNumber
	 * @return
	 */
	@Keyword
	public static String getColumnValue (String columnNumber, int rowNumber){
		String columnXpath = "(//div[contains(@style,'grid-area: r0-%ds / c%ss / r0-%de / c%se;')]/div//span)[1]"
		columnXpath = String.format(columnXpath,rowNumber,columnNumber, rowNumber, columnNumber)
		TestObject columnObject = getTestObject("xpath", columnXpath)
		String columnValue = WebUI.getText(columnObject)
	}
	/**
	 * Get current column value for numeric input fields
	 * @param columnNumber, rowNumber
	 * @return
	 */
	@Keyword
	public static String getColumnValueForNumericInputField (String columnNumber, int rowNumber){
		String columnXpath = "//div[contains(@style,'grid-area: r0-%ds / c%ss / r0-%de / c%se;')]//input"
		columnXpath = String.format(columnXpath,rowNumber,columnNumber, rowNumber, columnNumber)
		TestObject columnObject = getTestObject("xpath", columnXpath)
		String columnValue = WebUI.getAttribute(columnObject, "value", FailureHandling.CONTINUE_ON_FAILURE)
	}

	/**
	 * Verify if records sort by ascending order by a column, only compare the first 3 records to save execution time
	 * @param columnNumber
	 * @return
	 */
	@Keyword
	public static boolean verifySortByColumnAscending(String columnNumber){

		for (int i = 2;(i<=getTotalNumberOfToggles()) && (i<=3);i++) {
			String currentValue = getColumnValue(columnNumber,i)
			String previousValue = getColumnValue(columnNumber,i-1)

			String currentToggleValue = getToggleStatusValue(i)
			String previousToggleValue = getToggleStatusValue(i-1)


			if (currentToggleValue != previousToggleValue && currentValue>=previousValue) {
				continue
			}else {
				if(currentValue<previousValue) {
					return false
				}
			}
		}
		return true
	}
	/**
	 * Verify if records sort by ascending order by a column, only compare the first 3 records to save execution time
	 * @param columnNumber
	 * @return
	 */
	@Keyword
	public static void verifySortByColumnAscendingForNumericColumns (String columnNumber){

		for (int i = 2;(i<=getTotalNumberOfToggles()) && (i<=3);i++) {
			String currentValue = getColumnValueForNumericInputField(columnNumber,i)
			double currentValueNumber = Double.parseDouble(currentValue);

			String previousValue = getColumnValueForNumericInputField(columnNumber,i-1)
			double previousValueNumber = Double.parseDouble(previousValue);
			System.out.println(currentValueNumber>=previousValueNumber)
			System.out.println(currentValueNumber.toString())
			System.out.println(previousValueNumber.toString())

			String currentToggleValue = getToggleStatusValue(i)
			String previousToggleValue = getToggleStatusValue(i-1)


			if (currentToggleValue != previousToggleValue && currentValueNumber>=previousValueNumber) {
				continue
			}else {
				if(currentValueNumber<previousValueNumber) {
					System.out.println("Sort is not working correctly for the current column, please check the Console output values")
					assert false
				}
			}
		}
		assert true
	}
	/**
	 * Verify if records sort by descending order by column and only compare the first 3 records to save execution time
	 * @param columnNumber
	 * @return
	 */
	@Keyword
	public static void verifySortByColumnDescendingForNumericColumns(String columnNumber){

		for (int i = 2;(i<=getTotalNumberOfToggles()) && (i<=3);i++) {

			String currentValue = getColumnValueForNumericInputField(columnNumber,i)
			double currentValueNumber = Double.parseDouble(currentValue);
			String previousValue = getColumnValueForNumericInputField(columnNumber,i-1)
			double previousValueNumber = Double.parseDouble(previousValue);

			String currentToggleValue = getToggleStatusValue(i)
			String previousToggleValue = getToggleStatusValue(i-1)

			if (currentToggleValue != previousToggleValue && currentValue<=previousValue) {
				continue
			}else {
				if(currentValue>previousValue) {
					assert false
				}
			}
		}
		assert true
	}

	/**
	 * Verify if records sort by descending order by column and only compare the first 3 records to save execution time
	 * @param columnNumber
	 * @return
	 */
	@Keyword
	public static boolean verifySortByColumnDescending(String columnNumber){

		for (int i = 2;(i<=getTotalNumberOfToggles()) && (i<=3);i++) {

			String currentValue = getColumnValue(columnNumber,i)
			String previousValue = getColumnValue(columnNumber,i-1)

			String currentToggleValue = getToggleStatusValue(i)
			String previousToggleValue = getToggleStatusValue(i-1)

			if (currentToggleValue != previousToggleValue && currentValue<=previousValue) {
				continue
			}else {
				if(currentValue>previousValue) {
					return false
				}
			}
		}
		return true
	}

	/**
	 * Verify that given column is sorted in ascending order - for secondary sort purpose 
	 * @param list
	 * @return
	 */
	@Keyword
	public static boolean verifyColumnSortedInAscendingOrder(List<String> list){
		List<String> sortedList = list.stream().collect(Collectors.toList())
		Collections.sort(sortedList, new Comparator<String>() {
					@Override
					public int compare(String el1, String el2) {
						return el1.toUpperCase().compareTo(el2.toUpperCase());
					}
				})
		return WebUI.verifyEqual(list, sortedList, FailureHandling.CONTINUE_ON_FAILURE)
	}
	/**
	 * Verify if Up Arrow is displayed beside a Column
	 * @param columnName
	 * @return
	 */
	@Keyword
	public static boolean isUpArrowDisplayedOnTheColumn(String columnName){
		String upArrowPath = "//span[contains(text(),'%s')]/../..//*[contains(@class, 'sortAsc')]/."
		TestObject upArrowObject = getTestObject("xpath",String.format(upArrowPath, columnName))
		WebUI.waitForElementVisible(upArrowObject, 3, FailureHandling.CONTINUE_ON_FAILURE)
		return WebUI.verifyElementVisible(upArrowObject, FailureHandling.CONTINUE_ON_FAILURE)
	}
	/**
	 * Verify if Down Arrow is displayed beside a Column
	 * @param columnName
	 * @return
	 */
	@Keyword
	public static boolean isDownArrowDisplayedOnTheColumn(String columnName){
		String downArrowPath = "//span[contains(text(),'%s')]/../..//*[contains(@class, 'sortDesc')]/."
		TestObject downArrowObject = getTestObject("xpath",String.format(downArrowPath, columnName))
		WebUI.waitForElementVisible(downArrowObject, 3, FailureHandling.CONTINUE_ON_FAILURE)
		return WebUI.verifyElementVisible(downArrowObject, FailureHandling.CONTINUE_ON_FAILURE)
	}

	/**
	 * Retrieve organization name from droppdown
	 * @return
	 */
	@Keyword
	public static String getOrganizationName (){
		String organizationNamePath = "//div[contains(@class,'dropdownContainer')]//button//span"
		TestObject organizationObject = getTestObject("xpath", organizationNamePath)
		return WebUI.getText(organizationObject)
	}
	/**
	 * Generate random number
	 * @param numOfChar
	 * @return
	 */
	@Keyword
	public static String generateNumber(int numOfChar){
		Random rand = new Random()
		StringBuilder sb = new StringBuilder()
		for(int i = 0;i<numOfChar;i++){
			sb.append(rand.nextInt(9)+1)
		}
		return sb.toString()
	}
	/**
	 * Generate random Supplier Code
	 * @param numOfChar
	 * Generate random Supplier Alias Code
	 * @return
	 */
	@Keyword
	public static String generateSupplierCode(){
		Random rand = new Random()
		StringBuilder sb = new StringBuilder()

		for(int i = 0;i<5;i++){
			sb.append(rand.nextInt(9)+1)
		}
		return sb.toString()+"-"+"TEST"
	}
	/**
	 * Generate random Ingredient Alias Code
	 * @return
	 */
	@Keyword
	public static String generateIngredientCode(){
		Random rand = new Random()
		StringBuilder sb = new StringBuilder()
		for(int i = 0;i<5;i++){
			sb.append(rand.nextInt(9)+1)
		}
		return "Automation "+sb.toString()+"Test"
	}
	/**
	 * Generate random Nutrient Alias Code
	 * @return
	 */
	@Keyword
	public static String generateNutrientCode(){
		Random rand = new Random()
		StringBuilder sb = new StringBuilder()
		for(int i = 0;i<5;i++){
			sb.append(rand.nextInt(9)+1)
		}
		return "TEST"+" "+sb.toString()
	}
	/**
	 * Get all the drop down list values
	 * @param object - element to be looked for
	 * @return list of drop-down values
	 */
	public static List<WebElement> getDropDownValues(String dropDownName){
		TestObject isDropDownDisplayed = findTestObject(dropDownName)
		WebUI.waitForElementVisible(isDropDownDisplayed, 20)
		return WebUI.findWebElements(isDropDownDisplayed, 20)
	}

	/**
	 * Get current value for the drop-down
	 * @param object - element to be looked for
	 * @return drop down selected value
	 */
	public static String getSelectedDropDownValue(String dropdDownName){
		return WebUI.getAttribute(findTestObject(dropdDownName), 'innerHTML')
	}

	public static int generateRandomRangeProvided(int num){
		Random ran = new Random()
		int randVal = ran.nextInt(num)
		return randVal
	}

	public static int generateRandomStringRangeProvided(int s){
		Random ran = new Random()
		// int randVal = ran.nex(num)
		return ran.nextInt()
	}

	/**
	 *Validation verification
	 *@param object provide TestObject value
	 *@param text message to be verified
	 */
	@Keyword
	public static boolean isErrorMsgDisplayed(String object, String text){
		WebUI.waitForElementVisible(findTestObject(object), 10)
		String expected = WebUI.getAttribute(findTestObject(object),"innerHTML")
		return WebUI.verifyEqual(text, expected)
	}

	/**
	 * Verify if message displayed in Unsaved Changes Modal
	 * @return
	 */
	@Keyword
	public static boolean verifyMessageDisplayed(String object, String message){
		TestObject messageObject = findTestObject(object)
		WebUI.waitForElementVisible(messageObject, 5)
		String expected = WebUI.getAttribute(messageObject,"innerHTML")
		return WebUI.verifyEqual(message, expected)
	}
	/**
	 * Generate random email with valid format
	 * @param numOfChar
	 * @return
	 */
	@Keyword
	public static String generateEmail(){
		StringBuilder sb = new StringBuilder();
		sb.append("TestEmail"+generateNumber(5)+"@test.com")
		return sb.toString()
	}
	/**
	 * Return generated search criteria text
	 * @return
	 */
	@Keyword
	public static String generateRandomString(){
		StringBuilder sb = new StringBuilder();
		sb.append("New Text "+generateNumber(5)+"Test")
		return sb.toString()
	}
	/**
	 * Return generated random Name
	 * @return
	 */
	@Keyword
	public static String generateRandomString_Name(){
		StringBuilder sb = new StringBuilder();
		sb.append("Automation Name "+generateNumber(5)+"Test")
		return sb.toString()
	}
	/**
	 * Return generated search criteria text for Code alias
	 * @return
	 */
	@Keyword
	public static String generateRandomString_Code(){
		StringBuilder sb = new StringBuilder();
		sb.append("Automation Code "+generateNumber(5)+"Test")
		return sb.toString()
	}
	/**
	 * Return generated search criteria text for name alias
	 * @return
	 */
	@Keyword
	public static String generateRandomString_NameAlias(){
		StringBuilder sb = new StringBuilder();
		sb.append("Name Alias"+generateNumber(5)+"Test")
		return sb.toString()
	}
	/**
	 * Return generated search criteria text for Code alias
	 * @return
	 */
	@Keyword
	public static String generateRandomString_CodeAlias(){
		StringBuilder sb = new StringBuilder();
		sb.append("Code Alias"+generateNumber(5)+"Test")
		return sb.toString()
	}
	/**
	 * Return generated text with more than 50 chars
	 * @return
	 */
	@Keyword
	public static String generateStringWithMoreThan50Chars(){
		StringBuilder sb = new StringBuilder();
		sb.append("New Text "+generateNumber(43))
		return sb.toString()
	}

	/**
	 * Return generated text with more than 250 chars
	 * @return
	 */
	@Keyword
	public static String generateStringWithMoreThan250Chars(){
		StringBuilder sb = new StringBuilder();
		sb.append("New Text "+generateNumber(250))
		return sb.toString()
	}

	/**
	 * Return generated search criteria text
	 * @return
	 */
	@Keyword
	public static String generateRandomSearchCriteria(){
		StringBuilder sb = new StringBuilder();
		sb.append("automation_"+generateNumber(3))
		return sb.toString()
	}
	/**
	 * Enter search criteria in search field
	 * @param searchCriteria
	 */
	@Keyword
	public static void enterSearchCriteriaInSearchField(String searchCriteria){
		TestObject searchTextInputObject = findTestObject("Common Objects/Input_SearchBox")
		WebUI.waitForElementClickable(searchTextInputObject, 25)
		WebUI.scrollToElement(searchTextInputObject, 5)
		WebUI.setText(searchTextInputObject, searchCriteria)
		//sendTextToObjectUsingJS(searchTextInputObject, searchCriteria)
	}

	/**
	 * Click search button
	 */
	@Keyword
	public static void clickSearchButton(){
		String searchButtonXpath = "//div[contains(@class,'searchBoxContainer')]//button"
		TestObject searchButtonObject = getTestObject("xpath", searchButtonXpath)
		WebUI.waitForElementClickable(searchButtonObject, 5)
		WebUI.click(searchButtonObject)
		WebUI.delay(5)
	}
	/**
	 * Enter search criteria in search field and hit Search by certain fields
	 * @param searchCriteria
	 */
	@Keyword
	public static void searchBySearchCriteria (String searchCriteria){
		enterSearchCriteriaInSearchField(searchCriteria)
		clickSearchButton()
	}
	/**
	 * Get list of rows in toggled off status
	 * @return
	 * @param numberOfRows to be activated
	 */
	@Keyword
	public static List<String> getListOfInactiveRows(int numberofRows){
		WebDriver driver = DriverFactory.getWebDriver()
		List<String> listOfColumnValues = new ArrayList<>()

		for (int i = 1;i<=numberofRows;i++) {
			String rowColumnNameXpath = "(((//div[@title='Activate'])[%d]/../..)/following-sibling::div)[1]//div//span"
			TestObject identifierTextObject = getTestObject("xpath", String.format(rowColumnNameXpath,i))
			String columnValue= WebUI.getText(identifierTextObject)
			listOfColumnValues.add(columnValue)
		}

		return listOfColumnValues
	}

	/**
	 * Verify No Results Found Message Displays
	 * @return
	 */
	@Keyword
	public static boolean verifyNoResultsFoundMessageDisplayed(){
		String searchCriteriaText = generateRandomSearchCriteria()
		enterSearchCriteriaInSearchField(searchCriteriaText)
		clickSearchButton()
		String noResultsFoundMessageXpath = "//span[contains(text(),'No results found for ')]"
		TestObject noResultFoundMessageObject = getTestObject("xpath", noResultsFoundMessageXpath)
		WebUI.verifyElementText(noResultFoundMessageObject, "No results found for "+"\""+searchCriteriaText+"\"", FailureHandling.CONTINUE_ON_FAILURE)
	}

	/**
	 * Verify No Results Found Message Displays
	 * @return
	 */
	@Keyword
	public static void verifyNoResultsFoundMessageDisplayed(String searchText){
		enterSearchCriteriaInSearchField(searchText)
		clickSearchButton()
		String noResultsFoundMessageXpath = "//span[contains(text(),'No results found for ')]"
		TestObject noResultFoundMessageObject = getTestObject("xpath", noResultsFoundMessageXpath)
		WebUI.verifyElementText(noResultFoundMessageObject, "No results found for \""+searchText+"\"", FailureHandling.CONTINUE_ON_FAILURE)
	}

	/**
	 * Clear search results after no results search
	 */
	@Keyword
	public static void clearSearch(){
		String searchTextInputXpath = "//div[contains(@class,'searchBoxContainer')]//input"
		TestObject searchTextInputObject = getTestObject("xpath", searchTextInputXpath)
		WebUI.sendKeys(searchTextInputObject, Keys.chord(Keys.CONTROL, 'a'))
		WebUI.sendKeys(searchTextInputObject, Keys.chord(Keys.BACK_SPACE))
		clickSearchButton()
		isRelatedTabPageLoaded()
	}


	/*
	 * Verify if the search results contains the searching criteria text
	 * @param columnNumber, searchString
	 * @return
	 */
	@Keyword
	public static boolean verifySearchResultsHaveSearchString(String columnNumber, String searchString){

		for (int i = 1;i<=getTotalNumberOfToggles() && i<=8;i++) {
			String resultsXpath = "//div[contains(@style, 'grid-area: r0-%ds / c%ss / r0-%de / c%se')]/div//span"
			TestObject resultRecordObject = getTestObject("xpath", String.format(resultsXpath,i, columnNumber,i, columnNumber ))
			//System.out.println(WebUI.getText(resultRecordObject))
			assert WebUI.getText(resultRecordObject).toLowerCase().contains(searchString.toLowerCase())==true
		}
	}
	//	/**
	//	 * Verify if the search results contains the searching criteria text
	//	 * @param columnNumber, searchString
	//	 * @return
	//	 */
	//	@Keyword
	//	public static boolean verifySearchResultsHaveSearchString (List<String> columnNumbersList, String searchString){
	//
	//		List<String> resultsTextPerRowForSearchableColumns = new ArrayList<>()
	//
	//			for (int i = 1;i<=getTotalNumberOfToggles();i++) {
	//				for(int j=0; j<columnNumbersList.size(); j++){
	//					String resultsXpath = "//div[contains(@style, 'grid-area: r0-%ds / c%ss / r0-%de / c%se')]/div//span"
	//					TestObject resultRecordObject = getTestObject("xpath", String.format(resultsXpath,i, columnNumbersList.get(j),i, columnNumbersList.get(j) ))
	//					System.out.println(WebUI.getText(resultRecordObject))
	//					resultsTextPerRowForSearchableColumns.add(WebUI.getText(resultRecordObject).toLowerCase())
	//					System.out.println("resultsTextPerRowForSearchableColumns: "+resultsTextPerRowForSearchableColumns)
	//				}
	//				for(String s : resultsTextPerRowForSearchableColumns) {
	//					System.out.println("inside for loop")
	//					if(!s.contains(searchString.toLowerCase())) {
	//						return false
	//					}
	//				}
	//				i++
	//		}
	//			return true
	//	}
	/**
	 * Verify if the +more link exists
	 * @return
	 */
	@Keyword
	public static boolean verifyMoreLinkExists (){
		try{
			String resultsXpath = "(//span[contains(@class,'moreLink')])[1]"
			TestObject moreLinkObject = getTestObject("xpath", resultsXpath)
			return WebUI.verifyElementPresent(moreLinkObject, 5, FailureHandling.OPTIONAL)
		}catch(e) {
			return false
		}
	}
	/**
	 * Click on the + sign to expand for locations to display
	 * @param columnNumber
	 *
	 */
	@Keyword
	public static void clickMoreLinkToExpand (){
		String resultsXpath = "(//span[contains(@class,'moreLink')])[1]"
		TestObject moreLinkObject = getTestObject("xpath", resultsXpath)
		if (WebUI.verifyElementPresent(moreLinkObject, 10, FailureHandling.OPTIONAL)){
			clickOnObjectUsingJS(moreLinkObject)
			WebUI.delay(2)
		}
	}
	/**
	 * Retrieve rowNumber for which user clicks on "More" link
	 * @return
	 */
	@Keyword
	public static String getMoreLinkRowNumber(){

		String  moreLinkRowDivXpath = "(//span[contains(@class,'moreLink')])[1]/../../../.."
		TestObject moreLinkRowDivObject = getTestObject("xpath", moreLinkRowDivXpath)
		String styleString = WebUI.getAttribute(moreLinkRowDivObject, "style")
		return styleString.subSequence(styleString.lastIndexOf("grid-area: r0-")+14, styleString.indexOf("s / c"))
	}
	/**
	 * Retrieve row div style attribute for which user clicks on "More" link
	 * @return
	 */
	@Keyword
	public static String getMoreLinkDivStyle(){

		String  moreLinkRowDivXpath = "(//span[contains(@class,'moreLink')])[1]/../../../.."
		TestObject moreLinkRowDivObject = getTestObject("xpath", moreLinkRowDivXpath)
		String styleString = WebUI.getAttribute(moreLinkRowDivObject, "style")
		return styleString
	}

	/**
	 * Retrieve Locations displayed after user clicks on "More" link
	 * @return
	 */
	@Keyword
	public static List<String> getRelatedLocationsExpanded(String styleString){
		// Get the style attribute for the row on which the user clicks More link
		List<String> listOfRelatedLocations = new ArrayList<>()
		//String styleString = getMoreLinkDivStyle()

		WebDriver driver = DriverFactory.getWebDriver()
		List<WebElement> ls = driver.findElements(By.xpath(String.format("//div[contains(@style,'%s')]//span/div/div",styleString)))
		for(WebElement element:ls){
			String locationName = element.getText()
			listOfRelatedLocations.add(locationName)

		}
		return listOfRelatedLocations

	}


	/**
	 * Click on the specified column to sort
	 * @param columnName
	 *
	 */
	@Keyword
	public static void clickOnColumnToSort(String columnName){

		String columnPath = "(//span[text()='%s'])[1]"
		TestObject columnObject = getTestObject("xpath", String.format(columnPath, columnName))
		WebUI.waitForElementVisible(columnObject, 3, FailureHandling.CONTINUE_ON_FAILURE)
		WebUI.click(columnObject)
		WebUI.delay(3)
	}
	/*
	 * Verify if the search results contains the searching criteria text
	 * @return
	 @Keyword
	 public static void verifySearchResultsHaveLastName(String searchCriteria_LastName){
	 WebDriver driver = DriverFactory.getWebDriver()
	 List<WebElement> ls;
	 for (int i = 1;i<=ut.getTotalNumberOfToggles();i++) {
	 ls = driver.findElements(By.xpath("//div[@style='grid-area: r0-+i+s / c3s / r0-+i+e / c3e;']/div/span"))
	 }
	 for(WebElement element:ls){
	 String recLastName = element.getText()
	 WebUI.verifyEqual(recLastName.toLowerCase().contains(searchCriteria_LastName.toLowerCase()), true)
	 }
	 }
	 /**
	 * Verify if the search results contains the searching criteria text
	 * @return
	 @Keyword
	 public static void verifySearchResultsHaveFirstName(String searchCriteria_FirstName){
	 WebDriver driver = DriverFactory.getWebDriver()
	 List<WebElement> ls
	 for (int i = 1;i<=ut.getTotalNumberOfToggles();i++) {
	 ls = driver.findElements(By.xpath("//div[@style='grid-area: r0-+i+s / c4s / r0-+i+e / c4e;']/div/span"))
	 }
	 for(WebElement element:ls){
	 String recFirstName = element.getText()
	 WebUI.verifyEqual(recFirstName.toLowerCase().contains(searchCriteria_FirstName.toLowerCase()), true)
	 }
	 }
	 **/
	/**
	 * Verify if the related tab page is loaded for Location's page, including Related Suppliers, Related Formulas, Related Ingredients, Related Usrs
	 * @return
	 */
	@Keyword
	public static boolean isRelatedTabPageLoaded(){
		WebUI.delay(5)
		TestObject resultCountObject = findTestObject("Common Objects/Text_CountNumber")
		return WebUI.waitForElementVisible(resultCountObject, 15, FailureHandling.STOP_ON_FAILURE)
	}

	/**
	 * Click on "Activate All" toggle button
	 */
	@Keyword
	public static void clickActivateAllToggle(){
		WebUI.delay(2)
		TestObject activateAllToggleObject = findTestObject("Common Objects/ToggleButton_ActivateAll")
		WebUI.waitForElementVisible(activateAllToggleObject, 5)
		WebUI.scrollToElement(activateAllToggleObject, 2)
		WebUI.click(activateAllToggleObject)
		WebUI.delay(2)
	}

	/**
	 * Verify if toggle all status is in Activated status
	 * @return
	 */
	@Keyword
	public static boolean isToggleAllInOnStatus(){
		TestObject toggleAllOnObject = findTestObject("Common Objects/ToggleButton_DeactivateAll")

		try {
			WebUI.verifyElementVisible(toggleAllOnObject,15)
			return true
		} catch (Exception e) {
			return false
		}
	}

	/**
	 * Click on "Deactivate All" toggle button
	 */
	@Keyword
	public static void clickDeactivateAllToggle(){

		TestObject deactivateAllToggleObject = findTestObject("Common Objects/ToggleButton_DeactivateAll")
		WebUI.waitForElementVisible(deactivateAllToggleObject, 15)
		WebUI.scrollToElement(deactivateAllToggleObject, 2)
		WebUI.click(deactivateAllToggleObject)

	}
	/**
	 * Verify if toggle all status is in OFF status
	 * @return
	 */
	@Keyword
	public static boolean isToggleAllInOffStatus(){
		TestObject toggleAllOffObject = findTestObject("Common Objects/ToggleButton_ActivateAll")
		//WebUI.waitForElementVisible(activateAllToggleObject, 2)
		try {
			WebUI.verifyElementVisible(toggleAllOffObject,3)
			return true
		} catch (Exception e) {
			return false
		}
	}
	/**
	 * Toggle on a number of records
	 * @param numberofRows the number of the records to be toggled on starting from the top
	 */
	@Keyword
	public static void toggleOnRecords (int numberofRows){
		if (getTotalNumberOfActivateToggles()<numberofRows){
			clickDeactivateAllToggle()
		}

		String singleToggleOnXpath = "(//div[@title='Activate']//span[contains(@class,'toggleInputSlider')])[%d]"
		for(int i = 1; i<=numberofRows;i++){
			TestObject singleToggleOnObject = getTestObject("xpath", String.format(singleToggleOnXpath, 1))
			WebUI.delay(1)
			WebUI.waitForElementVisible(singleToggleOnObject, 5)
			WebUI.scrollToElement(singleToggleOnObject, 2)
			WebUI.click(singleToggleOnObject)
		}
	}
	/**
	 * Toggle on the number of records if less than the expected number of records are toggled on
	 * @param numberofRows the number of the records to be toggled on starting from the top
	 */
	@Keyword
	public static void toggleOnRecordsIfNotEnoughToggledOn (int numberofRows){
		if(getCountNumber()<numberofRows){

			String singleToggleOnXpath = "(//div[@title='Activate']//span[contains(@class,'toggleInputSlider')])[%d]"
			for(int i = 1; i<=numberofRows && getCountNumber()<numberofRows;i++){
				TestObject singleToggleOnObject = getTestObject("xpath", String.format(singleToggleOnXpath, 1))
				WebUI.delay(1)
				WebUI.waitForElementVisible(singleToggleOnObject, 5)
				WebUI.scrollToElement(singleToggleOnObject, 2)
				WebUI.click(singleToggleOnObject)
				clickSaveChangesButton()
			}

		}

	}
	/**
	 * Toggle on the current record
	 */
	@Keyword
	public static void toggleOnCurrentRecord(){
		if (isToggleInOffStatus()==true){
			String singleToggleOnXpath = "(//div[@title='Activate']//span[contains(@class,'toggleInputSlider')])[1]"
			TestObject singleToggleOnObject = getTestObject("xpath", singleToggleOnXpath)
			WebUI.waitForElementVisible(singleToggleOnObject, 5)
			WebUI.scrollToElement(singleToggleOnObject, 2)
			WebUI.click(singleToggleOnObject)
			clickSaveChangesButton()
		}
	}
	/**
	 * Toggle on the current record
	 */
	@Keyword
	public static void toggleOnCurrentRecordWithoutSaving(){
		if (isToggleInOffStatus()==true){
			String singleToggleOnXpath = "(//div[@title='Activate']//span[contains(@class,'toggleInputSlider')])[1]"
			TestObject singleToggleOnObject = getTestObject("xpath", singleToggleOnXpath)
			WebUI.waitForElementVisible(singleToggleOnObject, 5)
			WebUI.scrollToElement(singleToggleOnObject, 2)
			WebUI.click(singleToggleOnObject)
		}
	}
	/**
	 * Toggle off the current record
	 */
	@Keyword
	public static void toggleOffCurrentRecord(){
		if (isToggleInOffStatus()!=true){
			String singleToggleOffXpath = "(//div[@title='Deactivate']//span[contains(@class,'toggleInputSlider')])[1]"
			TestObject singleToggleOffObject = getTestObject("xpath", singleToggleOffXpath)
			WebUI.waitForElementVisible(singleToggleOffObject, 5)
			WebUI.scrollToElement(singleToggleOffObject, 2)
			WebUI.click(singleToggleOffObject)
			clickSaveChangesButton()
		}
	}
	/**
	 * Toggle off the current record
	 */
	@Keyword
	public static void toggleOffCurrentRecordWithoutSaving(){
		if (isToggleInOffStatus()!=true){
			String singleToggleOffXpath = "(//div[@title='Deactivate']//span[contains(@class,'toggleInputSlider')])[1]"
			TestObject singleToggleOffObject = getTestObject("xpath", singleToggleOffXpath)
			WebUI.waitForElementVisible(singleToggleOffObject, 5)
			WebUI.scrollToElement(singleToggleOffObject, 2)
			WebUI.click(singleToggleOffObject)
		}
	}

	/**
	 * Toggle off a number of records
	 * @param totalRowNumber the number of the records to be toggled off starting from the top
	 */
	@Keyword
	public static void toggleOffRecords (int totalRowNumber){
		if (getTotalNumberOfDeactivateToggles()<totalRowNumber){
			clickActivateAllToggle()
		}
		String singleToggleOffXpath = "(//div[@title='Deactivate']//span[contains(@class,'toggleInputSlider')])[%d]"
		for(int i = 1; i<=totalRowNumber;i++){
			TestObject singleToggleOffObject = getTestObject("xpath", String.format(singleToggleOffXpath, 1))
			WebUI.delay(1)
			WebUI.waitForElementVisible(singleToggleOffObject, 15)
			WebUI.scrollToElement(singleToggleOffObject, 2)

			WebUI.click(singleToggleOffObject)
			WebUI.delay(1)
		}
	}

	/**
	 * Click on "Save Changes" button
	 */
	@Keyword
	public static void clickSaveChangesButton(){

		TestObject saveChangesObject = findTestObject("Common Objects/Button_SaveChangesEnabled")
		WebUI.waitForElementClickable(saveChangesObject,5)
		WebUI.delay(1)
		WebUI.click(saveChangesObject)
		isLoadWheelGone()
		isCheckmarkSavedLabelGone()
		WebUI.delay(2)
	}

	/**
	 * Verify Save Changes button is disabled
	 * @return
	 */
	@Keyword
	public static boolean verifySaveChangesButtonDisabled(){
		try{
			TestObject disabledSaveChangesButtonObject = findTestObject("Common Objects/Button_SaveChangesDisabled")
			return WebUI.verifyElementNotClickable(disabledSaveChangesButtonObject,FailureHandling.OPTIONAL)
		} catch(e)
		{
			return false
		}
	}

	/**
	 * Verify View Link is disabled
	 * @return
	 */
	@Keyword
	public static boolean verifyViewLinkDisabled(){
		String viewLinkXpath = "//div[contains(@style,'grid-area: r0-1s ')]//button[contains(@name,'view_link')]"
		TestObject viewLinkObject = getTestObject("xpath", String.format(viewLinkXpath))
		WebUI.scrollToElement(viewLinkObject,3)
		return WebUI.verifyElementAttributeValue(viewLinkObject, "disabled","true" ,5, FailureHandling.CONTINUE_ON_FAILURE)
		//			return WebUI.verifyElementNotClickable(viewLinkObject,FailureHandling.CONTINUE_ON_FAILURE)
	}
	/**
	 * Verify Save Changes button is enabled
	 * @return
	 */
	@Keyword
	public static boolean verifySaveChangesButtonEnabled(){
		try{
			TestObject enabledSaveChangesButtonObject = findTestObject("Common Objects/Button_SaveChangesEnabled")
			WebUI.waitForElementVisible(enabledSaveChangesButtonObject, 5)
			return WebUI.verifyElementVisible(enabledSaveChangesButtonObject, FailureHandling.OPTIONAL)
		} catch(e){
			return false
		}

	}
	/**
	 * Retrieve first row number for the record in status of "Toggled Off" 
	 * @return
	 */
	@Keyword
	public static String getRowNumberForDeactivatedRecords(){
		String  firstRowForToggledOffXpath = "(//div[@title='Activate']//input[@type='checkbox'])[1]/../../.."
		TestObject firstRowObject = getTestObject("xpath", String.format(firstRowForToggledOffXpath))
		String styleString = WebUI.getAttribute(firstRowObject, "style")
		String firstRowNumberForDeactivated = styleString.subSequence(styleString.lastIndexOf("grid-area: r0-")+14, styleString.indexOf("s / "))
		return firstRowNumberForDeactivated
	}

	/**
	 * Check if the next page exists in the grid list
	 */
	@Keyword
	public static TestObject isNextPageExist (){
		try {
			TestObject nextPageObject = findTestObject("Common Objects/Button_NextPage")
			if (WebUI.verifyElementPresent(nextPageObject, 3, FailureHandling.OPTIONAL)==true){
				return nextPageObject
			}
		}catch(e)
		{
			KeywordUtil.logInfo("No next page exists in the current page")
			return null
		}
	}

	/**
	 * Check if the previous page exists in the grid list
	 */
	@Keyword
	public static TestObject isPreviousPageExist (){
		try{
			TestObject previousPageObject = findTestObject("Common Objects/Button_PreviousPage")
			WebUI.waitForElementPresent(previousPageObject, 5)

			return previousPageObject
		}catch(e){
			KeywordUtil.logInfo("No Previous page exists in the current page")
			return null
		}
	}
	/**
	 * Move to the next page
	 *
	 */
	@Keyword
	public static void clickOnNextPage(){
		TestObject nextPage = isNextPageExist()
		if(!nextPage.equals(null)){
			WebUI.scrollToElement(nextPage, 4)
			WebUI.click(nextPage)
			WebUI.delay(3)
			TestObject paginationLink = findTestObject('Common Objects/Button_PreviousPage')
			WebUI.waitForElementVisible(paginationLink, 30) // set ingredient, nutrient pages take time to load

			//WebUI.delay(5)

		}
	}
	/**
	 * Move to the previous page
	 */
	@Keyword
	public static void clickOnPreviousPage(){
		TestObject previousPage = isPreviousPageExist()
		if(!previousPage.equals(null)){
			WebUI.scrollToElement(previousPage, 10)

			WebUI.click(previousPage)
			WebUI.delay(3)
		}
	}
	/**
	 * Verify Large Pagination is display 50 records per page 
	 * @return boolean
	 */
	@Keyword
	public static boolean verifyLargePaginationDisplays50PerPage (){
		try{
			TestObject totalRecoCountObject = findTestObject("Common Objects/Text_Pagination_TotalNumberOfRecords")
			String totalRecoCountText = WebUI.getText(totalRecoCountObject)
			System.out.println(totalRecoCountText)
			int totalRecoNumber = Integer.parseInt(totalRecoCountText)

			if(totalRecoNumber<=50){
				TestObject disabledNextPageObjet = findTestObject("Common Objects/Button_NextPageDisabled")
				WebUI.waitForElementVisible(disabledNextPageObjet, 10)
				WebUI.scrollToElement(disabledNextPageObjet, 15)
				return WebUI.verifyEqual(WebUI.verifyElementVisible(disabledNextPageObjet), true, FailureHandling.CONTINUE_ON_FAILURE)
			} else
			{
				TestObject activeNextPageObjet = findTestObject("Common Objects/Button_NextPage")
				WebUI.waitForElementVisible(activeNextPageObjet, 10)
				WebUI.scrollToElement(activeNextPageObjet, 15)
				return WebUI.verifyElementVisible(activeNextPageObjet, FailureHandling.CONTINUE_ON_FAILURE)
			}
		}catch(e){
			return false
		}
	}
	/**
	 * Verify if the next page is displayed
	 * @return
	 */
	@Keyword
	public static boolean isNextPageDisplayed (){
		TestObject recoCountFromToObject = findTestObject ("Common Objects/Text_Pagination_FromToNumber")
		WebUI.waitForElementVisible(recoCountFromToObject, 10)
		WebUI.scrollToElement(recoCountFromToObject, 3)
		String recoCountFromToText = WebUI.getText(recoCountFromToObject)
		return WebUI.verifyEqual(recoCountFromToText.substring(0, 2), "51", FailureHandling.CONTINUE_ON_FAILURE)

	}
	/**
	 * Get the first word of a string 
	 * @return
	 */
	@Keyword
	public static String getFirstWord (String text){
		int index = text.indexOf(' ');
		if (index > -1) { // Check if there is more than one word.
			return text.substring(0, index).trim(); // Extract first word.
		} else {
			return text; // Text is the first word itself.
		}
	}
	/**
	 * Get the last word of a string
	 * @return
	 */
	@Keyword
	public static String getLastWord (String text){
		int index = text.indexOf(' ');
		if (index > -1) { // Check if there is more than one word.
			String[] parts = text.split(" ");
			String lastWord = parts[parts.length - 1];
			return lastWord
		} else {
			return text; // Text is the first word itself.
		}
	}

	/**
	 * Verify if the next page is displayed
	 * @return
	 */
	@Keyword
	public static boolean isPreviousPageDisplayed (){
		TestObject recoCountFromToObject = findTestObject ("Common Objects/Text_Pagination_FromToNumber")
		WebUI.waitForElementVisible(recoCountFromToObject, 10)
		WebUI.scrollToElement(recoCountFromToObject, 3)
		String recoCountFromToText = WebUI.getText(recoCountFromToObject)
		return WebUI.verifyEqual(recoCountFromToText.substring(0, 4), "1-50", FailureHandling.CONTINUE_ON_FAILURE)

	}
	/**
	 * Retrieve row identifier for the location based on Location Name: Related Locations tab for Users, Suppliers etc
	 * @return
	 */
	@Keyword
	public static String getRowIdnetifierForLocation(String locationName){

		String  locationDivXpath = "(//span[text()='%s'])/../../../.."
		TestObject locationDivObject = getTestObject("xpath", String.format(locationDivXpath,locationName))
		String styleString = WebUI.getAttribute(locationDivObject, "style")
		System.out.println(styleString)
		return styleString.subSequence(styleString.indexOf("grid-area: r"), styleString.indexOf("s / c"))
	}
	/**
	 * Retrieve Name based on Location Name for Related Locations tab for Users, Suppliers etc
	 * @param locationName
	 * @return
	 */
	@Keyword
	public static String getNameTextFromRelatedLocations(String locationName){
		String rowNumber = getRowIdnetifierForLocation(locationName)
		String nameXpath = "//div[contains(@style, '%s') and contains(@style, 'c0e')]//span[text()='%s']"
		TestObject nameObject = getTestObject("xpath", String.format(nameXpath, rowNumber,locationName))
		WebUI.waitForElementVisible(nameObject, 15)
		return WebUI.getText(nameObject)
	}
	/**
	 * Retrieve row identifier for the first location with View link for Related Locaitons grid list 
	 * @return
	 */
	@Keyword
	public static String getFirstLocationNameDivIdnetifier(){

		String  viewDivXpath = "((//button[text()='View'])/../../..)[1]"
		TestObject viewDivObject = getTestObject("xpath", String.format(viewDivXpath))
		String styleString = WebUI.getAttribute(viewDivObject, "style")
		System.out.println(styleString)
		String viewDivStyle = styleString.subSequence(styleString.indexOf("grid-area: r"), styleString.indexOf("s / r"))
		// "0" is the column sequence of location name
		return viewDivStyle.substring(0, viewDivStyle.length() - 1) + "0"
	}
	/**
	 * Retrieve first location's name for which View link is displayed 
	 * @return
	 */
	@Keyword
	public static String getFirstLocationNameWithViewLink(){
		String locationNameDiv = getFirstLocationNameDivIdnetifier()
		String nameXpath = "//div[contains(@style, '%s')]//span"
		TestObject nameObject = getTestObject("xpath", String.format(nameXpath,locationNameDiv))
		WebUI.waitForElementVisible(nameObject, 15)
		return WebUI.getText(nameObject)
	}

	/**
	 * Verify if all toggle buttons are in "ON" status after Activate All and click Save Changes, currently only check two pages to save execution time
	 * @return
	 */
	@Keyword
	public static boolean verifyAllTogglesActivated(){
		int totalToggleOffNumber = getTotalNumberOfDeactivateToggles()
		TestObject nextPage = isNextPageExist()
		if (totalToggleOffNumber>=1){
			return false
		}else{
			for (int pageNumber = 1;!nextPage.equals(null) && pageNumber<3; pageNumber++){
				WebUI.scrollToElement(nextPage, 4)
				WebUI.click(nextPage)
				WebUI.delay(3)
				totalToggleOffNumber=getTotalNumberOfDeactivateToggles()
				if (totalToggleOffNumber>=1) {
					return false
				}
			}
			return true
		}
	}

	/**
	 * Verify if all toggle buttons are in "OFF" status after Activate All and click Save Changes, only check at most two pages considering the execution time
	 * @return
	 */
	@Keyword
	public static boolean verifyAllTogglesDeactivated(){
		int totalToggleOnNumber = getTotalNumberOfActivateToggles()
		TestObject nextPage = isNextPageExist()

		if (totalToggleOnNumber>=1){
			return false
		}else{
			for (int pageNumber = 1;!nextPage.equals(null) && pageNumber<3; pageNumber++){
				WebUI.scrollToElement(nextPage, 4)
				WebUI.click(nextPage)
				WebUI.delay(3)
				totalToggleOnNumber=getTotalNumberOfDeactivateToggles()
				if (totalToggleOnNumber>=1) {
					return false
				}

			}
			return true
		}
	}

	/**
	 * Verify Activate All toggle button is not clickable
	 */
	@Keyword
	public static boolean verifyActivateAllToggleReadonly(){
		WebUI.delay(2)
		String activateAllXpath = "//div[@title='Activate all' and contains(@class, 'toggleInput')]//input"
		TestObject activateAllToggleObject = getTestObject("xpath", activateAllXpath)
		WebUI.waitForElementVisible(activateAllToggleObject, 5)
		return WebUI.verifyElementAttributeValue(activateAllToggleObject, "disabled","true" ,5, FailureHandling.CONTINUE_ON_FAILURE)
	}
	/**
	 * Verify toggles on each row are NOT clickable for View permission user, only checked the first five records
	 */
	@Keyword
	public static void verifyToggleButtonsReadonly (){
		String singleToggleXpath = "(//input[contains(@type,'checkbox')])[%d]"
		for(int i = 2; i<=5;i++){
			TestObject singleToggleObject = getTestObject("xpath", String.format(singleToggleXpath, 1))
			WebUI.delay(1)
			WebUI.waitForElementVisible(singleToggleObject, 5)
			WebUI.scrollToElement(singleToggleObject, 2)
			WebUI.verifyElementAttributeValue(singleToggleObject, "disabled","true" ,5, FailureHandling.CONTINUE_ON_FAILURE)
		}
	}

	@Keyword
	public static int getCountNumber(){
		TestObject currentCountObject = findTestObject("Common Objects/Text_CountNextToHeader")
		WebUI.waitForElementVisible(currentCountObject, 15, FailureHandling.CONTINUE_ON_FAILURE)
		String countNumberText = WebUI.getText(currentCountObject)
		return Integer.parseInt(countNumberText)
	}
	/*
	 * Verify if count equals to the total number of records
	 */
	@Keyword
	public static void verifyCountEqualTotalNumber(){
		TestObject totalNumberObject = findTestObject("Common Objects/Text_Pagination_TotalNumberOfRecords")
		String totalNumberText =  WebUI.getText(totalNumberObject)
		int countNumber = getCountNumber()
		WebUI.verifyEqual(Integer.parseInt(totalNumberText), countNumber)

	}

	/**
	 * Get total number of records
	 * @return 
	 */
	@Keyword
	public static int getTotalNumberOfRecords(){
		TestObject totalNumberObject = findTestObject("Common Objects/Text_Pagination_TotalNumberOfRecords")
		String totalNumber= WebUI.getText(totalNumberObject, FailureHandling.CONTINUE_ON_FAILURE)
		return Integer.parseInt(totalNumber)

	}
	/**
	 * Verify if count equals to zero
	 */
	@Keyword
	public static void verifyCountEqualToZero(){
		int countNumber = getCountNumber()
		WebUI.verifyEqual(countNumber, 0)

	}

	/**
	 * Verify if the dialog is displayed
	 */
	@Keyword
	public static void isDialogDisplayed (){
		TestObject dialogObject = findTestObject("Common Objects/PopupWindow_Dialog")
		WebUI.waitForElementVisible(dialogObject, 3)
	}
	/**
	 * Verify if message displayed in Unsaved Changes Modal
	 * @return
	 */
	@Keyword
	public static boolean verifyMessageInUnsavedChangesModal(){
		TestObject messageObject = findTestObject("Common Objects/Modal Objects/Text_Message")
		WebUI.waitForElementVisible(messageObject, 5)
		return WebUI.verifyElementVisible(messageObject)
	}
	/**
	 * Verify if Modal Popup Window closed, for example Unsaved Changed Modal, Invite User Modal etc
	 * @return
	 */
	@Keyword
	public static boolean isModalClosed(){
		TestObject modalObject = findTestObject("Common Objects/Modal Objects/Modal_PopupWindow")
		return WebUI.verifyElementNotPresent(modalObject, 5)
	}
	/**
	 * Verify if Modal Popup Window is displayed, for example Unsaved Changed Modal, Invite User Modal etc
	 * @return
	 */
	@Keyword
	public static boolean isModalDisplayed(){
		TestObject modalObject = findTestObject("Common Objects/Modal Objects/Modal_PopupWindow")
		return WebUI.verifyElementPresent(modalObject, 5)
	}
	/**
	 * Click on the "X" button to close the Unsaved Changes Modal
	 */
	@Keyword
	public static void clickXButton(){
		TestObject xButtonObject = findTestObject("Common Objects/Modal Objects/Button_X")
		WebUI.waitForElementClickable(xButtonObject, 5)
		WebUI.click(xButtonObject)
	}
	/**
	 * Click on the "Cancel" button to close the popup Modal
	 */
	@Keyword
	public static void clickCancelButton(){
		TestObject cancelButtonObject = findTestObject("Common Objects/Modal Objects/Button_Cancel")
		WebUI.waitForElementClickable(cancelButtonObject, 10)
		WebUI.click(cancelButtonObject)
	}
	/**
	 * Click on the "Yes, Exit" button to close the Unsaved Changes Modal
	 */
	@Keyword
	public static void clickYesExitButton(){
		TestObject yesExitButtonObject = findTestObject("Common Objects/Modal Objects/Button_YesExit")
		WebUI.waitForElementClickable(yesExitButtonObject, 10)
		WebUI.click(yesExitButtonObject)
	}

	/**
	 * Verify all selected toggle button stays as toggled on
	 * @param recordColumnValues
	 * @return
	 */
	@Keyword
	public static boolean isToggleOnForSelectedRows (List<String> recordColumnValues){

		String currentColumnXpath = "//span[text()='%s']/../.."
		for (int i= 0;i<recordColumnValues.size();i++){
			currentColumnXpath = String.format(currentColumnXpath, recordColumnValues.get(i))
			TestObject currentColumnDivObject = getTestObject("xpath", currentColumnXpath)
			String styleString = WebUI.getAttribute(currentColumnDivObject, "style")
			String toggleStyleRowNumber = styleString.subSequence(styleString.lastIndexOf("grid-area:"), styleString.indexOf("s / "))
			String toggleXpath = "//div[contains(@style,'%s')]//div[contains(@class,'toggleInput')]"
			TestObject toggleObject =  getTestObject("xpath", String.format(toggleXpath,toggleStyleRowNumber))
			if (WebUI.getAttribute(toggleObject, "class").contains("toggleInputOff")==true)
			{
				return false

			}
		}
		return true
	}


	/**
	 * Set up a supplier to be toggled on with more than xxx locations
	 * @param supplierName, numberOfLocations
	 */
	@Keyword
	public static void getSupplierForMultipleLocations (String supplierCode){
		searchBySearchCriteria(supplierCode)
		// Validate if it has more than 4 locations related
		if (verifyMoreLinkExists()==false){
			clickActivateAllToggle()
			clickSaveChangesButton()
			// Click Data -> Locations from Left Nav
			NavigationBar.navigateToDataLocations()
			// Wait for Manage Locations page loaded
			isDataManagementPageLoaded()
			// Toggle on the supplier with 4 Locations.
			for (int i=1; i<=4; i++){
				String viewLinkXpath = "(//div[contains(@style, 'c7s')]//button[text()='View'])[%d]"
				TestObject viewLinkObject = getTestObject("xpath", String.format(viewLinkXpath,i))
				WebUI.click(viewLinkObject)
				ManageLocations_LocationDetails.clickRelatedSuppliersTab()
				searchBySearchCriteria(supplierCode)
				clickActivateAllToggle()
				clickSaveChangesButton()
				NavigationBar.navigateToDataLocations()
				isDataManagementPageLoaded()
			}
			// Go back to the Manage Suppliers page
			NavigationBar.navigateToDataSuppliers()
			// Wait for the Manage Suppliers Page to load
			isDataManagementPageLoaded()
			// Search for the supplier
			searchBySearchCriteria(supplierCode)
		}


	}


	/**
	 Verify if Unsaved Changes Modal displays
	 * @return
	 * */
	@Keyword
	public static boolean verifyUnsavedChangesModalDisplayed(){
		TestObject unsavedChangesModalObject = findTestObject("Common Objects/Modal Objects/Button_YesExit")
		WebUI.waitForElementVisible(unsavedChangesModalObject, 5)
		return WebUI.verifyElementVisible(unsavedChangesModalObject)
	}
	/*
	 * Get the current login user's name
	 * @return
	 */
	@Keyword
	public static void clickProfileIcon(){

		TestObject profileIconObject = findTestObject("Header Objects/Button_Profile")
		WebUI.waitForElementVisible(profileIconObject, 2)
		WebUI.scrollToElement(profileIconObject, 5)
		WebUI.click(profileIconObject)
		WebUI.delay(1)
	}
	/*
	 * Get the current login user's name
	 * @return
	 */
	@Keyword
	public static String getLoginUserName(){
		clickProfileIcon()
		TestObject loginUserNameObject = findTestObject("Header Objects/Profile_Name")
		WebUI.waitForElementVisible(loginUserNameObject, 2)
		return WebUI.getText(loginUserNameObject)

	}
	/*
	 * Click Logout
	 */
	@Keyword
	public static void clickLogout(){
		TestObject logoutLinkObject = findTestObject("Header Objects/Link_Logout")
		WebUI.waitForElementVisible(logoutLinkObject, 2)
		WebUI.click(logoutLinkObject)

	}

	/**
	 * Clear search results after no results search
	 */
	@Keyword
	public static void clearAliasName(){
		TestObject aliasNameInputTextboxObject = findTestObject("Common Objects/Input_NameAlias")
		WebUI.waitForElementVisible(aliasNameInputTextboxObject , 5)
		WebUI.sendKeys(aliasNameInputTextboxObject, Keys.chord(Keys.CONTROL, 'a'))
		WebUI.sendKeys(aliasNameInputTextboxObject, Keys.chord(Keys.BACK_SPACE))
	}

	/**
	 * Enter Alias Name
	 * @param newAliasName
	 */
	@Keyword
	public static void updateAliasName(String newAliasName){
		TestObject aliasNameInputTextboxObject = findTestObject("Common Objects/Input_NameAlias")
		WebUI.waitForElementVisible(aliasNameInputTextboxObject , 5)
		WebUI.setText(aliasNameInputTextboxObject, newAliasName)
	}
	/**
	 * Enter Code Alias
	 * @param newCodeAlias
	 */
	@Keyword
	public static void updateCodeAlias (String newCodeAlias){
		TestObject codeAliasInputTextboxObject = findTestObject("Common Objects/Input_CodeAlias")
		WebUI.waitForElementVisible(codeAliasInputTextboxObject , 5)
		WebUI.setText(codeAliasInputTextboxObject, newCodeAlias)
	}
	/**
	 * Enter User Added Description
	 * @param newUserAddedDesc
	 */
	@Keyword
	public static void updateUserAddedDescription (String newUserAddedDesc){
		TestObject userAdddDescriptionObject = findTestObject("Common Objects/Input_UserAddedDescription")
		WebUI.waitForElementVisible(userAdddDescriptionObject , 5)
		WebUI.setText(userAdddDescriptionObject, newUserAddedDesc)
	}
	/**
	 * Get Alias Name
	 * @return
	 */
	@Keyword
	public static String getAliasName(){
		TestObject aliasNameInputTextboxObject = findTestObject("Common Objects/Input_NameAlias")
		WebUI.waitForElementVisible(aliasNameInputTextboxObject , 5)
		return WebUI.getAttribute(aliasNameInputTextboxObject, "value", FailureHandling.CONTINUE_ON_FAILURE)
	}
	/**
	 * Edit Details tab page with new Supplier Name Alias and Code Alias and hit Save Changes button
	 * @param newAliasName
	 * @param newCodeAlias
	 */
	@Keyword
	public static void updateDetailsPage(String newAliasName, String newCodeAlias){
		updateAliasName(newAliasName)
		updateCodeAlias(newCodeAlias)
		clickSaveChangesButton()
	}

	/**
	 * Check if Error Message is displayed when user enters more than 50 chars for Name Alias
	 *
	 */
	@Keyword
	public static void isErrorMessageDisplayedBelowNameAlias (){
		TestObject errorMessageObject = findTestObject("Common Objects/Message_ErrorMessageBelowNameAlias")
		WebUI.waitForElementVisible(errorMessageObject, 20)
		WebUI.verifyElementVisible(errorMessageObject, FailureHandling.CONTINUE_ON_FAILURE)
	}
	/**
	 * Check if Error Message is displayed when user enters more than 50 chars for Name Alias - Pages: Calc Model Details tab 
	 *
	 */
	@Keyword
	public static void isErrorMessageDisplayedBelowNameAliasForDetailsPageWithoutCodeField (){
		TestObject errorMessageObject = findTestObject("Common Objects/Message_ErrorMessageBelowNameAlias_NoCodeDetails")
		WebUI.waitForElementVisible(errorMessageObject, 20)
		WebUI.verifyElementVisible(errorMessageObject, FailureHandling.CONTINUE_ON_FAILURE)
	}
	/**
	 * Check if Error Message is displayed when user enters more than 50 chars for Code Alias
	 *
	 */
	@Keyword
	public static void isErrorMessageDisplayedBelowCodeAlias (){
		TestObject errorMessageObject = findTestObject("Common Objects/Message_ErrorMessageBelowCodeAlias")
		WebUI.waitForElementVisible(errorMessageObject, 20)
		WebUI.verifyElementVisible(errorMessageObject, FailureHandling.CONTINUE_ON_FAILURE)
	}
	/**
	 * Check if Error Message is displayed when user enters more than 250 chars for User Added Description
	 *
	 */
	@Keyword
	public static void isErrorMessageDisplayedBelowUserAddedDescription (){
		TestObject errorMessageObject = findTestObject("Common Objects/Message_ErrorMessageBelowUserAddedDescription")
		WebUI.waitForElementVisible(errorMessageObject, 20)
		WebUI.verifyElementVisible(errorMessageObject, FailureHandling.CONTINUE_ON_FAILURE)
	}
	/**
	 * Clear input for Name Alias, Code Alias and User Added Desc
	 */
	@Keyword
	public static void clearInput(){
		TestObject aliasNameInputTextboxObject = findTestObject("Common Objects/Input_NameAlias")
		WebUI.waitForElementVisible(aliasNameInputTextboxObject , 5)
		TestObject codeAliasInputTextboxObject = findTestObject("Common Objects/Input_CodeAlias")
		WebUI.waitForElementVisible(codeAliasInputTextboxObject , 5)
		TestObject userAddedDescInputTextboxObject = findTestObject("Common Objects/Input_UserAddedDescription")
		WebUI.waitForElementVisible(userAddedDescInputTextboxObject , 5)
		WebUI.sendKeys(aliasNameInputTextboxObject, Keys.chord(Keys.CONTROL, 'a'))
		WebUI.sendKeys(aliasNameInputTextboxObject, Keys.chord(Keys.BACK_SPACE))
		WebUI.sendKeys(codeAliasInputTextboxObject, Keys.chord(Keys.CONTROL, 'a'))
		WebUI.sendKeys(codeAliasInputTextboxObject, Keys.chord(Keys.BACK_SPACE))
		WebUI.sendKeys(userAddedDescInputTextboxObject, Keys.chord(Keys.CONTROL, 'a'))
		WebUI.sendKeys(userAddedDescInputTextboxObject, Keys.chord(Keys.BACK_SPACE))
	}

	/**
	 * Clear input for Name Alias, Code Alias 
	 */
	@Keyword
	public static void clearInputForNameAliasAndCodeAlias(){
		TestObject aliasNameInputTextboxObject = findTestObject("Common Objects/Input_NameAlias")
		WebUI.waitForElementVisible(aliasNameInputTextboxObject , 5)
		TestObject codeAliasInputTextboxObject = findTestObject("Common Objects/Input_CodeAlias")
		WebUI.waitForElementVisible(codeAliasInputTextboxObject , 5)

		WebUI.sendKeys(aliasNameInputTextboxObject, Keys.chord(Keys.CONTROL, 'a'))
		WebUI.sendKeys(aliasNameInputTextboxObject, Keys.chord(Keys.BACK_SPACE))
		WebUI.sendKeys(codeAliasInputTextboxObject, Keys.chord(Keys.CONTROL, 'a'))
		WebUI.sendKeys(codeAliasInputTextboxObject, Keys.chord(Keys.BACK_SPACE))

	}
	/**
	 * Click on dropdown button
	 */
	@Keyword
	public static void clickDropdownButton(String dropdownFiledName){
		String dropdownButtonXpath = "//div/button[contains(@placeholder,'%s')]"
		TestObject dropdownButtonObject = getTestObject("xpath", String.format(dropdownButtonXpath,dropdownFiledName))
		WebUI.waitForElementClickable(dropdownButtonObject, 5)
		WebUI.scrollToElement(dropdownButtonObject, 2)
		WebUI.click(dropdownButtonObject)
		WebUI.delay(2)
	}
	/**
	 * Retrieve dropdown list options 
	 * @return
	 */
	@Keyword
	public static List<String> getOptions (String dropdownListName){
		WebDriver driver = DriverFactory.getWebDriver()
		List<String> listOfOptions = new ArrayList<>()

		List<WebElement> ls = driver.findElements(By.xpath(String.format("(//button[@placeholder='%s']/../..)//ul//li//span", dropdownListName)))
		for(WebElement element:ls){
			String optionName = element.getText()
			listOfOptions.add(optionName)
		}
		System.out.println("listOfOptions: "+ listOfOptions)
		return listOfOptions
	}
	/**
	 * Retrieve dropdown list options
	 * @return
	 */
	@Keyword
	public static List<String> getOptions (){
		WebDriver driver = DriverFactory.getWebDriver()
		List<String> listOfOptions = new ArrayList<>()

		List<WebElement> ls = driver.findElements(By.xpath(String.format("//ul[contains(@class,'menuOpen')]//div/li[contains(@class,'dropdownOption')]/p/span")))
		for(WebElement element:ls){
			String optionName = element.getText()
			listOfOptions.add(optionName)
		}
		System.out.println("listOfOptions: "+ listOfOptions)
		return listOfOptions
	}

	/*
	 *
	 * Change the Current Option
	 * @param dropdownName, currentValue
	 */
	@Keyword
	public static void changeOptions(String dropdownName, String currentValue){
		clickDropdownButton(dropdownName)

		List<String> listOfOptions = getOptions(dropdownName)

		if(listOfOptions.size()<=1){
			System.out.println("There is only one option available")
		}else{
			for(int i=0;i<=listOfOptions.size();i++){
				if(currentValue!=listOfOptions.get(i)){
					String objectPath = "(//button[@placeholder='%s']/../..)//ul//li//span[text()='%s']"
					TestObject optionObject = getTestObject("xpath", String.format(objectPath, dropdownName, listOfOptions.get(i)))
					WebUI.click(optionObject)
					WebUI.delay(2)
					break
				}else{
					continue
				}
			}
		}
	}
	/*
	 *
	 * Change the Current Option
	 * @param dropdownName, currentValue
	 */
	@Keyword
	public static void changeOptionToExpected (String dropdownName, String expectedValue){
		clickDropdownButton(dropdownName)

		List<String> listOfOptions = getOptions(dropdownName)

		if(listOfOptions.size()<=1){
			System.out.println("There is only one option available")
		}else{
			for(int i=0;i<=listOfOptions.size();i++){
				if(expectedValue==listOfOptions.get(i)){
					String objectPath = "(//button[@placeholder='%s']/../..)//ul//li//span[text()='%s']"
					TestObject optionObject = getTestObject("xpath", String.format(objectPath, dropdownName, listOfOptions.get(i)))
					WebUI.click(optionObject)
					WebUI.delay(2)
					break
				}else{
					continue
				}
			}
		}
	}


	/**
	 * Retrieve the current first record's Name in the grid list except for Locations Page and Users page, has to have toggle as the first column
	 * @return
	 */
	@Keyword
	public static String getCurrentNameFromGridList (){
		//String firstRowNameXpath = "//div[contains(@style,'grid-area: r0-%ds / c1s / r0-%de / c1e;')]//span"
		//TestObject firstRowNameObject = getTestObject("xpath", String.format(firstRowNameXpath, 2, 2))
		WebUI.delay(7)
		TestObject firstRowNameObjet = findTestObject("Common Objects/Text_FirstColumnNameText")
		WebUI.waitForElementVisible(firstRowNameObjet, 20)
		//WebUI.waitForElementClickable(firstRowNameObjet, 20)
		return WebUI.getText(firstRowNameObjet, FailureHandling.OPTIONAL)
	}
	/**
	 * Retrieve the record's Name in the grid list based on its row number
	 * @return
	 */
	@Keyword
	public static String getCurrentNameFromGridList (int rowNumber){
		WebUI.delay(7)
		String specificRowNameXpath = "//div[contains(@style,'grid-area: r0-%ds / c1s / r0-%de / c1e;')]//span"
		TestObject specificRowNameObject = getTestObject("xpath", String.format(specificRowNameXpath, rowNumber, rowNumber))
		//WebUI.waitForElementVisible(specificRowNameObject, 20)
		WebUI.waitForElementClickable(specificRowNameObject, 25)
		String supplierName = WebUI.getText(specificRowNameObject)
	}
	//	/**
	//	 * Verify if toggled off record is showing View link as disabled
	//	 *@return
	//	 */
	//	@Keyword
	//	public static boolean verifyViewLinkDisabled (){
	//
	//		String viewLinkXpath = "(//*[@id='appContainer']//button[contains(text(),'View')])[1]"
	//		TestObject viewLinkObject = getTestObject("xpath", viewLinkXpath)
	//		WebUI.scrollToElement(viewLinkObject,3)
	//		return WebUI.verifyElementAttributeValue(viewLinkObject, "disabled","true" ,5, FailureHandling.CONTINUE_ON_FAILURE)
	//	}

	/**
	 * Upload files
	 * @param path
	 */
	@Keyword
	public static void fileUpload (String path) {
		StringSelection strSelection = new StringSelection(path);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(strSelection, null);

		Robot robot = new Robot();

		robot.delay(300);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(200);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	/**
	 * Update excel files
	 * @param newName, filePath, rowNumber, cellNumber
	 */
	@Keyword
	public static void updateExcel (String newName, String filePath, int rowNumber, int cellNumber) {


		FileInputStream file = new FileInputStream (new File(filePath))
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);

		'Read data from excel'
		String Data_fromCell=sheet.getRow(rowNumber).getCell(cellNumber).getStringCellValue();
		'Write data to excel'
		sheet.getRow(rowNumber).createCell(cellNumber).setCellValue(newName);

		file.close();
		FileOutputStream outFile =new FileOutputStream(new File(filePath));
		workbook.write(outFile);
		outFile.close();

	}


	/*
	 * Verify if Set Ingredient Costs/Template Nutrient Page is Loaded
	 */
	@Keyword
	public static boolean isSetManagementPageLoaded(String key){
		WebUI.delay(1)
		TestObject isAFilLinkDisplayed = findTestObject('Object Repository/Set Ingredient Costs/LinkApplyFilter_Disabled')
		WebUI.waitForElementVisible(isAFilLinkDisplayed, 50)
		if(WebUI.verifyElementVisible(isAFilLinkDisplayed, FailureHandling.STOP_ON_FAILURE)==false){
			WebUI.refresh()
		}
		System.out.println("Set "+key+" by Page loaded")
		return WebUI.verifyElementVisible(isAFilLinkDisplayed, FailureHandling.STOP_ON_FAILURE)

	}

	/**
	 * Read cell value from excel
	 * @param filePath, rowNumber, cellNumber
	 * @return
	 */
	@Keyword
	public static String readExcelCellValue (String filePath, int rowNumber, int cellNumber) {


		FileInputStream file = new FileInputStream (new File(filePath))
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);

		'Read data from excel'
		String Data_fromCell=sheet.getRow(rowNumber).getCell(cellNumber).getStringCellValue();

		file.close();
		FileOutputStream outFile =new FileOutputStream(new File(filePath));
		workbook.write(outFile);
		outFile.close();
		return Data_fromCell

	}

}


/**
 * check if dropdown list item exists
 * @param TestObject, option
 * @return T/F
 */


@Keyword
def CheckDropDownListElementExist(TestObject object, String option){

	boolean flag = false;

	//Get element into list
	WebElement element = WebUiCommonHelper.findWebElement(object, 20);
	Select ddl = new Select(element);
	for(WebElement ele: ddl.getOptions()){
		if(ele.getText().equals(option)){
			println "Element exists: " + option
			flag = true;

		} // end if

	} //end for

	//return flag status
	return flag;

}  // end checkdropdownlistelementexists




