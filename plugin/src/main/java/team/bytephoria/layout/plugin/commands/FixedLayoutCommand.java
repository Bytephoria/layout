package team.bytephoria.layout.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.base.MaterialItem;
import team.bytephoria.layout.items.types.ClickableItemLayout;
import team.bytephoria.layout.items.types.EmptyItemLayout;
import team.bytephoria.layout.layouts.FixedInventoryType;
import team.bytephoria.layout.layouts.Layout;
import team.bytephoria.layout.layouts.types.LayoutFixedInventory;
import team.bytephoria.layout.plugin.commands.abstraction.AbstractBukkitCommand;

public final class FixedLayoutCommand extends AbstractBukkitCommand {

    public FixedLayoutCommand() {
        super("fixed");
    }

    @Override
    public void execute(final @NotNull Player player) {
        final LayoutFixedInventory layoutFixedInventory = Layout.fixed()
                .type(FixedInventoryType.WORKBENCH)
                .title(Component.text("This is a fixed example menu!", NamedTextColor.BLACK))

                .fillAll(EmptyItemLayout.display(
                        MaterialItem.builder()
                                .material(Material.STONE)
                                .build()
                ))
                .item(0, ClickableItemLayout.builder()
                        .item(
                                MaterialItem.builder()
                                        .material(Material.STONE)
                                        .displayName(Component.text("This is a stone!"))
                                        .lore(Component.text("Line 1", NamedTextColor.GREEN))
                                        .build()
                        )
                        .onLeftClick(inventoryClickContext -> inventoryClickContext.player().sendMessage(Component.text("Left Click!")))
                        .onRightClick(inventoryClickContext -> inventoryClickContext.player().sendMessage(Component.text("Right Click!")))
                        .build()
                )

                .behavior(behaviorBuilder -> behaviorBuilder
                        .closeOnClick(true)
                        .onClick(context -> context.player().sendMessage("Click on inventory!"))
                        .onClose(context -> context.player().sendMessage("Close inventory!"))
                )

                .build();

        layoutFixedInventory.open(player);
        player.sendMessage(Component.text("The fixed inventory has been opened.", NamedTextColor.GREEN));
    }

}
