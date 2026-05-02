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


public class AdvanceScreenshotHelper
{
    public static String takeScreenshot(WebDriver driver) throws IOException
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_hhmmss");
        String datetime = formatter.format(localDateTime);
        int counterValueUsed = LevelUpLogin.counter.incrementAndGet();
        String path = System.getProperty("user.dir") + "\\src\\test\\resources\\screenshots\\screenshot_" +datetime + counterValueUsed + ".png";
        File dest = new File(path);
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        Files.copy(src.toPath(),dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return path;
    }
}
