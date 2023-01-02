package io.github.thewebcode.ycore.util;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileManager {
    private File FOLDER, configFile, messagesFile;

    private YamlConfiguration config, messages;

    public FileManager(){
        FOLDER = new File("plugins/YCore");
        configFile = new File(FOLDER, "config.yml");
        messagesFile = new File(FOLDER, "messages.yml");

        try {
            if (!configFile.exists()) {

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }
}
