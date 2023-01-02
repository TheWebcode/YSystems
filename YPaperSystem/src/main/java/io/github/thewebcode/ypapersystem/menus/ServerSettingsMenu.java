package io.github.thewebcode.ypapersystem.menus;

import io.github.thewebcode.ycore.YCore;
import io.github.thewebcode.ycore.menu.Menu;
import io.github.thewebcode.ycore.menu.menuutility.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ServerSettingsMenu extends Menu {

    public ServerSettingsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return YCore.get().getMessage("inventories.settings");
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

    }

    @Override
    public void setMenuItems() {
        YCore yCore = YCore.get();
    }
}
