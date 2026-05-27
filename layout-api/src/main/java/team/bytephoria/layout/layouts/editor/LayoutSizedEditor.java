package team.bytephoria.layout.layouts.editor;

import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.types.ItemLayout;

public interface LayoutSizedEditor {

    void column(final int column, final @NotNull ItemLayout itemLayout);

    void row(final int row, final @NotNull ItemLayout itemLayout);

    void border(final @NotNull ItemLayout itemLayout);

}
