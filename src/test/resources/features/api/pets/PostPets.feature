@PostPets
Feature: Post method tests for Pets endpoint

  @AddNewCustomPet
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

