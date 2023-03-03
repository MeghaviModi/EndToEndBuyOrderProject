package com.envisionAutomation.pages;

import com.envisionAutomation.Frameworks.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import sun.util.resources.cldr.es.CalendarData_es_PY;

import java.io.IOException;

public class UserPage extends BasePage {
    WebDriver driver;
    public UserPage(WebDriver driver){
        super(driver);
        this.driver= driver;
    }
    public UserPage ClickOnT_shirts() throws IOException {
        clickOn("UserPage.btnT_shirts");
        return this;
    }
    public UserPage ClickOnImageT_shirts() throws IOException {
        clickOn("UserPage.imgT_shirts");
        return this;
    }
    public UserPage AddToCart() throws IOException {
        clickOn("UserPage.btnCart");
        return this;
    }
    public UserPage getTextSuccessfulOrder() throws IOException {
       Assert.assertTrue(getWebElementText("UserPage.textDisplay").contains("Product successfully added to your shopping cart"),
               "fail to found");
        return this;
    }
    public UserPage ClickOnCheckOut() throws IOException {
        clickOn("PaymentPage.btnCheckout");
        return this;
    }
    public UserPage FetchTotalPrice() throws IOException {
       Assert.assertTrue(getWebElementText("PaymentPage.txtTotal")
               .contains("$19.25"),"Fail to Fetch Amount");
        return this;
    }
    public PaymentSummaryPage ClickOnProceedToCheckOut() throws IOException {
        clickOn("PaymentPage.btnCheckOut1");
        return new PaymentSummaryPage(this.driver);
    }


}
