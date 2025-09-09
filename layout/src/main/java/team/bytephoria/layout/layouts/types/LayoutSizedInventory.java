package team.bytephoria.layout.layouts.types;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.ItemLayoutBase;
import team.bytephoria.layout.layouts.builder.sized.SizedInventoryBuilder;
import team.bytephoria.layout.layouts.editor.LayoutSizedEditor;
import team.bytephoria.layout.layouts.base.LayoutInventoryBase;
import team.bytephoria.layout.layouts.behavior.LayoutBehavior;

public final class LayoutSizedInventory extends LayoutInventoryBase
        implements LayoutSizedEditor {

    private static final byte SLOTS_PER_ROW = 9;

    public LayoutSizedInventory(
            final @NotNull LayoutBehavior layoutBehavior,
            final @NotNull Int2ObjectArrayMap<ItemLayoutBase> itemLayouts,
            final @NotNull Component title,
            final int size
    ) {
        super(layoutBehavior, itemLayouts, title, size);
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    @Override
    public void column(final int column, final @NotNull ItemLayoutBase itemLayout) {
        for (int currentSlot = column; currentSlot < this.slots(); currentSlot += SLOTS_PER_ROW) {
            this.item(currentSlot, itemLayout);
        }
    }

    @Override
    public void row(final int row, final @NotNull ItemLayoutBase itemLayout) {
        final int slotToStart = row * SLOTS_PER_ROW;
        final int slotToEnd = slotToStart + 8;
        for (int slot = slotToStart; slot <= slotToEnd; slot++) {
            this.item(slot, itemLayout);
        }
    }

    @Override
    public void border(final @NotNull ItemLayoutBase itemLayout) {
        final int rows = this.slots() / SLOTS_PER_ROW;

        this.row(0, itemLayout);
        this.row(rows - 1, itemLayout);

        for (int row = 1; row < rows - 1; row++) {
            this.column(row * SLOTS_PER_ROW, itemLayout);
            this.column(row * SLOTS_PER_ROW + (SLOTS_PER_ROW - 1), itemLayout);
        }
    }

    public final static class Builder extends SizedInventoryBuilder<Builder, LayoutSizedInventory> {

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public @NotNull LayoutSizedInventory build() {
            return new LayoutSizedInventory(
                    this.layoutBehavior,
                    this.itemLayouts,
                    this.title,
                    this.size
            );
        }
    }


}
