@echo off
echo Starting Selenium Grid Node...

set SELENIUM_JAR=downloads\selenium-grid\selenium-server.jar

REM Check if the JAR file exists
if not exist %SELENIUM_JAR% (
    echo Error: Selenium Server JAR file not found at %SELENIUM_JAR%
    exit /b 1
)

echo Starting Selenium Grid Node to connect to hub at http://localhost:4444/...
java -jar %SELENIUM_JAR% node --hub http://localhost:4444

pause
