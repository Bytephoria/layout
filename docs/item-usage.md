# Item Usage Examples
Items in Layout inventories are highly customizable and interactive.  
There are **two main layout types**: **EmptyItemLayout** (for display) and **ClickableItemLayout** (for interaction).  
Both layouts require an **item implementation** (such as `MaterialItem` or `SkullItem`) that defines the item's appearance.

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

---

````java
final ClickableItemLayout skullItemLayout = ClickableItemLayout.builder()
        .item(
                SkullItem.builder()
                        // Option 1: Use a player reference
                        .fromPlayer(player)

                        // Option 2: Use a base64 texture value
                        // .withTextureValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWI2OGRmMDJkMGViY2ZhMmI0YTc3NDVhNDllYzAwZWZkNDJmN2E3MGVmNzNmYjdlZmI2MTA1NDRjZjBjMTA4ZiJ9fX0=")

                        // Option 3: Use a Minecraft skin texture ID
                        // .withSkinId("e609e36c6d6a631eb7b76b3eded9ccb37d2fea82031b50479be364bbd01e6340")
                        .build()
        )
        .onLeftClick(ctx -> ctx.player().sendMessage("You clicked a skull!"))
        .build();
````

---

### 💡 Notes:
- `EmptyItemLayout` is ideal for static decorative items or visual fillers in the inventory.
- `ClickableItemLayout` allows full control over player interactions through click contexts.
- Both types can be freely combined within the same inventory layout.
