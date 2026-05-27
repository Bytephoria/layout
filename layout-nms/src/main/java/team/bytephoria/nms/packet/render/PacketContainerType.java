package team.bytephoria.nms.packet.render;

import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.NotNull;

public enum PacketContainerType {

    DISPENSER(MenuType.GENERIC_3x3, 9),
    DROPPER(MenuType.GENERIC_3x3, 9),
    FURNACE(MenuType.FURNACE, 3),
    WORKBENCH(MenuType.CRAFTING, 10),
    ENCHANTING(MenuType.ENCHANTMENT, 2),
    BREWING(MenuType.BREWING_STAND, 5),
    ANVIL(MenuType.ANVIL, 3),
    SMITHING(MenuType.SMITHING, 4),
    BEACON(MenuType.BEACON, 1),
    HOPPER(MenuType.HOPPER, 5),
    SHULKER_BOX(MenuType.SHULKER_BOX, 27),
    BARREL(MenuType.GENERIC_9x3, 27),
    BLAST_FURNACE(MenuType.BLAST_FURNACE, 3),
    SMOKER(MenuType.SMOKER, 3),
    LOOM(MenuType.LOOM, 4),
    CARTOGRAPHY(MenuType.CARTOGRAPHY_TABLE, 3),
    GRINDSTONE(MenuType.GRINDSTONE, 3),
    STONECUTTER(MenuType.STONECUTTER, 2);

    private final MenuType<?> menuType;
    private final int size;

    PacketContainerType(final @NotNull MenuType<?> menuType, final int size) {
        this.menuType = menuType;
        this.size = size;
    }

    public @NotNull MenuType<?> toMenuType() {
        return this.menuType;
    }

    public int size() {
        return this.size;
    }
}
