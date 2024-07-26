package it.unibo.io;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {

    private String name;
    private int number;

    /**
     * Restituisce il nome dell'elemento.
     * 
     * @return Il nome dell'elemento.
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome dell'elemento.
     * 
     * @param name Il nome da impostare.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Restituisce il numero associato all'elemento.
     * 
     * @return Il numero dell'elemento.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Imposta il numero associato all'elemento.
     * 
     * @param number Il numero da impostare.
     */
    public void setNumber(int number) {
        this.number = number;
    }
}
