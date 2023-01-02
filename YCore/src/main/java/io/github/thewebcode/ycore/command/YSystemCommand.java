package io.github.thewebcode.ycore.command;

import io.github.thewebcode.ycore.YCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class YSystemCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0){

        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CommandManager commandManager = YCore.get().getCommandManager();
        ArrayList<YCommand> commands = commandManager.getCommands();

        if(args.length == 1){
            ArrayList<String> commandNames = new ArrayList<>();
            commands.forEach(cmd -> commandNames.add(cmd.getName()));
            return commandNames;
        }

        if(args.length > 1){
            YCommand yCommand = commandManager.getByName(args[0]);
            if(yCommand == null) return null;


        }

        return null;
    }
}
