package it.unibo.io.model.SignorCervo;

import java.util.List;

import it.unibo.io.Item;

/**
 * Rappresenta un membro con dialogo, oggetti, immagine e risposte.
 */
public class Member {
    private String dialog;
    private List<Item> item;
    private String img;
    private List<Response> respons;

    /**
     * Restituisce il dialogo.
     * 
     * @return Testo del dialogo.
     */
    public String getDialog() {
        return dialog;
    }

    /**
     * Imposta il dialogo.
     * 
     * @param dialog Testo del dialogo.
     */
    public void setDialog(String dialog) {
        this.dialog = dialog;
    }

    /**
     * Restituisce la lista degli oggetti.
     * 
     * @return Lista degli oggetti.
     */
    public List<Item> getItem() {
        return item;
    }

    /**
     * Imposta la lista degli oggetti.
     * 
     * @param item Lista degli oggetti.
     */
    public void setItem(List<Item> item) {
        this.item = item;
    }

    /**
     * Restituisce il nome del file immagine.
     * 
     * @return Nome del file immagine.
     */
    public String getImg() {
        return img;
    }

    /**
     * Imposta il nome del file immagine.
     * 
     * @param img Nome del file immagine.
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * Restituisce la lista delle risposte.
     * 
     * @return Lista delle risposte.
     */
    public List<Response> getRespons() {
        return respons;
    }

    /**
     * Imposta la lista delle risposte.
     * 
     * @param respons Lista delle risposte.
     */
    public void setRespons(List<Response> respons) {
        this.respons = respons;
    }
}
