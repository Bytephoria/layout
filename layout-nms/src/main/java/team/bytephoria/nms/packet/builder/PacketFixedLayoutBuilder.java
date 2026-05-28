package team.bytephoria.nms.packet.builder;

import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.types.ItemLayout;
import team.bytephoria.layout.layouts.builder.AbstractLayoutBuilder;
import team.bytephoria.nms.packet.render.PacketContainerType;
import team.bytephoria.nms.packet.types.PacketFixedLayout;

public final class PacketFixedLayoutBuilder extends AbstractLayoutBuilder<PacketFixedLayoutBuilder, PacketFixedLayout> {

    private PacketContainerType containerType = PacketContainerType.BARREL;

    public PacketFixedLayoutBuilder type(final @NotNull PacketContainerType containerType) {
        this.containerType = containerType;
        return this.self();
    }

    @Override
    public PacketFixedLayoutBuilder fill(final @NotNull ItemLayout itemLayout) {
        for (int slot = 0; slot < this.containerType.size(); slot++) {
            this.itemLayouts.put(slot, itemLayout);
        }
        return this.self();
    }

    @Override
    protected PacketFixedLayoutBuilder self() {
        return this;
    }

    @Override
    public @NotNull PacketFixedLayout build() {
        return new PacketFixedLayout(this.behavior, this.itemLayouts, this.title, this.containerType);
    }
}
