package team.bytephoria.layout.items.base.builder;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.base.MaterialItem;

public class MaterialItemBuilder extends ItemBuilder<MaterialItemBuilder, MaterialItem> {

    private Material material = Material.STONE;

    public MaterialItemBuilder material(final @NotNull Material material) {
        this.material = material;
        return this;
    }

    @Override
    protected MaterialItemBuilder self() {
        return this;
    }

    @Override
    public MaterialItem build() {
        return new MaterialItem(
                this.displayName,
                this.lore,
                this.material,
                this.amount,
                this.customModelData
        );
    }

}
