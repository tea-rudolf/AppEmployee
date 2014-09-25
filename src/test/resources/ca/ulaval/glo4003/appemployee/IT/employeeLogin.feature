Feature: When an employee log in

  Scenario: Return employee view with employee credentials
    Given valid employee credentials 
    When user post credentials
    Then returns employee view