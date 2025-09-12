package team.bytephoria.layout.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.base.MaterialItem;
import team.bytephoria.layout.items.base.builder.MaterialItemBuilder;
import team.bytephoria.layout.items.types.ClickableItemLayout;
import team.bytephoria.layout.items.types.ItemLayout;
import team.bytephoria.layout.layouts.Layout;
import team.bytephoria.layout.layouts.types.LayoutSizedInventory;
import team.bytephoria.layout.plugin.commands.abstraction.AbstractBukkitCommand;

public final class SizedLayoutCommand extends AbstractBukkitCommand {

    public SizedLayoutCommand() {
        super("sized");
    }

    @Override
    public void execute(final @NotNull Player player) {
        final LayoutSizedInventory layoutSizedInventory = Layout.sized()
                .title(Component.text("This is a sized example menu!", NamedTextColor.BLACK))
                .size(5)

                .item(22, ClickableItemLayout.builder()
                        .item(
                                new MaterialItemBuilder()
                                        .material(Material.NETHERITE_PICKAXE)
                                        .displayName(Component.text("Netherite Pickaxe", NamedTextColor.GREEN))
                                        .lore(
                                                Component.text("Line 1", NamedTextColor.GREEN),
                                                Component.text("Line 2", NamedTextColor.LIGHT_PURPLE)
                                        )
                                        .build()
                        )
                        .onLeftClick(clickContext -> clickContext.player().sendMessage("left click!"))
                        .onRightClick(clickContext -> clickContext.player().sendMessage("right click!"))
                        .onMiddleClick(clickContext -> clickContext.player().sendMessage("middle click!"))
                        .build()
                )


                .row(1, new ItemLayout(new MaterialItem(Material.RED_STAINED_GLASS_PANE)))
                .row(3, new ItemLayout(new MaterialItem(Material.GREEN_STAINED_GLASS_PANE)))
                .column(2, new ItemLayout(new MaterialItem(Material.LIME_STAINED_GLASS_PANE)))
                .column(6, new ItemLayout(new MaterialItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE)))

                .behavior(layoutBehaviorBuilder -> layoutBehaviorBuilder
                        .cancelAllClicks(false)
                        .cancelLayoutClicks(true)
                        .allowPlayerInventoryClicks(true)
                        .ignoreEmptySlots(true)
                        .onClick(context -> context.player().sendMessage("Click on inventory!"))
                        .onOpen(openContext ->
                                openContext.player().playSound(
                                        openContext.player(), Sound.BLOCK_CHEST_OPEN, 1f, 1f
                                )
                        )
                        .onClose(closeContext ->
                                closeContext.player().playSound(
                                        closeContext.player(), Sound.BLOCK_CHEST_CLOSE, 1f, 1f
                                )
                        )
                )

                .build();

        layoutSizedInventory.open(player);
        player.sendMessage(Component.text("The sized inventory has been opened.", NamedTextColor.GREEN));
    }
}
