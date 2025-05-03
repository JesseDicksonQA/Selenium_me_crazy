package com.selenium.test;

import com.selenium.test.utils.BrowserDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

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
    
    // WebDriver instance to control the browser - using ThreadLocal for parallel test safety
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    /**
     * Setup method that runs before each test method.
     * This method initializes the WebDriver based on the specified browser type.
     * 
     * @param browserType The type of browser to use for the test (specified in testng.xml)
     * @param useGrid Whether to use Selenium Grid (specified in testng.xml)
     */
    @BeforeMethod
    @Parameters({"browser", "useGrid"})
    public void setup(String browserType, boolean useGrid) {
        logger.info("Setting up test with browser: {} on {} execution", 
                browserType, useGrid ? "grid" : "local");
        
        // Initialize the WebDriver based on the browser type and grid setting
        driver.set(BrowserDriverManager.getDriver(browserType, useGrid));
        
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
    @Test(groups = "smoke")
    public void verifyWebsiteTitle() {
        logger.info("Starting test: verifyWebsiteTitle");
        
        // Step 1: Navigate to the website URL
        logger.info("Navigating to URL: {}", WEBSITE_URL);
        driver.get().get(WEBSITE_URL);
        
        // Step 2: Get the actual title of the webpage
        String actualTitle = driver.get().getTitle();
        logger.info("Actual website title: {}", actualTitle);
        
        // Step 3: Verify that the actual title matches the expected title
        logger.info("Verifying title. Expected: {}, Actual: {}", EXPECTED_TITLE, actualTitle);
        Assert.assertEquals(actualTitle, EXPECTED_TITLE, "Website title is not as expected");
        
        logger.info("Title verification completed successfully");
    }
    
    /**
     * Test method to verify that there are 6 items in the Women's Tops category.
     * 
     * This test performs the following steps:
     * 1. Navigate directly to the Women's Tops category page
     * 2. Count the number of products displayed
     * 3. Verify that there are exactly 6 items in this category
     */
    @Test(groups = "regression")
    public void verifyWomenTopsItemCount() {
        logger.info("Starting test: verifyWomenTopsItemCount");
        
        // Step 1: Navigate directly to the Women's Tops category page
        // The direct URL for the Women's Tops category is used to avoid navigation issues
        String womenTopsCategoryUrl = WEBSITE_URL + "category_products/2";
        logger.info("Navigating directly to Women's Tops category: {}", womenTopsCategoryUrl);
        driver.get().get(womenTopsCategoryUrl);
        
        // Step 2: Count the number of products displayed
        // Wait a bit for the products to load completely
        try {
            Thread.sleep(2000);  // 2-second wait to ensure page loads
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted", e);
        }
        
        List<WebElement> productItems = driver.get().findElements(By.className("single-products"));
        int actualItemCount = productItems.size();
        logger.info("Number of items found in Women's Tops category: {}", actualItemCount);
        
        // Step 3: Verify there are exactly 6 items in this category
        int expectedItemCount = 6;
        logger.info("Verifying item count. Expected: {}, Actual: {}", expectedItemCount, actualItemCount);
        Assert.assertEquals(actualItemCount, expectedItemCount, "Number of items in Women's Tops category is not as expected");
        
        logger.info("Women's Tops item count verification completed successfully");
    }
    
    /**
     * Cleanup method that runs after each test method.
     * This method properly closes the WebDriver instance.
     */
    @AfterMethod
    public void tearDown() {
        logger.info("Tearing down test");
        
        // Quit the WebDriver instance and remove the thread-local reference
        BrowserDriverManager.quitDriver(driver.get());
        driver.remove();
        
        logger.info("Test cleanup completed");
    }
}
