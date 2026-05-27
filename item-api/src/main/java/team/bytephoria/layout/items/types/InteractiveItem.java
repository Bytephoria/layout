package team.bytephoria.layout.items.types;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.Executable;
import team.bytephoria.layout.items.ItemClickType;
import team.bytephoria.layout.items.base.Item;
import team.bytephoria.layout.items.context.ClickContext;
import team.bytephoria.layout.items.types.builder.ItemLayoutBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.function.Consumer;

/**
 * A clickable slot occupant. It renders its {@link Item} and dispatches each
 * {@link ItemClickType} to the handler registered for it, if any.
 */
public class InteractiveItem extends ItemLayout implements Executable {

    protected final EnumMap<ItemClickType, Consumer<ClickContext>> clickActions;

    public InteractiveItem(
            final @NotNull Item item,
            final @NotNull EnumMap<ItemClickType, Consumer<ClickContext>> clickActions
    ) {
        super(item);
        this.clickActions = clickActions;
    }

    @Contract(" -> new")
    public static @NotNull InteractiveItem.Builder builder() {
        return new Builder();
    }

    @Override
    public void execute(final @NotNull ClickContext context) {
        final Consumer<ClickContext> consumer = this.clickActions.get(context.clickType());
        if (consumer != null) {
            consumer.accept(context);
        }
    }

    public static class Builder extends ItemLayoutBuilder<Builder, InteractiveItem> {

        protected final EnumMap<ItemClickType, Consumer<ClickContext>> clickActions = new EnumMap<>(ItemClickType.class);

        public Builder leftClick(final @NotNull Consumer<ClickContext> clickContextConsumer) {
            return this.click(ItemClickType.LEFT, clickContextConsumer);
        }

        public Builder rightClick(final @NotNull Consumer<ClickContext> clickContextConsumer) {
            return this.click(ItemClickType.RIGHT, clickContextConsumer);
        }

        public Builder middleClick(final @NotNull Consumer<ClickContext> clickContextConsumer) {
            return this.click(ItemClickType.MIDDLE, clickContextConsumer);
        }

        public Builder doubleClick(final @NotNull Consumer<ClickContext> clickContextConsumer) {
            return this.click(ItemClickType.DOUBLE_CLICK, clickContextConsumer);
        }

        public Builder shiftLeftClick(final @NotNull Consumer<ClickContext> clickContextConsumer) {
            return this.click(ItemClickType.SHIFT_LEFT, clickContextConsumer);
        }

        public Builder shiftRightClick(final @NotNull Consumer<ClickContext> clickContextConsumer) {
            return this.click(ItemClickType.SHIFT_RIGHT, clickContextConsumer);
        }

        public Builder numberKeyClick(final @NotNull Consumer<ClickContext> clickContextConsumer) {
            return this.click(ItemClickType.NUMBER_KEY, clickContextConsumer);
        }

        public Builder dropClick(final @NotNull Consumer<ClickContext> clickContextConsumer) {
            return this.click(ItemClickType.DROP, clickContextConsumer);
        }

        public Builder controlDropClick(final @NotNull Consumer<ClickContext> clickContextConsumer) {
            return this.click(ItemClickType.CONTROL_DROP, clickContextConsumer);
        }

        public Builder swapOffhandClick(final @NotNull Consumer<ClickContext> clickContextConsumer) {
            return this.click(ItemClickType.SWAP_OFFHAND, clickContextConsumer);
        }

        /**
         * Registers a fallback handler for every click type not yet bound. Call this <em>last</em>:
         * it only fills the click types that have no handler at the moment of invocation.
         */
        public Builder otherwiseClick(final @NotNull Consumer<ClickContext> clickContextConsumer) {
            final Collection<ItemClickType> registered = this.clickActions.keySet();
            final ItemClickType[] clickTypes = Arrays.stream(ItemClickType.values())
                    .filter(itemClickType -> !registered.contains(itemClickType))
                    .toArray(ItemClickType[]::new);

            return this.clicks(clickTypes, clickContextConsumer);
        }

        public Builder clicks(final @NotNull Consumer<ClickContext> consumer, final @NotNull ItemClickType @NotNull ... itemClickTypes) {
            return this.clicks(itemClickTypes, consumer);
        }

        private Builder clicks(final @NotNull ItemClickType @NotNull [] itemClickTypes, final @NotNull Consumer<ClickContext> clickContextConsumer) {
            for (final ItemClickType itemClickType : itemClickTypes) {
                this.click(itemClickType, clickContextConsumer);
            }

            return this.self();
        }

        public Builder click(final @NotNull ItemClickType itemClickType, @NotNull Consumer<ClickContext> consumer) {
            this.clickActions.put(itemClickType, consumer);
            return this.self();
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public InteractiveItem build() {
            return new InteractiveItem(
                    this.item,
                    this.clickActions
            );
        }
    }

}
