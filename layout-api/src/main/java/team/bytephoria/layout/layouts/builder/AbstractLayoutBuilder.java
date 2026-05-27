package team.bytephoria.layout.layouts.builder;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.common.Builder;
import team.bytephoria.layout.items.types.ItemLayout;
import team.bytephoria.layout.layouts.behavior.Behavior;
import team.bytephoria.layout.layouts.behavior.BehaviorBuilder;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractLayoutBuilder<B extends AbstractLayoutBuilder<B, O>, O> implements Builder<O> {

    protected Component title = Component.empty();
    protected Int2ObjectArrayMap<ItemLayout> itemLayouts = new Int2ObjectArrayMap<>();
    protected Behavior behavior = Behavior.defaults();

    protected abstract B self();

    public B title(final @Nullable Component title) {
        this.title = (title != null) ? title : Component.empty();
        return this.self();
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

    public abstract B fill(final @NotNull ItemLayout itemLayout);

    public B behavior(final @NotNull Consumer<BehaviorBuilder> layoutBehaviorBuilderConsumer) {
        final BehaviorBuilder behaviorBuilder = Behavior.builder();
        layoutBehaviorBuilderConsumer.accept(behaviorBuilder);
        return this.behavior(behaviorBuilder.build());
    }

    public B editBehavior(final @NotNull Consumer<BehaviorBuilder> layoutBehaviorBuilderConsumer) {
        final BehaviorBuilder behaviorBuilder = Behavior.builder()
                .closeOnClick(this.behavior.closeOnClick())
                .cancelLayoutClicks(this.behavior.cancelLayoutClicks())
                .allowPlayerInventoryClicks(this.behavior.allowPlayerInventoryClicks())
                .ignoreEmptySlots(this.behavior.ignoreEmptySlots())
                .onOpen(this.behavior.onOpen())
                .onClose(this.behavior.onClose())
                .onClick(this.behavior.onClick());

        layoutBehaviorBuilderConsumer.accept(behaviorBuilder);
        return this.behavior(behaviorBuilder.build());
    }

    public B behavior(final @NotNull Supplier<Behavior> behaviorSupplier) {
        return this.behavior(behaviorSupplier.get());
    }

    public B behavior(final @NotNull Behavior behavior) {
        this.behavior = behavior;
        return this.self();
    }
}
