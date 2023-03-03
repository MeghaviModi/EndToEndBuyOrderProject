package com.envisionAutomation.Frameworks.utils;

import com.relevantcodes.extentreports.LogStatus;

public class Reporter {
    public static void LogPassedSteps(String Message){
        ExtentTestManager.getTest().log(LogStatus.PASS, Message);

    }
    public static void LogFailedStep(String Message){
        ExtentTestManager.getTest().log(LogStatus.FAIL, Message);

    }
}
