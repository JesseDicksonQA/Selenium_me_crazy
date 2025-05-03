@echo off
echo Setting up Maven from project downloads directory...

:: ========================================================================
:: IMPORTANT: Update the path below if your project is in a different location
:: ========================================================================
set PROJECT_PATH=C:\Users\jesse\Code_away\Selenium_me_crazy
set M2_HOME=%PROJECT_PATH%\downloads\apache-maven-3.9.6
set PATH=%M2_HOME%\bin;%PATH%

echo.
echo Maven location: %M2_HOME%
echo.

echo Maven version:
call %M2_HOME%\bin\mvn.cmd -version

echo.
echo Running Selenium tests...
echo.

:: Run Maven test command
call %M2_HOME%\bin\mvn.cmd clean test

echo.
echo Test execution completed.
echo.

:: Copy ReportNG reports to test-output/reports
echo Running report copy script...
call %PROJECT_PATH%\copy-reports.bat
echo.

pause
