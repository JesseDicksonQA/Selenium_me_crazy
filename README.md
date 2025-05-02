# Selenium WebDriver Test Framework

This project is a basic Selenium WebDriver test framework using Java, Maven, and TestNG to test the Automation Exercise website. The framework is designed to be easy to understand, maintain, and extend.

## Project Structure

```
selenium-test-framework/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/selenium/test/utils/
│   │   │       └── WebDriverManager.java     # Manages WebDriver instances
│   │   └── resources/
│   └── test/
│       ├── java/
│       │   └── com/selenium/test/
│       │       └── AutomationExerciseTest.java  # Test class for the website
│       └── resources/
│           ├── logback.xml                   # Logging configuration
│           └── testng.xml                    # TestNG suite configuration
└── pom.xml                                  # Maven project configuration
```

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6.3 or higher
- Chrome or Edge browser installed

## How to Run Tests

### Using the Run Script (Recommended)

1. Clone or download this repository
2. Navigate to the project directory
3. Double-click the `run-test.bat` file or run it from a command prompt

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
- **Clear Documentation**: Well-documented code with comments explaining each section
- **Logging**: Detailed logging of test execution for troubleshooting
- **Simple Structure**: Organized for easy understanding and maintenance

## Current Test Cases

1. **Verify Website Title**: Navigates to the Automation Exercise website and verifies that the page title is correct

## Adding New Tests

To add new tests:

1. Create a new test method in `AutomationExerciseTest.java` or create a new test class
2. Update the `testng.xml` file if you create a new test class
3. Follow the existing pattern of clear documentation and logging

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

## Download Log

All downloaded components are documented in the `downloads/download_log.txt` file, including:

1. Apache Maven 3.9.6
2. ChromeDriver (downloaded automatically by WebDriverManager)
3. Maven dependencies from Maven Central Repository
