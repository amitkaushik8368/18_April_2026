package com.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashBoard
{
    WebDriver driver;
    WebDriverWait wait;
    public WebElement loggedInMessage;
    public WebElement logoutButton;
    public DashBoard(WebDriver driver)
    {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        loggedInMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
        logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a/i[normalize-space(text() = 'Logout')]")));
    }
}
