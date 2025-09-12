package team.bytephoria.layout.items.types;

import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.Executable;
import team.bytephoria.layout.items.base.Item;
import team.bytephoria.layout.items.types.builder.LayoutItemBuilder;
import team.bytephoria.layout.items.context.InventoryClickContext;

import java.util.EnumMap;
import java.util.function.Consumer;

public class ClickableItemLayout extends ItemLayout implements Executable {

    protected final EnumMap<ClickType, Consumer<InventoryClickContext>> clickActions;

    public ClickableItemLayout(
            final @NotNull Item item,
            final @NotNull EnumMap<ClickType, Consumer<InventoryClickContext>> clickActions
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
        final Consumer<InventoryClickContext> consumer = this.clickActions.get(context.clickEvent().getClick());
        if (consumer != null) {
            consumer.accept(context);
        }
    }

    public static class ItemBuilder extends LayoutItemBuilder<ItemBuilder, ClickableItemLayout> {

        protected final EnumMap<ClickType, Consumer<InventoryClickContext>> clickActions = new EnumMap<>(ClickType.class);

        public ItemBuilder onMiddleClick(final @NotNull Consumer<InventoryClickContext> clickContextConsumer) {
            return this.onClick(ClickType.MIDDLE, clickContextConsumer);
        }

        public ItemBuilder onDropClick(final @NotNull Consumer<InventoryClickContext> clickContextConsumer) {
            return this.onClick(ClickType.DROP, clickContextConsumer);
        }

        public ItemBuilder onLeftClick(final @NotNull Consumer<InventoryClickContext> clickContextConsumer) {
            return this.onClick(ClickType.LEFT, clickContextConsumer);
        }

        public ItemBuilder onRightClick(final @NotNull Consumer<InventoryClickContext> clickContextConsumer) {
            return this.onClick(ClickType.RIGHT, clickContextConsumer);
        }

        public ItemBuilder onClick(final @NotNull ClickType clickType, @NotNull Consumer<InventoryClickContext> consumer) {
            this.clickActions.put(clickType, consumer);
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
