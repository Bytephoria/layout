package team.bytephoria.layout.layouts.context;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public record InventoryCloseContext(Player player, InventoryCloseEvent.Reason reason) {}