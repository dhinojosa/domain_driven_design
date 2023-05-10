Feature: When a mentee requires a mentor they will be assigned to a
    mentor given that the mentor has the capability to teach the mentee

    Scenario: Initally a mentee would need to declare the topics that they wish
    to gain experience in
        Given a mentee
        When the mentee signs on
        And the mentee doesn't have any topic declarations
        Then the system should repond that it cannot go any further

