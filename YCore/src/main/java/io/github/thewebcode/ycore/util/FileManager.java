package io.github.thewebcode.ycore.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.thewebcode.ycore.YCore;
import org.apache.commons.io.FileUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.net.URL;

public class FileManager {
    private File FOLDER, configFile, messagesFile;

    private YamlConfiguration config;


    String messageFileUrl = "https://raw.githubusercontent.com/TheWebcode/YSystems/master/YCore/defaultfiles/messages.json";
    String configFileUrl = "https://raw.githubusercontent.com/TheWebcode/YSystems/master/YCore/defaultfiles/config.yml";

    public FileManager(){
        FOLDER = new File("plugins/YCore");
        configFile = new File(FOLDER, "config.yml");
        messagesFile = new File(FOLDER, "messages.json");

        try {

            if (!configFile.exists()) {
                YCore.logger.info("Config file not found, downloading from Github...");
                FileUtils.copyURLToFile(new URL(configFileUrl), configFile);
            }

            if (!messagesFile.exists()) {
                YCore.logger.info("Messages file not found, downloading from Github...");
                FileUtils.copyURLToFile(new URL(messageFileUrl), messagesFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveFiles() {
        try{
            config.save(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean resetConfig() {
        try{
            FileUtils.delete(configFile);
            FileUtils.delete(messagesFile);

            FileUtils.copyURLToFile(new URL(configFileUrl), configFile);
            FileUtils.copyURLToFile(new URL(messageFileUrl), messagesFile);

            config = YamlConfiguration.loadConfiguration(configFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateMessage(String path, String message){
        try {
            JsonObject jsonObject = new JsonParser().parse(FileUtils.readFileToString(messagesFile)).getAsJsonObject();
            jsonObject.remove(path);
            jsonObject.addProperty(path, message);
            Gson gson = new Gson();
            String s = gson.toJson(jsonObject);
            FileUtils.delete(messagesFile);
            FileUtils.writeStringToFile(messagesFile, s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMessage(String key) {
        try {
            JsonObject json = new JsonParser().parse(FileUtils.readFileToString(messagesFile, "UTF-8")).getAsJsonObject();
            return ChatColor.translateAlternateColorCodes('&', json.get(key).getAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
