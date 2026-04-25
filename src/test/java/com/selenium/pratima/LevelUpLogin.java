package com.selenium.pratima;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.selenium.driver.DriverManager;
import com.selenium.pages.DashBoard;
import com.selenium.pages.LoginPage;
import com.selenium.utilities.DataRepository;

import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;


public class LevelUpLogin
{
    public static void main(String[] args) throws IOException, InterruptedException {
//        DriverManager driverManager = new DriverManager();
//        driverManager.setDriver();
//        driverManager.getDriver().manage().window().maximize();
//        LoadProperties.loadProperties();
//        driverManager.getDriver().get(LoadProperties.getUrl());
//        LoginPage loginPage = new LoginPage(driverManager.getDriver());
//        loginPage.username.sendKeys(LoadProperties.getUserName());
//        loginPage.password.sendKeys(LoadProperties.getPassWord());
//        loginPage.clickButton.click();
//        DashBoard dashBoard = new DashBoard(driverManager.getDriver());
//        String loginMessage = dashBoard.loggedInMessage.getText().replaceAll("[^A-Za-z ]", "").trim();
//
//        ExtentReports extentReports = new ExtentReports();
//        ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") +
//                "\\src\\test\\resources\\ExtentReport.html");
//        extentReports.attachReporter(reporter);
//        ExtentTest test = extentReports.createTest("Login Test");
//
//        try {
//            assertThat(loginMessage).isEqualTo("You logged into a secure area");
//            test.pass("Test Passed");
//        } catch (AssertionError e)
//        {
//            test.fail("Test Failed" + e.getMessage());
//        }
//        extentReports.flush();
//        Thread.sleep(3000);
//        driverManager.teardown();
        loginPageLoadTest();
        validLogin();
        invalidUsernameLogin();
        invalidPasswordLogin();
        logoutCheck();
    }

    static void loginPageLoadTest() throws IOException {
        // This test is to validate whether login page is loading up successfully

        DriverManager driverManager = new DriverManager();
        driverManager.setDriver();
        DataRepository.loadData();
        driverManager.getDriver().get(DataRepository.getUrl());
        driverManager.getDriver().manage().window().maximize();
        String pageTitle = driverManager.getDriver().getTitle();
        ExtentReports reports = new ExtentReports();
        ExtentTest test = reports.createTest("Login Page Load");
        ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\src\\test\\resources\\PageLoad.html");
        reports.attachReporter(reporter);
        try{
            assertThat(pageTitle).contains("Internet");
            test.pass("The login Page is Loaded");
        } catch (AssertionError e)
        {
            test.fail("Login Page Did not Load" + e.getMessage());
        }
        driverManager.teardown();
        reports.flush();
    }

    static void validLogin() throws IOException {
        // Validate login with correct credentials
        DriverManager driverManager = new DriverManager();
        driverManager.setDriver();
        driverManager.getDriver().manage().window().maximize();
        DataRepository.loadData();
        driverManager.getDriver().get(DataRepository.getUrl());
        LoginPage loginPage = new LoginPage(driverManager.getDriver());
        loginPage.locateUsername().sendKeys(DataRepository.getUserName());
        loginPage.locatePassword().sendKeys(DataRepository.getPassWord());
        loginPage.locateLoginButton().click();
        DashBoard dashBoard = new DashBoard(driverManager.getDriver());
        String displayMessage = dashBoard.loggedInMessage.getText();
        ExtentReports reports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\src\\test\\resources\\ValidLogin.html");
        reports.attachReporter(reporter);
        ExtentTest test = reports.createTest("Valid Login");
        try {
            assertThat(displayMessage).contains("You logged");
            test.pass("The Test has passed");
        } catch (AssertionError e)
        {
            test.fail("The test has failed : " + e.getMessage());
        }
        driverManager.teardown();
        reports.flush();
    }

    static void invalidUsernameLogin() throws IOException {
        // Validate the right error message for invalid username
        DriverManager driverManager = new DriverManager();
        driverManager.setDriver();
        driverManager.getDriver().manage().window().maximize();
        DataRepository.loadData();
        driverManager.getDriver().get(DataRepository.getUrl());
        LoginPage loginPage = new LoginPage(driverManager.getDriver());
        loginPage.locateUsername().sendKeys(DataRepository.getInvalidUsername());
        loginPage.locatePassword().sendKeys(DataRepository.getPassWord());
        loginPage.locateLoginButton().click();
        ExtentReports reports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\src\\test\\resources\\InvalidUsernameLogin.html");
        reports.attachReporter(reporter);
        ExtentTest test = reports.createTest("Invalid Username");
        try {
            assertThat(loginPage.locateLoginErrorMessage().getText()).contains("username is invalid");
            test.pass("The Test has passed");
        } catch (AssertionError e)
        {
            test.fail("The test has failed : " + e.getMessage());
        }
        driverManager.teardown();
        reports.flush();
    }
    static void invalidPasswordLogin() throws IOException {
        // Validate the right error message for invalid password
        DriverManager driver = new DriverManager();
        driver.setDriver();
        DataRepository.loadData();
        driver.getDriver().manage().window().maximize();
        driver.getDriver().get(DataRepository.getUrl());
        LoginPage loginPage = new LoginPage(driver.getDriver());
        loginPage.locateUsername().sendKeys(DataRepository.getUserName());
        loginPage.locatePassword().sendKeys(DataRepository.getInvalidPassword());
        loginPage.locateLoginButton().click();
        ExtentReports reports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\src\\test\\resources\\InvalidPasswordLogin.html");
        reports.attachReporter(reporter);
        ExtentTest test = reports.createTest("Invalid Password");
        try {
            assertThat(loginPage.locateLoginErrorMessage().getText()).contains("password is invalid");
            test.pass("The Test has passed");
        } catch (AssertionError e)
        {
            test.fail("The test has failed : " + e.getMessage());
        }
        driver.teardown();
        reports.flush();
    }
    static void logoutCheck() throws IOException {
        // Validate user can log out after login
        DriverManager driver = new DriverManager();
        driver.setDriver();
        DataRepository.loadData();
        driver.getDriver().manage().window().maximize();
        driver.getDriver().get(DataRepository.getUrl());
        LoginPage loginPage = new LoginPage(driver.getDriver());
        loginPage.locateUsername().sendKeys(DataRepository.getUserName());
        loginPage.locatePassword().sendKeys(DataRepository.getPassWord());
        loginPage.locateLoginButton().click();
        DashBoard dashBoard = new DashBoard(driver.getDriver());
        String displayMessage = dashBoard.loggedInMessage.getText();
        ExtentReports reports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\src\\test\\resources\\LogoutCheck.html");
        reports.attachReporter(reporter);
        ExtentTest test = reports.createTest("Logout Check");
        try {
            assertThat(displayMessage).contains("You logged");
        } catch (AssertionError e)
        {
            test.fail("The test has failed : " + e.getMessage());
        }

        dashBoard.logoutButton.click();
        try{
            assertThat(driver.getDriver().getTitle()).contains("Internet");
            test.pass("User can logout successfully");
        } catch (AssertionError e)
        {
            test.fail("User isn't able to logout : " + e.getMessage());
        }
        driver.teardown();
        reports.flush();
    }
}
