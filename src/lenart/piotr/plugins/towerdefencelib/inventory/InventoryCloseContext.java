package lenart.piotr.plugins.towerdefencelib.inventory;

import org.bukkit.entity.Player;

public record InventoryCloseContext(Player player, boolean isOnline) { }