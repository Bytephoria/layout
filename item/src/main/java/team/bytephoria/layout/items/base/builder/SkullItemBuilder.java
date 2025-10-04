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

    /**
     * Sets a custom {@link PlayerProfile} for this skull.
     *
     * @param playerProfile the player profile to use
     * @return this builder
     */
    public SkullItemBuilder withPlayerProfile(final @NotNull PlayerProfile playerProfile) {
        this.playerProfile = playerProfile;
        return this;
    }

    /**
     * Sets the skull texture using the profile of a specific player.
     *
     * @param player the player whose profile will be used
     * @return this builder
     */
    public SkullItemBuilder fromPlayer(final @NotNull Player player) {
        this.playerProfile = player.getPlayerProfile();
        return this;
    }

    /**
     * Sets the skull texture using a Base64-encoded texture value.
     * <p>
     * Example:
     * <pre>{@code
     * builder.withTextureValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTc4ZDNjYmE0MWRj...");
     * }</pre>
     *
     * @param base64Texture the Base64 texture value (the full VALUE property)
     * @return this builder
     */
    public SkullItemBuilder withTextureValue(final @NotNull String base64Texture) {
        this.playerProfile = Bukkit.createProfile(UUID.randomUUID());
        this.playerProfile.setProperty(new ProfileProperty("textures", base64Texture));
        return this;
    }

    /**
     * Sets the skull skin using a Mojang texture ID.
     * <p>
     * This ID corresponds to the last part of the Mojang texture URL:
     * <pre><a href="http://textures.minecraft.net/texture/&lt;skinId&gt">https://textures.minecraft.net/texture/...</a>;</pre>
     * Example:
     * <pre>{@code
     * builder.withSkinId("978d3cba41dc11eba418d8c67d64db6cb179bc6abfce7ad0703fb4d7369d689a");
     * }</pre>
     *
     * @param skinId the texture ID from Mojang’s texture server
     * @return this builder
     */
    public SkullItemBuilder withSkinId(final @NotNull String skinId) {
        this.playerProfile = Bukkit.createProfile(UUID.randomUUID());
        final PlayerTextures playerTextures = this.playerProfile.getTextures();
        final String url = "http://textures.minecraft.net/texture/";

        try {
            playerTextures.setSkin(URI.create(url + skinId).toURL());
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
