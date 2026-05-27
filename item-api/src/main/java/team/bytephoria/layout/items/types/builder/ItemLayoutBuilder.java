package team.bytephoria.layout.items.types.builder;

import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.common.Builder;
import team.bytephoria.layout.items.base.Item;
import team.bytephoria.layout.items.types.ItemLayout;

/**
 * Base builder for {@link ItemLayout} subtypes. Holds the visual {@link Item} shown in the slot;
 * subtypes add their own behavior (e.g. click handlers).
 *
 * @param <B> the concrete builder type, for fluent self-returns
 * @param <T> the built {@link ItemLayout} subtype
 */
public abstract class ItemLayoutBuilder<B extends ItemLayoutBuilder<B, T>, T extends ItemLayout> implements Builder<T> {

    protected Item item;

    protected abstract B self();

    /** Sets the visual item rendered in the slot. */
    public B item(final @NotNull Item item) {
        this.item = item;
        return this.self();
    }
}
