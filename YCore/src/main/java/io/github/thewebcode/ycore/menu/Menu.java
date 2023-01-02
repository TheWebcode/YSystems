package io.github.thewebcode.ycore.menu;

import io.github.thewebcode.ycore.YCore;
import io.github.thewebcode.ycore.event.EventCancelable;
import io.github.thewebcode.ycore.event.impl.PlayerOpenMenuEvent;
import io.github.thewebcode.ycore.menu.menuutility.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class Menu implements InventoryHolder {

    protected PlayerMenuUtility playerMenuUtility;
    protected Inventory inventory;
    protected ItemStack FILLER_GLASS = makeItem(Material.GRAY_STAINED_GLASS_PANE, " ");
    protected ItemStack BACK_ITEM = makeItem(Material.BARRIER, YCore.get().getMessage("inventory.back"));
    protected ItemStack CLOSE_ITEM = makeItem(Material.BARRIER, YCore.get().getMessage("inventory.close"));

    public Menu(PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent e);

    public abstract void setMenuItems();

    public void open() {
        EventCancelable event = (EventCancelable) new PlayerOpenMenuEvent(this).call();
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());

        this.setMenuItems();

        if(event.isCancelled()) return;
        playerMenuUtility.getOwner().openInventory(inventory);
        playerMenuUtility.getOwner().playSound(playerMenuUtility.getOwner(), Sound.BLOCK_BARREL_OPEN, 1, 1);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void setFillerGlass(){
        for (int i = 0; i < getSlots(); i++) {
            if (inventory.getItem(i) == null){
                inventory.setItem(i, FILLER_GLASS);
            }
        }
    }

    public void addMenuBorder(){
        for (int i = 0; i < 10; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, FILLER_GLASS);
            }
        }

        inventory.setItem(17, FILLER_GLASS);
        inventory.setItem(18, FILLER_GLASS);
        inventory.setItem(26, FILLER_GLASS);
        inventory.setItem(27, FILLER_GLASS);
        inventory.setItem(35, FILLER_GLASS);
        inventory.setItem(36, FILLER_GLASS);

        for (int i = 44; i < 54; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, FILLER_GLASS);
            }
        }
    }

    public ItemStack makeItem(Material material, String displayName, String... lore) {

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);

        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);

        return item;
    }

    public PlayerMenuUtility getPlayerMenuUtility() {
        return playerMenuUtility;
    }
}
