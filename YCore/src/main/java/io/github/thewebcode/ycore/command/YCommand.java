package io.github.thewebcode.ycore.command;

import org.bukkit.command.CommandSender;

import java.util.HashMap;

public interface YCommand {
    void execute(CommandSender sender, String label, String[] args);

    String getName();

    String getDescription();

    String getSyntax();

    HashMap<Integer, Argument> getArguments();
}
