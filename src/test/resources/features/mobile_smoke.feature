@mobile
Feature: Mobile smoke test
  To validate connectivity with Appium
  As a mobile QA
  I want to start an Appium session

  Scenario: Create mobile session and verify app launch
    Given mobile execution is enabled
    When I initialize the Appium driver
    Then the mobile session should be available
    When I query the app launch state
    Then the app should be running in foreground
