package team.bytephoria.layout.layouts.base;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class InventoryHolderBase implements InventoryHolder {

    private final Inventory inventory;
    private final Component title;

    public InventoryHolderBase(final @NotNull Component title, final int size) {
        this.inventory = Bukkit.createInventory(this, size, title);
        this.title = title;
    }

    public InventoryHolderBase(final @NotNull InventoryType inventoryType, final @NotNull Component title) {
        this.inventory = Bukkit.createInventory(this, inventoryType, title);
        this.title = title;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public final @NotNull Component title() {
        return this.title;
    }

    public final int slots() {
        return this.getInventory().getSize();
    }
}
