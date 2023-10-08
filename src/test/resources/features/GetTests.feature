@GetPetsTests
Feature: Get Pets information

  Scenario: Get Pets information by valid status
    Given Base request specification
    And   Pet statuses are:
    |available|
    |sold     |
    When User triggers GET request with "/pet/findByStatus" endpoint
    Then Response status code is 200
    And  Number of pets in response is greater then 0
