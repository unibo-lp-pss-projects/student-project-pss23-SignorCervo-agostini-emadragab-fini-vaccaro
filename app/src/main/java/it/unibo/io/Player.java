package it.unibo.io;

import java.util.ArrayList;

/**
 * The `Player` class represents a player in the game.
 */

public class Player {

    private ArrayList<Item> item = new ArrayList<>();
    private Integer coin = 4;
    private static SignorCervoGUI gui;

    /**
     * Adds an item to the player's inventory and updates the GUI.
     *
     * @param item The item to be added.
     */
    
    public void addItem(Item item) {
        if (0 > coin - item.getNum()){
            gui.updateStatusTerminal("Non hai abbastanza monete");
        }else{
            this.coin = this.coin - item.getNum();
            this.item.add(item);
            String fromattedString = String.format("Hai scelto %s", item.getName());
            gui.updateStatusTerminal(fromattedString);
        }
        
    }

    /**
     * Checks if the player has a specific item.
     *
     * @param itemName The item to check for.
     * @return True if the player has the item, false otherwise.
     */
    
    public boolean hasItem(String itemName) {
        for ( Item item : this.item) {
            if (item.getName().equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of coins the player currently has.
     *
     * @return The number of coins.
     */
    
    public Integer getCoin() {
        return this.coin;
    }

}
