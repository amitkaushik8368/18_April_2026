package com.selenium.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataRepository
{
    public static Properties prop;
    public static void loadData() throws IOException
    {
        prop = new Properties();
        FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties"
        );
        prop.load(fis);
    }

    public static String getUserName()
    {
        return prop.getProperty("valid.username");
    }
    public static String getPassWord()
    {
        return prop.getProperty("valid.password");
    }
    public static String getUrl()
    {
        return prop.getProperty("url");
    }
    public static String getInvalidUsername()
    {
        return prop.getProperty("invalid.username");
    }
    public static String getInvalidPassword()
    {
        return prop.getProperty("invalid.password");
    }
}
