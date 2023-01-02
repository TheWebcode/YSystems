package io.github.thewebcode.ycore.menu.menuutility;

import org.bukkit.entity.Player;

public class TargetPlayerMenuUtility extends PlayerMenuUtility{
    private Player target;

    public TargetPlayerMenuUtility(Player owner, Player target) {
        super(owner);
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }
}
