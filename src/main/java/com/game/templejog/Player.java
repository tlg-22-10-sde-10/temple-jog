package com.game.templejog;

import java.util.ArrayList;
import java.util.List;

public class Player {
    Integer health;
    Integer steps;
    List<Item> inventory;
    String lastKnownPos;

    /*              CONSTRUCTORS                    */
    public Player(){
        this.health = 9;
        this.steps = 0;
        this.inventory = new ArrayList<>();
        this.lastKnownPos = "";
    }

    /*              HELPERS                    */
    public Integer inventoryHasItem(String itemName){
        for( Item item : getInventory() ){
            if( item.getName().toLowerCase().equals(itemName) ) return getInventory().indexOf(item);
        }
        return -1;
    }

    /*              ACCESSOR METHODS                    */
    public List<Item> getInventory() { return inventory; }
    public void setInventory(List<Item> inventory) {this.inventory = inventory;}
    public Integer getHealth() {return health;}
    public void setHealth(Integer health) {this.health = health;}
    public Integer getSteps() {return steps;}
    public void setSteps(Integer steps) {this.steps = steps;}
    public String getLastKnownPos() { return lastKnownPos; }
    public void setLastKnownPos(String lastKnownPos) { this.lastKnownPos = lastKnownPos;}
}