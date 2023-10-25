@GetPets
Feature: Get Pets information

  @GetPetsHeaders
  Scenario: Validate response headers
    Given Use base request specification
    When Trigger GET request with "/pet/findByStatus" endpoint
    Then Response status code is 200
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

  @WithAvailableStatus
  Scenario: Get pets by available status
    Given Use base request specification
    And   Pet's desired statuses are:
      | available |
    When Trigger GET request with "/pet/findByStatus" endpoint
    Then Response status code is 200
    And  Number of pets in response is greater then 0
    And  Each pet object in response matches "petsArrayJsonSchema" schema

  @WithPendingStatus
  Scenario: Get pets by available status
    Given Use base request specification
    And   Pet's desired statuses are:
      | pending |
    When Trigger GET request with "/pet/findByStatus" endpoint
    Then Response status code is 200
    And  Number of pets in response is greater then 0
    And  Each pet object in response matches "petsArrayJsonSchema" schema

  @WithSoldStatus
  Scenario: Get pets by available status
    Given Use base request specification
    And   Pet's desired statuses are:
      | sold      |
    When Trigger GET request with "/pet/findByStatus" endpoint
    Then Response status code is 200
    And  Number of pets in response is greater then 0
    And  Each pet object in response matches "petsArrayJsonSchema" schema

  @WithAllStatuses
  Scenario: Get pets by available status
    Given Use base request specification
    And   Pet's desired statuses are:
      | available |
      | pending   |
      | sold      |
    When Trigger GET request with "/pet/findByStatus" endpoint
    Then Response status code is 200
    And  Number of pets in response is greater then 0
    And  Each pet object in response matches "petsArrayJsonSchema" schema

  @SinglePet
  Scenario: Single Pet object matches schema
    Given Use base request specification
    And   Pet's desired statuses are:
      | available |
    When Trigger GET request with "/pet/findByStatus" endpoint
    And  Save 10 pet's id number in the list
    And  Use base request specification
    And  Trigger GET request with "/pet" endpoint with saved id
    Then Pet object in response matches "petJsonSchema" schema

  @WithEmptyStatus
  Scenario: Get pets by empty status
    Given Use base request specification
    When Trigger GET request with "/pet/findByStatus" endpoint
    Then Response status code is 200
    And  Responce body has empty array

  @WithInvalidStatus
  Scenario: Get pets by available status
    Given Use base request specification
    And   Pet's desired statuses are:
      | invalid |
    When Trigger GET request with "/pet/findByStatus" endpoint
    Then Response status code is 200
    And  Response headers amount is 8
    And  Responce body has empty array