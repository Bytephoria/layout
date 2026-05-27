package team.bytephoria.layout.layouts.types.paged;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.items.ItemSlot;
import team.bytephoria.layout.items.base.Item;
import team.bytephoria.layout.items.types.InteractiveItem;
import team.bytephoria.layout.layouts.builder.page.NavigationBuilder;

public record Navigation(
        Button previousItem,
        Button nextItem,
        boolean hiddenOnSinglePage,
        boolean hiddenOnFirstPage,
        boolean hiddenOnLastPage
) {

    public static @NotNull Navigation defaults() {
        return new Navigation(
                new Button(-1, null),
                new Button(-1, null),
                false,
                false,
                false
        );
    }

    public static @NotNull NavigationBuilder builder() {
        return new NavigationBuilder();
    }

    public void render(final @NotNull PagedLayoutView pagedLayoutView) {
        if (pagedLayoutView.totalPages() == 1 && this.hiddenOnSinglePage()) {
            return;
        }

        this.renderItem(pagedLayoutView, this.previousItem(), pagedLayoutView.isFirstPage(), this.hiddenOnFirstPage(), pagedLayoutView::previousPage);
        this.renderItem(pagedLayoutView, this.nextItem(), pagedLayoutView.isLastPage(), this.hiddenOnLastPage(), pagedLayoutView::nextPage);
    }

    private void renderItem(
            final @NotNull PagedLayoutView pagedLayoutView,
            final @NotNull Navigation.Button button,
            final boolean isEdgePage,
            final boolean hideOnEdgePage,
            final @NotNull Runnable action
    ) {
        if (button.slot() < 0 || button.item() == null) return;

        if (hideOnEdgePage) {
            if (isEdgePage) {
                pagedLayoutView.setItem(button.slot(), null);
                return;
            }

            pagedLayoutView.setItem(button.slot(), this.buildNavigationItem(button.item(), action));
            return;
        }

        if (!pagedLayoutView.isItemsLoaded()) {
            pagedLayoutView.setItem(button.slot(), this.buildNavigationItem(button.item(), action));
        }
    }

    private InteractiveItem buildNavigationItem(final @NotNull Item item, final @NotNull Runnable action) {
        return ItemSlot.interactive(item)
                .leftClick(ctx -> action.run())
                .build();
    }

    public record Button(int slot, @Nullable Item item) { }

}
