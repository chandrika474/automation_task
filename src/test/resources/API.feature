Feature:Post user data and Get user data

 Scenario Outline: Create user data with userid get User data
    Given I read api test data from "<SheetName>"
    When I send a POST request to "<endpoint>" using <row> row and "<flag>" request body
    And I validate the response code to be 201
    And I retrieve created "<endpoint>" and "<flag>" details from the system
    And I validate get the response code to be 200
    And I compare both responses
    Then I write the response to "<SheetName>" and <row> in Excel file
    
   Examples:
     | SheetName | | row  |  |  endpoint | flag |
     |  Sheet3   | | 1    |  | 	users 	 | true |
	
Scenario Outline: Get the http request 400 and 404
	
	Given I read api test data from "<SheetName>"
    When I send a POST request to "<endpoint>" using <row> row and "<flag>" request body
    And I validate the response code to be 400
    And I get "users/23" and "<flag>" details from the system
    And I validate get the response code to be 404
    
    Examples:
     | SheetName | | row  |  |  endpoint | flag |
     |  Sheet4   | | 1    |  | 	login 	 | true |
     
 Scenario Outline: Get the http request 401
	
	Given I read api test data from "<SheetName>"
    When I send a POST request to "<endpoint>" using <row> row and "<flag>" request body
    And I validate the response code to be 401
     
    Examples:
     | SheetName | | row  |  |  endpoint | flag |
     |  Sheet4   | | 2    |  | 	v2/posts | false|