package team.bytephoria.layout.layouts.behavior;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.common.Builder;
import team.bytephoria.layout.items.context.InventoryClickContext;
import team.bytephoria.layout.layouts.ItemLoadingStrategy;
import team.bytephoria.layout.layouts.context.InventoryCloseContext;
import team.bytephoria.layout.layouts.context.InventoryOpenContext;

import java.util.function.Consumer;

public final class LayoutBehaviorBuilder implements Builder<LayoutBehavior> {

    private boolean closeOnClick = false;
    private boolean cancelAllClicks = false;
    private boolean cancelLayoutClicks = true;
    private boolean allowPlayerInventoryClicks = true;
    private boolean ignoreEmptySlotClicks = false;

    private ItemLoadingStrategy itemLoadingStrategy = ItemLoadingStrategy.LAZY;

    private Consumer<InventoryOpenContext> onOpen = inventoryOpenContext -> {};
    private Consumer<InventoryCloseContext> onClose = inventoryCloseContext -> {};
    private Consumer<InventoryClickContext> onClick = clickContext -> {};

    public LayoutBehaviorBuilder closeOnClick(boolean closeOnClick) {
        this.closeOnClick = closeOnClick;
        return this;
    }

    public LayoutBehaviorBuilder cancelAllClicks(final boolean cancelAllClicks) {
        this.cancelAllClicks = cancelAllClicks;
        return this;
    }

    public LayoutBehaviorBuilder cancelLayoutClicks(final boolean cancelLayoutClicks) {
        this.cancelLayoutClicks = cancelLayoutClicks;
        return this;
    }

    public LayoutBehaviorBuilder allowPlayerInventoryClicks(final boolean allowPlayerInventoryClicks) {
        this.allowPlayerInventoryClicks = allowPlayerInventoryClicks;
        return this;
    }

    public LayoutBehaviorBuilder ignoreEmptySlots(final boolean ignoreEmptySlots) {
        this.ignoreEmptySlotClicks = ignoreEmptySlots;
        return this;
    }

    public LayoutBehaviorBuilder itemLoadingStrategy(final @NotNull ItemLoadingStrategy itemLoadingStrategy) {
        this.itemLoadingStrategy = itemLoadingStrategy;
        return this;
    }

    public LayoutBehaviorBuilder onOpen(final @NotNull Consumer<InventoryOpenContext> consumer) {
        this.onOpen = consumer;
        return this;
    }

    public LayoutBehaviorBuilder onClose(final @NotNull Consumer<InventoryCloseContext> consumer) {
        this.onClose = consumer;
        return this;
    }

    public LayoutBehaviorBuilder onClick(final @NotNull Consumer<InventoryClickContext> consumer) {
        this.onClick = consumer;
        return this;
    }

    @Contract(" -> new")
    public @NotNull LayoutBehavior build() {
        return new LayoutBehavior(
                this.closeOnClick,
                this.cancelAllClicks,
                this.cancelLayoutClicks,
                this.allowPlayerInventoryClicks,
                this.ignoreEmptySlotClicks,
                this.itemLoadingStrategy,
                this.onOpen,
                this.onClose,
                this.onClick
        );
    }
}