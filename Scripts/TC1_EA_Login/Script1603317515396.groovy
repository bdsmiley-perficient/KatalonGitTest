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
WebUI.setText(findTestObject('Object Repository/Page_Execute Automation/input_Login_UserName'), GlobalVariable.GV_UserName)

WebUI.setText(findTestObject('Object Repository/Page_Execute Automation/input_Login_Password'), GlobalVariable.GV_UserPass)

WebUI.click(findTestObject('Object Repository/Page_Execute Automation/input_Login_Login'))



WebUI.setText(findTestObject('Object Repository/Page_Execute Automation/input_Initial_Initial'), findTestData('EA/EA_TestData').getValue(
        'Initial', 1))

WebUI.setText(findTestObject('Page_Execute Automation/input__FirstName'), findTestData('EA/EA_TestData').getValue('FirstName', 
        1))

WebUI.setText(findTestObject('Object Repository/Page_Execute Automation/input_Middle Name_MiddleName'), 'Last')

gender = findTestData('EA/EA_TestData').getValue('Gender', 1)

if (gender=="Male"){
	WebUI.click(findTestObject('Object Repository/Page_Execute Automation/input_MaleFemale_Female'))
}else{
 WebUI.click(findTestObject('Object Repository/Page_Execute Automation/input_MaleFemale_Male'))
}



WebUI.click(findTestObject('Object Repository/Page_Execute Automation/input_EnglishHindi_Save'))

String myResult = WebUI.getText(findTestObject('Page_Execute Automation/text_UserForm'))

CustomKeywords.'kms.turing.katalon.plugins.assertj.StringAssert.equals'(myResult, 'User Form', false, 'validation match', 
    FailureHandling.CONTINUE_ON_FAILURE)

CustomKeywords.WebUI.closeBrowser()

CustomKeywords.'com.common.utils.WebUI_common.HelloWord'()

