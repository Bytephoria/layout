package team.bytephoria.layout.items.types.builder;

import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.common.Builder;
import team.bytephoria.layout.items.base.Item;

public abstract class LayoutItemBuilder<B extends LayoutItemBuilder<B, T>, T> implements Builder<T> {

    protected Item item;

    protected abstract B self();

    public B item(final @NotNull Item item) {
        this.item = item;
        return this.self();
    }
}
