package team.bytephoria.layout.items;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.base.Item;
import team.bytephoria.layout.items.types.InteractiveItem;
import team.bytephoria.layout.items.types.ItemLayout;
import team.bytephoria.layout.items.types.StaticItem;

/**
 * Convenience entry point for building the two kinds of {@link ItemLayout} placed into layout slots.
 *
 * <pre>{@code
 * // Display-only slot
 * layout.item(0, ItemSlot.display(glass));
 *
 * // Clickable slot
 * layout.item(13, ItemSlot.interactive(button)
 *         .leftClick(ctx -> ctx.player().sendMessage("Clicked!"))
 *         .build());
 * }</pre>
 */
public final class ItemSlot {

    private ItemSlot() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    /** A purely visual, non-clickable slot showing the given item. */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull StaticItem display(final @NotNull Item item) {
        return StaticItem.display(item);
    }

    /** A clickable slot builder. Set the visual item with {@code item(...)}, add handlers, then {@code build()}. */
    @Contract(value = " -> new", pure = true)
    public static InteractiveItem.@NotNull Builder interactive() {
        return InteractiveItem.builder();
    }

    /** A clickable slot builder pre-filled with the given visual item. Add handlers, then {@code build()}. */
    @Contract(value = "_ -> new", pure = true)
    public static InteractiveItem.@NotNull Builder interactive(final @NotNull Item item) {
        return InteractiveItem.builder().item(item);
    }
}
