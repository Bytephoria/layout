package team.bytephoria.layout.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.base.MaterialItem;
import team.bytephoria.layout.items.types.EmptyItemLayout;
import team.bytephoria.layout.layouts.Layout;
import team.bytephoria.layout.layouts.types.LayoutSizedInventory;
import team.bytephoria.layout.plugin.commands.abstraction.AbstractBukkitCommand;

public final class SizedPatternLayoutCommand extends AbstractBukkitCommand {

    public SizedPatternLayoutCommand() {
        super("sized-pattern");
    }

    @Override
    public void execute(final @NotNull Player player) {
        final LayoutSizedInventory layoutSizedInventory = Layout.sized()
                .title(Component.text("This is a sized pattern example menu!", NamedTextColor.BLACK))
                .patterned(patternBuilder -> patternBuilder
                        .pattern(
                                "",
                                "",
                                " AAAAAAA ",
                                " BBBBBBB ",
                                "",
                                ""
                        )
                        .key('A', EmptyItemLayout.display(new MaterialItem(Material.NETHERITE_INGOT)))
                        .key('B', EmptyItemLayout.display(new MaterialItem(Material.DIAMOND)))
                )

                .border(EmptyItemLayout.display(new MaterialItem(Material.MAGENTA_STAINED_GLASS_PANE)))
                .behavior(behaviorBuilder -> behaviorBuilder
                        .onOpen(openContext -> openContext.player().sendMessage("Opened inventory"))
                        .onClose(context -> context.player().sendMessage("Closed Inventory!"))
                        .onClick(context -> context.player().sendMessage("Click on inventory!"))
                )

                .build();

        layoutSizedInventory.open(player);
        player.sendMessage(Component.text("The sized pattern inventory has been opened.", NamedTextColor.GREEN));
    }
}
