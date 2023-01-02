package io.github.thewebcode.ybungeecore.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;

public class FileManager {
    private File FOLDER, configFile, messagesFile;

    private Configuration config;

    String messageFileUrl = "https://raw.githubusercontent.com/TheWebcode/YSystems/master/YCore/defaultfiles/messages.json";
    String configFileUrl = "https://raw.githubusercontent.com/TheWebcode/YSystems/master/YCore/defaultfiles/config.yml";


    public FileManager(){
        this.FOLDER = new File("plugins/YBungeeCore");
        this.configFile = new File(FOLDER, "config.yml");

        try{
            if(!FOLDER.exists()) FOLDER.mkdir();
            if(!configFile.exists()) {
                FileUtils.copyURLToFile(new URL(configFileUrl), configFile);
            }

            if(!messagesFile.exists()) {
                FileUtils.copyURLToFile(new URL(messageFileUrl), messagesFile);
            }

            config = YamlConfiguration.getProvider(YamlConfiguration.class).load(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try{
            YamlConfiguration.getProvider(YamlConfiguration.class).save(config, configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMessage(String path) {
        try {
            JsonObject json = new JsonParser().parse(FileUtils.readFileToString(messagesFile)).getAsJsonObject();
            return ChatColor.translateAlternateColorCodes('&', json.get(path).getAsString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public Configuration getConfig() {
        return config;
    }
}
