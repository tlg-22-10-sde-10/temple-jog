package com.game.templejog;

import java.util.HashMap;

public class Temple {
    private HashMap<String,Room> easymap;
    private HashMap<String,Encounter> encounters;
    private HashMap<String,Item> items;
    private Player player;
    private HashMap<String,String> gameText;


//  ACCESSOR METHODS
    public HashMap<String, Room> getEasymap() { return easymap; }
    public void setEasymap(HashMap<String, Room> easymap) { this.easymap = easymap; }
    public HashMap<String, Encounter> getEncounters() { return encounters; }
    public void setEncounters(HashMap<String, Encounter> encounters) { this.encounters = encounters; }
    public HashMap<String, Item> getItems() { return items; }
    public void setItems(HashMap<String, Item> items) { this.items = items; }
    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }
    public HashMap<String, String> getGameText() { return gameText; }
    public void setGameText(HashMap<String, String> gameText) { this.gameText = gameText; }
}