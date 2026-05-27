package team.bytephoria.layout.layouts;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;

public interface InventoryListener {

    void onInventoryClick(final @NotNull InventoryClickEvent clickEvent);

    void onInventoryClose(final @NotNull InventoryCloseEvent closeEvent);

    void onInventoryOpen(final @NotNull InventoryOpenEvent openEvent);

}
