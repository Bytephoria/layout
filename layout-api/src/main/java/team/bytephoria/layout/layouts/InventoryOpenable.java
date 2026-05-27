package team.bytephoria.layout.layouts;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface InventoryOpenable {

    void open(final @NotNull Player player);
    void open(final @NotNull Player @NotNull ... players);

}
