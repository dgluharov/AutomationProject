Feature: Shop features
  In order to operate with shop
  As as client
  I want options to add and remove items to the cart

  Scenario Outline: Add items to the cart
    Given An logged user is on the shop page
    And the user adds the following items to the cart:
      | Item 1  | Item 2  |
      | <Item1> | <Item2> |
    When the user navigates to the Checkout Page
    Then all items are successfully added
    Examples:
      | Item1               | Item2                   |
      | Sauce Labs Backpack | Sauce Labs Bike Light   |
      | Sauce Labs Onesie   | Sauce Labs Bolt T-Shirt |

  Scenario Outline: Sort the products in shop page
    Given An logged user is on the shop page
    When the user selects sort type "<type>"
    Then the products should correctly sorted by type "<type>"
    Examples:
      | type                |
      | Price (low to high) |
      | Name (Z to A)       |
      | Price (high to low) |