package it.unibo.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game {

    private static JsonReader j  = new JsonReader();
    private SignorCervoGUI gui;
    int i = 0; 
    Choice c = new Choice();
    Scanner myObj = new Scanner(System.in);
    Player player = new Player();
    Integer key = 2;
    Map<Integer, String> futureResponss = new HashMap<Integer,String>();

    Game(){
        j.readJson();
    }

    
    public void output(){

        // I = number of members,  j = in a particular mamber number 
        if (this.i > j.getSize()) {
            return;
        }; 

        j.updateMembers(i);
        
        //j.getDialog(i);
        gui.updateStatusTerminal(j.getDialog(i));

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        j.printChoices(i);

        if(j.checkShop()) j.printIteam();

    }

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

