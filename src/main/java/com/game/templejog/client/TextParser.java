package com.game.templejog.client;

import java.util.Arrays;
import java.util.List;

public class TextParser {

    /*              CONSTANT FIELDS                         */
    public static final List<String> GO_SYNONYMS = Arrays.asList("go","move", "walk", "travel", "skip");
    public static final List<String> GET_SYNONYMS = Arrays.asList("get","pickup", "grab", "obtain");
    public static final List<String> USE_SYNONYMS = Arrays.asList("use","utilize", "activate", "manipulate", "consume", "apply");
    public static final List<String> QUIT_SYNONYMS = Arrays.asList("quit","exit", "end", "quit");
    public static final List<String> LOOK_SYNONYMS = Arrays.asList("look","inspect","view","observe","peek");

    /*              PRIVATE CONSTRUCTOR                     */
    private TextParser() {}

    /*              BUSINESS METHODS                        */
    public static String[] parseText(String userInput) {
        String[] parsed = userInput.strip().toLowerCase().split(" ", 2); // splits first word from rest of string
        String verb = parsed[0];
        if( parsed.length > 1 ) parsed[1] = parsed[1].stripLeading(); // In case >1 white-space following verb
        if (GO_SYNONYMS.contains(verb)) {
            parsed[0] = "go";
        } else if (GET_SYNONYMS.contains(verb)) {
            parsed[0] = "get";
        } else if (QUIT_SYNONYMS.contains(verb)) {
            parsed[0] = "quit";
        } else if(LOOK_SYNONYMS.contains(verb)) {
            parsed[0] = "look";
        } else if(USE_SYNONYMS.contains(verb)) {
            parsed[0] = "use";
        } else if(verb.equals("help")) {
            parsed[0] = "help";
        } else if(verb.equals("sound")) {
            parsed[0] = "sound";
        } else if(verb.equals("save")) {
            parsed[0] = "save";
        }else {
            parsed[0] = "invalid";
        }

        return parsed;
    }
}