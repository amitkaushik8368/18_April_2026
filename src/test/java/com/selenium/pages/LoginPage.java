package com.selenium.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage
{
    WebDriver driver;
    WebDriverWait wait;
    public WebElement username;
    public WebElement password;
    public WebElement clickButton;
    public WebElement loggedInErrorMessage;
    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        clickButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("radius")));
        loggedInErrorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
    }

}
