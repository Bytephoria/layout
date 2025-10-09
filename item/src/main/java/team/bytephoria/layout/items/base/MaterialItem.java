package team.bytephoria.layout.items.base;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.base.builder.MaterialItemBuilder;

import java.util.Collections;
import java.util.List;

public class MaterialItem extends Item {

    private final Material material;

    public MaterialItem(
            final Component displayName,
            final List<Component> lore,
            final Material material,
            final int amount,
            final Integer customModelData
    ) {
        super(displayName, lore, amount, customModelData);
        this.material = material;
    }

    public MaterialItem(final Component displayName, final Material material, final int amount) {
        this(displayName, Collections.emptyList(), material, amount, null);
    }

    public MaterialItem(final Component displayName, final Material material) {
        this(displayName, Collections.emptyList(), material, 1, null);
    }

    public MaterialItem(final @NotNull Material material) {
        this.material = material;
    }

    public MaterialItem() {
        this.material = Material.STONE;
    }

    @Contract(" -> new")
    public static @NotNull MaterialItemBuilder builder() {
        return new MaterialItemBuilder();
    }

    @Override
    public Material material() {
        return this.material;
    }

    @Override
    public ItemStack to() {
        final ItemStack itemStack = new ItemStack(this.material, 1);
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) {
            return itemStack;
        }

        if (this.displayName != null) {
            itemMeta.displayName(this.displayName);
        }

        if (this.lore != null) {
            itemMeta.lore(this.lore);
        }

        itemMeta.setCustomModelData(this.customModelData);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
