package com.game.templejog;

public enum UserInput {
    START_GAME("Start a new Game? y/n"),
    USER_ACTION("What do you want to do?>"),
    TURN_MUSIC("Do you want the music on? [y/n]"),
    DIFFICULTY_LEVEL("Please choose a difficulty level: EASY, MEDIUM, HARD"),
    END_GAME("Are you sure you want to quit? [Type 'y' or 'n']");

    private final String userPrompt;
    private UserInput(String msg){this.userPrompt = msg; }

    public String getUserPrompt(){
        return this.userPrompt;
    }
}