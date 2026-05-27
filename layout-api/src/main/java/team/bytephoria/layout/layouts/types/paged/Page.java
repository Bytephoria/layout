package team.bytephoria.layout.layouts.types.paged;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.items.types.ItemLayout;
import team.bytephoria.layout.layouts.editor.LayoutEditor;

import java.util.List;

public record Page(List<ItemLayout> items) {

    public int totalItems() {
        return this.items.size();
    }

    public @Nullable ItemLayout item(final int index) {
        return (index < 0 || index >= this.totalItems()) ? null : this.items.get(index);
    }

    public void renderTo(final @NotNull LayoutEditor editor, final @NotNull SlotRange slotRange) {
        final int[] slots = slotRange.slots();
        for (int index = 0; index < slots.length; index++) {
            editor.item(slots[index], this.item(index));
        }
    }

}
