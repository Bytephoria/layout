package team.bytephoria.layout.layouts.builder.page;

import team.bytephoria.layout.items.types.ItemLayout;

import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.layouts.behavior.Behavior;
import team.bytephoria.layout.layouts.builder.AbstractLayoutBuilder;
import team.bytephoria.layout.layouts.types.layout.LayoutPagedInventory;
import team.bytephoria.layout.layouts.types.paged.Navigation;
import team.bytephoria.layout.layouts.types.paged.Page;
import team.bytephoria.layout.layouts.types.paged.SlotRange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class LayoutPagedInventoryBuilder extends AbstractLayoutBuilder<LayoutPagedInventoryBuilder, LayoutPagedInventory> {

    protected List<ItemLayout> paginationItems = new ArrayList<>();
    protected Navigation navigation = Navigation.defaults();
    protected Behavior behavior = Behavior.defaults();
    protected SlotRange slotRange = SlotRange.empty();

    protected int initialPage = 0;
    protected int pageSize = -1;
    protected int size = 0;

    @Override
    protected LayoutPagedInventoryBuilder self() {
        return this;
    }

    public LayoutPagedInventoryBuilder size(final int size) {
        this.size = Math.clamp(size, 1, 6) * 9;
        return this.self();
    }

    public LayoutPagedInventoryBuilder range(final @NotNull SlotRange slotRange) {
        this.slotRange = slotRange;
        return this.self();
    }

    public LayoutPagedInventoryBuilder range(final @NotNull Consumer<SlotRangeBuilder> pagedBoundBuilderConsumer) {
        final SlotRangeBuilder slotRangeBuilder = SlotRange.builder();
        pagedBoundBuilderConsumer.accept(slotRangeBuilder);
        return this.range(slotRangeBuilder.build());
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

    public LayoutPagedInventoryBuilder navigation(final @NotNull Navigation navigation) {
        this.navigation = navigation;
        return this.self();
    }

    public LayoutPagedInventoryBuilder navigation(final @NotNull Consumer<NavigationBuilder> navigationConsumer) {
        final NavigationBuilder navigationBuilder = Navigation.builder();
        navigationConsumer.accept(navigationBuilder);
        return this.navigation(navigationBuilder.build());
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
    public LayoutPagedInventoryBuilder fill(final @NotNull ItemLayout itemLayout) {
        for (int slot = 0; slot < this.size; slot++) {
            final int index = Arrays.binarySearch(this.slotRange.slots(), slot);
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
        final int itemsPerPage = this.pageSize == -1 ? this.slotRange.size() : this.pageSize;
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
                this.behavior,
                this.itemLayouts,
                this.title,
                this.size,
                pages,
                this.slotRange,
                this.navigation,
                this.initialPage
        );
    }
}
