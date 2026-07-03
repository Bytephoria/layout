package team.bytephoria.nms.adapter.v26_1;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryView;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import team.bytephoria.layout.items.ItemClickType;
import team.bytephoria.layout.layouts.behavior.Behavior;
import team.bytephoria.nms.packet.base.PacketLayoutBase;
import team.bytephoria.nms.packet.menu.PacketMenu;

import java.util.List;

/**
 * A real server-side container menu backing a visual-only layout, for Paper 26.1.2+. All slots are
 * locked (no pickup, no placement, no shift-move), so the vanilla click pipeline rejects every item
 * movement — including offhand swaps and number-key swaps — and re-syncs the client automatically
 * via {@link #broadcastChanges()}.
 */
public final class PacketLayoutMenu extends AbstractContainerMenu implements PacketMenu {

    private final SimpleContainer content;
    private final PacketLayoutBase layout;
    private final org.bukkit.entity.Player bukkitPlayer;
    private CraftInventoryView<PacketLayoutMenu, org.bukkit.inventory.Inventory> bukkitView;

    public PacketLayoutMenu(
            final @NotNull MenuType<?> type,
            final int containerId,
            final @NotNull Inventory playerInventory,
            final int containerSlots,
            final @NotNull PacketLayoutBase layout,
            final @NotNull org.bukkit.entity.Player bukkitPlayer
    ) {
        super(type, containerId);
        this.content = new SimpleContainer(containerSlots);
        this.layout = layout;
        this.bukkitPlayer = bukkitPlayer;

        final Behavior behavior = layout.behavior();
        // Mirrors the cancellation rules used by the Bukkit module's onInventoryClick:
        //  - layout (container) slots are locked when all clicks or layout clicks are cancelled.
        //  - player inventory slots are locked unless the developer allows interacting with them.
        final boolean lockLayout = behavior.cancelAllClicks() || behavior.cancelLayoutClicks();
        final boolean lockPlayerInventory = behavior.cancelAllClicks()
                || (!behavior.allowPlayerInventoryClicks() && behavior.cancelLayoutClicks());

        // Visual (container) slots.
        for (int i = 0; i < containerSlots; i++) {
            this.addSlot(slot(this.content, i, lockLayout));
        }

        // Player main inventory (inventory slots 9..35).
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(slot(playerInventory, col + row * 9 + 9, lockPlayerInventory));
            }
        }

        // Player hotbar (inventory slots 0..8).
        for (int col = 0; col < 9; col++) {
            this.addSlot(slot(playerInventory, col, lockPlayerInventory));
        }
    }

    private static @NotNull Slot slot(final @NotNull Container container, final int index, final boolean locked) {
        return locked ? new LockedSlot(container, index) : new Slot(container, index, 0, 0);
    }

    @Override
    public boolean stillValid(final @NotNull Player player) {
        return true;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(final @NotNull Player player, final int slotIndex) {
        // Shift-click moves nothing in a visual-only layout.
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull CraftInventoryView<PacketLayoutMenu, org.bukkit.inventory.Inventory> getBukkitView() {
        if (this.bukkitView == null) {
            final CraftInventory inventory = new CraftInventory(this.content);
            this.bukkitView = new CraftInventoryView<>(this.bukkitPlayer, inventory, this);
        }

        return this.bukkitView;
    }

    @Override
    public void clicked(final int slotIndex, final int buttonNum, final @NotNull ContainerInput input, final @NotNull Player player) {
        // Run the vanilla click pipeline. Each slot enforces its own lock state: locked slots reject
        // every movement (including offhand and number-key swaps), while unlocked slots — typically the
        // player inventory when the behavior allows it — move items exactly like a normal container.
        // Paper's broadcastChanges() (called right after this) re-syncs the client and reverts any
        // optimistic prediction the server refused, so no item can be taken from a locked slot.
        super.clicked(slotIndex, buttonNum, input, player);

        final ItemClickType clickType = ClickTypeConverter.fromNms(input, buttonNum);
        final int hotbarButton = clickType == ItemClickType.NUMBER_KEY ? buttonNum : -1;
        this.layout.handleMenuClick(this.bukkitPlayer, slotIndex, clickType, hotbarButton);
    }

    @Override
    public void removed(final @NotNull Player player) {
        super.removed(player);
        this.layout.handleMenuClose(this.bukkitPlayer);
    }

    @Override
    public int containerId() {
        return this.containerId;
    }

    /** Rebuilds the visual slot contents from the layout's current items. */
    @Override
    public void refreshContent() {
        final List<ItemStack> items = this.layout.buildNmsItemList();
        final int size = this.content.getContainerSize();
        for (int i = 0; i < size; i++) {
            this.content.setItem(i, i < items.size() ? items.get(i) : ItemStack.EMPTY);
        }
    }

    private static final class LockedSlot extends Slot {

        private LockedSlot(final Container container, final int slot) {
            super(container, slot, 0, 0);
        }

        @Override
        public boolean mayPickup(final @NonNull Player player) {
            return false;
        }

        @Override
        public boolean mayPlace(final @NonNull ItemStack itemStack) {
            return false;
        }

        @Override
        public boolean allowModification(final @NonNull Player player) {
            return false;
        }
    }
}
