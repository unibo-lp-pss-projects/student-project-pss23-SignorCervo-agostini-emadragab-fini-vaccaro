package it.unibo.io;

/**
 * The `Item` class represents an item in the Signor Cervo game.
 */

public class Item {

    private String name;
    private int num;

    /**
     * Constructs an `Item` object with the specified name and quantity.
     *
     * @param name The name of the item.
     * @param num  The quantity of the item.
     */

    public Item(String name, int num) {
        this.name = name;
        this.num = num;
    }

    /**
     * Get the name of the item.
     *
     * @return The name of the item.
     */

    public String getName() {
        return name;
    }

    /**
     * Get the quantity of the item.
     *
     * @return The quantity of the item.
     */
    
    public int getNum() {
        return num;
    }

    
}

