package it.unibo.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Game {

    private static JsonReader j  = new JsonReader();
    private SignorCervoGUI gui;

    Game(SignorCervoGUI gui){
        this.gui = gui;
        j.readJson();
    }

    
    public void Start() throws InterruptedException, IOException{
        
        Choice c = new Choice();
        Scanner myObj = new Scanner(System.in);
        Player player = new Player();
        Integer key = 2;
        Map<Integer, String> futureResponss = new HashMap<Integer,String>();

        while(true){  

            futureResponss.put(-1, "");
            j.getRule();

            // I = number of members,  j = in a particular mamber number 
            for(int i = 0; i < j.getSize(); i++){

                j.updateMembers(i);
                //j.getDialog(i);
                gui.updateStatusTerminal(j.getDialog(i));

                try {
                    TimeUnit.SECONDS.sleep(2);
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
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                    }while(key == 2); 

                }else{

                    key = c.inputChoice(myObj.nextLine());
                    key = key - 1;

                    if(futureResponss.keySet().iterator().next() == -1) futureResponss = j.giveRemember(key);
                    
                    if(j.checkChoice(i, key)){

                        j.printReply(key);

                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        break;
                    }else if(futureResponss.keySet().iterator().next() == i){
                        
                        System.out.println(futureResponss.values().iterator().next());

                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        break;
                    }

                    j.printReply(key);

                    

                }

                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

            }
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
    }
}

