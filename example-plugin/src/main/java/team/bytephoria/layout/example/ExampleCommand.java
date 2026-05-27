package team.bytephoria.layout.example;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.ItemClickType;
import team.bytephoria.layout.items.ItemSlot;
import team.bytephoria.layout.items.base.MaterialItem;
import team.bytephoria.layout.layouts.BukkitLayout;
import team.bytephoria.layout.layouts.LoadingStrategy;
import team.bytephoria.layout.layouts.types.layout.LayoutSizedInventory;
import team.bytephoria.nms.packet.PacketLayout;
import team.bytephoria.nms.packet.types.PacketSizedLayout;

import java.util.concurrent.TimeUnit;

public final class ExampleCommand extends BukkitCommand {

    private final LayoutSizedInventory bukkitGui;
    private final PacketSizedLayout packetGui;

    public ExampleCommand() {
        super("layout-example");

        final MaterialItem glass = MaterialItem.builder()
                .material(Material.GRAY_STAINED_GLASS_PANE)
                .displayName(Component.text("Item", NamedTextColor.GREEN))
                .lore(Component.text("Line 1", NamedTextColor.YELLOW))
                .build();

        final MaterialItem button = MaterialItem.builder()
                .material(Material.EMERALD)
                .displayName(Component.text("Click me!"))
                .build();

        this.bukkitGui = BukkitLayout.sized()
                .title(Component.text("Bukkit GUI"))
                .size(3)
                .border(ItemSlot.display(glass))
                .item(13, ItemSlot.interactive(button)
                        .clicks(clickContext -> clickContext.player().sendMessage(Component.text("Clicked! " + clickContext.clickType().name())), ItemClickType.values())
                        .build())
                .behavior(b -> b
                        .onOpen(ctx -> ctx.player().sendMessage(Component.text("Opened Bukkit GUI")))
                        .onClose(ctx -> ctx.player().sendMessage(Component.text("Closed Bukkit GUI"))))
                .build();

        this.packetGui = PacketLayout.sized()
                .title(Component.text("Packet GUI"))
                .size(3)
                .border(ItemSlot.display(glass))
                .item(13, ItemSlot.interactive(button)
                        .leftClick(clickContext -> {
                            clickContext.player().sendMessage(Component.text("Left Clicked! " + clickContext.clickType().name()));
                        })
                        .otherwiseClick(clickContext -> clickContext.player().sendMessage(Component.text("Clicked! " + clickContext.clickType().name())))
                        .build())

                .behavior(b -> b
                        .onOpen(ctx -> ctx.player().sendMessage(Component.text("Opened Packet GUI")))
                        .onClose(ctx -> ctx.player().sendMessage(Component.text("Closed Packet GUI")))
                        .onClick(clickContext -> clickContext.player().sendMessage(Component.text("Global click")))
                        .clickDelay(TimeUnit.SECONDS.toMillis(5))
                        .onClickDelayed(clickContext -> clickContext.player().sendMessage(Component.text("Yo are delayed!!", NamedTextColor.RED)))
                        .allowPlayerInventoryClicks(false)
                        .cancelAllClicks(true)
                        .loading(LoadingStrategy.LAZY)
                )
                .build();

    }

    @Override
    public boolean execute(
            final @NotNull CommandSender sender,
            final @NotNull String label,
            final @NotNull String @NotNull [] args
    ) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Only players can use this command."));
            return true;
        }

        if (args.length > 0 && args[0].equals("packet")) {
            this.packetGui.open(player);
        }  else {
            this.bukkitGui.open(player);
        }

        return true;
    }
}
