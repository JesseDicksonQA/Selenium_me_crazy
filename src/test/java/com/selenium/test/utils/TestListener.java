package com.selenium.test.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TestListener class that implements TestNG's ITestListener interface.
 * This class is responsible for capturing screenshots on test failure and
 * enhancing test reporting with ReportNG.
 */
public class TestListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);
    private static final String SCREENSHOT_DIR = "C:\\Users\\jesse\\Code_away\\Selenium_me_crazy\\test-output\\screenshots";
    
    // Create the screenshots directory at class initialization time
    static {
        File screenshotDir = new File(SCREENSHOT_DIR);
        if (!screenshotDir.exists()) {
            boolean created = screenshotDir.mkdirs();
            System.out.println("Creating screenshot directory: " + SCREENSHOT_DIR + " - Success: " + created);
            if (!created) {
                System.err.println("WARNING: Failed to create screenshot directory: " + SCREENSHOT_DIR);
            }
        }
    }

    /**
     * Called when a test starts
     */
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test Started: {}", result.getName());
    }

    /**
     * Called when a test succeeds
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test Passed: {}", result.getName());
    }

    /**
     * Called when a test fails
     * This method will capture a screenshot of the browser when a test fails
     */
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test Failed: {}", result.getName());
        System.out.println("Test Failed: " + result.getName() + " - Taking screenshot");
        
        try {
            // Import the test class directly
            com.selenium.test.AutomationExerciseTest test = (com.selenium.test.AutomationExerciseTest) result.getInstance();
            
            // Get static ThreadLocal driver field via reflection
            Field driverField = test.getClass().getDeclaredField("driver");
            driverField.setAccessible(true); // Allow access to private field
            ThreadLocal<WebDriver> driverThreadLocal = (ThreadLocal<WebDriver>) driverField.get(null);
            
            // Get WebDriver from ThreadLocal
            WebDriver driver = driverThreadLocal.get();
            
            if (driver != null) {
                System.out.println("Found driver instance: " + driver.getClass().getName());
                captureScreenshot(driver, result.getName());
            } else {
                System.err.println("Driver is null, cannot capture screenshot");
            }
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage(), e);
            System.err.println("ERROR taking screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Called when a test is skipped
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info("Test Skipped: {}", result.getName());
    }

    /**
     * Called when a test fails but is within success percentage
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.info("Test Failed But Within Success Percentage: {}", result.getName());
    }

    /**
     * Called when the test run starts
     */
    @Override
    public void onStart(ITestContext context) {
        logger.info("Test Suite Started: {}", context.getName());
        
        // Create screenshot directory if it doesn't exist
        File screenshotDir = new File(SCREENSHOT_DIR);
        if (!screenshotDir.exists()) {
            boolean created = screenshotDir.mkdirs();
            if (created) {
                logger.info("Created screenshot directory: {}", SCREENSHOT_DIR);
            } else {
                logger.error("Failed to create screenshot directory: {}", SCREENSHOT_DIR);
            }
        }
    }

    /**
     * Called when the test run finishes
     */
    @Override
    public void onFinish(ITestContext context) {
        logger.info("Test Suite Finished: {}", context.getName());
        logger.info("Passed tests: {}", context.getPassedTests().size());
        logger.info("Failed tests: {}", context.getFailedTests().size());
        logger.info("Skipped tests: {}", context.getSkippedTests().size());
    }

    /**
     * Captures a screenshot and saves it to the screenshots directory
     * 
     * @param driver WebDriver instance
     * @param testName Name of the test that failed
     */
    private void captureScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            logger.error("Cannot capture screenshot: WebDriver is null");
            System.err.println("SCREENSHOT ERROR: WebDriver is null");
            return;
        }
        
        try {
            // Create the screenshots directory again just to be sure
            File screenshotDir = new File(SCREENSHOT_DIR);
            if (!screenshotDir.exists()) {
                boolean created = screenshotDir.mkdirs();
                logger.info("Creating screenshot directory on-demand: {} - Success: {}", SCREENSHOT_DIR, created);
                System.out.println("Creating screenshot directory on-demand: " + SCREENSHOT_DIR + " - Success: " + created);
            }
            
            // Create a timestamp for unique file names
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotFileName = testName + "_" + timestamp + ".png";
            
            // Take screenshot
            logger.info("Taking screenshot using driver: {}", driver.getClass().getName());
            System.out.println("Taking screenshot using driver: " + driver.getClass().getName());
            
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            logger.info("Screenshot captured to temp file: {}", source.getAbsolutePath());
            System.out.println("Screenshot captured to temp file: " + source.getAbsolutePath());
            
            // Save the screenshot
            File destination = new File(SCREENSHOT_DIR + File.separator + screenshotFileName);
            FileUtils.copyFile(source, destination);
            
            logger.info("Screenshot saved to: {}", destination.getAbsolutePath());
            System.out.println("Screenshot saved to: " + destination.getAbsolutePath());
            
            // Verify the file was created
            if (destination.exists()) {
                logger.info("Screenshot file verified to exist: {}", destination.getAbsolutePath());
            } else {
                logger.error("Screenshot file does not exist after save: {}", destination.getAbsolutePath());
                System.err.println("SCREENSHOT ERROR: File does not exist after save: " + destination.getAbsolutePath());
            }
            
            // For ReportNG integration - store screenshot path in system property
            System.setProperty("org.uncommons.reportng.escape-output", "false");
            String screenshotPath = "screenshots/" + screenshotFileName;
            String htmlLink = "<a href='" + screenshotPath + "' target='_blank'>View Screenshot</a>";
            org.testng.Reporter.log(htmlLink);
            
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage(), e);
            System.err.println("SCREENSHOT ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
