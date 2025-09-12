package team.bytephoria.layout.layouts.builder.sized;

import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.common.Builder;
import team.bytephoria.layout.items.types.ItemLayout;

import java.util.Arrays;

public final class PatternLayoutBuilder implements Builder<Int2ObjectArrayMap<ItemLayout>> {

    private final Int2ObjectArrayMap<ItemLayout> layoutItems = new Int2ObjectArrayMap<>();
    private final Char2ObjectArrayMap<ItemLayout> keyMap = new Char2ObjectArrayMap<>();

    private String[] patternLines = new String[0];
    private int size = 1;

    private PatternLayoutBuilder() {}

    @Contract(" -> new")
    public static @NotNull PatternLayoutBuilder builder() {
        return new PatternLayoutBuilder();
    }

    public PatternLayoutBuilder pattern(final @NotNull String @NotNull ... patternLines) {
        this.patternLines = formatPattern(patternLines);
        return this;
    }

    public PatternLayoutBuilder item(final int slot, final @NotNull ItemLayout itemLayout) {
        this.layoutItems.put(slot, itemLayout);
        return this;
    }

    private @NotNull String @NotNull [] formatPattern(final @NotNull String @NotNull ... patternLines) {
        if (patternLines.length == 0) {
            return new String[0];
        }

        final int adjustedLength = Math.min(patternLines.length, 6);
        this.size = adjustedLength;
        return Arrays.copyOf(patternLines, adjustedLength);
    }

    public PatternLayoutBuilder key(final char key, final @NotNull ItemLayout itemLayout) {
        this.keyMap.put(key, itemLayout);
        return this;
    }

    public int size() {
        return this.size * 9;
    }

    @Override
    public @NotNull Int2ObjectArrayMap<ItemLayout> build() {
        int rowIndex = 0;
        for (final String patternLine : this.patternLines) {
            final int baseIndex = rowIndex * 9;
            for (int columnIndex = 0; columnIndex < patternLine.length(); columnIndex++) {
                final ItemLayout layout = this.keyMap.get(patternLine.charAt(columnIndex));
                if (layout != null) {
                    this.layoutItems.put(baseIndex + columnIndex, layout);
                }
            }

            rowIndex++;
        }

        return this.layoutItems;
    }
}
