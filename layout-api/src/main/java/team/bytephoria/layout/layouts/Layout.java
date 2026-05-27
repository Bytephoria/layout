package team.bytephoria.layout.layouts;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.layouts.editor.LayoutEditor;

public interface Layout extends LayoutEditor, InventoryOpenable {

    @NotNull Component title();
}
