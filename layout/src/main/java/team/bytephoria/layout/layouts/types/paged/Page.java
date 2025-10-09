package team.bytephoria.layout.layouts.types.paged;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.items.types.ItemLayout;
import team.bytephoria.layout.layouts.types.layout.LayoutPagedInventory;

import java.util.List;

public record Page(List<ItemLayout> items) {

    public int totalItems() {
        return this.items.size();
    }

    public @Nullable ItemLayout item(final int index) {
        return (index < 0 || index >= this.totalItems()) ? null : this.items.get(index);
    }

    public void renderTo(final @NotNull LayoutPagedInventory layoutPagedInventory, final @NotNull PagedSlotRange pagedSlotRange) {
        final int[] slots = pagedSlotRange.slots();
        for (int index = 0; index < slots.length; index++) {
            layoutPagedInventory.item(slots[index], this.item(index));
        }
    }

}
