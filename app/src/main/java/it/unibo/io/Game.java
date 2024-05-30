package it.unibo.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;

public class Game {

   private static JsonReader j = new JsonReader();
   private SignorCervoGUI gui;
   int i = 0;
   Choice c = new Choice();
   Scanner myObj = new Scanner(System.in);
   Player player = new Player();
   Integer key = 2;
   Map<Integer, String> futureResponss = new HashMap<Integer, String>();
   private String exampleState;

   Game() {
      j.readJson();
   }

   public void output() {

      if (this.i > j.getSize()) {
         return;
      }
      ;

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

   }

   public void input(String cmd) {

      if (j.checkShop()) {

         key = c.inputChoiceShop(cmd);
         key = key - 1;
         player.addItem(j.shop(key));
         this.i++;
         return;
      }

      key = c.inputChoice(cmd);
      key = key - 1;

      if (j.checkChoice(i, key)) {

         j.printReply(key);
         this.i = 0;
         return;

      }

      j.printReply(key);

      this.i++;
   }

   // metodo per popolare il file checkpoint.json e salvare lo stato del gioco
   public JSONObject getState() {
      JSONObject state = new JSONObject();

      // dati che mi interessano per salvare lo stato del gioco
      state.put("i", this.i);
      state.put("key", this.key);
      state.put("player", this.player.getPlayerState()); // metodo della classe Player che salva lo stato del player, inclusi item
      state.put("futureResponss", new JSONObject(this.futureResponss));
      return state;
   }

   // metodo per caricare il gioco ripristinando i dati dal file checkpoint.json
   public void loadState(JSONObject state) {
      // dati che mi servono per ripristinare lo stato del gioco
      this.i = state.getInt("i");
      this.key = state.getInt("key");
      this.player.loadState(state.getJSONObject("player")); // metodo della classe Player che carica lo stato del player, inclusi item
      this.futureResponss = convertToMap(state.getJSONObject("futureResponss"));
   }

   // un metodo per convertire JSONObject in Map
   private Map<Integer, String> convertToMap(JSONObject jsonObject) {
      Map<Integer, String> map = new HashMap<>();
      for (String key : jsonObject.keySet()) {
         map.put(Integer.parseInt(key), jsonObject.getString(key));
      }
      return map;
   }
}
