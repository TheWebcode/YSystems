package io.github.thewebcode.ypapersystem.menus;

import io.github.thewebcode.ycore.YCore;
import io.github.thewebcode.ycore.menu.Menu;
import io.github.thewebcode.ycore.menu.menuutility.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class YSettingsMenu extends Menu {

    public YSettingsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return YCore.get().getMessage("inventory.yclientsettings.name");
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ItemStack currentItem = e.getCurrentItem();
        String displayName = currentItem.getItemMeta().getDisplayName();

        if (displayName.equalsIgnoreCase(BACK_ITEM.getItemMeta().getDisplayName())) {
            new SettingsMenu(playerMenuUtility).open();
            return;
        }

        YCore yCore = YCore.get();
        if(!yCore.isYClientAPIEnabled()){
            ItemMeta itemMeta = currentItem.getItemMeta();
            itemMeta.setDisplayName(yCore.getMessage("inventory.yclientsettings.items.allowonlyyclient.noapi.name"));
            itemMeta.setLore(Arrays.asList(yCore.getMessage("inventory.yclientsettings.items.allowonlyyclient.noapi.lore")));
            currentItem.setItemMeta(itemMeta);
            return;
        }
    }

    @Override
    public void setMenuItems() {
        YCore yCore = YCore.get();
        ItemStack onlyYClient = yCore.itemFactory.create(Material.CHAIN_COMMAND_BLOCK, yCore.getMessage("inventory.yclientsettings.items.allowonlyyclient.name"), yCore.getMessage("inventory.yclientsettings.items.allowonlyyclient.lore"));

        inventory.setItem(11, onlyYClient);
        inventory.setItem(22, BACK_ITEM);
    }
}
