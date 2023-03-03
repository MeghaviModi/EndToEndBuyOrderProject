package com.envisionAutomation.Frameworks.utils;

import com.relevantcodes.extentreports.ExtentReports;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports suiteReporter;
    public static synchronized  ExtentReports getReporter(){
        try{
            if(suiteReporter == null){
                String extentReportPath= System.getProperty("user.dir")+"/src/test/resources/extentReports";
                File f= new File(extentReportPath);
                if(!f.isDirectory()){
                    f.mkdir();
                }
                suiteReporter = new ExtentReports(extentReportPath+ "//TestResult-"+new SimpleDateFormat("dd.MM.yyyy.hh.mm.ss").format(new Date())+".html");

            }
        }catch (Exception e){

        }
        return suiteReporter;
    }

}
