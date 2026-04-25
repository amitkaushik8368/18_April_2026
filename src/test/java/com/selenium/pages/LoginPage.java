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
    WebDriverWait wait;;

    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(3));
    }



    public WebElement locateUsername()
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
    }

    public WebElement locatePassword()
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
    }
    public WebElement locateLoginButton()
    {
        return wait.until(ExpectedConditions.elementToBeClickable(By.className("radius")));
    }
    public WebElement locateLoginErrorMessage()
    {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
    }
}
