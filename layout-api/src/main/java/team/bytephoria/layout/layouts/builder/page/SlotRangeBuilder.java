package team.bytephoria.layout.layouts.builder.page;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.common.Builder;
import team.bytephoria.layout.layouts.types.paged.SlotRange;

public class SlotRangeBuilder implements Builder<SlotRange> {

    private int start = 0;
    private int end = 0;

    private boolean rectangular = false;

    public SlotRangeBuilder start(final int start) {
        this.start = Math.clamp(start, 0, 53);
        return this;
    }

    public SlotRangeBuilder end(final int end) {
        this.end = Math.clamp(end, 0, 53);
        return this;
    }

    public SlotRangeBuilder rectangular() {
        this.rectangular = true;
        return this;
    }

    @Override
    public SlotRange build() {
        if (this.start == 0 && this.end == 0) {
            return SlotRange.empty();
        }

        int start = this.start;
        int end = this.end;
        if (start > end) {
            final int temporal = start;
            start = end;
            end = temporal;
        }

        if (!this.rectangular) {
            return this.buildContiguousArea(start, end);
        }

        final int columns = 9;
        final int startRow = start / columns;
        final int startColumn = start % columns;
        final int availableColumns = columns - startColumn;

        int bestWidth = 0;
        int bestRows = 0;
        int bestArea = 0;

        for (int width = 1; width <= availableColumns; width++) {
            final int endColumn = startColumn + width - 1;
            final int lastRowAllowed = (end - endColumn) / columns;
            final int rows = lastRowAllowed - startRow + 1;
            if (rows <= 0) {
                break;
            }

            final int area = rows * width;
            if (area > bestArea || (area == bestArea && width > bestWidth)) {
                bestArea = area;
                bestWidth = width;
                bestRows = rows;
            }
        }

        return this.buildRectangularArea(startRow, startColumn, bestWidth, bestRows);
    }

    @Contract("_, _ -> new")
    private @NotNull SlotRange buildContiguousArea(final int start, final int end) {
        if (start > end) {
            return SlotRange.empty();
        }

        final int size = end - start + 1;
        final int[] slots = new int[size];
        for (int index = 0; index < size; index++) {
            slots[index] = start + index;
        }

        return new SlotRange(slots);
    }

    @Contract("_, _, _, _ -> new")
    private @NotNull SlotRange buildRectangularArea(
            final int startRow,
            final int startColumn,
            final int width,
            final int rows
    ) {
        final int columns = 9;
        final int totalSlots = width * rows;
        final int[] slots = new int[totalSlots];
        int index = 0;

        for (int row = 0; row < rows; row++) {
            final int currentRow = startRow + row;
            final int rowBase = currentRow * columns;
            for (int w = 0; w < width; w++) {
                slots[index++] = rowBase + startColumn + w;
            }
        }

        return new SlotRange(slots);
    }
}
