package team.bytephoria.nms.packet.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import team.bytephoria.nms.packet.base.PacketLayoutBase;
import team.bytephoria.nms.packet.render.PacketContainerKind;

/**
 * Builds the concrete {@link PacketMenu} implementation for a single NMS generation. Each adapter
 * module (e.g. {@code layout-nms-adapter-1_21_11}, {@code layout-nms-adapter-26_1}) ships exactly
 * one implementation of this interface, instantiated by class name via
 * {@code PacketLayout#init(JavaPlugin, String)} — never referenced directly by a consumer plugin,
 * so depending on this library never requires a paperweight dev bundle just to compile.
 */
public interface PacketMenuFactory {

    /** Creates the real container menu backing a visual-only {@code PacketLayout}. */
    @NotNull AbstractContainerMenu create(
            @NonNull PacketContainerKind kind,
            int containerId,
            @NonNull Inventory playerInventory,
            int containerSlots,
            @NonNull PacketLayoutBase layout,
            org.bukkit.entity.@NonNull Player bukkitPlayer
    );
}
