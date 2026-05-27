package team.bytephoria.layout.items;

import org.jetbrains.annotations.NotNull;
import team.bytephoria.layout.items.context.ClickContext;

public interface Executable {

    void execute(final @NotNull ClickContext clickContext);

}
