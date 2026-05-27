package team.bytephoria.layout.layouts.types.paged;

import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.items.types.ItemLayout;
import team.bytephoria.layout.layouts.editor.LayoutEditor;

public interface PagedLayoutView extends LayoutEditor {

    boolean isFirstPage();

    boolean isLastPage();

    int totalPages();

    boolean isItemsLoaded();

    void setItem(int slot, @Nullable ItemLayout itemLayout);

    void nextPage();

    void previousPage();
}
