Feature: Login in the application

  @ScenarioId("FUNCTIONAL.AUTH.SCN.001") @UserStory("MyApp-135") @login-scenarios
  Scenario: User can login with valid user name and password
    Given I see the login page
    When I login with user name "Beijing" and password "passion"
    Then I see the welcome page
    And the title is "Country : "

  @ScenarioId("FUNCTIONAL.AUTH.SCN.002") @UserStory("MyApp-135") @login-scenarios
  Scenario: User can login with valid second user name and password
    Given I see the login page
    When I login with user name "beijing" and password "passion"
    Then I see the welcome page
    And the title is "Sunrise : "

  @ScenarioId("FUNCTIONAL.AUTH.SCN.003") @UserStory("MyApp-135") @login-scenarios
  Scenario: User can login with valid third user name and password
    Given I see the login page
    When I login with user name "ShangHai" and password "passion"
    Then I see the welcome page
    And the title is "Sunset : "