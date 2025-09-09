package team.bytephoria.layout.items;

import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.context.InventoryClickContext;

public interface Executable {

    void execute(final @NotNull InventoryClickContext inventoryClickContext);

}
