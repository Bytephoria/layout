package team.bytephoria.layout.items.builder;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.common.Builder;
import team.bytephoria.layout.items.ItemLayoutBase;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class ItemBuilderBase<B extends ItemBuilderBase<B, T>, T extends ItemLayoutBase>
        implements Builder<T> {

    protected Component displayName = null;
    protected List<Component> lore = null;
    protected Material material = Material.STONE;
    protected int amount = 1;
    protected Integer customModelData = null;

    protected abstract B self();

    @Contract("_, _ -> param1")
    public static <B extends ItemBuilderBase<B, ?>> @NotNull B fromItemStack(
            final @NotNull B builder,
            final @NotNull ItemStack itemStack
    ) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        return builder.material(itemStack.getType())
                .amount(itemStack.getAmount())
                .displayName(itemMeta.displayName())
                .lore(itemMeta.lore())
                .customModelData(itemMeta.getCustomModelData());
    }


    public B displayName(final @Nullable Component displayName) {
        this.displayName = displayName;
        return this.self();
    }

    public B lore(final @Nullable List<Component> lore) {
        this.lore = lore;
        return this.self();
    }

    public B lore(final @NotNull Component @NotNull ... lore) {
        this.lore = Arrays.stream(lore).toList();
        return this.self();
    }

    public B material(final @Nullable Material material) {
        this.material = Objects.requireNonNullElse(material, Material.STONE);
        return this.self();
    }

    public B amount(int amount) {
        this.amount = Math.max(1, amount);
        return this.self();
    }

    public B customModelData(Integer cmd) {
        this.customModelData = cmd;
        return this.self();
    }
}