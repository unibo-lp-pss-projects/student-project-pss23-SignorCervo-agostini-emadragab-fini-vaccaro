package it.unibo.io;

import java.util.ArrayList;

/**
 * Classe Player rappresenta il giocatore
 */
public class Player {

    private ArrayList<Item> item = new ArrayList<>();
    private Integer coin = 4;
    private static SignorCervoGUI gui;
    
    public void addItem(Item item) {
        if (0 > coin - item.getNum()){
            gui.updateStatusTerminal("Non hai abbastanza monete");
        }else{
            this.coin = this.coin - item.getNum();
            this.item.add(item);
            String fromattedString = String.format("Hai comprato %s", item.getName());
            gui.updateStatusTerminal(fromattedString);
        }
        
    }
    
    public boolean getItem(Item name) {
        if (this.item.contains(name)){
            return true;
        }else{
            return false;
        }
    }
    
    public Integer getCoin() {
        return this.coin;
    }

}
