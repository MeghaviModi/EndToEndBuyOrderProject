package com.envisionAutomation.Frameworks.utils;

import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    static Properties configurations;
    private  static  String browserType;
    private  static String chromeDriverPath;
    private  static String firefoxDriverPath;
    private  static String edgeDriverPath;
    private  static  int waitTime;

    public static int getWaitTime() {
        return waitTime;
    }

    public static String getBrowserType(){
        return browserType;
    }

    public static String getChromeDriverPath() {
        return chromeDriverPath;
    }

    public static String getFirefoxDriverPath() {
        return firefoxDriverPath;
    }

    public static String getEdgeDriverPath() {
        return edgeDriverPath;
    }

    public  ConfigLoader(){

    }
    public static void loadConfigurations() throws IOException {
        //step2 locate file
        try {
            configurations = PropertiesLoader.loadPropertiesFile(System.getProperty("user.dir") +
                    "/src/main/resources/configs/config.properties");

            browserType = getConfigValue("browserType");
            chromeDriverPath = getConfigValue("chromeDriverPath");
            firefoxDriverPath = getConfigValue("firefoxDriverPath");
            edgeDriverPath = getConfigValue("edgeDriverPath");
            waitTime = Integer.parseInt(getConfigValue("waitTime"));
        }catch (Exception e){
            Reporter.LogFailedStep("Unable to load configuration");
        }

    }
    public static String getConfigValue(String propertyName){
        String value = PropertiesLoader.getPropertyValue(propertyName, configurations);
        return value;
    }
    public static void main(String [] args) throws IOException {
        ConfigLoader configLoader = new ConfigLoader();
        configLoader.loadConfigurations();
        System.out.println("Property Value Is: "+ configLoader.getConfigValue("chromeDriverPath"));


    }
}
