# Item Usage Examples
Items in Layout inventories are highly customizable and interactive. The library provides two main types: ItemLayout and ClickableItemLayout.

---

### Basic Item (`ItemLayout`)

`ItemLayout` is used to create static items for an inventory. It's only for display purposes, defining the item's material, a custom name, and descriptive lore without adding any interactive behavior. It handles the item's appearance, not its actions.

```java
final ItemLayout stoneItem = ItemLayout.builder()
        .material(Material.STONE)
        .displayName(Component.text("Basic Stone", NamedTextColor.GRAY))
        .lore(
                Component.text("This is a stone", NamedTextColor.DARK_GRAY),
                Component.text("Static item example", NamedTextColor.GRAY)
        )
        .build();

```

- Material: The item type in Minecraft (e.g., STONE, DIAMOND).
- Display Name: The custom name shown to the player.
- Lore: Additional descriptive lines below the name.

### Clickable Item (`ClickableItemLayout`)
ClickableItemLayout extends ItemLayout with click handlers using ClickContext. You can define actions for left, right, or middle clicks.
```java
final ClickableItemLayout clickableItem = ClickableItemLayout.builder()
        .material(Material.DIAMOND_SWORD)
        .displayName(Component.text("Epic Sword", NamedTextColor.AQUA))
        .onLeftClick(clickContext -> clickContext.player().sendMessage("Left click!"))
        .onRightClick(clickContext -> clickContext.player().sendMessage("Right click!"))
        .onMiddleClick(clickContext -> clickContext.player().sendMessage("Middle click!"))
        .build();
```
- Click Handlers: Implement logic using the ClickContext. The library does not execute commands or play sounds automatically.
- Slot Assignment: Items are assigned to slots using the inventory builder (.item(slot, itemLayout)).
