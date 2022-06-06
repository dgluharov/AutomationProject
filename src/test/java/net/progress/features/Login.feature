Feature: Login feature
  In order to operate with shop
  As client
  I want to log in it

  Scenario: Login successfully
    Given a user is on the login page
    When the user enters credentials and press Login button
    Then the user is successfully redirected to shop page

  Scenario Outline: Login with invalid credentials
    Given a user is on the login page
    When the user enters invalid "<credentials>" credentials and press Login button
    Then the alert "<message>" message should displayed on the screen

    Examples:
      | credentials                 | message                                                                   |
      | standard_user, fakePassword | Epic sadface: Username and password do not match any user in this service |
      | fakeUsername, secret_sauce  | Epic sadface: Username and password do not match any user in this service |

