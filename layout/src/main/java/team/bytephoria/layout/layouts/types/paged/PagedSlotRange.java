package team.bytephoria.layout.layouts.types.paged;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.layouts.builder.page.PagedSlotRangeBuilder;

public record PagedSlotRange(int[] slots) {

    @Contract(" -> new")
    public static @NotNull PagedSlotRange empty() {
        return new PagedSlotRange(new int[0]);
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull PagedSlotRangeBuilder builder() {
        return new PagedSlotRangeBuilder();
    }

    public int size() {
        return this.slots.length;
    }

    public boolean isEmpty() {
        return this.slots.length == 0;
    }
}
