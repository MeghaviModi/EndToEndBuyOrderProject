package com.envisionAutomation.pages;

import com.envisionAutomation.Frameworks.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;


import java.io.IOException;

public class PaymentSummaryPage extends BasePage {
    WebDriver driver;
    public PaymentSummaryPage(WebDriver driver){
        super(driver);
        this.driver= driver;
    }
    public PaymentSummaryPage checkOutClickOn() throws IOException {
        clickOn("PaymentPage.btnCheckOut2");
        return this;
    }
    public PaymentSummaryPage fetchDeliveryFee() throws IOException {
        Assert.assertTrue(getWebElementText("PaymentPage.txtFees").contains("$2.00"),"Fail to Fetch deliveryFees");
        return this;
    }
    public PaymentSummaryPage CheckBoxClick() throws IOException {
        clickOn("PaymentPage.txtCheckBox");
        return this;
    }
    public PaymentSummaryPage ProceedToCheckOutAgain() throws IOException {
        clickOn("PaymentPage.btnProCheckout");
        return this;
    }
    public PaymentSummaryPage ClickOnWireBank() throws IOException {
        clickOn("PaymentPage.btnWireBank");
        return this;
    }

    public String getTotalAmount() throws IOException {
        String Total=getWebElementText("PaymentPage.TxtAmount");
        return Total;
    }
    public String getOwnerName() throws IOException {
        String ownerName=getWebElementText("PaymentPage.TxtName");
        return ownerName;
    }
    public String getBankName() throws IOException {
        String BankName=getWebElementText("PaymentPage.TxtBankName");
        return BankName;
    }

    public PaymentSummaryPage conformOrder() throws IOException {
        clickOn("PaymentPage.btnConformOrder");
        return this;
    }
    public String  validateOrder() throws IOException {
        String message=getWebElementText("PaymentPage.txtOrder");
        Assert.assertEquals(message,"Your order on My Store is complete.");
        return message;
    }



}
