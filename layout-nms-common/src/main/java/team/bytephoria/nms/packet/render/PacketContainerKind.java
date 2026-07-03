package team.bytephoria.nms.packet.render;

/**
 * NMS-free identifier for a container shape. {@code layout-nms-common}'s public API (and anything
 * a consumer plugin touches directly) uses this instead of the real {@code net.minecraft.world.
 * inventory.MenuType}, so depending on this library never requires a paperweight dev bundle just to
 * compile. Each {@code layout-nms-adapter-*} module maps this to the real {@code MenuType} for its
 * NMS generation internally.
 */
public enum PacketContainerKind {
    GENERIC_9X1,
    GENERIC_9X2,
    GENERIC_9X3,
    GENERIC_9X4,
    GENERIC_9X5,
    GENERIC_9X6,
    DISPENSER,
    DROPPER,
    FURNACE,
    WORKBENCH,
    ENCHANTING,
    BREWING,
    ANVIL,
    SMITHING,
    BEACON,
    HOPPER,
    SHULKER_BOX,
    BLAST_FURNACE,
    SMOKER,
    LOOM,
    CARTOGRAPHY,
    GRINDSTONE,
    STONECUTTER
}
