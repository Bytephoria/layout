package team.bytephoria.layout.items.builder;

import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.ClickableItemLayout;
import team.bytephoria.layout.items.context.InventoryClickContext;

import java.util.EnumMap;
import java.util.function.Consumer;

public abstract class ItemBuilderClickable<B extends ItemBuilderClickable<B, T>, T extends ClickableItemLayout>
        extends ItemBuilderBase<B, T> {

    protected final EnumMap<ClickType, Consumer<InventoryClickContext>> clickActions = new EnumMap<>(ClickType.class);

    public B onMiddleClick(final @NotNull Consumer<InventoryClickContext> clickContextConsumer) {
        return this.onClick(ClickType.MIDDLE, clickContextConsumer);
    }

    public B onDropClick(final @NotNull Consumer<InventoryClickContext> clickContextConsumer) {
        return this.onClick(ClickType.DROP, clickContextConsumer);
    }

    public B onLeftClick(final @NotNull Consumer<InventoryClickContext> clickContextConsumer) {
        return this.onClick(ClickType.LEFT, clickContextConsumer);
    }

    public B onRightClick(final @NotNull Consumer<InventoryClickContext> clickContextConsumer) {
        return this.onClick(ClickType.RIGHT, clickContextConsumer);
    }

    public B onClick(final @NotNull ClickType clickType, @NotNull Consumer<InventoryClickContext> consumer) {
        this.clickActions.put(clickType, consumer);
        return this.self();
    }
}