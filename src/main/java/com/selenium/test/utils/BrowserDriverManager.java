package com.selenium.test.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The BrowserDriverManager class is responsible for setting up and managing the WebDriver instances.
 * This class handles browser initialization, configuration, and cleanup.
 */
public class BrowserDriverManager {
    
    private static final Logger logger = LoggerFactory.getLogger(BrowserDriverManager.class);
    
    /**
     * Creates and configures a WebDriver instance based on the specified browser type.
     * 
     * @param browserType The type of browser to initialize (e.g., "chrome", "edge")
     * @return A configured WebDriver instance
     */
    public static WebDriver getDriver(String browserType) {
        WebDriver driver;
        
        switch (browserType.toLowerCase()) {
            case "chrome":
                logger.info("Initializing Chrome browser");
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                driver = new ChromeDriver(chromeOptions);
                break;
                
            case "edge":
                logger.info("Initializing Edge browser");
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                driver = new EdgeDriver(edgeOptions);
                break;
                
            default:
                logger.info("Browser type not recognized. Defaulting to Chrome.");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }
        
        // Maximize browser window
        driver.manage().window().maximize();
        logger.info("Browser window maximized");
        
        return driver;
    }
    
    /**
     * Safely quits the WebDriver instance and performs cleanup.
     * 
     * @param driver The WebDriver instance to quit
     */
    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            logger.info("Quitting WebDriver session");
            driver.quit();
        }
    }
}
