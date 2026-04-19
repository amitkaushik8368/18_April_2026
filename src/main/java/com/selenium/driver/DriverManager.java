package com.selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverManager
{
    WebDriver driver;
    void initialize()
    {
        driver = new EdgeDriver();
    }
    void teardown()
    {
        driver.quit();
    }
}
