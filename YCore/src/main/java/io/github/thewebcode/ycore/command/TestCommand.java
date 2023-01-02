package io.github.thewebcode.ycore.command;

import org.bukkit.command.CommandSender;

import java.util.HashMap;

public class TestCommand implements YCommand{
    @Override
    public void execute(CommandSender sender, String label, String[] args) {

    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getDescription() {
        return "Test Command";
    }

    @Override
    public String getSyntax() {
        return "/ysystem test <test|hello|1|2>";
    }

    @Override
    public HashMap<Integer, Argument> getArguments() {
        return Argument.asMap(
                new Argument("test", "hello", 1, 2)
        );
    }
}
