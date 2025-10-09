package team.bytephoria.layout.layouts.builder.page;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.common.Builder;
import team.bytephoria.layout.items.base.Item;
import team.bytephoria.layout.layouts.types.paged.PagedNavigation;

public final class PageNavigationBuilder implements Builder<PagedNavigation> {

    private PagedNavigation.PagedNavigationItem previousButton;
    private PagedNavigation.PagedNavigationItem nextButton;

    private boolean hiddenOnSinglePage = false;
    private boolean hiddenOnFirstPage = false;
    private boolean hiddenOnLastPage = false;

    public PageNavigationBuilder previous(final int slot, final @NotNull Item item) {
        this.previousButton = new PagedNavigation.PagedNavigationItem(slot, item);
        return this;
    }

    public PageNavigationBuilder next(final int slot, final @NotNull Item item) {
        this.nextButton = new PagedNavigation.PagedNavigationItem(slot, item);
        return this;
    }

    public PageNavigationBuilder hideOnSinglePage() {
        this.hiddenOnSinglePage = true;
        return this;
    }

    public PageNavigationBuilder hideOnFirstPage() {
        this.hiddenOnFirstPage = true;
        return this;
    }

    public PageNavigationBuilder hideOnLastPage() {
        this.hiddenOnLastPage = true;
        return this;
    }

    @Contract(" -> new")
    @Override
    public @NotNull PagedNavigation build() {
        return new PagedNavigation(
                this.previousButton,
                this.nextButton,
                this.hiddenOnSinglePage,
                this.hiddenOnFirstPage,
                this.hiddenOnLastPage
        );
    }
}