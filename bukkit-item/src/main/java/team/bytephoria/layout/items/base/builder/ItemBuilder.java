package team.bytephoria.layout.items.base.builder;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.common.Builder;
import team.bytephoria.layout.items.base.Item;

import java.util.Arrays;
import java.util.List;

public abstract class ItemBuilder<B extends ItemBuilder<B, T>, T extends Item>
        implements Builder<T> {

    protected Component displayName = null;
    protected List<Component> lore = null;
    protected int amount = 1;
    protected Integer customModelData = null;

    protected abstract B self();

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

    public B amount(int amount) {
        this.amount = Math.max(1, amount);
        return this.self();
    }

    public B customModelData(Integer cmd) {
        this.customModelData = cmd;
        return this.self();
    }
}