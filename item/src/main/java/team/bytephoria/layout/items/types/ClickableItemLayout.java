package team.bytephoria.layout.items.types;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.ItemClickType;
import team.bytephoria.layout.items.Executable;
import team.bytephoria.layout.items.base.Item;
import team.bytephoria.layout.items.context.InventoryClickContext;
import team.bytephoria.layout.items.types.builder.LayoutItemBuilder;

import java.util.EnumMap;
import java.util.function.Consumer;

public class ClickableItemLayout extends ItemLayout implements Executable {

    protected final EnumMap<ItemClickType, Consumer<InventoryClickContext>> clickActions;

    public ClickableItemLayout(
            final @NotNull Item item,
            final @NotNull EnumMap<ItemClickType, Consumer<InventoryClickContext>> clickActions
    ) {
        super(item);
        this.clickActions = clickActions;
    }

    @Contract(" -> new")
    public static @NotNull ClickableItemLayout.ItemBuilder builder() {
        return new ItemBuilder();
    }

    @Override
    public void execute(final @NotNull InventoryClickContext context) {
        final ItemClickType itemClickType = ItemClickType.fromBukkitType(context.clickEvent().getClick());
        final Consumer<InventoryClickContext> consumer = this.clickActions.get(itemClickType);
        if (consumer != null) {
            consumer.accept(context);
        }
    }

    public static class ItemBuilder extends LayoutItemBuilder<ItemBuilder, ClickableItemLayout> {

        protected final EnumMap<ItemClickType, Consumer<InventoryClickContext>> clickActions = new EnumMap<>(ItemClickType.class);

        public ItemBuilder onMiddleClick(final @NotNull Consumer<InventoryClickContext> clickContextConsumer) {
            return this.onClick(ItemClickType.MIDDLE, clickContextConsumer);
        }

        public ItemBuilder onDropClick(final @NotNull Consumer<InventoryClickContext> clickContextConsumer) {
            return this.onClick(ItemClickType.DROP, clickContextConsumer);
        }

        public ItemBuilder onLeftClick(final @NotNull Consumer<InventoryClickContext> clickContextConsumer) {
            return this.onClick(ItemClickType.LEFT, clickContextConsumer);
        }

        public ItemBuilder onRightClick(final @NotNull Consumer<InventoryClickContext> clickContextConsumer) {
            return this.onClick(ItemClickType.RIGHT, clickContextConsumer);
        }

        public ItemBuilder onClick(final @NotNull ItemClickType itemClickType, @NotNull Consumer<InventoryClickContext> consumer) {
            this.clickActions.put(itemClickType, consumer);
            return this;
        }

        @Override
        protected ItemBuilder self() {
            return this;
        }

        @Override
        public ClickableItemLayout build() {
            return new ClickableItemLayout(
                    this.item,
                    this.clickActions
            );
        }
    }

}
