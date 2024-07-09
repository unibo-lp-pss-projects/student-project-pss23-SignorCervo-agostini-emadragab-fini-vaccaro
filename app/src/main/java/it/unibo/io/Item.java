package it.unibo.io;

/**
 * The `Item` class represents an item in the Signor Cervo game.
 */

public class Item {

    private String name;
    private int num;

    /**
    * Costruttore per creare un nuovo oggetto Item con un nome e un numero.
    * @param name Il nome dell'oggetto.
    * @param num Il numero associato all'oggetto.
    */
    public Item(String name, int num) {
        this.name = name;
        this.num = num;
    }

    /**
    * Restituisce il nome dell'oggetto.
    * @return Il nome dell'oggetto.
    */
    public String getName() {
        return name;
    }
    
    /**
    * Restituisce il numero associato all'oggetto.
    * @return Il numero associato all'oggetto.
    */
    public int getNum() {
        return num;
    }

    
}

