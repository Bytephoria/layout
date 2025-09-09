package team.bytephoria.layout.layouts.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.layouts.base.LayoutInventoryBase;

public final class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClickEvent(final @NotNull InventoryClickEvent clickEvent) {
        final Inventory inventory = clickEvent.getInventory();
        if (inventory.getHolder(false) instanceof LayoutInventoryBase layoutInventoryBase) {
            layoutInventoryBase.onInventoryClick(clickEvent);
        }
    }
}
