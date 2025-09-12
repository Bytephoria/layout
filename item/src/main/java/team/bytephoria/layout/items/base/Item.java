package team.bytephoria.layout.items.base;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import team.bytephoria.layout.common.Builder;

import java.util.List;

public abstract class Item implements Builder<ItemStack> {

    protected final Component displayName;
    protected final List<Component> lore;
    protected final int amount;
    protected final Integer customModelData;

    public Item(
            final Component displayName,
            final List<Component> lore,
            final int amount,
            final Integer customModelData
    ) {
        this.displayName = displayName;
        this.lore = lore;
        this.amount = Math.min(Math.max(1, amount), 64);
        this.customModelData = customModelData;
    }

    public Item() {
        this(null, null, 1, null);
    }

    public abstract Material material();

    public Component displayName() {
        return this.displayName;
    }

    public List<Component> lore() {
        return this.lore;
    }

    public int amount() {
        return this.amount;
    }

    public Integer customModelData() {
        return this.customModelData;
    }

}