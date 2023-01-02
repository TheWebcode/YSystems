package io.github.thewebcode.ycore.event.impl;

import io.github.thewebcode.ycore.YCore;
import io.github.thewebcode.ycore.event.Event;

public class YCoreReadyEvent extends Event {
    private YCore instance;

    public YCoreReadyEvent(YCore instance) {
        this.instance = instance;
    }

    public YCore getPlugin() {
        return instance;
    }
}
