# Item Usage Examples
Items in Layout inventories are highly customizable and interactive.  
There are **two main layout types**: **EmptyItemLayout** (for display) and **ClickableItemLayout** (for interaction).  
Both layouts require an **item implementation** (such as `MaterialItem`) that defines the item's appearance.

---

### Basic Display Item (`EmptyItemLayout`)

`EmptyItemLayout` is a wrapper that displays an item in the inventory with **no click actions**.  
You can pass any implementation of `ItemLayoutBase` (e.g., `MaterialItem`) to it.

```java
final EmptyItemLayout emptyItemLayout = EmptyItemLayout.display(
        MaterialItem.builder()
                .material(Material.STONE)
                .displayName(Component.text("Basic Stone", NamedTextColor.GRAY))
                .lore(
                        Component.text("This is a stone", NamedTextColor.DARK_GRAY),
                        Component.text("Static item example", NamedTextColor.GRAY)
                )
                .build()
);
```

- **MaterialItem**: Defines the material, amount, name, and lore.
- **EmptyItemLayout**: Wraps the item for display without interactions

---
### Interactive Item (`ClickableItemLayout`)
`ClickableItemLayout` allows you to make an item respond to clicks.
It still requires an `ItemLayoutBase` implementation (like `MaterialItem`) to define its appearance.

````java
final ClickableItemLayout clickableItemLayout = ClickableItemLayout.builder()
        .item(
                MaterialItem.builder()
                        .material(Material.DIAMOND_SWORD)
                        .displayName(Component.text("Epic Sword", NamedTextColor.AQUA))
                        .lore(Component.text("An interactive sword!", NamedTextColor.GRAY))
                        .build()
        )
        .onLeftClick(ctx -> ctx.player().sendMessage("Left click!"))
        .onRightClick(ctx -> ctx.player().sendMessage("Right click!"))
        .onMiddleClick(ctx -> ctx.player().sendMessage("Middle click!"))
        .build();
````
- **Click Handlers**: Define custom behavior for each click type.
- **Appearance Decoupled**: The item look is still provided by MaterialItem (or other implementations).
- **Usage**: Add to an inventory using .item(slot, clickableItem) in your inventory builder.