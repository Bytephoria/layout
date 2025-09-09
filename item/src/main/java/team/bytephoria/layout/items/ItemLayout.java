package team.bytephoria.layout.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.builder.ItemBuilderBase;

import java.util.List;

public class ItemLayout extends ItemLayoutBase {

    public ItemLayout(
            final Component displayName,
            final List<Component> lore,
            final Material material,
            final int amount,
            final Integer customModelData
    ) {
        super(displayName, lore, material, amount, customModelData);
    }

    public static @NotNull ItemLayout display(final @NotNull Material material) {
        return new Builder(material).build();
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public static @NotNull Builder of(final @NotNull Material material) {
        return new Builder(material);
    }

    public static @NotNull Builder of(final @NotNull ItemStack itemStack) {
        return ItemBuilderBase.fromItemStack(new Builder(), itemStack);
    }

    public static class Builder extends ItemBuilderBase<Builder, ItemLayout> {

        public Builder() {
            // Keep default material
        }

        public Builder(final @NotNull Material material) {
            this.material = material;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public ItemLayout build() {
            return new ItemLayout(
                    this.displayName,
                    this.lore,
                    this.material,
                    this.amount,
                    this.customModelData
            );
        }
    }
}
