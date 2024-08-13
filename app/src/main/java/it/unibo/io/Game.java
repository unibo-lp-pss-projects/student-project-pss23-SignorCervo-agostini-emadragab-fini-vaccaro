package it.unibo.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * La classe Game rappresenta il gioco principale.
 */
public class Game {

   private static JsonReader j = new JsonReader();
   private SignorCervoGUI gui;
   int i = 0;
   Choice c = new Choice();
   Scanner myObj = new Scanner(System.in);
   Integer key = 2;
   int level = 0;
   Player player;
   private int dialogo;
   Map<Integer, String> futureResponss = new HashMap<Integer, String>();

   /**
    * Costruttore della classe Game.
    * Inizializza il gioco leggendo i dati dal file JSON in base al dialogo passato.
    */
   Game(int dialogo, Player p) {
      j.readJson(dialogo);
      level = dialogo;
      this.dialogo = dialogo;
      player = p;
   }

   /**
    * Metodo per generare l'output del gioco.
    * Aggiorna l'interfaccia grafica con l'immagine e il dialogo correnti.
    * Stampa le scelte disponibili e gli oggetti nel negozio, se presenti.
    */
    public boolean output() {
      if (this.i == j.getSize()) {
         // Mostra l'alert di completamento del livello
         Platform.runLater(() -> showLevelCompletedAlert());
         return false;
      }

      j.updateMembers(i);

      gui.updateImage(j.getImage(i));
      gui.updateStatusTerminal(j.getDialog(i));

      try {
         TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

      j.printChoices(i);

      if (j.checkShop())
         j.printIteam();
      return true;
   }
   /**
    * Metodo per gestire l'input dell'utente.
    * Elabora la scelta dell'utente e aggiorna lo stato del gioco di conseguenza.
    *
    * @param cmd numero della risposta
    */
   public void input(int cmd) {

      if (j.checkShop()) {
         key = cmd;
         key = key - 1;
         player.addItem(j.shop(key));
         if (player.getSizeItem() == 3)
            this.i++;
         return;
      }

      key = cmd;
      key = key - 1;

      if (j.checkChoice(i, key, player)) {

         if (j.checkRequire(i, key, player))
            j.printReply(key);
         else
            j.printReply(key+1);
         this.i = 0;
         return;

      }

      j.printReply(key);

      this.i++;
   }

   /**
    *
    * Controllo delle monete del player con quelle passate.
    *
    * @param coin monete
    */
   public boolean playerCoin(int coin) {
      if (player.getCoin() >= coin) {
         return false;
      } else {
         return true;
      }
   }

   public int getLevel() {
      return level;
   }

   public int getDialogo() {
      return this.dialogo;
   }

   // metodo per popolare il file checkpoint.json e salvare lo stato del gioco
   public JSONObject getState() {
      JSONObject state = new JSONObject();

      // dati che mi interessano per salvare lo stato del gioco
      state.put("i", this.i);
      state.put("key", this.key);
      state.put("player", this.player.getPlayerState()); // metodo della classe Player che salva lo stato del player, inclusi item
      state.put("futureResponss", new JSONObject(this.futureResponss));
      state.put("dialogo", this.dialogo);
      return state;
   }

   // metodo per caricare il gioco ripristinando i dati dal file checkpoint.json
   public void loadState(JSONObject state) {
      // dati che mi servono per ripristinare lo stato del gioco
      this.i = state.getInt("i");
      this.key = state.getInt("key");
      this.player.loadState(state.getJSONObject("player")); // metodo della classe Player che carica lo stato del
                                                            // player, inclusi item
      this.futureResponss = convertToMap(state.getJSONObject("futureResponss"));
      this.dialogo = state.getInt("dialogo"); // Ripristina dialogo
   }

   // un metodo per convertire JSONObject in Map
   private Map<Integer, String> convertToMap(JSONObject jsonObject) {
      Map<Integer, String> map = new HashMap<>();
      for (String key : jsonObject.keySet()) {
         map.put(Integer.parseInt(key), jsonObject.getString(key));
      }
      return map;
   }

   // metodo che mostra un alert quando il livello è completato
   private static void showLevelCompletedAlert() {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Livello Completato");
      alert.setHeaderText(null);
      alert.setContentText("Complimenti hai completato il livello!");
      alert.showAndWait();
   }
}
