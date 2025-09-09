# Usage Examples

Layout is designed to make inventory creation intuitive, expressive, and highly flexible. Below are examples showcasing the main features of the library.

---

### Fixed Inventories (`LayoutFixedInventory`)

Fixed inventories have a predefined number of slots and a specific type, such as a crafting table or a dispenser. They are ideal for static menus with a consistent layout.

```java
final LayoutFixedInventory layoutFixedInventory = Layout.fixed()
        .type(InventoryType.WORKBENCH)
        .title(Component.text("This is a fixed example menu!", NamedTextColor.BLACK))

        // Fill all slots with a specific item
        .fillAll(ItemLayout.display(Material.STONE))

        // Add a clickable item
        .item(0, ClickableItemLayout.of(Material.DIAMOND)
                .onLeftClick(clickContext -> clickContext.player().sendMessage("Left click!"))
                .build()
        )

        // Define inventory behavior
        .behavior(behaviorBuilder -> behaviorBuilder
                .closeOnClick(true)
                .onClick(context -> context.player().sendMessage("Clicked on inventory!"))
                .onClose(context -> context.player().sendMessage("Inventory closed!"))
        )

        .build();

// Open the inventory for the player
layoutFixedInventory.open(player);

```

<img src="images/fixed-example.png" height="336" alt="hola"/>


### Sized Inventories (`LayoutSizedInventory`)
Sized inventories resemble a chest interface where you can define the number of rows. They allow you to create flexible menus of varying sizes.
```java
final LayoutSizedInventory layoutSizedInventory = Layout.sized()
        .title(Component.text("This is a sized example menu!", NamedTextColor.BLACK))
        .size(5)

        // Add a clickable item with multiple click handlers
        .item(22, ClickableItemLayout.builder()
                .material(Material.NETHERITE_PICKAXE)
                .displayName(Component.text("Netherite Pickaxe", NamedTextColor.GREEN))
                .lore(
                        Component.text("Line 1", NamedTextColor.GREEN),
                        Component.text("Line 2", NamedTextColor.LIGHT_PURPLE)
                )
                .onLeftClick(clickContext -> clickContext.player().sendMessage("Left click!"))
                .onRightClick(clickContext -> clickContext.player().sendMessage("Right click!"))
                .onMiddleClick(clickContext -> clickContext.player().sendMessage("Middle click!"))
                .build()
        )

        // Fill specific rows and columns with items
        .row(1, ItemLayout.display(Material.RED_STAINED_GLASS_PANE))
        .row(3, ItemLayout.display(Material.GREEN_STAINED_GLASS_PANE))
        .column(2, ItemLayout.display(Material.LIME_STAINED_GLASS_PANE).buil)
        .column(6, ItemLayout.display(Material.LIGHT_BLUE_STAINED_GLASS_PANE))

        // Define inventory behavior
        .behavior(layoutBehaviorBuilder -> layoutBehaviorBuilder
                .cancelAllClicks(false)
                .cancelLayoutClicks(true)
                .allowPlayerInventoryClicks(true)
                .ignoreEmptySlots(true)
                .onClick(context -> context.player().sendMessage("Clicked on inventory!"))
                .onOpen(openContext -> openContext.player().sendMessage("Inventory opened!"))
                .onClose(closeContext -> closeContext.player().sendMessage("Inventory closed!"))
        )

        .build();

layoutSizedInventory.open(player);
```
<img src="images/sized-example.png" height="407" alt="hola"/>


### Pattern-Based Inventories (`LayoutSizedInventory` only)
Pattern-based inventories allow creating visually structured menus using a character grid. This feature is exclusive to `LayoutSizedInventory`.

```java
final LayoutSizedInventory layoutPatternInventory = Layout.sized()
        .title(Component.text("Patterned inventory example", NamedTextColor.BLACK))
        .patterned(patternBuilder -> patternBuilder
                .pattern(
                        "",
                        "",
                        " AAAAAAA ",
                        " BBBBBBB ",
                        "",
                        ""
                )
                .key('A', ItemLayout.display(Material.NETHERITE_INGOT))
                .key('B', ItemLayout.display(Material.DIAMOND))
        )

        // Add a border around the pattern
        .border(ItemLayout.display(Material.MAGENTA_STAINED_GLASS_PANE))

        // Define behavior
        .behavior(behaviorBuilder -> behaviorBuilder
                .onOpen(openContext -> openContext.player().sendMessage("Inventory opened"))
                .onClose(context -> context.player().sendMessage("Inventory closed"))
                .onClick(context -> context.player().sendMessage("Clicked on inventory"))
        )

        .build();

layoutPatternInventory.open(player);
```

<img src="images/sized-pattern-example.png" height="448" alt="hola"/>


### 💡 Notes:
- Click events are handled through ClickContext. The library does not perform automatic actions; you decide what happens on each click.
- Sounds, commands, or other effects must be implemented manually using the ClickContext or OpenContext/CloseContext.
- Patterned inventories simplify visual layout creation but are limited to LayoutSizedInventory.
- This library is designed for flexible inventory creation in Minecraft plugins. It provides layouts and click handling, but all behavior is controlled by you.
