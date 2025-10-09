package team.bytephoria.layout.layouts;

import net.kyori.adventure.text.Component;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.layouts.builder.page.LayoutPagedInventoryBuilder;
import team.bytephoria.layout.layouts.editor.LayoutEditor;
import team.bytephoria.layout.layouts.listener.InventoryClickListener;
import team.bytephoria.layout.layouts.listener.InventoryCloseListener;
import team.bytephoria.layout.layouts.listener.InventoryOpenListener;
import team.bytephoria.layout.layouts.types.layout.LayoutFixedInventory;
import team.bytephoria.layout.layouts.types.layout.LayoutSizedInventory;

public interface Layout extends LayoutEditor, InventoryOpenable {

    @Contract(value = " -> new", pure = true)
    static @NotNull LayoutFixedInventory.Builder fixed() {
        return LayoutFixedInventory.builder();
    }

    static @NotNull LayoutSizedInventory.Builder sized() {
        return LayoutSizedInventory.builder();
    }

    static @NotNull LayoutPagedInventoryBuilder paged() {
        return new LayoutPagedInventoryBuilder();
    }

    static void init(final @NotNull JavaPlugin javaPlugin) {
        final PluginManager pluginManager = javaPlugin.getServer().getPluginManager();
        pluginManager.registerEvents(new InventoryClickListener(), javaPlugin);
        pluginManager.registerEvents(new InventoryOpenListener(), javaPlugin);
        pluginManager.registerEvents(new InventoryCloseListener(), javaPlugin);
    }

    Component title();

}
