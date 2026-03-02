package lenart.piotr.plugins.towerdefencelib.inventory;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InventoryBuilder {
    private final Map<Character, ItemStack> palette = new HashMap<>();
    private String[] pattern = null;
    private final int rows;
    private String title;
    private final Map<Integer, ItemStack> manualSlots = new HashMap<>();

    private InventoryBuilder(int rows, String title) {
        this.rows = Math.min(6, Math.max(1, rows));
        this.title = title;
    }

    public static InventoryBuilder chest(int rows) {
        return new InventoryBuilder(rows, "Inventory");
    }

    public static InventoryBuilder smallChest() {
        return new InventoryBuilder(3, "Small Chest");
    }

    public static InventoryBuilder largeChest() {
        return new InventoryBuilder(6, "Large Chest");
    }

    public InventoryBuilder title(String title) {
        this.title = title;
        return this;
    }

    public InventoryBuilder item(int slot, ItemStack item) {
        manualSlots.put(slot, item);
        return this;
    }

    public InventoryBuilder fill(ItemStack item) {
        for (int i = 0; i < rows * 9; i++) {
            if (!manualSlots.containsKey(i)) {
                manualSlots.put(i, item);
            }
        }
        return this;
    }

    public InventoryBuilder border(ItemStack item) {
        int size = rows * 9;
        for (int i = 0; i < size; i++) {
            if (i < 9 || i >= size - 9 || i % 9 == 0 || i % 9 == 8) {
                manualSlots.put(i, item);
            }
        }
        return this;
    }

    public InventoryBuilder register(char symbol, ItemStack item) {
        palette.put(symbol, item);
        return this;
    }

    public InventoryBuilder pattern(String... rows) {
        this.pattern = rows;
        return this;
    }

    public Inventory build() {
        Inventory inv = Bukkit.createInventory(null, rows * 9, title);

        if (pattern != null) {
            for (int r = 0; r < Math.min(pattern.length, rows); r++) {
                String line = pattern[r];
                for (int c = 0; c < Math.min(line.length(), 9); c++) {
                    char symbol = line.charAt(c);
                    if (palette.containsKey(symbol)) {
                        inv.setItem(r * 9 + c, palette.get(symbol));
                    }
                }
            }
        }

        manualSlots.forEach(inv::setItem);

        return inv;
    }
}
