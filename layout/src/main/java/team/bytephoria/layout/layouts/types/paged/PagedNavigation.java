package team.bytephoria.layout.layouts.types.paged;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.items.base.Item;
import team.bytephoria.layout.items.base.MaterialItem;
import team.bytephoria.layout.items.context.InventoryClickContext;
import team.bytephoria.layout.items.types.ClickableItemLayout;
import team.bytephoria.layout.items.types.ItemLayout;
import team.bytephoria.layout.layouts.builder.page.PageNavigationBuilder;
import team.bytephoria.layout.layouts.types.layout.LayoutPagedInventory;

import java.util.function.Consumer;

public final class PagedNavigation {

    private final PagedNavigationItem backItem;
    private final PagedNavigationItem nextItem;
    private final boolean hideWhenSinglePage;
    private final boolean hideInFirstPage;
    private final boolean hideInLastPage;

    private ItemLayout capturedBackUnderlyingItem;
    private ItemLayout capturedNextUnderlyingItem;

    private boolean backCaptured = false;
    private boolean nextCaptured = false;

    public PagedNavigation(
            final PagedNavigationItem backItem,
            final PagedNavigationItem nextItem,
            final boolean hideWhenSinglePage,
            final boolean hideInFirstPage,
            final boolean hideInLastPage
    ) {
        this.backItem = backItem;
        this.nextItem = nextItem;
        this.hideWhenSinglePage = hideWhenSinglePage;
        this.hideInFirstPage = hideInFirstPage;
        this.hideInLastPage = hideInLastPage;
    }

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
        if (layoutPagedInventory.totalPages() == 1 && this.hideWhenSinglePage) {
            return;
        }

        this.renderItem(
                layoutPagedInventory,
                this.backItem,
                layoutPagedInventory.isFirstPage(),
                this.hideInFirstPage,
                layoutPagedInventory::previousPage,
                true
        );

        this.renderItem(
                layoutPagedInventory,
                this.nextItem,
                layoutPagedInventory.isLastPage(),
                this.hideInLastPage,
                layoutPagedInventory::nextPage,
                false
        );
    }

    private void renderItem(
            final @NotNull LayoutPagedInventory layoutPagedInventory,
            final @NotNull PagedNavigationItem pagedNavigationItem,
            final boolean isEdgePage,
            final boolean hideOnEdgePage,
            final @NotNull Runnable action,
            final boolean isBackSlot
    ) {
        final int slot = pagedNavigationItem.slot();

        if (hideOnEdgePage) {
            if (isBackSlot && !this.backCaptured) {
                if (layoutPagedInventory.isSlotTaken(slot)) {
                    this.capturedBackUnderlyingItem = layoutPagedInventory.getItem(slot);
                }

                this.backCaptured = true;
            } else if (!isBackSlot && !this.nextCaptured) {
                if (layoutPagedInventory.isSlotTaken(slot)) {
                    this.capturedNextUnderlyingItem = layoutPagedInventory.getItem(slot);
                }

                this.nextCaptured = true;
            }
        }

        if (hideOnEdgePage) {
            if (isEdgePage) {
                final ItemLayout underlying = isBackSlot
                        ? this.capturedBackUnderlyingItem
                        : this.capturedNextUnderlyingItem;

                layoutPagedInventory.setItem(slot, underlying);
                return;
            }

            layoutPagedInventory.setItem(
                    slot,
                    this.buildNavigationItem(pagedNavigationItem.item(), action, pagedNavigationItem.clickContextConsumer())
            );
            return;
        }

        if (!layoutPagedInventory.isItemsLoaded()) {
            layoutPagedInventory.setItem(
                    slot,
                    this.buildNavigationItem(pagedNavigationItem.item(), action, pagedNavigationItem.clickContextConsumer())
            );
        }
    }

    private ClickableItemLayout buildNavigationItem(
            final @NotNull Item item,
            final @NotNull Runnable runnable,
            final @Nullable Consumer<InventoryClickContext> anyInventoryClickContext
    ) {
        final ClickableItemLayout.ItemBuilder itemBuilder = ClickableItemLayout.builder()
                .item(item)
                .onAnyClick(ctx -> {
                    runnable.run();

                    if (anyInventoryClickContext != null) {
                        anyInventoryClickContext.accept(ctx);
                    }
                });

        return itemBuilder.build();
    }

    public record PagedNavigationItem(
            int slot,
            Item item,
            @Nullable Consumer<InventoryClickContext> clickContextConsumer
    ) {

        public PagedNavigationItem(final int slot, final Item item) {
            this(slot, item, null);
        }
    }
}