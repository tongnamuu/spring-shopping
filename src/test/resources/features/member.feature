Feature: Member registration and login

  Scenario: Register a new member
    When I register with email "test@example.com" and password "password123"
    Then the response status should be 201
    And the response should contain a token

  Scenario: Register with duplicate email
    Given a member exists with email "dup@example.com" and password "password123"
    When I register with email "dup@example.com" and password "password123"
    Then the response status should be 400

  Scenario: Login with valid credentials
    Given a member exists with email "login@example.com" and password "password123"
    When I login with email "login@example.com" and password "password123"
    Then the response status should be 200
    And the response should contain a token

  Scenario: Login with wrong password
    Given a member exists with email "wrong@example.com" and password "password123"
    When I login with email "wrong@example.com" and password "wrongpassword"
    Then the response status should be 400
