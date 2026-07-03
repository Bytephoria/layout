package team.bytephoria.nms.packet.menu;

/**
 * Version-agnostic surface of the real {@code AbstractContainerMenu} backing a {@code PacketLayout}.
 * Implemented once per NMS adapter module (see {@code PacketMenuFactory}) so that this shared module
 * never has to reference a concrete, adapter-specific menu class directly.
 */
public interface PacketMenu {

    /** Rebuilds the visual slot contents from the layout's current items. */
    void refreshContent();

    /** Forwards to {@code AbstractContainerMenu#broadcastChanges}. */
    void broadcastChanges();

    /** Forwards to {@code AbstractContainerMenu#sendAllDataToRemote}. */
    void sendAllDataToRemote();

    /** The window id the client knows this menu as. */
    int containerId();
}
