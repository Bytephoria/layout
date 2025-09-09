package team.bytephoria.layout.plugin.commands.abstraction;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBukkitCommand extends BukkitCommand {

    public AbstractBukkitCommand(final @NotNull String command) {
        super(command);
    }

    public abstract void execute(final @NotNull Player player);

    @Override
    public boolean execute(
            final @NotNull CommandSender sender,
            final @NotNull String commandLabel,
            final @NotNull String[] arguments
    ) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command is only for players.");
            return true;
        }

        this.execute(player);
        return true;
    }
}
