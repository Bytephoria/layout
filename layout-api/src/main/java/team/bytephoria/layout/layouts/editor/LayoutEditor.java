package team.bytephoria.layout.layouts.editor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.items.types.ItemLayout;

public interface LayoutEditor {

    void item(final int slot, final @Nullable ItemLayout itemLayout);

    void fill(final @NotNull ItemLayout itemLayout);

    void fillRange(final int from, final int to, final @NotNull ItemLayout itemLayout);

    void update(final int slot);

}
