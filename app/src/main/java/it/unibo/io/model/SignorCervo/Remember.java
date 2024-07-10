package it.unibo.io.model.SignorCervo;

/**
 * Classe che rappresenta un oggetto di tipo Remember, utilizzato per memorizzare
 * informazioni aggiuntive relative alle risposte, come il numero della slide e un testo di riproduzione.
 */
public class Remember {
    private int numSlide;
    private String replay;

    /**
     * Restituisce il numero della slide associata.
     * 
     * @return Il numero della slide.
     */
    public int getNumSlide() {
        return numSlide;
    }

    /**
     * Imposta il numero della slide associata.
     * 
     * @param numSlide Il numero della slide.
     */
    public void setNumSlide(int numSlide) {
        this.numSlide = numSlide;
    }

    /**
     * Restituisce il testo di riproduzione associato.
     * 
     * @return Il testo di riproduzione.
     */
    public String getReplay() {
        return replay;
    }

    /**
     * Imposta il testo di riproduzione associato.
     * 
     * @param replay Il testo di riproduzione.
     */
    public void setReplay(String replay) {
        this.replay = replay;
    }
}
