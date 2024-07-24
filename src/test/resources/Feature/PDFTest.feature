Feature: PDF Read Functionality
  Scenario Outline: Compare the PDF text with test data
    Given I read test data from "<Sheet>" at <Row>
    Then I validate text with PDF file content located at "<PDFPath>"


    Examples:
      | Sheet  |  | Row  | |  PDFPath    |
      | Sheet2 |  |  0   | |  sample.pdf  |
