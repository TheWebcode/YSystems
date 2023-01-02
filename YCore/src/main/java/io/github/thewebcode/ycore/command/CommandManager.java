package io.github.thewebcode.ycore.command;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandManager {
    private ArrayList<YCommand> commands = new ArrayList<>();

    public void register(YCommand command) {
        commands.forEach(cmd -> {
            if(cmd.getName().equalsIgnoreCase(command.getName())){
                throw new IllegalArgumentException("Command already exists!");
            }
        });

        commands.add(command);
    }

    public ArrayList<YCommand> getCommands() {
        return commands;
    }

    public YCommand getByName(String name) {
        for(YCommand command : commands){
            if(command.getName().equalsIgnoreCase(name)){
                return command;
            }
        }

        return null;
    }
}
