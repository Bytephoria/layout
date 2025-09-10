package team.bytephoria.layout.layouts.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.layouts.base.LayoutInventoryBase;

public final class InventoryOpenListener implements Listener {

    @EventHandler
    public void onInventoryOpenEvent(final @NotNull InventoryOpenEvent openEvent) {
        if (openEvent.getInventory().getHolder() instanceof LayoutInventoryBase layoutInventoryBase) {
            layoutInventoryBase.onInventoryOpen(openEvent);
        }
    }

}
