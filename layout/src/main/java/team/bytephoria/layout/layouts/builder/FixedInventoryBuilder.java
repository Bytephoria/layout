package team.bytephoria.layout.layouts.builder;

import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.ItemLayoutBase;
import team.bytephoria.layout.layouts.FixedInventoryType;
import team.bytephoria.layout.layouts.base.InventoryHolderBase;

public abstract class FixedInventoryBuilder<B extends AbstractLayoutBuilder<B, O>, O extends InventoryHolderBase>
        extends AbstractLayoutBuilder<B, O> {

    protected InventoryType type = InventoryType.WORKBENCH;

    public B type(final @NotNull FixedInventoryType fixedInventoryType) {
        this.type = fixedInventoryType.toBukkitType();
        return this.self();
    }

    @Override
    public B fillAll(final @NotNull ItemLayoutBase itemLayoutBase) {
        for (int slot = 0; slot < this.type.getDefaultSize(); slot++) {
            this.item(slot, itemLayoutBase);
        }

        return this.self();
    }
}
