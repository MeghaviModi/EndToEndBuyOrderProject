package com.envisionAutomation.Frameworks.utils;

import java.io.IOException;
import java.util.Properties;

public class ORUtils {
    static Properties ORProperties;
    public static void loadORFiles() throws IOException {
        ORProperties = PropertiesLoader.loadPropertiesFile(System.getProperty("user.dir") +
                "/src/main/resources/OR.properties");


    }

    public static String getORPropertyValue(String ORPropertyName){
       return PropertiesLoader.getPropertyValue(ORPropertyName, ORProperties);

    }


}
