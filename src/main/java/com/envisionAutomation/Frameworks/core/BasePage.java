package com.envisionAutomation.Frameworks.core;

import com.envisionAutomation.Frameworks.utils.ConfigLoader;
import com.envisionAutomation.Frameworks.utils.ORUtils;
import com.envisionAutomation.Frameworks.utils.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage {
    public static WebDriver baseDriver;
    public BasePage (WebDriver driver){
        this.baseDriver = driver;
    }      //constructor
    public static WebDriver launchBrowser(String browserType){

        try {
            if(browserType.equalsIgnoreCase("Chrome")){
                System.setProperty("webdriver.chrome.driver", ConfigLoader.getChromeDriverPath());
                baseDriver = new ChromeDriver();
            }else if (browserType.equalsIgnoreCase("edge")){
                System.setProperty("webdriver.edge.driver", ConfigLoader.getEdgeDriverPath());

            } else if (browserType.equalsIgnoreCase("firefox")) {
                System.setProperty("webdriver.gecko.driver",ConfigLoader.getFirefoxDriverPath() );
                baseDriver = new FirefoxDriver();

            }else {
                throw  new UnsupportedOperationException("Browser Type ["+browserType+"] is not supported");
            }

            baseDriver.manage().timeouts().pageLoadTimeout(ConfigLoader.getWaitTime(), TimeUnit.SECONDS);
            baseDriver.manage().timeouts().implicitlyWait(ConfigLoader.getWaitTime(), TimeUnit.SECONDS);
            baseDriver.manage().window().maximize();
            Reporter.LogPassedSteps("Browser["+browserType+"] launched and maximized successfully");
        } catch (Exception ex) {
            Reporter.LogFailedStep("Unable to launched Browser ["+browserType+"], Exception accurred"+ex);

        }
        return baseDriver;

    }
    public void launchUrl(String url){
        try{
            this.baseDriver.get(url);
            Reporter.LogPassedSteps("Url ["+ url +"] launched Successfully");
        } catch (Exception ex) {
            Reporter.LogFailedStep("Unable to launch URL["+url+"]");
        }
    }
    public By getFindBy(String propertyName) throws IOException {
        By by=null;
        try{
            ORUtils.loadORFiles();
            String orElementValue=ORUtils.getORPropertyValue(propertyName);

            String byMode = orElementValue.split("#",2)[0];
            String byValue = orElementValue.split("#",2)[1];

            if(byMode.equalsIgnoreCase("id")){
                by=  By.id(byValue);
            } else if (byMode.equalsIgnoreCase("name")) {
                by= By.name(byValue);

            }else if (byMode.equalsIgnoreCase("class")) {
                by= By.className(byValue);

            }else if (byMode.equalsIgnoreCase("tag")) {
                by= By.tagName(byValue);

            }else if (byMode.equalsIgnoreCase("css")) {
                by= By.cssSelector(byValue);

            }else if (byMode.equalsIgnoreCase("xpath")) {
                by= By.xpath(byValue);

            }else if (byMode.equalsIgnoreCase("lt")) {
                by= By.linkText(byValue);

            }else if (byMode.equalsIgnoreCase("plt")) {
                by= By.partialLinkText(byValue);

            }else {
                throw new UnsupportedOperationException("ByMode value is not supported, check the OR.properties and pass support Value");
            }
        } catch (Exception ex) {
            Reporter.LogFailedStep("Unable to Get By Locator["+by+"]");

        }
        return by;
    }
    public WebElement findWebElement(String orElement) throws IOException {
        WebElement element=null;
        try{
            By by= getFindBy(orElement);
            //element = this.baseDriver.findElement(by);
            element = new WebDriverWait(baseDriver,ConfigLoader.getWaitTime())
                    .until(ExpectedConditions.presenceOfElementLocated(by));
            element = new WebDriverWait(baseDriver,ConfigLoader.getWaitTime())
                    .until(ExpectedConditions.visibilityOfElementLocated(by));
            Reporter.LogPassedSteps("Or Element ["+orElement+"] found successfully");
        } catch (Exception e) {
            Reporter.LogFailedStep("Unable to find Element ["+orElement+"]");
        }
        return element;
    }
    public List<WebElement> findWebElements(String orElement) throws IOException {
        List<WebElement> elements=null;
        try {
            By by = getFindBy(orElement);
            // elements = this.baseDriver.findElements(by);
            elements = new WebDriverWait(baseDriver,ConfigLoader.getWaitTime())
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
            Reporter.LogPassedSteps("Element ["+orElement+"] found successfully ");
        }catch (Exception e){
            Reporter.LogFailedStep("Unable to find element ["+orElement+"]");
        }
        return elements;
    }

    public void clickOn(String elementName) throws IOException {
        try {

            WebElement element = findWebElement(elementName);
            new WebDriverWait(baseDriver,ConfigLoader.getWaitTime())
                    .until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            Reporter.LogPassedSteps("Click on element ["+elementName+"] successfully");
        }catch (Exception e){
            Reporter.LogFailedStep("Unable to click element ["+elementName+"]");
        }
    }
    public void typeInTo(String elementName, String contentToType) throws IOException, InterruptedException {
        try {
            WebElement element = findWebElement(elementName);
            new WebDriverWait(baseDriver, ConfigLoader.getWaitTime())
                    .until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            Thread.sleep(100);
            element.clear();
            Thread.sleep(100);
            element.sendKeys(contentToType);
            Reporter.LogPassedSteps("Typed ["+contentToType+"] Into element["+elementName+"] ");
        }catch (Exception e){
            Reporter.LogFailedStep("Typed ["+contentToType+"] Into element["+elementName+"] ");

        }
    }
    public String  getWebElementText(String elementName) throws IOException {
        WebElement element=null;
        String text="";
        try {
            element = findWebElement(elementName);
            text=element.getText();
            Reporter.LogPassedSteps("Fetched text[" +text+ "] from element ["+elementName+"]");
        }catch (Exception e){
            Reporter.LogFailedStep("Unable to fetch from element["+elementName+"]");
        }
        return text;
    }
    public void sendText(String orElement, String contentType) throws IOException {
        try {
            By by = getFindBy(orElement);
            WebElement element = baseDriver.findElement(by); element.sendKeys(contentType);
            Reporter.LogPassedSteps("Given text sent to ["+orElement+"]");
        } catch (IOException e) { Reporter.LogPassedSteps("Unable to send text"); throw new RuntimeException(e);
        } }
    public String getText(String orElement) throws IOException { WebElement element;
        try {
            By by = getFindBy(orElement);
            element = baseDriver.findElement(by);
            Reporter.LogPassedSteps("Text content read successfully"); } catch (IOException e) {
            Reporter.LogFailedStep("Make sure it contains text");
            throw new RuntimeException(e); }
        return element.getText(); }
    public String getSeletedText(String elementName) throws IOException {
        WebElement element = null;
        Select s = null;
        try{
            element = findWebElement(elementName);
            s = new Select(element);
            Reporter.LogPassedSteps("Fetched text ["+ s.getFirstSelectedOption().getText()+"] from Select element ["+element+"] successfully");
        }catch (Exception e) {
            Reporter.LogFailedStep("Unable to fatch text ["+ s.getFirstSelectedOption().getText()+"] from Select element ["+element+"]"); }
        return s.getFirstSelectedOption().getText();
    }
    public String getWebElementAttribute(String elementName, String attributeType) throws IOException {
        WebElement element=null;
        String attributeValue=null;
        try {
            element = findWebElement(elementName);
            attributeValue= element.getAttribute(attributeType);
            Reporter.LogPassedSteps("Fetched attribute ["+attributeValue+"] value ["+attributeValue+"]");
        }catch (Exception e){
            Reporter.LogFailedStep("Unable to Fetched attribute ["+attributeValue+"] value ");
        }
        return attributeValue;
    }

    public static void closeBrowser(){
        try {
            baseDriver.close();
            Reporter.LogPassedSteps("Close Browser Successfully");
        }catch (Exception e){
            Reporter.LogFailedStep("Unable to Close Browser");
        }
    }
    public static void closeAllBrowser(){
        try {
            baseDriver.quit();
            Reporter.LogPassedSteps("Close All Browser and terminated driver session");
        }catch (Exception e){
            Reporter.LogFailedStep("Unable to close or terminated driver session ");
        }
    }
    public void refreshPage(){
        try {
            this.baseDriver.navigate().refresh();
            Reporter.LogPassedSteps("Web Page refreshed!!");
        }catch (Exception e){
            Reporter.LogFailedStep("Web Page failed to refreshed!!");

        }
    }
    public void scroll(long x,long y){
        JavascriptExecutor j = (JavascriptExecutor) baseDriver; String scrollDown = "window.scroll("+x+","+y+")"; j.executeScript(scrollDown,"");
    }

    public String getCurrentUrl(){
        String url="";
        try{
            url= baseDriver.getCurrentUrl();
            Reporter.LogPassedSteps("The current Url of page is"+url);
        }catch (Exception e){
            Reporter.LogFailedStep("Unable to fetch Current Url of page ");
        }
        return url;
    }
    public String getTitleOfPage(){
        String title=null;
        try{
            title= baseDriver.getTitle();
            Reporter.LogPassedSteps("title of this page is"+ title);
        }catch (Exception e){
            Reporter.LogFailedStep("Unable to fected the Title of page ");
        }
        return title;
    }
    public void selectFromDropdown(String elementName , String how,String valueToSelect ) throws IOException {
        try {
            WebElement element = findWebElement(elementName);
            Select dropDownList = new Select(element);
            if (how.equalsIgnoreCase("value")) {
                dropDownList.selectByValue(valueToSelect);
            } else if (how.equalsIgnoreCase("visibleText")) {
                dropDownList.selectByVisibleText(valueToSelect);
            } else if (how.equalsIgnoreCase("index")) {
                dropDownList.selectByIndex(Integer.parseInt(valueToSelect));
            }
            Reporter.LogPassedSteps("value ["+valueToSelect+"] selected from dropdown["+elementName+"]");
        }catch (Exception e){
            Reporter.LogFailedStep("Unable to select value ["+valueToSelect+"] selected from dropdown["+elementName+"]");
        }
    }
    public void selectIndexValueFromDropdown(String elementName, String value) throws IOException {
        try {
            selectFromDropdown(elementName, "index", value);
        }catch (Exception e){

        }
    }
    public void selectVisibleValueFromDropdown(String elementName, String value) throws IOException {
        try{
            selectFromDropdown(elementName,"VisibleText", value);
        }catch (Exception e){

        }
    }
    public void selectValueFromDropdown(String elementName, String value) throws IOException {
        try{
            selectFromDropdown(elementName,"value", value);
        }catch (Exception e){

        }
    }
    public boolean isDisplay(String elementName) throws IOException {
        WebElement element=null;
        try{
            element= findWebElement(elementName);
            Reporter.LogPassedSteps("Element ["+elementName+"] displayed successfully ");
        }catch (Exception e){
            Reporter.LogFailedStep("Element ["+elementName+"] not displayed ");
        }
        return element.isDisplayed();
    }


}
