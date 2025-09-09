package team.bytephoria.layout.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.common.Builder;

import java.util.List;

public abstract class ItemLayoutBase implements Builder<ItemStack> {

    protected final Component displayName;
    protected final List<Component> lore;
    protected final Material material;
    protected final int amount;
    protected final Integer customModelData;

    public ItemLayoutBase(
            final Component displayName,
            final List<Component> lore,
            final Material material,
            final int amount,
            final Integer customModelData
    ) {
        this.displayName = displayName;
        this.lore = lore;
        this.material = material;
        this.amount = Math.min(Math.max(1, amount), 64);
        this.customModelData = customModelData;
    }

    public Component displayName() {
        return this.displayName;
    }

    public List<Component> lore() {
        return this.lore;
    }

    public Material material() {
        return this.material;
    }

    public int amount() {
        return this.amount;
    }

    public Integer customModelData() {
        return this.customModelData;
    }

    @Override
    public final @NotNull ItemStack build() {
        final ItemStack itemStack = new ItemStack(this.material, this.amount);
        final ItemMeta itemMeta = itemStack.getItemMeta();

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