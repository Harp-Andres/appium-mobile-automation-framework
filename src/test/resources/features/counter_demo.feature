@mobile
Feature: Counter Demo Interaction
  As a user of the ExpandTesting mobile app
  I want to interact with the Counter Demo feature
  So that I can validate the Appium automation is working correctly

  Background:
    Given mobile execution is enabled

  Scenario: Verify counter starts at default value
    Given the user is on the application home page
    And the application title should be "The Practice App"
    When the user navigates to the counter demo screen
    Then the counter should be visible
    Then the counter value should be "0"

  Scenario: Increment counter multiple times and verify value
    Given the user is on the application home page
    And the application title should be "The Practice App"
    When the user navigates to the counter demo screen
    And the user increments the counter 3 times
    Then the counter should be visible
    And the counter value should be "3"

  Scenario: Navigate to Counter Demo and increment counter
    Given the user is on the application home page
    And the application title should be "The Practice App"
    When the user navigates to the counter demo screen
    And the user increments the counter 2 times
    And the user reset the counter
    Then the counter should be visible
    And the counter value should be "0"
