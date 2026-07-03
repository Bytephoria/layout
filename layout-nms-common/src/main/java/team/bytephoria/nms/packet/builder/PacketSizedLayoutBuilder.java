package team.bytephoria.nms.packet.builder;

import team.bytephoria.layout.items.types.ItemLayout;

import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.layouts.builder.AbstractLayoutBuilder;
import team.bytephoria.nms.packet.types.PacketSizedLayout;

public final class PacketSizedLayoutBuilder extends AbstractLayoutBuilder<PacketSizedLayoutBuilder, PacketSizedLayout> {

    private static final int MAX_ROWS = 6;
    private static final int SLOTS_PER_ROW = 9;

    private int rows = 3;

    public PacketSizedLayoutBuilder size(final int rows) {
        this.rows = Math.clamp(rows, 1, MAX_ROWS);
        return this.self();
    }

    @Override
    public PacketSizedLayoutBuilder fill(final @NotNull ItemLayout itemLayout) {
        final int totalSlots = this.rows * SLOTS_PER_ROW;
        for (int slot = 0; slot < totalSlots; slot++) {
            this.itemLayouts.put(slot, itemLayout);
        }
        return this.self();
    }

    public PacketSizedLayoutBuilder column(final int column, final @NotNull ItemLayout itemLayout) {
        for (int row = 0; row < this.rows; row++) {
            this.itemLayouts.put(row * SLOTS_PER_ROW + column, itemLayout);
        }

        return this.self();
    }

    public PacketSizedLayoutBuilder row(final int row, final @NotNull ItemLayout itemLayout) {
        final int start = row * SLOTS_PER_ROW;
        for (int slot = start; slot < start + SLOTS_PER_ROW; slot++) {
            this.itemLayouts.put(slot, itemLayout);
        }
        return this.self();
    }

    public PacketSizedLayoutBuilder border(final @NotNull ItemLayout itemLayout) {
        final int totalSlots = this.rows * SLOTS_PER_ROW;
        for (int slot = 0; slot < totalSlots; slot++) {
            final int row = slot / SLOTS_PER_ROW;
            final int col = slot % SLOTS_PER_ROW;
            if (row == 0 || row == this.rows - 1 || col == 0 || col == SLOTS_PER_ROW - 1) {
                this.itemLayouts.put(slot, itemLayout);
            }
        }
        return this.self();
    }

    @Override
    protected PacketSizedLayoutBuilder self() {
        return this;
    }

    @Override
    public @NotNull PacketSizedLayout build() {
        return new PacketSizedLayout(this.behavior, this.itemLayouts, this.title, this.rows);
    }
}
