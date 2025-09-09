package team.bytephoria.layout.layouts.editor;

import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.ItemLayoutBase;

public interface LayoutSizedEditor {

    void column(final int column, final @NotNull ItemLayoutBase itemLayout);

    void row(final int row, final @NotNull ItemLayoutBase itemLayout);

    void border(final @NotNull ItemLayoutBase itemLayout);

}
