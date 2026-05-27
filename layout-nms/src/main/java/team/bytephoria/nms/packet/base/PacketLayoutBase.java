package team.bytephoria.nms.packet.base;

import io.papermc.paper.adventure.PaperAdventure;
import it.unimi.dsi.fastutil.ints.Int2LongOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.kyori.adventure.text.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.items.Executable;
import team.bytephoria.layout.items.ItemClickType;
import team.bytephoria.layout.items.context.ClickContext;
import team.bytephoria.layout.items.types.ItemLayout;
import team.bytephoria.layout.layouts.Layout;
import team.bytephoria.layout.layouts.LoadingStrategy;
import team.bytephoria.layout.layouts.behavior.Behavior;
import team.bytephoria.layout.layouts.context.CloseContext;
import team.bytephoria.layout.layouts.context.OpenContext;
import team.bytephoria.nms.packet.menu.PacketLayoutMenu;

import java.util.ArrayList;
import java.util.List;

public abstract class PacketLayoutBase implements Layout {

    protected final Int2ObjectArrayMap<ItemLayout> itemLayouts;
    protected final Behavior behavior;
    private final Component title;

    protected boolean itemsLoaded = false;

    private final PacketSessionRegistry sessionRegistry = new PacketSessionRegistry();

    /** Last accepted click timestamp (ms) per player entity id — used to enforce {@link Behavior#clickDelay()}. */
    private final Int2LongOpenHashMap lastClickAt = new Int2LongOpenHashMap();

    private static JavaPlugin plugin;

    public static void setPlugin(final @NotNull JavaPlugin javaPlugin) {
        plugin = javaPlugin;
    }

    protected PacketLayoutBase(
            final @NotNull Behavior behavior,
            final @NotNull Int2ObjectArrayMap<ItemLayout> itemLayouts,
            final @NotNull Component title
    ) {
        this.behavior = behavior;
        this.itemLayouts = itemLayouts;
        this.title = title;
        if (this.behavior.loadingStrategy() == LoadingStrategy.INSTANT) {
            this.itemsLoaded = true;
        }
    }

    protected abstract @NotNull MenuType<?> menuType();

    protected abstract int size();

    @Override
    public @NotNull Component title() {
        return this.title;
    }

    @Override
    public void open(final @NotNull Player player) {
        final ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        final net.minecraft.network.chat.Component nmsTitle = PaperAdventure.asVanilla(this.title);

        serverPlayer.openMenu(new MenuProvider() {
            @Override
            public @NotNull net.minecraft.network.chat.Component getDisplayName() {
                return nmsTitle;
            }

            @Override
            public @NotNull AbstractContainerMenu createMenu(final int containerId, final @NotNull Inventory inventory, final @NotNull net.minecraft.world.entity.player.Player p) {
                return new PacketLayoutMenu(menuType(), containerId, inventory, size(), PacketLayoutBase.this, player);
            }
        });

        // openMenu only assigns containerMenu if the menu was actually opened (e.g. not cancelled).
        if (!(serverPlayer.containerMenu instanceof PacketLayoutMenu menu)) {
            return;
        }

        final PacketSession session = new PacketSession(player, serverPlayer, menu);
        this.sessionRegistry.register(session);

        menu.refreshContent();
        menu.sendAllDataToRemote();

        this.behavior.onOpen().accept(new OpenContext(player));
    }

    @Override
    public void open(final @NotNull Player @NotNull ... players) {
        for (final Player player : players) {
            this.open(player);
        }
    }

    public void close(final @NotNull Player player) {
        final PacketSession session = this.sessionRegistry.get(player);
        if (session == null) {
            return;
        }
        // Triggers AbstractContainerMenu#removed -> handleMenuClose, which cleans up the session.
        session.serverPlayer().closeContainer();
    }

    /**
     * Invoked by {@link PacketLayoutMenu} when the player clicks a slot. Visual-only: this runs the
     * item's {@link Executable} and the behavior callbacks but never moves any item.
     */
    public void handleMenuClick(
            final @NotNull Player player,
            final int slotIndex,
            final @NotNull ItemClickType clickType,
            final int hotbarButton
    ) {
        if (slotIndex < 0 || slotIndex >= this.size()) {
            return;
        }

        final ClickContext context = ClickContext.fromPacket(player, clickType, slotIndex, hotbarButton, null);

        // Anti-spam: ignore clicks that arrive within the configured delay window and route them
        // to the customizable delayed-click handler instead of running the item action.
        if (this.isClickFrozen(player)) {
            this.behavior.onClickDelayed().accept(context);
            return;
        }

        final ItemLayout itemLayout = this.resolveItem(slotIndex);
        if (itemLayout instanceof Executable executable) {
            executable.execute(context);
        }

        this.behavior.onClick().accept(context);
        if (this.behavior.closeOnClick()) {
            // Defer: this runs inside Paper's handleContainerClick (mid-click). Closing now would
            // swap serverPlayer.containerMenu and crash the remaining click processing.
            Bukkit.getScheduler().runTask(plugin, () -> this.close(player));
        }
    }

    /**
     * Invoked by {@link PacketLayoutMenu} when the menu is closed (by the player, the server, or
     * because another menu replaced it).
     */
    public void handleMenuClose(final @NotNull Player player) {
        if (!this.sessionRegistry.has(player)) {
            return;
        }
        this.sessionRegistry.remove(player);
        this.lastClickAt.remove(player.getEntityId());
        this.behavior.onClose().accept(new CloseContext(player, null));
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

    /** Resolves the item shown at a given container slot. Overridden by paged layouts. */
    protected @Nullable ItemLayout resolveItem(final int slot) {
        return this.itemLayouts.get(slot);
    }

    /** Rebuilds and re-sends the visual contents to every viewer. */
    protected void renderAll() {
        for (final PacketSession session : this.sessionRegistry.sessions()) {
            session.menu().refreshContent();
            session.menu().broadcastChanges();
        }
    }

    /** Builds the NMS item list for the container portion. Overridden by paged layouts. */
    public @NotNull List<ItemStack> buildNmsItemList() {
        final List<ItemStack> items = new ArrayList<>(this.size());
        for (int slot = 0; slot < this.size(); slot++) {
            final ItemLayout layout = this.itemLayouts.get(slot);
            items.add(layout != null ? CraftItemStack.asNMSCopy(layout.item().to()) : ItemStack.EMPTY);
        }

        return items;
    }

    @Override
    public void item(final int slot, final @Nullable ItemLayout itemLayout) {
        if (itemLayout == null) {
            this.itemLayouts.remove(slot);
        } else {
            this.itemLayouts.put(slot, itemLayout);
        }

        this.renderAll();
    }

    @Override
    public void fill(final @NotNull ItemLayout itemLayout) {
        for (int slot = 0; slot < this.size(); slot++) {
            this.itemLayouts.put(slot, itemLayout);
        }

        this.renderAll();
    }

    @Override
    public void fillRange(final int from, final int to, final @NotNull ItemLayout itemLayout) {
        for (int slot = from; slot < to; slot++) {
            this.itemLayouts.put(slot, itemLayout);
        }
        this.renderAll();
    }

    @Override
    public void update(final int slot) {
        this.renderAll();
    }

    public @NotNull Behavior behavior() {
        return this.behavior;
    }

    public @NotNull PacketSessionRegistry sessionRegistry() {
        return this.sessionRegistry;
    }
}
