package team.bytephoria.nms.packet.builder.page;

import team.bytephoria.layout.items.types.ItemLayout;

import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.layouts.builder.page.NavigationBuilder;
import team.bytephoria.layout.layouts.builder.page.SlotRangeBuilder;
import team.bytephoria.layout.layouts.types.paged.Navigation;
import team.bytephoria.layout.layouts.types.paged.Page;
import team.bytephoria.layout.layouts.types.paged.SlotRange;
import team.bytephoria.layout.layouts.builder.AbstractLayoutBuilder;
import team.bytephoria.nms.packet.types.PacketPagedLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public final class PacketPagedLayoutBuilder extends AbstractLayoutBuilder<PacketPagedLayoutBuilder, PacketPagedLayout> {

    private final List<ItemLayout> paginationItems = new ArrayList<>();
    private Navigation navigation = Navigation.defaults();
    private SlotRange slotRange = SlotRange.empty();

    private int initialPage = 0;
    private int pageSize = -1;
    private int size = 0;

    public PacketPagedLayoutBuilder size(final int rows) {
        this.size = Math.clamp(rows, 1, 6) * 9;
        return this.self();
    }

    public PacketPagedLayoutBuilder range(final @NotNull SlotRange slotRange) {
        this.slotRange = slotRange;
        return this.self();
    }

    public PacketPagedLayoutBuilder range(final @NotNull Consumer<SlotRangeBuilder> slotRangeConsumer) {
        final SlotRangeBuilder builder = SlotRange.builder();
        slotRangeConsumer.accept(builder);
        return this.range(builder.build());
    }

    public PacketPagedLayoutBuilder extend(final @NotNull Collection<ItemLayout> itemLayouts) {
        this.paginationItems.addAll(itemLayouts);
        return this.self();
    }

    public PacketPagedLayoutBuilder extend(final @NotNull ItemLayout @NotNull ... itemLayouts) {
        return this.extend(List.of(itemLayouts));
    }

    public PacketPagedLayoutBuilder append(final @NotNull ItemLayout itemLayout) {
        this.paginationItems.add(itemLayout);
        return this.self();
    }

    public PacketPagedLayoutBuilder navigation(final @NotNull Navigation navigation) {
        this.navigation = navigation;
        return this.self();
    }

    public PacketPagedLayoutBuilder navigation(final @NotNull Consumer<NavigationBuilder> navigationConsumer) {
        final NavigationBuilder builder = Navigation.builder();
        navigationConsumer.accept(builder);
        return this.navigation(builder.build());
    }

    public PacketPagedLayoutBuilder pageSize(final int pageSize) {
        this.pageSize = pageSize;
        return this.self();
    }

    public PacketPagedLayoutBuilder page(final int page) {
        this.initialPage = page;
        return this.self();
    }

    @Override
    public PacketPagedLayoutBuilder fill(final @NotNull ItemLayout itemLayout) {
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
    protected PacketPagedLayoutBuilder self() {
        return this;
    }

    @Override
    public @NotNull PacketPagedLayout build() {
        final int totalItems = this.paginationItems.size();
        final int itemsPerPage = this.pageSize == -1 ? this.slotRange.size() : this.pageSize;
        final int totalPages = (totalItems + (itemsPerPage - 1)) / itemsPerPage;
        final List<Page> pages = new ArrayList<>(totalPages);

        for (int pageId = 0; pageId < totalPages; pageId++) {
            final int from = pageId * itemsPerPage;
            final int to = Math.min(from + itemsPerPage, totalItems);

            final List<ItemLayout> pageItems = new ArrayList<>(to - from);
            for (int i = from; i < to; i++) {
                pageItems.add(this.paginationItems.get(i));
            }

            pages.add(new Page(pageItems));
        }

        return new PacketPagedLayout(
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
