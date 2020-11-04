package com.ea.mysteps
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException

import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When


class EALogon {
	/**
	 * The step definitions below match with Katalon sample Gherkin steps
	 */
	@Given("I navigate to EA site")
	def I_NavigatetoSite() {
		WebUI.openBrowser('')
		WebUI.navigateToUrl('https://demosite.executeautomation.com/Login.html')
	}

	@When('I enter username (.*) and password (.*)')
	def I_Enter_UserCredentials(String username, String password) {
		WebUI.setText(findTestObject('Object Repository/Page_Execute Automation/input_Login_UserName'), username)
		WebUI.setText(findTestObject('Object Repository/Page_Execute Automation/input_Login_Password'), password)
	}

	@And("I click on Submit button")
	def I_ClickSubmit(){
		WebUI.click(findTestObject('Object Repository/Page_Execute Automation/input_Login_Login'))
		
	}
	
	
	@Then("I verify the status (.*) at the Home page")
	def I_VerifyStatus(String status) {
		WebUI.setText(findTestObject('Object Repository/Page_Execute Automation/input_Initial_Initial'), 'bs')

		WebUI.setText(findTestObject('Page_Execute Automation/input__FirstName'), 'First')
		
		WebUI.setText(findTestObject('Object Repository/Page_Execute Automation/input_Middle Name_MiddleName'), 'Last')
		
		WebUI.click(findTestObject('Object Repository/Page_Execute Automation/input_MaleFemale_Female'))
		
		WebUI.click(findTestObject('Object Repository/Page_Execute Automation/input_EnglishHindi_Save'))

		String myResult = WebUI.getText(findTestObject('Page_Execute Automation/text_UserForm'))

	
			
			WebUI.closeBrowser()
	}
}