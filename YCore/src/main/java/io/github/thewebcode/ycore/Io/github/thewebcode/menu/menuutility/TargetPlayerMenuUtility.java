package ycore.io.github.thewebcode.ycore.Io.github.thewebcode.menu.menuutility;

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
