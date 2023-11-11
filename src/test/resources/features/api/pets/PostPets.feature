@PostPets
Feature: Post method tests for Pets endpoint

  @PostNewCustomPet
  Scenario: Adding custom pet in the store
    Given Use base request specification
    And Single Pet object with the folowing data is created:
      | pet_id | category_id | category_name | name | photourls    | tags_id | tags_name   | status    |
      | 318989 | 1           | cat           | Max  | link1        | 1       | cute        | available |
    And  Created pet is added to request body
    When Trigger POST request with "/pet" endpoint
    Then Response status code is 200
    And  Response body matches "petJsonSchema" schema
    When Save pet from response into constant
    Then Saved pet from response is equal to created pet
    When Trigger GET request with "/pet" endpoint with created pet id
    Then Response status code is 200
    And  Response body matches "petJsonSchema" schema
    When Save pet from response into constant
    Then Saved pet from response is equal to created pet

  @PostPetWithoutBody
  Scenario: Sending POST request with empty body
    Given Use base request specification
    When  Trigger POST request with "/pet" endpoint
    Then  Response status code is 405
    And  Response headers amount is 8
    And  Response headers are equal to:
      | header                       | value                                |
      | Content-Type                 | application/json                     |
      | Transfer-Encoding            | chunked                              |
      | Access-Control-Allow-Origin  | *                                    |
      | Access-Control-Allow-Methods | GET, POST, DELETE, PUT               |
      | Access-Control-Allow-Headers | Content-Type, api_key, Authorization |
      | Server                       | Jetty(9.2.9.v20150224)               |
      | Connection                   | keep-alive                           |
    And Response headers matches:
      | header | regex                                                   |
      | Date   | \w{3}[,]\s\d{2}\s\w{3}\s\d{4}\s\d{2}\:\d{2}\:\d{2}\sGMT |
    And   Error has code 405, type "unknown" and message:
    """
    no data
    """