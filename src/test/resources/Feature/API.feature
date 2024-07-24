Feature:Post user data and Get user data

 Scenario Outline: Create user data with userid get User data
    Given I read api test data from "<SheetName>"
    When I send a POST request to "<endpoint>" using <row> row request body
    And I validate the response code to be 201
    And I retrieve created "<endpoint>" details from the system
    And I validate get the response code to be 200
    And I compare both responses
    Then I write the response to "<SheetName>" and <row> in Excel file
   Examples:
     | SheetName | | row  |  |  endpoint |
     |  Sheet3   | | 1    |   | users |
