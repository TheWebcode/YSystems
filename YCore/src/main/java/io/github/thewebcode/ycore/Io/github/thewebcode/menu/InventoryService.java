package ycore.io.github.thewebcode.ycore.Io.github.thewebcode.menu;

import org.bukkit.entity.Player;
import ycore.io.github.thewebcode.ycore.Io.github.thewebcode.menu.menuutility.PlayerMenuUtility;

import java.util.HashMap;

public class InventoryService {
    private HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap;

    public InventoryService() {
        this.playerMenuUtilityMap = new HashMap<>();
    }

    public PlayerMenuUtility get(Player player){
        if(playerMenuUtilityMap.containsKey(player)) {
            return playerMenuUtilityMap.get(player);
        }else{
            PlayerMenuUtility utility = new PlayerMenuUtility(player);
            playerMenuUtilityMap.put(player, utility);

            return utility;
        }
    }
}
