package team.bytephoria.layout.layouts.types.layout;

import team.bytephoria.layout.items.types.ItemLayout;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.items.Executable;
import team.bytephoria.layout.items.context.ClickContext;
import team.bytephoria.layout.layouts.base.LayoutInventoryBase;
import team.bytephoria.layout.layouts.behavior.Behavior;
import team.bytephoria.layout.layouts.builder.page.LayoutPagedInventoryBuilder;
import team.bytephoria.layout.layouts.types.paged.Navigation;
import team.bytephoria.layout.layouts.types.paged.Page;
import team.bytephoria.layout.layouts.types.paged.PagedLayoutView;
import team.bytephoria.layout.layouts.types.paged.SlotRange;

import java.util.Arrays;
import java.util.List;

public final class LayoutPagedInventory extends LayoutInventoryBase implements PagedLayoutView {

    private final List<Page> pages;
    private final SlotRange slotRange;
    private final Navigation navigation;
    private int currentPage;

    public LayoutPagedInventory(
            final @NotNull Behavior behavior,
            final @NotNull Int2ObjectArrayMap<ItemLayout> itemLayouts,
            final @NotNull Component title,
            final int size,
            final @NotNull List<Page> pages,
            final @NotNull SlotRange slotRange,
            final @NotNull Navigation navigation,
            final int initialPage
    ) {
        super(behavior, itemLayouts, title, size);
        this.pages = pages;
        this.slotRange = slotRange;
        this.navigation = navigation;
        this.currentPage = initialPage;
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull LayoutPagedInventoryBuilder builder() {
        return new LayoutPagedInventoryBuilder();
    }

    @Override
    protected void handleClick(final @NotNull ClickContext clickContext) {
        final int clickedSlot = clickContext.slot();
        final int areaPosition = Arrays.binarySearch(this.slotRange.slots(), clickedSlot);
        final ItemLayout itemLayout = areaPosition > -1
                ? this.itemPage(areaPosition)
                : this.itemLayouts.get(clickedSlot);

        if (itemLayout instanceof Executable executable) {
            executable.execute(clickContext);
        }
    }

    @Override
    protected void renderItems() {
        this.currentPage().renderTo(this, this.slotRange);
        this.navigation.render(this);
        super.renderItems();
    }

    public void nextPage() {
        if (this.isLastPage()) {
            return;
        }

        this.currentPage++;
        this.currentPage().renderTo(this, this.slotRange);
        this.navigation.render(this);
    }

    public void previousPage() {
        if (this.isFirstPage()) {
            return;
        }

        this.currentPage--;
        this.currentPage().renderTo(this, this.slotRange);
        this.navigation.render(this);
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
