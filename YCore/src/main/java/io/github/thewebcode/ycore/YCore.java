package io.github.thewebcode.ycore;

import io.github.thewebcode.ycore.command.CommandManager;
import io.github.thewebcode.ycore.command.YSystemCommand;
import io.github.thewebcode.ycore.command.impl.ConfigCommand;
import io.github.thewebcode.ycore.event.Eventlistener;
import io.github.thewebcode.ycore.event.impl.YCoreReadyEvent;
import io.github.thewebcode.ycore.menu.InventoryService;
import io.github.thewebcode.ycore.util.FileManager;
import io.github.thewebcode.ycore.util.ItemFactory;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class YCore extends JavaPlugin {
    public static Logger logger = Logger.getLogger("YCore");
    public ItemFactory itemFactory = new ItemFactory();
    public InventoryService inventoryService = new InventoryService();
    private static YCore instance;
    private CommandManager commandManager;
    private FileManager fileManager;

    @Override
    public void onEnable() {
        instance = this;
        this.fileManager = new FileManager();
        this.commandManager = new CommandManager();
        printPluginLogo(true);

        registerCommands();
        registerEvents();

        commandManager.register(new ConfigCommand());
        new YCoreReadyEvent(instance).call();
    }

    private void registerEvents(){
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new Eventlistener(), this);
    }

    private void registerCommands() {
        YSystemCommand command = new YSystemCommand();
        getCommand("ysystem").setExecutor(command);
        getCommand("ysystem").setTabCompleter(command);
    }

    @Override
    public void onDisable() {
        printPluginLogo(false);
    }

    private void printPluginLogo(boolean pluginStatusEnabled) {
        PluginDescriptionFile description = this.getDescription();

        ConsoleCommandSender console = Bukkit.getConsoleSender();

        console.sendMessage("----------------------------------------");
        console.sendMessage("§4 __     _______               ");
        console.sendMessage("§6 \\ \\   / / ____|              ");
        console.sendMessage("§e  \\ \\_/ / |     ___  _ __ ___ ");
        console.sendMessage("§a   \\   /| |    / _ \\| '__/ _ \\");
        console.sendMessage("§b    | | | |___| (_) | | |  __/");
        console.sendMessage("§9    |_|  \\_____\\___/|_|  \\___|");
        console.sendMessage(" ");
        console.sendMessage("Author: " + description.getAuthors());
        console.sendMessage("Version: " + description.getVersion());
        console.sendMessage("Plugin: " + (pluginStatusEnabled ? "§aEnabled" : "§cDisabled"));
        console.sendMessage("----------------------------------------");
    }

    public boolean isYClientAPIEnabled() {
        return Bukkit.getPluginManager().getPlugin("YAPI") != null;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public String getMessage(String key){
        return fileManager.getMessage(key);
    }

    public static YCore get() {
        return instance;
    }
}
