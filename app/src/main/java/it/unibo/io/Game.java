package it.unibo.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * The `Game` class represents the game logic for the Signor Cervo game.
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
     * Constructs a `Game` object and initializes the JSON reader.
     */

    Game(){
        j.readJson();
    }

    /**
     * Displays the output for the current game state.
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
     * Handles the player's input and updates the game state accordingly.
     *
     * @param cmd The player's input command.
     */

    public void input(String cmd){

        if(j.checkShop()){

            key = c.inputChoiceShop(cmd);
            key = key - 1;
            player.addItem(j.shop(key));
            this.i++;
            return;
        } 
        
        key = c.inputChoice(cmd);
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

