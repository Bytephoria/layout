package team.bytephoria.layout.layouts.base;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.Executable;
import team.bytephoria.layout.items.context.InventoryClickContext;
import team.bytephoria.layout.items.types.ItemLayout;
import team.bytephoria.layout.layouts.InventoryListener;
import team.bytephoria.layout.layouts.Layout;
import team.bytephoria.layout.layouts.behavior.LayoutBehavior;
import team.bytephoria.layout.layouts.context.InventoryCloseContext;
import team.bytephoria.layout.layouts.context.InventoryOpenContext;

public class LayoutInventoryBase extends InventoryHolderBase
        implements Layout, InventoryListener {

    protected final Int2ObjectArrayMap<ItemLayout> itemLayouts;
    protected final LayoutBehavior layoutBehavior;

    protected LayoutInventoryBase(
            final @NotNull LayoutBehavior layoutBehavior,
            final @NotNull Int2ObjectArrayMap<ItemLayout> itemLayouts,
            final @NotNull Component title,
            int size
    ) {
        super(title, size);
        this.itemLayouts = itemLayouts;
        this.layoutBehavior = layoutBehavior;
    }

    protected LayoutInventoryBase(
            final @NotNull LayoutBehavior layoutBehavior,
            final @NotNull Int2ObjectArrayMap<ItemLayout> itemLayouts,
            final @NotNull InventoryType type,
            final @NotNull Component title
    ) {
        super(type, title);
        this.itemLayouts = itemLayouts;
        this.layoutBehavior = layoutBehavior;
    }

    @Override
    public void onInventoryClick(final @NotNull InventoryClickEvent clickEvent) {
        final ItemStack currentItemStack = clickEvent.getCurrentItem();
        final ItemStack cursorItemStack = clickEvent.getCursor();

        // If clicked item is null and ignore empty slots is enabled:
        if (currentItemStack == null && this.layoutBehavior.ignoreEmptySlots()) {
            // If cursor item stack is null or is air
            if (cursorItemStack == null || cursorItemStack.getType() == Material.AIR) {
                return;
            }
        }

        if (this.layoutBehavior.cancelAllClicks()) {
            clickEvent.setCancelled(true);
        }

        final Inventory clickedInventory = clickEvent.getClickedInventory();
        if (clickedInventory == null) {
            return;
        }

        final InventoryType inventoryType = clickedInventory.getType();
        if (inventoryType == InventoryType.PLAYER && this.layoutBehavior.allowPlayerInventoryClicks()) {
            return;
        }

        if (this.layoutBehavior.cancelLayoutClicks()) {
            clickEvent.setCancelled(true);
        }

        final Player player = (Player) clickEvent.getWhoClicked();
        final InventoryClickContext inventoryClickContext = new InventoryClickContext(
                player,
                clickEvent
        );

        final int clickedSlot = clickEvent.getSlot();
        final ItemLayout itemLayout = this.itemLayouts.get(clickedSlot);
        if (itemLayout instanceof Executable executable) {
            executable.execute(inventoryClickContext);
        }

        this.layoutBehavior.onClick().accept(inventoryClickContext);
        if (this.layoutBehavior.closeOnClick()) {
            clickEvent.getWhoClicked().closeInventory();
        }
    }

    @Override
    public void onInventoryOpen(final @NotNull InventoryOpenEvent openEvent) {
        final Player player = (Player) openEvent.getPlayer();
        this.layoutBehavior.onOpen().accept(new InventoryOpenContext(player));
    }

    @Override
    public void onInventoryClose(final @NotNull InventoryCloseEvent closeEvent) {
        final Player player = (Player) closeEvent.getPlayer();
        this.layoutBehavior.onClose().accept(new InventoryCloseContext(player, closeEvent.getReason()));
    }

    private void item(final int slot, final @NotNull ItemStack itemStack) {
        this.getInventory().setItem(slot, itemStack);
    }

    @Override
    public void item(final int slot, final @NotNull ItemLayout itemLayout) {
        this.item(slot, itemLayout.itemLayoutBase().build());
    }

    @Override
    public void fill(final @NotNull ItemLayout itemLayout) {
        final ItemStack itemStack = itemLayout.itemLayoutBase().build();
        for (int slot = 0; slot < super.slots(); slot++) {
            this.item(slot, itemStack);
        }
    }

    @Override
    public void fillRange(final int from, final int to, final @NotNull ItemLayout itemLayout) {
        final ItemStack itemStack = itemLayout.itemLayoutBase().build();
        for (int slot = from; slot < to; slot++) {
            this.item(slot, itemStack);
        }
    }

    @Override
    public void update(final int slot) {
        final ItemLayout itemLayout = this.itemLayouts.get(slot);
        if (itemLayout != null) {
            this.item(slot, itemLayout.itemLayoutBase().build());
        }
    }

    @Override
    public void open(final @NotNull Player player) {
        this.itemLayouts.forEach(this::item);

        player.openInventory(this.getInventory());
    }

    @Override
    public void open(final @NotNull Player @NotNull ... players) {
        for (final Player player : players) {
            player.openInventory(this.getInventory());
        }
    }

    public @NotNull LayoutBehavior layoutBehavior() {
        return this.layoutBehavior;
    }

}