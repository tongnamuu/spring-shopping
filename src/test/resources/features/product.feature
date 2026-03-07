Feature: Product CRUD

  Scenario: Create a product
    When I create a product with name "Keyboard" price 50000 and imageUrl "https://example.com/kb.png"
    Then the response status should be 201
    And the product response should have name "Keyboard"

  Scenario: Find a product by id
    Given a product exists with name "Mouse" price 30000 and imageUrl "https://example.com/mouse.png"
    When I find the product by id
    Then the response status should be 200
    And the product response should have name "Mouse"

  Scenario: Update a product
    Given a product exists with name "Old Name" price 10000 and imageUrl "https://example.com/old.png"
    When I update the product with name "New Name" price 20000 and imageUrl "https://example.com/new.png"
    Then the response status should be 200
    And the product response should have name "New Name"

  Scenario: Delete a product
    Given a product exists with name "ToDelete" price 10000 and imageUrl "https://example.com/del.png"
    When I delete the product
    Then the response status should be 204

  Scenario: Find all products
    Given a product exists with name "Item1" price 10000 and imageUrl "https://example.com/1.png"
    And a product exists with name "Item2" price 20000 and imageUrl "https://example.com/2.png"
    When I find all products
    Then the response status should be 200
