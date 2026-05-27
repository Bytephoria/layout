package team.bytephoria.nms.packet.base;

import net.minecraft.server.level.ServerPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.nms.packet.menu.PacketLayoutMenu;

public record PacketSession(Player player, ServerPlayer serverPlayer, PacketLayoutMenu menu) {

    public PacketSession(
            final @NotNull Player player,
            final @NotNull ServerPlayer serverPlayer,
            final @NotNull PacketLayoutMenu menu
    ) {
        this.player = player;
        this.serverPlayer = serverPlayer;
        this.menu = menu;
    }

    @Override
    public @NotNull Player player() {
        return this.player;
    }

    @Override
    public @NotNull ServerPlayer serverPlayer() {
        return this.serverPlayer;
    }

    @Override
    public @NotNull PacketLayoutMenu menu() {
        return this.menu;
    }

    public int windowId() {
        return this.menu.containerId;
    }
}
