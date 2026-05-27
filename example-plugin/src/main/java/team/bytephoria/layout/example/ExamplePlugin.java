package team.bytephoria.layout.example;

import org.bukkit.plugin.java.JavaPlugin;
import team.bytephoria.layout.layouts.BukkitLayout;
import team.bytephoria.nms.packet.PacketLayout;

public final class ExamplePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        BukkitLayout.init(this);
        PacketLayout.init(this);

        this.getServer().getCommandMap().register("layout", new ExampleCommand());
    }
}
