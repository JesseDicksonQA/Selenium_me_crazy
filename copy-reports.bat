@echo off
echo Copying ReportNG reports to test-output/reports...

:: Create the target directory if it doesn't exist
if not exist "test-output\reports" mkdir "test-output\reports"

:: Copy the HTML reports
xcopy /Y /E "target\surefire-reports\html\*.*" "test-output\reports\"

echo Report copying completed.
