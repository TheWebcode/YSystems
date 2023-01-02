package io.github.thewebcode.ycore.menu;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class AnvilGUI {

    private static final VersionWrapper WRAPPER = new Wrapper();

    private static final ItemStack AIR = new ItemStack(Material.AIR);

    private final Plugin plugin;
    private final Player player;
    private final String inventoryTitle;
    private final ItemStack[] initialContents;
    private final boolean preventClose;

    private final Set<Integer> interactableSlots;

    private final Consumer<Player> closeListener;
    private final Function<Completion, List<ResponseAction>> completeFunction;

    private final Consumer<Player> inputLeftClickListener;
    private final Consumer<Player> inputRightClickListener;

    private int containerId;

    private Inventory inventory;
    private final ListenUp listener = new ListenUp();

    private boolean open;

    private AnvilGUI(
            Plugin plugin,
            Player player,
            String inventoryTitle,
            ItemStack[] initialContents,
            boolean preventClose,
            Set<Integer> interactableSlots,
            Consumer<Player> closeListener,
            Consumer<Player> inputLeftClickListener,
            Consumer<Player> inputRightClickListener,
            Function<Completion, List<ResponseAction>> completeFunction) {
        this.plugin = plugin;
        this.player = player;
        this.inventoryTitle = inventoryTitle;
        this.initialContents = initialContents;
        this.preventClose = preventClose;
        this.interactableSlots = Collections.unmodifiableSet(interactableSlots);
        this.closeListener = closeListener;
        this.inputLeftClickListener = inputLeftClickListener;
        this.inputRightClickListener = inputRightClickListener;
        this.completeFunction = completeFunction;

        openInventory();
    }

    private void openInventory() {
        WRAPPER.handleInventoryCloseEvent(player);
        WRAPPER.setActiveContainerDefault(player);

        Bukkit.getPluginManager().registerEvents(listener, plugin);

        final Object container = WRAPPER.newContainerAnvil(player, inventoryTitle);

        inventory = WRAPPER.toBukkitInventory(container);
        // We need to use setItem instead of setContents because a Minecraft ContainerAnvil
        // contains two separate inventories: the result inventory and the ingredients inventory.
        // The setContents method only updates the ingredients inventory unfortunately,
        // but setItem handles the index going into the result inventory.
        for (int i = 0; i < initialContents.length; i++) {
            inventory.setItem(i, initialContents[i]);
        }

        containerId = WRAPPER.getNextContainerId(player, container);
        WRAPPER.sendPacketOpenWindow(player, containerId, inventoryTitle);
        WRAPPER.setActiveContainer(player, container);
        WRAPPER.setActiveContainerId(container, containerId);
        WRAPPER.addActiveContainerSlotListener(container, player);

        open = true;
    }

    public void closeInventory() {
        closeInventory(true);
    }

    private void closeInventory(boolean sendClosePacket) {
        if (!open) {
            return;
        }

        open = false;

        HandlerList.unregisterAll(listener);

        if (sendClosePacket) {
            WRAPPER.handleInventoryCloseEvent(player);
            WRAPPER.setActiveContainerDefault(player);
            WRAPPER.sendPacketCloseWindow(player, containerId);
        }

        if (closeListener != null) {
            closeListener.accept(player);
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    private class ListenUp implements Listener {

        @EventHandler
        public void onInventoryClick(InventoryClickEvent event) {
            if (!event.getInventory().equals(inventory)) {
                return;
            }

            final Player clicker = (Player) event.getWhoClicked();
            // prevent players from merging items from the anvil inventory
            final Inventory clickedInventory = event.getClickedInventory();
            if (clickedInventory != null
                    && clickedInventory.equals(clicker.getInventory())
                    && event.getClick().equals(ClickType.DOUBLE_CLICK)) {
                event.setCancelled(true);
                return;
            }

            if (event.getRawSlot() < 3 || event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) {
                final int slot = event.getRawSlot();
                event.setCancelled(!interactableSlots.contains(slot));

                if (event.getRawSlot() == Slot.OUTPUT) {
                    final ItemStack clicked = inventory.getItem(Slot.OUTPUT);
                    if (clicked == null || clicked.getType() == Material.AIR) return;

                    final List<ResponseAction> actions = completeFunction.apply(new Completion(
                            notNull(inventory.getItem(Slot.INPUT_LEFT)),
                            notNull(inventory.getItem(Slot.INPUT_RIGHT)),
                            notNull(inventory.getItem(Slot.OUTPUT)),
                            player,
                            clicked.hasItemMeta() ? clicked.getItemMeta().getDisplayName() : ""));
                    for (final ResponseAction action : actions) {
                        action.accept(AnvilGUI.this, clicker);
                    }
                } else if (event.getRawSlot() == Slot.INPUT_LEFT) {
                    if (inputLeftClickListener != null) {
                        inputLeftClickListener.accept(player);
                    }
                } else if (event.getRawSlot() == Slot.INPUT_RIGHT) {
                    if (inputRightClickListener != null) {
                        inputRightClickListener.accept(player);
                    }
                }
            }
        }

        @EventHandler
        public void onInventoryDrag(InventoryDragEvent event) {
            if (event.getInventory().equals(inventory)) {
                for (int slot : Slot.values()) {
                    if (event.getRawSlots().contains(slot)) {
                        event.setCancelled(!interactableSlots.contains(slot));
                        break;
                    }
                }
            }
        }

        @EventHandler
        public void onInventoryClose(InventoryCloseEvent event) {
            if (open && event.getInventory().equals(inventory)) {
                closeInventory(false);
                if (preventClose) {
                    Bukkit.getScheduler().runTask(plugin, AnvilGUI.this::openInventory);
                }
            }
        }
    }

    private ItemStack notNull(ItemStack stack) {
        return stack == null ? AIR : stack;
    }

    public static class Builder {

        private Consumer<Player> closeListener;
        private boolean preventClose = false;

        private Set<Integer> interactableSlots = Collections.emptySet();

        private Consumer<Player> inputLeftClickListener;
        private Consumer<Player> inputRightClickListener;
        private Function<Completion, List<ResponseAction>> completeFunction;
        private Plugin plugin;
        private String title = "Repair & Name";
        private String itemText;
        private ItemStack itemLeft;
        private ItemStack itemRight;
        private ItemStack itemOutput;

        public Builder preventClose() {
            preventClose = true;
            return this;
        }

        public Builder interactableSlots(int... slots) {
            final Set<Integer> newValue = new HashSet<>();
            for (int slot : slots) {
                newValue.add(slot);
            }
            interactableSlots = newValue;
            return this;
        }

        public Builder onClose(Consumer<Player> closeListener) {
            Validate.notNull(closeListener, "closeListener cannot be null");
            this.closeListener = closeListener;
            return this;
        }

        public Builder onLeftInputClick(Consumer<Player> inputLeftClickListener) {
            this.inputLeftClickListener = inputLeftClickListener;
            return this;
        }

        public Builder onRightInputClick(Consumer<Player> inputRightClickListener) {
            this.inputRightClickListener = inputRightClickListener;
            return this;
        }

        @Deprecated
        public Builder onComplete(BiFunction<Player, String, List<ResponseAction>> completeFunction) {
            Validate.notNull(completeFunction, "Complete function cannot be null");
            this.completeFunction = completion -> completeFunction.apply(completion.player, completion.text);
            return this;
        }

        public Builder onComplete(Function<Completion, List<ResponseAction>> completeFunction) {
            Validate.notNull(completeFunction, "Complete function cannot be null");
            this.completeFunction = completeFunction;
            return this;
        }

        public Builder plugin(Plugin plugin) {
            Validate.notNull(plugin, "Plugin cannot be null");
            this.plugin = plugin;
            return this;
        }

        public Builder text(String text) {
            Validate.notNull(text, "Text cannot be null");
            this.itemText = text;
            return this;
        }

        public Builder title(String title) {
            Validate.notNull(title, "title cannot be null");
            this.title = title;
            return this;
        }

        @Deprecated
        public Builder item(ItemStack item) {
            return itemLeft(item);
        }

        public Builder itemLeft(ItemStack item) {
            Validate.notNull(item, "item cannot be null");
            this.itemLeft = item;
            return this;
        }

        public Builder itemRight(ItemStack item) {
            this.itemRight = item;
            return this;
        }

        public Builder itemOutput(ItemStack item) {
            this.itemOutput = item;
            return this;
        }

        public AnvilGUI open(Player player) {
            Validate.notNull(plugin, "Plugin cannot be null");
            Validate.notNull(completeFunction, "Complete function cannot be null");
            Validate.notNull(player, "Player cannot be null");

            if (itemText != null) {
                if (itemLeft == null) {
                    itemLeft = new ItemStack(Material.PAPER);
                }

                ItemMeta paperMeta = itemLeft.getItemMeta();
                paperMeta.setDisplayName(itemText);
                itemLeft.setItemMeta(paperMeta);
            }

            return new AnvilGUI(
                    plugin,
                    player,
                    title,
                    new ItemStack[] {itemLeft, itemRight, itemOutput},
                    preventClose,
                    interactableSlots,
                    closeListener,
                    inputLeftClickListener,
                    inputRightClickListener,
                    completeFunction);
        }
    }

    public interface ResponseAction extends BiConsumer<AnvilGUI, Player> {

        static ResponseAction replaceInputText(String text) {
            return (anvilgui, player) -> {
                final ItemStack outputSlotItem =
                        anvilgui.getInventory().getItem(Slot.OUTPUT).clone();
                final ItemMeta meta = outputSlotItem.getItemMeta();
                meta.setDisplayName(text);
                outputSlotItem.setItemMeta(meta);
                anvilgui.getInventory().setItem(Slot.INPUT_LEFT, outputSlotItem);
            };
        }

        static ResponseAction openInventory(Inventory otherInventory) {
            return (anvigui, player) -> player.openInventory(otherInventory);
        }

        static ResponseAction close() {
            return (anvilgui, player) -> anvilgui.closeInventory();
        }

        static ResponseAction run(Runnable runnable) {
            return (anvilgui, player) -> runnable.run();
        }
    }

    @Deprecated
    public static class Response {
        public static List<ResponseAction> close() {
            return Arrays.asList(ResponseAction.close());
        }

        public static List<ResponseAction> text(String text) {
            return Arrays.asList(ResponseAction.replaceInputText(text));
        }

        public static List<ResponseAction> openInventory(Inventory inventory) {
            return Arrays.asList(ResponseAction.openInventory(inventory));
        }
    }

    public static class Slot {

        private static final int[] values = new int[] {Slot.INPUT_LEFT, Slot.INPUT_RIGHT, Slot.OUTPUT};

        public static final int INPUT_LEFT = 0;
        public static final int INPUT_RIGHT = 1;
        public static final int OUTPUT = 2;

        public static int[] values() {
            return values;
        }
    }

    public static final class Completion {

        private final ItemStack leftItem, rightItem, outputItem;

        private final Player player;

        private final String text;

        public Completion(ItemStack leftItem, ItemStack rightItem, ItemStack outputItem, Player player, String text) {
            this.leftItem = leftItem;
            this.rightItem = rightItem;
            this.outputItem = outputItem;
            this.player = player;
            this.text = text;
        }

        public ItemStack getLeftItem() {
            return leftItem;
        }

        public ItemStack getRightItem() {
            return rightItem;
        }

        public ItemStack getOutputItem() {
            return outputItem;
        }

        public Player getPlayer() {
            return player;
        }

        public String getText() {
            return text;
        }
    }
}