package team.bytephoria.layout.layouts.builder.sized;

import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.ItemLayoutBase;
import team.bytephoria.layout.layouts.base.InventoryHolderBase;
import team.bytephoria.layout.layouts.builder.AbstractLayoutBuilder;

import java.util.function.Consumer;

public abstract class SizedInventoryBuilder<B extends AbstractLayoutBuilder<B, O>, O extends InventoryHolderBase>
        extends AbstractLayoutBuilder<B, O> {

    private static final byte MAX_ROWS = 6;
    private static final byte SLOTS_PER_ROW = 9;

    protected int size;

    protected SizedInventoryBuilder() {
        this.size = 1;
    }

    public @NotNull B patterned(final @NotNull Consumer<PatternLayoutBuilder> patternBuilderConsumer) {
        final PatternLayoutBuilder patternLayoutBuilder = PatternLayoutBuilder.builder();
        patternBuilderConsumer.accept(patternLayoutBuilder);

        this.size = patternLayoutBuilder.size();
        this.itemLayouts.putAll(
                patternLayoutBuilder.build()
        );

        return this.self();
    }

    public B size(final int size) {
        this.size = Math.max(1, Math.min(size, MAX_ROWS)) * SLOTS_PER_ROW;
        return this.self();
    }

    public B fill(final @NotNull ItemLayoutBase itemLayoutBase, final int @NotNull ... slots) {
        for (final int slot : slots) {
            this.item(slot, itemLayoutBase);
        }

        return this.self();
    }

    @Override
    public B fillAll(final @NotNull ItemLayoutBase itemLayoutBase) {
        for (int slot = 0; slot < this.size; slot++) {
            this.item(slot, itemLayoutBase);
        }

        return this.self();
    }

    public B column(final int column, final @NotNull ItemLayoutBase itemLayoutBase) {
        for (int currentSlot = column; currentSlot < this.size; currentSlot += SLOTS_PER_ROW) {
            this.item(currentSlot, itemLayoutBase);
        }

        return this.self();
    }

    public B border(final @NotNull ItemLayoutBase itemLayoutBase) {
        final int rows = this.size / SLOTS_PER_ROW;

        this.row(0, itemLayoutBase);
        this.row(rows - 1, itemLayoutBase);

        for (int row = 1; row < rows - 1; row++) {
            this.column(row * SLOTS_PER_ROW, itemLayoutBase);
            this.column(row * SLOTS_PER_ROW + (SLOTS_PER_ROW - 1), itemLayoutBase);
        }

        return this.self();
    }

    public B row(final int row, final @NotNull ItemLayoutBase itemLayoutBase) {
        final int slotToStart = row * SLOTS_PER_ROW;
        final int slotToEnd = Math.min(slotToStart + 8, this.size - 1);
        for (int slot = slotToStart; slot <= slotToEnd; slot++) {
            this.item(slot, itemLayoutBase);
        }

        return this.self();
    }

}
