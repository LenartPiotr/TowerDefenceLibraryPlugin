package lenart.piotr.plugins.towerdefencelib.inventory;

import lenart.piotr.plugins.towerdefencelib.utils.ColorUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ItemBuilder {

    // --- Base builder tools ---

    private final ItemStack item;
    private final ItemMeta meta;
    private final char featureColorCharacter;

    private ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
        this.featureColorCharacter = '&';
    }
    private ItemBuilder(ItemStack item) {
        this.item = item.clone();
        this.meta = this.item.getItemMeta();
        this.featureColorCharacter = '&';
    }

    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }
    public static ItemBuilder of(String materialName) {
        Material mat = Material.matchMaterial(materialName.toUpperCase());
        if (mat == null) mat = Material.STONE;
        return new ItemBuilder(mat);
    }
    public static ItemBuilder from(ItemStack item) {
        return new ItemBuilder(item);
    }

    @Override
    public ItemBuilder clone() {
        return new ItemBuilder(this.build());
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack b() {
        return build();
    }

    // --- Base setup and metadata ---

    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }
    public ItemBuilder name(String name) {
        meta.setDisplayName(ColorUtils.fix(name, featureColorCharacter));
        return this;
    }
    public ItemBuilder lore(String... lines) {
        List<String> lore = new ArrayList<>();
        Collections.addAll(lore, lines);
        meta.setLore(ColorUtils.fix(lore, featureColorCharacter));
        return this;
    }
    public ItemBuilder addLore(String line) {
        List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        lore.add(line);
        meta.setLore(ColorUtils.fix(lore, featureColorCharacter));
        return this;
    }
    public ItemBuilder modelData(Integer data) {
        meta.setCustomModelData(data);
        return this;
    }

    public <T extends ItemMeta> ItemBuilder editMeta(Class<T> clazz, Consumer<T> consumer) {
        if (clazz.isInstance(meta)) {
            consumer.accept(clazz.cast(meta));
        }
        return this;
    }

    // --- Enchantment ---

    public ItemBuilder enchant(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, false);
        return this;
    }

    public ItemBuilder enchant(String name, int level) {
        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(name.toLowerCase()));
        if (enchantment == null) {
            enchantment = Enchantment.getByName(name.toUpperCase());
        }

        if (enchantment != null) {
            enchant(enchantment, level);
        }
        return this;
    }

    public ItemBuilder enchantUnsafe(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder glow() {
        meta.addEnchant(Enchantment.LURE, 1, true);
        return this;
    }

    public ItemBuilder flag(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    public ItemBuilder hideAll() {
        meta.addItemFlags(ItemFlag.values());
        return this;
    }

    // --- Attributes and modifiers

    public ItemBuilder unbreakable() {
        meta.setUnbreakable(true);
        return this;
    }

    public ItemBuilder attribute(Attribute attribute, AttributeModifier modifier) {
        meta.addAttributeModifier(attribute, modifier);
        return this;
    }
}
