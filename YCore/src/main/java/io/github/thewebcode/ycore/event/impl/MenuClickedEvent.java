package io.github.thewebcode.ycore.event.impl;

import io.github.thewebcode.ycore.event.Event;
import io.github.thewebcode.ycore.menu.Menu;

public class MenuClickedEvent extends Event {
    private Menu menu;

    public MenuClickedEvent(Menu menu) {
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }
}
