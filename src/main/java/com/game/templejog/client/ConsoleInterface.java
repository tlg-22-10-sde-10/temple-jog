package com.game.templejog.client;

import com.game.templejog.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleInterface { // Previously TitleScreen

/*                  CONSTANTS & FIELDS                          */
    static final String titleSplash = "@|green ████████████████████████████████████████████████████████████████████████████████\n█░         █░        █░  █████    █░       ███░ ███████░        ████████████████\n█░         █░        █░   ████    █░        ██░ ███████░        ████████████████\n█░░░░  ░░░░█░ ░░░░░░░█░   ████    █░ ░░░░   ██░ ███████░ ░░░░░░░████████████████\n█████░ █████░ ████████░   ████    █░ ████░  ██░ ███████░ ███████████████████████\n█████░ █████░ ████████░   ███     █░ █████   █░ ███████░ ███████████████████████\n█████░ █████░       ██░    ██     █░ ████   ██░ ███████░       █████████████████\n█████░ █████░ ░░░░░░░█░ ░  █      █░      ░░██░ ███████░ ░░░░░░░████████████████\n█████░ █████░ ████████░ █░    ░   █░ ░░░░░████░ ███████░ ███████████████████████\n█████░ █████░ ████████░ █░    █░  █░ █████████░ ███████░ ███████████████████████\n█████░ █████░        █░ █░    █░  █░ █████████░       █░        ████████████████\n█████░░█████░░░░░░░░░█░░██░░░██░░░█░░█████████░░░░░░░░█░░░░░░░░░████████████████\n██████████████████████████████████████████░ ████████████████████████████████████\n██████████████████████████████████████████░ ███░       █████░      █████████████\n██████████████████████████████████████████░ ██░   ░░    ███░   ░░░  ████████████\n██████████████████████████████████████████░ ██░   ██░░  ███░  ███░░  ███████████\n██████████████████████████████████████████░ ██░  █████░  █░ ███████░ ███████████\n██████████████████████████████████████████░ ██░  █████░  █░ ████████████████████\n██████████████████████████████████████████░ ██░  █████░  █░ ███░   █████████████\n█████████████████████████████████████░ ███  ██░  █████   █░ ████░░   ███████████\n█████████████████████████████████████░  █   ██░   ██     █░  █████░  ███████████\n██████████████████████████████████████░    ███░░        ███░        ████████████\n███████████████████████████████████████░░░█████░░░░░░░░█████░░░░░░░█████████████|@";
    static final Integer CONSOLE_HEIGHT = 25;
    static final Integer CONSOLE_WIDTH = 80;

    Game game;

/*                      STATIC METHODS                          */
    public static int displaySetup() {
        String midLines = "\n|%" + (CONSOLE_WIDTH - 1) + "s";
        System.out.println('┌' + addDashes() + '┐' +
                String.valueOf(String.format(midLines, "|")).repeat(CONSOLE_HEIGHT - 5) +
                '\n' + '└' + addDashes() + '┘' +
                "\nAdjust console window to be 80 characters wide by 30 lines tall\n" +
                "Then hit <Enter> to continue...");
        return 0;
    }

    public static int displayTitle() {
        System.out.println(ansi().render(titleSplash));
        return 0;
    }

    private static String addDashes() {
        return "-".repeat(CONSOLE_WIDTH - 2);
    }

/*                      BUSINESS METHODS                        */
    public int displayIntro() throws InterruptedException {
        String title = ansi().render(getGame().getGameText().get("intro")).toString();
        char[] charArray = title.toCharArray();
        for (char c : charArray) {
            System.out.print(c);
            if (c == '\n') {
                TimeUnit.MILLISECONDS.sleep(250);
            } else {
                TimeUnit.MILLISECONDS.sleep(20);
            }
        }
        return 0;
    }

    public int displayScene() {
        StringBuilder scene = new StringBuilder();
        String currentRoom = game.getCurrentRoom().getName();
        Integer health = game.getPlayer().getHealth();
        String[] colors;

        // Top Bar Setup
        int hoursPlayed = game.getPlayer().getSteps() * 15;
        int hours = hoursPlayed / 60;
        int minutes = hoursPlayed % 60;
        int time = 1200 + (100 * hours) + minutes;
        colors = processColor();
        String status = String.format("Location:@|%s  %s|@ █ Health:@|%s  %s|@ █ TIME:@|%s  %s|@",
                colors[0], currentRoom,
                colors[1], health,
                colors[2], time > 999 ? Integer.toString(time) : "0" + time);
        status = ansi().render(status).toString();
        String statusSpace = "%" + ((CONSOLE_WIDTH - 1 - (status.length() - 24)) / 2 + status.length()) + "s";
        String endSpace = "%" + ((CONSOLE_WIDTH - (status.length() - 24)) / 2) + "s"; // "%20s"
        boolean hasEncounters = !getGame().getCurrentRoom().getEncounters_to().isEmpty();

        // Inventory Bar Setup v2
        String inventorySpaces;
        StringBuilder inventory =  new StringBuilder();
        StringBuilder line =     new StringBuilder("█  Inventory: ");
        StringBuilder lineTwo =  new StringBuilder("█             ");
        for(Item item : getGame().getPlayer().getInventory()) {
            if( line.length() + item.getName().length() < 75 ) {
                line.append(String.format("[%s] ", item.getName()));
            } else {
                lineTwo.append(String.format("[%s] ",item.getName()));
            }
        }
        inventorySpaces = "%" + (CONSOLE_WIDTH - line.length()) + "s";
        line.append(String.format(inventorySpaces,"█")).append("\n");
        inventorySpaces = "%" + (CONSOLE_WIDTH - lineTwo.length()) + "s";
        lineTwo.append(String.format(inventorySpaces,"█"));
        inventory.append(line).append(lineTwo);

        // Encounter Setup
        StringBuilder encounterDescription = null;
        if (hasEncounters) {
            encounterDescription = new StringBuilder();
            for (String encounter : getGame().getCurrentRoom().getEncounters_to()) {
                encounterDescription.append(formatDisplay(getGame().getEncounters().get(encounter).getDescription(), "encounter"));
            }
            encounterDescription.append("█").append(" ".repeat(78)).append("█").append("\n");
        }

        // Room Display Setup
        String lineOne = getGame().getCurrentRoom().getDescription();
        String roomDescription = formatDisplay(lineOne, "room");
        roomDescription = roomDescription.concat("█" + " ".repeat(78) + "█" + "\n");


        // Scene Builder
        scene.append("█".repeat(CONSOLE_WIDTH))
                .append("\n█")
                .append(String.format(statusSpace, status))
                .append(String.format(endSpace, "█"))
                .append("\n")
                .append("█".repeat(CONSOLE_WIDTH))
                .append("\n")
                .append(inventory)
                .append("\n")
                .append("█".repeat(CONSOLE_WIDTH))
                .append("\n");
        if (hasEncounters) {
            scene.append(encounterDescription)
                    .append("█".repeat(CONSOLE_WIDTH))
                    .append("\n");
        }
        scene.append(roomDescription)
                .append("█".repeat(CONSOLE_WIDTH))
                .append("\n");

        int displayLines = scene.length() / 80;
        scene.append("\n".repeat(Math.max(22 - displayLines, 1)));
        System.out.println(scene);
        return 0;
    }

    private String formatDisplay(String description, String type) {
        List<String> lines = new ArrayList<>();
        String roomSpaceBefore, roomSpaceAfter;
        StringBuilder sceneDescription = new StringBuilder();

        sceneDescription.append("█").append(" ".repeat(78)).append("█").append("\n");
        if (description.length() > 78) {
            while (description.length() > 78) {
                int splitIndex = description.lastIndexOf(" ", 76);

                lines.add(description.substring(0, splitIndex));
                description = description.substring(splitIndex + 1);
            }
            lines.add(description);
        } else {
            lines.add(description);
        }
        // Add Items
        List<String> itemList = getGame().getCurrentRoom().getItems();
        if (!itemList.isEmpty() && type.equals("room")) {
            lines.add("");
            StringBuilder items = new StringBuilder();
            items.append(String.format("You see a %s", itemList.get(0)));
            int totalItems = itemList.size();
            for (int i = 1; i < totalItems; i++) {
                if (i == totalItems - 1) {
                    items.append(String.format(" and a %s", itemList.get(i)));
                } else {
                    items.append(String.format(", %s", itemList.get(i)));
                }
            }
            items.append(".");
            lines.add(items.toString());
        }
        for (String line : lines) {
            int amountOfSpaces = (CONSOLE_WIDTH - 1 - line.length()) / 2;
            roomSpaceBefore = "%" + (amountOfSpaces == 0 ? "" : (amountOfSpaces + line.length())) + "s";
            amountOfSpaces = (CONSOLE_WIDTH - line.length()) / 2;
            roomSpaceAfter = "%" + (amountOfSpaces == 0 ? "" : amountOfSpaces) + "s";
            sceneDescription.append("█")
                    .append(String.format(roomSpaceBefore, line))
                    .append(String.format(roomSpaceAfter, "█"))
                    .append("\n");
        }
        return sceneDescription.toString();
    }

    public int displayResult(String processChoice, int lineNumber) throws InterruptedException {
        // Break up into lines
        lineNumber = lineNumber > 0 ? 12 - lineNumber : 12;
        List<String> lines = new ArrayList<>();
        if (processChoice.length() > 78) {
            while (processChoice.length() > 78) {
                int splitIndex = processChoice.lastIndexOf(" ", 77);
                lines.add(processChoice.substring(0, splitIndex));
                processChoice = processChoice.substring(splitIndex + 1);
            }
            lines.add(processChoice);
        } else {
            lines.add(processChoice);
        }

        // Format string to center on page
        StringBuilder display = new StringBuilder();
        for (String line : lines) {
            int amountOfSpaces = (CONSOLE_WIDTH - 1 - line.length()) / 2;
            String spaceBefore = "%" + (amountOfSpaces == 0 ? "" : (amountOfSpaces + line.length())) + "s";
            amountOfSpaces = (CONSOLE_WIDTH - line.length()) / 2;
            String spaceAfter = "%" + (amountOfSpaces == 0 ? "" : amountOfSpaces) + "s";
            display.append(" ")
                    .append(String.format(spaceBefore, line))
                    .append(String.format(spaceAfter, " "))
                    .append("\n");
        }

    // Print gap above
        int displayLines = display.length() / 80;
        System.out.print("\n".repeat(lineNumber - displayLines / 2));
    // Iterate through string like the intro

        char[] charArray = display.toString().toCharArray();
        int pause = 0;
        for (char c : charArray) {
            System.out.print(c);
            if (c == ' ') {
                TimeUnit.MILLISECONDS.sleep(0);
            } else {
                TimeUnit.MILLISECONDS.sleep(20);
                pause++;
            }
        }
    // Print gap below
        System.out.print("\n".repeat(lineNumber - 1 - displayLines / 2));
    // Pause
        TimeUnit.MILLISECONDS.sleep((pause * 20L) < 1500 ? 1500 : pause * 20L);
        return 0;
    }

    public void displayEnding() throws InterruptedException {
        if (game.getCommunicatorOff()) {
            if (getGame().getPlayer().getSteps() > 24 || getGame().getPlayer().getHealth() <= 0) {
                clearScreen();
                System.out.println(getGame().getGameText().get("gameOverNuked"));
                TimeUnit.SECONDS.sleep(5L);
                displayResult(getGame().getGameText().get("sortOfWin"), 7);
            } else {
                clearScreen();
                System.out.println(ansi().fgBrightGreen().render(getGame().getGameText().get("gameOver")).fgDefault());
                displayResult(getGame().getGameText().get("winText"), 7);
            }
        } else {
            if (getGame().getPlayer().getSteps() >= 24) {
                clearScreen();
                System.out.print(getGame().getGameText().get("gameOverNuked"));
                TimeUnit.SECONDS.sleep(5L);
                displayResult(getGame().getGameText().get("outOfTime"), 7);
            } else if(getGame().getPlayer().getHealth() <= 0) {
                clearScreen();
                System.out.print(getGame().getGameText().get("gameOverNuked"));
                TimeUnit.SECONDS.sleep(5L);
                displayResult(getGame().getGameText().get("outOfLife"), 7);
            }
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private String[] processColor() {
        String locColor,timeColor,healthColor;

        if(game.getPlayer().getHealth() <= 2) {
            healthColor = "red";
        } else if(game.getPlayer().getHealth() <= 5) {
            healthColor = "yellow";
        } else {
            healthColor ="cyan";
        }

        if(game.getPlayer().getSteps() >= 20){
            timeColor = "red";
        } else if(game.getPlayer().getSteps() >= 12) {
            timeColor = "yellow";
        } else {
            timeColor = "cyan";
        }

        if(timeColor.equals("red") || healthColor.equals("red")) {
            locColor = "red";
        } else if (timeColor.equals("yellow") || healthColor.equals("yellow")) {
            locColor = "yellow";
        } else {
            locColor = "cyan";
        }

        return new String[]{locColor,healthColor,timeColor};
    }

/*                      ACCESSORS                               */
    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }
}
