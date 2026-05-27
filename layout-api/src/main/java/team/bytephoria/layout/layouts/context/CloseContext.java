package team.bytephoria.layout.layouts.context;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.Nullable;

public record CloseContext(Player player, @Nullable InventoryCloseEvent.Reason reason) {}