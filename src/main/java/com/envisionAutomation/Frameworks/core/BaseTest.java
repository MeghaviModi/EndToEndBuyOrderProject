package com.envisionAutomation.Frameworks.core;

import com.envisionAutomation.Frameworks.utils.ConfigLoader;
import com.envisionAutomation.Frameworks.utils.ExtentManager;
import com.envisionAutomation.Frameworks.utils.ExtentTestManager;
import com.envisionAutomation.Frameworks.utils.JsonUtils;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.lang.reflect.Method;

public class BaseTest {
    public static WebDriver driver;
    public static JsonUtils jsonUtils;
    @BeforeSuite
    public void loadConfigurations() throws IOException, ParseException {
        ExtentManager.getReporter();
        ConfigLoader.loadConfigurations();
        jsonUtils=new JsonUtils();
        jsonUtils.loadTestDataFile("testData.json");
    }
    @BeforeMethod
    public void loadBrowser(Method methodName){
        ExtentTestManager.startTest(methodName.getName(),"");
        this.driver=BasePage.launchBrowser(ConfigLoader.getBrowserType());

    }
    @AfterMethod
    public void tearDownBrowser(){
        BasePage.closeBrowser();
        ExtentTestManager.stopTest();
    }
    @AfterSuite
    public void tearDownConfiguration(){
        driver =null;
        ExtentManager.getReporter().flush();
        ExtentManager.getReporter().close();
    }
}