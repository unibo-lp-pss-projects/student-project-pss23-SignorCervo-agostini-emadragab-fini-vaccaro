package it.unibo.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * La classe Game rappresenta il gioco principale.
 */
public class Game {

    private static JsonReader j  = new JsonReader();
    private SignorCervoGUI gui;
    int i = 0; 
    Choice c = new Choice();
    Scanner myObj = new Scanner(System.in);
    Player player = new Player();
    Integer key = 2;
    Map<Integer, String> futureResponss = new HashMap<Integer,String>();

    /**
     * Costruttore della classe Game.
     * Inizializza il gioco leggendo i dati dal file JSON.
     */
    Game(){
        j.readJson();
    }

    /**
     * Metodo per generare l'output del gioco.
     * Aggiorna l'interfaccia grafica con l'immagine e il dialogo correnti.
     * Stampa le scelte disponibili e gli oggetti nel negozio, se presenti.
     */
    public void output(){

        if (this.i > j.getSize()) {
            return;
        }; 

        j.updateMembers(i);
        
        gui.updateImage(j.getImage(i));
        gui.updateStatusTerminal(j.getDialog(i));

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        j.printChoices(i);

        if(j.checkShop()) j.printIteam();

    }

    /**
     * Metodo per gestire l'input dell'utente.
     * Elabora la scelta dell'utente e aggiorna lo stato del gioco di conseguenza.
     *
     * @param cmd numero della risposta
     */
    public void input(int cmd){

        if(j.checkShop()){

            key = cmd;
            key = key - 1;
            player.addItem(j.shop(key));
            this.i++;
            return;
        } 
        
        key = cmd;
        key = key - 1;
            
        if(j.checkChoice(i, key)){


            j.printReply(key);
            this.i = 0;
            return;

        }

        j.printReply(key);

        this.i++;
    }
}

