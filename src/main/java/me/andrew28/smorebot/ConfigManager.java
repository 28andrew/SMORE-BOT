package me.andrew28.smorebot;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import me.andrew28.smorebot.types.Rank;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrew Tran
 */
public class ConfigManager {
    private static File saveFile;
    private static Yaml yaml;
    private static HashMap<Object, Object> config = new HashMap<>();
    private static ArrayList<SmoreUser> users = new ArrayList<>();
    public static void init(){
        yaml = new Yaml();
        try {
            saveFile = new File("./save.yml");
            if (!saveFile.exists()){
                saveFile.createNewFile();
            }
            config = (HashMap<Object, Object>) yaml.load(new FileInputStream(saveFile));
            if (config == null){
                config = new HashMap<Object, Object>();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not load YML");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Could not create YML");
            e.printStackTrace();
        }
        loadUsers();
    }

    private static void loadUsers(){
        users.clear();
        if (!config.containsKey("users")){
            config.put("users", new HashMap<String, Object>());
        }
        HashMap<String, Object> users = (HashMap<String, Object>) config.get("users");
        for (Map.Entry<String, Object> user : users.entrySet()){
            String ID = user.getKey();
            HashMap<String, String> values = (HashMap<String, String>) user.getValue();
            ConfigManager.users.add(new SmoreUser(ID, Rank.valueOf(values.get("rank"))));
        }
    }
    private static void createUserIfNotExists(String ID, Rank rank){
        if (!config.containsKey("users")){
            config.put("users", new HashMap<String, Object>());
        }
        HashMap<String, Object> users = (HashMap<String, Object>) config.get("users");
        if (!users.containsKey(ID)){
            //Create user
            HashMap<String, String> userInfo = new HashMap<>();
            userInfo.put("rank", rank.name());
            users.put(ID, userInfo);
        }
        config.put("users", users);
        save();
    }
    public static SmoreUser getUser(String ID){
        createUserIfNotExists(ID, Rank.NORMAL);
        for (SmoreUser su : users){
            if (su.getID().equals(ID)){
                return su;
            }
        }
        return null;
    }
    public static HashMap<Object, Object> getConfig(){
        return config;
    }
    public static void save(HashMap<Object, Object> config){
        ConfigManager.config = config;
        save();
    }
    public static void save(){
        try {
            yaml.dump(config, new FileWriter(saveFile));
            loadUsers();
        } catch (IOException e) {
            System.out.println("Could not save YML");
            e.printStackTrace();
        }
    }


    public static class SmoreUser{
        private String ID;
        private Rank rank;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public Rank getRank() {
            return rank;
        }

        public void setRank(Rank rank) {
            this.rank = rank;
        }


        public SmoreUser(String ID, Rank rank){
            this.ID = ID;
            this.rank = rank;
        }

    }
}
