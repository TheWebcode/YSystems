package io.github.thewebcode.ycore.event.impl;

import io.github.thewebcode.ycore.command.YCommand;
import io.github.thewebcode.ycore.event.EventCancelable;

public class YCommandRegisterEvent extends EventCancelable {
    private YCommand command;

    public YCommandRegisterEvent(YCommand command) {
        this.command = command;
    }

    public YCommand getCommand() {
        return command;
    }
}
