package it.unibo.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonReader {

    private static JSONArray rule;
    private static JSONArray members;
    private static JSONObject resp;
    private static JSONObject e;
    private static JSONArray r;
    private static SignorCervoGUI gui;

    public void updateMembers(int i){
        e = members.getJSONObject(i);
        r = e.getJSONArray("respons");
    }

    public void getRule(){
        gui.updateStatusTerminal("-------------------REGOLE-------------------\n");
        for(int i = 0; i < rule.length(); i++){
            String fromattedString = String.format("%d. %s%n", i + 1, rule.get(i));
            gui.updateStatusTerminal(fromattedString);
        }
        gui.updateStatusTerminal("---------------------------------------------\n PREMERE INVIO PER INIZIARE");
    }

    public void readJson(){
        try{
            String contents = new String((Files.readAllBytes(Paths.get("app\\src\\main\\java\\it\\unibo\\io\\dialoghi\\dialoghi.json"))));
            JSONObject o = new JSONObject(contents);
            rule = o.getJSONArray("rule");
            members = o.getJSONArray("members");

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Integer getSize(){
        return members.length();
    }

    public void printChoices(int i){
            
        for(int j = 0; j < r.length(); j++){
            resp = r.getJSONObject(j);
            String fromattedString = String.format("\n%d. %s%n", j + 1, resp.getString("resp"));
            gui.updateStatusTerminal(fromattedString);
        }

    }

    public void printIteam(){
        JSONArray item = e.optJSONArray("item");

        for(int i = 0; i < item.length(); i++){
            JSONObject resp = item.getJSONObject(i);
            String fromattedString = String.format("%d. %s %d$\n",i+1, resp.getString("name"), resp.getInt("number"));
            gui.updateStatusTerminal(fromattedString);
        }
    }

    public String getDialog(int i){
        // System.out.println("\n" + e.getString("dialog"));
        return (e.getString("dialog") + "\n");
    }

    public Boolean checkChoice(int i, int key){
        resp = r.getJSONObject(key);
        return resp.getBoolean("die");
    }

    public boolean checkShop() {
        JSONArray item = e.optJSONArray("item");
        return 1 < item.length();
    }

    public void printReply(int key){
        resp = r.getJSONObject(key);
        gui.updateStatusTerminal(resp.getString("replay")); 
    }

    public Item shop(int key) {
        JSONArray item = e.optJSONArray("item");
        JSONObject resp = item.getJSONObject(key);
        return new Item(resp.getString("name"), resp.getInt("number"));
    }

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
