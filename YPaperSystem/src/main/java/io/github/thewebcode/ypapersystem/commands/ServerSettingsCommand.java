package io.github.thewebcode.ypapersystem.commands;

import io.github.thewebcode.ycore.YCore;
import io.github.thewebcode.ycore.command.Argument;
import io.github.thewebcode.ycore.command.YCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ServerSettingsCommand implements YCommand {
    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(YCore.get().getFileManager().getMessage("error.no-player"));
            return;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("y.serversettings")) {
            player.sendMessage(YCore.get().getMessage("error.no-permission"));
            return;
        }

        //TODO: Open server settings menu
    }

    @Override
    public String getName() {
        return "server-settings";
    }

    @Override
    public String getDescription() {
        return "Change the settings of the server and this plugin";
    }

    @Override
    public String getSyntax() {
        return "/ysystem server-settings";
    }

    @Override
    public HashMap<Integer, Argument> getArguments() {
        return Argument.empty();
    }
}
