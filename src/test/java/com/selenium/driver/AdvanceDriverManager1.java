package com.selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class AdvanceDriverManager1
{
    public static ThreadLocal<WebDriver> threadedDriver = new ThreadLocal<>();
    public static void setDriver(WebDriver driver)
    {
        if (threadedDriver.get() == null)
            threadedDriver.set(driver);

    }
    public static WebDriver getDriver()
    {
        if (threadedDriver.get()==null) {
            throw new IllegalStateException("The driver is null");
        }
        else return threadedDriver.get();
    }
    public static void setUp()
    {
        WebDriver driver = new EdgeDriver();
        AdvanceDriverManager1.setDriver(driver);
    }
    public static void teardown()
    {
        threadedDriver.get().quit();
    }
}
