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
 * The `JsonReader` class is responsible for reading and processing JSON data for the Signor Cervo game.
 */
public class JsonReader {

    private static JSONArray rule;
    private static JSONArray members;
    private static JSONObject resp;
    private static JSONObject e;
    private static JSONArray r;
    private static SignorCervoGUI gui;

    List<File> resource = GetResources.findResourcesDirectory(new File(System.getProperty("user.dir")), "dialoghi");
    String dialogPath = resource.get(1).toURI().toString().replace("file:/", "");

    /**
     * Updates the members based on the index.
     *
     * @param i The index of the member to update.
     */
    public void updateMembers(int i) {
        e = members.getJSONObject(i);
        r = e.getJSONArray("respons");
    }

    /**
     * Displays the game rules.
     */
    public void getRule() {
        gui.updateStatusTerminal("-------------------RULES-------------------\n");
        for (int i = 0; i < rule.length(); i++) {
            String formattedString = String.format("%d. %s%n", i + 1, rule.get(i));
            gui.updateStatusTerminal(formattedString);
        }
        gui.updateStatusTerminal("---------------------------------------------\n PRESS ENTER TO START");
    }

    /**
     * Reads JSON data from the specified file.
     */
    public void readJson() {
        try {
            String contents = new String(Files.readAllBytes(Paths.get(dialogPath)));
            JSONObject o = new JSONObject(contents);
            rule = o.getJSONArray("rule");
            members = o.getJSONArray("members");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the size of the members.
     *
     * @return The size of the members.
     */
    public Integer getSize() {
        return members.length();
    }

    /**
     * Displays choices for the player.
     *
     * @param i The index of the member.
     */
    public void printChoices(int i) {
        for (int j = 0; j < r.length(); j++) {
            resp = r.getJSONObject(j);
            String formattedString = String.format("\n%d. %s%n", j + 1, resp.getString("resp"));
            gui.updateStatusTerminal(formattedString);
        }
    }

    /**
     * Displays items available for purchase.
     */
    public void printIteam() {
        JSONArray item = e.optJSONArray("item");
        if (item != null) {
            for (int i = 0; i < item.length(); i++) {
                JSONObject resp = item.getJSONObject(i);
                String name = resp.getString("name");
                int number = resp.optInt("number", 0);
    
                String formattedString;
                if (number == 0) {
                    formattedString = String.format("%d. %s\n", i + 1, name);
                } else {
                    formattedString = String.format("%d. %s %d$\n", i + 1, name, number);
                }
    
                gui.updateStatusTerminal(formattedString);
            }
        }
    }

    /**
     * Gets the dialogue for a given member.
     *
     * @param i The index of the member.
     * @return The dialogue.
     */
    public String getDialog(int i) {
        return e.getString("dialog") + "\n";
    }

    /**
     * Gets the image associated with a member.
     *
     * @param i The index of the member.
     * @return The image path.
     */
    public String getImage(int i) {
        return e.getString("img");
    }

    /**
     * Checks if the chosen option leads to death.
     *
     * @param i   The index of the member.
     * @param key The choice made by the player.
     * @return True if the choice leads to death, false otherwise.
     */
    public Boolean checkChoice(int i, int key) {
        resp = r.getJSONObject(key);
        return resp.getBoolean("die");
    }

    /**
     * Gets the required item for a choice, if any.
     *
     * @param i   The index of the member.
     * @param key The choice made by the player.
     * @return The name of the required item, or null if none.
     */
    public String checkRequire(int i, int key) {
        resp = r.getJSONObject(key);
        return resp.has("require") ? resp.getString("require") : null;
    }

    /**
     * Checks if the member has a shop.
     *
     * @return True if the member has a shop, false otherwise.
     */
    public boolean checkShop() {
        JSONArray item = e.optJSONArray("item");
        return item != null && item.length() > 0;
    }

    /**
     * Displays the reply associated with a choice.
     *
     * @param key The choice made by the player.
     */
    public void printReply(int key) {
        resp = r.getJSONObject(key);
        gui.updateStatusTerminal(resp.getString("replay"));
    }

    /**
     * Gets the item from a shop.
     *
     * @param key The choice made by the player.
     * @return The purchased item.
     */
    public Item shop(int key) {
        JSONArray item = e.optJSONArray("item");
        if (item != null) {
            JSONObject resp = item.getJSONObject(key);
            return new Item(resp.getString("name"), resp.getInt("number"));
        }
        return null;
    }

    /**
     * Gets the information to remember for a given choice.
     *
     * @param key The choice made by the player.
     * @return A map containing the slide number and the information to remember.
     */
    public Map<Integer, String> giveRemember(int key) {
        resp = r.getJSONObject(key);
        if (resp.has("remember")) {
            JSONObject remember = resp.getJSONObject("remember");
            return new HashMap<Integer, String>() {{
                put(remember.getInt("numSlide"), remember.getString("replay"));
            }};
        } else {
            return new HashMap<Integer, String>() {{
                put(-1, "");
            }};
        }
    }
}
