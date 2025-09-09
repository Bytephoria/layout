package team.bytephoria.layout.layouts.editor;

import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.ItemLayoutBase;

public interface LayoutEditor {

    void item(final int slot, final @NotNull ItemLayoutBase itemLayout);

    void fill(final @NotNull ItemLayoutBase itemLayout);
    void fillRange(final int from, final int to, final @NotNull ItemLayoutBase itemLayout);

    void update(final int slot);


}
