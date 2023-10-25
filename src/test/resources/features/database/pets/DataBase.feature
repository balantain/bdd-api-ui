Feature: Adding PetObject into database

  @AddPetsFromStoreToDB
  Scenario: Insert 10 existing pets from petstore API into Database
    Given Use base request specification
    And   Pet's desired statuses are:
      | available |
    When Trigger GET request with "/pet/findByStatus" endpoint
    And  Save 10 first pets from response into database


  @AddCustomPetToDB
  Scenario: Add custom Pet to DB
    Given Insert pets into database with values:
      | pet_id | category_id | category_name | name | photourls                                                                                | tags_id | tags_name | status    |
      | 333    | 1           | cat           | Max  | https://t4.ftcdn.net/jpg/00/97/58/97/240_F_97589769_t45CqXyzjz0KXwoBZT9PRaWGHRk5hQqQ.jpg | 1       | cute      | available |
    When Select pet form database by name "Max"
    Then Validate pet from database has pet_id 333

  @CheckPetCreation
  Scenario: Check Pet creation
    Given Print pets with the following parameters:
      | pet_id | category_id | category_name | name | photourls    | tags_id | tags_name   | status    |
      | 111    | 1           | cat           | Max  | link1        | 1       | cute        | available |
      | 222    |             | cat           | Alex | link1, link2 | 1,2     | cute, angry |           |
      | 333    |             |               | Ann  |              |         |             |           |
      |        |             |               |      |              |         |             |           |
    And Print pets with the following parameters:
    |name|status|
    |BOW |sold  |

  @CheckAddingInDB
  Scenario: Check Pet creation
    Given Add pets with the following parameters into Database:
      | pet_id | category_id | category_name | name | photourls    | tags_id | tags_name   | status    |
      | 111    | 1           | cat           | Max  | link1        | 1       | cute        | available |
      | 222    |             | cat           | Alex | link1, link2 | 1,2     | cute, angry |           |
      | 333    |             |               | Ann  |              |         |             |           |