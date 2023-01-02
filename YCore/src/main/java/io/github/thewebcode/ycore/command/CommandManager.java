package io.github.thewebcode.ycore.command;

import io.github.thewebcode.ycore.event.Event;
import io.github.thewebcode.ycore.event.EventCancelable;
import io.github.thewebcode.ycore.event.impl.YCommandRegisterEvent;

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

        EventCancelable call = (EventCancelable) new YCommandRegisterEvent(command).call();
        if(call.isCancelled()) return;

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
