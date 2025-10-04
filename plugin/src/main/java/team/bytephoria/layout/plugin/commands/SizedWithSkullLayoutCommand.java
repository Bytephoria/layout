package team.bytephoria.layout.plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.base.SkullItem;
import team.bytephoria.layout.items.types.ClickableItemLayout;
import team.bytephoria.layout.items.types.EmptyItemLayout;
import team.bytephoria.layout.layouts.Layout;
import team.bytephoria.layout.layouts.types.LayoutSizedInventory;
import team.bytephoria.layout.plugin.commands.abstraction.AbstractBukkitCommand;

public final class SizedWithSkullLayoutCommand extends AbstractBukkitCommand {

    private static final String TEXTURE =
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWI2OGRmMDJkMGViY2ZhMmI0YTc3NDVhNDllYzAwZWZkNDJmN2E3MGVmNzNmYjdlZmI2MTA1NDRjZjBjMTA4ZiJ9fX0=";

    public SizedWithSkullLayoutCommand() {
        super("sized-with-skull");
    }

    @Override
    public void execute(final @NotNull Player player) {
        final LayoutSizedInventory layoutSizedInventory = Layout.sized()
                .title(Component.text("This is a sized example menu!", NamedTextColor.BLACK))
                .size(5)

                .item(21, new EmptyItemLayout(
                                SkullItem.builder()
                                        .fromPlayer(player)
                                        .build()
                        )
                )

                .item(22, ClickableItemLayout.builder()
                        .item(
                                SkullItem.builder()
                                        .withTextureValue(TEXTURE)
                                        .build()
                        )

                        .onLeftClick(inventoryClickContext -> inventoryClickContext.player().sendMessage(Component.text("Left click!")))
                        .build()
                )

                .item(23, EmptyItemLayout.builder()
                        .item(
                                SkullItem.builder()
                                        .withSkinId("e609e36c6d6a631eb7b76b3eded9ccb37d2fea82031b50479be364bbd01e6340")
                                        .build()
                        )
                        .build()
                )
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
        player.sendMessage(Component.text("The sized with skull inventory has been opened.", NamedTextColor.GREEN));
    }
}
