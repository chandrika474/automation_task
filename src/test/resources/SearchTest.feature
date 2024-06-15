Feature: Search Functionality
  Scenario: Search for Deloitte in Google
    Given I navigate to google home page
    When I enter "SearchKey" in search field
    Then I verify first link in the results contains "SearchKey"