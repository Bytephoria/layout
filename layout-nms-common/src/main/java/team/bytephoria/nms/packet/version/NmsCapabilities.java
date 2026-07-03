package team.bytephoria.nms.packet.version;

import org.jetbrains.annotations.Contract;

/**
 * Optional helper for plugins that ship more than one {@code layout-nms-adapter-*} module and pick
 * between them at startup — see {@code PacketLayout#init}. A server only ever runs one NMS
 * generation, so this is a plain boolean probe, not a registry: the plugin decides what to do with
 * the answer, once, in its own {@code onEnable()}.
 *
 * <p>Probes for the class itself rather than parsing a version string, so it keeps working
 * correctly regardless of exactly which build of a given Minecraft version is running.
 */
public final class NmsCapabilities {

    private NmsCapabilities() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    /** {@code true} on Paper 26.1.2+, where {@code AbstractContainerMenu#clicked} takes a {@code ContainerInput}. */
    @Contract(pure = true)
    public static boolean hasContainerInputApi() {
        try {
            Class.forName("net.minecraft.world.inventory.ContainerInput");
            return true;
        } catch (final ClassNotFoundException e) {
            return false;
        }
    }
}
