@mobile
Feature: TheApp login smoke
  As a mobile QA
  I want to log in through TheApp
  So that local Appium + POM wiring is validated end-to-end

  Background:
    Given mobile execution is enabled

  Scenario: Successful login shows secret area
    Given the user is on TheApp home screen
    When the user opens the login screen
    And the user logs in with username "alice" and password "mypassword"
    Then the secret area should show logged-in message
