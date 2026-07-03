package team.bytephoria.nms.packet.types;

import team.bytephoria.layout.items.types.ItemLayout;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.kyori.adventure.text.Component;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.bytephoria.layout.layouts.behavior.Behavior;
import team.bytephoria.layout.layouts.types.paged.Navigation;
import team.bytephoria.layout.layouts.types.paged.Page;
import team.bytephoria.layout.layouts.types.paged.PagedLayoutView;
import team.bytephoria.layout.layouts.types.paged.SlotRange;
import team.bytephoria.nms.packet.base.PacketLayoutBase;
import team.bytephoria.nms.packet.builder.page.PacketPagedLayoutBuilder;
import team.bytephoria.nms.packet.render.PacketContainerKind;

import java.util.Arrays;
import java.util.List;

public final class PacketPagedLayout extends PacketLayoutBase implements PagedLayoutView {

    private final List<Page> pages;
    private final SlotRange slotRange;
    private final Navigation navigation;
    private final int inventorySize;
    private int currentPage;

    private boolean rendering = false;

    public PacketPagedLayout(
            final @NotNull Behavior behavior,
            final @NotNull Int2ObjectArrayMap<ItemLayout> itemLayouts,
            final @NotNull Component title,
            final int inventorySize,
            final @NotNull List<Page> pages,
            final @NotNull SlotRange slotRange,
            final @NotNull Navigation navigation,
            final int initialPage
    ) {
        super(behavior, itemLayouts, title);
        this.inventorySize = inventorySize;
        this.pages = pages;
        this.slotRange = slotRange;
        this.navigation = navigation;
        this.currentPage = initialPage;
    }

    @Contract(" -> new")
    public static @NotNull PacketPagedLayoutBuilder builder() {
        return new PacketPagedLayoutBuilder();
    }

    @Override
    protected @NotNull PacketContainerKind containerKind() {
        return switch (this.inventorySize / 9) {
            case 1 -> PacketContainerKind.GENERIC_9X1;
            case 2 -> PacketContainerKind.GENERIC_9X2;
            case 3 -> PacketContainerKind.GENERIC_9X3;
            case 4 -> PacketContainerKind.GENERIC_9X4;
            case 5 -> PacketContainerKind.GENERIC_9X5;
            default -> PacketContainerKind.GENERIC_9X6;
        };
    }

    @Override
    protected int size() {
        return this.inventorySize;
    }

    @Override
    protected @Nullable ItemLayout resolveItem(final int slot) {
        final int areaPosition = Arrays.binarySearch(this.slotRange.slots(), slot);
        return areaPosition > -1
                ? this.currentPage().item(areaPosition)
                : this.itemLayouts.get(slot);
    }

    @Override
    public @NotNull List<ItemStack> buildNmsItemList() {
        final List<ItemStack> items = super.buildNmsItemList();

        final Page page = this.currentPage();
        final int[] slots = this.slotRange.slots();
        for (int i = 0; i < Math.min(page.totalItems(), slots.length); i++) {
            final ItemLayout layout = page.item(i);
            if (layout != null) {
                items.set(slots[i], CraftItemStack.asNMSCopy(layout.item().to()));
            }
        }

        return items;
    }

    @Override
    public void item(final int slot, final @Nullable ItemLayout itemLayout) {
        if (itemLayout == null) {
            this.itemLayouts.remove(slot);
        } else {
            this.itemLayouts.put(slot, itemLayout);
        }
        if (!this.rendering) {
            this.renderAll();
        }
    }

    @Override
    public void setItem(final int slot, final @Nullable ItemLayout itemLayout) {
        this.item(slot, itemLayout);
    }

    @Override
    public void nextPage() {
        if (this.isLastPage()) {
            return;
        }
        this.currentPage++;
        this.refreshPage();
    }

    @Override
    public void previousPage() {
        if (this.isFirstPage()) {
            return;
        }
        this.currentPage--;
        this.refreshPage();
    }

    private void refreshPage() {
        this.rendering = true;
        this.clearPaginationSlots();
        this.currentPage().renderTo(this, this.slotRange);
        this.navigation.render(this);
        this.rendering = false;
        this.renderAll();
    }

    private void clearPaginationSlots() {
        for (final int slot : this.slotRange.slots()) {
            this.itemLayouts.remove(slot);
        }
    }

    @Override
    public boolean isFirstPage() {
        return this.currentPage == 0;
    }

    @Override
    public boolean isLastPage() {
        return this.currentPage == this.pages.size() - 1;
    }

    @Override
    public int totalPages() {
        return this.pages.size();
    }

    @Override
    public boolean isItemsLoaded() {
        return this.itemsLoaded;
    }

    private @NotNull Page currentPage() {
        return this.pages.get(this.currentPage);
    }
}
