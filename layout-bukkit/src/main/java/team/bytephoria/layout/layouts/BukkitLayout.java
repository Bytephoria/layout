package team.bytephoria.layout.layouts;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.layouts.builder.page.LayoutPagedInventoryBuilder;
import team.bytephoria.layout.layouts.listener.InventoryClickListener;
import team.bytephoria.layout.layouts.listener.InventoryCloseListener;
import team.bytephoria.layout.layouts.listener.InventoryOpenListener;
import team.bytephoria.layout.layouts.types.layout.LayoutFixedInventory;
import team.bytephoria.layout.layouts.types.layout.LayoutSizedInventory;

public interface BukkitLayout {

    @Contract(" -> new")
    static @NotNull LayoutFixedInventory.Builder fixed() {
        return LayoutFixedInventory.builder();
    }

    static @NotNull LayoutSizedInventory.Builder sized() {
        return LayoutSizedInventory.builder();
    }

    @Contract(" -> new")
    static @NotNull LayoutPagedInventoryBuilder paged() {
        return new LayoutPagedInventoryBuilder();
    }

    static void init(final @NotNull JavaPlugin plugin) {
        final PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(new InventoryClickListener(), plugin);
        pluginManager.registerEvents(new InventoryOpenListener(), plugin);
        pluginManager.registerEvents(new InventoryCloseListener(), plugin);
    }
}
