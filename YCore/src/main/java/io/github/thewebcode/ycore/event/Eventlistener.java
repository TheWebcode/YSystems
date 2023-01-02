package io.github.thewebcode.ycore.event;

import io.github.thewebcode.ycore.event.impl.MenuClickedEvent;
import io.github.thewebcode.ycore.menu.Menu;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class Eventlistener implements Listener {
    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getItemMeta() == null) return;

        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            Menu menu = (Menu) holder;
            Player owner = menu.getPlayerMenuUtility().getOwner();
            owner.playSound(owner, Sound.BLOCK_ANVIL_STEP, 1, 1);
            menu.handleMenu(e);
            new MenuClickedEvent(menu).call();
        }
    }
}
