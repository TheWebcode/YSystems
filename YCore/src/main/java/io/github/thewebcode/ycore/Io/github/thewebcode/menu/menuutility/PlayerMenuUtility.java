package ycore.io.github.thewebcode.ycore.Io.github.thewebcode.menu.menuutility;

import org.bukkit.entity.Player;

public class PlayerMenuUtility {
    private Player owner;

    public PlayerMenuUtility(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }
}
