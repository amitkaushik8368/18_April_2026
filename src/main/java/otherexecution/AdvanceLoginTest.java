package com.selenium.pratima.otherexecution;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class AdvanceLoginTest
{
    static String validUsername;
    static String validPassword;
    static String invalidUsername;
    static String invalidPassword;
    static String url;
    static WebElement usernameLocator;
    static WebElement passwordLocator;
    static WebElement loginButton;
    static WebElement logoutButton;
    static WebDriver driver;
    static WebDriverWait wait;
    static WebElement errorMessageLocator;

    public static void main(String[] args) {
        loadProperties();
        testValidLogin();
        testInvalidLogin();
    }


    static void loadProperties(){
        Properties prop = new Properties();
        try(
                FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//main//resources//config.properties")
        ) {
            prop.load(fis);
        } catch (IOException e) {
            System.out.println("Exception has come");
        }
        validUsername = prop.getProperty("validUsername");
        validPassword = prop.getProperty("validPassword");
        invalidUsername = prop.getProperty("invalidUsername");
        invalidPassword = prop.getProperty("invalidPassword");
        url = prop.getProperty("url");

    }

    static void initializeDriver()
    {
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get(url);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        usernameLocator = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        passwordLocator = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("radius")));

    }
    static void testValidLogin()
    {
        initializeDriver();
        usernameLocator.sendKeys(validUsername);
        passwordLocator.sendKeys(validPassword);
        loginButton.click();
        try {
            logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Logout']")));
            assert logoutButton.isDisplayed(): "Login has failed and so does the test";
            System.out.println("Login is successful");
        } catch (TimeoutException e)
        {
            errorMessageLocator = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
            assert errorMessageLocator.isDisplayed():"No error message found, details are unclear";
            System.out.println("The test has failed ");
        }

        driver.quit();

    }

    static void testInvalidLogin()
    {
        initializeDriver();
        usernameLocator.sendKeys(invalidUsername);
        passwordLocator.sendKeys(invalidPassword);
        loginButton.click();
        try {
            logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Logout']")));
            assert logoutButton.isDisplayed():"The login has failed";
            System.out.println("The test has failed");
        } catch (TimeoutException e) {
            errorMessageLocator = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));
            assert errorMessageLocator.isDisplayed() : "No error message found, the test is failed";
            System.out.println("The test has passed login is unsuccessful");
        }
        driver.quit();
    }
}
