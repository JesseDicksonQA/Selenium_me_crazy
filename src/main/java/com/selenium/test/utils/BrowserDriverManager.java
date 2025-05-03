package com.selenium.test.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

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
     * @param useGrid Whether to use Selenium Grid or local WebDriver
     * @return A configured WebDriver instance
     */
    public static WebDriver getDriver(String browserType, boolean useGrid) {
        // If grid is enabled, use RemoteWebDriver instead of local WebDriver
        if (useGrid) {
            return getRemoteDriver(browserType);
        }
        
        // Otherwise use local WebDriver (original implementation)
        WebDriver driver;
        
        switch (browserType.toLowerCase()) {
            case "chrome":
                logger.info("Initializing local Chrome browser");
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                driver = new ChromeDriver(chromeOptions);
                break;
                
            case "edge":
                logger.info("Initializing local Edge browser");
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
     * For backward compatibility - returns a local WebDriver instance
     * 
     * @param browserType The type of browser to initialize
     * @return A local WebDriver instance
     */
    public static WebDriver getDriver(String browserType) {
        // Use the new method with useGrid = false for local execution
        return getDriver(browserType, false);
    }

    /**
     * Creates a RemoteWebDriver instance that connects to Selenium Grid.
     * 
     * @param browserType The type of browser to request from the grid
     * @return A RemoteWebDriver instance connected to the Selenium Grid hub
     */
    private static WebDriver getRemoteDriver(String browserType) {
        WebDriver driver;
        String hubUrl = System.getProperty("selenium.grid.hubUrl", "http://localhost:4444/wd/hub");
        
        try {
            URL gridUrl = new URL(hubUrl);
            
            switch (browserType.toLowerCase()) {
                case "chrome":
                    logger.info("Initializing Chrome browser on Selenium Grid at {}", hubUrl);
                    ChromeOptions chromeOptions = new ChromeOptions();
                    driver = new RemoteWebDriver(gridUrl, chromeOptions);
                    break;
                    
                case "edge":
                    logger.info("Initializing Edge browser on Selenium Grid at {}", hubUrl);
                    EdgeOptions edgeOptions = new EdgeOptions();
                    driver = new RemoteWebDriver(gridUrl, edgeOptions);
                    break;
                    
                default:
                    logger.info("Browser type not recognized. Defaulting to Chrome on Grid.");
                    driver = new RemoteWebDriver(gridUrl, new ChromeOptions());
                    break;
            }
            
            // Maximize browser window
            driver.manage().window().maximize();
            logger.info("Remote browser window maximized");
            
        } catch (MalformedURLException e) {
            logger.error("Error connecting to Selenium Grid: {}", e.getMessage());
            throw new RuntimeException("Failed to connect to Selenium Grid: " + e.getMessage(), e);
        }
        
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