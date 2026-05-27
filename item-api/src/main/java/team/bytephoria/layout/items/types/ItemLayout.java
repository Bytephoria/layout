package team.bytephoria.layout.items.types;

import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.base.Item;

/**
 * The occupant of a single layout slot. An {@code ItemLayout} pairs the visual data of an
 * {@link Item} with the slot's behavior, and it is the unit that layouts store and render.
 *
 * <p>Two built-in kinds are provided:
 * <ul>
 *   <li>{@link StaticItem} — purely visual, ignores clicks.</li>
 *   <li>{@link InteractiveItem} — visual plus per click-type actions.</li>
 * </ul>
 *
 * <p>Prefer {@link team.bytephoria.layout.items.ItemSlot} as the entry point for building either kind.
 */
public abstract class ItemLayout {

    private final Item item;

    protected ItemLayout(final @NotNull Item item) {
        this.item = item;
    }

    /** The visual item rendered in the slot. */
    public @NotNull Item item() {
        return this.item;
    }
}
