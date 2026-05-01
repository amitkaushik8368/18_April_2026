package com.selenium.utilities;

import com.selenium.pratima.LevelUpLogin;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


public class AdvanceScreenshotHelper
{
    public static String takeScreenshot(WebDriver driver) throws IOException {
        int counterValueUsed = LevelUpLogin.counter.incrementAndGet();
        String path = System.getProperty("user.dir") + "\\src\\test\\resources\\screenshots\\screenshot_" + counterValueUsed + ".png";
        File dest = new File(path);
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        Files.copy(src.toPath(),dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return path;
    }
}
