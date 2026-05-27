package team.bytephoria.layout.layouts.behavior;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.context.ClickContext;
import team.bytephoria.layout.layouts.LoadingStrategy;
import team.bytephoria.layout.layouts.context.CloseContext;
import team.bytephoria.layout.layouts.context.OpenContext;

import java.util.function.Consumer;

public record Behavior(
        boolean closeOnClick,
        boolean cancelAllClicks,
        boolean cancelLayoutClicks,
        boolean allowPlayerInventoryClicks,
        boolean ignoreEmptySlots,
        LoadingStrategy loadingStrategy,
        Consumer<OpenContext> onOpen,
        Consumer<CloseContext> onClose,
        Consumer<ClickContext> onClick,
        long clickDelay,
        Consumer<ClickContext> onClickDelayed
) {

    public static @NotNull Behavior defaults() {
        return builder().build();
    }

    @Contract(" -> new")
    public static @NotNull BehaviorBuilder builder() {
        return new BehaviorBuilder();
    }
}