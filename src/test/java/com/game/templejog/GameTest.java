package com.game.templejog;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.templejog.client.FileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    ArrayList<Item> playerInventory;
    HashMap<String, Item> itemsMap;
    HashMap<String, Room> roomsMap;
    Game game;

    @BeforeEach
    void setUp() {
        roomsMap = new HashMap<>();
        Item room01_alphaItem = new Item("alpha", "room01", "test description", 1, 1);
        Item room02_bravoItem = new Item("bravo", "room02", "test description", 1, 1);
        List<String> room01RoomItems = List.of("alpha");
        List<String> room02RoomItems = List.of("bravo");
// WIP                                                                        west south north east
//        Room room01 = new Room(1, "test room name","test room01 description","room02","","","",true,room01RoomItems);
//        Room room02 = new Room(2, "test room name","test room02 description","","","","room01",true,room02RoomItems);
        playerInventory = new ArrayList<>();
        playerInventory.add(room01_alphaItem);
        playerInventory.add(room02_bravoItem);
//        roomsMap.put("room01",room01);
//        roomsMap.put("room02",room02);
        itemsMap = new HashMap<>();
        itemsMap.put("alpha",room01_alphaItem);
        itemsMap.put("bravo",room02_bravoItem);

//DONE update constructor
        game = new Game(new Player(), roomsMap, new HashMap<>(), itemsMap);
//        game.setCurrentRoom(room01);
    }
    public Game generateGameFromJSON() throws IOException {
        Game gameJSON;
        Temple gameFiles = FileLoader.jsonLoader("JSON/gameFiles.json");
        gameJSON = new Game(gameFiles.getPlayer(), gameFiles.getEasymap(), gameFiles.getEncounters(), gameFiles.getItems());
        return gameJSON;
    }
//CONTROLLERS


    //NAVIGATING
    @Test
    @Disabled
    void processNavigating_given_SubsequentValidInputs_playerCanTraverseAllRoomsInGame_LoadedFromJSON() throws IOException {
        Game gameJSON = generateGameFromJSON();
        String[] fastPath = new String[]{"north", "north", "north", "east", "south", "east", "north", "south", "west", "north", "west", "south", "south", "south","end"};
        String[] fastPathRooms = new String[]{"room02", "room03", "room04", "room07", "room06", "room09", "room10", "room09", "room06", "room07", "room04", "room03", "room02", "room01", InvalidNounInput.BAD_NAV.getWarning()};
        List<String> roundTrip = new ArrayList<>(Arrays.asList(fastPath));
        List<String> expectedRooms = new ArrayList<>(Arrays.asList(fastPathRooms));
        String expectedRoom = "";
        String[] choice = new String[]{"go",""};
        while(  choice[1] != "end" ){
            choice[1] = roundTrip.remove(0);
            String actualRoom = gameJSON.processChoice(choice);
            expectedRoom = expectedRooms.remove(0);
            assertEquals(expectedRoom,actualRoom);
        }

        // completed successfully
        String expected = "end";
        String actual = choice[1];
        assertEquals(expected,actual);
    }
    @Test
    @Disabled
    void processNavigating_given_validInputString_shouldReturnStringName_ofAccessableRoom(){
        String expect = "room02";
        String testNoun = "west";
        String[] testChoice = new String[]{"go", testNoun};
        String actual = game.processChoice(testChoice);
        assertEquals(expect,actual);
    }
    @Test
    @Disabled
    void processNavigating_given_emptyInputString_shouldReturnEnum_BAD_NAV(){
        String expect = InvalidNounInput.BAD_NAV.getWarning();
        String emptyTestString = "";
        String[] testChoice = new String[]{"go", emptyTestString};
        String actual = game.processChoice(testChoice);
        assertEquals(expect,actual);
    }
    @Test
    void processNavigating_given_validInputString_butNonStandardDirectionString_shouldReturnEnum_BAD_NAV(){
        String expect = InvalidNounInput.BAD_NAV.getWarning();
        String testNoun = "northwest";
        String[] testChoice = new String[]{"go", testNoun};
        String actual = game.processChoice(testChoice);
        assertEquals(expect,actual);
    }

    // GETTING
    // test if can get all items from every room and add to inventory
    // if present in room
    // add to inventory, pop from map -> inventory
    // remove item from room items and map of items
    // check inventory

    @Test
    void processGetting_given_validInputString_andItemIsPresentInCurrentRoom_shouldAddItemToPlayerInventory_returnString(){}

    //USING
    //LOOKING
    @Test
    void processLooking_Given_ValidInputString_ShouldReturnItemDescription_WhenItemIsInInventory(){
        String expect = "test description";
        String noun = "alpha";
        String[] testChoice = new String[]{"look",noun};
        String actual = game.processChoice(testChoice);
        assertEquals(expect,actual);
    }
    @Test
    @Disabled
    void processLooking_Given_ValidInputString_ShouldReturnItemDescription_WhenItemIsPresentInCurrentRoom(){
        playerInventory.clear();
        String expect = "test description";
        String noun = "alpha";
        String[] testChoice = new String[]{"look",noun};
        String actual = game.processChoice(testChoice);
        assertEquals(expect,actual);
    }
    @Test
    void processLooking_Given_EmptyString_ShouldReturnBAD_LOOK_StringMessage(){
        String expect = InvalidNounInput.BAD_LOOK.getWarning();
        String[] test = new String[]{"look",""};
        String actual = game.processChoice(test);
        assertEquals( expect, actual );
    }

    //QUITTING
    @Test
    void processQuitting_Given_InputStringToQuit_ShouldReturnSameInputString(){}
    @Test
    void processQuitting_Given_BadInputStringToQuit_ShouldReturnInvalidInputString(){}


    //HELPER METHODS
    @Test
    void popItemFromMap_GivenValidNounString_ShouldReturnValidItem(){
        Item expected = new Item("alpha");
        Item actual = game.popItemFromMap("alpha");
        assertEquals( expected.getName(), actual.getName() );
    }
    @Test
    void popItemFromMap_GivenInvalidNoun_ShouldReturnEmptyItemObject(){
        Item expected = new Item();
        String testKey = "Zulu";
        Item actual = game.popItemFromMap(testKey);
        assertTrue(actual.equals(expected));
    }
    @Test
    void popItemFromMap_GivenEmptyNoun_ShouldReturnEmptyItemObject() {
        Item expected = new Item();
        String testKey = "";
        Item actual = game.popItemFromMap(testKey);
        assertTrue(actual.equals(expected));
    }
    @Test
    void popItemFromMap_GivenNull(){
        Item expected = new Item();
        Item actual = game.popItemFromMap(null);
        assertTrue(actual.equals(expected));
    }
}