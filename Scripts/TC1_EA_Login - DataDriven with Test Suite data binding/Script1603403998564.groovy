import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys



WebUI.openBrowser(GlobalVariable.GV_TestURL)

//WebUI.navigateToUrl('https://demosite.executeautomation.com/Login.html')
// datadrive loop using excel data
//for (def ii,ii<findTestData("EA_TestData").getRowCount,ii++) {
//} // end of for loop/


WebUI.setText(findTestObject('Object Repository/Page_Execute Automation/input_Login_UserName'), username)


WebUI.setText(findTestObject('Object Repository/Page_Execute Automation/input_Login_Password'), password)

WebUI.click(findTestObject('Object Repository/Page_Execute Automation/input_Login_Login'))

WebUI.setText(findTestObject('Object Repository/Page_Execute Automation/input_Initial_Initial'), initial)

WebUI.setText(findTestObject('Page_Execute Automation/input__FirstName'), firstname)

WebUI.setText(findTestObject('Object Repository/Page_Execute Automation/input_Middle Name_MiddleName'), lastname)

WebUI.click(findTestObject('Object Repository/Page_Execute Automation/input_MaleFemale_Female'))

WebUI.click(findTestObject('Object Repository/Page_Execute Automation/input_EnglishHindi_Save'))

String myResult = WebUI.getText(findTestObject('Page_Execute Automation/text_UserForm'))

println "${username} - ${password}"

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.equals'(myResult, 'User Form', false, 'validation match', 
    FailureHandling.CONTINUE_ON_FAILURE)

WebUI.closeBrowser()

