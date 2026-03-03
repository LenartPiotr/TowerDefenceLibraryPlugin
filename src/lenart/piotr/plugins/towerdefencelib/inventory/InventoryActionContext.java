package lenart.piotr.plugins.towerdefencelib.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public record InventoryActionContext(InventoryClickEvent event, Player player) {

    public void close() {
        player.closeInventory();
    }

    public void updateItem(ItemStack newItem) {
        event.setCurrentItem(newItem);
    }

    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    public void openOther(InteractiveInventory other) {
        // InventoryManager.open(player, other);
    }
}