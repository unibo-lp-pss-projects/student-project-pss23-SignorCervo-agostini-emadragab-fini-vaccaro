package it.unibo.io;

import java.io.IOException;
import java.io.File;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.scene.control.Button;
import it.unibo.io.model.SignorCervo.Member;
import it.unibo.io.model.SignorCervo.Response;
import it.unibo.io.model.SignorCervo.RootDialog;

/**
 * La classe JsonReader si occupa di leggere e gestire i dati di un file JSON contenente
 * le informazioni per un gioco di dialoghi.
 */
public class JsonReader {

    private static List<String> rule;
    private static List<Member> members;
    private static Member currentMember;
    private static Response currentResponse;
    private static SignorCervoGUI gui;

    List<File> resource = GetResources.findResourcesDirectory(new File(System.getProperty("user.dir")), "dialoghi");
    String dialogPath = resource.get(1).toURI().toString().replace("file:/", "");

    /**
     * Aggiorna i membri del dialogo corrente.
     *
     * @param i l'indice del membro corrente
     */
    public void updateMembers(int i){
        currentMember = members.get(i);
    }

    /**
     * Visualizza le regole del gioco nell'interfaccia utente.
     */
    public void getRule(){
        gui.updateStatusTerminal("-------------------REGOLE-------------------\n");
        for(int i = 0; i < rule.size(); i++){
            String formattedString = String.format("%d. %s%n", i + 1, rule.get(i));
            gui.updateStatusTerminal(formattedString);
        }
//        gui.updateStatusTerminal("---------------------------------------------\n PREMERE INVIO PER INIZIARE");
    }

    /**
     * Legge il file JSON contenente i dati dei dialoghi.
     */
    public void readJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            RootDialog rootdialog = objectMapper.readValue(new File(dialogPath), RootDialog.class);
            rule = rootdialog.getRule();
            members = rootdialog.getMembers();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restituisce la dimensione dei membri.
     *
     * @return La dimensione dei membri.
     */
    public Integer getSize() {
        return members.size();
    }

    /**
     * Stampa le scelte disponibili per un membro specifico.
     *
     * @param i l'indice del membro
     */
    public void printChoices(int i){
        updateMembers(i);
        List<Response> responses = currentMember.getRespons();
        for(int j = 0; j < responses.size(); j++){
            currentResponse = responses.get(j);
            gui.updateButton(currentResponse.getResp(), j + 1);
        }
    }

    /**
     * Stampa gli oggetti disponibili per l'acquisto.
     */
    public void printIteam(){
        List<Item> items = currentMember.getItem();
        for(int i = 0; i < items.size(); i++){
            Item item = items.get(i);
            gui.updateButton(item.getName(), i + 1);
        }
    }

    /**
     * Restituisce il dialogo per un determinato membro.
     *
     * @param i L'indice del membro.
     * @return Il dialogo.
     */
    public String getDialog(int i) {
        updateMembers(i);
        return currentMember.getDialog() + "\n";
    }

    /**
     * Restituisce l'immagine associata a un membro.
     *
     * @param i L'indice del membro.
     * @return Il percorso dell'immagine.
     */
    public String getImage(int i) {
        updateMembers(i);
        return currentMember.getImg();
    }

    /**
     * Verifica se l'opzione scelta porta alla morte.
     *
     * @param i   L'indice del membro.
     * @param key La scelta fatta dal giocatore.
     * @return True se la scelta porta alla morte, false altrimenti.
     */
    public Boolean checkChoice(int i, int key){
        updateMembers(i);
        currentResponse = currentMember.getRespons().get(key);
        return currentResponse.isDie();
    }

    /**
     * Restituisce l'oggetto richiesto per una scelta, se presente.
     *
     * @param i   L'indice del membro.
     * @param key La scelta fatta dal giocatore.
     * @return Il nome dell'oggetto richiesto, o null se non presente.
     */
    public String checkRequire(int i, int key) {
        updateMembers(i);
        currentResponse = currentMember.getRespons().get(key);
        return currentResponse.isRequire();
    }

    /**
     * Verifica se il membro ha un negozio (elementi disponibili per l'acquisto).
     *
     * @return True se il membro ha un negozio, false altrimenti.
     */
    public boolean checkShop() {
        return currentMember.getItem().size() > 1;
    }

    /**
     * Visualizza la risposta associata a una scelta.
     *
     * @param key La scelta fatta dal giocatore.
     */
    public void printReply(int key){
        currentResponse = currentMember.getRespons().get(key);
        gui.updateStatusTerminal(currentResponse.getReplay());
    }

    /**
     * Acquista un oggetto dal negozio.
     *
     * @param key La scelta fatta dal giocatore.
     * @return L'oggetto acquistato.
     */
    public Item shop(int key) {
        return currentMember.getItem().get(key);
    }

    /**
     * Restituisce le informazioni da ricordare per una determinata scelta.
     *
     * @param key La scelta fatta dal giocatore.
     * @return Una mappa contenente il numero di slide e le informazioni da ricordare.
     */
    public Map<Integer, String> giveRemember(int key){
        currentResponse = currentMember.getRespons().get(key);
        Map<Integer, String> rememberMap = new HashMap<>();
        if(currentResponse.getReplay() != null && !currentResponse.getReplay().isEmpty()){
            // Qui si aggiunge una logica ipotetica, visto che nel modello originale
            // non è presente una chiave "remember" e "numSlide".
            // La sostituzione effettiva dipenderà dalla specifica logica dell'applicazione.
            rememberMap.put(0, currentResponse.getReplay()); // Esempio
        } else {
            rememberMap.put(-1, "");
        }
        return rememberMap;
    }
}
