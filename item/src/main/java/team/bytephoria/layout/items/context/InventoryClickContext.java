package team.bytephoria.layout.items.context;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public record InventoryClickContext(
        Player player,
        InventoryClickEvent clickEvent
) {}