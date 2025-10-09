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
import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.items.Executable;
import team.bytephoria.layout.items.context.InventoryClickContext;
import team.bytephoria.layout.items.types.ItemLayout;
import team.bytephoria.layout.layouts.InventoryListener;
import team.bytephoria.layout.layouts.ItemLoadingStrategy;
import team.bytephoria.layout.layouts.Layout;
import team.bytephoria.layout.layouts.behavior.LayoutBehavior;
import team.bytephoria.layout.layouts.context.InventoryCloseContext;
import team.bytephoria.layout.layouts.context.InventoryOpenContext;

public class LayoutInventoryBase extends InventoryHolderBase
        implements Layout, InventoryListener {

    protected final Int2ObjectArrayMap<ItemLayout> itemLayouts;
    protected final LayoutBehavior layoutBehavior;

    protected boolean itemsLoaded = false;

    protected LayoutInventoryBase(
            final @NotNull LayoutBehavior layoutBehavior,
            final @NotNull Int2ObjectArrayMap<ItemLayout> itemLayouts,
            final @NotNull Component title,
            int size
    ) {
        super(title, size);
        this.itemLayouts = itemLayouts;
        this.layoutBehavior = layoutBehavior;
        this.initializeItemsIfRequired();
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
        this.initializeItemsIfRequired();
    }

    @Override
    public void onInventoryClick(final @NotNull InventoryClickEvent clickEvent) {
        final ItemStack currentItemStack = clickEvent.getCurrentItem();
        final ItemStack cursorItemStack = clickEvent.getCursor();

        // If clicked item is null and ignore empty slots is enabled:
        if (this.shouldIgnoreClick(currentItemStack, cursorItemStack)) {
            return;
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

        this.handleClick(inventoryClickContext);

        this.layoutBehavior.onClick().accept(inventoryClickContext);
        if (this.layoutBehavior.closeOnClick()) {
            clickEvent.getWhoClicked().closeInventory();
        }
    }

    @Override
    public void onInventoryOpen(final @NotNull InventoryOpenEvent openEvent) {
        if (!this.itemsLoaded && this.layoutBehavior.itemLoadingStrategy() == ItemLoadingStrategy.LAZY) {
            this.renderItems();
            this.markAsLoaded();
        }

        final Player player = (Player) openEvent.getPlayer();
        this.layoutBehavior.onOpen().accept(new InventoryOpenContext(player));
    }

    @Override
    public void onInventoryClose(final @NotNull InventoryCloseEvent closeEvent) {
        final Player player = (Player) closeEvent.getPlayer();
        this.layoutBehavior.onClose().accept(new InventoryCloseContext(player, closeEvent.getReason()));
    }

    protected void handleClick(final @NotNull InventoryClickContext inventoryClickContext) {
        final int clickedSlot = inventoryClickContext.clickEvent().getSlot();
        final ItemLayout itemLayout = this.itemLayouts.get(clickedSlot);
        if (itemLayout instanceof Executable executable) {
            executable.execute(inventoryClickContext);
        }
    }

    private void initializeItemsIfRequired() {
        if (this.layoutBehavior.itemLoadingStrategy() == ItemLoadingStrategy.INSTANT) {
            this.renderItems();
            this.itemsLoaded = true;
        }
    }

    protected void renderItems() {
        this.itemLayouts.forEach(this::item);
    }

    private boolean shouldIgnoreClick(final @Nullable ItemStack current, final @Nullable ItemStack cursor) {
        return current == null && this.layoutBehavior.ignoreEmptySlots() &&
                (cursor == null || cursor.getType() == Material.AIR);
    }

    protected void markAsLoaded() {
        this.itemsLoaded = true;
    }

    public boolean isItemsLoaded() {
        return this.itemsLoaded;
    }

    public void setItem(final int slot, final @Nullable ItemLayout itemLayout) {
        if (itemLayout == null) {
            this.itemLayouts.remove(slot);
            this.getInventory().setItem(slot, null);
            return;
        }

        this.itemLayouts.put(slot, itemLayout);
        this.item(slot, itemLayout.itemLayoutBase().to());
    }

    protected void item(final int slot, final @Nullable ItemStack itemStack) {
        this.getInventory().setItem(slot, itemStack);
    }

    @Override
    public void item(final int slot, final @Nullable ItemLayout itemLayout) {
        this.item(slot, itemLayout == null ? null : itemLayout.itemLayoutBase().to());
    }

    @Override
    public void fill(final @NotNull ItemLayout itemLayout) {
        final ItemStack itemStack = itemLayout.itemLayoutBase().to();
        for (int slot = 0; slot < super.slots(); slot++) {
            this.item(slot, itemStack);
        }
    }

    @Override
    public void fillRange(final int from, final int to, final @NotNull ItemLayout itemLayout) {
        final ItemStack itemStack = itemLayout.itemLayoutBase().to();
        for (int slot = from; slot < to; slot++) {
            this.item(slot, itemStack);
        }
    }

    @Override
    public void update(final int slot) {
        final ItemLayout itemLayout = this.itemLayouts.get(slot);
        if (itemLayout != null) {
            this.item(slot, itemLayout.itemLayoutBase().to());
        }
    }

    @Override
    public void open(final @NotNull Player player) {
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