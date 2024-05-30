package it.unibo.io;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Classe Player rappresenta il giocatore
 */
public class Player {

   private ArrayList<Item> item = new ArrayList<>();
   private Integer coin = 4;
   private static SignorCervoGUI gui;

   public void addItem(Item item) {
      if (0 > coin - item.getNum()) {
         gui.updateStatusTerminal("Non hai abbastanza monete");
      } else {
         this.coin = this.coin - item.getNum();
         this.item.add(item);
         String fromattedString = String.format("Hai comprato %s", item.getName());
         gui.updateStatusTerminal(fromattedString);
      }

   }

   public boolean getItem(Item name) {
      if (this.item.contains(name)) {
         return true;
      } else {
         return false;
      }
   }

   public Integer getCoin() {
      return this.coin;
   }

   // metodo che salva lo stato del player, inclusi item e coin
   public JSONObject getPlayerState() {
      JSONObject state = new JSONObject();
      // salvataggio delle monete del giocatore
      state.put("coin", this.coin);

      // salvataggio degli oggetti dell'inventario del giocatore
      JSONArray items = new JSONArray();
      for (Item item : this.item) {
         JSONObject itemJson = new JSONObject();
         itemJson.put("name", item.getName());
         itemJson.put("num", item.getNum());
         items.put(itemJson);
      }
      state.put("items", items);

      return state;
   }

   // metodo della classe Player che carica lo stato del player, inclusi item
   public void loadState(JSONObject state) {
      // ripristino delle monete del giocatore
      this.coin = state.getInt("coin");

      // ripristino degli oggetti dell'inventario del giocatore
      this.item.clear();
      JSONArray items = state.getJSONArray("items");
      for (int i = 0; i < items.length(); i++) {
         JSONObject itemJson = items.getJSONObject(i);
         String name = itemJson.getString("name");
         int num = itemJson.getInt("num");
         this.item.add(new Item(name, num));
      }
   }
}
