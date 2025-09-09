package team.bytephoria.layout.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.layouts.Layout;
import team.bytephoria.layout.plugin.commands.abstraction.AbstractBukkitCommand;
import team.bytephoria.layout.plugin.commands.FixedLayoutCommand;
import team.bytephoria.layout.plugin.commands.SizedLayoutCommand;
import team.bytephoria.layout.plugin.commands.SizedPatternLayoutCommand;

public final class PaperPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Layout.init(this);

        this.registerCommand(
                new FixedLayoutCommand(),
                new SizedLayoutCommand(),
                new SizedPatternLayoutCommand()
        );
    }

    private void registerCommand(final @NotNull AbstractBukkitCommand @NotNull ... abstractBukkitCommand) {
        for (final AbstractBukkitCommand abstractBukkitCommand1 : abstractBukkitCommand) {
            this.getServer().getCommandMap().register("layout", abstractBukkitCommand1);
        }
    }
}
