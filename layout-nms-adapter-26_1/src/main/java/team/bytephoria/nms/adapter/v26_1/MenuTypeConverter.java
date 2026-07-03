package team.bytephoria.nms.adapter.v26_1;

import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.nms.packet.render.PacketContainerKind;

final class MenuTypeConverter {

    private MenuTypeConverter() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    @Contract(pure = true)
    static @NotNull MenuType<?> toNms(final @NotNull PacketContainerKind kind) {
        return switch (kind) {
            case GENERIC_9X1 -> MenuType.GENERIC_9x1;
            case GENERIC_9X2 -> MenuType.GENERIC_9x2;
            case GENERIC_9X3 -> MenuType.GENERIC_9x3;
            case GENERIC_9X4 -> MenuType.GENERIC_9x4;
            case GENERIC_9X5 -> MenuType.GENERIC_9x5;
            case GENERIC_9X6 -> MenuType.GENERIC_9x6;
            case DISPENSER, DROPPER -> MenuType.GENERIC_3x3;
            case FURNACE -> MenuType.FURNACE;
            case WORKBENCH -> MenuType.CRAFTING;
            case ENCHANTING -> MenuType.ENCHANTMENT;
            case BREWING -> MenuType.BREWING_STAND;
            case ANVIL -> MenuType.ANVIL;
            case SMITHING -> MenuType.SMITHING;
            case BEACON -> MenuType.BEACON;
            case HOPPER -> MenuType.HOPPER;
            case SHULKER_BOX -> MenuType.SHULKER_BOX;
            case BLAST_FURNACE -> MenuType.BLAST_FURNACE;
            case SMOKER -> MenuType.SMOKER;
            case LOOM -> MenuType.LOOM;
            case CARTOGRAPHY -> MenuType.CARTOGRAPHY_TABLE;
            case GRINDSTONE -> MenuType.GRINDSTONE;
            case STONECUTTER -> MenuType.STONECUTTER;
        };
    }
}
