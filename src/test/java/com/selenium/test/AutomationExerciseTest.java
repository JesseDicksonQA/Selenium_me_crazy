package com.selenium.test;

import com.selenium.test.utils.BrowserDriverManager;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Test class for the Automation Exercise website.
 * This class contains test methods to verify basic functionality of the website.
 * 
 * The tests use Selenium WebDriver to automate browser interactions and TestNG for test assertions.
 */
public class AutomationExerciseTest {
    
    // Logger for capturing test execution information
    private static final Logger logger = LoggerFactory.getLogger(AutomationExerciseTest.class);
    
    // The website URL we are testing
    private static final String WEBSITE_URL = "https://www.automationexercise.com/";
    
    // Expected title of the website homepage
    private static final String EXPECTED_TITLE = "Automation Exercise";
    
    // WebDriver instance to control the browser
    private WebDriver driver;
    
    /**
     * Setup method that runs before each test method.
     * This method initializes the WebDriver based on the specified browser type.
     * 
     * @param browserType The type of browser to use for the test (specified in testng.xml)
     */
    @BeforeMethod
    @Parameters({"browser"})
    public void setup(String browserType) {
        logger.info("Setting up test with browser: {}", browserType);
        
        // Initialize the WebDriver based on the browser type
        driver = BrowserDriverManager.getDriver(browserType);
        
        logger.info("WebDriver initialized successfully");
    }
    
    /**
     * Test method to verify the website title.
     * 
     * This test performs the following steps:
     * 1. Navigate to the Automation Exercise website
     * 2. Get the actual title of the webpage
     * 3. Verify that the actual title matches the expected title
     */
    @Test
    public void verifyWebsiteTitle() {
        logger.info("Starting test: verifyWebsiteTitle");
        
        // Step 1: Navigate to the website URL
        logger.info("Navigating to URL: {}", WEBSITE_URL);
        driver.get(WEBSITE_URL);
        
        // Step 2: Get the actual title of the webpage
        String actualTitle = driver.getTitle();
        logger.info("Actual website title: {}", actualTitle);
        
        // Step 3: Verify that the actual title matches the expected title
        logger.info("Verifying title. Expected: {}, Actual: {}", EXPECTED_TITLE, actualTitle);
        Assert.assertEquals(actualTitle, EXPECTED_TITLE, "Website title is not as expected");
        
        logger.info("Title verification completed successfully");
    }
    
    /**
     * Cleanup method that runs after each test method.
     * This method properly closes the WebDriver instance.
     */
    @AfterMethod
    public void tearDown() {
        logger.info("Tearing down test");
        
        // Quit the WebDriver instance
        BrowserDriverManager.quitDriver(driver);
        
        logger.info("Test cleanup completed");
    }
}
