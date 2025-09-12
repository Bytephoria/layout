package team.bytephoria.layout.layouts.types;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.types.ItemLayout;
import team.bytephoria.layout.layouts.base.LayoutInventoryBase;
import team.bytephoria.layout.layouts.behavior.LayoutBehavior;
import team.bytephoria.layout.layouts.builder.FixedInventoryBuilder;

public final class LayoutFixedInventory extends LayoutInventoryBase {

    public LayoutFixedInventory(
            final @NotNull LayoutBehavior layoutBehavior,
            final @NotNull Int2ObjectArrayMap<ItemLayout> itemLayouts,
            final @NotNull InventoryType inventoryType,
            final @NotNull Component title
    ) {
        super(layoutBehavior, itemLayouts, inventoryType, title);
    }

    @Contract(" -> new")
    public static @NotNull Builder builder() {
        return new Builder();
    }

    public final static class Builder extends FixedInventoryBuilder<Builder, LayoutFixedInventory> {

        @Override
        protected Builder self() {
            return this;
        }

        @Contract(" -> new")
        @Override
        public @NotNull LayoutFixedInventory build() {
            return new LayoutFixedInventory(
                    this.layoutBehavior,
                    this.itemLayouts,
                    this.type,
                    this.title
            );
        }
    }
}
