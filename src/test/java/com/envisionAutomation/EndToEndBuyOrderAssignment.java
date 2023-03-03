package com.envisionAutomation;

import com.envisionAutomation.Frameworks.core.BaseTest;
import com.envisionAutomation.pages.HomePage;
import com.envisionAutomation.pages.LandingPage;
import com.envisionAutomation.pages.PaymentSummaryPage;
import com.envisionAutomation.pages.UserPage;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;

public class EndToEndBuyOrderAssignment extends BaseTest {
    @Test
    public static void  EndToEndBuyOrderAssignmentTest() throws IOException, InterruptedException {
        JSONObject loginData=jsonUtils.getJsonObject(jsonUtils.mainJsonObj,"loginData");
        String username=jsonUtils.getJsonObjectValue(loginData,"username");
        String password=jsonUtils.getJsonObjectValue(loginData,"password");
        String validateMessage;
        String Totalamount;
        String ownerName;
        String BankName;

        LandingPage landingpage = new LandingPage(driver);

        HomePage homePage=landingpage.launchAutomationPractiseApplication().clickOnSignIn();
        UserPage userPage=homePage.enterUsername(username)
                .enterPassword(password).clickOnSignIn();

        PaymentSummaryPage paymentSummaryPage=userPage.ClickOnT_shirts()
                .ClickOnImageT_shirts().AddToCart()
                .getTextSuccessfulOrder().ClickOnCheckOut().FetchTotalPrice()
                .ClickOnProceedToCheckOut();
        paymentSummaryPage.checkOutClickOn().fetchDeliveryFee().CheckBoxClick()
                .ProceedToCheckOutAgain()
                .ClickOnWireBank().conformOrder();

        validateMessage=paymentSummaryPage.validateOrder();
        Totalamount=paymentSummaryPage.getTotalAmount();
        ownerName=paymentSummaryPage.getOwnerName();
        BankName=paymentSummaryPage.getBankName();
        System.out.println(validateMessage);
        System.out.println(Totalamount);
        System.out.println(ownerName);
        System.out.println(BankName);

    }

}
