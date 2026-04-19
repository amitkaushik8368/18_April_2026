package com.selenium.pratima;

import org.openqa.selenium.By;
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

    public static void main(String[] args) {
        loadProperties();
        initializeDriver();
        testValidLogin();



//        Properties prop = new Properties();
//        try(
//                FileInputStream fis = new FileInputStream
//                        (
//                                System.getProperty("user.dir")+"\\src\\main\\resources\\config.properties"
//                        )
//        )
//        {
//           prop.load(fis);
//        }  catch(RuntimeException | IOException e) {
//            System.out.println("Exception Occurred");
//        }
//        String validUsername = prop.getProperty("validUsername");
//        String validPassword = prop.getProperty("validPassword");
//        String invalidUsername = prop.getProperty("invalidUsername");
//        String invalidPassword = prop.getProperty("invalidPassword");
//        String url = prop.getProperty("url");
//        WebDriver driver = new EdgeDriver();
//        driver.manage().window().maximize();
//        driver.get(url);
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement usernameLocator = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
//        WebElement passwordLocator = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
//        WebElement loginButtonLocator = wait.until(ExpectedConditions.elementToBeClickable(By.className("radius")));
//        usernameLocator.sendKeys(invalidUsername);
//        passwordLocator.sendKeys(invalidPassword);
//        loginButtonLocator.click();

        /**
         *
         * Main Method
         *    ↓
         * Load Properties
         *    ↓
         * Initialize Driver
         *    ↓
         * Run Test Case 1 (Valid Login)
         *    ↓
         * Run Test Case 2 (Invalid Login)
         *    ↓
         * Close Driver
         *    ↓
         * Write Results
         *
         */
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        usernameLocator = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        passwordLocator = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("radius")));

    }
    static void testValidLogin()
    {
        usernameLocator.sendKeys(validUsername);
        passwordLocator.sendKeys(validPassword);
        loginButton.click();
        logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[normalize-space()='Logout']")));
        driver.close();

    }
}
