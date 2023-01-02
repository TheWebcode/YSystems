package io.github.thewebcode.ypapersystem.menus;

import io.github.thewebcode.ycore.YCore;
import io.github.thewebcode.ycore.menu.Menu;
import io.github.thewebcode.ycore.menu.menuutility.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SettingsMenu extends Menu {

    public SettingsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return YCore.get().getMessage("inventory.settings.name");
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ItemStack currentItem = e.getCurrentItem();
        String displayName = e.getCurrentItem().getItemMeta().getDisplayName();

        if (displayName.equalsIgnoreCase(CLOSE_ITEM.getItemMeta().getDisplayName())) {
            playerMenuUtility.getOwner().closeInventory();
            return;
        }

        if(displayName.equalsIgnoreCase(YCore.get().getMessage("inventory.settings.items.spawnsettings.name"))){
            new SpawnpointSettingsMenu(playerMenuUtility).open();
            return;
        }

        if(displayName.equalsIgnoreCase(YCore.get().getMessage("inventory.settings.items.yclientsettings.name"))){
            new YSettingsMenu(playerMenuUtility).open();
            return;
        }

    }

    @Override
    public void setMenuItems() {
        YCore yCore = YCore.get();
        ItemStack spawnpointSettings = yCore.itemFactory.create(Material.NETHER_STAR, yCore.getMessage("inventory.settings.items.spawnsettings.name"), yCore.getMessage("inventory.settings.items.spawnsettings.lore"));
        ItemStack ysettingsMenu = yCore.itemFactory.create(Material.COMMAND_BLOCK, yCore.getMessage("inventory.settings.items.yclientsettings.name"), yCore.getMessage("inventory.settings.items.yclientsettings.lore"));



        inventory.setItem(11, spawnpointSettings);
        inventory.setItem(15, ysettingsMenu);

        inventory.setItem(22, CLOSE_ITEM);
    }
}
