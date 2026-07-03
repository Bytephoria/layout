package team.bytephoria.nms.packet.render;

import org.jetbrains.annotations.NotNull;

public enum PacketContainerType {

    DISPENSER(PacketContainerKind.DISPENSER, 9),
    DROPPER(PacketContainerKind.DROPPER, 9),
    FURNACE(PacketContainerKind.FURNACE, 3),
    WORKBENCH(PacketContainerKind.WORKBENCH, 10),
    ENCHANTING(PacketContainerKind.ENCHANTING, 2),
    BREWING(PacketContainerKind.BREWING, 5),
    ANVIL(PacketContainerKind.ANVIL, 3),
    SMITHING(PacketContainerKind.SMITHING, 4),
    BEACON(PacketContainerKind.BEACON, 1),
    HOPPER(PacketContainerKind.HOPPER, 5),
    SHULKER_BOX(PacketContainerKind.SHULKER_BOX, 27),
    BARREL(PacketContainerKind.GENERIC_9X3, 27),
    BLAST_FURNACE(PacketContainerKind.BLAST_FURNACE, 3),
    SMOKER(PacketContainerKind.SMOKER, 3),
    LOOM(PacketContainerKind.LOOM, 4),
    CARTOGRAPHY(PacketContainerKind.CARTOGRAPHY, 3),
    GRINDSTONE(PacketContainerKind.GRINDSTONE, 3),
    STONECUTTER(PacketContainerKind.STONECUTTER, 2);

    private final PacketContainerKind kind;
    private final int size;

    PacketContainerType(final @NotNull PacketContainerKind kind, final int size) {
        this.kind = kind;
        this.size = size;
    }

    public @NotNull PacketContainerKind kind() {
        return this.kind;
    }

    public int size() {
        return this.size;
    }
}
