package team.bytephoria.layout.layouts.types.paged;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.layouts.builder.page.SlotRangeBuilder;

public record SlotRange(int[] slots) {

    @Contract(" -> new")
    public static @NotNull SlotRange empty() {
        return new SlotRange(new int[0]);
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull SlotRangeBuilder builder() {
        return new SlotRangeBuilder();
    }

    public int size() {
        return this.slots.length;
    }

    public boolean isEmpty() {
        return this.slots.length == 0;
    }
}
