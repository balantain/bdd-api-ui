@GetPetsTests
Feature: Get Pets information

  Scenario: Get Pets information by valid status
    Given Use base request specification
    And   Pet's desired statuses are:
    |available|
    |sold     |
    When Trigger GET request with "/pet/findByStatus" endpoint
    Then Response status code is 200
    And  Number of pets in response is greater then 0
    And  Each pet object in response matches "petsArrayJsonSchema" schema

  Scenario: Concrete Pet object matches schema
    Given Use base request specification
    And   Pet's desired statuses are:
      |available|
    When Trigger GET request with "/pet/findByStatus" endpoint
    And  Save 10 pet's id number in the list
    And  Use base request specification
    And  Trigger GET request with "/pet" endpoint with saved id
    Then Pet object in response matches "petJsonSchema" schema