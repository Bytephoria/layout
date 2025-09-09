package team.bytephoria.layout.layouts.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.layouts.base.LayoutInventoryBase;

public final class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryCloseEvent(final @NotNull InventoryCloseEvent closeEvent) {
        final Inventory inventory = closeEvent.getInventory();
        if (inventory.getHolder(false) instanceof LayoutInventoryBase layoutInventoryBase) {
            layoutInventoryBase.onInventoryClose(closeEvent);
        }
    }

}
