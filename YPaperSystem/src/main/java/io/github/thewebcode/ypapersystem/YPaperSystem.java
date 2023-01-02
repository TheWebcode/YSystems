package io.github.thewebcode.ypapersystem;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class YPaperSystem extends JavaPlugin {
    private static YPaperSystem instance;

    @Override
    public void onEnable() {
        printLogoMessage(true);
    }

    @Override
    public void onDisable() {
        printLogoMessage(false);
    }

    private void printLogoMessage(boolean pluginEnabled) {
        Bukkit.getConsoleSender().sendMessage("\n----------------------------------------\n __     _______                      \n" +
                " \\ \\   / /  __ \\                     \n" +
                "  \\ \\_/ /| |__) |_ _ _ __   ___ _ __ \n" +
                "   \\   / |  ___/ _` | '_ \\ / _ \\ '__|\n" +
                "    | |  | |  | (_| | |_) |  __/ |   \n" +
                "    |_|  |_|   \\__,_| .__/ \\___|_|   \n" +
                "                    | |              \n" +
                "                    |_|              " + " v" + getDescription().getVersion() + " by TheWebCode is now " + (pluginEnabled ? "§aenabled" : "§cdisabled") + "§f!\n" +
                "----------------------------------------");
    }

    public static YPaperSystem getInstance() {
        return instance;
    }
}
