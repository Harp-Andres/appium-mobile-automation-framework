@echo off
REM Downloads TheApp.apk for Windows self-hosted runners (same AUT as Unix script).
setlocal enabledelayedexpansion
set "ROOT=%~dp0.."
set "OUT=%ROOT%\apps"
set "DEST=%OUT%\TheApp.apk"
set "VER=v1.12.0"
if not "%THEAPP_VERSION%"=="" set "VER=%THEAPP_VERSION%"
set "URL=https://github.com/appium-pro/TheApp/releases/download/%VER%/TheApp.apk"

if not exist "%OUT%" mkdir "%OUT%"
if exist "%DEST%" (
  echo OK ^(cached^): %DEST%
  exit /b 0
)

echo Downloading TheApp.apk ^(%VER%^)...
curl.exe -fL --retry 3 -o "%DEST%.partial" "%URL%"
if errorlevel 1 (
  echo ERROR: download failed
  exit /b 1
)
move /Y "%DEST%.partial" "%DEST%" >nul
echo OK: %DEST%
exit /b 0
