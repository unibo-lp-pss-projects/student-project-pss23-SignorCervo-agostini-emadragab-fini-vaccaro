package it.unibo.io.model.SignorCervo;

/**
 * Rappresenta una risposta con un testo di risposta, un'azione di riproduzione e una bandiera di morte.
 */
public class Response {
    private String resp;
    private String replay;
    private boolean die;
    private String require;
    private Remember remember;

    /**
     * Restituisce il testo della risposta.
     * 
     * @return Testo della risposta.
     */
    public String getResp() {
        return resp;
    }

    /**
     * Imposta il testo della risposta.
     * 
     * @param resp Testo della risposta.
     */
    public void setResp(String resp) {
        this.resp = resp;
    }

    /**
     * Restituisce il testo della riproduzione.
     * 
     * @return Testo della riproduzione.
     */
    public String getReplay() {
        return replay;
    }

    /**
     * Imposta il testo della riproduzione.
     * 
     * @param replay Testo della riproduzione.
     */
    public void setReplay(String replay) {
        this.replay = replay;
    }

    /**
     * Restituisce se il giocatore muore o no.
     * 
     * @return True se la risposta porta alla morte, altrimenti false.
     */
    public boolean isDie() {
        return die;
    }

    /**
     * Imposta la morte o meno del giocatore.
     * 
     * @param die True se la risposta porta alla morte, altrimenti false.
     */
    public void setDie(boolean die) {
        this.die = die;
    }

    /**
     * Restituisce se un oggetto è richiesto o no.
     * 
     * @return Nome dell' oggetto richiesto.
     */
    public String getRequire() {
        return require;
    }

    /**
     * Imposta il nome dell'oggetto richiesto.
     * 
     * @param require Nome dell'oggetto richiesto.
     */
    public void setRequire(String require) {
        this.require = require;
    }

    /**
     * Restituisce l'oggetto Remember associato alla risposta.
     * 
     * @return L'oggetto Remember associato alla risposta.
     */
    public Remember getRemember() {
        return remember;
    }

    /**
     * Imposta l'oggetto Remember associato alla risposta.
     * 
     * @param remember L'oggetto Remember da associare alla risposta.
     */
    public void setRemember(Remember remember) {
        this.remember = remember;
    }    
}
