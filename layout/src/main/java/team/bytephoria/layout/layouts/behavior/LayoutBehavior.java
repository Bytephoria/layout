package team.bytephoria.layout.layouts.behavior;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.context.InventoryClickContext;
import team.bytephoria.layout.layouts.context.InventoryCloseContext;
import team.bytephoria.layout.layouts.context.InventoryOpenContext;

import java.util.function.Consumer;

public record LayoutBehavior(
        boolean closeOnClick,
        boolean cancelAllClicks,
        boolean cancelLayoutClicks,
        boolean allowPlayerInventoryClicks,
        boolean ignoreEmptySlots,
        Consumer<InventoryOpenContext> onOpen,
        Consumer<InventoryCloseContext> onClose,
        Consumer<InventoryClickContext> onClick
) {

    @Contract(" -> new")
    public static @NotNull LayoutBehaviorBuilder builder() {
        return new LayoutBehaviorBuilder();
    }
}