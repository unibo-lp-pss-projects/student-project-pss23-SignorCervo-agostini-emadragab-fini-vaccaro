package it.unibo.io;

import java.io.IOException;
import java.io.File;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * La classe JsonReader si occupa di leggere e gestire i dati di un file JSON contenente
 * le informazioni per un gioco di dialoghi.
 */
public class JsonReader {

    private static JSONArray rule;
    private static JSONArray members;
    private static JSONObject resp;
    private static JSONObject e;
    private static JSONArray r;
    private static SignorCervoGUI gui;
    private static JSONObject img;

    List<File> resource = GetResources.findResourcesDirectory(new File(System.getProperty("user.dir")), "dialoghi");
    String dialogPath = resource.get(0).toURI().toString().replace("file:/", "");

    /**
     * Aggiorna i membri del dialogo corrente.
     *
     * @param i l'indice del membro corrente
     */
    public void updateMembers(int i){
        e = members.getJSONObject(i);
        r = e.getJSONArray("respons");
    }

    /**
     * Visualizza le regole del gioco nell'interfaccia utente.
     */
    public void getRule(){
        gui.updateStatusTerminal("-------------------REGOLE-------------------\n");
        for(int i = 0; i < rule.length(); i++){
            String fromattedString = String.format("%d. %s%n", i + 1, rule.get(i));
            gui.updateStatusTerminal(fromattedString);
        }
        gui.updateStatusTerminal("---------------------------------------------\n PREMERE INVIO PER INIZIARE");
    }

    /**
     * Legge il file JSON contenente i dati dei dialoghi.
     */
    public void readJson(){
        try{
            String contents = new String((Files.readAllBytes(Paths.get(dialogPath))));
            JSONObject o = new JSONObject(contents);
            rule = o.getJSONArray("rule");
            members = o.getJSONArray("members");

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Restituisce il numero di membri nel dialogo.
     *
     * @return il numero di membri nel dialogo
     */
    public Integer getSize(){
        return members.length();
    }

    /**
     * Stampa le scelte disponibili per un membro specifico.
     *
     * @param i l'indice del membro
     */
    public void printChoices(int i){
            
        for(int j = 0; j < r.length(); j++){
            resp = r.getJSONObject(j);
            String fromattedString = String.format("\n%d. %s%n", j + 1, resp.getString("resp"));
            gui.updateStatusTerminal(fromattedString);
        }

    }

    /**
     * Stampa gli oggetti disponibili per l'acquisto.
     */
    public void printIteam(){
        JSONArray item = e.optJSONArray("item");

        for(int i = 0; i < item.length(); i++){
            JSONObject resp = item.getJSONObject(i);
            String fromattedString = String.format("%d. %s %d$\n",i+1, resp.getString("name"), resp.getInt("number"));
            gui.updateStatusTerminal(fromattedString);
        }
    }


    /**
     * Restituisce il dialogo per un membro specifico.
     *
     * @param i l'indice del membro
     * @return il dialogo del membro
     */
    public String getDialog(int i){
        return (e.getString("dialog") + "\n");
    }

    /**
     * Restituisce l'URL dell'immagine per un membro specifico.
     *
     * @param i l'indice del membro
     * @return l'URL dell'immagine del membro
     */
    public String getImage(int i){
        return (e.getString("img"));
    }

    /**
     * Verifica se una scelta specifica porta alla morte del personaggio.
     *
     * @param i   l'indice del membro
     * @param key l'indice della scelta
     * @return true se la scelta porta alla morte, false altrimenti
     */
    public Boolean checkChoice(int i, int key){
        resp = r.getJSONObject(key);
        return resp.getBoolean("die");
    }

    /**
     * Verifica se è disponibile un negozio per l'acquisto di oggetti.
     *
     * @return true se il negozio è disponibile, false altrimenti
     */
    public boolean checkShop() {
        JSONArray item = e.optJSONArray("item");
        return 1 < item.length();
    }

    /**
     * Stampa la risposta corrispondente a una scelta specifica.
     *
     * @param key l'indice della scelta
     */
    public void printReply(int key){
        resp = r.getJSONObject(key);
        gui.updateStatusTerminal(resp.getString("replay")); 
    }

    /**
     * Effettua l'acquisto di un oggetto dal negozio.
     *
     * @param key l'indice dell'oggetto da acquistare
     * @return l'oggetto acquistato
     */
    public Item shop(int key) {
        JSONArray item = e.optJSONArray("item");
        JSONObject resp = item.getJSONObject(key);
        return new Item(resp.getString("name"), resp.getInt("number"));
    }

    /**
     * Restituisce una mappa contenente il numero di slide e la risposta da ricordare
     * per una scelta specifica.
     *
     * @param key l'indice della scelta
     * @return una mappa con il numero di slide e la risposta da ricordare
     */
    public Map<Integer, String> giveRemember(int key){
        resp = r.getJSONObject(key);
        if(resp.has("remember")){
            JSONObject remember = resp.getJSONObject("remember");
            return new HashMap<Integer,String>() {{
                put(remember.getInt("numSlide"), remember.getString("replay"));
            }};
        }else{
            return new HashMap<Integer,String>() {{
                put(-1, "");
            }};
        }
    }
        
}
