package team.bytephoria.layout.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import team.bytephoria.layout.layouts.Layout;

public final class PaperPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Initialize the Layout library (required for the library to work properly)
        Layout.init(this);
    }

}
