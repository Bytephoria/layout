package team.bytephoria.layout.layouts;

import org.bukkit.event.inventory.InventoryType;

public enum FixedInventoryType {

    DISPENSER,
    DROPPER,
    FURNACE,
    WORKBENCH,
    ENCHANTING,
    BREWING,
    ENDER_CHEST,
    ANVIL,
    SMITHING,
    BEACON,
    HOPPER,
    SHULKER_BOX,
    BARREL,
    BLAST_FURNACE,
    LECTERN,
    SMOKER,
    LOOM,
    CARTOGRAPHY,
    GRINDSTONE,
    STONECUTTER,
    COMPOSTER
    ;

    public InventoryType toBukkitType() {
        return switch (this) {
            case DISPENSER -> InventoryType.DISPENSER;
            case DROPPER -> InventoryType.DROPPER;
            case FURNACE -> InventoryType.FURNACE;
            case WORKBENCH -> InventoryType.WORKBENCH;
            case ENCHANTING -> InventoryType.ENCHANTING;
            case BREWING -> InventoryType.BREWING;
            case ENDER_CHEST -> InventoryType.ENDER_CHEST;
            case ANVIL -> InventoryType.ANVIL;
            case SMITHING -> InventoryType.SMITHING;
            case BEACON -> InventoryType.BEACON;
            case HOPPER -> InventoryType.HOPPER;
            case SHULKER_BOX -> InventoryType.SHULKER_BOX;
            case BARREL -> InventoryType.BARREL;
            case BLAST_FURNACE -> InventoryType.BLAST_FURNACE;
            case LECTERN -> InventoryType.LECTERN;
            case SMOKER -> InventoryType.SMOKER;
            case LOOM -> InventoryType.LOOM;
            case CARTOGRAPHY -> InventoryType.CARTOGRAPHY;
            case GRINDSTONE -> InventoryType.GRINDSTONE;
            case STONECUTTER -> InventoryType.STONECUTTER;
            case COMPOSTER -> InventoryType.COMPOSTER;
        };
    }

}
