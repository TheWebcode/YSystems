package io.github.thewebcode.ycore.command.impl;

import io.github.thewebcode.ycore.YCore;
import io.github.thewebcode.ycore.command.Argument;
import io.github.thewebcode.ycore.command.YCommand;
import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class ConfigCommand implements YCommand {
    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(YCore.get().getFileManager().getMessage("error.invalid-argument"));
            return;
        }

        if(args[0].equalsIgnoreCase("reset")){
            boolean success = YCore.get().getFileManager().resetConfig();

            sender.sendMessage(YCore.get().getMessage(success ? "config.reset" : "error.configreset"));
        }
    }

    @Override
    public String getName() {
        return "config";
    }

    @Override
    public String getDescription() {
        return "Edit the config of this plugin";
    }

    @Override
    public String getSyntax() {
        return "/ysystem config <reset>";
    }

    @Override
    public HashMap<Integer, Argument> getArguments() {
        return Argument.asMap(
                new Argument("reset")
        );
    }
}
