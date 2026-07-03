package team.bytephoria.nms.packet;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.nms.packet.base.PacketLayoutBase;
import team.bytephoria.nms.packet.builder.PacketFixedLayoutBuilder;
import team.bytephoria.nms.packet.builder.PacketSizedLayoutBuilder;
import team.bytephoria.nms.packet.builder.page.PacketPagedLayoutBuilder;
import team.bytephoria.nms.packet.menu.PacketMenuFactory;
import team.bytephoria.nms.packet.types.PacketFixedLayout;
import team.bytephoria.nms.packet.types.PacketPagedLayout;
import team.bytephoria.nms.packet.types.PacketSizedLayout;

public interface PacketLayout {

    @Contract(" -> new")
    static @NotNull PacketSizedLayoutBuilder sized() {
        return PacketSizedLayout.builder();
    }

    @Contract(" -> new")
    static @NotNull PacketFixedLayoutBuilder fixed() {
        return PacketFixedLayout.builder();
    }

    @Contract(" -> new")
    static @NotNull PacketPagedLayoutBuilder paged() {
        return PacketPagedLayout.builder();
    }

    /**
     * Wires this library up to the given plugin, using {@code menuFactory} to build every menu.
     * Pass a {@code new PacketMenuFactoryImpl()} from whichever {@code layout-nms-adapter-*}
     * module matches the Paper version this plugin targets.
     */
    static void init(final @NotNull JavaPlugin plugin, final @NotNull PacketMenuFactory menuFactory) {
        PacketLayoutBase.setPlugin(plugin);
        PacketLayoutBase.setMenuFactory(menuFactory);
    }
}
