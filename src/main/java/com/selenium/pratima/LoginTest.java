package com.selenium.pratima;

import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class LoginTest
{
    public static void main(String[] args) throws IOException {
        /**
         * Build a Selenium automation script that:
         *
         * 	1. Opens a login website  => https://the-internet.herokuapp.com/login
         * 	2. Attempts login with invalid credentials
         * 	3. Validates error message
         * 	4. Takes screenshot on failure
         * 	5. Logs results to CSV file
         */

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username : ");    // tomsmith
        String uname = scanner.nextLine();
        System.out.println("Enter password : ");    // SuperSecretPassword!
        String p_word = scanner.nextLine();
        boolean status;
        String basedir = System.getProperty("user.dir");

        WebDriver driver = new EdgeDriver();
        driver.get("https://the-internet.herokuapp.com/login");
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("radius")));
        username.sendKeys(uname);
        password.sendKeys(p_word);
        loginButton.click();
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flash")));   // //*[@id='flash']
        String errorMessageTest = errorMessage.getText().replaceAll("[^A-Za-z! ]", "").trim();   // Figure this out with ChatGPT
        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        String datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy_hhmmss"));
        File destFile;
        if (errorMessageTest.contains("Your username is invalid!")) {
            System.out.println("Login Failed with Error Message ==> " + errorMessageTest);
            status = false;
            destFile = new File(basedir + "\\src\\main\\ScreenShots\\LoginError_" + datetime + ".png");
            Files.copy(srcFile.toPath(),destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } else
        {
            status = true;
            System.out.println("Login Successful");
            destFile = new File(basedir + "\\src\\main\\ScreenShots\\LoginSuccessful_" + datetime + ".png");
            Files.copy(srcFile.toPath(),destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }


        driver.quit();
        File testResults = new File(basedir + "\\src\\main\\ScreenShots\\test_results.csv");
        try(FileWriter writer = new FileWriter(testResults)) {
            writer.write("Test_Name,Status,Error_Message_Displayed\n");
            if (status)
                writer.write("Valid_Login" + "," + "PASS" + "," + errorMessageTest);
            else writer.write("Invalid_Login" + "," + "PASS" + "," + errorMessageTest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
