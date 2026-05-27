package team.bytephoria.nms.packet.types;

import team.bytephoria.layout.items.types.ItemLayout;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.kyori.adventure.text.Component;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.layouts.behavior.Behavior;
import team.bytephoria.nms.packet.base.PacketLayoutBase;
import team.bytephoria.nms.packet.builder.PacketSizedLayoutBuilder;

public final class PacketSizedLayout extends PacketLayoutBase {

    private final int rows;

    public PacketSizedLayout(
            final @NotNull Behavior behavior,
            final @NotNull Int2ObjectArrayMap<ItemLayout> itemLayouts,
            final @NotNull Component title,
            final int rows
    ) {
        super(behavior, itemLayouts, title);
        this.rows = rows;
    }

    @Contract(" -> new")
    public static @NotNull PacketSizedLayoutBuilder builder() {
        return new PacketSizedLayoutBuilder();
    }

    @Override
    protected @NotNull MenuType<?> menuType() {
        return switch (this.rows) {
            case 1 -> MenuType.GENERIC_9x1;
            case 2 -> MenuType.GENERIC_9x2;
            case 3 -> MenuType.GENERIC_9x3;
            case 4 -> MenuType.GENERIC_9x4;
            case 5 -> MenuType.GENERIC_9x5;
            default -> MenuType.GENERIC_9x6;
        };
    }

    @Override
    protected int size() {
        return this.rows * 9;
    }

    public int rows() {
        return this.rows;
    }
}
