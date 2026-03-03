package lenart.piotr.plugins.towerdefencelib.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;

import java.util.HashMap;
import java.util.Map;

public class InteractiveInventory {

    private final InventoryBuilder builder;

    private boolean allowInsert = false;
    private boolean allowRemove = false;

    private final Map<Integer, Consumer<InventoryActionContext>> slotActions = new HashMap<>();
    private final Map<Material, Consumer<InventoryActionContext>> typeActions = new HashMap<>();
    private Consumer<InventoryActionContext> globalAction = null;
    private Consumer<InventoryCloseContext> closeAction = null;

    private InteractiveInventory(InventoryBuilder builder) {
        this.builder = builder;
    }

    public static InteractiveInventory from(InventoryBuilder builder) {
        return new InteractiveInventory(builder);
    }

    public static InteractiveInventory chest(int rows) {
        return new InteractiveInventory(InventoryBuilder.chest(rows));
    }

    // --- Config ---

    public InteractiveInventory allowInsert(boolean value) { this.allowInsert = value; return this; }
    public InteractiveInventory allowRemove(boolean value) { this.allowRemove = value; return this; }

    public Inventory buildInternal() {
        return builder.build();
    }

    // --- Register callbacks ---

    public InteractiveInventory item(int slot, ItemBuilder itemBuilder, Consumer<InventoryActionContext> action) {
        builder.item(slot, itemBuilder.build());
        slotActions.put(slot, action);
        return this;
    }

    public InteractiveInventory item(int slot, ItemStack item, Consumer<InventoryActionContext> action) {
        builder.item(slot, item);
        slotActions.put(slot, action);
        return this;
    }

    public InteractiveInventory onClick(int slot, Consumer<InventoryActionContext> action) {
        slotActions.put(slot, action);
        return this;
    }

    public InteractiveInventory onClick(Material material, Consumer<InventoryActionContext> action) {
        typeActions.put(material, action);
        return this;
    }

    public InteractiveInventory onGlobalClick(Consumer<InventoryActionContext> action) {
        this.globalAction = action;
        return this;
    }

    public InteractiveInventory onClose(Consumer<InventoryCloseContext> action) {
        this.closeAction = action;
        return this;
    }

    // --- Getters ---

    public InventoryBuilder getInventoryBuilder() { return builder; }
    public Map<Integer, Consumer<InventoryActionContext>> getSlotActions() { return slotActions; }
    public Map<Material, Consumer<InventoryActionContext>> getTypeActions() { return typeActions; }
    public Consumer<InventoryActionContext> getGlobalAction() { return globalAction; }
    public boolean isAllowInsert() { return allowInsert; }
    public boolean isAllowRemove() { return allowRemove; }
    public Consumer<InventoryCloseContext> getCloseAction() { return closeAction; }
}