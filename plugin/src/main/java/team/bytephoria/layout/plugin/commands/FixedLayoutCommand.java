package team.bytephoria.layout.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.ClickableItemLayout;
import team.bytephoria.layout.items.ItemLayout;
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
                .type(InventoryType.WORKBENCH)
                .title(Component.text("This is a fixed example menu!", NamedTextColor.BLACK))

                .fillAll(
                        ItemLayout.of(Material.STONE)
                                .material(Material.STONE)
                                .build()
                )

                .item(0,
                        ClickableItemLayout.of(Material.DIAMOND)
                                .material(Material.STONE)
                                .onLeftClick(clickContext -> clickContext.player().playSound(
                                        clickContext.player(), Sound.ENTITY_DONKEY_AMBIENT, 1f, 1f
                                ))
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
