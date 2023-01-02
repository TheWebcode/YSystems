package io.github.thewebcode.ypapersystem.menus;

import io.github.thewebcode.ycore.YCore;
import io.github.thewebcode.ycore.menu.Menu;
import io.github.thewebcode.ycore.menu.menuutility.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnpointSettingsMenu extends Menu {

    public SpawnpointSettingsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return YCore.get().getMessage("inventory.spawnpointsettings.name");
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
            new SpawnpointSettingsMenu(playerMenuUtility).open();
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack setSpawnpointItem = YCore.get().itemFactory.create(Material.STRUCTURE_VOID, YCore.get().getMessage("inventory.spawnpointsettings.items.setspawn.name"), YCore.get().getMessage("inventory.spawnpointsettings.items.setspawn.lore"));

        inventory.setItem(11, setSpawnpointItem);
        inventory.setItem(22, BACK_ITEM);
    }
}
