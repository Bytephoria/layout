package team.bytephoria.nms.packet.types;

import team.bytephoria.layout.items.types.ItemLayout;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.layouts.behavior.Behavior;
import team.bytephoria.nms.packet.base.PacketLayoutBase;
import team.bytephoria.nms.packet.builder.PacketSizedLayoutBuilder;
import team.bytephoria.nms.packet.render.PacketContainerKind;

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
    protected @NotNull PacketContainerKind containerKind() {
        return switch (this.rows) {
            case 1 -> PacketContainerKind.GENERIC_9X1;
            case 2 -> PacketContainerKind.GENERIC_9X2;
            case 3 -> PacketContainerKind.GENERIC_9X3;
            case 4 -> PacketContainerKind.GENERIC_9X4;
            case 5 -> PacketContainerKind.GENERIC_9X5;
            default -> PacketContainerKind.GENERIC_9X6;
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
