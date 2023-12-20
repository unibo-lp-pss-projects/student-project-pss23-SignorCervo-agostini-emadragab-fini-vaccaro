package it.unibo.io;
/**
 * The 'Choice' class represents the player's choices in the game.
 */

public class Choice {
    
    /**
     * Maps the player's input to a choice in the game.
     * @param input The player's input.
     * @return The corresponding choice.
     */
    
    public Integer inputChoice(String input){
        switch(input){
            case "1":
                return 1;

            case "2":
                return 2;

            case "3": 
                return 3;
            default:
                return 0;
        }
    }

    /**
     * Maps the player's input to a choice in the shop.
     * @param input The playrr's input
     * @return The corresponding choice in the shop.
     */

    public Integer inputChoiceShop(String input){
        switch(input){
            case "1":
                return 1;

            case "2":
                return 2;

            case "3": 
                return 3;
            
            case "4":
                return 4;
            
            case "5":
                return 5;

            case "6":
                return 6;

            case "7":
                return 7;

            case "8":
                return 8;

            case "9":
                return 9;
            
            default:
                return 0;
        }
    }
}
