package team.bytephoria.nms.packet.types;

import team.bytephoria.layout.items.types.ItemLayout;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.kyori.adventure.text.Component;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.layouts.behavior.Behavior;
import team.bytephoria.nms.packet.base.PacketLayoutBase;
import team.bytephoria.nms.packet.builder.PacketFixedLayoutBuilder;
import team.bytephoria.nms.packet.render.PacketContainerType;

public final class PacketFixedLayout extends PacketLayoutBase {

    private final PacketContainerType containerType;

    public PacketFixedLayout(
            final @NotNull Behavior behavior,
            final @NotNull Int2ObjectArrayMap<ItemLayout> itemLayouts,
            final @NotNull Component title,
            final @NotNull PacketContainerType containerType
    ) {
        super(behavior, itemLayouts, title);
        this.containerType = containerType;
    }

    @Contract(" -> new")
    public static @NotNull PacketFixedLayoutBuilder builder() {
        return new PacketFixedLayoutBuilder();
    }

    @Override
    protected @NotNull MenuType<?> menuType() {
        return this.containerType.toMenuType();
    }

    @Override
    protected int size() {
        return this.containerType.size();
    }

    public @NotNull PacketContainerType containerType() {
        return this.containerType;
    }
}
