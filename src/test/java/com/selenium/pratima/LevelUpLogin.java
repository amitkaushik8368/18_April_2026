package com.selenium.pratima;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.selenium.driver.DriverManager;
import com.selenium.pages.DashBoard;
import com.selenium.pages.LoginPage;
import com.selenium.utilities.AdvanceScreenshotHelper;
import com.selenium.utilities.DataRepository;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import static org.assertj.core.api.Assertions.assertThat;


public class LevelUpLogin
{
    public static final AtomicInteger counter = new AtomicInteger();
    public static final Logger logger = Logger.getLogger(LevelUpLogin.class.getName());
    static ExtentReports reports = new ExtentReports();
    static ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\src\\test\\resources\\test_reports\\CombinedExtentReport.html");
    public static void main(String[] args) throws Exception
    {
        //public static final AtomicInteger counter = new AtomicInteger();
        FileHandler fileHandler = new FileHandler(System.getProperty("user.dir") + "\\src\\test\\resources\\heroku_logs\\heroku.log", true);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
        logger.info("Let's begin our Test Execution");
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Runnable task1 = () -> {
            try {
                loginPageLoadTest();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable task2 = () -> {
            try {
                validLogin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        Runnable task3 = () -> {
          try {
              invalidUsernameLogin();
          }  catch (IOException e){
              throw new RuntimeException(e);
          }
        };
        Runnable task4 = ()->{
          try {
              invalidPasswordLogin();
          }  catch (IOException e){
              throw new RuntimeException(e);
          }
        };
        Runnable task5 = () -> {
            try {
                logoutCheck();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        DataRepository.loadData();
        logger.info("Data properties are loaded");
        reports.attachReporter(reporter);

        executorService.submit(task1);
        executorService.submit(task2);
        executorService.submit(task3);
        executorService.submit(task4);
        executorService.submit(task5);
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES); //wait indefinitely
        reports.flush();
        //logger.info("All tests executed and report generated");
    }

    static void loginPageLoadTest() throws IOException {
        // This test is to validate whether login page is loading up successfully
        //logger.info("Login Page Load test execution started");
        DriverManager driverManager = new DriverManager();
        driverManager.setDriver();
        //logger.info("Edge browser set for usage");
        driverManager.getDriver().get(DataRepository.getUrl());
        System.out.println(driverManager.getDriver());
        WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains("Internet"));
        String pageTitle = driverManager.getDriver().getTitle();
        ExtentTest test = reports.createTest("Login Page Load");
        try{
            assertThat(pageTitle).contains("Inter7net");
            test.pass("The login Page is Loaded");
        } catch (AssertionError e)
        {
            test.fail("Login Page did not Load" + e.getMessage());
            test.addScreenCaptureFromPath(AdvanceScreenshotHelper.takeScreenshot(driverManager.getDriver()), "Error Snap");
            logger.info("Login Page Load Test has failed");
            //test.
        }finally {
            driverManager.teardown();
            //logger.info("Login Page Load test execution ended");
        }
    }

    static void validLogin() throws IOException {
        // Validate login with correct credentials
        //logger.info("Valid Login test execution started");
        DriverManager driverManager = new DriverManager();
        driverManager.setDriver();
        driverManager.getDriver().get(DataRepository.getUrl());
        System.out.println(driverManager.getDriver());
        LoginPage loginPage = new LoginPage(driverManager.getDriver());
        loginPage.locateUsername().sendKeys(DataRepository.getUserName());
        loginPage.locatePassword().sendKeys(DataRepository.getPassWord());
        loginPage.locateLoginButton().click();
        DashBoard dashBoard = new DashBoard(driverManager.getDriver());
        String displayMessage = dashBoard.loggedInMessage.getText();
        ExtentTest test = reports.createTest("Valid Login");
        try {
            assertThat(displayMessage).contains("You lo7gged");
            test.pass("The Test has passed");
        } catch (AssertionError e)
        {
            test.fail("The test has failed : " + e.getMessage());
            test.addScreenCaptureFromPath(AdvanceScreenshotHelper.takeScreenshot(driverManager.getDriver()), "Valid Login Error Snap");
            logger.info("Valid Login Test has failed");
        } finally {
            driverManager.teardown();
            //logger.info("Valid Login test execution ended");
        }

    }

    static void invalidUsernameLogin() throws IOException {
        // Validate the right error message for invalid username
        //logger.info("InValid Username Login test execution started");
        DriverManager driverManager = new DriverManager();
        driverManager.setDriver();
        driverManager.getDriver().get(DataRepository.getUrl());
        System.out.println(driverManager.getDriver());
        LoginPage loginPage = new LoginPage(driverManager.getDriver());
        loginPage.locateUsername().sendKeys(DataRepository.getInvalidUsername());
        loginPage.locatePassword().sendKeys(DataRepository.getPassWord());
        loginPage.locateLoginButton().click();
        ExtentTest test = reports.createTest("Invalid Username");
        try {
            assertThat(loginPage.locateLoginErrorMessage().getText()).contains("usern7ame is invalid");
            test.pass("The Test has passed");
        } catch (AssertionError e)
        {
            test.fail("The test has failed : " + e.getMessage());
            test.addScreenCaptureFromPath(AdvanceScreenshotHelper.takeScreenshot(driverManager.getDriver()), "Error Snap");
            logger.info("Invalid Username Test has failed");
        }finally {
            driverManager.teardown();
            //logger.info("InValid Username Login test execution ended");
        }
    }


    static void invalidPasswordLogin() throws IOException {
        // Validate the right error message for invalid password
        //logger.info("InValid Password Login test execution started");
        DriverManager driverManager = new DriverManager();
        driverManager.setDriver();
        driverManager.getDriver().get(DataRepository.getUrl());
        System.out.println(driverManager.getDriver());
        LoginPage loginPage = new LoginPage(driverManager.getDriver());
        loginPage.locateUsername().sendKeys(DataRepository.getUserName());
        loginPage.locatePassword().sendKeys(DataRepository.getInvalidPassword());
        loginPage.locateLoginButton().click();
        ExtentTest test = reports.createTest("Invalid Password");
        try {
            assertThat(loginPage.locateLoginErrorMessage().getText()).contains("passwor7d is invalid");
            test.pass("The Test has passed");
        } catch (AssertionError e)
        {
            test.fail("The test has failed : " + e.getMessage());
            test.addScreenCaptureFromPath(AdvanceScreenshotHelper.takeScreenshot(driverManager.getDriver()), "Error Snap");
            logger.info("Invalid Password Login Test has failed");
        }finally {
            driverManager.teardown();
            //logger.info("InValid Password Login test execution ended");
        }
    }

    static void logoutCheck() throws IOException {
        // Validate user can log out after login
        //logger.info("Logout check test execution started");
        DriverManager driverManager = new DriverManager();
        driverManager.setDriver();
        driverManager.getDriver().get(DataRepository.getUrl());
        System.out.println(driverManager.getDriver());
        LoginPage loginPage = new LoginPage(driverManager.getDriver());
        loginPage.locateUsername().sendKeys(DataRepository.getUserName());
        loginPage.locatePassword().sendKeys(DataRepository.getPassWord());
        loginPage.locateLoginButton().click();
        DashBoard dashBoard = new DashBoard(driverManager.getDriver());
        String displayMessage = dashBoard.loggedInMessage.getText();
        ExtentTest test = reports.createTest("Logout Check");
        try {
            assertThat(displayMessage).contains("You logged");
        } catch (AssertionError e)
        {
            test.fail("The test has failed : " + e.getMessage());
            test.addScreenCaptureFromPath(AdvanceScreenshotHelper.takeScreenshot(driverManager.getDriver()), "Error Snap");
        }

        dashBoard.logoutButton.click();
        try{
            assertThat(driverManager.getDriver().getTitle()).contains("Inte7rnet");
            test.pass("User can logout successfully");
        } catch (AssertionError e)
        {
            test.fail("User isn't able to logout : " + e.getMessage());
            test.addScreenCaptureFromPath(AdvanceScreenshotHelper.takeScreenshot(driverManager.getDriver()), "Error Snap");
            logger.info("Login Check Test has failed");
        }finally {
            driverManager.teardown();
            //logger.info("Logout check test execution ended");
        }
    }
}
