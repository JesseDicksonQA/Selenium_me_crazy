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
import java.io.IOException;
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
        
        // Get the failed test's instance to access the WebDriver
        Object testInstance = result.getInstance();
        
        // Try to get the WebDriver from the test class
        WebDriver driver = null;
        try {
            // This assumes the test class has a field named 'driver'
            driver = (WebDriver) testInstance.getClass().getDeclaredField("driver").get(testInstance);
        } catch (Exception e) {
            logger.error("Could not access WebDriver instance from test class", e);
            return;
        }
        
        if (driver != null) {
            captureScreenshot(driver, result.getName());
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
        try {
            // Create a timestamp for unique file names
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotFileName = testName + "_" + timestamp + ".png";
            
            // Take screenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            
            // Save the screenshot
            File destination = new File(SCREENSHOT_DIR + File.separator + screenshotFileName);
            FileUtils.copyFile(source, destination);
            
            logger.info("Screenshot captured and saved: {}", destination.getAbsolutePath());
            
            // For ReportNG integration - store screenshot path in system property
            // ReportNG can access this in the HTML report
            System.setProperty("org.uncommons.reportng.escape-output", "false");
            String screenshotPath = "screenshots/" + screenshotFileName;
            String htmlLink = "<a href='" + screenshotPath + "' target='_blank'>View Screenshot</a>";
            org.testng.Reporter.log(htmlLink);
            
        } catch (IOException e) {
            logger.error("Failed to capture screenshot", e);
        }
    }
}
