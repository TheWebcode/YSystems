package io.github.thewebcode.ycore.Io.github.thewebcode.event.impl;

import io.github.thewebcode.ycore.Io.github.thewebcode.YCore;
import io.github.thewebcode.ycore.Io.github.thewebcode.event.Event;

public class YCoreReadyEvent extends Event {
    private YCore instance;

    public YCoreReadyEvent(YCore instance) {
        this.instance = instance;
    }

    public YCore getPlugin() {
        return instance;
    }
}
