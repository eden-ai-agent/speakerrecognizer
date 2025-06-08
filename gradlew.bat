@echo off
set DIR=%~dp0
if exist "%DIR%\gradle\wrapper\gradle-wrapper.jar" (
  java -jar "%DIR%\gradle\wrapper\gradle-wrapper.jar" %*
) else (
  echo gradle-wrapper.jar missing; falling back to system gradle 1>&2
  gradle %*
)
