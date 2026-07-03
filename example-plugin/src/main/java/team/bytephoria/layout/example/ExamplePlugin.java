package team.bytephoria.layout.example;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.layouts.BukkitLayout;
import team.bytephoria.nms.packet.PacketLayout;
import team.bytephoria.nms.packet.menu.PacketMenuFactory;
import team.bytephoria.nms.packet.version.NmsCapabilities;

public final class ExamplePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        BukkitLayout.init(this);
        PacketLayout.init(this, this.resolveFactory());

        this.getServer().getCommandMap().register("layout", new ExampleCommand());
    }

    private @NotNull PacketMenuFactory resolveFactory() {
        return NmsCapabilities.hasContainerInputApi() ?
                new team.bytephoria.nms.adapter.v26_1.PacketMenuFactoryImpl() :
                new team.bytephoria.nms.adapter.v1_21_11.PacketMenuFactoryImpl();
    }
}
