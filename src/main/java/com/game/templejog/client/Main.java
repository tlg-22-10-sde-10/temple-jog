package com.game.templejog.client;

import com.game.templejog.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

// ENTRY
        ConsoleInterface.clearScreen();
        Scanner scanner = new Scanner(System.in);
        ConsoleInterface.displaySetup();
        scanner.nextLine();
        ConsoleInterface console = new ConsoleInterface();

        ConsoleInterface.clearScreen();
        ConsoleInterface.displayTitle();
        String playerInput = "";
        while(playerInput.isEmpty()){
            if(!FileLoader.printAllSavedGames().isEmpty()){
                System.out.println("Select From Saved Game/s: ");
                System.out.println(FileLoader.printAllSavedGames());
            }
            System.out.println(UserInput.START_GAME.getUserPrompt());
            playerInput = scanner.nextLine();
        }
        playerInput = playerInput.toLowerCase().substring(0, 1);
// LOAD GAME
        if (playerInput.equals("y") || (Integer.parseInt(playerInput)>0) ) {

            // DONE Resume Saved Game - Load saved game if user chose to
            //      - don't load game intro, keep difficulty level
            String path = FileLoader.getGameFilePath(playerInput);
            Temple gameFiles = FileLoader.jsonLoader(path);
            Game game = new Game(gameFiles);

            if(playerInput.equals("y")){
                playerInput = "";
                do {
                    System.out.println(UserInput.DIFFICULTY_LEVEL.getUserPrompt());
                    playerInput = scanner.nextLine();

                    if(TextParser.parseText(playerInput)[0].equals("quit")) {
                        System.out.println("Quitting... ");
                        exit(0);
                    }
                    playerInput = TextParser.parseDifficulty(playerInput);
                } while(playerInput.equals(""));
                // Setup Difficulty
                game.processDifficulty(playerInput);
            }
            console.setGame(game);
            Sound.gameSound(scanner, game);

            // Play intro
            ConsoleInterface.clearScreen();
            if(TextParser.DIFFICULTIES.contains(playerInput)) {
                console.displayIntro();
                scanner.nextLine();
                ConsoleInterface.clearScreen();
            }

            // Play Sound
            /* Stop the background music when entering landing zone */
            if (game.getPlaySound()) {
                Sound.stopSound();
                Sound.themeSound("sounds/landing_zone.wav");
            }
// GAME LOOP
            do {
                ConsoleInterface.clearScreen();
                console.displayScene();
                System.out.println(UserInput.USER_ACTION.getUserPrompt());
                game.updateScannerString();
                String[] choice = TextParser.parseText(game.getScannerString());
                ConsoleInterface.clearScreen();
                console.displayResult(game.processChoice(choice),0);
            } while (   !game.getQuitGame()
                        && game.getPlayer().getSteps() < 24
                        && game.getPlayer().getHealth() > 0
                        && !(game.getCommunicatorOff() && game.getCurrentRoom().getName().equalsIgnoreCase("landing zone"))
                    );
            console.displayEnding();
        }
    }

}