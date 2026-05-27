package team.bytephoria.layout.layouts.base;

import team.bytephoria.layout.items.types.ItemLayout;

import it.unimi.dsi.fastutil.ints.Int2LongOpenHashMap;
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
import team.bytephoria.layout.items.base.Item;
import team.bytephoria.layout.items.context.ClickContext;
import team.bytephoria.layout.layouts.InventoryListener;
import team.bytephoria.layout.layouts.LoadingStrategy;
import team.bytephoria.layout.layouts.Layout;
import team.bytephoria.layout.layouts.behavior.Behavior;
import team.bytephoria.layout.layouts.context.CloseContext;
import team.bytephoria.layout.layouts.context.OpenContext;

public class LayoutInventoryBase extends InventoryHolderBase
        implements Layout, InventoryListener {

    protected final Int2ObjectArrayMap<ItemLayout> itemLayouts;
    protected final Behavior behavior;

    protected boolean itemsLoaded = false;

    /** Last accepted click timestamp (ms) per player entity id — used to enforce {@link Behavior#clickDelay()}. */
    private final Int2LongOpenHashMap lastClickAt = new Int2LongOpenHashMap();

    protected LayoutInventoryBase(
            final @NotNull Behavior behavior,
            final @NotNull Int2ObjectArrayMap<ItemLayout> itemLayouts,
            final @NotNull Component title,
            int size
    ) {
        super(title, size);
        this.itemLayouts = itemLayouts;
        this.behavior = behavior;
        this.initializeItemsIfRequired();
    }

    protected LayoutInventoryBase(
            final @NotNull Behavior behavior,
            final @NotNull Int2ObjectArrayMap<ItemLayout> itemLayouts,
            final @NotNull InventoryType type,
            final @NotNull Component title
    ) {
        super(type, title);
        this.itemLayouts = itemLayouts;
        this.behavior = behavior;
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

        final Player player = (Player) clickEvent.getWhoClicked();

        // Anti-spam: ignore clicks that arrive within the configured delay window and route them
        // to the customizable delayed-click handler instead of running the item action.
        if (this.isClickFrozen(player)) {
            clickEvent.setCancelled(true);
            final ClickContext frozenContext = ClickContext.fromEvent(player, clickEvent);
            this.behavior.onClickDelayed().accept(frozenContext);
            return;
        }

        if (this.behavior.cancelAllClicks()) {
            clickEvent.setCancelled(true);
        }

        final Inventory clickedInventory = clickEvent.getClickedInventory();
        if (clickedInventory == null) {
            return;
        }

        final InventoryType inventoryType = clickedInventory.getType();
        if (inventoryType == InventoryType.PLAYER && this.behavior.allowPlayerInventoryClicks()) {
            return;
        }

        if (this.behavior.cancelLayoutClicks()) {
            clickEvent.setCancelled(true);
        }

        final ClickContext clickContext = ClickContext.fromEvent(player, clickEvent);

        this.handleClick(clickContext);

        this.behavior.onClick().accept(clickContext);
        if (this.behavior.closeOnClick()) {
            clickEvent.getWhoClicked().closeInventory();
        }
    }

    @Override
    public void onInventoryOpen(final @NotNull InventoryOpenEvent openEvent) {
        if (!this.itemsLoaded && this.behavior.loadingStrategy() == LoadingStrategy.LAZY) {
            this.renderItems();
            this.markAsLoaded();
        }

        final Player player = (Player) openEvent.getPlayer();
        this.behavior.onOpen().accept(new OpenContext(player));
    }

    @Override
    public void onInventoryClose(final @NotNull InventoryCloseEvent closeEvent) {
        final Player player = (Player) closeEvent.getPlayer();
        this.lastClickAt.remove(player.getEntityId());
        this.behavior.onClose().accept(new CloseContext(player, closeEvent.getReason()));
    }

    protected void handleClick(final @NotNull ClickContext clickContext) {
        final ItemLayout itemLayout = this.itemLayouts.get(clickContext.slot());
        if (itemLayout instanceof Executable executable) {
            executable.execute(clickContext);
        }
    }

    private void initializeItemsIfRequired() {
        if (this.behavior.loadingStrategy() == LoadingStrategy.INSTANT) {
            this.renderItems();
            this.itemsLoaded = true;
        }
    }

    protected void renderItems() {
        this.itemLayouts.forEach(this::item);
    }

    /**
     * Returns {@code true} if the player's click should be frozen (it arrived within the
     * {@link Behavior#clickDelay()} window). When {@code false}, the click timestamp is refreshed.
     */
    private boolean isClickFrozen(final @NotNull Player player) {
        final long delay = this.behavior.clickDelay();
        if (delay <= 0L) {
            return false;
        }
        final int entityId = player.getEntityId();
        final long now = System.currentTimeMillis();
        if (now - this.lastClickAt.get(entityId) < delay) {
            return true;
        }
        this.lastClickAt.put(entityId, now);
        return false;
    }

    private boolean shouldIgnoreClick(final @Nullable ItemStack current, final @Nullable ItemStack cursor) {
        return current == null && this.behavior.ignoreEmptySlots() &&
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
        this.item(slot, itemLayout.item().to());
    }

    protected void item(final int slot, final @Nullable ItemStack itemStack) {
        this.getInventory().setItem(slot, itemStack);
    }

    @Override
    public void item(final int slot, final @Nullable ItemLayout itemLayout) {
        this.item(slot, itemLayout == null ? null : itemLayout.item().to());
    }

    @Override
    public void fill(final @NotNull ItemLayout itemLayout) {
        final ItemStack itemStack = itemLayout.item().to();
        for (int slot = 0; slot < super.slots(); slot++) {
            this.item(slot, itemStack);
        }
    }

    @Override
    public void fillRange(final int from, final int to, final @NotNull ItemLayout itemLayout) {
        final ItemStack itemStack = itemLayout.item().to();
        for (int slot = from; slot < to; slot++) {
            this.item(slot, itemStack);
        }
    }

    @Override
    public void update(final int slot) {
        final ItemLayout itemLayout = this.itemLayouts.get(slot);
        if (itemLayout != null) {
            this.item(slot, itemLayout.item().to());
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

    public @NotNull Behavior behavior() {
        return this.behavior;
    }

}
