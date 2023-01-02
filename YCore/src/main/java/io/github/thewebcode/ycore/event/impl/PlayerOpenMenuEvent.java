package io.github.thewebcode.ycore.event.impl;

import io.github.thewebcode.ycore.event.EventCancelable;
import io.github.thewebcode.ycore.menu.Menu;

public class PlayerOpenMenuEvent extends EventCancelable {
    private Menu menu;

    public PlayerOpenMenuEvent(Menu menu) {
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }
}
