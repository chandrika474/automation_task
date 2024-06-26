Feature: Flight Booking and Payment Functionality

  Scenario Outline: Flight booking confirmation with available flights
    Given I navigate to home page
    And I search with "<SheetName>" and <Row> data with current date
    And I choose available flight from search results
#    And I verify "<From>", "<To>" details and click on continue button

    Examples:
      | SheetName |  | Row |
      | Sheet1    |  | 0   |
      | Sheet1    |  | 1   |