Feature: Verify the POST operation
  Scenario:Verifying the POST operation with body
    Given The request body for the POST operation
    |name|age|character|
    |ben |20 |good|
    When Perform the Post operation with "/anything"
    Then The response body should have key "name" and value "ben"

  Scenario: Verifying the POST operation by passing json from a file
    Given The request body for the POST operation from a file path "src/test/resources/TestJson/postEndpoint.json"
    When Perform the Post operation with "/anything"
    Then The response body should have key "name" and value "ben"