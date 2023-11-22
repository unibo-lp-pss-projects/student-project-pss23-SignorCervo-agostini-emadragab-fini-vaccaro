package it.unibo.io;

import java.util.ArrayList;

/**
 * Classe Player rappresenta il giocatore
 */
public class Player {

    private ArrayList<Item> item = new ArrayList<>();
    private Integer coin = 4;
    
    public void addItem(Item item) {
        if (0 > coin - item.getNum()){
            System.out.println("Non hai abbastanza monete");
        }else{
            this.coin = this.coin - item.getNum();
            this.item.add(item);
            System.out.printf("Hai comprato %s", item.getName());
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
