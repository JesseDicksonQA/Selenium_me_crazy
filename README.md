# Selenium WebDriver Test Framework

This project is a basic Selenium WebDriver test framework using Java, Maven, and TestNG to test the Automation Exercise website. The framework is designed to be easy to understand, maintain, and extend.

## Project Structure

```
selenium-test-framework/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/selenium/test/utils/
│   │   │       └── BrowserDriverManager.java   # Manages WebDriver instances with Grid support
│   │   └── resources/
│   └── test/
│       ├── java/
│       │   └── com/selenium/test/
│       │       └── AutomationExerciseTest.java  # Test class for the website
│       └── resources/
│           ├── logback.xml                   # Logging configuration
│           ├── testng.xml                    # TestNG suite configuration
│           └── grid-testng.xml               # Grid-specific TestNG config for parallel execution
├── downloads/
│   ├── apache-maven-3.9.6/                  # Maven installation
│   └── selenium-grid/                      # Selenium Grid server
├── run-test.bat                             # Script to run tests locally
├── run-grid-tests.bat                       # Script to run tests on Selenium Grid
├── start-hub.bat                            # Script to start Selenium Grid Hub
├── start-node.bat                           # Script to start Selenium Grid Node
└── pom.xml                                  # Maven project configuration
```

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6.3 or higher
- Chrome or Edge browser installed

## How to Run Tests

### Using the Local Run Script

1. Clone or download this repository
2. Navigate to the project directory
3. Double-click the `run-test.bat` file or run it from a command prompt

### Using Selenium Grid for Parallel Test Execution

1. Start the Selenium Grid Hub:
   - Double-click the `start-hub.bat` file or run it from a command prompt
   - Wait until you see a message indicating the hub is running at http://localhost:4444

2. Start the Selenium Grid Node:
   - Double-click the `start-node.bat` file or run it from a command prompt
   - Wait until you see a message indicating the node has registered with the hub

3. Run tests on the Grid:
   - Double-click the `run-grid-tests.bat` file or run it from a command prompt
   - Tests will be executed in parallel according to the configuration in `grid-testng.xml`

**Note:** If your installation path is different from `C:\Users\jesse\Code_away\Selenium_me_crazy`, you will need to update the path in the `run-test.bat` file:

```batch
:: Update this path if your project is installed in a different location
set M2_HOME=C:\Users\jesse\Code_away\Selenium_me_crazy\downloads\apache-maven-3.9.6
```

### Using Your Own Maven Installation

If you have Maven installed globally on your system:

1. Navigate to the project directory in a terminal or command prompt
2. Run the following Maven command:

```
mvn clean test
```

By default, tests will run in Chrome. To run with a different browser, modify the browser parameter in `testng.xml`.

## Key Features

- **Cross-browser Testing**: Supports Chrome and Edge browsers
- **Parallel Test Execution**: Using Selenium Grid for concurrent test runs
- **Test Grouping**: Tests organized into 'smoke' and 'regression' groups
- **Thread-Safe Design**: ThreadLocal WebDriver pattern for safe parallel execution
- **Clear Documentation**: Well-documented code with comments explaining each section
- **Logging**: Detailed logging of test execution for troubleshooting
- **Simple Structure**: Organized for easy understanding and maintenance

## Current Test Cases

1. **Verify Website Title** (Smoke Group): Navigates to the Automation Exercise website and verifies that the page title is correct
2. **Verify Women's Tops Item Count** (Regression Group): Navigates directly to the Women's Tops category page and verifies that there are exactly 6 items displayed

## Adding New Tests

To add new tests:

1. Create a new test method in `AutomationExerciseTest.java` or create a new test class
2. Assign the test to an appropriate group using the TestNG group annotation:
   ```java
   @Test(groups = "smoke") // or "regression" or a new group
   public void newTestMethod() {
       // Test implementation
   }
   ```
3. Update the appropriate TestNG XML file (either `testng.xml` or `grid-testng.xml`)
4. Follow the existing pattern of clear documentation and logging

## Dependencies

This project uses the following main dependencies:

- Selenium WebDriver: Browser automation framework
- TestNG: Test execution framework
- WebDriverManager: For automatic driver management
- Logback: For logging test execution details

All dependencies are managed in the `pom.xml` file.

## Included Tools

This project includes the following tools in the `downloads` directory:

- **Apache Maven 3.9.6**: Used for building and running the tests
  - Location: `downloads/apache-maven-3.9.6`
  - The `run-test.bat` file is configured to use this local Maven installation

- **Selenium Server**: Used for Selenium Grid functionality
  - Location: `downloads/selenium-grid/selenium-server.jar`
  - Used by `start-hub.bat` and `start-node.bat` to enable parallel test execution

## Download Log

All downloaded components are documented in the `downloads/download_log.txt` file, including:

1. Apache Maven 3.9.6
2. Selenium Server JAR (for Grid functionality)
3. ChromeDriver (downloaded automatically by WebDriverManager)
4. Maven dependencies from Maven Central Repository

## Using Selenium Grid

Selenium Grid allows you to run tests in parallel across multiple browsers and machines. This implementation includes:

### Thread-Safe WebDriver Management

The test framework uses the ThreadLocal pattern to ensure each test thread has its own WebDriver instance:

```java
private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
```

### Test Grouping Strategy

Tests are organized into logical groups:

- **Smoke Tests**: Quick tests that verify core functionality (e.g., verifyWebsiteTitle)
- **Regression Tests**: More comprehensive tests (e.g., verifyWomenTopsItemCount)

You can run specific groups by modifying the Grid TestNG configuration file.
