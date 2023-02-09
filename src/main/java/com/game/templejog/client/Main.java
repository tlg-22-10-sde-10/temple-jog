package com.game.templejog.client;

import com.game.templejog.*;
import java.io.IOException;
import java.util.Scanner;

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
            System.out.println("Start a new Game? y/n");
            playerInput = scanner.nextLine();
        }
        playerInput = playerInput.toLowerCase().substring(0, 1);

// LOAD GAME
        if (playerInput.equals("y")) {
            Temple gameFiles = FileLoader.jsonLoader("JSON/gameFiles.json");
            console.setGameFiles(gameFiles);
            Game game = new Game(gameFiles.getPlayer(),gameFiles.getEasymap(),gameFiles.getEncounters(),gameFiles.getItems());
            Sound.gameSound(scanner, game); //extracted method
            console.setGame(game);

// Play intro
            ConsoleInterface.clearScreen();
            console.displayIntro();
            scanner.nextLine();
            ConsoleInterface.clearScreen();

/* Stop the background music when entering landing zone */
            if(game.getPlaySound()){
                Sound.stopSound();
                Sound.themeSound("sounds/landing_zone.wav");
            }
// GAME LOOP
            do {
                ConsoleInterface.clearScreen();
                console.displayScene();
                System.out.print("What do you want to do?\n>");
                game.updateScannerString();
                String[] choice = TextParser.parseText(game.getScannerString());
                ConsoleInterface.clearScreen();
                console.displayResult(game.processChoice(choice),0);
            } while ( !game.getQuitGame()
                    && game.getPlayer().getSteps() < 24
                    && game.getPlayer().getHealth() > 0
                    && !(game.getCommunicatorOff() && game.getCurrentRoom().getName().equalsIgnoreCase("landing zone")));

            console.displayEnding();
        }
    }

}