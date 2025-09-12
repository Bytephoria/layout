package team.bytephoria.layout.layouts.builder;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.common.Builder;
import team.bytephoria.layout.items.types.ItemLayout;
import team.bytephoria.layout.layouts.base.InventoryHolderBase;
import team.bytephoria.layout.layouts.behavior.LayoutBehavior;
import team.bytephoria.layout.layouts.behavior.LayoutBehaviorBuilder;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractLayoutBuilder<B extends AbstractLayoutBuilder<B, O>, O
        extends InventoryHolderBase> implements Builder<O> {

    protected Component title;

    protected Int2ObjectArrayMap<ItemLayout> itemLayouts = new Int2ObjectArrayMap<>();
    protected LayoutBehavior layoutBehavior = LayoutBehavior.builder()
            .build();

    protected abstract B self();

    public B title(final @Nullable Component title) {
        this.title = (title != null) ? title : Component.empty();
        return self();
    }

    public B item(final int slot, final @NotNull ItemLayout itemLayout) {
        this.itemLayouts.put(slot, itemLayout);
        return this.self();
    }

    public B range(final int from, final int to, final @NotNull ItemLayout itemLayout) {
        for (int index = from; index < to; index++) {
            this.itemLayouts.put(index, itemLayout);
        }

        return this.self();
    }

    public abstract B fillAll(final @NotNull ItemLayout itemLayoutBase);

    public B behavior(final @NotNull Consumer<LayoutBehaviorBuilder> layoutBehaviorBuilderConsumer) {
        final LayoutBehaviorBuilder behaviorBuilder = LayoutBehavior.builder();
        layoutBehaviorBuilderConsumer.accept(behaviorBuilder);
        this.layoutBehavior = behaviorBuilder.build();
        return this.self();
    }

    public B editBehavior(final @NotNull Consumer<LayoutBehaviorBuilder> layoutBehaviorBuilderConsumer) {
        final LayoutBehaviorBuilder layoutBehaviorBuilder = LayoutBehavior.builder()
                .closeOnClick(this.layoutBehavior.closeOnClick())
                .cancelLayoutClicks(this.layoutBehavior.cancelLayoutClicks())
                .allowPlayerInventoryClicks(this.layoutBehavior.allowPlayerInventoryClicks())
                .ignoreEmptySlots(this.layoutBehavior.ignoreEmptySlots())
                .onOpen(this.layoutBehavior.onOpen())
                .onClose(this.layoutBehavior.onClose())
                .onClick(this.layoutBehavior.onClick());

        layoutBehaviorBuilderConsumer.accept(layoutBehaviorBuilder);
        this.layoutBehavior = layoutBehaviorBuilder.build();
        return this.self();
    }

    public B behavior(final @NotNull Supplier<LayoutBehavior> layoutBehaviorBuilderSupplier) {
        this.layoutBehavior = layoutBehaviorBuilderSupplier.get();
        return self();
    }
}
