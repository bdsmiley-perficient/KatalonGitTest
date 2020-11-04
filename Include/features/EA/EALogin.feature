#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template


@Login
Feature: Login feature	
  I want to login to EA site  and land on Home page

  @ValidLogin
  Scenario Outline: Log into site with valid credentials
    Given I navigate to EA site
    When I enter username <username> and password <password>
    And I click on Submit button
    Then I verify the status <status> at the Home page

    Examples: 
      | username  | password | status  |
      | admin | admin | success |
      | admin | admintest | Fail    |