package config;

public interface FootballGameApiEndpoints {
    String ALL_AVAILABLE_COMPETITIONS = "competitions/";
    String ONE_PARTICULAR_COMPETITION = "competitions/{id}";
    String ALL_TEAMS_FOR_A_PARTICULAR_COMPETITION = "competitions/{id}/teams";
    String SHOW_STANDINGS_FOR_A_PARTICULAR_COMPETITION =  "competitions/{id}/standings";
    String ALL_MATCHES_FOR_A_PARTICULAR_COMPETITION = "competitions/{id}/matches";
    String LIST_GOAL_SCORERS_FOR_A_PARTICULAR_COMPETITION = "competitions/{id}/scorers";
    String LIST_MATCHES_ACROSS_COMPETITIONS = "matches";
    String SHOW_ONE_PARTICULAR_MATCH = "matches/{id}";
    String SHOW_ALL_MATCHES_FOR_A_PARTICULAR_TEAM = "teams/{id}/matches/";
    String SHOW_ONE_PARTICULAR_TEAM = "teams/{id}";
    String LIST_ALL_AVAILABLE_AREAS = "areas/";
    String LIST_ONE_PARTICULAR_AREA = "areas/{id}";
    String LIST_ONE_PARTICULAR_PLAYER = "players/{id}";
    String SHOW_ALL_MATCHES_FOR_A_PARTICULAR_PLAYER = "players/{id}/matches";
}
