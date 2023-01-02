package io.github.thewebcode.ycore.util;

import io.github.thewebcode.ycore.YCore;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.net.URL;

public class FileManager {
    private File FOLDER, configFile, messagesFile;

    private YamlConfiguration config, messages;

    public FileManager(){
        FOLDER = new File("plugins/YCore");
        configFile = new File(FOLDER, "config.yml");
        messagesFile = new File(FOLDER, "messages.yml");

        try {
            String messageFileUrl = "https://raw.githubusercontent.com/TheWebcode/YSystems/master/YCore/defaultfiles/messages.yml";
            String configFileUrl = "https://raw.githubusercontent.com/TheWebcode/YSystems/master/YCore/defaultfiles/config.yml";

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
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void saveFiles() {
        try{
            config.save(configFile);
            messages.save(messagesFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMessage(String key) {
        return messages.getString(key);
    }
}
