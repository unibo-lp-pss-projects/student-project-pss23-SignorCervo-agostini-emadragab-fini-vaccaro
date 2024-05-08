package it.unibo.io;

import java.util.ArrayList;

/**
 * La classe Player rappresenta il giocatore nel gioco.
 */
public class Player {
    /**
     * Aggiunge un oggetto all'inventario del giocatore.
     * Se il giocatore ha abbastanza monete, l'oggetto viene aggiunto all'inventario
     * e le monete vengono detratte. Altrimenti, viene visualizzato un messaggio
     * che indica che il giocatore non ha abbastanza monete.
     *
     * @param item l'oggetto da aggiungere all'inventario
     */
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

    /**
     * Verifica se il giocatore possiede un determinato oggetto nell'inventario.
     *
     * @param name l'oggetto da verificare
     * @return true se il giocatore possiede l'oggetto, false altrimenti
     */
    public boolean getItem(Item name) {
        if (this.item.contains(name)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Restituisce il numero di monete possedute dal giocatore.
     *
     * @return il numero di monete possedute dal giocatore
     */
    public Integer getCoin() {
        return this.coin;
    }

}
