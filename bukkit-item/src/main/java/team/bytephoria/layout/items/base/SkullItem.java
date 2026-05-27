package team.bytephoria.layout.items.base;

import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.base.builder.SkullItemBuilder;

import java.util.Collections;
import java.util.List;

public class SkullItem extends Item {

    private final PlayerProfile playerProfile;

    public SkullItem(
            final Component displayName,
            final List<Component> lore,
            final int amount,
            final Integer customModelData,
            final PlayerProfile playerProfile
    ) {
        super(displayName, lore, amount, customModelData);
        this.playerProfile = playerProfile;
    }

    public SkullItem(
            final Component displayName,
            final int amount,
            final PlayerProfile playerProfile
    ) {
        this(displayName, Collections.emptyList(), amount, null, playerProfile);
    }

    public SkullItem(final Component displayName, final PlayerProfile playerProfile) {
        this(displayName, Collections.emptyList(), 1, null, playerProfile);
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull SkullItemBuilder builder() {
        return new SkullItemBuilder();
    }

    @Override
    public Material material() {
        return Material.PLAYER_HEAD;
    }

    public PlayerProfile playerProfile() {
        return this.playerProfile;
    }

    @Override
    public ItemStack to() {
        final ItemStack itemStack = new ItemStack(this.material(), this.amount);
        final SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        if (this.displayName != null) {
            skullMeta.displayName(this.displayName);
        }

        if (this.lore != null) {
            skullMeta.lore(this.lore);
        }

        if (this.customModelData != null) {
            skullMeta.setCustomModelData(this.customModelData);
        }

        if (this.playerProfile != null) {
            skullMeta.setPlayerProfile(this.playerProfile);
        }

        itemStack.setItemMeta(skullMeta);
        return itemStack;
    }
}
