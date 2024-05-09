package it.unibo.io;

public class Choice {
    
    /**
    * Converte l'input in un intero, rappresentante la scelta dell'utente.
    * @param input L'input dell'utente.
    * @return La scelta dell'utente in formato intero.
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
    * Converte l'input in un intero, rappresentante la scelta dell'utente per lo shop.
    * @param input L'input dell'utente.
    * @return La scelta dell'utente per lo shop in formato intero.
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
