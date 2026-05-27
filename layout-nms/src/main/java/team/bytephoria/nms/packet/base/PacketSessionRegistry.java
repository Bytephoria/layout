package team.bytephoria.nms.packet.base;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class PacketSessionRegistry {

    private final Map<UUID, PacketSession> sessions = new ConcurrentHashMap<>();

    public void register(final @NotNull PacketSession session) {
        this.sessions.put(session.player().getUniqueId(), session);
    }

    public @Nullable PacketSession get(final @NotNull Player player) {
        return this.sessions.get(player.getUniqueId());
    }

    public void remove(final @NotNull Player player) {
        this.sessions.remove(player.getUniqueId());
    }

    public boolean has(final @NotNull Player player) {
        return this.sessions.containsKey(player.getUniqueId());
    }

    public @NotNull Collection<PacketSession> sessions() {
        return this.sessions.values();
    }
}
