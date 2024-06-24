Feature: Flight Booking and Payment Functionality

  Scenario Outline: Flight booking confirmation with available flights
    Given I navigate to home page
    And I travel "<From>" to "<To>" on current date
    And I choose available flight from search results
#    And I verify "<From>", "<To>" details and click on continue button

    Examples:
      | From      |  | To     |
      | New Delhi |  | Mumbai |
      | Mumbai    |  | Hyderabad |