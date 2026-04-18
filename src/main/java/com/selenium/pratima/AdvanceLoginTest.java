package com.selenium.pratima;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class AdvanceLoginTest
{
    public static void main(String[] args) throws FileNotFoundException {
        Properties properties = new Properties();
        System.out.println(System.getProperty("user.dir"));
        System.out.println(System.getProperty("user.dir")+"\\src\\main\\resources\\config.properties");//+"\\src\\main\\resources\\config.properties"));
        try(
                FileInputStream fis = new FileInputStream
                        (
                                System.getProperty("user.dir")+"\\src\\main\\resources\\config.properties"
                        )
        ){
           properties.load(fis);
           String username = properties.getProperty("username");
           String password = properties.getProperty("password");
           String url = properties.getProperty("url");

           System.out.println("Username for property file: " + username);
           System.out.println("Password for property file: " + password);
            System.out.println("Website to be Automated: " + url);
        }  catch(RuntimeException | IOException e) {
            System.out.println("Exception Occurred");
        }



    }
}
