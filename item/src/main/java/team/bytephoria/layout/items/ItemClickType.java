package team.bytephoria.layout.items;

import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the different types of clicks that can occur on an item within
 * a custom inventory or GUI context. This is a simplified abstraction of
 * Bukkit's {@link ClickType}, focusing only on slot/item interactions.
 */
public enum ItemClickType {

    LEFT,
    SHIFT_LEFT,
    RIGHT,
    SHIFT_RIGHT,
    MIDDLE,
    NUMBER_KEY,
    DOUBLE_CLICK,
    DROP,
    CONTROL_DROP,
    SWAP_OFFHAND,
    UNKNOWN;

    /**
     * Converts this {@link ItemClickType} to its corresponding Bukkit {@link ClickType}.
     *
     * @return the matching {@link ClickType}, or {@link ClickType#UNKNOWN} if no direct mapping exists.
     */
    public ClickType toBukkitType() {
        return switch (this) {
            case LEFT -> ClickType.LEFT;
            case SHIFT_LEFT -> ClickType.SHIFT_LEFT;
            case RIGHT -> ClickType.RIGHT;
            case SHIFT_RIGHT -> ClickType.SHIFT_RIGHT;
            case MIDDLE -> ClickType.MIDDLE;
            case NUMBER_KEY -> ClickType.NUMBER_KEY;
            case DOUBLE_CLICK -> ClickType.DOUBLE_CLICK;
            case DROP -> ClickType.DROP;
            case CONTROL_DROP -> ClickType.CONTROL_DROP;
            case SWAP_OFFHAND -> ClickType.SWAP_OFFHAND;
            case UNKNOWN -> ClickType.UNKNOWN;
        };
    }

    /**
     * Converts a Bukkit {@link ClickType} into an {@link ItemClickType}.
     *
     * @param clickType the Bukkit click type to convert.
     * @return the corresponding {@link ItemClickType}, or {@link #UNKNOWN} if no direct match exists.
     */
    @Contract(pure = true)
    public static @NotNull ItemClickType fromBukkitType(final @NotNull ClickType clickType) {
        return switch (clickType) {
            case LEFT -> LEFT;
            case SHIFT_LEFT -> SHIFT_LEFT;
            case RIGHT -> RIGHT;
            case SHIFT_RIGHT -> SHIFT_RIGHT;
            case MIDDLE -> MIDDLE;
            case NUMBER_KEY -> NUMBER_KEY;
            case DOUBLE_CLICK -> DOUBLE_CLICK;
            case DROP -> DROP;
            case CONTROL_DROP -> CONTROL_DROP;
            case SWAP_OFFHAND -> SWAP_OFFHAND;
            default -> UNKNOWN;
        };
    }
}