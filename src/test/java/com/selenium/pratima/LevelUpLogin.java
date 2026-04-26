package com.selenium.pratima;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.selenium.driver.DriverManager;
import com.selenium.pages.DashBoard;
import com.selenium.pages.LoginPage;
import com.selenium.utilities.DataRepository;
import com.selenium.utilities.ScreenshotHelper;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

public class LevelUpLogin
{
    public static final Logger logger = Logger.getLogger(LevelUpLogin.class.getName());
    static ExtentReports reports = new ExtentReports();
    static ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\src\\test\\resources\\test_reports\\CombinedExtentReport.html");
    public static void main(String[] args) throws Exception
    {
        FileHandler fileHandler = new FileHandler(System.getProperty("user.dir") + "\\src\\test\\resources\\heroku_logs\\heroku.log", true);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
        logger.info("Let's begin our Test Execution");
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        DataRepository.loadData();
        logger.info("Data properties are loaded");
        reports.attachReporter(reporter);
        executorService.submit(() -> {
            try { loginPageLoadTest(); } catch (Exception e) {
                System.out.println("Exception Occurred in Test1: " + e.getMessage());
            }
        });

        logger.info("First test executed");
        executorService.submit(()->
        {
            try {validLogin(); }catch (Exception e){
                System.out.println("Exception Occurred in Test2 : " + e.getMessage());
            }
        });

        logger.info("Second test executed");

        executorService.submit(()->
        {
            try {invalidUsernameLogin(); }catch (Exception e){
                System.out.println("Exception Occurred in Test3 : " + e.getMessage());
            }
        });


        logger.warning("Third test executed");
        executorService.submit(()->
        {
            try {invalidPasswordLogin(); }catch (Exception e){
                System.out.println("Exception Occurred in Test4 : " + e.getMessage());
            }
        });

        logger.info("Fourth test executed");
        executorService.submit(()->
        {
            try {logoutCheck(); }catch (Exception e){
                System.out.println("Exception Occurred in Test5 : " + e.getMessage());
            }
        });

        logger.info("Fifth test executed");
        reports.flush();
        logger.info("Status of all tests are logged into the report");
        executorService.shutdown();
    }

    static void loginPageLoadTest() throws IOException {
        // This test is to validate whether login page is loading up successfully
        DriverManager driverManager = new DriverManager();
        driverManager.setDriver();
        logger.info("Edge browser set for usage");
        driverManager.getDriver().get(DataRepository.getUrl());
        String pageTitle = driverManager.getDriver().getTitle();
        ExtentTest test = reports.createTest("Login Page Load");
        try{
            assertThat(pageTitle).contains("Internet7");
            test.pass("The login Page is Loaded");
        } catch (AssertionError e)
        {
            test.fail("Login Page Did not Load" + e.getMessage());
            ScreenshotHelper.takeScreenshot(driverManager.getDriver());
            test.addScreenCaptureFromPath(ScreenshotHelper.getScreenshotFile(), "Error Snap");
            //test.
        }finally {
            driverManager.teardown();
        }
    }

    static void validLogin() throws IOException {
        // Validate login with correct credentials
        DriverManager driverManager = new DriverManager();
        driverManager.setDriver();
        driverManager.getDriver().get(DataRepository.getUrl());
        LoginPage loginPage = new LoginPage(driverManager.getDriver());
        loginPage.locateUsername().sendKeys(DataRepository.getUserName());
        loginPage.locatePassword().sendKeys(DataRepository.getPassWord());
        loginPage.locateLoginButton().click();
        DashBoard dashBoard = new DashBoard(driverManager.getDriver());
        String displayMessage = dashBoard.loggedInMessage.getText();
        ExtentTest test = reports.createTest("Valid Login");
        try {
            assertThat(displayMessage).contains("You log3ged");
            test.pass("The Test has passed");
        } catch (AssertionError e)
        {
            test.fail("The test has failed : " + e.getMessage());
            ScreenshotHelper.takeScreenshot(driverManager.getDriver());
            test.addScreenCaptureFromPath(ScreenshotHelper.getScreenshotFile(), "Valid Login Error Snap");
        } finally {
            driverManager.teardown();
        }

    }

    static void invalidUsernameLogin() throws IOException {
        // Validate the right error message for invalid username
        DriverManager driverManager = new DriverManager();
        driverManager.setDriver();
        driverManager.getDriver().get(DataRepository.getUrl());
        LoginPage loginPage = new LoginPage(driverManager.getDriver());
        loginPage.locateUsername().sendKeys(DataRepository.getInvalidUsername());
        loginPage.locatePassword().sendKeys(DataRepository.getPassWord());
        loginPage.locateLoginButton().click();
        ExtentTest test = reports.createTest("Invalid Udsername");
        try {
            assertThat(loginPage.locateLoginErrorMessage().getText()).contains("usersname is invalid");
            test.pass("The Test has passed");
        } catch (AssertionError e)
        {
            test.fail("The test has failed : " + e.getMessage());
            ScreenshotHelper.takeScreenshot(driverManager.getDriver());
            test.addScreenCaptureFromPath(ScreenshotHelper.getScreenshotFile(), "Error Snap");
        }finally {
            driverManager.teardown();
        }
    }


    static void invalidPasswordLogin() throws IOException {
        // Validate the right error message for invalid password
        DriverManager driverManager = new DriverManager();
        driverManager.setDriver();
        driverManager.getDriver().get(DataRepository.getUrl());
        LoginPage loginPage = new LoginPage(driverManager.getDriver());
        loginPage.locateUsername().sendKeys(DataRepository.getUserName());
        loginPage.locatePassword().sendKeys(DataRepository.getInvalidPassword());
        loginPage.locateLoginButton().click();
        ExtentTest test = reports.createTest("Invalid Password");
        try {
            assertThat(loginPage.locateLoginErrorMessage().getText()).contains("passwsord is invalid");
            test.pass("The Test has passed");
        } catch (AssertionError e)
        {
            test.fail("The test has failed : " + e.getMessage());
            ScreenshotHelper.takeScreenshot(driverManager.getDriver());
            test.addScreenCaptureFromPath(ScreenshotHelper.getScreenshotFile(), "Error Snap");
        }finally {
            driverManager.teardown();
        }
    }

    static void logoutCheck() throws IOException {
        // Validate user can log out after login
        DriverManager driverManager = new DriverManager();
        driverManager.setDriver();
        driverManager.getDriver().get(DataRepository.getUrl());
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
            ScreenshotHelper.takeScreenshot(driverManager.getDriver());
            test.addScreenCaptureFromPath(ScreenshotHelper.getScreenshotFile(), "Error Snap");
        }

        dashBoard.logoutButton.click();
        try{
            assertThat(driverManager.getDriver().getTitle()).contains("Intkernet");
            test.pass("User can logout successfully");
        } catch (AssertionError e)
        {
            test.fail("User isn't able to logout : " + e.getMessage());
            ScreenshotHelper.takeScreenshot(driverManager.getDriver());
            test.addScreenCaptureFromPath(ScreenshotHelper.getScreenshotFile(), "Error Snap");
        }finally {
            driverManager.teardown();
        }
    }
}
