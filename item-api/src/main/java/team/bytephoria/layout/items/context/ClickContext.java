package team.bytephoria.layout.items.context;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.items.ItemClickType;

public final class ClickContext {

    private final @NotNull Player player;
    private final @NotNull ItemClickType clickType;
    private final int slot;
    private final int hotbarButton;
    private final @Nullable ItemStack carriedItem;
    private final @Nullable InventoryClickEvent clickEvent;

    private ClickContext(
            final @NotNull Player player,
            final @NotNull ItemClickType clickType,
            final int slot,
            final int hotbarButton,
            final @Nullable ItemStack carriedItem,
            final @Nullable InventoryClickEvent clickEvent
    ) {
        this.player = player;
        this.clickType = clickType;
        this.slot = slot;
        this.hotbarButton = hotbarButton;
        this.carriedItem = carriedItem;
        this.clickEvent = clickEvent;
    }

    @Contract("_, _ -> new")
    public static @NotNull ClickContext fromEvent(
            final @NotNull Player player,
            final @NotNull InventoryClickEvent event
    ) {
        final ItemStack cursor = event.getCursor();
        return new ClickContext(
                player,
                ItemClickType.fromBukkitType(event.getClick()),
                event.getSlot(),
                event.getHotbarButton(),
                cursor.getType() == Material.AIR ? null : cursor,
                event
        );
    }

    @Contract("_, _, _, _, _ -> new")
    public static @NotNull ClickContext fromPacket(
            final @NotNull Player player,
            final @NotNull ItemClickType clickType,
            final int slot,
            final int hotbarButton,
            final @Nullable ItemStack carriedItem
    ) {
        return new ClickContext(player, clickType, slot, hotbarButton, carriedItem, null);
    }

    public @NotNull Player player() {
        return this.player;
    }

    public @NotNull ItemClickType clickType() {
        return this.clickType;
    }

    /** Slot index that was clicked inside the opened container. */
    public int slot() {
        return this.slot;
    }

    /** Hotbar key (0-8) for {@link ItemClickType#NUMBER_KEY} clicks, -1 otherwise. */
    public int hotbarButton() {
        return this.hotbarButton;
    }

    /** Item the cursor was holding at the time of the click, or {@code null} if the cursor was empty. */
    public @Nullable ItemStack carriedItem() {
        return this.carriedItem;
    }

    /** Bukkit event, present only in Bukkit-managed inventories. Always {@code null} in packet inventories. */
    public @Nullable InventoryClickEvent clickEvent() {
        return this.clickEvent;
    }
}
