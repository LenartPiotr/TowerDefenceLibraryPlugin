package lenart.piotr.plugins.towerdefencelib.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryManager implements Listener {

    private static InventoryManager instance;
    private final Map<UUID, InteractiveInventory> openInventories = new HashMap<>();

    private InventoryManager(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public static void init(JavaPlugin plugin) {
        if (instance == null) {
            instance = new InventoryManager(plugin);
        }
    }

    public static InventoryManager get() {
        if (instance == null) throw new IllegalStateException("InventoryManager not initialized!");
        return instance;
    }

    public void open(Player player, InteractiveInventory config) {
        Inventory inv = config.getInventoryBuilder().build();

        openInventories.put(player.getUniqueId(), config);
        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        InteractiveInventory config = openInventories.get(player.getUniqueId());
        if (config == null) return;

        if (event.getClickedInventory() == event.getView().getTopInventory()) {
            if (!config.isAllowRemove()) event.setCancelled(true);
        } else {
            if (!config.isAllowInsert()) event.setCancelled(true);
        }

        InventoryActionContext context = new InventoryActionContext(event, player);

        if (config.getGlobalAction() != null) config.getGlobalAction().accept(context);

        if (config.getSlotActions().containsKey(event.getRawSlot())) {
            config.getSlotActions().get(event.getRawSlot()).accept(context);
        }

        if (event.getCurrentItem() != null && config.getTypeActions().containsKey(event.getCurrentItem().getType())) {
            config.getTypeActions().get(event.getCurrentItem().getType()).accept(context);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        handleClose((Player) event.getPlayer(), true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        handleClose(event.getPlayer(), false);
    }

    private void handleClose(Player player, boolean isOnline) {
        InteractiveInventory config = openInventories.remove(player.getUniqueId());
        if (config != null && config.getCloseAction() != null) {
            config.getCloseAction().accept(new InventoryCloseContext(player, isOnline));
        }
    }
}