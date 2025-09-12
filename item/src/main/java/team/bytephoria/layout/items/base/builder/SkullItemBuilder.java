package team.bytephoria.layout.items.base.builder;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.base.SkullItem;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.UUID;

public class SkullItemBuilder extends ItemBuilder<SkullItemBuilder, SkullItem> {

    private PlayerProfile playerProfile;

    @Override
    protected SkullItemBuilder self() {
        return this;
    }

    public SkullItemBuilder playerProfile(final @NotNull PlayerProfile playerProfile) {
        this.playerProfile = playerProfile;
        return this;
    }

    public SkullItemBuilder player(final @NotNull Player player) {
        this.playerProfile = player.getPlayerProfile();
        return this;
    }

    public SkullItemBuilder texture(final @NotNull String texture) {
        this.playerProfile = Bukkit.createProfile(UUID.randomUUID());
        this.playerProfile.setProperty(new ProfileProperty("textures", texture));
        return this;
    }

    public SkullItemBuilder skin(final @NotNull String skin) {
        this.playerProfile = Bukkit.createProfile(UUID.randomUUID());
        final PlayerTextures playerTextures = this.playerProfile.getTextures();
        final String url = "http://textures.minecraft.net/texture/";

        try {
            playerTextures.setSkin(URI.create(url + skin).toURL());
            this.playerProfile.setTextures(playerTextures);
        } catch (MalformedURLException ignored) {
        }

        return this;
    }

    @Override
    public SkullItem build() {
        return new SkullItem(
                this.displayName,
                this.lore,
                this.amount,
                this.customModelData,
                this.playerProfile
        );
    }

}
