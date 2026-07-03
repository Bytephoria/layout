package team.bytephoria.nms.adapter.v1_21_11;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import team.bytephoria.nms.packet.base.PacketLayoutBase;
import team.bytephoria.nms.packet.menu.PacketMenuFactory;
import team.bytephoria.nms.packet.render.PacketContainerKind;

public final class PacketMenuFactoryImpl implements PacketMenuFactory {

    @Override
    public @NotNull AbstractContainerMenu create(
            final @NonNull PacketContainerKind kind,
            final int containerId,
            final @NonNull Inventory playerInventory,
            final int containerSlots,
            final @NonNull PacketLayoutBase layout,
            final org.bukkit.entity.@NonNull Player bukkitPlayer
    ) {
        return new PacketLayoutMenu(MenuTypeConverter.toNms(kind), containerId, playerInventory, containerSlots, layout, bukkitPlayer);
    }
}
