package com.selenium.utilities;

import com.selenium.pratima.LevelUpLogin;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ScreenshotHelper
{
    static LocalDateTime localDateTime;
    static DateTimeFormatter formatter;
    static String dateTime;
    static File dest;
    public static void takeScreenshot(WebDriver driver) throws IOException {
        localDateTime = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("ddMMyyy_hhmmss");
        dateTime = formatter.format(localDateTime);
        dest = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\screenshots\\screenshot" + dateTime + ".png");
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        LevelUpLogin.logger.info("Screenshot Taken");
    }

    public static String  getScreenshotFile()
    {
        return System.getProperty("user.dir") + "\\src\\test\\resources\\screenshots\\screenshot" + dateTime + ".png";
    }
}
