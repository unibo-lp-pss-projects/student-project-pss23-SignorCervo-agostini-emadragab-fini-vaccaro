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

        if(j.checkShop()){

            do{   

                j.printIteam();
                key = c.inputChoiceShop(myObj.nextLine());
                key = key - 1;
                player.addItem(j.shop(key));
                System.out.println("\nfinito?");
                System.out.println("\n1) si");
                System.out.println("\n2) no");
                key = c.inputChoice(myObj.nextLine());

            }while(key == 2); 

        }
    }

    public void input(String cmd){
        key = c.inputChoice(cmd);
        key = key - 1;
        futureResponss.put(-1, "");

        if(futureResponss.keySet().iterator().next() == -1) futureResponss = j.giveRemember(key);
            
        if(j.checkChoice(i, key)){

            j.printReply(key);           
            this.i = 0;
            return;

        }else if(futureResponss.keySet().iterator().next() == i){
            
            gui.updateStatusTerminal(futureResponss.values().iterator().next());
            
        }

        j.printReply(key);
        
        this.i++;
    }
}

