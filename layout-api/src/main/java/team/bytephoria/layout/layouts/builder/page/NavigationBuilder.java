package team.bytephoria.layout.layouts.builder.page;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.common.Builder;
import team.bytephoria.layout.items.base.Item;
import team.bytephoria.layout.layouts.types.paged.Navigation;

public final class NavigationBuilder implements Builder<Navigation> {

    private Navigation.Button previousButton;
    private Navigation.Button nextButton;

    private boolean hiddenOnSinglePage = false;
    private boolean hiddenOnFirstPage = false;
    private boolean hiddenOnLastPage = false;

    public NavigationBuilder previous(final int slot, final @NotNull Item item) {
        this.previousButton = new Navigation.Button(slot, item);
        return this;
    }

    public NavigationBuilder next(final int slot, final @NotNull Item item) {
        this.nextButton = new Navigation.Button(slot, item);
        return this;
    }

    public NavigationBuilder hideOnSinglePage() {
        this.hiddenOnSinglePage = true;
        return this;
    }

    public NavigationBuilder hideOnFirstPage() {
        this.hiddenOnFirstPage = true;
        return this;
    }

    public NavigationBuilder hideOnLastPage() {
        this.hiddenOnLastPage = true;
        return this;
    }

    public NavigationBuilder autoHide() {
        this.hiddenOnSinglePage = true;
        this.hiddenOnFirstPage = true;
        this.hiddenOnLastPage = true;
        return this;
    }

    @Contract(" -> new")
    @Override
    public @NotNull Navigation build() {
        return new Navigation(
                this.previousButton,
                this.nextButton,
                this.hiddenOnSinglePage,
                this.hiddenOnFirstPage,
                this.hiddenOnLastPage
        );
    }
}