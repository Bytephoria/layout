package team.bytephoria.layout.layouts.types.layout;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.items.Executable;
import team.bytephoria.layout.items.context.InventoryClickContext;
import team.bytephoria.layout.items.types.ItemLayout;
import team.bytephoria.layout.layouts.base.LayoutInventoryBase;
import team.bytephoria.layout.layouts.behavior.LayoutBehavior;
import team.bytephoria.layout.layouts.builder.page.LayoutPagedInventoryBuilder;
import team.bytephoria.layout.layouts.types.paged.Page;
import team.bytephoria.layout.layouts.types.paged.PagedNavigation;
import team.bytephoria.layout.layouts.types.paged.PagedSlotRange;

import java.util.Arrays;
import java.util.List;

public final class LayoutPagedInventory extends LayoutInventoryBase {

    private final List<Page> pages;
    private final PagedSlotRange pagedSlotRange;
    private final PagedNavigation pagedNavigation;
    private int currentPage;

    public LayoutPagedInventory(
            final @NotNull LayoutBehavior layoutBehavior,
            final @NotNull Int2ObjectArrayMap<ItemLayout> itemLayouts,
            final @NotNull Component title,
            final int size,
            final @NotNull List<Page> pages,
            final @NotNull PagedSlotRange pagedSlotRange,
            final @NotNull PagedNavigation pagedNavigation,
            final int initialPage
    ) {
        super(layoutBehavior, itemLayouts, title, size);
        this.pages = pages;
        this.pagedSlotRange = pagedSlotRange;
        this.pagedNavigation = pagedNavigation;
        this.currentPage = initialPage;
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull LayoutPagedInventoryBuilder builder() {
        return new LayoutPagedInventoryBuilder();
    }

    @Override
    protected void handleClick(final @NotNull InventoryClickContext inventoryClickContext) {
        final int clickedSlot = inventoryClickContext.clickEvent().getSlot();
        final int areaPosition = Arrays.binarySearch(this.pagedSlotRange.slots(), clickedSlot);
        final boolean isPaginationItem = areaPosition > -1;
        final ItemLayout itemLayout = isPaginationItem ? this.itemPage(areaPosition) :
                this.itemLayouts.get(clickedSlot);

        if (itemLayout instanceof Executable executable) {
            executable.execute(inventoryClickContext);
        }
    }

    @Override
    protected void renderItems() {
        this.currentPage().renderTo(this, this.pagedSlotRange);
        this.pagedNavigation.render(this);
        super.renderItems();
    }

    public void nextPage() {
        if (this.isLastPage()) {
            return;
        }

        this.currentPage++;
        this.currentPage().renderTo(this, this.pagedSlotRange);
        this.pagedNavigation.render(this);
    }

    public void previousPage() {
        if (this.isFirstPage()) {
            return;
        }

        this.currentPage--;
        this.currentPage().renderTo(this, this.pagedSlotRange);
        this.pagedNavigation.render(this);
    }

    private Page currentPage() {
        return this.pages.get(this.currentPage);
    }

    private @Nullable ItemLayout itemPage(final int itemIndex) {
        return this.currentPage().item(itemIndex);
    }

    public boolean isLastPage() {
        return this.currentPage == (this.pages.size() - 1);
    }

    public boolean isFirstPage() {
        return this.currentPage == 0;
    }

    public int totalPages() {
        return this.pages.size();
    }

}
