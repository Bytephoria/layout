package team.bytephoria.nms.adapter.v26_1;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerInput;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.ItemClickType;

public final class ClickTypeConverter {

    private ClickTypeConverter() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    @Contract(pure = true)
    public static @NotNull ItemClickType fromNms(final @NotNull ContainerInput input, final int button) {
        return switch (input) {
            case PICKUP -> button == 0 ? ItemClickType.LEFT : ItemClickType.RIGHT;
            case QUICK_MOVE -> button == 0 ? ItemClickType.SHIFT_LEFT : ItemClickType.SHIFT_RIGHT;
            case SWAP -> button == Inventory.SLOT_OFFHAND ? ItemClickType.SWAP_OFFHAND : ItemClickType.NUMBER_KEY;
            case CLONE -> ItemClickType.MIDDLE;
            case THROW -> button == 1 ? ItemClickType.CONTROL_DROP : ItemClickType.DROP;
            case PICKUP_ALL -> ItemClickType.DOUBLE_CLICK;
            default -> ItemClickType.UNKNOWN;
        };
    }
}
