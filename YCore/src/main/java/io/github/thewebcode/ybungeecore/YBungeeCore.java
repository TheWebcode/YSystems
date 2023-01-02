package io.github.thewebcode.ybungeecore;

import io.github.thewebcode.ybungeecore.utils.FileManager;
import net.md_5.bungee.api.plugin.Plugin;

public class YBungeeCore extends Plugin {
    private static YBungeeCore instance;
    private FileManager fileManager;

    @Override
    public void onEnable() {
        instance = this;
        this.fileManager = new FileManager();
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public String getMessage(String path) {
        return fileManager.getMessage(path);
    }

    public static YBungeeCore get() {
        return instance;
    }
}
