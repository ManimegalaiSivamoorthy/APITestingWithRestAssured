Feature: Verify GET operations using various inputs
  Scenario: Verify that GET endpoint gives back status code 200 when no inputs are passed
    When Perform GET operation for the endpoint "/get"
    Then The status code should be 200


  Scenario: Verify that GET endpoint works well with the query parameter
    When Perform GET operation for the endpoint "/get" with query parameter "key" and value "value"
    Then The status code should be 200
    And The response body should contain the key "key" and value "value"

  Scenario Outline: Verify that status endpoints gives back the status codes which is passed as path parameter when get GET operation is performed
    When Perform GET operation for the endpoint "/status/<statusCode>"
    Then output status code must match the input <statusCode>
    Examples:
      |statusCode|
      |200|
      |201|
      |400|
      |404|
      |500|
      |503|

  Scenario: Verify that GET endpoint returns a simple Json
    When Perform GET operation for the endpoint "/json"
    Then Should get a simple json as response


