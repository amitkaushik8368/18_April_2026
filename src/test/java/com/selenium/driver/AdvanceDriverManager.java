package com.selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class AdvanceDriverManager
{
    public static WebDriver driver1;
    public static void setDriver(WebDriver driver)
    {
        driver1 = driver;
    }
    public static WebDriver getDriver()
    {
        return driver1;
    }
    public static void setUp()
    {
        WebDriver driver = new EdgeDriver();
        AdvanceDriverManager.setDriver(driver);
    }
    public static void teardown()
    {
        driver1.quit();
    }
}
