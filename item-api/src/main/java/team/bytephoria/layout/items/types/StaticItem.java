package team.bytephoria.layout.items.types;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.base.Item;
import team.bytephoria.layout.items.types.builder.ItemLayoutBuilder;

/**
 * A purely visual slot occupant. It renders its {@link Item} and ignores every click.
 */
public class StaticItem extends ItemLayout {

    public StaticItem(final @NotNull Item item) {
        super(item);
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull Builder builder() {
        return new Builder();
    }

    /** Shortcut for a display-only slot when no further configuration is needed. */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull StaticItem display(final @NotNull Item item) {
        return new StaticItem(item);
    }

    public static class Builder extends ItemLayoutBuilder<Builder, StaticItem> {

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public StaticItem build() {
            return new StaticItem(this.item);
        }
    }

}
