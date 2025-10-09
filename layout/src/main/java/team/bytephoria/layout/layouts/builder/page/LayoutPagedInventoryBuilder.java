package team.bytephoria.layout.layouts.builder.page;

import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.types.ItemLayout;
import team.bytephoria.layout.layouts.behavior.LayoutBehavior;
import team.bytephoria.layout.layouts.builder.AbstractLayoutBuilder;
import team.bytephoria.layout.layouts.types.layout.LayoutPagedInventory;
import team.bytephoria.layout.layouts.types.paged.PagedNavigation;
import team.bytephoria.layout.layouts.types.paged.Page;
import team.bytephoria.layout.layouts.types.paged.PagedSlotRange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class LayoutPagedInventoryBuilder extends AbstractLayoutBuilder<LayoutPagedInventoryBuilder, LayoutPagedInventory> {

    protected List<ItemLayout> paginationItems = new ArrayList<>();
    protected PagedNavigation pagedNavigation = PagedNavigation.defaults();
    protected LayoutBehavior layoutBehavior = LayoutBehavior.defaults();
    protected PagedSlotRange pagedSlotRange = PagedSlotRange.empty();

    protected int initialPage = 0;
    protected int pageSize = -1;
    protected int size = 0;

    @Override
    protected LayoutPagedInventoryBuilder self() {
        return this;
    }

    public LayoutPagedInventoryBuilder size(final int size) {
        this.size = Math.max(1, Math.min(size, 6)) * 9;
        return this.self();
    }

    public LayoutPagedInventoryBuilder range(final @NotNull PagedSlotRange pagedSlotRange) {
        this.pagedSlotRange = pagedSlotRange;
        return this.self();
    }

    public LayoutPagedInventoryBuilder range(final @NotNull Consumer<PagedSlotRangeBuilder> pagedBoundBuilderConsumer) {
        final PagedSlotRangeBuilder pagedSlotRangeBuilder = PagedSlotRange.builder();
        pagedBoundBuilderConsumer.accept(pagedSlotRangeBuilder);
        return this.range(pagedSlotRangeBuilder.build());
    }

    public LayoutPagedInventoryBuilder extend(final @NotNull Collection<ItemLayout> itemLayouts) {
        this.paginationItems.addAll(itemLayouts);
        return this.self();
    }

    public LayoutPagedInventoryBuilder extend(final @NotNull ItemLayout @NotNull ... itemLayouts) {
        return this.extend(List.of(itemLayouts));
    }

    public LayoutPagedInventoryBuilder append(final @NotNull ItemLayout itemLayout) {
        this.paginationItems.add(itemLayout);
        return this.self();
    }

    public LayoutPagedInventoryBuilder navigation(final @NotNull PagedNavigation pagedNavigation) {
        this.pagedNavigation = pagedNavigation;
        return this.self();
    }

    public LayoutPagedInventoryBuilder navigation(final @NotNull Consumer<PageNavigationBuilder> navigationConsumer) {
        final PageNavigationBuilder pageNavigationBuilder = PagedNavigation.builder();
        navigationConsumer.accept(pageNavigationBuilder);
        return this.navigation(pageNavigationBuilder.build());
    }

    public LayoutPagedInventoryBuilder pageSize(final int pageSize) {
        this.pageSize = pageSize;
        return this.self();
    }

    public LayoutPagedInventoryBuilder page(final int page) {
        this.initialPage = page;
        return this.self();
    }

    @Override
    public LayoutPagedInventoryBuilder fillAll(final @NotNull ItemLayout itemLayout) {
        for (int slot = 0; slot < this.size; slot++) {
            final int index = Arrays.binarySearch(this.pagedSlotRange.slots(), slot);
            if (index > -1) {
                continue;
            }

            this.item(slot, itemLayout);
        }

        return this.self();
    }

    @Override
    public LayoutPagedInventory build() {
        final int totalItems = this.paginationItems.size();
        final int itemsPerPage = this.pageSize == -1 ? this.pagedSlotRange.size() : this.pageSize;
        final int totalPages = (totalItems + (itemsPerPage - 1)) / itemsPerPage;
        final List<Page> pages = new ArrayList<>(totalPages);

        for (int currentPageId = 0; currentPageId < totalPages; currentPageId++) {
            final int initialItemIndex = currentPageId * itemsPerPage;
            final int finalItemIndex = Math.min(initialItemIndex + itemsPerPage, totalItems);

            final List<ItemLayout> itemsPage = new ArrayList<>(finalItemIndex - initialItemIndex);
            for (int itemIndex = initialItemIndex; itemIndex < finalItemIndex; itemIndex++) {
                itemsPage.add(this.paginationItems.get(itemIndex));
            }

            pages.add(new Page(itemsPage));
        }

        return new LayoutPagedInventory(
                this.layoutBehavior,
                this.itemLayouts,
                this.title,
                this.size,
                pages,
                this.pagedSlotRange,
                this.pagedNavigation,
                this.initialPage
        );
    }
}
