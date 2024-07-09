package it.unibo.io.model.SignorCervo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Rappresenta la radice della struttura JSON contenente regole e membri.
 */
public class RootDialog {
    private List<String> rule;
    private List<Member> members;

    /**
     * Restituisce la lista delle regole.
     * 
     * @return Lista delle regole.
     */
    public List<String> getRule() {
        return rule;
    }

    /**
     * Imposta la lista delle regole.
     * 
     * @param rule Lista delle regole.
     */
    public void setRule(List<String> rule) {
        this.rule = rule;
    }

    /**
     * Restituisce la lista dei membri.
     * 
     * @return Lista dei membri.
     */
    public List<Member> getMembers() {
        return members;
    }

    /**
     * Imposta la lista dei membri.
     * 
     * @param members Lista dei membri.
     */
    public void setMembers(List<Member> members) {
        this.members = members;
    }
}
