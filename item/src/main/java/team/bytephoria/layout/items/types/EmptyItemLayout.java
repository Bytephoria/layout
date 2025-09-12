package team.bytephoria.layout.items.types;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.base.Item;
import team.bytephoria.layout.items.types.builder.LayoutItemBuilder;

public class EmptyItemLayout extends ItemLayout {

    public EmptyItemLayout(final @NotNull Item item) {
        super(item);
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull Builder builder() {
        return new Builder();
    }

    public static @NotNull EmptyItemLayout display(final @NotNull Item item) {
        return new EmptyItemLayout(item);
    }

    public static class Builder extends LayoutItemBuilder<Builder, EmptyItemLayout> {

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public EmptyItemLayout build() {
            return new EmptyItemLayout(
                    this.item
            );
        }
    }

}
