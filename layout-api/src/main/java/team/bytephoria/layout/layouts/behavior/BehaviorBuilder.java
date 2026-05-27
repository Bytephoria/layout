package team.bytephoria.layout.layouts.behavior;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.common.Builder;
import team.bytephoria.layout.items.context.ClickContext;
import team.bytephoria.layout.layouts.LoadingStrategy;
import team.bytephoria.layout.layouts.context.CloseContext;
import team.bytephoria.layout.layouts.context.OpenContext;

import java.util.function.Consumer;

public final class BehaviorBuilder implements Builder<Behavior> {

    private boolean closeOnClick = false;
    private boolean cancelAllClicks = false;
    private boolean cancelLayoutClicks = true;
    private boolean allowPlayerInventoryClicks = true;
    private boolean ignoreEmptySlots = false;

    private LoadingStrategy loadingStrategy = LoadingStrategy.LAZY;

    private long clickDelay = 0L;

    private Consumer<OpenContext> onOpen = openContext -> {};
    private Consumer<CloseContext> onClose = closeContext -> {};
    private Consumer<ClickContext> onClick = clickContext -> {};
    private Consumer<ClickContext> onClickDelayed = clickContext -> {};

    public BehaviorBuilder closeOnClick(boolean closeOnClick) {
        this.closeOnClick = closeOnClick;
        return this;
    }

    public BehaviorBuilder cancelAllClicks(final boolean cancelAllClicks) {
        this.cancelAllClicks = cancelAllClicks;
        return this;
    }

    public BehaviorBuilder cancelLayoutClicks(final boolean cancelLayoutClicks) {
        this.cancelLayoutClicks = cancelLayoutClicks;
        return this;
    }

    public BehaviorBuilder allowPlayerInventoryClicks(final boolean allowPlayerInventoryClicks) {
        this.allowPlayerInventoryClicks = allowPlayerInventoryClicks;
        return this;
    }

    public BehaviorBuilder ignoreEmptySlots(final boolean ignoreEmptySlots) {
        this.ignoreEmptySlots = ignoreEmptySlots;
        return this;
    }

    /**
     * Minimum time, in milliseconds, that must elapse between two accepted layout clicks from the
     * same player. Clicks that arrive sooner are ignored (the item action and {@code onClick} are
     * skipped) and routed to {@link #onClickDelayed(Consumer)} instead. {@code 0} disables the delay.
     */
    public BehaviorBuilder clickDelay(final long clickDelayMillis) {
        this.clickDelay = clickDelayMillis;
        return this;
    }

    public BehaviorBuilder loading(final @NotNull LoadingStrategy loadingStrategy) {
        this.loadingStrategy = loadingStrategy;
        return this;
    }

    public BehaviorBuilder onOpen(final @NotNull Consumer<OpenContext> consumer) {
        this.onOpen = consumer;
        return this;
    }

    public BehaviorBuilder onClose(final @NotNull Consumer<CloseContext> consumer) {
        this.onClose = consumer;
        return this;
    }

    public BehaviorBuilder onClick(final @NotNull Consumer<ClickContext> consumer) {
        this.onClick = consumer;
        return this;
    }

    /**
     * Handler invoked when a player clicks while still within the {@link #clickDelay(long)} window.
     * Receives the {@link ClickContext} of the rejected click so it can be customized (feedback,
     * sounds, messages, ...). The item action and {@code onClick} are not run for delayed clicks.
     */
    public BehaviorBuilder onClickDelayed(final @NotNull Consumer<ClickContext> consumer) {
        this.onClickDelayed = consumer;
        return this;
    }

    @Contract(" -> new")
    public @NotNull Behavior build() {
        return new Behavior(
                this.closeOnClick,
                this.cancelAllClicks,
                this.cancelLayoutClicks,
                this.allowPlayerInventoryClicks,
                this.ignoreEmptySlots,
                this.loadingStrategy,
                this.onOpen,
                this.onClose,
                this.onClick,
                this.clickDelay,
                this.onClickDelayed
        );
    }
}