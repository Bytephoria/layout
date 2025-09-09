package team.bytephoria.layout.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.items.builder.ItemBuilderBase;
import team.bytephoria.layout.items.builder.ItemBuilderClickable;
import team.bytephoria.layout.items.context.InventoryClickContext;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Consumer;

public class ClickableItemLayout extends ItemLayoutBase implements Executable {

    protected final EnumMap<ClickType, Consumer<InventoryClickContext>> clickActions;

    public ClickableItemLayout(
            final @Nullable Component displayName,
            final @Nullable List<Component> lore,
            final @NotNull Material material,
            final int amount,
            final @Nullable Integer customModelData,
            final @NotNull EnumMap<ClickType, Consumer<InventoryClickContext>> clickActions
    ) {
        super(displayName, lore, material, amount, customModelData);
        this.clickActions = clickActions;
    }

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public static @NotNull Builder of(final @NotNull Material material) {
        return new Builder(material);
    }

    public static @NotNull Builder of(final @NotNull ItemStack itemStack) {
        return ItemBuilderBase.fromItemStack(new Builder(), itemStack);
    }

    @Override
    public void execute(final @NotNull InventoryClickContext context) {
        final Consumer<InventoryClickContext> consumer = this.clickActions.get(context.clickEvent().getClick());
        if (consumer != null) {
            consumer.accept(context);
        }
    }

    public static class Builder extends ItemBuilderClickable<Builder, ClickableItemLayout> {

        public Builder() {
            // Keep default material
        }

        public Builder(final @NotNull Material material) {
            this.material = material;
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public ClickableItemLayout build() {
            return new ClickableItemLayout(
                    this.displayName,
                    this.lore,
                    this.material,
                    this.amount,
                    this.customModelData,
                    this.clickActions
            );
        }
    }
}
