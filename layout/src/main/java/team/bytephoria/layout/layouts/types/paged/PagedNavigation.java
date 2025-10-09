package team.bytephoria.layout.layouts.types.paged;

import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.base.Item;
import team.bytephoria.layout.items.base.MaterialItem;
import team.bytephoria.layout.items.types.ClickableItemLayout;
import team.bytephoria.layout.layouts.builder.page.PageNavigationBuilder;
import team.bytephoria.layout.layouts.types.layout.LayoutPagedInventory;

public record PagedNavigation(
        PagedNavigationItem backItem,
        PagedNavigationItem nextItem,
        boolean hideWhenSinglePage,
        boolean hideInFirstPage,
        boolean hideInLastPage
) {

    public static @NotNull PagedNavigation defaults() {
        return new PagedNavigation(
                new PagedNavigationItem(-1, new MaterialItem()),
                new PagedNavigationItem(-1, new MaterialItem()),
                false,
                false,
                false
        );
    }

    public static @NotNull PageNavigationBuilder builder() {
        return new PageNavigationBuilder();
    }

    public void render(final @NotNull LayoutPagedInventory layoutPagedInventory) {
        if (layoutPagedInventory.totalPages() == 1 && this.hideWhenSinglePage()) {
            return;
        }

        this.renderItem(layoutPagedInventory, this.backItem(), layoutPagedInventory.isFirstPage(), this.hideInFirstPage(), layoutPagedInventory::previousPage);
        this.renderItem(layoutPagedInventory, this.nextItem(), layoutPagedInventory.isLastPage(), this.hideInLastPage(), layoutPagedInventory::nextPage);
    }

    private void renderItem(
            final @NotNull LayoutPagedInventory layoutPagedInventory,
            final @NotNull PagedNavigationItem pagedNavigationItem,
            final boolean isEdgePage,
            final boolean hideOnEdgePage,
            final @NotNull Runnable action
    ) {
        if (hideOnEdgePage) {
            if (isEdgePage) {
                layoutPagedInventory.setItem(pagedNavigationItem.slot(), null);
                return;
            }

            final ClickableItemLayout clickable = ClickableItemLayout.builder()
                    .item(pagedNavigationItem.item())
                    .onLeftClick(ctx -> action.run())
                    .build();

            layoutPagedInventory.setItem(pagedNavigationItem.slot(), clickable);
            return;
        }

        if (!layoutPagedInventory.isItemsLoaded()) {
            layoutPagedInventory.setItem(pagedNavigationItem.slot(), this.buildNavigationItem(pagedNavigationItem.item(), action));
        }
    }

    private ClickableItemLayout buildNavigationItem(final @NotNull Item item, final @NotNull Runnable runnable) {
        return ClickableItemLayout.builder()
                .item(item)
                .onLeftClick(inventoryClickContext -> runnable.run())
                .build();
    }

    public record PagedNavigationItem(int slot, Item item) { }

}
