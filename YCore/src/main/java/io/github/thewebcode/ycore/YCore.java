package io.github.thewebcode.ycore;

import io.github.thewebcode.ycore.command.CommandManager;
import io.github.thewebcode.ycore.command.YSystemCommand;
import io.github.thewebcode.ycore.event.impl.YCoreReadyEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class YCore extends JavaPlugin {
    private static YCore instance;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        instance = this;
        this.commandManager = new CommandManager();
        printPluginLogo(true);

        registerCommands();
        new YCoreReadyEvent(instance).call();
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

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public static YCore get() {
        return instance;
    }
}
