package com.selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverManager
{
    WebDriver driver;
    public void setDriver()
    {
        this.driver = new EdgeDriver();
        driver.manage().window().maximize();
    }
    public WebDriver getDriver()
    {
        if (driver==null) {
            throw new IllegalStateException("The driver is null");
        }
        else return this.driver;
    }
    public void teardown()
    {
        driver.quit();
    }
}
