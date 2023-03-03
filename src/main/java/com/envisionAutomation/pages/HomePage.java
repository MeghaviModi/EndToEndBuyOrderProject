package com.envisionAutomation.pages;

import com.envisionAutomation.Frameworks.core.BasePage;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class HomePage extends BasePage {
    WebDriver driver;
    public HomePage(WebDriver driver){
        super(driver);
        this.driver= driver;
    }
    public HomePage enterUsername(String emailId) throws IOException, InterruptedException {
        typeInTo("HomePage.txbEmailAddress",emailId);
        return  this;
    }

    public HomePage enterPassword(String password) throws IOException, InterruptedException {
        typeInTo("HomePage.txbPassword",password);
        return this;
    }
    public UserPage clickOnSignIn() throws IOException {
        clickOn("UserPage.btnSignIn");
        return new UserPage(this.driver);
    }
}
