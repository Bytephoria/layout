package team.bytephoria.layout.items.types;

import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.base.Item;

public class ItemLayout {

    private final Item item;
    public ItemLayout(final @NotNull Item item) {
        this.item = item;
    }

    public Item itemLayoutBase() {
        return this.item;
    }

}
