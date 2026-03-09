Feature: Framework base health check
  To validate the technical base
  As an automation team
  I want to run a quick test without device

  Scenario: Load base configuration
    Given the framework loads the configuration
    When I query the active environment
    Then I should get a valid environment

